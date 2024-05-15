import { useNavigate } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import CryptoJS from "crypto-js";
import "./login.css";

function hashPassword(password: string) {
  return CryptoJS.SHA256(password).toString(CryptoJS.enc.Hex);
}

const sanitize_username_input = (value: string) => {
  const sanitizedValue = value.toLowerCase().replace(/[^a-z0-9]/g, "");
  return sanitizedValue;
};

function Login() {
  const navigate = useNavigate();

  const initial_values = {
    username: "",
    password: "",
  };

  const handleSubmit = (values: { username: string; password: string }) => {
    const username = sanitize_username_input(values.username);
    const password = hashPassword(values.password);
    console.log(
      "Submitted values - username : ",
      username,
      " - password : ",
      password
    );
  };

  const validate = (values: { username: string; password: string }) => {
    const errors: { username?: string; password?: string } = {};
    if (!values.username) {
      errors.username = "* Veuillez saisir un nom d'utilisateur";
    }
    if (!values.password) {
      errors.password = "* Veuillez saisir un mot de passe";
    }
    if (values.username !== sanitize_username_input(values.username)) {
      errors.username = "* Mauvais nom d'utilisateur";
    }
    return errors;
  };

  return (
    <div className="container">
      <h1>Page de connexion</h1>
      <Formik
        initialValues={initial_values}
        validate={validate}
        onSubmit={handleSubmit}
      >
        {({ isSubmitting }) => (
          <Form>
            <div className="entry">
              <label htmlFor="username">Nom d'utilisateur:</label>
              <Field type="text" id="username" name="username" />
            </div>
            <ErrorMessage
              name="username"
              component="div"
              className="errorMsg"
            />
            <div className="entry">
              <label htmlFor="password">Mot de passe:</label>
              <Field type="password" id="password" name="password" />
            </div>
            <ErrorMessage
                name="password"
                component="div"
                className="errorMsg"
              />
            <div className="button-container">
              <button type="submit" disabled={isSubmitting}>
                Se connecter
              </button>
              <button onClick={() => navigate("/")}>Home</button>
            </div>
          </Form>
        )}
      </Formik>
    </div>
  );
}

export default Login;
