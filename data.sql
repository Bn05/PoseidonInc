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

CREATE TABLE Curve_point
(
    curve_point_id INTEGER NOT NULL AUTO_INCREMENT,
    as_of_date     TIMESTAMP,
    term           DOUBLE,
    value          DOUBLE,
    creation_date  TIMESTAMP,

    PRIMARY KEY (curve_point_id)
);

CREATE TABLE Rating
(
    rating_id     INTEGER NOT NULL AUTO_INCREMENT,
    moodys_rating VARCHAR(125),
    sandprating   VARCHAR(125),
    fitch_rating  VARCHAR(125),
    order_number  INTEGER,

    PRIMARY KEY (rating_id)
);

CREATE TABLE Rule_name
(
    rule_name_id INTEGER NOT NULL AUTO_INCREMENT,
    name         VARCHAR(125),
    description  VARCHAR(125),
    json         VARCHAR(125),
    template     VARCHAR(512),
    sql_str      VARCHAR(125),
    sql_part     VARCHAR(125),

    PRIMARY KEY (rule_name_id)
);

CREATE TABLE Users
(
    user_id   INTEGER NOT NULL AUTO_INCREMENT,
    user_name     VARCHAR(125),
    password  VARCHAR(125),
    full_name VARCHAR(125),
    role      VARCHAR(125),
    provider  VARCHAR(7),

    PRIMARY KEY (user_id)
);

