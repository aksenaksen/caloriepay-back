package com.pknu.caloriepay.domain.tier.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.tier.application.DailyTierFinder;
import com.pknu.caloriepay.domain.tier.application.MonthlyTierFinder;
import com.pknu.caloriepay.domain.tier.application.TierService;
import com.pknu.caloriepay.domain.tier.application.out.MonthTierResponse;
import com.pknu.caloriepay.domain.tier.application.out.TierSummaryResponse;
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

    private final TierService tierService;

    @GetMapping("/month")
    public ResponseEntity<BaseRes<MonthTierResponse>> getMonthTier(@AuthenticationPrincipal CurrentMemberInfo currentMemberInfo, @RequestParam("year") Long year, @RequestParam Long month){

        LocalDate date = LocalDate.of(year.intValue(), month.intValue(), 1);
        LocalDate lastDayOfMonth = date.withDayOfMonth(LocalDate.of(year.intValue(), month.intValue(), 1).lengthOfMonth());

        MonthTierResponse tier = tierService.findMonthTier(currentMemberInfo.memberId(),lastDayOfMonth);

        return ResponseEntity.ok(BaseRes.success(tier));
    }
    @GetMapping("/month/amount")
    public ResponseEntity<BaseRes<List<TierSummaryResponse>>> getMonthTierAmount(@AuthenticationPrincipal CurrentMemberInfo currentMemberInfo, @RequestParam("date") LocalDate date){
        List<TierSummaryResponse> dailyTierOfMonths= tierService.findSummary(currentMemberInfo.memberId(),date);

        return ResponseEntity.ok(BaseRes.success(dailyTierOfMonths));
    }

}
