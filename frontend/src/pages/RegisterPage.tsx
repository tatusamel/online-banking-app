import { useState } from 'react';
import {
  Flex,
  Heading,
  Input,
  Button,
  Link,
  Box,
  FormControl,
  FormLabel,
  useColorModeValue,
} from '@chakra-ui/react';
import { Link as RouteLink, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useToast } from '@chakra-ui/react';

export const RegisterPage = () => {
  const formBackground = useColorModeValue('gray.100', 'gray.700');
  const toast = useToast();
  const navigate = useNavigate();

  const registerToast = () => {
    toast({
      title: 'Success.',
      description: 'Your account is created.',
      status: 'success',
      duration: 9000,
      isClosable: true,
    });
  };

  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    phone: '', // Add phone field to the formData state
    address: '', // Add address field to the formData state
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/customers/insert', formData);

      registerToast();
      navigate('/login');
    } catch (error) {
      console.error(error);
      toast({
        title: 'Error',
        description: 'Registration failed. Please try again.',
        status: 'error',
        duration: 9000,
        isClosable: true,
      });
    }
  };

  return (
    <Flex h="100vh" alignItems="center" justifyContent="center">
      <Flex
        flexDirection="column"
        bg={formBackground}
        p={12}
        borderRadius={8}
        boxShadow="lg"
        width="30vw"
      >
        <Heading mb={6}>Register</Heading>
        <form onSubmit={handleSubmit}>
          <FormControl isRequired>
            <FormLabel>First Name</FormLabel>
            <Input
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              variant="filled"
              mb={3}
              borderColor="white"
              borderWidth="2px"
            />
          </FormControl>
          <FormControl isRequired>
            <FormLabel>Last Name</FormLabel>
            <Input
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              variant="filled"
              mb={3}
              borderColor="white"
              borderWidth="2px"
            />
          </FormControl>
          <FormControl isRequired>
            <FormLabel>Email</FormLabel>
            <Input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              variant="filled"
              mb={3}
              borderColor="white"
              borderWidth="2px"
            />
          </FormControl>
          <FormControl isRequired>
            <FormLabel>Password</FormLabel>
            <Input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              variant="filled"
              mb={3}
              borderColor="white"
              borderWidth="2px"
            />
          </FormControl>
          <FormControl isRequired>
            <FormLabel>Phone</FormLabel>
            <Input
              type="tel"
              name="phone"
              value={formData.phone}
              onChange={handleChange}
              variant="filled"
              mb={3}
              borderColor="white"
              borderWidth="2px"
            />
          </FormControl>
          <FormControl isRequired>
            <FormLabel>Address</FormLabel>
            <Input
              type="text"
              name="address"
              value={formData.address}
              onChange={handleChange}
              variant="filled"
              mb={3}
              borderColor="white"
              borderWidth="2px"
            />
          </FormControl>
          <Button colorScheme="teal" type="submit" mb={8}>
            Register
          </Button>
        </form>
        <Box>
          Already have an account?{' '}
          <Link as={RouteLink} to="/login" color="teal.500" href="#">
            Log In
          </Link>
        </Box>
      </Flex>
    </Flex>
  );
};
