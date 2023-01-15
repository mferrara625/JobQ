package com.mferrara.jobs.controllers;

import com.mferrara.jobs.auth.User;
import com.mferrara.jobs.models.Employer;
import com.mferrara.jobs.repositories.EmployerRepository;
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
@RequestMapping("/Employer")
public class EmployerController {

    @Autowired
    private EmployerRepository repository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/")
    public ResponseEntity<Employer> createEmployer(@RequestBody Employer newEmployer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User currentUser = userRepository.findById(userDetails.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        newEmployer.setUser(currentUser);
        return new ResponseEntity<>(repository.save(newEmployer), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Employer>> getAllEmployers(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employer> updateEmployer(@PathVariable Long id, @RequestBody Employer update){
        Employer current = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(update.getId() != null){
            current.setId(update.getId());
        }

        if(update.getCompanyName() != null){
            current.setCompanyName(update.getCompanyName());
        }

        if(update.getJobListings() != null){
            current.setJobListings(update.getJobListings());
        }


        return new ResponseEntity<>(repository.save(current), HttpStatus.ACCEPTED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployer(@PathVariable Long id){
        repository.deleteById(id);
        return new ResponseEntity<>("Employer Deleted", HttpStatus.GONE);
    }
}

