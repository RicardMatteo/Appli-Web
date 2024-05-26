import { useNavigate } from "react-router-dom";
import "./places.scss";
import { useEffect, useState } from "react";
import Cookies from "js-cookie";
import { invokeGetWithCookie } from "../../include/getwithcookie";
import { invokePost } from "../../include/requests";
import { useFormik } from "formik";

type SmallPlace = {
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

function Places() {
  const navigate = useNavigate();
  const [places, setPlaces] = useState<SmallPlace[]>([]);

  useEffect(() => {
    if (Cookies.get("authToken") === undefined) {
      navigate("/login");
    }
  }, [navigate]);

  useEffect(() => {
    invokeGetWithCookie(
      "getplaces",
      "Get places success",
      "Get places failure"
    ).then((res) => {
      if (res !== null) {
        setPlaces(res);
      }
    });
  }, []);

  const formikPlace = useFormik({
    initialValues: {
      name: "",
      capacity: "",
    },
    onSubmit: (values) => {
      console.log("Submitted values - name : ", values.name);
      console.log("Submitted values - capacity : ", values.capacity);
      invokePost(
        "addplace",
        {
          name: values.name,
          capacity: Number(values.capacity),
        },
        "Add place success",
        "Add place failure"
      ).then((res) => {
        if (res !== null) {
          console.log("Add place success", res);
          setPlaces((prevPlaces) => [
            ...prevPlaces,
            {
              name: values.name,
              capacity: Number(values.capacity),
            },
          ]);
        }
      });
      formikPlace.resetForm();
    },
  });

  return (
    <>
      <HeroPattern pttrn={"topography-pattern"}>
        <div></div>
      </HeroPattern>
      <div className="topbar">
        <h1>Gérer les salles</h1>
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
        <h1>Salles</h1>
        <p>
          Ici vous pouvez voir les endroits disponibles pour les évènements, et
          en ajouter
        </p>
        <p>Liste des salles utilisables :</p>
        {places.length === 0 ? (
          <p>Aucune salle enregistrée</p>
        ) : (
          <ul>
            {places.map((place, index) => (
              <li key={index}>
                {place.name} - Capacité : {place.capacity}
              </li>
            ))}
          </ul>
        )}
      </div>
      <div className="container">
        <h1>Enregistrer une nouvelle salle</h1>
        <form onSubmit={formikPlace.handleSubmit}>
          <div>
            <label htmlFor="name">Nom de la salle :</label>
            <input
              id="name"
              name="name"
              type="text"
              onChange={formikPlace.handleChange}
              value={formikPlace.values.name}
            />
          </div>
          <div>
            <label htmlFor="capacity">Capacité de la salle :</label>
            <input
              id="capacity"
              name="capacity"
              type="number"
              onChange={formikPlace.handleChange}
              value={formikPlace.values.capacity}
            />
          </div>
          <div className="padding">
            <button type="submit" className="button-64">
              <span className="text">Ajouter</span>
            </button>
          </div>
        </form>
      </div>
    </>
  );
}
export default Places;
