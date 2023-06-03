package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.model.requests.CustomerRequest;
import com.example.onlinebankingapp.service.CheckingAccountService;
import com.example.onlinebankingapp.service.CustomerService;
import com.example.onlinebankingapp.view.converter.CustomerDTOConverter;
import com.example.onlinebankingapp.view.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerDTOConverter customerDTOConverter;
    @Autowired
    public CustomerController(CustomerService customerService,
                              CustomerDTOConverter customerDTOConverter) {
        this.customerService = customerService;
        this.customerDTOConverter = customerDTOConverter;
    }

    @GetMapping()
    public ResponseEntity<List<CustomerDTO>> listAllCustomers(){
        List<CustomerDTO> customerDTOS = customerService.listAllCustomers()
                .stream().map(customerDTOConverter::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
        CustomerDTO customerDTO =  customerDTOConverter.convertToDto(customerService.getCustomerById(customerId));
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<CustomerDTO> insertCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerDTO customerDTO =  customerDTOConverter.convertToDto(customerService.insertCustomer(customerRequest));
        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerRequest customerRequest) {
        CustomerDTO customerDTO = customerDTOConverter.convertToDto(customerService.updateCustomer(customerId, customerRequest));
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    public  ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
