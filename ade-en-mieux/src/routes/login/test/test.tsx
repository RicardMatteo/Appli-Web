import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { invokePost } from "../../../include/requests";

function Test() {
  const [username, setUsername] = useState(""); // Utilisation de useState pour gérer l'état de username
  const navigate = useNavigate();

  useEffect(() => {
    // Utilisation de useEffect pour exécuter une action après le rendu
    // check if cookie auth token is present
    // if not, redirect to login
    if (Cookies.get("authToken") === undefined) {
      navigate("/login");
    } else {
      invokePost(
        "getuser",
        {
          cookie: Cookies.get("authToken"),
        },
        "User get success",
        "User get failed"
      )
        .then((response: Response) => {
          const username_header = response.headers.get("username");
          if (username_header === null) {
            console.error("Username is null");
          } else {
            setUsername(username_header); // Mettre à jour l'état de username avec le nouveau username
          }
        })
        .catch((error: Error) => {
          // Gérer l'erreur ici
          console.error("Error:", error);
        });
    }
  }, [navigate]); // Utilisation d'un tableau vide pour exécuter useEffect une seule fois après le rendu initial

  console.log("Username : ", username);

  return (
    <>
      <h1>Test</h1>
      <p>Vous êtes connecté et votre username est : {username}</p>
    </>
  );
}

export default Test;
