// React base imports
import * as React from "react";
import * as ReactDOM from "react-dom/client";
// React router imports
import { createBrowserRouter, RouterProvider } from "react-router-dom";
// CSS imports
import "./index.scss";
// Routes imports
import Root from "./routes/root";
import Login from "./routes/login/login";
import Test from "./routes/login/test/test";
import Register from "./routes/register/register";
import Dashboard from "./routes/dashboard/dashboard";
import Agenda from "./routes/agenda/agenda";
import Organiser from "./routes/organiser/organiser";
import Participant from "./routes/participant/participant";
import Events from "./routes/events/events";
import ReplyToInvite from "./routes/events/replytoinvite/replytoinvite";
import WrongEventId from "./routes/events/wrongeventid/wrongeventid";
import InvitedEvents from "./routes/events/invitedevents/invitedevents";
import Places from "./routes/places/places";
import RecapEvents from "./routes/recapevents/recapevents";

// Create Router and render App

const rootElement = document.getElementById("root");
const container = rootElement || document.createElement("div");

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/register",
    element: <Register />,
  },
  {
    path: "/login/test",
    element: <Test />,
  },
  {
    path: "/dashboard",
    element: <Dashboard />,
  },
  {
    path: "/agenda",
    element: <Agenda />,
  },
  {
    path: "/organiser",
    element: <Organiser />,
  },
  {
    path: "/participant",
    element: <Participant />,
  },
  {
    path: "/events",
    element: <Events />,
  },
  {
    path: "/events/replytoinvite/:eventId",
    element: <ReplyToInvite />,
  },
  {
    path: "/events/wrongeventid",
    element: <WrongEventId />,
  },
  {
    path: "/events/invitedevents",
    element: <InvitedEvents />,
  },
  {
    path: "/places",
    element: <Places />,
  },
  {
    path: "/recapevents",
    element: <RecapEvents />,
  },
]);

ReactDOM.createRoot(container).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
