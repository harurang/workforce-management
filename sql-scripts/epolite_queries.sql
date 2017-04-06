
Description: Gets list of a company workers by name
--1
SELECT NAME
FROM PERSON NATURAL JOIN PAID_BY NATURAL JOIN JOB
WHERE JOB.COMP_ID='14';


Description: Gets list of a company staff by salary in descending order
--2
SELECT PAY_RATE, NAME
FROM JOB NATURAL JOIN PAID_BY NATURAL JOIN PERSON
WHERE COMP_ID='14'
ORDER BY PAY_RATE DESC


Description: Gets list of companies labor cost in descending order
--3
SELECT COMP_ID, SUM(PAY_RATE) AS TOTAL_RATE
FROM JOB NATURAL JOIN COMPANY NATURAL JOIN PAID_BY
GROUP BY COMP_ID
ORDER BY TOTAL_RATE DESC;


Description: Gets all jobs a person is currently holding and worked in the
past
--4
SELECT PER_ID, JOB_TITLE, START_DATE, END_DATE
FROM JOB_HISTORY NATURAL JOIN JOB
WHERE PER_ID='2';


Description: Gets a list of all job categories that a person is qualified for
--13
SELECT PER_ID, TITLE
FROM PERSON_SKILL NATURAL JOIN JOB_CATEGORY
WHERE PER_ID='7';


--14



Description: Gets a list of all the names along with emails of the persons
according to his/her skill qualification
--15
SELECT DISTINCT  NAME, EMAIL, JOB_TITLE
FROM PERSON NATURAL JOIN PERSON_SKILL NATURAL JOIN JOB


Description: Gets a list of people who miss only one skill for a specified job
--16
SELECT NAME
FROM PERSON NATURAL JOIN JOB
WHERE JOB_CODE=67
MINUS
SELECT NAME
FROM PERSON NATURAL JOIN PERSON_SKILL
WHERE PERSON_SKILL.KS_CODE=435785;



Description: Gets the skillID and the number of people in the missing-one
list for a given job in the ascending order of the people counts.
--17
SELECT DISTINCT PERSON_SKILL.KS_CODE, COUNT(DISTINCT NAME) AS TOTAL_COUNT
FROM (SELECT NAME
      FROM PERSON NATURAL JOIN JOB
      WHERE JOB_CODE=67
      MINUS
      SELECT NAME
      FROM PERSON NATURAL JOIN PERSON_SKILL
      WHERE PERSON_SKILL.KS_CODE=435785), PERSON_SKILL, JOB
WHERE JOB_CODE=91
GROUP BY PERSON_SKILL.KS_CODE
ORDER BY TOTAL_COUNT ASC;


Description: Gets the list of person who miss the least number of skillss and 
reported the least number
--18
SELECT NAME, COUNT(KNOWLEDGE_SKILL.KS_CODE)
FROM PERSON NATURAL JOIN KNOWLEDGE_SKILL
WHERE KNOWLEDGE_SKILL.KS_CODE=435786
GROUP BY NAME
MINUS
SELECT NAME, PERSON_SKILL.KS_CODE
FROM PERSON NATURAL JOIN PERSON_SKILL
WHERE PERSON_SKILL.KS_CODE=435786;

--19


--20
