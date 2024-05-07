import "./App.css";
import Loading from "./Loading";
import DateHeader from "./DateHeader";

const App = () => {
  return (
    <div>
      <h1>ade en mieux</h1>
      <DateHeader day="ajd" time="12:34:56" />
      <Loading />
    </div>
  );
};

export default App;
