package com.lumine.repositories;

import com.lumine.helpers.ClientFileTypeCategory;
import com.lumine.models.UploadedFileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UploadedFileTypeRepository extends JpaRepository<UploadedFileType,Integer>
{
    Optional<UploadedFileType> findByName(ClientFileTypeCategory name);

    @Query("SELECT ft FROM UploadedFileType ft WHERE ft.difficultyLevel >= :minDifficulty")
    List<UploadedFileType> findByMinimumDifficulty(@Param("minDifficulty") int minDifficulty);
}
