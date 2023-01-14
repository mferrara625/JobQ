package com.mferrara.jobs.controllers;

import com.mferrara.jobs.models.Applicant;
import com.mferrara.jobs.repositories.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
@RequestMapping("/applicant")
public class ApplicantController {

    @Autowired
    private ApplicantRepository repository;


    @PostMapping("/")
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant newApplicant) {
        return new ResponseEntity<>(repository.save(newApplicant), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Applicant>> getAllApplicants(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Applicant> updateApplicant(@PathVariable Long id, @RequestBody Applicant update){
        Applicant current = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(update.getId() != null){
            current.setId(update.getId());
        }

        if(update.getName() != null){
            current.setName(update.getName());
        }

        if(update.getJobsList() != null){
            current.setJobsList(update.getJobsList());
        }

        if(update.getPosts() != null){
            current.setPosts(update.getPosts());
        }

        if(update.getResume() != null){
            current.setResume(update.getResume());
        }

        return new ResponseEntity<>(repository.save(current), HttpStatus.ACCEPTED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicant(@PathVariable Long id){
        repository.deleteById(id);
        return new ResponseEntity<>("Applicant Deleted", HttpStatus.GONE);
    }
}
