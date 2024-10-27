package com.pknu.caloriepay.domain.exercise.dao;

import com.pknu.caloriepay.domain.exercise.domain.ExerciseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord ,Long> {

    public List<ExerciseRecord> findAllByUserIdAndDate(Long userId, LocalDate date);
}
