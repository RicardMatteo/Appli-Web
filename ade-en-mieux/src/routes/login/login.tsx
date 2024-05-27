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
          Cookies.set("authToken", authToken, {
            expires: 365,
            sameSite: "None",
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
          </div>
        </form>
      </div>
    </>
  );
};

export default Login;
