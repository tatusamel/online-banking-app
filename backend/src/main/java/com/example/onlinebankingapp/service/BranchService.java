package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.repositories.BranchRepository;
import com.example.onlinebankingapp.model.requests.BranchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<Branch> listAllBranches() {
        return branchRepository.findAll();
    }

    public Branch getBranchById(Long branchId) {
        return branchRepository.findById(branchId)
                .orElseThrow( () -> new NoSuchElementException("No Branch with id: " + branchId));
    }

    public Branch insertBranch(BranchRequest branchRequest) {
        Branch newBranch = new Branch();

        newBranch.setName(branchRequest.getName());
        newBranch.setAddress(branchRequest.getAddress());

        return branchRepository.save(newBranch);
    }

    public Branch updateBranch(Long branchId, BranchRequest branchRequest) {
        Branch branchToUpdate = branchRepository.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException("No Branch with id: " + branchId));

        branchToUpdate.setName(branchRequest.getName());
        branchToUpdate.setAddress(branchRequest.getAddress());

        return branchRepository.save(branchToUpdate);
    }

    public void deleteBranch(Long branchId) {
        Branch branchToDelete = branchRepository.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException("No Branch with id: " + branchId));
        branchRepository.delete(branchToDelete);
    }

}
