import "./wrongeventid.scss";

const HeroPattern = ({
  pttrn,
  children,
}: {
  pttrn: string;
  children: React.ReactNode;
}) => <div className={pttrn}>{children}</div>;

const WrongEventId = () => {
  return (
    <>
      <HeroPattern pttrn={"topography-pattern"}>
        <div></div>
      </HeroPattern>
      <div className="container">
        <h1>Wrong Event Id</h1>
        <p>Event Id does not exist</p>
      </div>
    </>
  );
};
export default WrongEventId;
