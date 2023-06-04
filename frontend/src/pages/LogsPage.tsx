import React, { useState, useEffect } from 'react';
import {
  Box,
  Heading,
  Table,
  Thead,
  Tbody,
  Button,
  Tr,
  Th,
  Td,
  useToast,
  Flex,
  Input,
} from '@chakra-ui/react';
import axios from 'axios';

interface Log {
    id: number;
    userId: string;
    action: number;
    timestamp: string;
  }

export const LogsPage = () => {
    const [logs, setLogs] = useState<Log[]>([]); // Add type annotation for logs
    const [filteredLogs, setFilteredLogs] = useState<Log[]>([]); // Add type annotation for filteredLogs
    const [userIdFilter, setUserIdFilter] = useState('');
    const toast = useToast();
  
    useEffect(() => {
      fetchLogs();
    }, []);
  
    const fetchLogs = async () => {
      try {
        const response = await axios.get('http://localhost:8080/actions');
        setLogs(response.data);
        setFilteredLogs(response.data);
      } catch (error) {
        console.error(error);
        toast({
          title: 'An error occurred when fetching logs.',
          status: 'error',
          duration: 3000,
          isClosable: true,
        });
      }
    };
  
    const handleFilterLogs = () => {
      const filtered = logs.filter((log) => {
        return log.userId.toString().includes(userIdFilter);
      });
      setFilteredLogs(filtered);
    };

  return (
    <Box p={8}>
      <Flex justify="space-between" align="center" mb={4}>
        <Heading size="lg">Logs</Heading>
        <Flex>
          <Input
            placeholder="User ID"
            value={userIdFilter}
            onChange={(e) => setUserIdFilter(e.target.value)}
            marginRight={2}
          />
          <Button colorScheme="teal" onClick={handleFilterLogs}>
            Filter
          </Button>
        </Flex>
      </Flex>
      <Table variant="simple">
        <Thead>
          <Tr>
            <Th>User ID</Th>
            <Th>Action</Th>
            <Th>Timestamp</Th>
          </Tr>
        </Thead>
        <Tbody>
          {filteredLogs.map((log) => (
            <Tr key={log.id}>
              <Td>{log.userId}</Td>
              <Td>{log.action}</Td>
              <Td>{new Date(log.timestamp).toLocaleString("en-US", {
                    hour12: false,
                    hour: "numeric",
                    minute: "numeric",
                    second: "numeric",
                    day: "numeric",
                    month: "long",
                    year: "numeric",
                    })
            }</Td>
            </Tr>
          ))}
        </Tbody>
      </Table>
    </Box>
  );
};
