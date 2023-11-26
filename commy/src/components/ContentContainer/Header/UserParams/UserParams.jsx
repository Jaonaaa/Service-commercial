import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import EllipsisVerticale from "../../../../assets/svg/EllipsisVerticale";
import useIdentity from "../../../../hooks/useIdentity";
import ArrowRight from "../../../../assets/svg/ArrowRight";

import "./UserParams.sass";

const UserParams = () => {
  const navigate = useNavigate();
  const [openPaper, closePaper] = useState(false);
  const { user, logout, getNewTokenByRefreshToken, checkTokenStatus, disableRefreshToken } = useIdentity();
  const goLogin = () => {
    navigate("/login");
  };
  const handleOpen = () => {
    closePaper(!openPaper);
  };

  return (
    <div className="user_param">
      {user == undefined ? (
        <>
          <div className="button_log_in" onClick={goLogin}>
            <span className="text">Login</span> <ArrowRight />{" "}
          </div>
        </>
      ) : (
        <>
          <div className="avatar">
            {user == undefined ? (
              ""
            ) : user.photoURL ? (
              <image src={user.photoURL} alt="user picture" />
            ) : (
              user.firstname[0]
            )}
            <div className="blank"></div>
            <div className="user_about">
              <div className="name">{user.firstname + " " + user.lastname}</div>
              <div className="post">{user.roles + " / " + user.society}</div>
            </div>
          </div>
          <div
            className={`paper_option ${openPaper} `}
            tabIndex={0}
            onBlur={() => {
              closePaper(false);
            }}
            onClick={handleOpen}
          >
            <EllipsisVerticale />
            {openPaper && (
              <div
                className="paper_in"
                onClick={(e) => {
                  e.stopPropagation();
                }}
              >
                <div
                  className="row"
                  onClick={() => {
                    getNewTokenByRefreshToken();
                  }}
                >
                  Regenerate token
                </div>
                <div className="row" onClick={checkTokenStatus}>
                  Check Status
                </div>
                <div className="row" onClick={disableRefreshToken}>
                  Disable refresh token
                </div>
                <div
                  className="row"
                  onClick={() => {
                    logout("/");
                  }}
                >
                  Deconnexion
                </div>
              </div>
            )}
          </div>
        </>
      )}
    </div>
  );
};

export default UserParams;
