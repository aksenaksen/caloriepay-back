package com.pknu.caloriepay.domain.user.dto;

import com.pknu.caloriepay.domain.user.domain.ActivityLevel;
import com.pknu.caloriepay.domain.user.domain.Gender;
import com.pknu.caloriepay.domain.user.domain.Goal;

public record ProfileDto(
        Gender gender,
        int age,
        double height,
        double weight,
        Goal goal,
        double targetWeight,
        ActivityLevel activityLevel
) {
}
