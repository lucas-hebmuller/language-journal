package com.languagejournal.service;

import com.languagejournal.dto.DashboardSummaryResponse;
import com.languagejournal.dto.SkillBreakdownResponse;
import com.languagejournal.dto.TrendResponse;
import com.languagejournal.dto.WeeklyProgressResponse;
import com.languagejournal.repository.StudySessionRepository;
import com.languagejournal.repository.WeeklyGoalRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // goal vs actual minutes this week per language
    public List<WeeklyProgressResponse> getWeeklyProgress(UUID userId) {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);

        // Use a map for easy lookup and because not every language with a goal may have a session
        List<Object[]> rows = studySessionRepository.sumMinutesPerLanguage(userId, weekStart, today);
        Map<Long, Integer> actualByLanguage = new HashMap<>();
        for (Object[] row : rows) {
            actualByLanguage.put((Long) row[0], ((Number) row[1]).intValue());
        }

        // Look through goals and default to 0 if there are no study sessions for a language
        return weeklyGoalRepository.findByUserId(userId).stream()
                .map(goal -> new WeeklyProgressResponse(
                        goal.getLanguage().getId(),
                        goal.getLanguage().getName(),
                        goal.getTargetMinutes(),
                        actualByLanguage.getOrDefault(goal.getLanguage().getId(), 0)
                ))
                .toList();
    }

    // daily minutes for the last 8 weeks
    public List<TrendResponse> getTrend(UUID userId) {
        LocalDate since = LocalDate.now().minusWeeks(8);

        List<Object[]> rows = studySessionRepository.sumMinutesPerDay(userId, since);

        return rows.stream()
                .map(row -> new TrendResponse(
                        (LocalDate) row[0],
                        ((Number) row[1]).intValue()
                ))
                .toList();
    }

    // total minutes per skill
    public List<SkillBreakdownResponse> getSkillBreakdown(UUID userId) {
        List<Object[]> rows = studySessionRepository.sumMinutesPerSkill(userId);

        return rows.stream()
                .map(row -> new SkillBreakdownResponse(
                        (String) row[0],
                        ((Number) row[1]).intValue()
                ))
                .toList();
    }
}
