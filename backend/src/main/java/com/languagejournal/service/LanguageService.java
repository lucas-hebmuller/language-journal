package com.languagejournal.service;

import com.languagejournal.dto.LanguageRequest;
import com.languagejournal.dto.LanguageResponse;
import com.languagejournal.exception.ForbiddenException;
import com.languagejournal.exception.ResourceNotFoundException;
import com.languagejournal.model.Language;
import com.languagejournal.model.User;
import com.languagejournal.repository.LanguageRepository;
import com.languagejournal.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LanguageService {

    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;

    public LanguageService(UserRepository userRepository, LanguageRepository languageRepository) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
    }

    private LanguageResponse toResponse(Language language) {
        return new LanguageResponse(
                language.getId(),
                language.getName(),
                language.getColorHex(),
                language.getCreatedAt()
        );
    }

    public List<LanguageResponse> getLanguagesByUser(UUID userId) {
        return languageRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public LanguageResponse createLanguage(UUID userId, LanguageRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Language language = new Language(user, request.getName(), request.getColorHex());

        return toResponse(languageRepository.save(language));
    }

    @Transactional
    public LanguageResponse updateLanguage(UUID userId, Long languageId, LanguageRequest request) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found"));

        if (!language.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Unauthorized");
        }

        language.setName(request.getName());
        language.setColorHex(request.getColorHex());

        return toResponse(languageRepository.save(language));
    }

    @Transactional
    public void deleteLanguage(UUID userId, Long languageId) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found"));

        if (!language.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Unauthorized");
        }

        languageRepository.delete(language);
    }
}
