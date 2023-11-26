import React from "react";
import "./Spinner.sass";

export const Spinner = ({ On, size = "0.65rem", borderWidth = "3px" }) => {
  return (
    <>
      {On && (
        <div
          className="lds-dual-ring"
          style={{ width: size, height: size, borderWidth: borderWidth }}
        ></div>
      )}
    </>
  );
};

export default Spinner;
