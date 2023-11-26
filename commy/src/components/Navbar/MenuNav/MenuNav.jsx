import React from "react";
import RowNav from "../RowNav/RowNav";
import "./MenuNav.sass";

const MenuNav = (props) => {
  const { type, menuLabel, rows, setActiveLink, row, miniNav } = props;
  return (
    <div
      className={`menu_container ${miniNav ? "mini_menu" : ""} ${
        type === "single" ? "to_down" : ""
      }`}
    >
      {!miniNav && <div className="row_title_menu">{menuLabel}</div>}
      {miniNav && <div className="line"> </div>}
      <div className="list_nav_link">
        {type === "menu" &&
          rows.map((rowData, index) => (
            <RowNav
              setActiveLink={setActiveLink}
              miniNav={miniNav}
              {...rowData}
              key={index}
            />
          ))}
        {type === "single" && (
          <RowNav
            setActiveLink={setActiveLink}
            miniNav={miniNav}
            {...row}
            last
          />
        )}
      </div>
    </div>
  );
};

export default MenuNav;
