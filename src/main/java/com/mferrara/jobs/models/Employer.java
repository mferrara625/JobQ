package com.mferrara.jobs.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Employer {

    @Id
    @GeneratedValue
    private Long id;
    private String companyName;
    @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
    private List<JobPost> jobListings;

    public Employer() {
    }

    public Employer(Long id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<JobPost> getJobListings() {
        return jobListings;
    }

    public void setJobListings(List<JobPost> jobListings) {
        this.jobListings = jobListings;
    }

    public void addJobListing(JobPost job){
        jobListings.add(job);
    }
}
