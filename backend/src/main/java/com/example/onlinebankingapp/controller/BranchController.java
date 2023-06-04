package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Branch;
import com.example.onlinebankingapp.model.requests.BranchRequest;
import com.example.onlinebankingapp.service.BranchService;
import com.example.onlinebankingapp.view.converter.BranchDTOConverter;
import com.example.onlinebankingapp.view.dto.BranchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/branches")
public class BranchController {

    private final BranchService branchService;
    private final BranchDTOConverter branchDTOConverter;

    @Autowired
    public BranchController(BranchService branchService,
                            BranchDTOConverter branchDTOConverter) {
        this.branchService = branchService;
        this.branchDTOConverter = branchDTOConverter;
    }

    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAll() {
        List<BranchDTO> branchDTOS = branchService.getAll()
                .stream().map(branchDTOConverter::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(branchDTOS, HttpStatus.OK);
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long branchId) {
        BranchDTO branchDTO = branchDTOConverter.convertToDto(branchService.getBranchById(branchId));
        return new ResponseEntity<>(branchDTO, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<BranchDTO> insertBranch(@RequestBody BranchRequest branchRequest) {
        BranchDTO branchDTO = branchDTOConverter.convertToDto(branchService.insertBranch(branchRequest));
        return new ResponseEntity<>(branchDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{branchId}")
    public ResponseEntity<BranchDTO> updateBranch(@PathVariable Long branchId, @RequestBody BranchRequest branchRequest) {
        BranchDTO branchDTO = branchDTOConverter.convertToDto(branchService.updateBranch(branchId, branchRequest));
        return new ResponseEntity<>(branchDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{branchId}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long branchId) {
        branchService.deleteBranch(branchId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
