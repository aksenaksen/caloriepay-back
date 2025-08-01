package com.pknu.caloriepay.concept;

import java.time.LocalDate;

public record ExerciseInfo(
        long id,
        long userId,
        String title,
        LocalDate date
) {


}
