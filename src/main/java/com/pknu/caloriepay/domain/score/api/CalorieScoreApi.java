package com.pknu.caloriepay.domain.score.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.score.application.CalorieRankingService;
import com.pknu.caloriepay.domain.score.application.CalorieScoreService;
import com.pknu.caloriepay.domain.score.domain.DailyCalorieChangeScheduler;
import com.pknu.caloriepay.domain.score.dto.out.ResponseCalorieScoreDto;
import com.pknu.caloriepay.domain.score.dto.out.ResponseCalorieScoreRankingDto;
import com.pknu.caloriepay.domain.score.dto.out.ResponseScoreAndCalorieDto;
import com.pknu.caloriepay.global.dto.BaseRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kcal")
public class CalorieScoreApi {

    private final CalorieScoreService calorieScoreService;
    private final DailyCalorieChangeScheduler dailyCalorieChangeService;
    private final CalorieRankingService calorieRankingService;

    @GetMapping("")
    public ResponseEntity<BaseRes<ResponseScoreAndCalorieDto>> getCalorieChange(@AuthenticationPrincipal CurrentMemberInfo memberInfo){
        ResponseScoreAndCalorieDto responseScoreAndCalorieDto = new ResponseScoreAndCalorieDto(
                dailyCalorieChangeService.getCalorieChange(memberInfo.memberId()),
                calorieScoreService.getCalorieScoreByUserIdAndDate(memberInfo.memberId())
        );
        return ResponseEntity.ok(BaseRes.success(responseScoreAndCalorieDto));
    }
    @GetMapping("/change")
    public ResponseEntity<BaseRes<List<ResponseCalorieScoreDto>>> getCalorieChangesFor5Month(@AuthenticationPrincipal CurrentMemberInfo memberInfo, @RequestParam(name = "offset") Integer offset){
        List<ResponseCalorieScoreDto> calorieScoreDto = calorieScoreService.getCalorieScoreChangeFor5Month(memberInfo.memberId(),offset);
        return ResponseEntity.ok(BaseRes.success(calorieScoreDto));
    }
    @PostMapping("")
    public ResponseEntity<BaseRes<Void>> postRefreshCalorieScore(@AuthenticationPrincipal CurrentMemberInfo memberInfo){
        calorieScoreService.refreshCalorieScoreByUserId(memberInfo.memberId());
        return ResponseEntity.ok(BaseRes.success(null));
    }
    @GetMapping("/rank")
    public ResponseEntity<BaseRes<List<ResponseCalorieScoreRankingDto>>> getCalorieScoreRanking(){

        List<ResponseCalorieScoreRankingDto> calorieScoreRankingDtoList = calorieRankingService.getLatestScoreRanking();
        return ResponseEntity.ok(BaseRes.success(calorieScoreRankingDtoList));
    }

    @GetMapping("/rank/member")
    public ResponseEntity<BaseRes<ResponseCalorieScoreRankingDto>> getCalorieScoreRankingMember(@AuthenticationPrincipal CurrentMemberInfo memberInfo){
        ResponseCalorieScoreRankingDto calorieScoreRankingDto = calorieRankingService.findUserRankingByUserId(memberInfo.memberId());
        return ResponseEntity.ok(BaseRes.success(calorieScoreRankingDto));
    }

    @GetMapping("/month")
    public ResponseEntity<BaseRes<ResponseCalorieScoreDto>> getHighCalorieScoreOfMonth(@AuthenticationPrincipal CurrentMemberInfo memberInfo, @RequestParam(name = "date")LocalDate date){
        ResponseCalorieScoreDto dto = calorieScoreService.getHighCalorieScoreOfMonth(memberInfo.memberId(), date);
        return ResponseEntity.ok(BaseRes.success(dto));
    }


}
