/* eslint-disable jsx-a11y/anchor-is-valid */
import Cookies from "js-cookie";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { invokeGetWithCookie } from "../../include/getwithcookie";
import React, { useState } from "react";
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
  startDate: Date;
  endDate: Date;
  capacity: number;
};

const transformToSlot = (original: {
  eventName: string;
  startDate: number;
  endDate: number;
  location: string;
}): Slot => {
  return {
    name: original.eventName,
    startDate: new Date(original.startDate),
    endDate: new Date(original.endDate),
    capacity: 100, // Par exemple, en définissant une capacité par défaut
  };
};

// Variables pour la navigation de la semaine
let currentDate = new Date();

// il faut rentrer les slots ici !!
let slots: Slot[] = [];

function Agenda() {
  const navigate = useNavigate();

  // On vérifie que l'utilisateur est connecter avant d'afficher la page
  useEffect(() => {
    if (Cookies.get("authToken") === undefined) {
      navigate("/login");
    } else {
      // on recupère tt les slots du boug
      invokeGetWithCookie(
        "getuserslots",
        "Slots de l'utilisateur récupéré",
        "Erreur recupération des Slots user"
      ).then((resultat) => {
        if (Array.isArray(resultat)) {
          resultat.forEach((res) => slots.push(transformToSlot(res)));
          currentDate.setDate(currentDate.getDate());
          updateWeekDisplay();
        }
      });
    }
    init_display();
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
            <li>
              <a href="/dashboard">Accueil</a>
            </li>
            <li>
              <a id="add-event-button">Ajouter une activité</a>
            </li>
            <li>
              <a href="#">Paramètres</a>
            </li>
            <li>
              <a href="#">Aide</a>
            </li>
          </ul>
        </div>
        <div className="main-content">
          <div className="agenda">
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
          </div>
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
                <ul>
                  <label htmlFor="event-name">Nom de l'événement:</label>
                  <input
                    type="text"
                    id="event-name"
                    name="event-name"
                    required
                  />
                </ul>
                <ul>
                  <label htmlFor="event-date">Date:</label>
                  <DateInputComponent />
                </ul>

                <ul>
                  <label htmlFor="start-time">Heure de début:</label>
                  <input
                    type="time"
                    id="start-time"
                    name="start-time"
                    required
                  />

                  <label htmlFor="end-time">Heure de fin:</label>
                  <input type="time" id="end-time" name="end-time" required />

                  <button type="submit">Ajouter</button>
                </ul>
              </form>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Agenda;

function n(num: number, len = 2) {
  return `${num}`.padStart(len, "0");
}

function updateWeekDisplay() {
  //reset

  for (let i = 0; i < 7; i++) {
    const column = document.querySelector(`.column[data-day="${i}"]`);
    if (column != null) {
      while (column.firstChild) {
        column.removeChild(column.firstChild);
      }
      for (let h = 8; h < 20; h++) {
        const div = document.createElement("div");

        // Ajoute la classe 'time-slot'
        div.className = "time-slot";

        // Ajoute l'attribut data-hour avec la valeur h
        div.setAttribute("data-hour", String(h));
        column.appendChild(div);
      }
    }
  }
  let hours = document.querySelector(`.hours-column`);
  if (hours != null) {
    while (hours.firstChild) {
      hours.removeChild(hours.firstChild);
    }
    for (let h: number = 8; h <= 20; h++) {
      const div = document.createElement("div");
      div.className = "hour";
      div.setAttribute("hour", String(h));
      div.textContent = h + ":00";

      hours.appendChild(div);
    }
  }
  const startOfWeek = new Date(
    currentDate.getFullYear(),
    currentDate.getMonth(),
    currentDate.getDate()
  );
  startOfWeek.setDate(startOfWeek.getDate() - startOfWeek.getDay() + 1); // Lundi

  const endOfWeek = new Date(startOfWeek);
  endOfWeek.setDate(endOfWeek.getDate() + 6); // Dimanche

  let doc = document.getElementById("current-week");
  if (doc != null) {
    doc.textContent = `Semaine du ${startOfWeek.toLocaleDateString("fr-FR")} au ${endOfWeek.toLocaleDateString("fr-FR")}`;
  }

  slots.forEach((slot: Slot) => {
    if (slot.endDate > startOfWeek && slot.startDate < endOfWeek) {
      //console.log("semaine du slot" );
      //on veux afficher pour tt les jours

      const column = document.querySelector(
        `.column[data-day="${Math.round(slot.startDate.getDate() - startOfWeek.getDate())}"]`
      );
      if (column) {
        let dayStart = 8;
        let dayEnd = 20;
        let slotStart =
          slot.startDate.getHours() + slot.startDate.getMinutes() / 60;
        let slotEnd = slot.endDate.getHours() + slot.endDate.getMinutes() / 60;
        let top = ((slotStart - dayStart) * 100) / (dayEnd - dayStart) + "%";
        let percent = ((slotEnd - slotStart) * 100) / (dayEnd - dayStart) + "%";

        const activityDiv = document.createElement("div");
        activityDiv.className = "activity";
        activityDiv.textContent = `${slot.name} \n${slot.startDate.getHours()}:${n(slot.startDate.getMinutes())} - ${slot.endDate.getHours()}:${n(slot.endDate.getMinutes())}`;
        activityDiv.style.top = top;
        activityDiv.style.height = percent; // Adjust this to place the event correctly

        column.appendChild(activityDiv);
      }
    }
  });
}

function init_display() {
  if (currentDate != null) {
    let buttonprev = document.getElementById("previous-week");
    if (buttonprev != null)
      buttonprev.addEventListener("click", () => {
        currentDate.setDate(currentDate.getDate() - 7);
        updateWeekDisplay();
      });

    let buttonnext = document.getElementById("next-week");
    if (buttonnext != null) {
      buttonnext.addEventListener("click", () => {
        currentDate.setDate(currentDate.getDate() + 7);
        updateWeekDisplay();
      });
    }
  }
  const modal = document.getElementById("event-modal");
  const addEventButton = document.getElementById("add-event-button");
  const closeButton = document.querySelector(".close-button");

  if (addEventButton != null && modal != null && closeButton != null) {
    addEventButton.addEventListener("click", () => {
      modal.style.display = "block";
    });

    closeButton.addEventListener("click", () => {
      modal.style.display = "none";
    });

    window.addEventListener("click", (event) => {
      if (event.target === modal) {
        modal.style.display = "none";
      }
    });
  }
  updateWeekDisplay();
}

const DateInputComponent = () => {
  // Déclaration de l'état pour stocker la valeur de l'input

  const [datee, setDate] = useState("");
  // Fonction pour gérer le changement de valeur de l'input
  const handleDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDate(event.target.value);
  };

  return (
    <input
      type="date"
      id="event-date"
      value={datee}
      onChange={handleDateChange}
    />
  );
};
