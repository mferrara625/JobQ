package com.mferrara.jobs.repositories;

import com.mferrara.jobs.models.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
}
