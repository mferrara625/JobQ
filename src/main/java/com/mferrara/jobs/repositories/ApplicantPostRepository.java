package com.mferrara.jobs.repositories;

import com.mferrara.jobs.models.ApplicantPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantPostRepository extends JpaRepository<ApplicantPost, Long> {
}
