package com.pknu.caloriepay.domain.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Builder
@Getter
@ToString
@AllArgsConstructor
public class Profile {
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;
    private double height;
    private double weight;
    @Enumerated(EnumType.STRING)
    private Goal goal;
    private double targetWeight;
    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;

    protected Profile() {}

}
