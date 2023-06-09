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
import { AddAccountPage } from "./pages/AddAccountPage";
import { UpdateUserPage } from "./pages/UpdateUserPage";
import { AddBranchPage } from "./pages/AddBranchPage";
import { UpdateBranchPage } from "./pages/UpdateBranchPage";
import { LogsPage } from "./pages/LogsPage";
import { MoneyTransferPage } from "./pages/MoneyTransferPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: localStorage.getItem("userId") ? <Navigate to="/home" /> : <Navigate to="/login" />,
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
  },
  {
    "path": "/user-settings",
    element: <UpdateUserPage />
  },
  {
    "path": "/add-branch",
    element: <AddBranchPage />
  },
  {
    "path": "/update-branch",
    element: <UpdateBranchPage branchId="" />
  },
  {
    "path": "/logs",
    element: <LogsPage />
  },
  {
    "path": "/money-transfer",
    element: <MoneyTransferPage />
  }
]);




const container = document.getElementById("root")
if (!container) throw new Error('Failed to find the root element');
const root = ReactDOM.createRoot(container)


root.render(
  <>
    <ColorModeScript />
    <ChakraProvider>
      <RouterProvider router={router} />
    </ChakraProvider>
  </>
)
