import {
  Flex,
  Heading,
  Input,
  Button,
  Link,
  Box,
  useColorModeValue,
} from '@chakra-ui/react';
import { Link as RouteLink } from 'react-router-dom';

export const LoginPage = () => {
  const formBackground = useColorModeValue('gray.100', 'gray.700');

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
          placeholder="johndoe@gmail.com"
          type="email"
          variant="filled"
          mb={3}
          borderColor="white"
          borderWidth="2px"
        />
        <Input
          placeholder="**********"
          type="password"
          variant="filled"
          mb={6}
          borderColor="white"
          borderWidth="2px"
        />
        <Button colorScheme="teal" mb={8}>
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
