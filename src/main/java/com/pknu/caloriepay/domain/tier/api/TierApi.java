package com.pknu.caloriepay.domain.tier.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.tier.application.DailyTierService;
import com.pknu.caloriepay.domain.tier.application.MonthlyTierService;
import com.pknu.caloriepay.domain.tier.dto.out.ResponseDailyTierOfMonth;
import com.pknu.caloriepay.domain.tier.dto.out.ResponseTier;
import com.pknu.caloriepay.global.dto.BaseRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tier")
@RequiredArgsConstructor
public class TierApi {

    private final MonthlyTierService monthlyTierService;
    private final DailyTierService dailyTierService;

    @GetMapping("/month")
    public ResponseEntity<BaseRes<ResponseTier>> getMonthTier(@AuthenticationPrincipal CurrentMemberInfo currentMemberInfo, @RequestParam("year") Long year, @RequestParam Long month){
        LocalDate date = LocalDate.of(year.intValue(), month.intValue(), 1);
        LocalDate lastDayOfMonth = date.withDayOfMonth(LocalDate.of(year.intValue(), month.intValue(), 1).lengthOfMonth());
        ResponseTier tier = monthlyTierService.getTierByDate(currentMemberInfo.memberId(),lastDayOfMonth);
        return ResponseEntity.ok(BaseRes.success(tier));
    }
    @GetMapping("/month/amount")
    public ResponseEntity<BaseRes<List<ResponseDailyTierOfMonth>>> getMonthTierAmount(@AuthenticationPrincipal CurrentMemberInfo currentMemberInfo, @RequestParam("date") LocalDate date){
        List<ResponseDailyTierOfMonth> dailyTierOfMonths= dailyTierService.getDailyTierOfMonth(currentMemberInfo.memberId(),date);

        return ResponseEntity.ok(BaseRes.success(dailyTierOfMonths));
    }

}
