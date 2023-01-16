package com.mferrara.jobs.models;

import java.util.Set;

public interface PostInterface {

    String title = null;
    String content = null;
    Set<Applicant> applicants = null;

    void addApplicantToList(Applicant applicant);

}
