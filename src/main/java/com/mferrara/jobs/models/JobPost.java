package com.mferrara.jobs.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class JobPost implements PostInterface {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    @ManyToMany(mappedBy = "jobsList")
    private Set<Applicant> applicants;  // list of Applicants that have applied to this job
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id", referencedColumnName = "id")
    @JsonIncludeProperties("id")
    private Employer employer;

    public JobPost() {
    }

    public JobPost(Long id, String title, String content, Employer employer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.employer = employer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(Set<Applicant> applicants) {
        this.applicants = applicants;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    @Override
    public void addApplicantToList(Applicant applicant) {
//      TODO:  if(matchesCriteria) create method to pre-screen employees based on resume
        applicants.add(applicant);
    }
}
