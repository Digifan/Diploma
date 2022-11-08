package edu.alevel.danylenko.diploma.repository;

import edu.alevel.danylenko.diploma.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}