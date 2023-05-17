import {
    Table,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    TableContainer,
} from '@chakra-ui/react'

export const TableTuples = (props: any) => {
    let items = [];
    for (let index = 0; index < 100; index++) {
        items.push(

            <Tr>
                <Td>inches</Td>
                <Td>millimetres (mm)</Td>
                <Td isNumeric>25.4</Td>
                <Td>Dummy</Td>
                <Td>Dummy</Td>
                <Td>Dummy</Td>
                <Td>Dummy</Td>
                <Td>Dummy</Td>
            </Tr>
        )

    }

    const columns = ["To convert", "into", "multiply by", "Dummy", "Dummy2", "Dummy3", "Dummy4", "Dummy5"];
    const columnItems = columns.map(
        (column) => <Th>{column}</Th>
    );


    return (<TableContainer overflowY="unset" overflowX="unset">
        <Table colorScheme='teal'>
            <Thead>
                <Tr>
                    {columnItems}
                </Tr>
            </Thead>
            <Tbody>
                {items}
            </Tbody>

        </Table>
    </TableContainer>)

}