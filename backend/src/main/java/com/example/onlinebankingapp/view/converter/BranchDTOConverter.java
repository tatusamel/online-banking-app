package com.example.onlinebankingapp.view.converter;

import com.example.onlinebankingapp.model.entities.Branch;
import com.example.onlinebankingapp.view.dto.BranchDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BranchDTOConverter {

    @Autowired
    ModelMapper modelMapper;

    public BranchDTO convertToDto(Branch branch) {
        BranchDTO branchDTO = modelMapper.map(branch, BranchDTO.class);
        return branchDTO;
    }

    public Branch convertToEntity(BranchDTO branchDTO) {
        Branch branch = modelMapper.map(branchDTO, Branch.class);
        return branch;
    }

}
