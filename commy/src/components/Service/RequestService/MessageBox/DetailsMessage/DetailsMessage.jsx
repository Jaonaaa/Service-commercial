import React from "react";
import "./DetailsMessage.sass";
import Table from "../../../../../utilsComponents/Table/Table";

const data = {
  headerOn: { title: "", side_component: "" },
  titles: ["Article", "Categorie", "Quantity"],
  index: ["article", "categorie", "quantity"],
  body: [],
};

const DetailsMessage = ({ details }) => {
  return (
    <div className="container_details">
      <div className="title"> Details request </div>
      <div className="container_tab">
        <Table {...data} body={details} />
      </div>
    </div>
  );
};

export default DetailsMessage;
