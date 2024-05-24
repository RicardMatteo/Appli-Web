import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { invokePost } from "../include/requests";

const HeroPattern = ({
  pttrn,
  children,
}: {
  pttrn: string;
  children: React.ReactNode;
}) => <div className={pttrn}>{children}</div>;

export default function Root() {
  const navigate = useNavigate();

  return (
    <>
      <HeroPattern pttrn={"topography-pattern"}>
        <div></div>
      </HeroPattern>
      <div className="container">
        <div>
          <h1>ADE en Mieux</h1>
        </div>
        <div>
          <h2>Ceci sera la future homepage du site !</h2>
        </div>
        <div className="button-container">
          <div className="padding">
            <button className="button-64" onClick={() => navigate("/tp3")}>
              <span className="text">TP3</span>
            </button>
          </div>
          <div className="padding">
            <button className="button-64" onClick={() => navigate("/login")}>
              <span className="text">Login</span>
            </button>
          </div>
          <div className="padding">
            <button className="button-64" onClick={() => navigate("/register")}>
              <span className="text">Register</span>
            </button>
          </div>
          <div className="padding">
            <button
              className="button-64"
              onClick={() => Cookies.remove("authToken")}
            >
              <span className="text">Logout</span>
            </button>
          </div>
          <div className="padding">
            <button
              className="button-64"
              onClick={() =>
                invokePost("/initdb", null, "InitDB success", "InitDB failure")
              }
            >
              <span className="text">InitDB</span>
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
