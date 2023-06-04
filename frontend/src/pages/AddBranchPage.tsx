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

export const AddBranchPage = () => {
  const [name, setName] = useState('');
  const [address, setAddress] = useState('');
  const toast = useToast();
  const navigate = useNavigate();

  const handleCreateBranch = async () => {
    if (name.trim() === '' || address.trim() === '') {
      // Display error toast if name or address is empty
      toast({
        title: 'Name and address are required.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
      return;
    }

    try {
      const branchRequest = {
        name: name,
        address: address,
      };

      // Send the branch creation request to the backend
      const response = await axios.post('http://localhost:8080/branches/insert', branchRequest);

      // Display success toast and reset the form fields
      toast({
        title: 'Branch created successfully.',
        status: 'success',
        duration: 3000,
        isClosable: true,
      });
      navigate("/");
    } catch (error) {
      // Display error toast if an error occurred during branch creation
      console.log(error);
      toast({
        title: 'An error occurred when creating the branch.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    }
  };

  return (
    <Box p={8} maxWidth={500} mx="auto">
      <Heading size="lg" mb={4}>
        Create Branch
      </Heading>
      <FormControl id="name" mb={4}>
        <FormLabel>Name</FormLabel>
        <Input
          type="text"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
      </FormControl>
      <FormControl id="address" mb={4}>
        <FormLabel>Address</FormLabel>
        <Input
          type="text"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
        />
      </FormControl>
      <Button colorScheme="teal" onClick={handleCreateBranch}>
        Create Branch
      </Button>
    </Box>
  );
};
