package com.michaelwayne.AntiBias.repositories;

import com.michaelwayne.AntiBias.entities.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

}
