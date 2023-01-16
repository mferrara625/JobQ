package com.mferrara.jobs.controllers;

import com.mferrara.jobs.auth.ERole;
import com.mferrara.jobs.auth.Role;
import com.mferrara.jobs.auth.User;
import com.mferrara.jobs.models.Applicant;
import com.mferrara.jobs.repositories.ApplicantRepository;
import com.mferrara.jobs.repositories.RoleRepository;
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
import java.util.Set;

@RestController
@Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
@RequestMapping("/applicant")
public class ApplicantController {

    @Autowired
    private ApplicantRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @PostMapping("/")
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant newApplicant) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User currentUser = userRepository.findById(userDetails.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Set<Role> roles = currentUser.getRoles();
        Role applicantRole = roleRepository.findByName(ERole.ROLE_APPLICANT)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
        roles.add(applicantRole);
        currentUser.setRoles(roles);
        newApplicant.setUser(currentUser);
        return new ResponseEntity<>(repository.save(newApplicant), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Applicant>> getAllApplicants(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/findApplicant/{username}")
    public ResponseEntity<Applicant> findApplicant(@PathVariable String username){
        return new ResponseEntity<>(repository.findApplicantByUser_Username(username), HttpStatus.OK);
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

        if(update.getLikedPosts() != null){
            current.setLikedPosts(update.getLikedPosts());
        }

        if(update.getUser() != null){
            current.setUser(update.getUser());
        }

        return new ResponseEntity<>(repository.save(current), HttpStatus.ACCEPTED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicant(@PathVariable Long id){
        repository.deleteById(id);
        return new ResponseEntity<>("Applicant Deleted", HttpStatus.GONE);
    }
}
