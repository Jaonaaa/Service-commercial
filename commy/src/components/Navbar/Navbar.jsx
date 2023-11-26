import React, { useEffect, useState } from "react";
import HeadNav from "./HeadNav/HeadNav";
import MenuNav from "./MenuNav/MenuNav";
import useNav from "./hooks/useNav";
import linksNavData from "./NavLink";
import useHideNav from "./hooks/useHideNav";
import "./Navbar.sass";

const Navbar = ({ links }) => {
  const { setActiveLink, putActiveLink } = useNav("row_link_nav");
  const initialNavSize = localStorage.getItem("navbar_mini");
  const [miniNav, setMiniNav] = useState(initialNavSize === "true" ? true : false);
  const linksData = links ? links : linksNavData;
  const { visibleNav } = useHideNav();
  const [dataDashBoard, setdataDashboard] = useState();
  const user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : undefined;

  const switchNav = () => {
    setMiniNav(!miniNav);
    setTimeout(() => {
      putActiveLink();
    }, 10);
  };

  useEffect(() => {
    localStorage.setItem("navbar_mini", JSON.stringify(miniNav));
    getDetailsDashboard();
  }, [miniNav]);

  const getDetailsDashboard = () => {
    let detailsSociety = localStorage.getItem("details_user_");

    if (detailsSociety) {
      let dashboard = JSON.parse(detailsSociety);
      if (dashboard.supplier)
        setdataDashboard({
          data: dashboard.supplier,
          user: dashboard.user,
        });
      if (dashboard.service)
        setdataDashboard({
          data: dashboard.service,
          user: dashboard.user,
        });
    }
  };

  return (
    <>
      {visibleNav && (
        <div className={`navbar_container ${miniNav ? "mini" : ""}`}>
          <HeadNav miniNav={miniNav} switchNav={switchNav} dataDashBoard={dataDashBoard} />
          {linksData.map((menuData, key) => {
            if (user) if (menuData.menuLabel === "Admin Dashboard" && user.roles !== "ADMIN") return "";
            return <MenuNav key={key} miniNav={miniNav} {...menuData} setActiveLink={setActiveLink} />;
          })}
        </div>
      )}
    </>
  );
};

export default Navbar;
