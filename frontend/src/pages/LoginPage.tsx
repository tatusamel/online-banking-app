import {
  Flex,
  Heading,
  Input,
  Button,
  Link,
  Box,
  useColorModeValue,
  useToast,
} from '@chakra-ui/react';
import { Link as RouteLink, useNavigate } from 'react-router-dom';
import axios from 'axios';

export const LoginPage = () => {
  const formBackground = useColorModeValue('gray.100', 'gray.700');
  const toast = useToast();
  const navigate = useNavigate();

  const loginToast = () => {
    toast({
      title: 'Success.',
      description: 'Logged in successfully.',
      status: 'success',
      duration: 9000,
      isClosable: true,
    });
  };

  const handleLogin = async () => {
    try {
      const emailInput = document.getElementById('email') as HTMLInputElement;
      const passwordInput = document.getElementById('password') as HTMLInputElement;

      const email = emailInput?.value;
      const password = passwordInput?.value;

      const response = await axios.post('http://localhost:8080/login', {
        email: email,
        password: password,
      });
      console.log("Response", response);

      if (response.status == 200) {
        // Set userId in localStorage
        localStorage.setItem('userId', response.data.id);

        loginToast();
        navigate('/home'); // Redirect to home page if login is successful
      } else {
        toast({
          title: 'Error.',
          description: 'Invalid credentials.',
          status: 'error',
          duration: 9000,
          isClosable: true,
        });
      }
    } catch (error: any) {
      if (error.response.status == 401 || error.response.status == 404) {
        toast({
          title: 'Error.',
          description: 'Invalid credentials.',
          status: 'error',
          duration: 9000,
          isClosable: true,
        });
      }
      else {
        toast({
          title: 'An error occurred when sending the request.',
          status: 'error',
          isClosable: true,
        });

      }
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
        <Heading mb={6}>Log In</Heading>
        <Input
          id="email"
          placeholder="johndoe@gmail.com"
          type="email"
          variant="filled"
          mb={3}
          borderColor="white"
          borderWidth="2px"
        />
        <Input
          id="password"
          placeholder="**********"
          type="password"
          variant="filled"
          mb={6}
          borderColor="white"
          borderWidth="2px"
        />
        <Button colorScheme="teal" onClick={handleLogin} mb={8}>
          Log In
        </Button>
        <Box>
          New to us?{' '}
          <Link as={RouteLink} to="/register" color="teal.500" href="#">
            Register
          </Link>
        </Box>
      </Flex>
    </Flex>
  );
};
