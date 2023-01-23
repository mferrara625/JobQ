package com.mferrara.jobs.controllers;

import com.mferrara.jobs.auth.ERole;
import com.mferrara.jobs.auth.User;
import com.mferrara.jobs.models.Applicant;
import com.mferrara.jobs.models.JobPost;
import com.mferrara.jobs.repositories.*;
import com.mferrara.jobs.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@CrossOrigin
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

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private RoleRepository roleRepository;


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
        return new ResponseEntity<>(repository.findByContentLike(keyword), HttpStatus.OK);
    }

    @GetMapping("/searchByCompany/{companyName}")
    public @ResponseBody ResponseEntity<List<JobPost>> searchJobListingByCompany(@PathVariable String companyName){
        return new ResponseEntity<>(repository.findAllJobPostsByEmployer_CompanyName(companyName), HttpStatus.OK);
    }


    @GetMapping("/viewYourCurrentListings/{id}")
    public @ResponseBody ResponseEntity<List<JobPost>> searchJobListingByEmployerUserId(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User currentUser = userRepository.findById(userDetails.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(Objects.equals(currentUser.getId(), id))
        return new ResponseEntity<>(repository.findAllJobPostsByEmployer_User_Id(id), HttpStatus.OK);

        List<JobPost> emptyList = new ArrayList<>();

        return new ResponseEntity<>(emptyList, HttpStatus.NOT_FOUND);
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

    @PutMapping("/apply/{jobId}")
    public ResponseEntity<String> applyForJob(@PathVariable Long jobId){

        JobPost current = repository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User currentUser = userRepository.findById(userDetails.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Applicant currentApplicant = applicantRepository.findApplicantByUser(currentUser);
        Set<JobPost> jobs = currentApplicant.getJobsList();
        jobs.add(current);
        currentApplicant.setJobsList(jobs);
        applicantRepository.save(currentApplicant);


        return new ResponseEntity<>("Application Submitted!", HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobPost(@PathVariable Long id){
        JobPost current = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User currentUser = userRepository.findById(userDetails.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(current.getEmployer() == employerRepository.findEmployerByUser(currentUser) || currentUser.getRoles().contains(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found")))) {
            for(Applicant a : current.getApplicants()) {
                a.getJobsList().remove(current);
                applicantRepository.save(a);
            }
            Set<Applicant> emptySet = new HashSet<>();
            current.setApplicants(emptySet);
            repository.save(current);
            repository.deleteById(id);
        }

        return new ResponseEntity<>("Job Post Deleted", HttpStatus.OK);
    }
}
