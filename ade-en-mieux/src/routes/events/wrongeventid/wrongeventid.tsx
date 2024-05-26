import { useNavigate } from "react-router-dom";
import "./wrongeventid.scss";

const HeroPattern = ({
  pttrn,
  children,
}: {
  pttrn: string;
  children: React.ReactNode;
}) => <div className={pttrn}>{children}</div>;

const WrongEventId = () => {
  const navigate = useNavigate();
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
      <div className="container">
        <h1>Impossible de s'inscrire à cet évènement</h1>
        <p>L'évènement auquel vous essayez de vous inscrire n'est pas valide</p>
        <p>ou vous êtes déjà inscrits</p>
      </div>
    </>
  );
};
export default WrongEventId;
