import { useNavigate } from "react-router-dom";
import "./recapevents.scss";
import { invokeGetWithCookie } from "../../include/getwithcookie";
import { useEffect, useState } from "react";

type Slot = {
  eventName: string;
  startDate: number;
  endDate: number;
  location: string;
};

const HeroPattern = ({
  pttrn,
  children,
}: {
  pttrn: string;
  children: React.ReactNode;
}) => <div className={pttrn}>{children}</div>;

function RecapEvents() {
  const navigate = useNavigate();
  const [slots, setSlots] = useState<Slot[]>([]);

  useEffect(() => {
    invokeGetWithCookie(
      "getuserslots",
      "Get events success",
      "Get events failure"
    ).then((res) => {
      if (res !== null) {
        setSlots(res);
      }
    });
  }, []);

  return (
    <>
      <HeroPattern pttrn={"topography-pattern"}>
        <div></div>
      </HeroPattern>
      <div className="topbar">
        <h1>Liste des évènements auquels vous êtes inscrits</h1>
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
        {slots.length === 0 ? (
          <div>Aucun évènement</div>
        ) : (
          slots.map((slot) => (
            <div key={slot.startDate}>
              <h2>{slot.eventName}</h2>
              <p>
                {new Date(slot.startDate).toLocaleString()} -{" "}
                {new Date(slot.endDate).toLocaleString()}
              </p>
              <p>{slot.location}</p>
            </div>
          ))
        )}
      </div>
    </>
  );
}
export default RecapEvents;
