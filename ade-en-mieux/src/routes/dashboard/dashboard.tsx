import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { useEffect } from "react";

function Dashboard() {
  const navigate = useNavigate();

  // On vérifie que l'utilisateur est connecter avant d'afficher la page
  useEffect(() => {
    if (Cookies.get("authToken") === undefined) {
      navigate("/login");
    }
  }, [navigate]); // Utilisation d'un tableau vide pour exécuter useEffect une seule fois après le rendu initial

  return (
    <>
      <div>
        <h1>Dashboard</h1>
        <p>Welcome to your dashboard</p>
      </div>

      <div className="button-container">
        <button onClick={() => navigate("/agenda")}>Agenda</button>
        <button onClick={() => navigate("/organiser")}>Orginise</button>
        <button onClick={() => navigate("/participant")}>Participate</button>
      </div>
    </>
  );
}

export default Dashboard;
