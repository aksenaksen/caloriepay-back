package com.pknu.caloriepay.domain.calender.application;

import com.pknu.caloriepay.domain.exercise.dao.ExerciseRecordRepository;
import com.pknu.caloriepay.domain.exercise.dto.ResponseExerciseRecordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CalendarDetailSearchService {

    private final ExerciseRecordRepository exerciseRecordRepository;

    @Transactional(readOnly = true)
    public List<ResponseExerciseRecordDto> getExerciseRecordList(Long userId, LocalDate date){

        return exerciseRecordRepository.findAllByUserIdAndDate(userId, date)
                .stream()
                .map(ResponseExerciseRecordDto::fromEntity)
                .toList();
    }

}
