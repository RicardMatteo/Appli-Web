import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { useEffect } from "react";
import "./dashboard.scss";

const HeroPattern = ({
  pttrn,
  children,
}: {
  pttrn: string;
  children: React.ReactNode;
}) => <div className={pttrn}>{children}</div>;

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
      <HeroPattern pttrn={"topography-pattern"}>
        <div></div>
      </HeroPattern>
      <div className="topbar">
        <h1>Dashboard</h1>
      </div>
      <div className="box">
        <div className="container">
          <div>
            <h1>Organiser</h1>
            <p>Ici vous pourrez gérer vos évènements et vos groupes</p>
          </div>

          <div className="padding">
            <button
              className="button-64"
              onClick={() => navigate("/organiser")}
            >
              <span className="text">Organiser</span>
            </button>
          </div>

          <div className="padding">
            <button className="button-64" onClick={() => navigate("/events")}>
              <span className="text">Events</span>
            </button>
          </div>

          <div className="padding">
            <button
              className="button-64"
              onClick={() => navigate("/events/invitedevents")}
            >
              <span className="text">Invited Events</span>
            </button>
          </div>
        </div>
        <div className="container">
          <div>
            <h1>Agenda</h1>
            <p>Ici vous pourrez voir vos évènements</p>
          </div>

          <div className="padding">
            <button className="button-64" onClick={() => navigate("/agenda")}>
              <span className="text">Agenda</span>
            </button>
          </div>
        </div>
        <div className="container">
          <div>
            <h1>Participant</h1>
            <p>
              Ici vous pourrez consulter les évènements auquels vous avez été
              invité, accepter les invitations ou choisir un créneau de passage
            </p>
          </div>

          <div className="padding">
            <button
              className="button-64"
              onClick={() => navigate("/participant")}
            >
              <span className="text">Participer</span>
            </button>
          </div>
        </div>
      </div>
    </>
  );
}

export default Dashboard;
