package com.pknu.caloriepay.domain.exercise.application;

import com.pknu.caloriepay.concept.ExerciseDetail;
import com.pknu.caloriepay.domain.exercise.application.command.ExerciseRecordCommand;
import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

    private final List<ExerciseRecordCommand> commands = new ArrayList<>();

    @BeforeEach
    public void init() {
        ExerciseRecordCommand cmd1 = new ExerciseRecordCommand("운동 제목1", 1L, "걷기", 30);
        ExerciseRecordCommand cmd2 = new ExerciseRecordCommand("운동 제목2", 2L, "달리기", 15);

        commands.clear();
        commands.add(cmd1);
        commands.add(cmd2);
    }

    @Mock
    private ExerciseRecorder exerciseRecorder;

    @Mock
    private ExerciseTypeFinder exerciseTypeFinder;

    @InjectMocks
    private ExerciseService exerciseService;

    @Test
    void recordExercise() {
        // given
        ExerciseType exerciseType1 = mock(ExerciseType.class);
        when(exerciseType1.getId()).thenReturn(101L);
        when(exerciseType1.calculateCalories(30)).thenReturn(250.0);

        ExerciseType exerciseType2 = mock(ExerciseType.class);
        when(exerciseType2.getId()).thenReturn(102L);
        when(exerciseType2.calculateCalories(15)).thenReturn(100.0);

        when(exerciseTypeFinder.find("걷기")).thenReturn(exerciseType1);
        when(exerciseTypeFinder.find("달리기")).thenReturn(exerciseType2);

        ArgumentCaptor<ExerciseDetail> captor = ArgumentCaptor.forClass(ExerciseDetail.class);

        // when
        exerciseService.record(commands);

        // then
        // exerciseRecorder.record가 두 번 호출됐는지 확인
        verify(exerciseRecorder, times(2)).record(anyLong(), anyString(), captor.capture());

        List<ExerciseDetail> capturedDetails = captor.getAllValues();

        // 첫 번째 호출 검증
        ExerciseDetail detail1 = capturedDetails.get(0);
        Assertions.assertThat(detail1.exerciseTypeId()).isEqualTo(101L);
        Assertions.assertThat(detail1.burnedCalorie()).isEqualTo(250);
        Assertions.assertThat(detail1.duration()).isEqualTo(commands.get(0).toDuration());

        // 두 번째 호출 검증
        ExerciseDetail detail2 = capturedDetails.get(1);
        Assertions.assertThat(detail2.exerciseTypeId()).isEqualTo(102L);
        Assertions.assertThat(detail2.burnedCalorie()).isEqualTo(100);
        Assertions.assertThat(detail2.duration()).isEqualTo(commands.get(1).toDuration());
    }
}
