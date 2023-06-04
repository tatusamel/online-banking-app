import React, { useEffect, useState } from 'react';
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
import axios from 'axios';

interface UserAccount {
    id: number;
    accountNumber: string;
    accountType: string;
    balance: number;
    branchName: string;
  }

export const HomePage = () => {
  const formBackground = useColorModeValue('gray.100', 'gray.700');
  const toast = useToast();
  const navigate = useNavigate();
  const [userAccounts, setUserAccounts] = useState<UserAccount[]>([]);
  const [activeTabIndex, setActiveTabIndex] = useState(0);

  const handleAccountSettings = (accountId:any) => {};

  useEffect(() => {
    fetchAccounts();
  }, []);

  const fetchAccounts = async () => {
    const accountEndpoints = [
      'checking-accounts',
      'saving-accounts',
      'credit-card-accounts',
    ];

    try {
      const accounts = await Promise.all(
        accountEndpoints.map(async (accountEndpoint) => {
          const response = await axios.get(`http://localhost:8080/${accountEndpoint}`);
          return response.data;
        })
      );
      const branch_response = await axios.get('http://localhost:8080/branches');
      const branches = branch_response.data;
      const branches_dict: { [id: number]: string } = branches.reduce((acc: { [id: number]: string }, item: any) => {
        acc[item.id] = item.name;
        return acc;
      }, {});

      const mergedAccounts = accounts.flat().map(
        (account) => {
            return {
                ...account,
                branchName: branches_dict[account.branchId],
            }
        }
      );
      setUserAccounts(mergedAccounts);
    } catch (error) {
      console.error(error);
      toast({
        title: 'An error occurred when fetching accounts.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    }
  };

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
        width="100vw"
        maxW="60vw"
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
            <Box p={8}>
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
                {userAccounts.map(({id, accountNumber, accountType, balance, branchName}) => (
                  <Card key={id} p={4}>
                    <CardHeader>
                      <Text>Account Number: {accountNumber}</Text>
                      <Badge colorScheme="teal">{accountType}</Badge>
                    </CardHeader>
                    <CardBody>
                      <Text>Balance: {balance}</Text>
                      <Text>Branch: {branchName}</Text>
                      <Button
                        size="sm"
                        colorScheme="teal"
                        onClick={() => handleAccountSettings(id)}
                      >
                        Account Settings
                      </Button>
                    </CardBody>
                  </Card>
                ))}
              </Stack>
            </Box>
          )}
          {activeTabIndex === 1 && <BranchesPage />}
        </Tabs>
      </Box>
    </Flex>
  );
};
