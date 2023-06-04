import { ColorModeScript } from "@chakra-ui/react"
import * as React from "react"
import * as ReactDOM from "react-dom/client"
import { ChakraProvider } from "@chakra-ui/react";
import {
  createBrowserRouter,
  RouterProvider,
  Navigate,
} from "react-router-dom";
import { RegisterPage } from "./pages/RegisterPage";
import { LoginPage } from "./pages/LoginPage"
import { HomePage } from "./pages/HomePage";
import { AddAccountPage } from "./pages/AddAccount";


const loggedIn = false;



const router = createBrowserRouter([
  {
    path: "/",
    element: localStorage.getItem("userId") ? <Navigate to="/home"/> : <Navigate to="/login" />,
  },
  {
    path: "/login",
    element: <LoginPage />
  },
  {
    path: "/register",
    element: <RegisterPage />
  },
  {
    path: "/home",
    element: <HomePage />
  },
  {
    "path": "/add-account",
    element: <AddAccountPage />
  }
]);




const container = document.getElementById("root")
if (!container) throw new Error('Failed to find the root element');
const root = ReactDOM.createRoot(container)


root.render(
  <React.StrictMode>
    <ColorModeScript />
    <ChakraProvider>
      <RouterProvider router={router} />
    </ChakraProvider>
  </React.StrictMode>,
)
