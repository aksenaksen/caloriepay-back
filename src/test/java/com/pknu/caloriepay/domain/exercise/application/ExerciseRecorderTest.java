package com.pknu.caloriepay.domain.exercise.application;

import com.pknu.caloriepay.ExerciseDetailFixture;
import com.pknu.caloriepay.concept.ExerciseDetail;
import com.pknu.caloriepay.domain.exercise.domain.Exercise;
import com.pknu.caloriepay.domain.exercise.infrastructor.ExerciseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExerciseRecorderTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseRecorder exerciseRecorder;

    @Test
    @DisplayName("성공적으로 운동 기록을 완료한 경우")
    public void successRecordExercise(){
        // given
        long userId = 1L;
        String title = "행복한 운동";
        ExerciseDetail exerciseDetail = ExerciseDetailFixture.createDummy();
        //when
        exerciseRecorder.record(userId,title,exerciseDetail);
        //then
        verify(exerciseRepository).save(any(Exercise.class));
    }

}