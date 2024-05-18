import Cookies from "js-cookie";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useFormik } from "formik";
import * as yup from "yup";
import "./organiser.scss";
import { invokePost } from "../../include/requests";

type Group = { id: number; name: string };

const HeroPattern = ({
  pttrn,
  children,
}: {
  pttrn: string;
  children: React.ReactNode;
}) => <div className={pttrn}>{children}</div>;

const groupValidationSchema = yup.object({
  groupName: yup.string().required("Group name is required"),
});

function createGroup(groupName: string) {
  console.log(groupName);
  invokePost(
    "creategroup",
    { groupName: groupName },
    "Group créé",
    "Erreur lors de la création du groupe"
  );
}

function ListGroups() {
  const [listGroup, setListGroup] = useState<Group[]>([]);

  useEffect(() => {
    invokePost(
      "listgroups",
      { cookie: Cookies.get("authToken") },
      "Group listé",
      "Erreur lors de la récupération de la liste des groupes"
    ).then((res) => {
      console.log(res);
      res.json().then((data) => {
        setListGroup(data);
      });
    });
  }, []);

  if (listGroup === null) {
    return (
      <>
        <p>Pas de groupes</p>
        <br />
      </>
    );
  }

  return (
    <>
      <ul>
        {listGroup.map((g: Group) => (
          <li key={g.id}>{g.name}</li>
        ))}
      </ul>
      <br />
    </>
  );
}

function Organiser() {
  const navigate = useNavigate();

  const formikCreateGroup = useFormik({
    initialValues: {
      groupName: "",
    },
    validationSchema: groupValidationSchema,
    onSubmit: (values) => {
      createGroup(values.groupName);
    },
  });

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
        <h1>Organiser les groupes et les évènements</h1>
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
      <div className="box">
        <div className="container">
          <div>
            <h1>Créer un groupe</h1>
          </div>
          <div>
            <form onSubmit={formikCreateGroup.handleSubmit}>
              <div className="form__group field">
                <input
                  className="form__field"
                  type="text"
                  id="groupName"
                  name="groupName"
                  onChange={formikCreateGroup.handleChange}
                  value={formikCreateGroup.values.groupName}
                />
                <label htmlFor="groupName" className="form__label">
                  Nom du groupe
                </label>
              </div>
              <div className="padding">
                <button type="submit" className="button-64">
                  <span className="text">Créer</span>
                </button>
              </div>
            </form>
          </div>
        </div>
        <div className="container">
          <div>
            <h1>Ajouter des utilisateurs à un groupe</h1>
          </div>
          <ListGroups />
        </div>
      </div>
    </>
  );
}

export default Organiser;
