# CaloriePay

> 2024 BIDIA Dev-ton 출품작

AI 기반 식단 관리 다이어리 플랫폼

## 프로젝트 소개

CaloriePay는 사용자의 일일 칼로리 섭취량을 모니터링하고, 운동을 통한 칼로리 소모를 추적하여 개인 맞춤형 건강 관리를 지원하는 플랫폼입니다. AI 이미지 분석을 통해 식사 사진만으로 칼로리를 자동으로 계산하고, 게임처럼 티어 시스템을 통해 재미있게 건강을 관리할 수 있습니다.

## 주요 기능

- **회원관리 및 JWT 인증**: 안전한 토큰 기반 인증 시스템
- **AI 식사 분석**: 식사 사진 업로드를 통한 자동 칼로리 분석
- **운동 기록**: 다양한 운동 종류별 칼로리 소모량 추적
- **일일/월간 티어 시스템**: 칼로리 관리 성과를 S/A/B/C/D 티어로 평가
- **개인 맞춤 추천 칼로리**: BMR 기반 일일 권장 칼로리 계산
- **실시간 랭킹**: Redis 기반 전체 사용자 점수 랭킹
- **캘린더 조회**: 월간/일별 칼로리 관리 내역 확인
- **배치 처리**: 매일 자정 자동 점수 및 티어 정산

## 담당 기능
- **운동 기록**
- **일일/월간 티어 시스템**
- **실시간 랭킹** : Redis활용
- **캘린더 조회**
- **배치 처리** : Spring Batch 기반 정산

## 기술 스택

### Backend
- **Java 24** with Preview Features
- **Spring Boot 3.5.3**
- **Spring Data JPA** + **QueryDSL 5.0.0**
- **Spring Batch** (일일 정산 자동화)
- **Spring Security** + **JWT** (JJWT 0.11.5)

### Database
- **MariaDB** (운영 DB)
- **H2 Database** (테스트 DB)
- **Redis** (캐싱 & 랭킹 시스템)

### Infrastructure
- **AWS S3** (이미지 파일 저장)

## 아키텍처
- 계층형 + 이벤트 기반

## 해결한 부분

### 1️⃣ 메인화면 및 사용자 스코어 랭킹 조회 시 속도 저하 문제

**문제:** 메인화면 및 사용자 스코어 랭킹 조회 시 100ms 및 600ms 속도 저하 발생

**원인:** 메인화면에서는 여러가지의 도메인들을 사용했기 때문에 그에 따른 지연시간이 발생, 랭킹 기능에서는 사용자 수가 증가했을때 요청 마다 통계 쿼리를 적용하였기 때문에 그에 따른 지연시간이 발생

**해결:**

- 먼저 메인화면의 경우 하루에 많아도 **데이터가 5번 이하로 변경**되는것을 파악, 랭킹 시스템의 경우에는 **하루에 한 번 정산**되는 스코어를 기반으로 동작하기 때문에 변경은 하루에 한번으로 파악
- 따라서 자주 변하지 않는 데이터 특성상 **캐싱기능 도입**을 고려
- 데이터는 **Memcached or Redis** 두가지 중 하나를 고려했지만 랭킹 시스템 구현시 **SortedSet 자료구조**를 활용할 수 있는 **Redis를 선택**

**핵심 코드:**

RankingRepository의 일부
```java
    public void add(long userId, int score) {
        redisTemplate.executePipelined((RedisCallback<?>) action -> {
            StringRedisConnection conn = (StringRedisConnection) action;
            conn.zAdd(KEY, score, String.valueOf(userId));  // SortedSet에 추가
            conn.zRemRange(KEY, 0, -limit-1);  // 상위 100명만 유지
            conn.expire(KEY, ttl.toSeconds());
            return null;
        });
    }
```

메인화면은 다음과 같이 해당하는 내용돌을 @Cacheable을 적용하여 구현
```java
    @Cacheable(value = USER_CALORIE_SCORE_CACHE, key = "#userId",cacheManager = "caloriePayCacheManager")
    public ResponseCalorieScoreDto getCalorieScoreByUserIdAndDate(Long userId){}
```

데이터가 변경될때는 다음과 같이 @CacheEvict로 캐시를 무효화
```java
    @CacheEvict(value = USER_CALORIE_SCORE_CACHE, key = "#exerciseEventDto.userId", cacheManager = "caloriePayCacheManager")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void caloriePlus(ExerciseEventDto exerciseEventDto){}
```

