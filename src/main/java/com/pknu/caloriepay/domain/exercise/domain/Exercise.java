package com.pknu.caloriepay.domain.exercise.domain;

import com.pknu.caloriepay.global.event.Events;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    private Long exerciseTypeId;

    @Embedded
    @AttributeOverride(name = "minutes", column = @Column(name = "duration_minutes"))
    private Duration duration;

    private double caloriesBurned;

    private LocalDate date;

    public static Exercise of(Long userId,ExerciseType exerciseType, String title, Duration duration) {
        return Exercise.builder()
                .exerciseTypeId(exerciseType.getId())
                .title(title)
                .duration(duration)
                .userId(userId)
                .caloriesBurned(exerciseType.calculateCalories(duration.getMinutes()))
                .date(LocalDate.now())
                .build();
    }

    public void recordExercise(){
        Events.publish(new ExerciseRecordEvent(userId, caloriesBurned));
    }

}
