package com.mferrara.jobs.models;

import com.mferrara.jobs.auth.User;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Employer {

    @Id
    @GeneratedValue
    private Long id;
    private String companyName;
    @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
    private Set<JobPost> jobListings;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "employer_user",
            joinColumns =
                    { @JoinColumn(name = "employer_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "user_id", referencedColumnName = "id") })
    private User user;

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

    public Set<JobPost> getJobListings() {
        return jobListings;
    }

    public void setJobListings(Set<JobPost> jobListings) {
        this.jobListings = jobListings;
    }

    public void addJobListing(JobPost job){
        jobListings.add(job);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
