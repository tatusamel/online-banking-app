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

interface UpdateBranchPageProps {
    branchId: string;
  }

export const UpdateBranchPage: React.FC<UpdateBranchPageProps> = ({ branchId }) => {

  //const { branchId } = useParams();
  const [name, setName] = useState('');
  const [address, setAddress] = useState('');

  const toast = useToast();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchBranch = async () => {
      try {
        console.log(branchId);
        const response = await axios.get(`http://localhost:8080/branches/${branchId}`);
        setName(response.data.name);
        setAddress(response.data.address);
      } catch (error) {
        console.error(error);
      }
    };

    fetchBranch();
  }, [branchId]);

  const handleUpdateBranch = async () => {
    try {
      const branchData = {
        name: name,
        address: address,
      };

      const response = await axios.put(`http://localhost:8080/branches/update/${branchId}`, branchData);

      toast({
        title: 'Branch updated successfully.',
        status: 'success',
        duration: 3000,
        isClosable: true,
      });

      navigate('/'); // Redirect to the branches page after successful update
    } catch (error) {
      console.error(error);

      toast({
        title: 'An error occurred while updating the branch.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    }
  };

  const handleDeleteBranch = async () => {
    try {
      await axios.delete(`http://localhost:8080/branches/${branchId}`);

      toast({
        title: 'Branch deleted successfully.',
        status: 'success',
        duration: 3000,
        isClosable: true,
      });

      navigate('/branches');
    } catch (error) {
      console.error(error);

      toast({
        title: 'An error occurred while deleting the branch.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    }
  };

  return (
    <Box p={8} maxWidth={500} mx="auto">
      <Heading size="lg" mb={4}>
        Update Branch
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
      <Button colorScheme="teal" onClick={handleUpdateBranch} mr={4}>
        Update
      </Button>
      <Button colorScheme="red" onClick={handleDeleteBranch}>
        Delete
      </Button>
    </Box>
  );
};
