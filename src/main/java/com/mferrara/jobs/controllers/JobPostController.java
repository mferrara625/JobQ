package com.mferrara.jobs.controllers;

import com.mferrara.jobs.models.JobPost;
import com.mferrara.jobs.repositories.JobPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
@RequestMapping("/JobPost")
public class JobPostController {

    @Autowired
    private JobPostRepository repository;


    @PostMapping("/")
    public ResponseEntity<JobPost> createJobPost(@RequestBody JobPost newJobPost) {
        return new ResponseEntity<>(repository.save(newJobPost), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<JobPost>> getAllJobPosts(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobPost> updateJobPost(@PathVariable Long id, @RequestBody JobPost update){
        JobPost current = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

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

        if(update.getEmployer() != null){
            current.setEmployer(update.getEmployer());
        }

        return new ResponseEntity<>(repository.save(current), HttpStatus.ACCEPTED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobPost(@PathVariable Long id){
        repository.deleteById(id);
        return new ResponseEntity<>("Job Post Deleted", HttpStatus.GONE);
    }
}
