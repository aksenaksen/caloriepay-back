package com.pknu.caloriepay.domain.score.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.score.application.CalorieScoreService;
import com.pknu.caloriepay.domain.score.application.DailyCalorieChangeService;
import com.pknu.caloriepay.domain.score.dto.ResponseScoreAndCalorieDto;
import com.pknu.caloriepay.global.dto.BaseRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kcal")
public class CalorieScoreApi {

    private final CalorieScoreService calorieScoreService;
    private final DailyCalorieChangeService dailyCalorieChangeService;

    @GetMapping("")
    public ResponseEntity<BaseRes<ResponseScoreAndCalorieDto>> getCalorieChange(@AuthenticationPrincipal CurrentMemberInfo memberInfo){
        ResponseScoreAndCalorieDto responseScoreAndCalorieDto = new ResponseScoreAndCalorieDto(
                dailyCalorieChangeService.getCalorieChange(memberInfo.memberId()),
                calorieScoreService.getCalorieScoreByUserIdAndDate(memberInfo.memberId(), LocalDate.now())
        );
        return ResponseEntity.ok(BaseRes.success(responseScoreAndCalorieDto));
    }

}
