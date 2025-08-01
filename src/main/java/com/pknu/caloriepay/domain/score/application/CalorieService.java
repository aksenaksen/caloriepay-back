package com.pknu.caloriepay.domain.score.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalorieService {

    private final CalorieScoreFinder calorieScoreFinder;


}
