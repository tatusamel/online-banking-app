import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  Box,
  Heading,
  Button,
  FormControl,
  FormLabel,
  Input,
  useToast,
} from '@chakra-ui/react';
import axios from 'axios';

export const UpdateAccountPage = () => {
  const [account, setAccount] = useState(null);
  const [balance, setBalance] = useState('');
  const [branchId, setBranchId] = useState('');

  const { accountId } = useParams();
  const toast = useToast();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAccount = async () => {
      try {
        const response = await axios.get(`/checking-accounts/${accountId}`);
        setAccount(response.data);
        setBalance(response.data.balance);
        setBranchId(response.data.branchId);
      } catch (error) {
        console.error(error);
      }
    };

    fetchAccount();
  }, [accountId]);

  const handleUpdateAccount = async () => {
    try {
      const accountData = {
        balance: balance,
        branchId: branchId,
      };

      const response = await axios.put(`/checking-accounts/update/${accountId}`, accountData);

      toast({
        title: 'Account updated successfully.',
        status: 'success',
        duration: 3000,
        isClosable: true,
      });

      navigate('/accounts'); // Redirect to the accounts page after successful update
    } catch (error) {
      console.error(error);

      toast({
        title: 'An error occurred while updating the account.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    }
  };

  const handleDeleteAccount = async () => {
    try {
      await axios.delete(`/checking-accounts/delete/${accountId}`);

      toast({
        title: 'Account deleted successfully.',
        status: 'success',
        duration: 3000,
        isClosable: true,
      });

      navigate('/accounts'); // Redirect to the accounts page after successful deletion
    } catch (error) {
      console.error(error);

      toast({
        title: 'An error occurred while deleting the account.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    }
  };

  if (!account) {
    return <Box>Loading...</Box>;
  }

  return (
    <Box p={8} maxWidth={500} mx="auto">
      <Heading size="lg" mb={4}>
        Update Account
      </Heading>
      <FormControl id="balance" mb={4}>
        <FormLabel>Balance</FormLabel>
        <Input
          type="number"
          value={balance}
          onChange={(e) => setBalance(e.target.value)}
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
      <Button colorScheme="teal" onClick={handleUpdateAccount} mr={4}>
        Update
      </Button>
      <Button colorScheme="red" onClick={handleDeleteAccount}>
        Delete
      </Button>
    </Box>
  );
};
