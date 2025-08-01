package com.pknu.caloriepay.domain.exercise.infrastructor;

import com.pknu.caloriepay.domain.exercise.domain.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, Long> {
//    List<ExerciseType> findAllByUserIdAndIdIn(Long userId, List<Long> exerciseIds);
    Optional<ExerciseType> findByName(String name);

}
