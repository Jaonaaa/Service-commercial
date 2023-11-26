import { useEffect, useState } from "react";
import { alaivoDelete, alaivoPost } from "../utils/Alaivo";

const getUserPresp = () => {
  return localStorage.getItem("user") === null ? undefined : JSON.parse(localStorage.getItem("user"));
};

const useIdentity = (addNotifs) => {
  const userPresp = getUserPresp();
  const [user, setUser] = useState(userPresp);
  const [token_refresh_status, setTokenRefresh_status] = useState(false);
  const [token_status, setToken_status] = useState(false);

  const initUser = () => {
    setUser(getUserPresp());
  };
  useEffect(() => {
    initUser();
  }, [document.location.pathname]);

  const logout = (to = "/") => {
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    localStorage.removeItem("refresh_token");
    localStorage.removeItem("details_user_");
    setUser(undefined);
    document.location = to;
  };

  const getNewTokenByRefreshToken = (refreshToken) => {
    alaivoPost(
      "auth/refresh_token",
      JSON.stringify({
        refresh_token: refreshToken ? refreshToken : localStorage.getItem("refresh_token"),
      }),
      {
        headers: {
          "Content-Type": "application/json",
        },
      },
      true
    )
      .then((res) => {
        if (res.data) {
          setUpStorageConnect(res.data);
        }
        if (res.status) console.log(res.details);
      })
      .catch((err) => {
        console.error(err);
      });
  };

  const checkTokenStatus = () => {
    alaivoPost(
      "auth/checkTokenStatus",
      JSON.stringify({
        refresh_token: localStorage.getItem("refresh_token"),
        token: localStorage.getItem("token"),
      }),
      {
        headers: {
          "Content-Type": "application/json",
        },
      },
      true
    )
      .then((res) => {
        setTokenRefresh_status(res.refresh_token_valid);
        setToken_status(res.token_valid);
      })
      .catch((err) => {
        console.error(err);
      });
  };

  const disableRefreshToken = () => {
    alaivoDelete(
      "auth/refreshToken",
      JSON.stringify({
        refresh_token: localStorage.getItem("refresh_token"),
      }),
      {
        headers: {
          "Content-Type": "application/json",
        },
      },
      true
    )
      .then((res) => {
        localStorage.removeItem("refresh_token");
        console.log(res);
      })
      .catch((err) => {
        console.error(err);
      });
  };

  const signIn = (formData, to) => {
    alaivoPost(
      "auth/authenticate",
      JSON.stringify(formData),
      {
        headers: {
          "Content-Type": "application/json",
        },
      },
      true
    )
      .then((response) => {
        console.log(response);
        setUpStorageConnect(response);
        if (to) document.location = to;
      })
      .catch((error) => {
        addNotifs("error", "Email or password not correct.");
        // console.log(error);
      });
  };

  const setUpStorageConnect = (data) => {
    localStorage.setItem("refresh_token", data.refresh_token);
    localStorage.setItem("token", data.token);
    localStorage.setItem("user", JSON.stringify(data.user));
    localStorage.setItem("details_user_", JSON.stringify(data.details_user_));
  };

  const signUp = (formData, to) => {
    alaivoPost(
      "auth/register",
      JSON.stringify(formData),
      {
        headers: {
          "Content-Type": "application/json",
        },
      },
      true
    )
      .then((response) => {
        console.log(response);
        if (to) document.location = to;
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return {
    user,
    signIn,
    setUser,
    token_refresh_status,
    token_status,
    logout,
    getNewTokenByRefreshToken,
    signUp,
    checkTokenStatus,
    disableRefreshToken,
    setUpStorageConnect,
  };
};

export const getHeaderAuthJWT = () => ({
  headers: {
    Authorization: "Bearer " + localStorage.getItem("token"),
    "Content-Type": "application/json",
  },
});

export default useIdentity;
