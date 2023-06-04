import React, { useEffect, useState } from 'react';
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
} from '@chakra-ui/react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import { UpdateBranchPage } from './UpdateBranchPage';

export const BranchesPage = () => {
  const [branches, setBranches] = useState([]);
  const toast = useToast();
  const navigate = useNavigate();
  const [showUpdatePage, setShowUpdatePage] = useState(false);
  const [selectedBranchId, setSelectedBranchId] = useState('');

  useEffect(() => {
    fetchBranches();
  }, []);

  const fetchBranches = async () => {
    try {
      const response = await axios.get('http://localhost:8080/branches');
      setBranches(response.data);
    } catch (error) {
      console.error(error);
      toast({
        title: 'An error occurred when fetching branches.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    }
  };

  const handleUpdateBranch = (id: any) => {
    setSelectedBranchId(id);
    setShowUpdatePage(true);
  };

  const handleDeleteBranch = async (id: any) => {
    try {
      const response = await axios.delete(`http://localhost:8080/branches/delete/${id}`);
      if (response.status === 204) {
        toast({
          title: 'Branch deleted successfully.',
          status: 'success',
          duration: 3000,
          isClosable: true,
        });
        navigate('/');
      }
    } catch (error) {
      console.error(error);
      toast({
        title: 'An error occurred when deleting branch.',
        status: 'error',
        duration: 3000,
        isClosable: true,
      });
    }
  };

  const handleCreateBranch = () => {
    navigate('/add-branch');
  };

  return (
    <Box p={8}>
      <Flex justify="space-between" align="center" mb={4}>
        <Heading size="lg">Branches</Heading>
        <Button colorScheme="teal" onClick={handleCreateBranch}>
          Add Branch
        </Button>
      </Flex>
      {showUpdatePage ? (
        <UpdateBranchPage branchId={selectedBranchId} />
      ) : (
        <Table variant="simple">
          <Thead>
            <Tr>
              <Th>Branch ID</Th>
              <Th>Name</Th>
              <Th>Location</Th>
              <Th>Actions</Th>
            </Tr>
          </Thead>
          <Tbody>
            {branches.map(({ id, name, address, accountIds }) => (
              <Tr key={id}>
                <Td>{id}</Td>
                <Td>{name}</Td>
                <Td>{address}</Td>
                <Td>
                  <Button
                    size="sm"
                    colorScheme="teal"
                    onClick={() => handleUpdateBranch(id)}
                  >
                    Update
                  </Button>
                </Td>
                <Td>
                  <Button
                    size="sm"
                    colorScheme="red"
                    onClick={() => handleDeleteBranch(id)}
                  >
                    Delete
                  </Button>
                </Td>
              </Tr>
            ))}
          </Tbody>
        </Table>
      )}
    </Box>
  );
};
