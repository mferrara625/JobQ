package com.mferrara.jobs.controllers;

import com.mferrara.jobs.auth.User;
import com.mferrara.jobs.models.Content;
import com.mferrara.jobs.models.JobPost;
import com.mferrara.jobs.repositories.EmployerRepository;
import com.mferrara.jobs.repositories.JobPostRepository;
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

@RestController
@Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
@RequestMapping("/JobPost")
public class JobPostController {

    @Autowired
    private JobPostRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployerRepository employerRepository;


    @Secured({"ROLE_EMPLOYER", "ROLE_ADMIN"})
    @PostMapping("/")
    public ResponseEntity<JobPost> createJobPost(@RequestBody JobPost newJobPost) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User currentUser = userRepository.findById(userDetails.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        newJobPost.setEmployer(employerRepository.findEmployerByUser(currentUser));

        return new ResponseEntity<>(repository.save(newJobPost), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<JobPost>> getAllJobPosts(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public @ResponseBody ResponseEntity<List<JobPost>> searchContentText(@PathVariable String keyword){
        System.out.println(keyword);
        return new ResponseEntity<>(repository.findByContentLike(keyword), HttpStatus.OK);
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
