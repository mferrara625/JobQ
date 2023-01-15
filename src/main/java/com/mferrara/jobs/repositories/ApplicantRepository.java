package com.mferrara.jobs.repositories;

import com.mferrara.jobs.models.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    Applicant findApplicantByUser_Username(String username);
}
