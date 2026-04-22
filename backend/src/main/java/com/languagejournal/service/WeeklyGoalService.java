package com.languagejournal.service;

import com.languagejournal.dto.WeeklyGoalResponse;
import com.languagejournal.exception.ResourceNotFoundException;
import com.languagejournal.model.User;
import com.languagejournal.model.WeeklyGoal;
import com.languagejournal.repository.LanguageRepository;
import com.languagejournal.repository.UserRepository;
import com.languagejournal.repository.WeeklyGoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WeeklyGoalService {

    private final WeeklyGoalRepository weeklyGoalRepository;
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;

    public WeeklyGoalService(WeeklyGoalRepository weeklyGoalRepository,
                             UserRepository userRepository, LanguageRepository languageRepository) {
        this.weeklyGoalRepository = weeklyGoalRepository;
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
    }

    private WeeklyGoalResponse toResponse(WeeklyGoal weeklyGoal) {
        return new WeeklyGoalResponse(
                weeklyGoal.getId(),
                weeklyGoal.getLanguage().getId(),
                weeklyGoal.getLanguage().getName(),
                weeklyGoal.getTargetMinutes(),
                weeklyGoal.getCreatedAt()
        );
    }

    public List<WeeklyGoalResponse> getWeeklyGoalsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return weeklyGoalRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
