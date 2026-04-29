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
        AND (CAST(:from AS date) IS NULL OR s.date >= :from)
        AND (CAST(:to AS date) IS NULL OR s.date <= :to)
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

    // SUM returns null if there are no sessions, COALESCE replaces with a 0
    @Query("""
        SELECT COALESCE(SUM(s.durationMinutes), 0)
        FROM StudySession s
        WHERE s.user.id = :userId
        AND s.date BETWEEN :from AND :to
    """)
    Integer sumMinutesBetween(
            @Param("userId") UUID userId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    @Query("""
        SELECT s.language.id, COALESCE(SUM(s.durationMinutes), 0)
        FROM StudySession s
        WHERE s.user.id = :userId
        AND s.date BETWEEN :from AND :to
        GROUP BY s.language.id
    """)
    List<Object[]> sumMinutesPerLanguage(
            @Param("userId") UUID userId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    @Query("""
        SELECT s.date, COALESCE(SUM(s.durationMinutes), 0)
        FROM StudySession s
        WHERE s.user.id = :userId
        AND s.date >= :since
        GROUP BY s.date
        ORDER BY s.date ASC
    """)
    List<Object[]> sumMinutesPerDay(
            @Param("userId") UUID userId,
            @Param("since") LocalDate since
    );

    @Query("""
        SELECT s.skill.name, COALESCE(SUM(s.durationMinutes), 0)
        FROM StudySession s
        WHERE s.user.id = :userId
        GROUP BY s.skill.name
        ORDER BY SUM(s.durationMinutes) DESC
    """)
    List<Object[]> sumMinutesPerSkill(@Param("userId") UUID userId);

    @Query("""
        SELECT DISTINCT s.date FROM StudySession s
        WHERE s.user.id = :userId
        ORDER BY s.date DESC
    """)
    List<LocalDate> findDistinctDatesByUserId(@Param("userId") UUID userId);

    long countByUserId(UUID userId);
}
