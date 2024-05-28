import Cookies from "js-cookie";
import { useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { invokeGetWithCookie } from "../../../include/getwithcookie";
import React from "react";
import { useFormik } from "formik";
import { invokePost } from "../../../include/requests";

type SmallEvent = {
  eventId: number;
  eventName: string;
  slotIds: number[];
  startDates: number[];
  endDates: number[];
  capacities: number[];
};

type Slot = {
  slotId: number;
  startDate: number;
  endDate: number;
  capacity: number;
};

const HeroPattern = ({
  pttrn,
  children,
}: {
  pttrn: string;
  children: React.ReactNode;
}) => <div className={pttrn}>{children}</div>;

function PresentEventDetails({ eventName }: { eventName: string }) {
  return (
    <>
      <div className="topbar">
        <h1>{eventName}</h1>
      </div>
      <div className="container">Choisissez votre créneau :</div>
    </>
  );
}

function ChooseSlot({ slots }: { slots: Slot[] }) {
  const navigate = useNavigate();
  const formikSlot = useFormik({
    initialValues: { selectedSlot: "" },
    onSubmit: (values) => {
      const selectedSlotId = parseInt(values.selectedSlot);
      invokePost(
        "addusertoslot",
        {
          slotId: selectedSlotId,
        },
        "Reply to invite success",
        "Reply to invite failure"
      ).then(
        (res) => {
          if (res !== null) {
            alert("Inscription au créneau réussie");
            navigate("/events/invitedevents");
          }
        },
        (error) => {
          console.error("Reply to invite failure", error);
          alert("Erreur lors de l'inscription au créneau");
        }
      );
    },
  });

  return (
    <>
      <div className="container">
        {slots.length === 0 ? (
          <p>Désolé, plus aucun créneau disponible</p>
        ) : (
          <form onSubmit={formikSlot.handleSubmit}>
            {slots.map((slot) => (
              <div key={slot.slotId}>
                <input
                  type="radio"
                  name="selectedSlot"
                  value={slot.slotId.toString()}
                  onChange={formikSlot.handleChange}
                  onBlur={formikSlot.handleBlur}
                  checked={
                    formikSlot.values.selectedSlot === slot.slotId.toString()
                  }
                />
                <label>
                  {new Date(slot.startDate).toLocaleDateString()} -{" "}
                  {new Date(slot.startDate).toLocaleTimeString()}{" "}
                  {new Date(slot.endDate).toLocaleDateString()} -{" "}
                  {new Date(slot.endDate).toLocaleTimeString()} {slot.capacity}{" "}
                  places restantes
                </label>
              </div>
            ))}
            <button type="submit">Submit</button>
          </form>
        )}
      </div>
    </>
  );
}

function ReplyToInvite() {
  const navigate = useNavigate();
  const { eventId } = useParams();
  const eventRef = useRef<SmallEvent | null>(null);
  // eslint-disable-next-line react-hooks/exhaustive-deps

  const [listSlot, setListSlot] = useState<Slot[]>([]);

  useEffect(() => {
    if (Cookies.get("authToken") === undefined) {
      navigate("/login");
    }
  }, [navigate]);

  useEffect(() => {
    const slots: Slot[] = [];
    // Call backend to get event details
    // and see if you were invited

    invokeGetWithCookie(
      "getevent",
      "Event details retrieved",
      "Error retrieving event details",
      "eventId",
      eventId
    )
      .then((res) => {
        if (res !== null) {
          eventRef.current = res as SmallEvent;
          slots.length = 0;
          for (let i = 0; i < eventRef.current.slotIds.length; i++) {
            slots.push({
              slotId: eventRef.current.slotIds[i],
              startDate: eventRef.current.startDates[i],
              endDate: eventRef.current.endDates[i],
              capacity: eventRef.current.capacities[i],
            });
          }
          setListSlot(slots);
        } else {
          console.error("Error retrieving event details");
        }
      })
      .catch((error) => {
        console.error("Error retrieving event details", error);
        navigate("/events/wrongeventid");
      });
  }, [eventId, navigate]);

  return (
    <>
      <HeroPattern pttrn={"topography-pattern"}>
        <div></div>
      </HeroPattern>
      <PresentEventDetails eventName={eventRef.current?.eventName || ""} />
      <ChooseSlot slots={listSlot} />
    </>
  );
}

export default ReplyToInvite;
