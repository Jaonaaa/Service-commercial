import React, { useEffect, useRef, useState } from "react";
import Box from "../../../utilsComponents/Box/Box";
import AddArticle from "./AddArticle";
import DeleteArticle from "./DeleteArticle";
import UpdateArticle from "./UpdateArticle";
import "./Manage.sass";

const ManageArticle = ({ addNotifs }) => {
  const [state, setState] = useState(0);

  const [add, setAdd] = useState(0);
  const firstChips = useRef(null);
  const [size, setSize] = useState({ left: 0, width: 0 });

  const handleSlider = (e) => {
    setAdd(+e.target.getAttribute("num"));
    setState(+e.target.getAttribute("num"));
    getProprety(e.target);
  };

  const getProprety = (target) => {
    const { width } = target.getBoundingClientRect();
    const left = target.offsetLeft;
    setSize({ left: left, width: width });
  };
  useEffect(() => {
    setTimeout(() => {
      if (firstChips != null) getProprety(firstChips.current);
    }, 100);
  }, []);

  return (
    <div className="centered_mg_top">
      <div className="handler_add">
        <div className="slider" style={size}></div>
        <div ref={firstChips} className={`chips ${add === 0 ? "selected" : ""}`} onClick={handleSlider} num="0">
          Add
        </div>
        <div className={`chips ${add === 1 ? "selected" : ""}`} onClick={handleSlider} num="1">
          Update
        </div>
        <div className={`chips ${add === 2 ? "selected" : ""}`} onClick={handleSlider} num="2">
          Remove
        </div>
      </div>

      <Box className="centered">
        {state === 0 && <AddArticle addNotifs={addNotifs} />}
        {state === 1 && <UpdateArticle addNotifs={addNotifs} />}
        {state === 2 && <DeleteArticle addNotifs={addNotifs} />}
      </Box>
    </div>
  );
};

const todo = ["Add", "Update", "Delete"];

export default ManageArticle;
