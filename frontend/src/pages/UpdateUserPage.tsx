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

export const UpdateUserPage = () => {
  const userId: any = localStorage.getItem("userId");
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const toast = useToast();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/users/${userId}`);
        setFirstName(response.data.firstName);
        setLastName(response.data.lastName);
        setEmail(response.data.email);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUser();
  }, []);

  const handleUpdateUser = async () => {
    try {
      const userData = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password,
      };

      const response = await axios.put(`http://localhost:8080/users/${userId}`, userData);

      toast({
        title: 'User updated successfully.',
        status: 'success',
        duration: 3000,
        isClosable: true,
      });

      navigate('/'); // Redirect to the users page after successful update
    } catch (error) {
      console.error(error);

      toast({
        title: 'An error occurred while updating the user.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    }
  };

  const handleDeleteUser = async () => {
    try {
      await axios.delete(`http://localhost:8080/users/${userId}`);
      localStorage.removeItem("userId");

      toast({
        title: 'User deleted successfully.',
        status: 'success',
        duration: 3000,
        isClosable: true,
      });

      navigate('/');
    } catch (error) {
      console.error(error);

      toast({
        title: 'An error occurred while deleting the user.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    }
  };

  return (
    <Box p={8} maxWidth={500} mx="auto">
      <Heading size="lg" mb={4}>
        Update User
      </Heading>
      <FormControl id="firstName" mb={4}>
        <FormLabel>First Name</FormLabel>
        <Input
          type="text"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
        />
      </FormControl>
      <FormControl id="lastName" mb={4}>
        <FormLabel>Last Name</FormLabel>
        <Input
          type="text"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
        />
      </FormControl>
      <FormControl id="email" mb={4}>
        <FormLabel>Email</FormLabel>
        <Input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
      </FormControl>
      <FormControl id="password" mb={4}>
        <FormLabel>Password</FormLabel>
        <Input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </FormControl>
      <Button colorScheme="teal" onClick={handleUpdateUser} mr={4}>
        Update
      </Button>
      <Button colorScheme="red" onClick={handleDeleteUser}>
        Delete
      </Button>
    </Box>
  );
};
