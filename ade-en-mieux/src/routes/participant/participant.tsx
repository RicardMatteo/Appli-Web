import Cookies from "js-cookie";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Participant() {
  const navigate = useNavigate();

  // On vérifie que l'utilisateur est connecter avant d'afficher la page
  useEffect(() => {
    if (Cookies.get("authToken") === undefined) {
      navigate("/login");
    }
  }, [navigate]); // Utilisation d'un tableau vide pour exécuter useEffect une seule fois après le rendu initial

  return (
    <div>
      <h1>Participant</h1>
    </div>
  );
}

export default Participant;
