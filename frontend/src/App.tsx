import {
  ChakraProvider,
  Box,
  Grid,
  theme,
  Container
} from "@chakra-ui/react"
import { ColorModeSwitcher } from "./ColorModeSwitcher"
import { Home } from "./Home";
import { LoginPage } from "./pages/LoginPage"


export const App = () => (
  <ChakraProvider theme={theme}>
    <Box textAlign="center" fontSize="xl">
      <Grid p={4}>
        <ColorModeSwitcher justifySelf="flex-end" />
        <Container centerContent><LoginPage /></Container>

      </Grid>
    </Box>
  </ChakraProvider>
)
