package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.repositories.CustomerRepository;
import com.example.onlinebankingapp.model.requests.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<List<Customer>> listAllCustomers() {
        return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Customer> getCustomerById(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if ( customer.isEmpty() ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer.get(), HttpStatus.FOUND);
    }

    public ResponseEntity<Customer> insertCustomer(CustomerRequest customerRequest) {
        Customer newCustomer = new Customer();

        newCustomer.setFirstName(customerRequest.getFirstName());
        newCustomer.setLastName(customerRequest.getLastName());
        newCustomer.setEmail(customerRequest.getEmail());
        newCustomer.setPhone(customerRequest.getPhone());
        newCustomer.setAddress(customerRequest.getAddress());

        return new ResponseEntity<>(customerRepository.save(newCustomer), HttpStatus.CREATED);
    }

    public ResponseEntity<Customer> updateCustomer(Long customerId, CustomerRequest customerRequest) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Customer customerToUpdate = customer.get();

        customerToUpdate.setFirstName(customerRequest.getFirstName());
        customerToUpdate.setLastName(customerRequest.getLastName());
        customerToUpdate.setEmail(customerRequest.getEmail());
        customerToUpdate.setPhone(customerRequest.getPhone());
        customerToUpdate.setAddress(customerRequest.getAddress());

        return new ResponseEntity<>(customerRepository.save(customerToUpdate), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        customerRepository.delete(customer.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
