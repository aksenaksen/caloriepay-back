package com.pknu.caloriepay.domain.tier.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private Tier tier;

    private LocalDate date;


    public static MonthlyTier of(Long userId, Tier tier, LocalDate date) {
        return MonthlyTier.builder()
                .userId(userId)
                .tier(tier)
                .date(date)
                .build();
    }
}
