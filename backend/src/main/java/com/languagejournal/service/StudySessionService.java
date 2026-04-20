package com.languagejournal.service;

import com.languagejournal.dto.StudySessionRequest;
import com.languagejournal.dto.StudySessionResponse;
import com.languagejournal.exception.ForbiddenException;
import com.languagejournal.exception.ResourceNotFoundException;
import com.languagejournal.model.Language;
import com.languagejournal.model.Skill;
import com.languagejournal.model.StudySession;
import com.languagejournal.model.User;
import com.languagejournal.repository.LanguageRepository;
import com.languagejournal.repository.SkillRepository;
import com.languagejournal.repository.StudySessionRepository;
import com.languagejournal.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class StudySessionService {

    private final StudySessionRepository studySessionRepository;
    private final LanguageRepository languageRepository;
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    public StudySessionService(StudySessionRepository studySessionRepository,
                               LanguageRepository languageRepository,
                               SkillRepository skillRepository,
                               UserRepository userRepository) {
        this.studySessionRepository = studySessionRepository;
        this.languageRepository = languageRepository;
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
    }

    private StudySessionResponse toResponse(StudySession studySession) {
        return new StudySessionResponse(
                studySession.getId(),
                studySession.getLanguage().getId(),
                studySession.getLanguage().getName(),
                studySession.getSkill().getId(),
                studySession.getSkill().getName(),
                studySession.getDate(),
                studySession.getDurationMinutes(),
                studySession.getNotes(),
                studySession.getCreatedAt()
        );
    }

    public Page<StudySessionResponse> getSessionsByUser(
            UUID userId,
            Long languageId,
            Long skillId,
            LocalDate from,
            LocalDate to,
            Pageable pageable) {

        return studySessionRepository.findFiltered(userId, languageId, skillId, from, to, pageable)
                .map(this::toResponse);
    }

    @Transactional
    public StudySessionResponse createSession(UUID userId, StudySessionRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Language language = languageRepository.findById(request.getLanguageId())
                .orElseThrow(() -> new ResourceNotFoundException("Language not found"));

        if (!language.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Unauthorized");
        }

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

        StudySession studySession = new StudySession(
                user,
                language,
                skill,
                request.getDate(),
                request.getDurationMinutes(),
                request.getNotes()
                );

        return toResponse(studySessionRepository.save(studySession));
    }

    @Transactional
    public StudySessionResponse updateSession(UUID userId, Long sessionId, StudySessionRequest request) {
        StudySession studySession = studySessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        if (!studySession.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Unauthorized");
        }

        Language language = languageRepository.findById(request.getLanguageId())
                .orElseThrow(() -> new ResourceNotFoundException("Language not found"));

        if (!language.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Unauthorized");
        }

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

        studySession.setLanguage(language);
        studySession.setSkill(skill);
        studySession.setDate(request.getDate());
        studySession.setDurationMinutes(request.getDurationMinutes());
        studySession.setNotes(request.getNotes());

        return toResponse(studySessionRepository.save(studySession));
    }

    @Transactional
    public void deleteSession(UUID userId, Long sessionId) {
        StudySession studySession = studySessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        if (!studySession.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Unauthorized");
        }

        studySessionRepository.delete(studySession);
    }
}
