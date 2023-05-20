package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Branch;
import com.example.onlinebankingapp.model.requests.BranchRequest;
import com.example.onlinebankingapp.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<List<Branch>> listAllBranches() {
        return branchService.listAllBranches();
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long branchId) {
        return branchService.getBranchById(branchId);
    }

    @PostMapping("/insert")
    public ResponseEntity<Branch> insertBranch(@RequestBody BranchRequest branchRequest) {
        return branchService.insertBranch(branchRequest);
    }

    @PutMapping("/update/{branchId}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long branchId, @RequestBody BranchRequest branchRequest) {
        return branchService.updateBranch(branchId, branchRequest);
    }

    @DeleteMapping("/delete/{branchId}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long branchId) {
        return branchService.deleteBranch(branchId);
    }


}
