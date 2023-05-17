import { Grid, GridItem } from "@chakra-ui/layout"
import { TableTuples } from "./TableTuples"
import { TableList } from "./TableList"
import { TableCRUD } from "./TableCRUD"
import { Heading } from '@chakra-ui/react'


export const Home = (props: any) => (
    <Grid
        minHeight="25vh"
        maxHeight="50vh"
        templateRows='repeat(9, 1fr)'
        templateColumns='repeat(5, 1fr)'
        gap={4}
    >
        <GridItem rowSpan={9} colSpan={1}>
            <TableList />
        </GridItem>
        <GridItem rowSpan={1} colSpan={4}>
            <Heading sx={{ textAlign: 'left' }} as='h3' size='xl'>Selected: Table</Heading>
        </GridItem>
        <GridItem rowSpan={8} colSpan={3} overflow="scroll auto" ><TableTuples /></GridItem>
        <GridItem border="border:1px solid black;" rowSpan={8} colSpan={1}><TableCRUD /></GridItem>

    </Grid>
)
/*
<Grid
  h='200px'
  templateRows='repeat(9, 1fr)'
  templateColumns='repeat(5, 1fr)'
  gap={4}
>
  <GridItem rowSpan={9} colSpan={1} bg='tomato' />
  <GridItem rowSpan={1} colSpan={4} bg='yellow' />
  <GridItem rowSpan={8} colSpan={3} bg='papayawhip' />
  <GridItem rowSpan={8} colSpan={1} bg='blue' />

</Grid>
*/