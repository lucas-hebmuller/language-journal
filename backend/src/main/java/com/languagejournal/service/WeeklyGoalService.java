package com.languagejournal.service;

import com.languagejournal.dto.WeeklyGoalRequest;
import com.languagejournal.dto.WeeklyGoalResponse;
import com.languagejournal.exception.ForbiddenException;
import com.languagejournal.exception.ResourceNotFoundException;
import com.languagejournal.model.Language;
import com.languagejournal.model.User;
import com.languagejournal.model.WeeklyGoal;
import com.languagejournal.repository.LanguageRepository;
import com.languagejournal.repository.UserRepository;
import com.languagejournal.repository.WeeklyGoalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return weeklyGoalRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public WeeklyGoalResponse upsertWeeklyGoal(UUID userId, WeeklyGoalRequest request) {
        Language language = languageRepository.findById(request.getLanguageId())
                .orElseThrow(() -> new ResourceNotFoundException("Language not found"));

        if (!language.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Unauthorized");
        }

        Optional<WeeklyGoal> existing = weeklyGoalRepository.findByUserIdAndLanguageId(userId, language.getId());

        WeeklyGoal weeklyGoal;

        if (existing.isPresent()) {
            weeklyGoal = existing.get();
            weeklyGoal.setTargetMinutes(request.getTargetMinutes());
        }
        else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            weeklyGoal = new WeeklyGoal(user, language, request.getTargetMinutes());
        }

        return toResponse(weeklyGoalRepository.save(weeklyGoal));
    }

    @Transactional
    public void deleteWeeklyGoal(UUID userId, Long weeklyGoalId) {
        WeeklyGoal weeklyGoal = weeklyGoalRepository.findById(weeklyGoalId)
                .orElseThrow(() -> new ResourceNotFoundException("Weekly goal not found"));

        if (!weeklyGoal.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Unauthorized");
        }

        weeklyGoalRepository.delete(weeklyGoal);
    }
}
