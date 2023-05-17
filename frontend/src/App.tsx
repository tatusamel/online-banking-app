import {
  ChakraProvider,
  Box,
  Grid,
  theme,
  Container
} from "@chakra-ui/react"
import { ColorModeSwitcher } from "./ColorModeSwitcher"
import { Home } from "./Home"

export const App = () => (
  <ChakraProvider theme={theme}>
    <Box textAlign="center" fontSize="xl">
      <Grid p={4}>
        <ColorModeSwitcher justifySelf="flex-end" />
        <Container maxW="75vw" maxH="50vh" centerContent><Home/></Container>
        
      </Grid>
    </Box>
  </ChakraProvider>
)
