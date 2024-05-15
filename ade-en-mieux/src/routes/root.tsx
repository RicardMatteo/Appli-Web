import { useNavigate } from "react-router-dom";

export default function Root() {
  const navigate = useNavigate();

  return (
    <>
      <div>
        <h1>ADE en Mieux</h1>
      </div>
      <div>
        <h2>Ceci sera la future homepage du site !</h2>
      </div>
      <button onClick={() => navigate("/tp3")}>TP3</button>
      <button onClick={() => navigate("/login")}>Login toi mon reuf !</button>
    </>
  );
}
