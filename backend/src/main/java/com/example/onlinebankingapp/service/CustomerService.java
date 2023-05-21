package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.repositories.CustomerRepository;
import com.example.onlinebankingapp.model.requests.CustomerRequest;
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

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow( () -> new NoSuchElementException("No Customer with id: " + customerId));

    }

    public Customer insertCustomer(CustomerRequest customerRequest) {
        Customer newCustomer = new Customer();

        newCustomer.setFirstName(customerRequest.getFirstName());
        newCustomer.setLastName(customerRequest.getLastName());
        newCustomer.setEmail(customerRequest.getEmail());
        newCustomer.setPhone(customerRequest.getPhone());
        newCustomer.setAddress(customerRequest.getAddress());

        return customerRepository.save(newCustomer);
    }

    public Customer updateCustomer(Long customerId, CustomerRequest customerRequest) {

        Customer customerToUpdate = customerRepository.findById(customerId)
                        .orElseThrow( () -> new NoSuchElementException("No Customer with id: "+ customerId));

        customerToUpdate.setFirstName(customerRequest.getFirstName());
        customerToUpdate.setLastName(customerRequest.getLastName());
        customerToUpdate.setEmail(customerRequest.getEmail());
        customerToUpdate.setPhone(customerRequest.getPhone());
        customerToUpdate.setAddress(customerRequest.getAddress());

        return customerRepository.save(customerToUpdate);
    }

    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                        .orElseThrow(() -> new NoSuchElementException("No Customer with id: " + customerId ));
        customerRepository.delete(customer);
    }

}
