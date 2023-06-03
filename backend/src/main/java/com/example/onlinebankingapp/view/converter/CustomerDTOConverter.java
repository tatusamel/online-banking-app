package com.example.onlinebankingapp.view.converter;

import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.view.dto.CustomerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerDTOConverter {

    @Autowired
    ModelMapper modelMapper;

    public CustomerDTO convertToDto(Customer customer) {
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        return customerDTO;
    }

    public Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        return customer;
    }
}
