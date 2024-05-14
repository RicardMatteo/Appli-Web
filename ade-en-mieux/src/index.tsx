// React base imports
import * as React from "react";
import * as ReactDOM from "react-dom/client";
// React router imports
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
// CSS imports
import "./index.css";
// Routes imports
import Root from "./routes/root";
import Tp3 from "./routes/tp3/tp3";
/*
import App from "./App";
import reportWebVitals from "./reportWebVitals";
*/

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
  }
]);

ReactDOM.createRoot(container).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

/*

const rootElement = document.getElementById("root");
if (rootElement) {
  const root = ReactDOM.createRoot(rootElement);
  root.render(
    <React.StrictMode>
      <App />
    </React.StrictMode>
  );
}

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

*/
