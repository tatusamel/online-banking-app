package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.repositories.CustomerRepository;
import com.example.onlinebankingapp.model.requests.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Customer createCustomer(CustomerRequest customerRequest) {
        Customer newCustomer = new Customer();
        mapRequestToCustomer(customerRequest, newCustomer);
        Customer savedCustomer = customerRepository.save(newCustomer);
        userActionService.userCreatedAction(savedCustomer.getId());
        return savedCustomer;
    }

    public Customer updateCustomer(Long customerId, CustomerRequest customerRequest) {
        Customer customerToUpdate = this.getCustomerById(customerId);
        mapRequestToCustomer(customerRequest, customerToUpdate);
        userActionService.userUpdatedAction(customerId);
        return customerRepository.save(customerToUpdate);
    }

    public void deleteCustomer(Long customerId) {
        Customer customer = this.getCustomerById(customerId);
        userActionService.userDeletedAction(customerId);
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
