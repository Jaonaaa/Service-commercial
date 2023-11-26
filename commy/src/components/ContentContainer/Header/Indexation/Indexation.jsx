import React, { useEffect, useState } from "react";
import "./Indexation.sass";

const Indexation = () => {
  const [indexName, setIndexName] = useState(["index", "text"]);

  useEffect(() => {
    handleIndex();
  }, [window.location.pathname]);

  const handleIndex = () => {
    let currentPath = window.location.pathname;
    let pathArray = currentPath.split("/");
    pathArray.shift();
    if (currentPath === "/") setIndexName(["home"]);
    else setIndexName(pathArray);
  };

  const getIndex = () => {
    return indexName.map((name, index) => {
      if (index == indexName.length - 1)
        return (
          <span className="index_current" key={index}>
            {UpFirst(name)}
          </span>
        );
      return (
        <span className="index_before" key={index}>
          {UpFirst(name)} /{" "}
        </span>
      );
    });
  };

  return <div className="indexation_container">{getIndex()}</div>;
};

const UpFirst = (str) => {
  return str.charAt(0).toUpperCase() + str.slice(1);
};

export default Indexation;
