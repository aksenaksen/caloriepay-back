package com.pknu.caloriepay.domain.score.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.score.application.CalorieScoreHistoryService;
import com.pknu.caloriepay.domain.score.application.out.CalorieScoreHistoryResponse;
import com.pknu.caloriepay.domain.score.event.DailyCalorieChangeScheduler;
import com.pknu.caloriepay.global.dto.BaseRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kcal")
public class CalorieScoreHistoryApi {

    private final CalorieScoreHistoryService calorieScoreHistoryService;
    private final DailyCalorieChangeScheduler dailyCalorieChangeService;
//
//    @GetMapping("")
//    public ResponseEntity<BaseRes<ResponseScoreAndCalorieDto>> getCalorieChange(@AuthenticationPrincipal CurrentMemberInfo memberInfo){
//        ResponseScoreAndCalorieDto responseScoreAndCalorieDto = new ResponseScoreAndCalorieDto(
//                dailyCalorieChangeService.getCalorieChange(memberInfo.memberId()),
//                calorieScoreService.getCalorieScoreByUserIdAndDate(memberInfo.memberId())
//        );
//        return ResponseEntity.ok(BaseRes.success(responseScoreAndCalorieDto));
//    }
    @GetMapping("/change")
    public ResponseEntity<BaseRes<List<CalorieScoreHistoryResponse>>> getCalorieHistoryBetweenMonth(@AuthenticationPrincipal CurrentMemberInfo memberInfo, @RequestParam(name = "offset") Integer offset){
        List<CalorieScoreHistoryResponse> calorieScoreDto = calorieScoreHistoryService.findCalorieScoreHistoryBetweenMonth(memberInfo.memberId(),offset);
        return ResponseEntity.ok(BaseRes.success(calorieScoreDto));
    }


}
