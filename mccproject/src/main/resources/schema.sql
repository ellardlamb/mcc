

-- ----------------------------------------------
-- DDL Statements for tables
-- ----------------------------------------------

CREATE TABLE CUSTOMERS(ID INT IDENTITY NOT NULL PRIMARY KEY, CUSTOMER_NAME VARCHAR(255), EMAIL VARCHAR(255), PASSWORD VARCHAR(255));
CREATE TABLE EVENTS(ID INT IDENTITY NOT NULL PRIMARY KEY, EVENT_CODE VARCHAR(255), EVENT_TITLE VARCHAR(255), EVENT_DESCRIPTION VARCHAR(255));
-- ----------------------------------------------
-- DDL Statements for keys
-- ----------------------------------------------

-- primary/unique