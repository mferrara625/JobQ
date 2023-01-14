package com.mferrara.jobs.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.mferrara.jobs.auth.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ApplicantPost implements PostInterface {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    @ManyToMany(mappedBy = "likedPosts")

    private List<Applicant> applicants;  // list of applicants that have liked this post
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonIncludeProperties("id")
    private Applicant author;
    private Integer numberOfLikes;

    public ApplicantPost() {
    }

    public ApplicantPost(Long id, String title, String content, Applicant author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
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

    public List<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Applicant> applicants) {
        this.applicants = applicants;
    }

    public Applicant getAuthor() {
        return author;
    }

    public void setAuthor(Applicant author) {
        this.author = author;
    }

    public Integer getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    @Override
    public void addApplicantToList(Applicant applicant){
        applicants.add(applicant);
        numberOfLikes++;
    }
}
