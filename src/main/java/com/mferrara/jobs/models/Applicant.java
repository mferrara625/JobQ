package com.mferrara.jobs.models;

import jakarta.persistence.*;

import java.io.File;
import java.util.List;

@Entity
public class Applicant {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "applicant_resume",
            joinColumns =
                    { @JoinColumn(name = "applicant_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "resume_id", referencedColumnName = "id") })
    private Resume resume;
    @ManyToMany
    @JoinTable(
            name = "applicant_jobs",
            joinColumns = @JoinColumn(name = "applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "jobPost_id"))
    private List<JobPost> jobsList;
    @ManyToMany
    @JoinTable(
            name = "applicant_likedPosts",
            joinColumns = @JoinColumn(name = "applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "applicantPost_id"))
    private List<ApplicantPost> likedPosts;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<ApplicantPost> posts;

    public Applicant() {
    }

    public Applicant(String name) {
        this.name = name;
    }

    public Applicant(Long id, String name, Resume resume, List<JobPost> jobsList, List<ApplicantPost> likedPosts, List<ApplicantPost> posts) {
        this.id = id;
        this.name = name;
        this.resume = resume;
        this.jobsList = jobsList;
        this.likedPosts = likedPosts;
        this.posts = posts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public List<JobPost> getJobsList() {
        return jobsList;
    }

    public void setJobsList(List<JobPost> jobsList) {
        this.jobsList = jobsList;
    }

    public List<ApplicantPost> getPosts() {
        return posts;
    }

    public void setPosts(List<ApplicantPost> posts) {
        this.posts = posts;
    }
}
