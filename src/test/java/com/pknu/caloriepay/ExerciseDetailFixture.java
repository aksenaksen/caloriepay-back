package com.pknu.caloriepay;

import com.pknu.caloriepay.concept.ExerciseDetail;
import com.pknu.caloriepay.domain.exercise.domain.Duration;


public class ExerciseDetailFixture {

    public static ExerciseDetail createDummy() {
        return new ExerciseDetail(
            1L,
                "핼스",// exerciseTypeId,
            35.0,                     // exerciseName// duration// burnedCalorie
            new Duration(30)
        );
    }
}
