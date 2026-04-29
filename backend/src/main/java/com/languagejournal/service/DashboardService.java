package com.languagejournal.service;

import com.languagejournal.dto.DashboardSummaryResponse;
import com.languagejournal.dto.SkillBreakdownResponse;
import com.languagejournal.dto.TrendResponse;
import com.languagejournal.dto.WeeklyProgressResponse;
import com.languagejournal.exception.ResourceNotFoundException;
import com.languagejournal.repository.StudySessionRepository;
import com.languagejournal.repository.WeeklyGoalRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class DashboardService {

    private final StudySessionRepository studySessionRepository;
    private final WeeklyGoalRepository weeklyGoalRepository;

    public DashboardService(StudySessionRepository studySessionRepository,
                            WeeklyGoalRepository weeklyGoalRepository) {
        this.studySessionRepository = studySessionRepository;
        this.weeklyGoalRepository = weeklyGoalRepository;
    }

    private int calculateStreak(UUID userId) {
        List<LocalDate> sessionDates = studySessionRepository.findDistinctDatesByUserId(userId);
        int streak = 0;

        LocalDate expected = LocalDate.now();

        for (LocalDate date : sessionDates) {
            if (date.equals(expected)) {
                streak++;
                expected = expected.minusDays(1);
            }
            else {
                break;
            }
        }

        return streak;
    }

    public DashboardSummaryResponse getSummary(UUID userId) {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);
        LocalDate monthStart = today.withDayOfMonth(1);

        Integer totalMinutesThisWeek = studySessionRepository.sumMinutesBetween(userId, weekStart, today);
        Integer totalMinutesThisMonth = studySessionRepository.sumMinutesBetween(userId, monthStart, today);
        int streak = calculateStreak(userId);
        Long totalSessions = studySessionRepository.countByUserId(userId);

        return new DashboardSummaryResponse(
                totalMinutesThisWeek,
                totalMinutesThisMonth,
                streak,
                totalSessions
        );


    }

    public List<WeeklyProgressResponse> getWeeklyProgress(UUID userId) {

    }

    public List<TrendResponse> getTrend(UUID userId) {

    }

    public List<SkillBreakdownResponse> getSkillBreakdown(UUID userId) {

    }
}
