import React, { useState } from "react";
import LogoDefault from "../../../assets/svg/LogoDefault";
import RowInput from "../RowInput/RowInput";

import "./FormSignIn.sass";
import useIdentity from "../../../hooks/useIdentity";
import { Link } from "react-router-dom";

const FormSignIn = ({ addNotifs }) => {
  const [formData, setFormData] = useState({ email: undefined, password: undefined });
  const { signIn } = useIdentity(addNotifs);

  const handleInput = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    signIn(formData, "/");
  };
  return (
    <div className="sign_in_form">
      <div className="logo">
        <LogoDefault />
      </div>
      <div className="title">Sign-in</div>
      <div className="subtitle">Let's dive with us in this incredible journey.</div>
      <form action="" method="post" onSubmit={handleSubmit}>
        <RowInput title="Email" type="email" id="email" name="email" onChange={handleInput} fullWidth />
        <RowInput title="Password" type="password" id="password" name="password" onChange={handleInput} fullWidth />
        <div className="button">
          <button>Login</button>
        </div>
        +
        <div className="sign_up_link">
          <div className="text">Don't have an account ?</div>
          <Link to={"/signup"}>
            <div className="link">Register here.</div>
          </Link>
        </div>
      </form>
    </div>
  );
};

export default FormSignIn;
