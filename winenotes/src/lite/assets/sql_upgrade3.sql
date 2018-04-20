UPDATE "main_flag" SET name = 'To Buy' WHERE _id = 1;
UPDATE "main_flag" SET name = 'Undecided' WHERE _id = 2;
UPDATE "main_flag" SET name = 'Blacklist' WHERE _id = 3;
INSERT INTO "main_flag" VALUES(4,'Cellar',60,NULL,NULL);
INSERT INTO "main_flag" VALUES(5,'Consumed',70,NULL,NULL);
INSERT INTO "main_flag" VALUES(6,'Other',80,NULL,NULL);
UPDATE main_wine SET flag_id = 6 WHERE flag_id IS NULL OR flag_id = 0;
