package com.languagejournal.service;

import com.languagejournal.dto.LanguageRequest;
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

    public List<Language> getLanguagesByUser(UUID userId) {
        return languageRepository.findByUserId(userId);
    }

    @Transactional
    public Language createLanguage(UUID userId, LanguageRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Language language = new Language(user, request.getName(), request.getColorHex());

        return languageRepository.save(language);
    }

    @Transactional
    public Language updateLanguage(UUID userId, Long languageId, LanguageRequest request) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new RuntimeException("Language not found"));

        if (!language.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        language.setName(request.getName());
        language.setColorHex(request.getColorHex());

        return languageRepository.save(language);
    }

    @Transactional
    public void deleteLanguage(UUID userId, Long languageId) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new RuntimeException("Language not found"));

        if (!language.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        languageRepository.delete(language);
    }
}
