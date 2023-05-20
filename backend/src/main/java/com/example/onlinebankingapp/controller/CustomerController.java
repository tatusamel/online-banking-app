package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Customer;
import com.example.onlinebankingapp.model.requests.CustomerRequest;
import com.example.onlinebankingapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public ResponseEntity<List<Customer>> listAllCustomers(){
        return customerService.listAllCustomers();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/insert")
    public ResponseEntity<Customer> insertCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.insertCustomer(customerRequest);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerRequest customerRequest) {
        return customerService.updateCustomer(customerId, customerRequest);
    }

    @DeleteMapping("/delete/{customerId}")
    public  ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        return customerService.deleteCustomer(customerId);
    }

}