**아쉬웠던 점:** 돌아와서 생각해보니 운동이면 운동 따로 사용자 정보면 사용자 정보 따로 캐싱을 진행한것이 아쉬운것같다. 별도의 Read 모델을 만들고 해당 모델을 캐시했다면 더 좋았을것 같다

**결과:** 메인화면 응답 속도 **100ms → 40ms**, 랭킹 조회 속도 **600ms → 40ms**로 개선

---

### 2️⃣ 대량 데이터 정산 시 트랜잭션 롤백 및 처리 지연 문제

**문제:** 대량 데이터 정산 시 트랜잭션 롤백 및 처리 지연 발생 및 정산 로직 추적이 어려웠던 점

**원인:**

- 먼저 **추적이 어려웠던점**은 스코어 정산 -> 티어정산 -> 일일 스코어 초기화 작업을 진행 했었는데 이벤트를 도입하게 되면서 스코어 정산 -> 스코어 정산 이벤트 발행 -> 티어 정산 -> 티어 정산 이벤트 발행 ..
이런식으로 진행되어서 실제 코드를 유지보수할 때 **추적에 어려움**이 있었다.
- 정산작업을 **while()문을 사용**해 ...Repository.save()를 하여 **많은 네트워크 요청이 발생**해 속도가 매우 느려지는 현상
- **while()문이 하나의 트랜잭션으로 묶여** 만개의 데이터를 처리할 때 오류가 발생하면 이전에 정상적으로 완료된 작업들까지 **한꺼번에 롤백**되는 현상

**해결:**

**Spring Batch를 도입**하여 이 문제를 해결하였다

**① 정산 로직 추적 문제 해결**

Spring Batch를 사용하면 **Job 및 Step을 단계적으로 구현**하여 추적에 용이하였다.

```java
@Bean
public Job dailySummaryJob(){
    return new JobBuilder("dailySummaryJob", jobRepository)
            .start(dailyCalorieSummaryScoreStep)
            .next(dailyCalorieSummaryTierStep)
            .next(dailyCalorieSummaryResetStep)
            .next(dailyUserScoreStep)
            .build();
}
```

**② 네트워크 요청 문제 해결**

**커서기반 ItemReader를 도입**하여 해결하였다. 커서 기반 ItemReader는 한번의 연결 후 포인터를 옮겨가며 데이터를 읽어오기에 이전과 달리 **만건의 네트워크 요청에서 1번의 네트워크 요청으로 줄였다.**

```java
@Bean
public JpaCursorItemReader<Object[]> dailyCalorieSummaryScoreReader() {
    // Cursor 기반 Reader → 스트리밍 방식으로 데이터 조회
    return new JpaCursorItemReaderBuilder<Object[]>()
            .name("dailyCalorieSummaryScoreReader")
            .entityManagerFactory(emf)
            .queryString("SELECT m, c.score FROM Member m " +
                    "JOIN CalorieScoreHistory c ON m.id = c.userId " +
                    "WHERE c.date = :currentDate")
            .parameterValues(Map.of("currentDate", LocalDate.now()))
            .build();
}
```

**③ 트랜잭션 롤백 문제 해결**

Spring Batch는 **JobExecution에 배치 작업 결과 및 정보를 저장**해놓기 때문에 이전에 완료한 시점부터 처리가 가능하다. 뿐만 아니라 **청크 단위의 스텝을 구성**하여 만 건의 데이터가 있을때 **청크 단위로 트랜잭션을 묶을 수 있었다.**

```java
@Bean
public Step dailyUserScoreStep() {
    return new StepBuilder("DailyUserScoreStep", jobRepository)
            // 청크 단위를 20으로 설정 → 트랜잭션 범위 축소
            .<Object[], Member>chunk(20, transactionManager)
            .reader(dailyCalorieSummaryScoreReader())
            .processor(dailyCalorieSummaryScoreProcessor())
            .writer(dailyCalorieSummaryScoreWriter())
            .build();
}
```

**결과:** 데이터를 청크 단위로 트랜잭션을 분리 및 실패한 지점부터 재시작 가능하도록 개선할 수 있었다.

---

