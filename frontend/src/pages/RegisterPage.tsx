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
  useColorMode,
  useColorModeValue,
} from '@chakra-ui/react';
import { Link as RouteLink } from 'react-router-dom';
import axios from 'axios';

export const RegisterPage = () => {
  const formBackground = useColorModeValue('gray.100', 'gray.700');

  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  });

  

  const handleChange = (e: any) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
  
    try {
      console.log(formData);
      const response = await axios.post('http://localhost:8080/users', formData);
      console.log(response.data); // Assuming the response contains the created user data
  
      // Reset the form
      setFormData({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
      });
    } catch (error) {
      console.error(error);
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
              mb={6}
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
}

