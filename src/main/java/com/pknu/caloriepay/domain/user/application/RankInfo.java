package com.pknu.caloriepay.domain.user.application;

public record RankInfo(
        long userId,
        int rank,
        Integer score
) {
}
