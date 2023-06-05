import React, { useEffect, useState } from 'react';
import {
  Box,
  Heading,
  FormControl,
  FormLabel,
  Input,
  Button,
  Select,
  useToast,
} from '@chakra-ui/react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export const MoneyTransferPage = () => {
  const [accountFrom, setAccountFrom] = useState('');
  const [accountOptions, setAccountOptions] = useState([]);
  const [accountTo, setAccountTo] = useState('');
  const [amount, setAmount] = useState('');
  const [accountBalance, setAccountBalance] = useState<number | null>(null); // Specify accountBalance type as 'number | null'
  const [isLoading, setIsLoading] = useState(true);
  const toast = useToast();
  const navigate = useNavigate();

  const userId = localStorage.getItem('userId');

  const fetchAccounts = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/customers/${userId}/accounts`);
      setAccountOptions(response.data);
    } catch (error) {
      console.error(error);
      toast({
        title: 'An error occurred when fetching accounts.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchAccounts();
  }, []);

  useEffect(() => {
    const fetchAccountBalance = async () => {
      if (accountFrom) {
        try {
          const response = await axios.get(`http://localhost:8080/accounts/${accountFrom}`);
          setAccountBalance(response.data.balance);
        } catch (error) {
          console.error(error);
          toast({
            title: 'An error occurred when fetching the account balance.',
            status: 'error',
            duration: 3000,
            isClosable: true,
          });
        }
      } else {
        setAccountBalance(null);
      }
    };

    fetchAccountBalance();
  }, [accountFrom]);

  const handleTransfer = async () => {
    try {
      // Validate the transfer amount
      const transferAmount = parseFloat(amount);

      if (transferAmount <= 0) {
        toast({
          title: 'Invalid transfer amount.',
          status: 'error',
          duration: 3000,
          isClosable: true,
        });
        return;
      }

      if (transferAmount > accountBalance!) {
        toast({
          title: 'Insufficient balance in accountFrom.',
          status: 'error',
          duration: 3000,
          isClosable: true,
        });
        return;
      }

      const toAccountId_response = await axios.get(`http://localhost:8080/accounts/by-name/${accountTo}`);
      const toAccountId = toAccountId_response.data.id;

      const data = {
        fromAccountId: accountFrom,
        toAccountId: toAccountId,
        amount: transferAmount,
      };
      // Perform the money transfer
      const response = await axios.post('http://localhost:8080/transactions/insert', data);

      // Display success toast and reset form inputs
      toast({
        title: 'Money transfer successful.',
        status: 'success',
        duration: 3000,
        isClosable: true,
      });
      setAccountFrom('');
      setAccountTo('');
      setAmount('');
      navigate('/');
    } catch (error) {
      console.error(error);
      // Display error toast if the money transfer fails
      toast({
        title: 'An error occurred during the money transfer.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    }
  };

  if (isLoading) {
    return <div>Loading...</div>; // Render a loading indicator while fetching data
  }

  return (
    <>
      <Box p={8} maxWidth={500} mx="auto">
        <Heading size="m" mb={4}>
          Transfer money from one account into another.
        </Heading>
        <FormControl id="accountFrom" mb={4}>
          <FormLabel>Account From</FormLabel>
          <Select
            value={accountFrom}
            onChange={(e) => setAccountFrom(e.target.value)}
          >
            {accountOptions.map(({ id, accountNumber }) => (
              <option key={id} value={id}>
                {accountNumber}
              </option>
            ))}
          </Select>
        </FormControl>
        {accountFrom && accountBalance !== null && (
          <Box mb={4}>
            <strong>Balance:</strong> {accountBalance}
          </Box>
        )}
        <FormControl id="accountTo" mb={4}>
          <FormLabel>Account To</FormLabel>
          <Input
            type="text"
            value={accountTo}
            onChange={(e) => setAccountTo(e.target.value)}
          />
        </FormControl>
        <FormControl id="amount" mb={4}>
          <FormLabel>Amount</FormLabel>
          <Input
            type="number"
            step="0.01"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
          />
        </FormControl>
        <Button colorScheme="teal" onClick={handleTransfer}>
          Transfer Money
        </Button>
      </Box>
    </>
  );
};
