import React from "react";
import Header from "./Header/Header";

import "./ContentContainer.sass";

const ContentContainer = (props) => {
  const { children } = props;
  return (
    <div className="content_container_main">
      <Header />
      {children}
    </div>
  );
};

export default ContentContainer;
