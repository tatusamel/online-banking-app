package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.repositories.BranchRepository;
import com.example.onlinebankingapp.model.requests.BranchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public ResponseEntity<List<Branch>> listAllBranches() {
        return new ResponseEntity<>(branchRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Branch> getBranchById(Long branchId) {
        Optional<Branch> branch = branchRepository.findById(branchId);
        if ( branch.isEmpty() ) {
            return new ResponseEntity<> (null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(branch.get(), HttpStatus.OK);
    }

    public ResponseEntity<Branch> insertBranch(BranchRequest branchRequest) {
        Branch newBranch = new Branch();

        newBranch.setName(branchRequest.getName());
        newBranch.setAddress(branchRequest.getAddress());

        return new ResponseEntity<>(branchRepository.save(newBranch), HttpStatus.CREATED);
    }

    public ResponseEntity<Branch> updateBranch(Long branchId, BranchRequest branchRequest) {
        Optional<Branch> branch = branchRepository.findById(branchId);

        if (branch.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Branch branchToUpdate = branch.get();

        branchToUpdate.setName(branchRequest.getName());
        branchToUpdate.setAddress(branchRequest.getAddress());

        return new ResponseEntity<>(branchRepository.save(branchToUpdate), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteBranch(Long branchId) {
        Optional<Branch> branch = branchRepository.findById(branchId);
        if (branch.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        branchRepository.delete(branch.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
