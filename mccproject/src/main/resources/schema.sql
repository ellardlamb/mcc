

-- ----------------------------------------------
-- DDL Statements for tables
-- ----------------------------------------------

CREATE TABLE CUSTOMERS(ID INT IDENTITY NOT NULL PRIMARY KEY, CUSTOMER_NAME VARCHAR(255), EMAIL VARCHAR(255), PASSWORD VARCHAR(255));
-- ----------------------------------------------
-- DDL Statements for keys
-- ----------------------------------------------

-- primary/unique
-- ALTER TABLE "CUSTOMERS" ADD CONSTRAINT "SQL120325130144011" PRIMARY KEY ("ID");

-- CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))
-- CREATE TABLE CUSTOMERS(ID INT PRIMARY KEY, CUSTOMER_NAME ))