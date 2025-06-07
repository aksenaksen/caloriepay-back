package com.pknu.caloriepay.domain.calender.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.calender.application.CalendarService;
import com.pknu.caloriepay.domain.calender.dto.out.CalendarResponse;
import com.pknu.caloriepay.domain.calender.dto.out.CalendarDetailResponse;
import com.pknu.caloriepay.domain.calender.exception.StartIsAfterEndDateException;
import com.pknu.caloriepay.global.dto.BaseRes;
import com.pknu.caloriepay.global.enums.ResCode;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendar")
@Validated
public class CalendarApi {

    private final CalendarService calendarService;

    @GetMapping("")
    public ResponseEntity<BaseRes<List<CalendarResponse>>> getCalendarByMemberId(
            @AuthenticationPrincipal CurrentMemberInfo memberInfo,
            @RequestParam ("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end)
    {
        if (start.isAfter(end)){
            throw new StartIsAfterEndDateException(ResCode.START_IS_AFTER_END_DATE);
        }

        List<CalendarResponse> calendarDtoList = calendarService.getCalendarByUserIdAndDate(memberInfo.memberId(), start,end);

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
