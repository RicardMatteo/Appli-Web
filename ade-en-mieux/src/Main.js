import React from "react";
import { useState, useEffect } from "react";
import ReactDOM from 'react-dom';

var init=false;

async function invokePost(method, data, successMsg, failureMsg) {
   const requestOptions = {
        method: "POST",
        headers: { "Content-Type": "application/json; charset=utf-8" },
        body: JSON.stringify(data)
    };
    const res = await fetch("/ADEenMieux/rest/"+method,requestOptions);
    if (res.ok) ShowMessage(successMsg);
    else ShowMessage(failureMsg);
}

async function invokeGet(method, failureMsg) {

  const res = await fetch("/ADEenMieux/rest/"+method);
  if (res.ok) return await res.json();	
  ShowMessage(failureMsg);
  return null;
}        
        
function AddPerson() {

  const [fname, setFname] = useState("");
  const [lname, setLname] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();
    let person={};
    person.firstName=fname;
    person.lastName=lname;
    invokePost("addperson", person, "person added", "pb with addperson");
    CleanWorker();
  }

  if (init) {
  	init=false;
  	setFname("");
  	setLname("");
  }

  return (
  <>
    <form onSubmit={handleSubmit}>
      First Name: <input type="text" value={fname} 
    		onChange={(e) => setFname(e.target.value)}/><br/>
      Last Name:  <input type="text" value={lname}
    		onChange={(e) => setLname(e.target.value)}/><br/>
      <br/>
      <input type="submit" value="OK"/>
    </form>
  </>
  );
}

function AddAddress() {

  const [street, setStreet] = useState("");
  const [city, setCity] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();
    let address={};
    address.street=street;
    address.city=city;
    invokePost("addaddress", address, "address added", "pb with addaddress");
    CleanWorker();
  }
  
  if (init) {
  	init=false;
  	setStreet("");
  	setCity("");
  }

  return (
  <>
    <form onSubmit={handleSubmit}>
      Street: <input type="text" value={street} 
    		onChange={(e) => setStreet(e.target.value)}/><br/>
      City:  <input type="text" value={city}
    		onChange={(e) => setCity(e.target.value)}/><br/>
      <br/>
      <input type="submit" value="OK"/>
    </form>
  </>
  );
}

function Associate() {

  const [listP, setListP] = useState([]);
  const [listA, setListA] = useState([]);
  const [selectedP, setSelectedP] = useState("");
  const [selectedA, setSelectedA] = useState("");
  
  const handleSubmit = (event) => {
    event.preventDefault();
    let association={};
    association.personId=selectedP;
    association.addressId=selectedA;
    invokePost("associate", association, "association added", "pb with associate");
    CleanWorker();
  }
  
  if (init) {
  	init=false;
	invokeGet("listpersons", "pb with listpersons")
	.then(data => setListP(data));
	invokeGet("listaddresses", "pb with listaddresses")
	.then(data => setListA(data));
  }
  
  return (
  <>
    <form onSubmit={handleSubmit}>
    
    Select a person:<br/>
    {listP.map((p) => (
    	<>
	<input type="radio" value={p.id}
		checked={selectedP==p.id} 
		onChange={(e) => setSelectedP(e.target.value)}/>
	{p.firstName} {p.lastName}<br/>
	</>
    ))}
    <br/>
    Select an address:<br/>
    {listA.map((a) => (
    	<>
	<input type="radio" value={a.id}
		checked={selectedA==a.id} 
		onChange={(e) => setSelectedA(e.target.value)}/>
		{a.street} {a.city}<br/>
	</>
    ))}
    <br/>
    <input type="submit" value="OK"/>
    </form>
  </>
  );
}

function List() {

  const [list, setList] = useState([]);

  if (init) {
  	init=false;
	invokeGet("listpersons", "pb with listpersons")
	.then(data => setList(data));
  }
  	
  return (
  <>
    <h1>List of persons</h1>
      <ul>
     	 {list.map((p) => (

        <li>
        {p.firstName} {p.lastName}
        <ul>
        { p.addresses.map((a) => (
        	<li>
        	{a.street} {a.city}
        	</li>
        ))}
        </ul>
        </li>
      ))}

      </ul>
      <br/>
  </>
  );
}

function ShowMessage(message) {
    ReactDOM.render(<p>{message}</p>, document.getElementById("Message"));
}

function CleanWorker() {
    ReactDOM.render("", document.getElementById("Worker"));
}

function Main() {

  const addPerson = () => {
    ShowMessage("");
    init=true;
    ReactDOM.render(<AddPerson />, document.getElementById("Worker"));
  }
  const addAddress = () => {
    ShowMessage("");
    init=true;
    ReactDOM.render(<AddAddress />, document.getElementById("Worker"));
  }
  const associate = () => {
    ShowMessage("");
    init=true;
    ReactDOM.render(<Associate />, document.getElementById("Worker"));
  }
  const list = () => {
     ShowMessage("");
     init=true;
     ReactDOM.render(<List />, document.getElementById("Worker"));
  }
  
  return (	
  <>
    <div id="Main">
    	<button onClick={() => addPerson()}>Add personne</button>
    	<button onClick={() => addAddress()}>Add address</button>
    	<button onClick={() => associate()}>Associate</button>
    	<button onClick={() => list()}>List</button>
    </div>
    <br/>
    <div id="Message">
    </div>
    <br/>
    <div id ="Worker">
    </div>
  </>
    
  );
}

export default Main;
