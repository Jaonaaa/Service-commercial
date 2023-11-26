import React, { useEffect, useState } from "react";
import LogoDefault from "../../../assets/svg/LogoDefault";
import RowInput from "../RowInput/RowInput";
import UseHandleForm from "./UseHandleForm";
import { Link } from "react-router-dom";
import { alaivoGet, alaivoPost } from "../../../utils/Alaivo";
import Select from "../../../utilsComponents/Select/Select";
import "./FormSignUp.sass";
import useIdentity from "../../../hooks/useIdentity";

const FormSignUp = ({ addNotifs }) => {
  const stepTotal = 4;
  const { setUpStorageConnect, getNewTokenByRefreshToken } = useIdentity();
  const { roles, services, society, supplier, checkIfEmailAvailable } = useData(addNotifs);
  const handleSubmit = async (formData) => {
    alaivoPost("auth/register", JSON.stringify(formData)).then((res) => {
      setUpStorageConnect(res);
      document.location = "/";
    });
  };
  useEffect(() => {}, []);

  const { formData, moveStep, handleForm, handleInputForm, step } = UseHandleForm(stepTotal, [
    checkIfEmailAvailable,
    null,
    null,
    handleSubmit,
  ]);

  return (
    <div className="sign_up_form">
      <div className="logo">
        <LogoDefault />
      </div>
      <div className="title">Sign up</div>
      <div className="subtitle">Come with us in this incredible journey.</div>
      <div className="slider">
        {[...Array(stepTotal)].map((i, ind) => (
          <div
            key={ind}
            className={`slide ${step === ind + 1 ? "slide_on" : ""}`}
            onClick={() => {
              moveStep(ind + 1);
            }}
          ></div>
        ))}
      </div>
      <form action="" method="post" onSubmit={handleForm}>
        {step === 1 ? (
          <>
            <RowInput
              title="Your email"
              type="email"
              required
              value={formData.email}
              id="email"
              name="email"
              fullWidth
              onChange={handleInputForm}
            />
            <RowInput
              title="Your password"
              type="password"
              required
              value={formData.password}
              id="password"
              name="password"
              fullWidth
              onChange={handleInputForm}
            />
          </>
        ) : (
          ""
        )}
        {step === 2 ? (
          <>
            <RowInput
              title="Your firstname"
              type="text"
              required
              value={formData.firstname}
              id="firstname"
              name="firstname"
              fullWidth
              onChange={handleInputForm}
            />
            <RowInput
              title="Your lastname"
              type="text"
              required
              value={formData.lastname}
              id="lastname"
              name="lastname"
              fullWidth
              onChange={handleInputForm}
            />
          </>
        ) : (
          ""
        )}
        {step === 3 ? (
          <>
            <Select title={"Society"} fullWidth name="society" onChange={handleInputForm} optionsType={society} />
            <Select title={"Role"} fullWidth name="role" onChange={handleInputForm} optionsType={roles} />
          </>
        ) : (
          ""
        )}
        {step === 4 ? (
          <>
            {console.log(formData)}
            {formData.society === "SERVICE" && (
              <Select
                title={"Services"}
                fullWidth
                name="society_id"
                onChange={handleInputForm}
                optionsType={services}
              />
            )}
            {formData.society === "SUPPLIER" && (
              <Select
                title={"Suppliers"}
                fullWidth
                name="society_id"
                onChange={handleInputForm}
                optionsType={supplier}
              />
            )}
          </>
        ) : (
          ""
        )}

        <div className="button">
          <button>{stepTotal === step ? "Register" : "Next"}</button>
        </div>

        <div className="sign_up_link">
          <div className="text">Already have an account ?</div>
          <Link to={"/login"}>
            <div className="link">Sign in.</div>
          </Link>
        </div>
      </form>
    </div>
  );
};

const useData = (addNotifs) => {
  const [society, setSociety] = useState([]);
  const [roles, setRoles] = useState([]);

  const [services, setServices] = useState([]);
  const [supplier, setSupplier] = useState([]);

  useEffect(() => {
    getRegisterData();
  }, []);

  const getRegisterData = () => {
    alaivoGet("auth/register").then((data) => {
      const { roles, services, supplier, society } = data;
      console.log(supplier);
      setSociety(society.map((soc) => ({ label: soc, value: soc })));
      setRoles(roles.map((soc) => ({ label: soc, value: soc })));
      setServices(services.map((soc) => ({ label: soc.name, value: soc.id })));
      setSupplier(supplier.map((soc) => ({ label: soc.name + " / " + soc.localisation.name, value: soc.id })));
    });
  };

  const checkIfEmailAvailable = (formData) => {
    if (formData.email === undefined || formData.password === undefined) {
      addNotifs("error", "Fill up all required fields please.");
      return false;
    }
    if (formData.email.trim() === "" || formData.password.trim() === "") {
      addNotifs("error", "Fill up all required fields please.");
      return false;
    }

    return new Promise((resolve, reject) => {
      alaivoPost("auth/check?email=" + formData.email).then((data) => {
        console.log(data);
        if (!data) {
          addNotifs("error", "Email not available");
        }
        resolve(data);
      });
    });
  };

  return { society, supplier, services, roles, checkIfEmailAvailable };
};

export default FormSignUp;
