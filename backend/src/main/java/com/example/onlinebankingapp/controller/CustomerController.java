package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.model.requests.CustomerRequest;
import com.example.onlinebankingapp.service.CheckingAccountService;
import com.example.onlinebankingapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public ResponseEntity<List<Customer>> listAllCustomers(){
        List<Customer> customers = customerService.listAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        Customer customer =  customerService.getCustomerById(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Customer> insertCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer =  customerService.insertCustomer(customerRequest);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerRequest customerRequest) {
        Customer customer = customerService.updateCustomer(customerId, customerRequest);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    public  ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
