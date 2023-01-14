package com.mferrara.jobs.controllers;

import com.mferrara.jobs.models.ApplicantPost;
import com.mferrara.jobs.repositories.ApplicantPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
@RequestMapping("/ApplicantPost")
public class ApplicantPostController {

    @Autowired
    private ApplicantPostRepository repository;


    @PostMapping("/")
    public ResponseEntity<ApplicantPost> createApplicantPost(@RequestBody ApplicantPost newApplicantPost) {
        return new ResponseEntity<>(repository.save(newApplicantPost), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ApplicantPost>> getAllApplicantApplicants(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicantPost> updateApplicantPost(@PathVariable Long id, @RequestBody ApplicantPost update){
        ApplicantPost current = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(update.getId() != null){
            current.setId(update.getId());
        }

        if(update.getTitle() != null){
            current.setTitle(update.getTitle());
        }

        if(update.getContent() != null){
            current.setContent(update.getContent());
        }

        if(update.getApplicants() != null){
            current.setApplicants(update.getApplicants());
        }

        if(update.getAuthor() != null){
            current.setAuthor(update.getAuthor());
        }

        if(update.getNumberOfLikes() != null){
            current.setNumberOfLikes(update.getNumberOfLikes());
        }

        return new ResponseEntity<>(repository.save(current), HttpStatus.ACCEPTED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicantPost(@PathVariable Long id){
        repository.deleteById(id);
        return new ResponseEntity<>("ApplicantPost Deleted", HttpStatus.GONE);
    }
}
