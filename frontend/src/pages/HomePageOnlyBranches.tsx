import React, { useState } from 'react';
import {
  Box,
  Flex,
  Heading,
  Button,
  IconButton,
  useColorModeValue,
  useToast,
  Stack,
  Text,
  Card,
  CardHeader,
  CardBody,
  Badge,
  Tabs,
  TabList,
  Tab,
} from '@chakra-ui/react';
import { Link as RouteLink, useNavigate } from 'react-router-dom';
import { AddIcon, SettingsIcon } from '@chakra-ui/icons';

import { BranchesPage } from './BranchesPage';

export const HomePage = () => {
  const formBackground = useColorModeValue('gray.100', 'gray.700');
  const toast = useToast();
  const navigate = useNavigate();
  const [activeTabIndex, setActiveTabIndex] = useState(0);

  const handleAccountSettings = (accountId: number) => {};

  const logoutToast = () => {
    toast({
      title: 'Success.',
      description: 'Logged out successfully.',
      status: 'success',
      duration: 9000,
      isClosable: true,
    });
  };

  const handleLogout = async () => {
    try {
      localStorage.removeItem('userId');
      logoutToast();
      navigate('/login'); // Redirect to login page after logout
    } catch (error: any) {
      toast({
        title: 'An error occurred when sending the request.',
        status: 'error',
        isClosable: true,
      });
    }
  };

  const handleSettings = () => {
    navigate('/user-settings'); // Redirect to user settings page
  };

  const handleAddAccount = () => {
    navigate('/add-account');
  };

  const handleBranchTabClick = () => {
    // Do nothing since we are already on the Branches tab
  };

  return (
    <Flex h="100vh" flexDirection="column" alignItems="center">
      <Box
        bg={formBackground}
        p={8}
        borderRadius={8}
        boxShadow="lg"
        width="80vw"
        maxW="900px"
      >
        <Flex justifyContent="space-between" alignItems="center" mb={8}>
          <Heading size="xl">Home Page</Heading>
          <Flex>
            <IconButton
              icon={<SettingsIcon />}
              aria-label="Account Settings"
              colorScheme="teal"
              variant="ghost"
              onClick={handleSettings}
              mr={2}
            />
            <Button
              colorScheme="teal"
              variant="ghost"
              onClick={handleLogout}
              ml={2}
            >
              Logout
            </Button>
          </Flex>
        </Flex>
        <Tabs index={activeTabIndex} onChange={setActiveTabIndex}>
          <TabList>
            <Tab>User Accounts</Tab>
            <Tab>Branches</Tab>
          </TabList>
          {activeTabIndex === 0 && (
            <Stack spacing={4}>
              <Flex justifyContent="space-between" alignItems="center">
                <Heading size="lg">User Accounts</Heading>
                <Button
                  leftIcon={<AddIcon />}
                  colorScheme="teal"
                  onClick={handleAddAccount}
                >
                  Add Account
                </Button>
              </Flex>
              {/* Render user accounts here */}
              {/* ... */}
            </Stack>
          )}
          {activeTabIndex === 1 && <BranchesPage />}
        </Tabs>
      </Box>
    </Flex>
  );
};
