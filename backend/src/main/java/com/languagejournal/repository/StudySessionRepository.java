package com.languagejournal.repository;

import com.languagejournal.model.StudySession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
    List<StudySession> findByUserId(UUID userId);

    @Query("""
    SELECT s FROM StudySession s
    WHERE s.user.id = :userId
    AND (:languageId IS NULL OR s.language.id = :languageId)
    AND (:skillId IS NULL OR s.skill.id = :skillId)
    AND (:from IS NULL OR s.date >= :from)
    AND (:to IS NULL OR s.date <= :to)
    ORDER BY s.date DESC
""")
    Page<StudySession> findFiltered(
            @Param("userId") UUID userId,
            @Param("languageId") Long languageId,
            @Param("skillId") Long skillId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable
    );
}
