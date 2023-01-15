package com.mferrara.jobs.repositories;

import com.mferrara.jobs.auth.User;
import com.mferrara.jobs.models.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

    Employer findEmployerByCompanyName(String companyName);

    Employer findEmployerByUser(User user);
}
