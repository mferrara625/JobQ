package com.mferrara.jobs.repositories;

import com.mferrara.jobs.models.Content;
import com.mferrara.jobs.models.Employer;
import com.mferrara.jobs.models.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findAllJobPostsByEmployer_CompanyName(String companyName);
    List<JobPost> findAllJobPostsByEmployer_User_Id(Long userId);

    @Query("SELECT j FROM JobPost j WHERE j.content LIKE %?1%")
    List<JobPost> findByContentLike(String content);
}
