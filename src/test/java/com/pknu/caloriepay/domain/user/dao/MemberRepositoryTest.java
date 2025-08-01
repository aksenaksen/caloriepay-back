package com.pknu.caloriepay.domain.user.dao;

import com.pknu.caloriepay.domain.user.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    private List<Member> dummyMembers;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        dummyMembers = new ArrayList<>();

        Profile commonProfile = Profile.builder()
                .gender(Gender.MALE)
                .age(30)
                .height(175.0)
                .weight(70.0)
                .goal(Goal.MAINTAIN)
                .targetWeight(70.0)
                .activityLevel(ActivityLevel.NORMAL)
                .build();

        Preferences commonPreferences = new Preferences(true, JoinType.SOSIAL);

        for (long i = 1; i <= 160; i++) {
            int score = (int)(Math.random() * 1000);

            Member member = Member.builder()
                    .name("User" + i)
                    .email("user" + i + "@test.com")
                    .phoneNumber("010-0000-" + String.format("%04d", i))
                    .nickname("nickname" + i)
                    .profile(commonProfile)
                    .preferences(commonPreferences)
                    .score(score)
                    .build();

            dummyMembers.add(member);
            memberRepository.save(member);
        }

        Member member = Member.builder()
                .name("User" + "RankOne")
                .email("userrank1@test.com")
                .phoneNumber("010-0000-1212")
                .nickname("nickname" + "rank")
                .profile(commonProfile)
                .preferences(commonPreferences)
                .score(77777)
                .build();

        dummyMembers.add(member);
        memberRepository.save(member);

        Member member2 = Member.builder()
                .name("User" + "RankLast")
                .email("userrank2@test.com")
                .phoneNumber("010-0000-2222")
                .nickname("nickname" + "rank")
                .profile(commonProfile)
                .preferences(commonPreferences)
                .score(-10)
                .build();

        dummyMembers.add(member2);
        memberRepository.save(member2);

        Member member3 = Member.builder()
                .name("User" + "RankOne3")
                .email("userrank3@test.com")
                .phoneNumber("010-0000-1312")
                .nickname("nickname" + "ran2k")
                .profile(commonProfile)
                .preferences(commonPreferences)
                .score(77777)
                .build();

        dummyMembers.add(member3);
        memberRepository.save(member3);
    }

    @Test
    @DisplayName("멤버 저장 및 수 조회 테스트")
    void memberSaveTest() {
        List<Member> all = memberRepository.findAll();
        assertThat(all).hasSize(163);
    }

    @Test
    @DisplayName("")
    void memberRankTest(){
        Member member = memberRepository.findByEmail("userrank2@test.com").get();
        assertThat(member).isInstanceOf(Member.class);

        Long userId = member.getId();
        Long rank = memberRepository.findRank(userId);
        assertThat(rank).isEqualTo(163L);



        Member member2 = memberRepository.findByEmail("userrank1@test.com").get();
        assertThat(member).isInstanceOf(Member.class);

        Long userId2 = member2.getId();
        Long rank2 = memberRepository.findRank(userId2);
        assertThat(rank2).isEqualTo(1L);

        Member member3 = memberRepository.findByEmail("userrank3@test.com").get();
        assertThat(member).isInstanceOf(Member.class);

        Long userId3 = member2.getId();
        Long rank3 = memberRepository.findRank(userId3);
        assertThat(rank2).isEqualTo(1L);
    }

}
