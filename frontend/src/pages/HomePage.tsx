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
} from '@chakra-ui/react';
import { Link as RouteLink, useNavigate } from 'react-router-dom';
import { AddIcon, SettingsIcon } from '@chakra-ui/icons';
import axios from 'axios';

export const HomePage = () => {
    const formBackground = useColorModeValue('gray.100', 'gray.700');
    const toast = useToast();
    const navigate = useNavigate();

    const handleAccountSettings = (account_id: number) => { };

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
            localStorage.removeItem("userId");
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
        navigate("/add-account")
    };

    // Sample list of user accounts
    const userAccounts = [
        {
            id: 1,
            accountNumber: '123456789',
            balance: 5000,
            accountType: 'Savings account',
        },
        {
            id: 2,
            accountNumber: '987654321',
            balance: 10000,
            accountType: 'Checking account',
        },
        {
            id: 3,
            accountNumber: '456789123',
            balance: 15000,
            accountType: 'Credit Card account',
        },
    ];

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
                    {userAccounts.map((account) => (
                        <Card key={account.id} p={4}>
                            <CardHeader>
                                <Text>Account Number: {account.accountNumber}</Text>
                                <Badge colorScheme="teal">{account.accountType}</Badge>
                            </CardHeader>
                            <CardBody>
                                <Text>Balance: {account.balance}</Text>
                                <Button
                                    size="sm"
                                    colorScheme="teal"
                                    onClick={() => handleAccountSettings(account.id)}
                                >
                                    Account Settings
                                </Button>
                            </CardBody>
                        </Card>
                    ))}
                </Stack>
            </Box>
        </Flex>
    );
};
