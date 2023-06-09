package com.example.onlinebankingapp.service;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.repositories.CustomerRepository;
import com.example.onlinebankingapp.model.requests.CustomerRequest;
import com.example.onlinebankingapp.view.converter.TransactionDTOConverter;
import com.example.onlinebankingapp.view.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserActionService userActionService;
    private final PasswordEncoder passwordEncoder;
    private final TransactionDTOConverter transactionDTOConverter;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           UserActionService userActionService,
                           PasswordEncoder passwordEncoder,
                           TransactionDTOConverter transactionDTOConverter) {
        this.customerRepository = customerRepository;
        this.userActionService = userActionService;
        this.passwordEncoder = passwordEncoder;
        this.transactionDTOConverter = transactionDTOConverter;
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

        String hashedPassword = passwordEncoder.encode(customerRequest.getPassword());
        customer.setPassword(hashedPassword);

        return customer;
    }

    public List<TransactionDTO> get10MostRecentTransactionsByCustomerId(Long customerId) {
        return customerRepository.find10MostRecentTransactionsByCustomerId(customerId, PageRequest.of(0, 10))
                .stream()
                .map(transactionDTOConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public Integer getNumberOfCustomers() {
        return customerRepository.findNumberOfCustomers();
    }
}
