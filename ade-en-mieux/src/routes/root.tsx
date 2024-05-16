import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";

export default function Root() {
  const navigate = useNavigate();

  return (
    <>
      <div className="container">
        <div>
          <h1>ADE en Mieux</h1>
        </div>
        <div>
          <h2>Ceci sera la future homepage du site !</h2>
        </div>
        <div className="button-container">
          <button onClick={() => navigate("/tp3")}>TP3</button>
          <button onClick={() => navigate("/login")}>Login</button>
          <button onClick={() => navigate("/register")}>Register</button>
          <button onClick={() => Cookies.remove("authToken")}>Logout</button>
        </div>
      </div>
    </>
  );
}
