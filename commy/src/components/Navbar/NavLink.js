import ArchiveIcon from "../../assets/svg/ArchiveIcon";
import BellIcon from "../../assets/svg/BellIcon";
import ChatIcon from "../../assets/svg/ChatIcon";
import HomeIcon from "../../assets/svg/HomeIcon";
import OverviewIcon from "../../assets/svg/OverviewIcon";
import SettingsIcon from "../../assets/svg/SettingsIcon";
import ShopIcon from "../../assets/svg/ShopIcon";
import StockStatusIcon from "../../assets/svg/StockStatusIcon";
import ArticleIcon from "../../assets/svg/ArticleIcon";
import RequestIcon from "../../assets/svg/RequestIcon";
import FileIcon from "../../assets/svg/FileIcon";

//Aza adino le "/" aloha path rehetra
const linksNavData = [
  {
    type: "menu",
    menuLabel: "Dashboard",
    rows: [
      {
        type: "link",
        linkTo: "/",
        label: "Home",
        icon: <HomeIcon />,
      },

      {
        type: "link_list",
        label: "Notification",
        icon: <BellIcon />,
        sublinks: [
          {
            label: "Example 1",
            linkTo: "/Home/Test/example_1",
          },
          {
            label: "Example 2",
            linkTo: "/test/example_2",
          },
        ],
      },
    ],
  },
  {
    type: "menu",
    menuLabel: "Main menu",
    rows: [
      {
        type: "link_info",
        linkTo: "/chat",
        label: "Chat",
        icon: <ChatIcon />,
        info: 10,
      },
      {
        type: "link_list",
        label: "Shop",
        icon: <ShopIcon />,
        sublinks: [
          {
            label: "Example 11",
            linkTo: "/example_11",
          },
        ],
      },
    ],
  },
  {
    type: "single",
    row: {
      type: "link",
      linkTo: "/settings",
      label: "Settings",
      icon: <SettingsIcon />,
    },
  },
];

export const link_to_hide_nav = ["/settings", "/login"];

export const linkService = [
  {
    type: "menu",
    menuLabel: "Dashboard",
    rows: [
      {
        type: "link",
        linkTo: "/",
        label: "Home",
        icon: <HomeIcon />,
      },
      {
        type: "link",
        linkTo: "/service_sc",
        label: "Request to SC ",
        icon: <RequestIcon />,
      },
    ],
  },
  {
    type: "menu",
    menuLabel: "Admin Dashboard",
    rows: [
      {
        type: "link_info",
        linkTo: "/admin_validation",
        label: "Validation request",
        icon: <HomeIcon />,
      },
    ],
  },
  {
    type: "single",
    row: {
      type: "link",
      linkTo: "/settings",
      label: "Settings",
      icon: <SettingsIcon />,
    },
  },
];

export const linkServiceCommercial = [
  {
    type: "menu",
    menuLabel: "Dashboard",
    rows: [
      {
        type: "link",
        linkTo: "/",
        label: "Home",
        icon: <HomeIcon />,
      },
      {
        type: "link_info",
        linkTo: "/request",
        label: "Request Service",
        icon: <RequestIcon />,
        info: 2,
      },
      {
        type: "link_list",
        label: "Proforma",
        icon: <FileIcon />,
        sublinks: [
          {
            label: "Request",
            linkTo: "/proforma/request",
          },
        ],
      },
    ],
  },
  {
    type: "menu",
    menuLabel: "Admin Dashboard",
    rows: [
      {
        type: "link_info",
        linkTo: "/admin_validation",
        label: "Validation request",
        icon: <HomeIcon />,
      },
    ],
  },
  {
    type: "single",
    row: {
      type: "link",
      linkTo: "/settings",
      label: "Settings",
      icon: <SettingsIcon />,
    },
  },
];

export const linkSupplier = [
  {
    type: "menu",
    menuLabel: "Dashboard",
    rows: [
      {
        type: "link",
        linkTo: "/",
        label: "Home",
        icon: <HomeIcon />,
      },
      {
        type: "link",
        linkTo: "/stock_state",
        label: "Stock availibility ",
        icon: <StockStatusIcon />,
      },
      {
        type: "link_list",
        label: "Request",
        icon: <RequestIcon />,
        sublinks: [
          {
            label: "Proforma",
            linkTo: "/proforma/request",
          },
        ],
      },
      {
        type: "link",
        linkTo: "/manage_stock",
        label: "Manage stock ",
        icon: <ArticleIcon />,
      },
    ],
  },

  {
    type: "single",
    row: {
      type: "link",
      linkTo: "/settings",
      label: "Settings",
      icon: <SettingsIcon />,
    },
  },
];

export default linksNavData;
