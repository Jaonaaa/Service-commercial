import React from "react";
import Indexation from "./Indexation/Indexation";
import "./Header.sass";
import UserParams from "./UserParams/UserParams";

const Header = () => {
  return (
    <div className="container_header">
      <Indexation />
      <div className="search_bar"></div>
      <div className="params">
        <UserParams />
      </div>
    </div>
  );
};

export default Header;
