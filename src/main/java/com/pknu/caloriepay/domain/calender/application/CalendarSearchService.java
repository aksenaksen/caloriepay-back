package com.pknu.caloriepay.domain.calender.application;

import com.pknu.caloriepay.domain.calender.dto.out.ResponseCalendarDto;
import com.pknu.caloriepay.domain.tier.dao.DailyTierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarSearchService {

    private final DailyTierRepository dailyTierRepository;

    public List<ResponseCalendarDto> getCalendarByUserIdAndDate(Long userId,LocalDate start, LocalDate end){
        return dailyTierRepository.findByUserIdAndDateBetween(userId,start,end).stream()
                .map(ResponseCalendarDto::fromEntity)
                .toList();
    }
}
