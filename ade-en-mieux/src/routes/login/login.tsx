import { useNavigate } from "react-router-dom";
import { useFormik } from "formik";
import { invokePost } from "../../include/requests";
import { useEffect } from "react";
import * as yup from "yup";
import CryptoJS from "crypto-js";
import "./login.scss";
import Cookies from "js-cookie";

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
  password: yup
    .string()
    .min(1, "Password should be of minimum 1 characters length")
    .required("Password is required"),
});

const Login = () => {
  const navigate = useNavigate();

  useEffect(() => {
    if (!(Cookies.get("authToken") === undefined)) {
      navigate("/dashboard");
    }
  }, [navigate]);

  const formik = useFormik({
    initialValues: {
      username: "",
      password: "",
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      const username = sanitize_username_input(values.username);
      const password = hashPassword(values.password);
      console.log(
        "Submitted values - username : ",
        username,
        " - password : ",
        password
      );

      invokePost(
        "login",
        {
          username: username,
          hashedPassword: password,
        },
        "User logged in",
        "pb with login"
      )
        .then((response: Response) => {
          const authToken = response.headers.get("authtoken");
          if (authToken === null) {
            console.error("AuthToken is null");
            return;
          }
          console.log("AuthToken : ", authToken);
          Cookies.set("authToken", authToken, {
            expires: 365,
            sameSite: "Strict",
          });
          navigate("/dashboard");
        })
        .catch((error: Error) => {
          // Handle the error here (ALED)
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
                <span className="text">Login</span>
              </button>
            </div>
            <div className="padding">
              <button className="button-64" onClick={() => navigate("/")}>
                <span className="text">Home</span>
              </button>
            </div>
            <div className="padding">
              <button
                className="button-64"
                onClick={() => navigate("/login/test")}
              >
                <span className="text">Test login</span>
              </button>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default Login;

/*
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

    invokePost(
      "login",
      {
        username: username,
        hashedPassword: password,
      },
      "User logged in",
      "pb with login"
    )
      .then((response: Response) => {
        const authToken = response.headers.get("authtoken");
        if (authToken === null) {
          console.error("AuthToken is null");
          return;
        }
        console.log("AuthToken : ", authToken);
        Cookies.set("authToken", authToken, { expires: 365 });
      })
      .catch((error: Error) => {
        // Handle the error here (ALED)
      });
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
    <>
      <HeroPattern pttrn={"topography-pattern"}>
        <div></div>
      </HeroPattern>
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
                <div className="padding">
                  <button
                    className="button-64"
                    type="submit"
                    disabled={isSubmitting}
                  >
                    <span className="text">Se connecter</span>
                  </button>
                </div>
                <div className="padding">
                  <button
                    className="button-64"
                    onClick={() => navigate("/login/test")}
                  >
                    <span className="text">Tester l'inscription</span>
                  </button>
                </div>
                <div className="padding">
                  <button className="button-64" onClick={() => navigate("/")}>
                    <span className="text">Home</span>
                  </button>
                </div>
              </div>
            </Form>
          )}
        </Formik>
      </div>
    </>
  );
}

export default Login;
*/
