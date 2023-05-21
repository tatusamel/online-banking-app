package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Branch;
import com.example.onlinebankingapp.model.requests.BranchRequest;
import com.example.onlinebankingapp.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branchs")
public class BranchController {

    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<List<Branch>> listAllBranches() {
        List<Branch> branches = branchService.listAllBranches();
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long branchId) {
        Branch branch = branchService.getBranchById(branchId);
        return new ResponseEntity<>(branch, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Branch> insertBranch(@RequestBody BranchRequest branchRequest) {
        Branch branch = branchService.insertBranch(branchRequest);
        return new ResponseEntity<>(branch, HttpStatus.CREATED);
    }

    @PutMapping("/update/{branchId}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long branchId, @RequestBody BranchRequest branchRequest) {
        Branch branch = branchService.updateBranch(branchId, branchRequest);
        return new ResponseEntity<>(branch, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{branchId}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long branchId) {
        branchService.deleteBranch(branchId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
