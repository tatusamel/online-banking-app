package com.example.onlinebankingapp.view.converter;

import com.example.onlinebankingapp.model.entities.Bill;
import com.example.onlinebankingapp.view.dto.BillDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillDTOConverter {

    @Autowired
    ModelMapper modelMapper;

    public BillDTO convertToDto(Bill bill) {
        BillDTO billDTO = modelMapper.map(bill, BillDTO.class);
        return billDTO;
    }

    public Bill convertToEntity(BillDTO billDTO) {
        Bill bill = modelMapper.map(billDTO, Bill.class);
        return bill;
    }
}
