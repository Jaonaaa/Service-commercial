import { Route, Routes, Navigate } from "react-router-dom";

import Navbar from "./components/Navbar/Navbar";
import ContentContainer from "./components/ContentContainer/ContentContainer";
import Home from "./components/Home/Home";
import { switchTheme, useDefaultTheme } from "./themes/Theme";
import { useMyNotifs } from "./utilsComponents/Notif/useNotifs";
import useDashboard from "./hooks/useDashboard";
import FullScreen from "./components/FullScreen/FullScreen";
import Login from "./components/Login/Login";
import { linkService, linkServiceCommercial, linkSupplier } from "./components/Navbar/NavLink";

import SwitcherTheme from "./components/Home/Switcher/SwitcherTheme";
import "./App.sass";
import RequestSCForm from "./components/Service/RequestSCForm/RequestSCForm";
import MessageBox from "./components/Service/RequestService/MessageBox/MessageBox";
import ProformaService from "./components/Profoma/ProformaService/ProformaService";
import ProformaSupplier from "./components/Profoma/ProformaSupplier/ProformaSupplier";
import EtatStock from "./components/Supplier/EtatStock/EtatStock";
import ManageArticle from "./components/Supplier/Management/ManageArticle";
import AdminValidation from "./components/Service/AdminValidation/AdminValidation";

function App() {
  useDefaultTheme();
  const { dashboard } = useDashboard();

  return (
    <div className="App">
      {dashboard == "service" && <Service />}
      {dashboard == "supplier" && <Supplier />}
      {!dashboard && <LoginScreen />}
    </div>
  );
}

const LoginScreen = () => {
  switchTheme("light");

  const { addNotifs, notifs } = useMyNotifs();

  return (
    <>
      {notifs.map((notif) => notif)}
      <Routes>
        <Route path="/" element={<Navigate to={"/login"} />} />
        <Route
          path="/login"
          element={
            <FullScreen>
              <Login addNotifs={addNotifs} />
            </FullScreen>
          }
        />
        <Route
          path="/signup"
          element={
            <FullScreen>
              <Login screen="sign_up" addNotifs={addNotifs} />
            </FullScreen>
          }
        />
      </Routes>
    </>
  );
};

const Service = () => {
  const { addNotifs, notifs } = useMyNotifs();
  const service = localStorage.getItem("details_user_");
  const isCommerce = JSON.parse(service).service ? JSON.parse(service).service.logo === "commercialLogo" : false;

  return (
    <>
      {notifs.map((notif) => notif)}
      <Navbar links={isCommerce ? linkServiceCommercial : linkService} />
      <SwitcherTheme />

      <Routes>
        <Route
          path="/"
          element={
            <ContentContainer>
              <Home />
            </ContentContainer>
          }
        />

        <Route
          path="/request"
          element={
            <ContentContainer>
              <MessageBox addNotifs={addNotifs} />
            </ContentContainer>
          }
        />

        <Route
          path="/proforma/request"
          element={
            <ContentContainer>
              <ProformaService addNotifs={addNotifs} />
            </ContentContainer>
          }
        />

        <Route path="/proforma/result" element={<ContentContainer></ContentContainer>} />

        <Route
          path="/service_sc"
          element={
            <ContentContainer>
              <RequestSCForm addNotifs={addNotifs} />
            </ContentContainer>
          }
        />

        <Route
          path="/admin_validation"
          element={
            <ContentContainer>
              <AdminValidation addNotifs={addNotifs} />
            </ContentContainer>
          }
        />

        <Route path="/settings" element={<ContentContainer></ContentContainer>} />
      </Routes>
    </>
  );
};

const Supplier = () => {
  const { addNotifs, notifs } = useMyNotifs();
  return (
    <>
      {notifs.map((notif) => notif)}
      <Navbar links={linkSupplier} />
      <SwitcherTheme />
      <Routes>
        <Route
          path="/"
          element={
            <ContentContainer>
              <Home />
            </ContentContainer>
          }
        />

        <Route
          path="/stock_state"
          element={
            <ContentContainer>
              <EtatStock addNotifs={addNotifs} />
            </ContentContainer>
          }
        />
        <Route
          path="/proforma/request"
          element={
            <ContentContainer>
              <ProformaSupplier addNotifs={addNotifs} />
            </ContentContainer>
          }
        />

        <Route
          path="/manage_stock"
          element={
            <ContentContainer>
              <ManageArticle addNotifs={addNotifs} />
            </ContentContainer>
          }
        />
        <Route path=" /proforma/bon_commande" element={<ContentContainer></ContentContainer>} />
        <Route path="/settings" element={<ContentContainer></ContentContainer>} />
      </Routes>
    </>
  );
};

export default App;