### 3️⃣ 트랜잭션 롤백 시에도 이벤트가 실행되는 데이터 정합성 문제

**문제:** 회원이 가입 후 프로필을 등록하게 되면 프로필생성 이벤트가 발행되고 해당 유저의 스코어를 생성하는 로직이 있었다.
이때 **프로필 등록중 오류가 발생하여 트랜잭션이 롤백 되어도 해당 유저의 스코어가 생성되버리는 문제**가 생겼다.

**원인:** 기존에 사용하던 **@EventListener는 트랜잭션 커밋과 무관하게 이벤트 발생 즉시 리스너를 실행**하도록 설계되어 있던 점이 문제였다.

**해결:**

**① 트랜잭션 커밋 후에만 이벤트 실행**

위의 문제를 해결하기위해 트랜잭션의 특정 단계에서 이벤트를 처리할 수 있는 **@TransactionalEventListener**을 적용하였다.

옵션에서 **TransactionPhase.AFTER_COMMIT**을 사용해 프로필 등록 트랜잭션이 커밋된 후 이벤트 리스너가 실행되도록 만들었다. 즉 **프로필 등록 중 오류가 발생하면 이벤트가 실행되지 않는다.**

**② 이벤트 처리 실패가 본 트랜잭션에 영향을 주지 않도록 트랜잭션 분리**

두번째로는 프로필 등록 후 스코어가 생성되는 부분에서 한 트랜잭션으로 묶이기 때문에 스코어가 생성 도중 오류가 발생하면 등록되었던 프로필도 롤백되는 문제가 있었는데 이것은 **스코어 생성시 새 트랜잭션을 시작**하도록 만들어 해결했다.

**@Transactional**의 **Propagation.REQUIRES_NEW** 옵션을 사용해 이전 트랜잭션과 분리하여 새 트랜잭션을 시작하도록 구성했다.

**핵심 코드:**

```java
// Before: 트랜잭션 커밋과 무관하게 즉시 실행
@EventListener
public void calorieMinus(MealEventDto event) {
    // 이전 트랜잭션이 롤백되어도 실행됨 → 데이터 정합성 문제
    RecommandCalorie calorieChange = repository.findByUserId(event.getUserId());
    calorieChange.minusCalorie(event.getCalorie());
    repository.save(calorieChange);
}

// After: 트랜잭션 커밋 후 새로운 트랜잭션에서 실행
@Component
public class CalorieScoreAndChangeEventListener {

    // 1. 트랜잭션 커밋 후에만 실행
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    // 2. 별도 트랜잭션으로 분리 → 이벤트 처리 실패가 원본 트랜잭션에 영향 없음
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void calorieMinus(MealEventDto mealEventDto) {
        RecommandCalorie calorieChange = dailyCalorieChangeRepository
            .findByUserId(mealEventDto.getUserId())
            .orElseThrow(() -> new CustomException(ResCode.DAILY_CHANGE_NOT_FOUND));

        calorieChange.minusCalorie(mealEventDto.getCalorie());
        dailyCalorieChangeRepository.save(calorieChange);
    }
}
```

**결과:**

- 이벤트가 **트랜잭션 커밋 이후에만 실행**되도록 개선하여 **데이터 정합성과 처리 순서 보장**
- 트랜잭션 분리로 **이벤트 처리 실패가 본 트랜잭션에 영향을 주지 않도록 안정성 확보**

## 프로젝트 구조

```
src/main/java/com/pknu/caloriepay/
├── concept/                   # 공통 개념 (티어, 운동 정보 등)
├── domain/                    # 비즈니스 도메인
│   ├── auth/                 # 인증 (JWT)
│   ├── user/                 # 사용자 관리
│   ├── meal/                 # 식사 기록
│   ├── exercise/             # 운동 기록
│   ├── score/                # 칼로리 점수
│   ├── tier/                 # 티어 관리
│   ├── recommandcalorie/     # 추천 칼로리
│   ├── calender/             # 캘린더
│   └── file/                 # 파일 업로드 (S3)
└── global/                    # 전역 설정
    ├── batch/                # 배치 처리
    ├── config/               # 설정 (Security, Redis, QueryDSL 등)
    ├── event/                # 이벤트 시스템
    ├── error/                # 에러 처리
    └── filter/               # JWT 필터
```


```
