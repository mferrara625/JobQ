package com.mferrara.jobs.controllers;

import com.mferrara.jobs.auth.User;
import com.mferrara.jobs.models.Applicant;
import com.mferrara.jobs.models.Resume;
import com.mferrara.jobs.repositories.ApplicantRepository;
import com.mferrara.jobs.repositories.ResumeRepository;
import com.mferrara.jobs.repositories.UserRepository;
import com.mferrara.jobs.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
@RequestMapping("/Resume")
public class ResumeController {

    @Autowired
    private ResumeRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicantRepository applicantRepository;


    @PostMapping("/")
    public ResponseEntity<Resume> createResume(@RequestBody Resume newResume){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User currentUser = userRepository.findById(userDetails.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Applicant currentApplicant = applicantRepository.findApplicantByUser(currentUser);

        currentApplicant.setResume(newResume);

        return new ResponseEntity<>(repository.save(newResume), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Resume>> getAllResumes(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resume> updateResume(@PathVariable Long id, @RequestBody Resume update){
        Resume current = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(update.getId() != null){
            current.setId(update.getId());
        }

        if(update.getApplicant() != null){
            current.setApplicant(update.getApplicant());
        }

        if(update.getResumeLink() != null){
            current.setResumeLink(update.getResumeLink());
        }

        return new ResponseEntity<>(repository.save(current), HttpStatus.ACCEPTED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable Long id){
        repository.deleteById(id);
        return new ResponseEntity<>("Resume Deleted", HttpStatus.GONE);
    }
}