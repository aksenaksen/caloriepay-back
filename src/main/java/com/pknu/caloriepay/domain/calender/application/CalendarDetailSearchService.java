package com.pknu.caloriepay.domain.calender.application;

import com.pknu.caloriepay.domain.exercise.dao.ExerciseRecordRepository;
import com.pknu.caloriepay.domain.exercise.dao.ExerciseTypeRepository;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseRecord;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
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
    private final ExerciseTypeRepository exerciseTypeRepository;

    @Transactional(readOnly = true)
    public List<ResponseExerciseRecordDto> getExerciseRecordList(Long userId, LocalDate date) {
        // 1. 해당 날짜의 운동 기록을 조회
        List<ExerciseRecord> exerciseRecords = exerciseRecordRepository.findAllByUserIdAndDate(userId, date);

        return exerciseRecords.stream()
                .map(exerciseRecord -> {
                    ExerciseType type = exerciseTypeRepository.findById(exerciseRecord.getExerciseTypeId())
                            .orElseThrow(() -> new RuntimeException("Exercise type not found")); // 예외 처리

                    return ResponseExerciseRecordDto.fromEntity(exerciseRecord, type);
                })
                .toList();
    }
}
