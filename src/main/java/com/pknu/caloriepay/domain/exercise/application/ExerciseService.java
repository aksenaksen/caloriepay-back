package com.pknu.caloriepay.domain.exercise.application;

import com.pknu.caloriepay.concept.ExerciseDetail;
import com.pknu.caloriepay.domain.calender.application.out.CalendarExerciseResponse;
import com.pknu.caloriepay.domain.exercise.application.out.ExerciseResponse;
import com.pknu.caloriepay.domain.exercise.domain.Duration;
import com.pknu.caloriepay.domain.exercise.domain.Exercise;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import com.pknu.caloriepay.domain.exercise.application.command.ExerciseRecordCommand;
import com.pknu.caloriepay.domain.exercise.api.out.ExerciseTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRecorder exerciseRecorder;
    private final ExerciseTypeFinder exerciseTypeFinder;
    private final ExerciseFinder exerciseFinder;

    public void record(List<ExerciseRecordCommand> commands) {

        commands.forEach(exerciseRecordCommand -> {

            ExerciseType exerciseType = exerciseTypeFinder.find(exerciseRecordCommand.exerciseName());
            ExerciseDetail exerciseDetail = new ExerciseDetail(exerciseType.getId(),
                            exerciseType.getName(),
                            exerciseType.calculateCalories(exerciseRecordCommand.duration()),
                            exerciseRecordCommand.toDuration()
                            );

            exerciseRecorder.record(exerciseRecordCommand.userId(), exerciseRecordCommand.title(), exerciseDetail);
         }
        );
    }

    public List<ExerciseResponse> findExerciseByDate(Long userId, LocalDate date){
        List<Exercise> exercise = exerciseFinder.findByDate(userId, date);
        return exercise.stream()
                .map(exerciseRecord -> {
                    ExerciseType type = exerciseTypeFinder.find(exerciseRecord.getId());
                    return ExerciseResponse.from(exerciseRecord, type);
                })
                .toList();
    }


    @Transactional(readOnly = true)
    public List<ExerciseTypeResponse> findTypes(){

        return exerciseTypeFinder.findAll().stream()
                .map(ExerciseTypeResponse::fromEntity)
                .collect(Collectors.toList());
    }



}
