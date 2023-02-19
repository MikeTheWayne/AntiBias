package com.michaelwayne.AntiBias.repositories;

import com.michaelwayne.AntiBias.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultsRepository extends JpaRepository<Result, Long> {

}
