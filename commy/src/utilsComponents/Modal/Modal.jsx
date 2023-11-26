import React from "react";
import ReactDOM from "react-dom";
import "./style/style.sass";
import { motion } from "framer-motion";

const Modal = ({ children, closer }) => {
  return ReactDOM.createPortal(
    <>
      <div id="hider" onClick={closer}></div>

      <motion.div
        initial={{ y: "0%", x: "-50%", scale: 0.8 }}
        animate={{ y: "-50%", x: "-50%", scale: 1 }}
        exit={{ y: "80%", x: "-50%", scale: 0.8, opacity: 0 }}
        id="content_modal"
      >
        {children}
      </motion.div>
    </>,
    document.getElementById("modal_container")
  );
};

export default Modal;
