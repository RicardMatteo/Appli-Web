import React from "react";
import { useNavigate } from "react-router-dom";
import { useFormik } from "formik";
import * as yup from "yup";
import CryptoJS from "crypto-js";
import { invokePost } from "../../include/requests";
import "./register.scss";

const HeroPattern = ({
  pttrn,
  children,
}: {
  pttrn: string;
  children: React.ReactNode;
}) => <div className={pttrn}>{children}</div>;

function hashPassword(password: string) {
  return CryptoJS.SHA256(password).toString(CryptoJS.enc.Hex);
}

const sanitize_username_input = (value: string) => {
  const sanitizedValue = value.toLowerCase().replace(/[^a-z0-9]/g, "");
  return sanitizedValue;
};

const validationSchema = yup.object({
  username: yup
    .string()
    .required("Username is required")
    .matches(
      /^[a-z0-9]+$/,
      "Username should contain only lowercase letters and numbers"
    ),
  firstname: yup
    .string()
    .required("Firstname is required")
    .matches(/^[a-zA-Z]+$/, "Firstname should contain only letters"),
  lastname: yup
    .string()
    .required("Lastname is required")
    .matches(/^[a-zA-Z]+$/, "Lastname should contain only letters"),
  password: yup
    .string()
    .min(1, "Password should be of minimum 1 characters length")
    .required("Password is required"),
});

const Register = () => {
  const navigate = useNavigate();

  const formik = useFormik({
    initialValues: {
      username: "",
      firstname: "",
      lastname: "",
      password: "",
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      const username = sanitize_username_input(values.username);
      const password = hashPassword(values.password);
      /* Debug
      console.log(
        "Submitted values - username : ",
        username,
        " - password : ",
        password,
        " - firstname : ",
        firstname,
        " - lastname : ",
        lastname
      ); */

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
      ).then((response: Response) => {
        if (response.ok) {
          navigate("/login");
        }
      });
    },
  });
  return (
    <>
      <HeroPattern pttrn={"topography-pattern"}>
        <div></div>
      </HeroPattern>
      <div className="container">
        <form onSubmit={formik.handleSubmit}>
          <div className="form__group field">
            <input
              className="form__field"
              type="text"
              placeholder="Username"
              name="username"
              id="username"
              value={formik.values.username}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              required
            />
            <label htmlFor="username" className="form__label">
              Username
            </label>
            {formik.touched.username && formik.errors.username ? (
              <div className="errorMsg">{formik.errors.username}</div>
            ) : null}
          </div>
          <div className="form__group field">
            <input
              className="form__field"
              type="text"
              placeholder="First Name"
              name="firstname"
              id="firstname"
              value={formik.values.firstname}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              required
            />
            <label htmlFor="firstname" className="form__label">
              First Name
            </label>
          </div>
          <div className="form__group field">
            <input
              className="form__field"
              type="text"
              placeholder="Last Name"
              name="lastname"
              id="lastname"
              value={formik.values.lastname}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              required
            />
            <label htmlFor="lastname" className="form__label">
              Last Name
            </label>
          </div>
          <div className="form__group field">
            <input
              className="form__field"
              type="password"
              placeholder="Password"
              name="password"
              id="password"
              value={formik.values.password}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              required
            />
            <label htmlFor="password" className="form__label">
              Password
            </label>
          </div>
          <div className="button-container">
            <div className="padding">
              <button type="submit" className="button-64">
                <span className="text">Create Account</span>
              </button>
            </div>
            <div className="padding">
              <button className="button-64" onClick={() => navigate("/")}>
                <span className="text">Home</span>
              </button>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default Register;
