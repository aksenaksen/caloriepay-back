package com.pknu.caloriepay.domain.calender.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.calender.application.CalendarService;
import com.pknu.caloriepay.domain.calender.application.out.CalendarDetailResponse;
import com.pknu.caloriepay.domain.calender.exception.StartIsAfterEndDateException;
import com.pknu.caloriepay.domain.tier.application.out.DailyTierResponse;
import com.pknu.caloriepay.global.dto.BaseRes;
import com.pknu.caloriepay.global.enums.ResCode;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
@Validated
public class CalendarApi {

    private final CalendarService calendarService;

    @GetMapping("")
    public ResponseEntity<BaseRes<List<DailyTierResponse>>> getCalendarByMemberId(
            @AuthenticationPrincipal CurrentMemberInfo memberInfo,
            @RequestParam ("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end)
    {

        if (start.isAfter(end)){
            throw new StartIsAfterEndDateException(ResCode.START_IS_AFTER_END_DATE);
        }

        List<DailyTierResponse> calendarDtoList = calendarService.getCalendarByUserIdAndDate(memberInfo.memberId(), start,end);

        return ResponseEntity.ok(BaseRes.success(calendarDtoList));
    }

    @GetMapping("/detail")
    public ResponseEntity<BaseRes<CalendarDetailResponse>> getCalenderDetailByMemberId(
            @AuthenticationPrincipal CurrentMemberInfo memberInfo,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){

        CalendarDetailResponse response = calendarService.findCalendarDetail(memberInfo.memberId(), date);

        return ResponseEntity.ok(BaseRes.success(response));
    }
}
