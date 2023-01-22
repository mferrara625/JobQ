package com.mferrara.jobs.models;

import jakarta.persistence.*;

@Entity
public class Resume {

    @Id
    @GeneratedValue
    private Long id;
    private String resumeLink;
    @OneToOne(mappedBy = "resume")
    private Applicant applicant;

    public Resume() {
    }

    public Resume(Long id, String resumeLink, Applicant applicant) {
        this.id = id;
        this.resumeLink = resumeLink;
        this.applicant = applicant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResumeLink() {
        return resumeLink;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
