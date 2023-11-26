import React from "react";
import FormSignIn from "./FormSignIn/FormSignIn";
import "./Login.sass";
import FormSignUp from "./FormSignUp/FormSignUp";

const Login = ({ screen = "sign_in", addNotifs }) => {
  return (
    <div id="login_container">
      {screen === "sign_in" && <FormSignIn addNotifs={addNotifs} />}
      {screen === "sign_up" && <FormSignUp addNotifs={addNotifs} />}
    </div>
  );
};

export default Login;
