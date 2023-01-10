package com.mferrara.jobs.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.io.File;

@Entity
public class Resume {

    @Id
    @GeneratedValue
    private Long id;
    private File resume;
    @OneToOne(mappedBy = "resume")
    private Applicant applicant;

    public Resume() {
    }

    public Resume(Long id, File resume, Applicant applicant) {
        this.id = id;
        this.resume = resume;
        this.applicant = applicant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getResume() {
        return resume;
    }

    public void setResume(File resume) {
        this.resume = resume;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
