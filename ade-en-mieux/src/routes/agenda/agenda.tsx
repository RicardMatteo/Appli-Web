import Cookies from "js-cookie";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./agenda.scss";

const HeroPattern = ({
  pttrn,
  children,
}: {
  pttrn: string;
  children: React.ReactNode;
}) => <div className={pttrn}>{children}</div>;


type Slot = {
  name: string;
  startDate: number;
  endDate: number;
  capacity: number;
};

// Variables pour la navigation de la semaine
let currentDate = new Date();

function Agenda() {
  const navigate = useNavigate();

  // On vérifie que l'utilisateur est connecter avant d'afficher la page
  useEffect(() => {
    
  }, [navigate]); // Utilisation d'un tableau vide pour exécuter useEffect une seule fois après le rendu initial

  return (
    <>
      <HeroPattern pttrn={"topography-pattern"}>
        <div></div>
      </HeroPattern>
      <div className="main-container">
        <div className="sidebar">
          <h2>Menu</h2>
          <ul>
              <li><a href="/dashboard">Accueil</a></li>
              <li><a id="add-event-button">Ajouter une activité</a></li>
              <li><a href="#">Paramètres</a></li>
              <li><a href="#">Aide</a></li>
          </ul>
        </div>
        <div className="main-content">
          <table className="agenda">
          <div className="header">
              <div className="hours-label"></div>
              <div className="day">Lundi</div>

              <div className="day">Mardi</div>
              <div className="day">Mercredi</div>
              <div className="day">Jeudi</div>
              <div className="day">Vendredi</div>
              <div className="day">Samedi</div>
              <div className="day">Dimanche</div>
            </div>
            <div className="body">
              <div className="hours-column"></div>
              <div className="column" data-day="0"></div>
              <div className="column" data-day="1"></div>
              <div className="column" data-day="2"></div>
              <div className="column" data-day="3"></div>
              <div className="column" data-day="4"></div>
              <div className="column" data-day="5"></div>
              <div className="column" data-day="6"></div>
            </div>
          </table>
          <div className="week-navigation">
            <button id="previous-week">Semaine précédente</button>
            <span id="current-week"></span>
            <button id="next-week">Semaine suivante</button>
          </div>
        
          <div id="event-modal" className="modal">
            <div className="modal-content">
              <span className="close-button">&times;</span>
              <h2>Ajouter un événement</h2>
              <form id="event-form">
                <label htmlFor="event-name">Nom de l'événement:</label>
                <input type="text" id="event-name" name="event-name" required />

                <label htmlFor="event-date">Date:</label>
                <input type="date" id="event-date" name="event-date" required />

                <label htmlFor="start-time">Heure de début:</label>
                <input type="time" id="start-time" name="start-time" required />

                <label htmlFor="end-time">Heure de fin:</label>
                <input type="time" id="end-time" name="end-time" required />

                <button type="submit">Ajouter</button>
              </form>
            </div>
          </div>
          
        </div>
      
        
      </div>
    </>
  );
}

export default Agenda;

