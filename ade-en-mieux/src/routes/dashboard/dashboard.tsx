import { useNavigate } from "react-router-dom";

function Dashboard() {
  const navigate = useNavigate();

  return (
    <>
      <div>
        <h1>Dashboard</h1>
        <p>Welcome to your dashboard</p>
      </div>

      <div className="button-container">
        <button onClick={() => navigate("/agenda")}>Agenda</button>
        <button onClick={() => navigate("/organise")}>Orginise</button>
        <button onClick={() => navigate("/participate")}>Participate</button>
      </div>
    </>
  );
}

export default Dashboard;
