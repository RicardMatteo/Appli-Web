import { useNavigate } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import CryptoJS from "crypto-js";
import { invokePost } from "../../include/requests";
import "./register.css";

function hashPassword(password: string) {
  return CryptoJS.SHA256(password).toString(CryptoJS.enc.Hex);
}

const sanitize_username_input = (value: string) => {
  const sanitizedValue = value.toLowerCase().replace(/[^a-z0-9]/g, "");
  return sanitizedValue;
};

const sanitize_name_input = (value: string) => {
  // Remove all non-alphabetical characters
  const sanitizedValue = value.replace(/[^a-zA-Z]/g, "");
  return sanitizedValue;
};

function Login() {
  const navigate = useNavigate();

  const initial_values = {
    username: "",
    firstname: "",
    lastname: "",
    password: "",
  };

  const handleSubmit = (values: {
    username: string;
    firstname: string;
    lastname: string;
    password: string;
  }) => {
    const username = sanitize_username_input(values.username);
    const password = hashPassword(values.password);
    const firstname = sanitize_name_input(values.firstname);
    const lastname = sanitize_name_input(values.lastname);
    console.log(
      "Submitted values - username : ",
      username,
      " - password : ",
      password,
      " - firstname : ",
      firstname,
      " - lastname : ",
      lastname
    );
    /*axios.post("http://localhost:8080/ADEenMieux/rest/adduser", {
            username: username,
            hashedPassword: password,
            firstName: values.firstname,
            lastName: values.lastname
        })
        .then((response) => {
            console.log(response);
            navigate("/login");
        })*/

    invokePost(
      "adduser",
      {
        username: username,
        firstName: values.firstname,
        lastName: values.lastname,
        hashedPassword: password,
      },
      "Utilisateur ajouté",
      "Erreur lors de l'ajout de l'utilisateur"
    );
  };

  const validate = (values: {
    username: string;
    firstname: string;
    lastname: string;
    password: string;
  }) => {
    const errors: {
      username?: string;
      firstname?: string;
      lastname?: string;
      password?: string;
    } = {};
    if (!values.username) {
      errors.username = "* Veuillez saisir un nom d'utilisateur";
    }
    if (!values.firstname) {
      errors.firstname = "* Veuillez saisir un prénom";
    }
    if (!values.lastname) {
      errors.lastname = "* Veuillez saisir un nom de famille";
    }
    if (!values.password) {
      errors.password = "* Veuillez saisir un mot de passe";
    }
    if (values.username !== sanitize_username_input(values.username)) {
      errors.username =
        "*Mauvais nom d'utilisateur : ne doit contenir que des lettres minuscules et des chiffres";
    }
    if (values.firstname !== sanitize_name_input(values.firstname)) {
      errors.firstname = "*Mauvais prénom : ne doit contenir que des lettres";
    }
    if (values.lastname !== sanitize_name_input(values.lastname)) {
      errors.lastname =
        "*Mauvais nom de famille : ne doit contenir que des lettres";
    }
    return errors;
  };

  return (
    <div className="container">
      <div>
        <h1>Créez votre compte !</h1>
        <Formik
          initialValues={initial_values}
          validate={validate}
          onSubmit={handleSubmit}
          >
          {({ isSubmitting }) => (
            <Form>
              <div className="entry">
                <label htmlFor="username">Nom d'utilisateur: </label>
                <Field type="text" id="username" name="username" />
              </div>
              <ErrorMessage
                  name="username"
                  component="div"
                  className="errorMsg"
                  />
              <div className="entry">
                <label htmlFor="firstname">Prénom: </label>
                <Field type="text" id="firstname" name="firstname" />
                
              </div>
              <ErrorMessage
                  name="firstname"
                  component="div"
                  className="errorMsg"
                  />
              <div className="entry">
                <label htmlFor="lastname">Nom de famille: </label>
                <Field type="text" id="lastname" name="lastname" />
              </div>
              <ErrorMessage
                  name="lastname"
                  component="div"
                  className="errorMsg"
                  />
              <div className="entry">
                <label htmlFor="password">Mot de passe: </label>
                <Field type="password" id="password" name="password" />
              </div>
              <ErrorMessage
                  name="password"
                  component="div"
                  className="errorMsg"
                  />
              <div className="button-container">
                <button type="submit" disabled={isSubmitting}>
                  Créer son compte
                </button>
                <button onClick={() => navigate("/")}>Retour</button>
              </div>
            </Form>
          )}
        </Formik>
        </div>
    </div>
  );
}

export default Login;
