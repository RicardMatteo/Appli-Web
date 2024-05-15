import React from "react";
import { useNavigate } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from "formik";
import CryptoJS from "crypto-js";
import axios from "axios";

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
    console.log(
      "Submitted values - username : ",
      username,
      " - password : ",
      password,
      " - firstname : ",
      values.firstname,
      " - lastname : ",
      values.lastname
    );
    axios
      .post("http://localhost:8080/ADEenMieux/rest/adduser", {
        username: username,
        hashedPassword: password,
        firstName: values.firstname,
        lastName: values.lastname,
      })
      .then((response) => {
        console.log(response);
        navigate("/login");
      });
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
      errors.username = "Veuillez saisir un nom d'utilisateur";
    }
    if (!values.firstname) {
      errors.firstname = "Veuillez saisir un prénom";
    }
    if (!values.lastname) {
      errors.lastname = "Veuillez saisir un nom de famille";
    }
    if (!values.password) {
      errors.password = "Veuillez saisir un mot de passe";
    }
    if (values.username !== sanitize_username_input(values.username)) {
      errors.username =
        "Mauvais nom d'utilisateur : ne doit contenir que des lettres minuscules et des chiffres";
    }
    return errors;
  };

  return (
    <div>
      <h1>Créez votre compte !</h1>
      <Formik
        initialValues={initial_values}
        validate={validate}
        onSubmit={handleSubmit}
      >
        {({ isSubmitting }) => (
          <Form>
            <div>
              <label htmlFor="username">Nom d'utilisateur:</label>
              <Field type="text" id="username" name="username" />
              <ErrorMessage name="username" component="div" />
            </div>
            <div>
              <label htmlFor="firstname">Prénom:</label>
              <Field type="text" id="firstname" name="firstname" />
              <ErrorMessage name="firstname" component="div" />
            </div>
            <div>
              <label htmlFor="lastname">Nom de famille:</label>
              <Field type="text" id="lastname" name="lastname" />
              <ErrorMessage name="lastname" component="div" />
            </div>
            <div>
              <label htmlFor="password">Mot de passe:</label>
              <Field type="password" id="password" name="password" />
              <ErrorMessage name="password" component="div" />
            </div>
            <button type="submit" disabled={isSubmitting}>
              Créer son compte
            </button>
          </Form>
        )}
      </Formik>
      <button onClick={() => navigate("/")}>Home</button>
    </div>
  );
}

export default Login;
