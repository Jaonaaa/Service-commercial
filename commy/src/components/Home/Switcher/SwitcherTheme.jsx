import React, { useState } from "react";
import { getCurrentThemeName, switchTheme } from "../../../themes/Theme";

import "./SwitcherTheme.sass";

const SwitcherTheme = () => {
  const [theme, setTheme] = useState(getCurrentThemeName());
  const handleTheme = (theme) => {
    switchTheme(theme);
    setTheme(getCurrentThemeName());
  };
  return (
    <div
      className="switcher_theme_container"
      onClick={() => {
        if (theme == "light") handleTheme("dark");
        else handleTheme("light");
      }}
    >
      <div className={`holder ${theme}`}></div>
      <div className={`block_theme ${theme === "light" ? "light" : ""}`}>L</div>

      <div className={`block_theme ${theme === "dark" ? "dark" : ""}`}>D</div>
    </div>
  );
};

export default SwitcherTheme;
