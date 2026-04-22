package com.languagejournal.repository;

import com.languagejournal.model.WeeklyGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeeklyGoalRepository extends JpaRepository<WeeklyGoal, Long> {

    List<WeeklyGoal> findByUserId(UUID userId);

    Optional<WeeklyGoal> findByUserIdAndLanguageId(UUID userId, Long languageId);
}
