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
  const [balance, setBalance] = useState(0);
  const [branches, setBranches] = useState([]);
  const [accountType, setAccountType] = useState('');

  const toast = useToast();

  const userId = localStorage.getItem('userId');

  useEffect(() => {
    const fetchBranches = async () => {
      try {
        const response = await axios.get('http://localhost:8080/branches');
        if (response.data.length == 0) {
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
      const accountRequest = {
        accountNumber: generateAccountNumber(),
        balance: 0,
        userId: userId,
        branchId: (document.getElementById("branch") as HTMLInputElement).value,
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
      //TODO update for checking account, saving account, credit account
      console.log(accountRequest);
      const response = await axios.post('http://localhost:8080/accounts/insert', accountRequest);

      // Display success toast and reset the form fields
      toast({
        title: 'Account created successfully.',
        status: 'success',
        duration: 3000,
        isClosable: true,
      });
      setBalance(0);
      setAccountType('');
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
        <Select
          // value={branches.length > 0 ? branches[0].name : null}
          //onChange={(e) => setBranch(e.target.value)}
        >
          {branches.map(({id, name}) => (
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
      <Button colorScheme="teal" onClick={handleAddAccount}>
        Add Account
      </Button>
    </Box>
  );
};
