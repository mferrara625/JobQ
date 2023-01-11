package com.mferrara.jobs.repositories;

import com.mferrara.jobs.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
