-- 5
SELECT NAME, TITLE FROM PERSON NATURAL JOIN PERSON_SKILL NATURAL JOIN KNOWLEDGE_SKILL;

-- 6 
SELECT KS_CODE FROM PAID_BY NATURAL JOIN JOB_SKILL WHERE PER_ID=2 
MINUS
SELECT KS_CODE FROM PERSON_SKILL WHERE PER_ID=2;

-- 7
-- a
SELECT JOB_TITLE, TITLE AS SKILL 
FROM JOB NATURAL JOIN JOB_SKILL NATURAL JOIN KNOWLEDGE_SKILL;

-- b
