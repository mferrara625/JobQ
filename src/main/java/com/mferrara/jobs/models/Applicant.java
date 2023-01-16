package com.mferrara.jobs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mferrara.jobs.auth.User;
import jakarta.persistence.*;

import java.util.Set;

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
    @JsonIgnore
    private Set<JobPost> jobsList;
    @ManyToMany
    @JoinTable(
            name = "applicant_likedPosts",
            joinColumns = @JoinColumn(name = "applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "applicantPost_id"))
    private Set<ApplicantPost> likedPosts;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<ApplicantPost> posts;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "applicant_user",
            joinColumns =
                    { @JoinColumn(name = "applicant_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "user_id", referencedColumnName = "id") })
    private User user;

    public Applicant() {
    }

    public Applicant(String name) {
        this.name = name;
    }

    public Applicant(Long id, String name, Resume resume, Set<JobPost> jobsList, Set<ApplicantPost> likedPosts, Set<ApplicantPost> posts) {
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

    public Set<JobPost> getJobsList() {
        return jobsList;
    }

    public void setJobsList(Set<JobPost> jobsList) {
        this.jobsList = jobsList;
    }

    public Set<ApplicantPost> getPosts() {
        return posts;
    }

    public void setPosts(Set<ApplicantPost> posts) {
        this.posts = posts;
    }

    public Set<ApplicantPost> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<ApplicantPost> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
