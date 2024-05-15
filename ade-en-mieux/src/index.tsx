// React base imports
import * as React from "react";
import * as ReactDOM from "react-dom/client";
// React router imports
import { createBrowserRouter, RouterProvider } from "react-router-dom";
// CSS imports
import "./index.css";
// Routes imports
import Root from "./routes/root";
import Tp3 from "./routes/tp3/tp3";
import Login from "./routes/login/login";
import Register from "./routes/register/register";

// Create Router and render App

const rootElement = document.getElementById("root");
const container = rootElement || document.createElement("div");

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
  },
  {
    path: "/tp3",
    element: <Tp3 />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/register",
    element: <Register />,
  },
]);

ReactDOM.createRoot(container).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
