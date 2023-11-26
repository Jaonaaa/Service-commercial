import React, { useState } from "react";
import Box from "../../../../utilsComponents/Box/Box";
import Input from "../../../../utilsComponents/Input/Input";
import "./FormSC.sass";
import { alaivoPost } from "../../../../utils/Alaivo";

const FormSC = ({ addNotifs, listArticle = [] }) => {
  const [listData, setListData] = useState(
    listArticle.map((article) => ({
      key_id: "article_" + article.id,
      quantity: 0,
      article: {
        ...article.article_body,
      },
    }))
  );
  const sendList = () => {
    const service = JSON.parse(localStorage.getItem("details_user_")).service;
    const data = {
      service_sender: { id: service.id },
      validate: false,
      details: listData,
    };
    alaivoPost("requestsc", JSON.stringify(data)).then((response) => {
      addNotifs(response.status, response.details);
    });
  };
  const handleInput = (e) => {
    const id = e.target.name;
    const newData = listData.filter((art) => art.key_id !== id);
    const target = listData.filter((art) => art.key_id === id)[0];
    target.quantity = +e.target.value;
    setListData([...newData, target]);
  };

  return (
    <Box className="form_sc_container">
      <div className="title">List article</div>
      <div className="form_list">
        {listArticle.map((article) => (
          <div className="row_input" key={article.id}>
            <div className="name">{article.article}</div>
            <Input
              type="number"
              name={"article_" + article.id}
              id={"article_" + article.id}
              defaultValue={0}
              onChange={handleInput}
              title={"Quantity"}
            />
            <div className="name last">{article.categorie}</div>
          </div>
        ))}
      </div>
      <button onClick={sendList}>Register</button>
    </Box>
  );
};

export default FormSC;
