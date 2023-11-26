CREATE DATABASE service_commercial ;

CREATE Table _user 
{
    id SERIAL PRIMARY KEY  ,
    first_name VARCHAR , 
    last_name VARCHAR ,
    email VARCHAR ,
    password VARCHAR ,
    roles VARCHAR ,
}


--
-- Differenciena mints BDB an Supplier sy le Service 

CREATE TABLE localisation (
 id SERIAL PRIMARY KEY ,
 name VARCHAR
);

INSERT INTO localisation VALUES(1,'Imerintsiatosika');
INSERT INTO localisation VALUES(2,'Ambohimanala');
INSERT INTO localisation VALUES(3,'Anjomakely');
INSERT INTO localisation VALUES(4,'Analakely');
INSERT INTO localisation VALUES(5,'67 ha');
INSERT INTO localisation VALUES(6,'Ankorondrano');
INSERT INTO localisation VALUES(7,'Ambanidia');

--SERVICE 
CREATE TABLE service (
    id SERIAL PRIMARY KEY ,
    name VARCHAR ,
    logo VARCHAR ,
    id_localisation INT REFERENCES localisation (id) ON DELETE CASCADE,
    color VARCHAR ,
);

INSERT INTO service  (id, name, logo, id_localisation,color) VALUES (100,'Finance','financeLogo',6,'#3a4e4f');
INSERT INTO service  (id, name, logo, id_localisation,color) VALUES (101,'Commercial','commercialLogo',6,'#14a569');
INSERT INTO service  (id, name, logo, id_localisation,color) VALUES (102,'Juridique','juridiqueLogo',6,'#2b3353');

CREATE TABLE service_user (
    id SERIAL PRIMARY KEY ,
    id_user INT REFERENCES user (id) ON DELETE CASCADE ,
    id_service INT REFERENCES service (id) ON DELETE CASCADE
);

-- INSERT INTO service_user (id,id_user,id_service) VALUES(500,1,101);

--SUPPLIER
CREATE Table supplier (
    id SERIAL PRIMARY KEY ,
    name VARCHAR ,
    logo VARCHAR ,
    id_localisation INT REFERENCES localisation (id) ON DELETE CASCADE,
    color VARCHAR 
)

INSERT INTO supplier  (id, name, logo, id_localisation,color) VALUES (90,'Jumbo Score','jumboLogo',1,'#dd032d');
INSERT INTO supplier  (id, name, logo, id_localisation,color) VALUES (91,'Leader Price','leaderPriceLogo',2,'#f50117');
INSERT INTO supplier  (id, name, logo, id_localisation,color) VALUES (92,'Supermaki','supermakiLogo',3,'#db4200');
INSERT INTO supplier  (id, name, logo, id_localisation,color) VALUES (93,'Jumbo Score','jumboLogo',4,'#dd032d');
INSERT INTO supplier  (id, name, logo, id_localisation,color) VALUES (94,'Shopy','shopyLogo',5,'#72b41a');
INSERT INTO supplier  (id, name, logo, id_localisation,color) VALUES (95,'Shopy','shopyLogo',6,'#72b41a');
INSERT INTO supplier  (id, name, logo, id_localisation,color) VALUES (96,'Leader Price','leaderPriceLogo',7,'#f50117');


CREATE TABLE supplier_user (
    id SERIAL PRIMARY KEY ,
    id_user INT REFERENCES user (id) ON DELETE CASCADE ,
    id_supplier INT REFERENCES supplier (id) ON DELETE CASCADE
)



CREATE TABLE categorie 
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR ,
);

INSERT INTO categorie (id, name) VALUES (100,'Fourniture de bureau');
INSERT INTO categorie (id, name) VALUES (101,'Transport');
INSERT INTO categorie (id, name) VALUES (102,'Nourriture');
INSERT INTO categorie (id, name) VALUES (103,'Decoration');
INSERT INTO categorie (id, name) VALUES (104,'Autre');
INSERT INTO categorie (id, name) VALUES (105,'Mobilier');



CREATE TABLE article (
    id SERIAL PRIMARY KEY ,
    name VARCHAR ,
    id_categorie INT REFERENCES categorie (id) ON DELETE CASCADE, 
);

