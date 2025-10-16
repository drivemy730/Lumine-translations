package com.lumine.repositories;
import com.lumine.helpers.LanguageFamily;
import com.lumine.helpers.LanguageIsoCode;
import com.lumine.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer>
{
    Optional<Language> findByIsoCode(LanguageIsoCode isoCode);

    List<Language> findByIsRareFalse();

    @Query("SELECT l FROM Language l WHERE l.family = :family")
    List<Language> findByLanguageFamily(@Param("family") LanguageFamily family);

}
