package com.pknu.caloriepay.domain.exercise.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
public record Duration(int minutes) {

    public Duration {
        if (minutes < 0) {
            throw new IllegalArgumentException("운동시간은 0보다 커야합니다.");
        }
    }

    // JPA를 위한 기본 생성자
    protected Duration() {
        this(0);
    }
}