import React from "react";
import "./App.css";

type DateProps = {
  day: string;
  time: string;
};

const DateHeader: React.FC<DateProps> = ({ day, time }) => {
  return (
    <header className="App">
      <p>
        {day} {time}
      </p>
    </header>
  );
};

export default DateHeader;
