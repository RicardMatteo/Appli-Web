import Cookies from "js-cookie";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useFormik } from "formik";
import { invokeGetWithCookie } from "../../include/getwithcookie";
import "./events.scss";
import { invokePost } from "../../include/requests";

type User = {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  hashedPassword: string;
};

type Slot = {
  capacity: number;
  startDate: number;
  endDate: number;
};

const HeroPattern = ({
  pttrn,
  children,
}: {
  pttrn: string;
  children: React.ReactNode;
}) => <div className={pttrn}>{children}</div>;

function Events() {
  const navigate = useNavigate();
  const [listUser, setListUser] = useState<User[]>([]);
  const [listSlot, setListSlot] = useState<Slot[]>([]);
  //at start use invokeGetWithCookie to retrieve a list of all users and store it

  useEffect(() => {
    invokeGetWithCookie(
      "listusers",
      "Liste des utilisateurs récupérée",
      "Erreur lors de la récupération de la liste des utilisateurs"
    ).then((res) => {
      console.log(res);
      if (Array.isArray(res)) {
        setListUser(res);
      } else {
        console.error("Expected an array but received", res);
      }
    });
  }, []);

  // On vérifie que l'utilisateur est connecter avant d'afficher la page
  useEffect(() => {
    if (Cookies.get("authToken") === undefined) {
      navigate("/login");
    }
  }, [navigate]); // Utilisation d'un tableau vide pour exécuter useEffect une seule fois après le rendu initial

  const formik = useFormik({
    //i want multiple fields :
    // a EventName string field for the event
    // I then want to select multiple users from the list of users that will be participating in the event
    //and need to store the selected users in a list
    //nothing else
    initialValues: {
      eventName: "",
      selectedUsers: [],
    },
    onSubmit: (values) => {
      console.log("Submitted values - event name : ", values.eventName);
      console.log("Selected users : ", values.selectedUsers);
      // create the list of start dates
      const listStartDate = listSlot.map((s) => s.startDate);
      // create the list of end dates
      const listEndDate = listSlot.map((s) => s.endDate);
      // create the list of capacities
      const listCapacity = listSlot.map((s) => s.capacity);

      invokePost(
        "addevent",
        {
          eventName: values.eventName,
          participants: values.selectedUsers,
          startDates: listStartDate,
          endDates: listEndDate,
          capacities: listCapacity,
        },
        "Evènement ajouté",
        "Erreur lors de l'ajout de l'évènement"
      ).then((response: Response) => {
        if (response.ok) {
          navigate("/dashboard");
        }
      });
    },
  });

  const formikSlot = useFormik({
    initialValues: {
      startDate: "",
      endDate: "",
      capacity: "",
    },
    onSubmit: (values) => {
      console.log("Submitted values - start date : ", values.startDate);
      console.log("Submitted values - end date : ", values.endDate);
      console.log("Submitted values - capacity : ", values.capacity);
      //add the slot to the list of slots
      listSlot.push({
        capacity: Number(values.capacity),
        startDate: Date.parse(values.startDate),
        endDate: Date.parse(values.endDate),
      });
      console.log(listSlot);
    },
  });

  return (
    <>
      <HeroPattern pttrn={"topography-pattern"}>
        <div></div>
      </HeroPattern>
      <div className="topbar">
        <h1>Events</h1>
        <div className="dashboard">
          <div className="padding">
            <button
              className="button-64"
              onClick={() => navigate("/dashboard")}
            >
              <span className="text">Dashboard</span>
            </button>
          </div>
        </div>
      </div>
      <div className="container">
        <div>
          <h2>Créer un nouvel évènement</h2>
          <form onSubmit={formik.handleSubmit}>
            <div className="form__group field">
              <input
                className="form__field"
                type="text"
                name="eventName"
                id="eventName"
                onChange={formik.handleChange}
                value={formik.values.eventName}
                required
              />
              <label htmlFor="eventName" className="form__label">
                Nom de l'évènement
              </label>
            </div>
            <div>
              <label htmlFor="selectedUsers">Participants</label>
              <select
                id="selectedUsers"
                name="selectedUsers"
                multiple
                onChange={formik.handleChange}
                value={formik.values.selectedUsers}
              >
                {listUser.map((u) => (
                  <option key={u.id} value={u.id}>
                    {u.firstName} {u.lastName}
                  </option>
                ))}
              </select>
            </div>
            <div className="button-container">
              <div className="padding">
                <button className="button-64" type="submit">
                  <span className="text">Créer</span>
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
      <div className="container">
        <div>
          <h2>Créer un créneau</h2>
          <form onSubmit={formikSlot.handleSubmit}>
            <div className="form__group field">
              <input
                className="form__field"
                type="date"
                name="startDate"
                id="startDate"
                onChange={formikSlot.handleChange}
                value={formikSlot.values.startDate}
                required
              />
              <label htmlFor="startDate" className="form__label">
                Date de début
              </label>
            </div>
            <div className="form__group field">
              <input
                className="form__field"
                type="date"
                name="endDate"
                id="endDate"
                onChange={formikSlot.handleChange}
                value={formikSlot.values.endDate}
                required
              />
              <label htmlFor="endDate" className="form__label">
                Date de fin
              </label>
            </div>
            <div className="form__group field">
              <input
                className="form__field"
                type="number"
                name="capacity"
                id="capacity"
                onChange={formikSlot.handleChange}
                value={formikSlot.values.capacity}
                required
              />
              <label htmlFor="capacity" className="form__label">
                Capacité
              </label>
            </div>
            <div className="button-container">
              <div className="padding">
                <button className="button-64" type="submit">
                  <span className="text">Créer</span>
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}

export default Events;
