import { useEffect, useState } from "react";
import useIdentity from "./useIdentity";

const useDashboard = () => {
  const { user, checkTokenStatus, logout } = useIdentity();
  const [dashboard, setDashboard] = useState(user);
  // const status_profile = ["admin", "user"];
  // const society_dashboard = ["supplier", "service"];
  const handleUserStatus = () => {
    if (user) {
      setDashboard(user.society.toLowerCase());
    }
  };
  useEffect(() => {
    handleUserStatus();
    checkTokenStatus();
    // logout();
  }, [user]);
  return { dashboard };
};

export default useDashboard;
