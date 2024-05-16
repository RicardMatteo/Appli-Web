import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";

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
        <div>
          <button className="button-64" onClick={() => navigate("/tp3")}>
            <span className="text">TP3</span>
          </button>
          <button onClick={() => navigate("/login")}>Login</button>
          <button onClick={() => navigate("/register")}>Register</button>
          <button onClick={() => Cookies.remove("authToken")}>Logout</button>
        </div>
      </div>
    </>
  );
}
