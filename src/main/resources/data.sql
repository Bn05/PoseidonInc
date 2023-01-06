DROP DATABASE IF EXISTS poseidondb;

CREATE DATABASE poseidondb;
USE poseidondb;



CREATE TABLE Bid_list
(
    bid_list_id    INTEGER     NOT NULL AUTO_INCREMENT,
    account        VARCHAR(30) NOT NULL,
    type           VARCHAR(30) NOT NULL,
    bid_quantity   DOUBLE      NOT NULL,
    ask_quantity   DOUBLE,
    bid_list       DOUBLE,
    ask            DOUBLE,
    benchmark      VARCHAR(125),
    bid_list_date  TIMESTAMP,
    commentary     VARCHAR(125),
    security       VARCHAR(125),
    status         VARCHAR(10),
    trader         VARCHAR(125),
    book           VARCHAR(125),
    creation_name  VARCHAR(125),
    creation_date  TIMESTAMP,
    revision_name  VARCHAR(125),
    revision_date  TIMESTAMP,
    deal_name      VARCHAR(125),
    deal_type      VARCHAR(125),
    source_list_id VARCHAR(125),
    side           VARCHAR(125),

    PRIMARY KEY (bid_list_id)
);

CREATE TABLE Trade
(
    trade_id       INTEGER     NOT NULL AUTO_INCREMENT,
    account        VARCHAR(30) NOT NULL,
    type           VARCHAR(30) NOT NULL,
    buy_quantity   DOUBLE,
    sell_quantity  DOUBLE,
    buy_price      DOUBLE,
    sell_price     DOUBLE,
    trade_date     TIMESTAMP,
    security       VARCHAR(125),
    status         VARCHAR(10),
    trader         VARCHAR(125),
    benchmark      VARCHAR(125),
    book           VARCHAR(125),
    creation_name  VARCHAR(125),
    creation_date  TIMESTAMP,
    revision_name  VARCHAR(125),
    revision_date  TIMESTAMP,
    deal_name      VARCHAR(125),
    deal_type      VARCHAR(125),
    source_list_id VARCHAR(125),
    side           VARCHAR(125),

    PRIMARY KEY (trade_id)
);

CREATE TABLE CurvePoint
(
    curvePointId INTEGER NOT NULL AUTO_INCREMENT,
    asOfDate     TIMESTAMP,
    term         DOUBLE,
    value        DOUBLE,
    creationDate TIMESTAMP,

    PRIMARY KEY (curvePointId)
);

CREATE TABLE Rating
(
    ratingId     INTEGER NOT NULL AUTO_INCREMENT,
    moodysRating VARCHAR(125),
    sandPRating  VARCHAR(125),
    fitchRating  VARCHAR(125),
    orderNumber  INTEGER,

    PRIMARY KEY (ratingId)
);

CREATE TABLE RuleName
(
    ruleNameId  INTEGER NOT NULL AUTO_INCREMENT,
    name        VARCHAR(125),
    description VARCHAR(125),
    json        VARCHAR(125),
    template    VARCHAR(512),
    sqlStr      VARCHAR(125),
    sqlPart     VARCHAR(125),

    PRIMARY KEY (ruleNameId)
);

CREATE TABLE Users
(
    user_id  INTEGER NOT NULL AUTO_INCREMENT,
    email    VARCHAR(125),
    password VARCHAR(125),
    fullname VARCHAR(125),
    role     VARCHAR(125),

    PRIMARY KEY (user_id)
);

insert into Users(fullname, email, password, role)
values ("Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "ADMIN");
insert into Users(fullname, email, password, role)
values ("User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER");