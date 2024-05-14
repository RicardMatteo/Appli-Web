import "./App.css";
import { useState, useEffect } from "react";
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Link, Route } from 'react-router-dom';
import login from './login/login';
import { createRoot } from "react-dom/client";

var init = false;

function ShowMessage(message: string): void {
  const container = document.getElementById("Message");
  const root = createRoot(container!);
  root.render(<p>{message}</p>);
}

function CleanWorker(): void {
  const container = document.getElementById("Worker");
  const root = createRoot(container!);
  root.render([]);
}

async function invokePost(
  method: string,
  data: any,
  successMsg: string,
  failureMsg: string
): Promise<void> {
  const requestOptions: RequestInit = {
    method: "POST",
    headers: { "Content-Type": "application/json; charset=utf-8" },
    body: JSON.stringify(data),
  };

  try {
    const res = await fetch("/ADEenMieux/rest/" + method, requestOptions);
    if (res.ok) {
      ShowMessage(successMsg);
    } else {
      ShowMessage(failureMsg);
    }
  } catch (error) {
    console.error("Error in invokePost :", error);
  }
}

async function invokeGet(
  method: string,
  failureMsg: string
): Promise<any | null> {
  try {
    const res = await fetch("/ADEenMieux/rest/" + method);
    if (res.ok) {
      return await res.json();
    } else {
      ShowMessage(failureMsg);
      return null;
    }
  } catch (error) {
    console.error("Error in invokeGet :", error);
    return null;
  }
}

const AddPerson: React.FC = () => {
  const [fname, setFname] = useState<string>("");
  const [lname, setLname] = useState<string>("");

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>): void => {
    event.preventDefault();
    let person: { firstName: string; lastName: string } = {
      firstName: fname,
      lastName: lname,
    };
    invokePost("addperson", person, "person added", "pb with addperson");
    CleanWorker();
  };

  if (init) {
    init = false;
    setFname("");
    setLname("");
  }

  return (
    <>
      <form onSubmit={handleSubmit}>
        First Name:{" "}
        <input
          type="text"
          value={fname}
          onChange={(e) => setFname(e.target.value)}
        />
        <br />
        Last Name:{" "}
        <input
          type="text"
          value={lname}
          onChange={(e) => setLname(e.target.value)}
        />
        <br />
        <br />
        <input type="submit" value="OK" />
      </form>
    </>
  );
};

function AddAddress() {
  const [street, setStreet] = useState("");
  const [city, setCity] = useState("");

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>): void => {
    event.preventDefault();
    let address: { street: string; city: string } = {
      street: street,
      city: city,
    };
    invokePost("addaddress", address, "address added", "pb with addaddress");
    CleanWorker();
  };

  useEffect(() => {
    if (init) {
      setStreet("");
      setCity("");
      init = false;
    }
  }, []);

  return (
    <>
      <form onSubmit={handleSubmit}>
        Street:{" "}
        <input
          type="text"
          value={street}
          onChange={(e) => setStreet(e.target.value)}
        />
        <br />
        City:{" "}
        <input
          type="text"
          value={city}
          onChange={(e) => setCity(e.target.value)}
        />
        <br />
        <br />
        <input type="submit" value="OK" />
      </form>
    </>
  );
}

const Associate: React.FC = () => {
  const [listP, setListP] = useState<any[]>([]);
  const [listA, setListA] = useState<any[]>([]);
  const [selectedP, setSelectedP] = useState<number>();
  const [selectedA, setSelectedA] = useState<number>();
  type person = { id: number; firstName: string; lastName: string };
  type address = { id: number; street: string; city: string };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>): void => {
    event.preventDefault();
    let association: any = {
      secondId: selectedP,
      firstId: selectedA,
    };
    invokePost(
      "associate",
      association,
      "association added",
      "pb with associate"
    );
    CleanWorker();
  };

  if (init) {
    init = false;
    invokeGet("listpersons", "pb with listpersons").then((data) =>
      setListP(data)
    );
    invokeGet("listaddresses", "pb with listaddresses").then((data) =>
      setListA(data)
    );
  }

  return (
    <>
      <form onSubmit={handleSubmit}>
        Select a person:
        <br />
        {listP.map((p: person) => (
          <div key={p.id}>
            <input
              type="radio"
              value={p.id}
              checked={selectedP === p.id}
              onChange={(e) => setSelectedP(parseInt(e.target.value))}
            />
            {p.firstName} {p.lastName}
            <br />
          </div>
        ))}
        <br />
        Select an address:
        <br />
        {listA.map((a: address) => (
          <div key={a.id}>
            <input
              type="radio"
              value={a.id}
              checked={selectedA === a.id}
              onChange={(e) => setSelectedA(parseInt(e.target.value))}
            />
            {a.street} {a.city}
            <br />
          </div>
        ))}
        <br />
        <input type="submit" value="OK" />
      </form>
    </>
  );
};

function List() {
  const [list, setList] = useState<any[]>([]);

  useEffect(() => {
    if (init) {
      init = false;
      invokeGet("listpersons", "pb with listpersons").then((data) =>
        setList(data)
      );
    }
  }, []);

  // if null list is return, return a no association message
  if (list === null) {
    return (
      <>
        <h1>List of persons</h1>
        <p>No association</p>
        <br />
      </>
    );
  }

  return (
    <>
      <h1>List of persons</h1>
      <ul>
        {list.map((p: any) => (
          <li key={p.id}>
            {p.firstName} {p.lastName}
            <ul>
              {p.addresses.map((a: any) => (
                <li key={a.id}>
                  {a.street} {a.city}
                </li>
              ))}
            </ul>
          </li>
        ))}
      </ul>
      <br />
    </>
  );
}

function App() {
  const addPerson = () => {
    ShowMessage("");
    init = true;
    const container = document.getElementById("Worker");
    const root = createRoot(container!);
    root.render(<AddPerson />);
  };
  const addAddress = () => {
    ShowMessage("");
    init = true;
    const container = document.getElementById("Worker");
    const root = createRoot(container!);
    root.render(<AddAddress />);
  };
  const associate = () => {
    ShowMessage("");
    init = true;
    const container = document.getElementById("Worker");
    const root = createRoot(container!);
    root.render(<Associate />);
  };
  const list = () => {
    ShowMessage("");
    init = true;
    const container = document.getElementById("Worker");
    const root = createRoot(container!);
    root.render(<List />);
  };

  return (
    <>
      <div id="Main">
        <button onClick={addPerson}>Add personne</button>
        <button onClick={addAddress}>Add address</button>
        <button onClick={associate}>Associate</button>
        <button onClick={list}>List</button>
        <Link to="/login">Login</Link>
      </div>
      <br/>
      <div id="Message">
      </div>
      <br/>
      <div id ="Worker">
      </div>
        <div>
          <Router>
            <Route path="/login" Component={login} />
          </Router>
        </div>
      </>
      <br />
      <div id="Message"></div>
      <br />
      <div id="Worker"></div>
    </>
  );
}

export default App;