INSERT INTO article (id, name,id_categorie) VALUES (200,'Chaise',105);
INSERT INTO article (id, name,id_categorie) VALUES (201,'Bureau ( petit )',105);
INSERT INTO article (id, name,id_categorie) VALUES (202,'Bureau ( grand )',105);
INSERT INTO article (id, name,id_categorie) VALUES (203,'Mini bus',101);
INSERT INTO article (id, name,id_categorie) VALUES (204,'Voiture de plaisir',101);
INSERT INTO article (id, name,id_categorie) VALUES (205,'Voiture de luxe',101);
INSERT INTO article (id, name,id_categorie) VALUES (206,'Voiture simple',101);
INSERT INTO article (id, name,id_categorie) VALUES (207,'Feutre',100);
INSERT INTO article (id, name,id_categorie) VALUES (208,'Stylo',100);
INSERT INTO article (id, name,id_categorie) VALUES (209,'Scotch',100);
INSERT INTO article (id, name,id_categorie) VALUES (210,'Cahier',100);
INSERT INTO article (id, name,id_categorie) VALUES (211,'Barbe Papa',102);
INSERT INTO article (id, name,id_categorie) VALUES (212,'Poisson Tilapia',102);
INSERT INTO article (id, name,id_categorie) VALUES (213,'Boisson gazeuse',102);
INSERT INTO article (id, name,id_categorie) VALUES (214,'Riz',102);
INSERT INTO article (id, name,id_categorie) VALUES (215,'Fromage',102);
INSERT INTO article (id, name,id_categorie) VALUES (216,'Plante exterieur',103);
INSERT INTO article (id, name,id_categorie) VALUES (217,'Plante interieur',103);
INSERT INTO article (id, name,id_categorie) VALUES (218,'Tableu mureaux',103);
INSERT INTO article (id, name,id_categorie) VALUES (219,'Souris gaming',104);



CREATE TABLE article_supplier(
    id SERIAL PRIMARY KEY ,
    id_supplier INT REFERENCES supplier (id) ,
    id_artcile INT REFERENCES article (id) ,
    price_HT NUMERIC ,
    tva NUMERIC,--percent
    quantity NUMERIC ,
    -- price_TTC NUMERIC // a calculer par rapport a price_HT + tva(%)
);

CREATE TABLE article_service(
    id SERIAL PRIMARY KEY ,
    id_article INT REFERENCES,
    id_service INT REFERENCES ,
    quantity NUMERIC
);


CREATE TABLE request_sc(
    id SERIAL PRIMARY KEY ,
    id_service_commercial INT REFERENCES service (id) ON DELETE CASCADE,
    id_service_sender INT REFERENCES service (id) ON DELETE CASCADE ,
    date TIMESTAMP ,
    validate boolean  
);

-- lier proforma and request 
CREATE TABLE request_link_proforma(
    id SERIAL PRIMARY KEY,   
    validation INT  
)

CREATE TABLE request_sc_details(
    id SERIAL PRIMARY KEY ,
    id_request_sc INT REFERENCES request_sc(id) ON DELETE CASCADE,
    id_article INT REFERENCES ,
    quantity NUMERIC ,
);

CREATE Table request_link_proforma_details(
    id SERIAL PRIMARY KEY,
    id_request_link_proforma INT REFERENCES request_link_proforma(id),
    id_request_sc INT REFERENCES request_sc(id) ON DELETE CASCADE
)


CREATE TABLE request_proforma (
    id SERIAL PRIMARY KEY ,
    date TIMESTAMP ,
    id_request_link_proforma INT REFERENCES request_link_proforma
);


CREATE TABLE request_proforma_supplier (
    id SERIAL PRIMARY KEY ,
    id_request_proforma INT REFERENCES request_proforma (id),
    id_service INT REFERENCES service (id) ON DELETE CASCADE,
    id_supplier INT REFERENCES supplier (id) ON DELETE CASCADE
    answered BOOLEAN
);

CREATE Table request_proforma_details (
    id SERIAL PRIMARY KEY , 
    id_request_proforma INT REFERENCES request_proforma (id),
    id_article INT REFERENCES 
)

CREATE TABLE proforma_details(
    id SERIAL PRIMARY KEY ,
    id_request_proforma_supplier INT REFERENCES request_proforma (id),
    pdf_path VARCHAR
)