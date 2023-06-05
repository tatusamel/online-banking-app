import React, { useState, useEffect } from 'react';
import {
  Box,
  Heading,
  Button,
  FormControl,
  FormLabel,
  Input,
  Select,
  useToast,
} from '@chakra-ui/react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const generateAccountNumber = () => {
  const accountNumberLength = 10;
  const accountNumberChars = '0123456789';
  let accountNumber = '';

  for (let i = 0; i < accountNumberLength; i++) {
    const randomIndex = Math.floor(Math.random() * accountNumberChars.length);
    accountNumber += accountNumberChars.charAt(randomIndex);
  }

  return accountNumber;
}

export const AddAccountPage = () => {
  const [branches, setBranches] = useState([]);
  const [accountType, setAccountType] = useState('');
  const [interestRate, setInterestRate] = useState('');
  const [creditLimit, setCreditLimit] = useState('');
  const navigate = useNavigate();
  const toast = useToast();

  const userId = localStorage.getItem('userId');

  useEffect(() => {
    const fetchBranches = async () => {
      try {
        const response = await axios.get('http://localhost:8080/branches');
        if (response.data.length === 0) {
          toast({
            title: 'There are no branches!',
            duration: 3000,
            isClosable: true,
          });
        }
        setBranches(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchBranches();
  }, []);

  const handleAddAccount = async () => {
    try {
      let accountRequest_ = {
        accountNumber: generateAccountNumber(),
        balance: 0,
        customerId: userId,
        branchId: (document.getElementById('branch') as HTMLInputElement).value,
        accountType: (document.getElementById('accountType') as HTMLInputElement).value,
      };
      let accountRequest = {};
      let endpoint = "http://localhost:8080/checking-accounts/insert";
      if (accountRequest_.accountType === "Checking") {
        accountRequest = {
          ...accountRequest_,
          accountType: "CHECKING_ACCOUNT"
        }
        endpoint = "http://localhost:8080/checking-accounts/insert";
      } else if (accountRequest_.accountType === "Savings") {
        accountRequest = {
          ...accountRequest_,
          accountType: "SAVING_ACCOUNT",
          interestRate: parseFloat(interestRate),
        }
        endpoint = "http://localhost:8080/saving-accounts/insert";
      } else if (accountRequest_.accountType === "CreditCard") {
        accountRequest = {
          ...accountRequest_,
          accountType: "CREDIT_CARD_ACCOUNT",
          interestRate: parseFloat(interestRate),
          balance: parseFloat(creditLimit),
          creditLimit: parseFloat(creditLimit)
        }
        endpoint = "http://localhost:8080/credit-card-accounts/insert";
      }

      // Send the account creation request to the backend
      const response = await axios.post(endpoint, accountRequest);

      // Display success toast and reset the form fields
      toast({
        title: 'Account created successfully.',
        status: 'success',
        duration: 3000,
        isClosable: true,
      });
      navigate("/home")
    } catch (error) {
      // Display error toast if an error occurred during account creation
      console.log(error);
      toast({
        title: 'An error occurred when creating the account.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    }
  };

  return (
    <Box p={8} maxWidth={500} mx="auto">
      <Heading size="lg" mb={4}>
        Add Account
      </Heading>
      <FormControl id="branch" mb={4}>
        <FormLabel>Branch</FormLabel>
        <Select>
          {branches.map(({ id, name }) => (
            <option key={id} value={id}>
              {name}
            </option>
          ))}
        </Select>
      </FormControl>
      <FormControl id="accountType" mb={4}>
        <FormLabel>Account Type</FormLabel>
        <Select
          value={accountType}
          onChange={(e) => setAccountType(e.target.value)}
        >
          <option value="Checking">Checking Account</option>
          <option value="Savings">Savings Account</option>
          <option value="CreditCard">Credit Card Account</option>
        </Select>
      </FormControl>
      {(accountType === 'Savings' || accountType === 'CreditCard') && (
        <FormControl id="interestRate" mb={4}>
          <FormLabel>Interest Rate (Taken as input just for the sake of demonstration purposes, real interest rate calculation does not work like this)</FormLabel>
          <Input
            type="number"
            step="0.01"
            value={interestRate}
            onChange={(e) => setInterestRate(e.target.value)}
          />
        </FormControl>
      )}
      {accountType === 'CreditCard' && (
          <FormControl id="creditLimit" mb={4}>
            <FormLabel>Credit Limit</FormLabel>
            <Input
              type="number"
              step="0.01"
              value={creditLimit}
              onChange={(e) => setCreditLimit(e.target.value)}
            />
          </FormControl>
      )}
      <Button colorScheme="teal" onClick={handleAddAccount}>
        Add Account
      </Button>
    </Box>
  );
};
