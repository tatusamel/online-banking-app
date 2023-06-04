package com.example.onlinebankingapp.config.data_initialize;

import com.example.onlinebankingapp.model.entities.*;
import com.example.onlinebankingapp.model.enums.AccountType;
import com.example.onlinebankingapp.model.enums.TransactionType;
import com.example.onlinebankingapp.model.repositories.*;
import com.example.onlinebankingapp.service.UserActionService;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final BranchRepository branchRepository;
    private final AccountRepository accountRepository;
    private final SavingAccountRepository savingAccountRepository;
    private final TransactionRepository transactionRepository;
    private final CheckingAccountRepository checkingAccountRepository;
    private final CreditCardAccountRepository creditCardAccountRepository;
    private final UserActionService userActionService;


    public DatabaseSeeder(CustomerRepository customerRepository,
                          PasswordEncoder passwordEncoder,
                          BranchRepository branchRepository,
                          AccountRepository accountRepository,
                          SavingAccountRepository savingAccountRepository,
                          TransactionRepository transactionRepository,
                          CheckingAccountRepository checkingAccountRepository,
                          CreditCardAccountRepository creditCardAccountRepository,
                          UserActionService userActionService
    ) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.branchRepository = branchRepository;
        this.accountRepository = accountRepository;
        this.savingAccountRepository = savingAccountRepository;
        this.transactionRepository = transactionRepository;
        this.checkingAccountRepository = checkingAccountRepository;
        this.creditCardAccountRepository = creditCardAccountRepository;
        this.userActionService = userActionService;
    }

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();
        createCustomersHardCoded();
        createCustomers(faker);
        createBranches(faker);
        createSavingAccounts(faker);
        createCheckingAccounts(faker);
        createCreditCardAccounts(faker);
        createTransactions(faker);


    }
    public void createCustomersHardCoded() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("a@a");
        customer.setPassword(passwordEncoder.encode("a"));
        customer.setPhone("123");
        customer.setAddress("terme");
        customerRepository.save(customer);

        Customer c2 = new Customer();
        c2.setFirstName("Serhat");
        c2.setLastName("Gundem");
        c2.setEmail("b@b");
        c2.setPassword(passwordEncoder.encode("b"));
        c2.setPhone("123");
        c2.setAddress("samsun");
        customerRepository.save(c2);
    }
    public void createCustomers(Faker faker) {
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            customer.setFirstName(faker.name().firstName());
            customer.setLastName(faker.name().lastName());
            customer.setEmail(faker.internet().emailAddress());
            customer.setPhone(faker.phoneNumber().phoneNumber());
            customer.setAddress(faker.address().fullAddress());

            String hashedPassword = passwordEncoder.encode(faker.internet().password());
            customer.setPassword(hashedPassword);
            Customer savedCustomer = customerRepository.save(customer);
            userActionService.userCreatedAction(savedCustomer.getId());
        }
    }

    public void createBranches(Faker faker) {
        for (int i = 0; i < 20; i++) {
            String name = faker.company().name();
            String address = faker.address().fullAddress();
            String phoneNumber = faker.phoneNumber().phoneNumber();

            Branch branch = new Branch();
            branch.setName(name);
            branch.setAddress(address);
            branchRepository.save(branch);
        }
    }

    public void createSavingAccounts(Faker faker) {
        List<Customer> customers = customerRepository.findAll();
        List<Branch> branches = branchRepository.findAll();

        if(customers.isEmpty() || branches.isEmpty()) {
            throw new RuntimeException("Customers or branches not found");
        }

        Random rand = new Random();

        for (int i = 0; i < 100; i++) {
            Double balance = faker.number().randomDouble(2, 1000, 10000);
            Double interestRate = faker.number().randomDouble(2, 1, 5);

            SavingAccount savingAccount = new SavingAccount();
            savingAccount.setBalance(balance);
            savingAccount.setInterestRate(interestRate);
            savingAccount.setAccountNumber(UUID.randomUUID().toString());
            savingAccount.setAccountType(AccountType.SAVING_ACCOUNT);

            // Get random branch and customer
            Branch branch = branches.get(rand.nextInt(branches.size()));
            Customer customer = customers.get(rand.nextInt(customers.size()));

            savingAccount.setBranch(branch);
            savingAccount.setCustomer(customer);

            SavingAccount sa = savingAccountRepository.save(savingAccount);
            userActionService.accountCreatedAction(sa.getCustomer().getId(), sa.getAccountNumber());
        }
    }
    public void createCheckingAccounts(Faker faker) {
        List<Customer> customers = customerRepository.findAll();
        List<Branch> branches = branchRepository.findAll();

        if(customers.isEmpty() || branches.isEmpty()) {
            throw new RuntimeException("Customers or branches not found");
        }

        Random rand = new Random();

        for (int i = 0; i < 40; i++) {
            Double balance = Double.valueOf(faker.number().randomDouble(2, 1000, 10000));
            Double overdraftLimit = Double.valueOf(faker.number().randomDouble(2, 500, 1000));

            CheckingAccount checkingAccount = new CheckingAccount();
            checkingAccount.setBalance(balance);
            checkingAccount.setAccountNumber(UUID.randomUUID().toString());
            checkingAccount.setAccountType(AccountType.CHECKING_ACCOUNT);

            // Get random branch and customer
            Branch branch = branches.get(rand.nextInt(branches.size()));
            Customer customer = customers.get(rand.nextInt(customers.size()));

            checkingAccount.setBranch(branch);
            checkingAccount.setCustomer(customer);

            CheckingAccount ca = checkingAccountRepository.save(checkingAccount);
            userActionService.accountCreatedAction(ca.getCustomer().getId(), ca.getAccountNumber());
        }
    }
    public void createCreditCardAccounts(Faker faker) {
        List<Customer> customers = customerRepository.findAll();
        List<Branch> branches = branchRepository.findAll();

        if(customers.isEmpty() || branches.isEmpty()) {
            throw new RuntimeException("Customers or branches not found");
        }

        Random rand = new Random();

        for (int i = 0; i < 40; i++) {
            Double balance = faker.number().randomDouble(2, 0, 10000);
            int creditLimit = (int) faker.number().randomDouble(2, 1000, 10000);
            Double interestRate = faker.number().randomDouble(2, 1, 5);

            CreditCardAccount creditCardAccount = new CreditCardAccount();
            creditCardAccount.setBalance(balance);
            creditCardAccount.setCreditLimit(creditLimit);
            creditCardAccount.setInterestRate(interestRate);
            creditCardAccount.setAccountNumber(UUID.randomUUID().toString());
            creditCardAccount.setAccountType(AccountType.CREDIT_CARD_ACCOUNT);

            // Get random branch and customer
            Branch branch = branches.get(rand.nextInt(branches.size()));
            Customer customer = customers.get(rand.nextInt(customers.size()));

            creditCardAccount.setBranch(branch);
            creditCardAccount.setCustomer(customer);

            CreditCardAccount cca = creditCardAccountRepository.save(creditCardAccount);
            userActionService.accountCreatedAction(cca.getCustomer().getId(), cca.getAccountNumber());
        }
    }

    public void createTransactions(Faker faker) {
        List<Account> accounts = accountRepository.findAll();

        if(accounts.isEmpty() || accounts.size() < 2) {
            throw new RuntimeException("At least two accounts are needed to perform a transaction");
        }

        Random rand = new Random();

        for (int i = 0; i < 300; i++) {
            // Get random fromAccount and toAccount
            Account fromAccount = accounts.get(rand.nextInt(accounts.size()));
            Account toAccount = accounts.get(rand.nextInt(accounts.size()));

            // Ensure fromAccount and toAccount are not the same
            while (fromAccount.equals(toAccount)) {
                toAccount = accounts.get(rand.nextInt(accounts.size()));
            }

            // Set a random transaction amount
            Double amount = faker.number().randomDouble(2, 1, 500);

            // Check that fromAccount has enough funds
            if (fromAccount.getBalance() < amount) {
                continue;
            }

            // Perform the transaction
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);

            // Update account balances in the database
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            // Create and save the transaction
            Transaction transaction = new Transaction();
            transaction.setFromAccount(fromAccount);
            transaction.setToAccount(toAccount);
            transaction.setTransactionDate(new Date());
            transaction.setTransactionType(TransactionType.DEPOSIT);
            transaction.setAmount(amount);

            Transaction t = transactionRepository.save(transaction);
            userActionService.transactionCreatedAction(t.getFromAccount().getCustomer().getId(), t);
        }
    }







}
