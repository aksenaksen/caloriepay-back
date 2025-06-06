package com.pknu.caloriepay.domain.exercise.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Duration {

    private int minutes;

    public Duration(int minutes) {
        if(minutes < 0){
            throw new IllegalArgumentException("운동시간은 0보다 커야합니다.");
        }
        this.minutes = minutes;
    }


}
