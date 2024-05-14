import { useNavigate } from "react-router-dom";

export default function Root() {
        const navigate = useNavigate();

        return (
            <>
                <div><h1>Future HomePage</h1></div>
                <button onClick={() => navigate("/tp3")}>TP3</button>
            </>
        );
}
