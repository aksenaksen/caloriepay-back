package com.pknu.caloriepay.domain.exercise.application;

import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import com.pknu.caloriepay.domain.exercise.dto.in.ExerciseRecordCommand;
import com.pknu.caloriepay.domain.exercise.dto.out.ExerciseTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRecorder exerciseRecorder;
    private final ExerciseTypeFinder exerciseTypeFinder;

    public void record(Long userId, String title, List<ExerciseRecordCommand> exerciseRecordCommands){
        exerciseRecordCommands.forEach(exerciseRecordCommand -> {
            ExerciseType exerciseType = exerciseTypeFinder.find(exerciseRecordCommand.exerciseName());
            exerciseRecorder.record(userId,title,exerciseType, exerciseRecordCommand.toDuration());
        }
        );
    }

    public List<ExerciseTypeResponse> findTypes(){
        return exerciseTypeFinder.findAll().stream()
                .map(ExerciseTypeResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
