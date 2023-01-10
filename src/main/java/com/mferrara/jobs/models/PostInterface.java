package com.mferrara.jobs.models;

import com.mferrara.jobs.auth.User;

import java.util.List;

public interface PostInterface {

    String title = null;
    String content = null;
    List<Applicant> applicants = null;

    void addApplicantToList(Applicant applicant);

}
