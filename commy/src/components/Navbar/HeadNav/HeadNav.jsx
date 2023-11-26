import React from "react";
import LogoDefault from "../../../assets/svg/LogoDefault";
import DashboardIcon from "../../../assets/svg/DashboardIcon";

import "./HeadNav.sass";
import SupplierLogo from "../../../assets/Logo/SupplierLogo";
import CommercialLogo from "../../../assets/Logo/CommercialLogo";
import FinanceLogo from "../../../assets/Logo/FinanceLogo";
import JuridiqueLogo from "../../../assets/Logo/JuridiqueLogo";

const HeadNav = ({ miniNav, switchNav, dataDashBoard }) => {
  const details = localStorage.getItem("details_user_") ? JSON.parse(localStorage.getItem("details_user_")) : undefined;
  const handleLogo = () => {
    if (miniNav) switchNav();
  };
  return (
    <>
      <div className={`head_nav ${miniNav ? "mini_head" : ""}`}>
        <div className="logo">
          <div className="icon" onClick={handleLogo}>
            {handleLogoStatic(details)}
          </div>
          <div className="text">{dataDashBoard ? dataDashBoard.data.name : "Unknow"}</div>
        </div>
        {!miniNav && (
          <div className="btn_small_nav" onClick={switchNav}>
            <DashboardIcon />
          </div>
        )}
      </div>
    </>
  );
};

const handleLogoStatic = (details) => {
  if (details.service) {
    //
    if (details.service.logo === "commercialLogo") return <CommercialLogo />;
    else if (details.service.logo === "financeLogo") return <FinanceLogo />;
    else if (details.service.logo === "juridiqueLogo") return <JuridiqueLogo />;
    //
  } else if (details.supplier) {
    return <SupplierLogo />;
  } else return <LogoDefault />;
};
export const handleLogoStaticIn = (logo) => {
  //
  if (logo === "commercialLogo") return <CommercialLogo />;
  else if (logo === "financeLogo") return <FinanceLogo />;
  else if (logo === "juridiqueLogo") return <JuridiqueLogo />;
  //
  else return <SupplierLogo />;
};

export default HeadNav;
