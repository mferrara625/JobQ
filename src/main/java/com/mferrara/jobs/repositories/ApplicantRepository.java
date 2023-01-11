package com.mferrara.jobs.repositories;

import com.mferrara.jobs.models.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
}
