package com.pknu.caloriepay.domain.exercise.dao;

import com.pknu.caloriepay.domain.exercise.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise,Long> {

    public List<Exercise> findAllByUserIdAndDate(Long userId, LocalDate date);
}
