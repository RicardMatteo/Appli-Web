import Cookies from "js-cookie";
import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { invokeGetWithCookie } from "../../../include/getwithcookie";

function ReplyToInvite() {
  const navigate = useNavigate();
  const { eventId } = useParams();

  useEffect(() => {
    if (Cookies.get("authToken") === undefined) {
      navigate("/login");
    }
  }, [navigate]);

  useEffect(() => {
    // Call backend to get event details
    // and see if you were invited
    invokeGetWithCookie(
      "getevent",
      "Event details retrieved",
      "Error retrieving event details",
      "eventId",
      eventId
    );
  }, [eventId]);

  return (
    <>
      <div>
        <h1>Reply to invite</h1>
        <p>Event ID: {eventId}</p>
      </div>
    </>
  );
}

export default ReplyToInvite;
