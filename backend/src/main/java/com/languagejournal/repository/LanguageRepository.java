package com.languagejournal.repository;

import com.languagejournal.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    List<Language> findByUserId(UUID userId);
}
