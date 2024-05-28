import Cookies from "js-cookie";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useFormik } from "formik";
import { invokeGetWithCookie } from "../../include/getwithcookie";
import "./events.scss";
import { invokePost } from "../../include/requests";

type User = {
  userId: number;
  username: string;
  firstName: string;
  lastName: string;
  hashedPassword: string;
  admin: boolean;
};

type Slot = {
  capacity: number;
  startDate: number;
  endDate: number;
  placeId: number;
};

type SmallPlaceGet = {
  id: number;
  name: string;
  capacity: number;
};

const HeroPattern = ({
  pttrn,
  children,
}: {
  pttrn: string;
  children: React.ReactNode;
}) => <div className={pttrn}>{children}</div>;

function ManageSlots({
  listSlot,
  setListSlot,
  places,
  setPlaces,
}: {
  listSlot: Slot[];
  setListSlot: React.Dispatch<React.SetStateAction<Slot[]>>;
  places: SmallPlaceGet[];
  setPlaces: React.Dispatch<React.SetStateAction<SmallPlaceGet[]>>;
}) {
  useEffect(() => {
    invokeGetWithCookie(
      "getplacesid",
      "Get places success",
      "Get places failure"
    ).then((res) => {
      if (res !== null) {
        setPlaces(res);
      }
    });
  }, [setPlaces]);

  const formikSlot = useFormik({
    initialValues: {
      startDate: "",
      endDate: "",
      capacity: "",
      placeId: "",
    },
    onSubmit: (values) => {
      /* Debug
      console.log("Submitted values - start date : ", values.startDate);
      console.log("Submitted values - end date : ", values.endDate);
      console.log("Submitted values - capacity : ", values.capacity);
      console.log("Submitted values - place id : ", values.placeId); */
      //add the slot to the list of slots
      setListSlot((prevListSlot) => [
        ...prevListSlot,
        {
          capacity: Number(values.capacity),
          startDate: Date.parse(values.startDate),
          endDate: Date.parse(values.endDate),
          placeId: Number(values.placeId),
        },
      ]);
      /* Debug
      console.log(listSlot); */
      formikSlot.resetForm();
    },
  });

  return (
    <>
      <h1>Gérer les slots</h1>
      <div className="container">
        <h1>Créer un créneau</h1>
        <form onSubmit={formikSlot.handleSubmit}>
          <div className="form__group field">
            <input
              className="form__field"
              type="datetime-local"
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
              type="datetime-local"
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
          <div>
            <label htmlFor="placeId">Lieu</label>
            <select
              id="placeId"
              name="placeId"
              onChange={(e) => {
                /* Debug
                console.log("Place id", e.target.value); */
                formikSlot.handleChange(e);
              }}
              value={formikSlot.values.placeId}
            >
              {places.map((place) => (
                <option key={place.id} value={place.id}>
                  {place.name} (ID: {place.id})
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
    </>
  );
}

function ListSlots({ listSlot }: { listSlot: Slot[] }) {
  return (
    <>
      <h1>Liste des Slots</h1>
      <div className="container">
        {listSlot.map((slot, index) => (
          <div key={index} className="container">
            <p>Start date : {new Date(slot.startDate).toLocaleString()}</p>
            <p>End date : {new Date(slot.endDate).toLocaleString()}</p>
            <p>Capacity : {slot.capacity}</p>
          </div>
        ))}
      </div>
    </>
  );
}

function CreateEvent({
  listSlot,
  listUser,
  setListSlot,
}: {
  listSlot: Slot[];
  listUser: User[];
  setListSlot: React.Dispatch<React.SetStateAction<Slot[]>>;
}) {
  const formikEvent = useFormik({
    initialValues: {
      eventName: "",
      selectedUsers: [],
    },
    onSubmit: (values) => {
      /* Debug
      console.log("Submitted values - event name : ", values.eventName);
      console.log("Selected users : ", values.selectedUsers); */
      // create the list of start dates
      const listStartDate = listSlot.map((s) => s.startDate);
      // create the list of end dates
      const listEndDate = listSlot.map((s) => s.endDate);
      // create the list of capacities
      const listCapacity = listSlot.map((s) => s.capacity);
      const listSlotId = listSlot.map((s) => s.placeId);

      /* Debug
      console.log("List start date", listStartDate);
      console.log("List end date", listEndDate);
      console.log("List capacity", listCapacity);
      console.log("List slot id", listSlotId);
      */

      invokePost(
        "createevent",
        {
          eventName: values.eventName,
          participants: values.selectedUsers,
          startDates: listStartDate,
          endDates: listEndDate,
          capacities: listCapacity,
          places: listSlotId,
        },
        "Evènement ajouté",
        "Erreur lors de l'ajout de l'évènement"
      ).then((response: Response) => {
        if (response.ok) {
          alert(
            "Évènement ajouté ! Les utilisateurs pourront voir l'évènement sur leur compte"
          );
        }
      });

      setListSlot([]);
      formikEvent.resetForm();
    },
  });

  return (
    <>
      <div>
        <h1>Créer l'évènement</h1>
        <h2>Tous les slots créés vont être assignés à cet évènement</h2>
        <div className="container">
          <form onSubmit={formikEvent.handleSubmit}>
            <div className="form__group field">
              <input
                className="form__field"
                type="text"
                name="eventName"
                id="eventName"
                onChange={formikEvent.handleChange}
                value={formikEvent.values.eventName}
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
                onChange={formikEvent.handleChange}
                value={formikEvent.values.selectedUsers}
              >
                {listUser.map((u) => (
                  <option key={u.userId} value={u.userId}>
                    {u.username}
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
    </>
  );
}

function Events() {
  const navigate = useNavigate();
  // On vérifie que l'utilisateur est connecter avant d'afficher la page
  useEffect(() => {
    if (Cookies.get("authToken") === undefined) {
      navigate("/login");
    }
  }, [navigate]); // Utilisation d'un tableau vide pour exécuter useEffect une seule fois après le rendu initial

  const [listUser, setListUser] = useState<User[]>([]);
  const [listSlot, setListSlot] = useState<Slot[]>([]);
  const [places, setPlaces] = useState<SmallPlaceGet[]>([]);

  useEffect(() => {
    invokeGetWithCookie(
      "listusers",
      "Liste des utilisateurs récupérée",
      "Erreur lors de la récupération de la liste des utilisateurs"
    ).then((res) => {
      if (Array.isArray(res)) {
        setListUser(res);
      } else {
        console.error("Expected an user array but received", res);
      }
    });
  }, []);

  return (
    <>
      <HeroPattern pttrn={"topography-pattern"}>
        <div></div>
      </HeroPattern>
      <div className="topbar">
        <h1>Dashboard</h1>
        <div className="dashboard">
          <div className="padding">
            <button
              className="button-64"
              onClick={() => navigate("/dashboard")}
            >
              <span className="text">Home</span>
            </button>
          </div>
        </div>
      </div>
      <div>
        <ManageSlots
          listSlot={listSlot}
          setListSlot={setListSlot}
          places={places}
          setPlaces={setPlaces}
        />
        <ListSlots listSlot={listSlot} />
        <CreateEvent
          listSlot={listSlot}
          listUser={listUser}
          setListSlot={setListSlot}
        />
      </div>
    </>
  );
}

export default Events;
