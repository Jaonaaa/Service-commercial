import React, { useState } from "react";
import Modal from "../../utilsComponents/Modal/Modal";
import Select from "../../utilsComponents/Select/Select";
import Input from "../../utilsComponents/Input/Input";
import Spinner from "../../utilsComponents/Spinner/Spinner";
import SwitcherTheme from "./Switcher/SwitcherTheme";
import { useMyNotifs } from "../../utilsComponents/Notif/useNotifs";

import "./Home.sass";

const Home = () => {
  const [openModal, setOpenModal] = useState(false);
  const { showRandomNotif, notifs } = useMyNotifs();

  const [options] = useState([
    { label: "Example 1 ", value: "example_1" },
    { label: "Example 2 ", value: "example_2" },
    { label: "Example 3", value: "example_3" },
  ]);
  const handleModal = () => {
    setOpenModal(!openModal);
  };

  return (
    <div className="container_components">
      <div className="component_box">
        <button onClick={handleModal}> Open Modal </button>
        {openModal && <Modal closer={handleModal}> I'm a modal (～￣▽￣)～ </Modal>}
        <div className="title_compo">Modal</div>
      </div>
      <div className="component_box">
        <button onClick={showRandomNotif}> Add notifs</button>
        {notifs.map((notif) => notif)}
        <div className="title_compo">Notifications</div>
      </div>

      <div className="component_box">
        <Input onChange={() => {}} title={"Name"} />

        <div className="title_compo">Input </div>
      </div>

      <div className="component_box">
        <Spinner On={true} size="5rem" />
        <div className="title_compo">Spinner</div>
      </div>

      <div className="component_box">
        <Select
          onChange={(e) => {
            //   console.log(e);
          }}
          name={"select_input"}
          optionsType={options}
        />
        <div className="title_compo">Dropdown</div>
      </div>
    </div>
  );
};

export default Home;
