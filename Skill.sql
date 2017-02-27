--------------------------------------------------------
--  File created - Monday-February-27-2017   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table SKILL
--------------------------------------------------------

  CREATE TABLE "JUNIT"."SKILL" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(20 BYTE), 
	"PARENT_ID" NUMBER
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
REM INSERTING into JUNIT.SKILL
SET DEFINE OFF;
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (1,'JAVA',null);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (2,'Italiano',null);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (3,'Spring',1);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (4,'Batch',3);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (5,'Hibernate',1);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (22,'0',5);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (23,'0',2);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (24,'C#',null);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (9,'1',4);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (10,'2',4);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (21,'0',4);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (11,'1',2);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (12,'2',2);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (13,'3',2);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (14,'4',2);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (15,'5',2);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (16,'6',2);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (17,'3',4);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (18,'1',5);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (19,'2',5);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (20,'3',5);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (25,'MVC',24);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (26,'1',25);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (27,'2',25);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (28,'3',25);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (29,'0',25);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (30,'Cobol',null);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (31,'0',30);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (32,'1',30);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (33,'2',30);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (34,'3',30);
Insert into JUNIT.SKILL (ID,NAME,PARENT_ID) values (35,'4',30);
--------------------------------------------------------
--  DDL for Index SYS_C007280
--------------------------------------------------------

  CREATE UNIQUE INDEX "JUNIT"."SYS_C007280" ON "JUNIT"."SKILL" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  Constraints for Table SKILL
--------------------------------------------------------

  ALTER TABLE "JUNIT"."SKILL" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
