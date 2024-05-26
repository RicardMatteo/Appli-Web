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
        <h1>Erreur</h1>
        <p>L'évènement auquel vous essayez de vous inscrire n'est pas valide</p>
        <p>ou vous êtes déjà inscrits</p>
      </div>
    </>
  );
};
export default WrongEventId;
