package com.pknu.caloriepay.domain.user.dto.response;

import com.pknu.caloriepay.domain.user.domain.MemberHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class WeightResponseDto {

    private Long memberId;

    private double weight;

    private LocalDate date;

    public static WeightResponseDto from(MemberHistory memberHistory){
        return WeightResponseDto.builder()
                .memberId(memberHistory.getMemberId())
                .date(memberHistory.getUpdateDate())
                .weight(memberHistory.getWeight())
                .build();
    }


}
