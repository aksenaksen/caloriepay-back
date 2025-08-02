package com.pknu.caloriepay.domain.exercise.infrastructor;

import com.pknu.caloriepay.domain.exercise.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise,Long> {

    List<Exercise> findAllByUserIdAndDate(Long userId, LocalDate date);
}
