import { useEffect, useState } from "react";
import "./invitedevents.scss";
import { invokeGetWithCookie } from "../../../include/getwithcookie";
import { useNavigate } from "react-router-dom";

type SmallInvitedEvent = {
  eventId: number;
  eventName: string;
};

const HeroPattern = ({
  pttrn,
  children,
}: {
  pttrn: string;
  children: React.ReactNode;
}) => <div className={pttrn}>{children}</div>;

const InvitedEvents = () => {
  const navigate = useNavigate();
  const [listEvents, setListEvents] = useState<SmallInvitedEvent[]>([]);

  useEffect(() => {
    invokeGetWithCookie(
      "getinvitedevents",
      "Get invited events success",
      "Get invited events failure"
    ).then((res) => {
      if (res !== null) {
        setListEvents(res);
      }
    });
  }, []);
  return (
    <>
      <HeroPattern pttrn={"topography-pattern"}>
        <div></div>
      </HeroPattern>
      <div className="container">
        <h1>Liste des évènements auquels vous êtes invités</h1>
        <div>
          {listEvents.map((event) => (
            <div key={event.eventId}>
              <h2>{event.eventName}</h2>
              <div className="padding">
                <button
                  className="button-64"
                  onClick={() =>
                    navigate("/events/replytoinvite/" + event.eventId)
                  }
                >
                  <span className="text">Répondre à l'invitation</span>
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};
export default InvitedEvents;
