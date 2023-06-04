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

export const AddAccountPage = () => {
  const [accountNumber, setAccountNumber] = useState('');
  const [balance, setBalance] = useState(0);
  const [branchId, setBranchId] = useState('');
  const [accountType, setAccountType] = useState('');

  const toast = useToast();

  const userId = localStorage.getItem('userId');

  const handleAddAccount = async () => {
    try {
      const accountRequest = {
        accountNumber: accountNumber,
        balance: balance,
        userId: userId,
        branchId: branchId,
        accountType: accountType,
      };

      // Check if the account type is a savings account
      if (accountType === 'Savings') {
        // Add the interest rate logic here for savings account
        const interestRate = 0.05; // Assuming an interest rate of 5%
        const interestAmount = balance * interestRate;

        // Add the interest amount to the balance
        accountRequest.balance += interestAmount;
      }

      // Send the account creation request to the backend
      const response = await axios.post('/accounts/insert', accountRequest);

      // Display success toast and reset the form fields
      toast({
        title: 'Account created successfully.',
        status: 'success',
        duration: 3000,
        isClosable: true,
      });
      setAccountNumber('');
      setBalance(0);
      setBranchId('');
      setAccountType('');
    } catch (error) {
      // Display error toast if an error occurred during account creation
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
      <FormControl id="accountNumber" mb={4}>
        <FormLabel>Account Number</FormLabel>
        <Input
          type="text"
          value={accountNumber}
          onChange={(e) => setAccountNumber(e.target.value)}
        />
      </FormControl>
      <FormControl id="balance" mb={4}>
        <FormLabel>Balance</FormLabel>
        <Input
          type="number"
          value={balance}
          onChange={(e) => setBalance(Number(e.target.value))}
        />
      </FormControl>
      <FormControl id="branchId" mb={4}>
        <FormLabel>Branch ID</FormLabel>
        <Input
          type="text"
          value={branchId}
          onChange={(e) => setBranchId(e.target.value)}
        />
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
      <Button colorScheme="teal" onClick={handleAddAccount}>
        Add Account
      </Button>
    </Box>
  );
};
