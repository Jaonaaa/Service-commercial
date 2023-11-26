import React from "react";
import "./Table.sass";

const Table = ({ headerOn, body = [], index = [], titles = [], classes = [] }) => {
  return (
    <div className="table_container">
      {headerOn && (
        <div className="header_container">
          <div className="title">{headerOn.title} </div>
          <div className="search_table">{headerOn.side_component}</div>
        </div>
      )}
      <table>
        <thead>
          <tr>
            {titles.map((title, i) => (
              <th className="head_th" key={i}>
                {title}
              </th>
            ))}
          </tr>
        </thead>
        {body.map((row, i) => {
          const column = index.map((ind, ind__) => (
            <td key={ind__} className={classes[ind__]}>
              {" "}
              {row[ind]}{" "}
            </td>
          ));
          return (
            <tbody key={i}>
              <tr className={`tab_row ${(2 + i) % 2 === 0 ? "one" : "two"}`}>{column}</tr>
            </tbody>
          );
        })}
      </table>
    </div>
  );
};

const data = {
  headerOn: { title: "Active users >> ", side_component: "" },
  titles: ["Name", "Email", "Total downloads", "Available"],
  index: [],
  body: [
    ["Lorem ing elit.", "Lorem ing elit.", "Lorem ing elit.", "Lorem ing elit."],
    ["Lorem ing elit.", "Lorem ing elit.", "Lorem ing elit.", "Lorem ing elit."],
  ],
};

export default Table;
