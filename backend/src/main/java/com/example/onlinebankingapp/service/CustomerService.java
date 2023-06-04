package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.repositories.CustomerRepository;
import com.example.onlinebankingapp.model.requests.CustomerRequest;
import com.example.onlinebankingapp.view.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserActionService userActionService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           UserActionService userActionService) {
        this.customerRepository = customerRepository;
        this.userActionService = userActionService;
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow( () -> new NoSuchElementException("No Customer with id: " + customerId));

    }

    public Customer insertCustomer(CustomerRequest customerRequest) {
        Customer newCustomer = new Customer();
        mapRequestToCustomer(customerRequest, newCustomer);
        return customerRepository.save(newCustomer);
    }

    public Customer updateCustomer(Long customerId, CustomerRequest customerRequest) {
        Customer customerToUpdate = this.getCustomerById(customerId);
        mapRequestToCustomer(customerRequest, customerToUpdate);
        return customerRepository.save(customerToUpdate);
    }

    public void deleteCustomer(Long customerId) {
        Customer customer = this.getCustomerById(customerId);
        customerRepository.delete(customer);
    }

    private Customer mapRequestToCustomer(CustomerRequest customerRequest, Customer customer) {
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhone(customerRequest.getPhone());
        customer.setAddress(customerRequest.getAddress());
        // TODO:encrypt password
        customer.setPassword(customerRequest.getPassword());

        return customer;
    }

}
