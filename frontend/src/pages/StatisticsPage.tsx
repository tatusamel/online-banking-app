import React, { useState, useEffect } from 'react';
import { Box, Heading, FormControl, Select, FormLabel, Button, useToast } from '@chakra-ui/react';
import axios from 'axios';

export const StatisticsPage = () => {
    const [selectedOption, setSelectedOption] = useState('');
    const [pageContent, setPageContent] = useState(null);
    const [branches, setBranches] = useState([]);
    const [customers, setCustomers] = useState([]);
    const [customerId, setCustomerId] = useState('');
    const [branchId, setBranchId] = useState('');
    const [accountType, setAccountType] = useState('');
    const toast = useToast();

    const userId = localStorage.getItem('userId');

    useEffect(() => {
        const fetchBranches = async () => {
            try {
                const response = await axios.get('http://localhost:8080/branches');
                if (response.data.length === 0) {
                    toast({
                        title: 'There are no branches!',
                        duration: 3000,
                        isClosable: true,
                    });
                }
                setBranches(response.data);
            } catch (error) {
                console.error(error);
            }
        };

        const fetchCustomers = async () => {
            try {
                const response = await axios.get('http://localhost:8080/customers');
                if (response.data.length === 0) {
                    toast({
                        title: 'There are no customers!',
                        duration: 3000,
                        isClosable: true,
                    });
                }
                setCustomers(response.data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchBranches();
        fetchCustomers();
    }, []);

    const handleOptionChange = (event: any) => {
        setSelectedOption(event.target.value);
        setPageContent(null);
    };

    const handleRetrieveClick = async () => {
        if (selectedOption === '') {
            toast({
                title: 'Please select an option.',
                duration: 3000,
                isClosable: true,
            });
            return;
        }

        if (selectedOption) {
            try {
                let endpoint = `http://localhost:8080/${selectedOption}`;

                if (selectedOption.includes('{customerId}')) {
                    endpoint = endpoint.replaceAll('{customerId}', customerId);
                }
                if (selectedOption.includes('{branchId}')) {
                    endpoint = endpoint.replaceAll('{branchId}', branchId);
                }
                if (selectedOption.includes('{accountType}')) {
                    endpoint = endpoint.replaceAll('{accountType}', accountType.replaceAll(" ", "_").toUpperCase());
                }

                const response = await axios.get(endpoint);
                setPageContent(response.data);
            } catch (error) {
                console.error(error);
                setPageContent(null);
            }
        } else {
            setPageContent(null);
        }
    };
    const renderPage = () => {
        if (selectedOption === '') {
            return <div>Please select an option.</div>;
        }

        if (selectedOption.includes("{customerId}") && customerId === '') {
            return <div>Please select a customer ID.</div>;
        }

        if (selectedOption.includes("{branchId}") && branchId === '') {
            return <div>Please select a branch.</div>;
        }

        if (selectedOption.includes("{accountType}") && accountType === '') {
            return <div>Please select an account type.</div>;
        }
        switch (selectedOption) {
            case 'accounts/total-balance':
                return <TotalBalanceOfAllAccountsPage content={pageContent} />;
            case 'accounts/total-balance/{customerId}':
                return <TotalBalanceOfCustomerAccountsPage content={pageContent} />;
            case 'branches/{branchId}/total-money':
                return <TotalMoneyInBranchPage content={pageContent} />;
            case 'accounts/average-balance/{accountType}':
                return <AverageBalancePerAccountTypePage content={pageContent} />;
            case 'customers/{customerId}/transactions/top10':
                return <RecentTransactionsByCustomerPage content={pageContent} />;
            case 'customers/number-of-customers':
                return <NumberOfCustomersPage content={pageContent} />;
            case 'accounts/number-of-accounts/{accountType}':
                return <NumberOfAccountsByTypePage content={pageContent} />;
            case 'accounts/max-balance':
                return <AccountWithMaximumBalancePage content={pageContent} />;
            case 'saving-accounts/min-balance':
                return <MinimumBalanceInSavingsAccountPage content={pageContent} />;
            default:
                return null;
        }
    };

    return (
        <Box p={8} maxWidth={500} mx="auto">
            <Heading size="m" mb={4}>
                Statistics Page
            </Heading>
            <FormControl id="option" mb={4}>
                <Select value={selectedOption} onChange={handleOptionChange}>
                    <option value="">Select an option</option>
                    <option value="accounts/total-balance">Find total balance of all accounts</option>
                    <option value="accounts/total-balance/{customerId}">
                        Find total balance of the accounts that a customer has
                    </option>
                    <option value="branches/{branchId}/total-money">Find total money of the accounts in a branch</option>
                    <option value="accounts/average-balance/{accountType}">Find average balance per account type</option>
                    <option value="customers/{customerId}/transactions/top10">
                        Find 10 most recent transactions made by a customer
                    </option>
                    <option value="customers/number-of-customers">Find number of customers</option>
                    <option value="accounts/number-of-accounts/{accountType}">Find number of accounts of one type</option>
                    <option value="accounts/max-balance">Find the balance in the account with the maximum balance</option>
                    <option value="saving-accounts/min-balance">Find minimum balance in all savings accounts</option>
                </Select>
            </FormControl>
            {selectedOption.includes("{customerId}") && (
                <FormControl id="customerId">
                    <FormLabel>Customer Id</FormLabel>
                    <Select value={customerId} onChange={(e) => {
                        setCustomerId(e.target.value);
                        setPageContent(null);
                    }}>
                        <option value="">Select an option</option>
                        {customers.map(({ id }) => (
                            <option key={id} value={id}>
                                {id}
                            </option>
                        ))}
                    </Select>
                </FormControl>
            )}
            {selectedOption.includes("{branchId}") && (
                <FormControl id="branch" mb={4}>
                    <FormLabel>Branch</FormLabel>
                    <Select value={branchId} onChange={(e) => {
                        setBranchId(e.target.value);
                        setPageContent(null);
                    }}>
                        <option value="">Select an option</option>
                        {branches.map(({ id, name }) => (
                            <option key={id} value={id}>
                                {name}
                            </option>
                        ))}
                    </Select>
                </FormControl>
            )}
            {selectedOption.includes("{accountType}") && (
                <FormControl id="accountType" mb={4}>
                    <FormLabel>Account Type</FormLabel>
                    <Select value={accountType} onChange={(e) => {
                        setAccountType(e.target.value);
                        setPageContent(null);
                    }}>
                        <option value="">Select an option</option>
                        <option value="Checking Account">Checking Account</option>
                        <option value="Saving Account">Savings Account</option>
                        <option value="Credit Card Account">Credit Card Account</option>
                    </Select>
                </FormControl>
            )}
            <Button colorScheme="blue" onClick={handleRetrieveClick} disabled={!selectedOption}>
                Retrieve
            </Button>
            {renderPage()}
        </Box>
    );
};

// Example pages for each option
const TotalBalanceOfAllAccountsPage = ({ content }: { content: any }) => (
    <div>Total Balance of All Accounts: {content}</div>
);
const TotalBalanceOfCustomerAccountsPage = ({ content }: { content: any }) => (
    <div>Total Balance of Customer Accounts: {content}</div>
);
const TotalMoneyInBranchPage = ({ content }: { content: any }) => <div>Total Money in Branch: {content}</div>;
const AverageBalancePerAccountTypePage = ({ content }: { content: any }) => (
    <div>Average Balance per Account Type: {content}</div>
);
const RecentTransactionsByCustomerPage = ({ content }: { content: any }) => {
    if (!content || !Array.isArray(content) || content.length === 0) {
      return <div>Select a Customer.</div>;
    }
  
    return (
      <div>
        <div>Recent Transactions by Customer:</div>
        {content.map((transaction: any) => (
          <div key={transaction.id}>
            <br />
            <div>ID: {transaction.id}</div>
            <div>Amount: {transaction.amount}</div>
            <div>Transaction Date: {transaction.transactionDate}</div>
            <div>From Account ID: {transaction.fromAccountId}</div>
            <div>To Account ID: {transaction.toAccountId}</div>
            <div>Transaction Type: {transaction.transactionType}</div>
          </div>
        ))}
      </div>
    );
  };
const NumberOfCustomersPage = ({ content }: { content: any }) => <div>Number of Customers: {content}</div>;
const NumberOfAccountsByTypePage = ({ content }: { content: any }) => (
    <div>Number of Accounts by Type: {content}</div>
);
const AccountWithMaximumBalancePage = ({ content }: { content: any }) => (
    <div>Account with Maximum Balance: {content}</div>
);
const MinimumBalanceInSavingsAccountPage = ({ content }: { content: any }) => (
    <div>Minimum Balance in Savings Account: {content}</div>
);
