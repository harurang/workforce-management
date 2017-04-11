--1
--Description: Gets list of a company workers by name

SELECT NAME
FROM PERSON NATURAL JOIN PAID_BY NATURAL JOIN COMP_JOB
WHERE COMP_ID=19;


--2
--Description: Gets list of a company staff by salary in descending order

SELECT NAME, PAY_RATE
FROM PERSON INNER JOIN PAID_BY
ON PERSON.PER_ID = PAID_BY.PER_ID
INNER JOIN JOB
ON PAID_BY.JOB_CODE = JOB.JOB_CODE
INNER JOIN COMP_JOB
ON PAID_BY.JOB_CODE = COMP_JOB.JOB_CODE
WHERE COMP_ID = 19
AND PAY_TYPE = 'salary'
ORDER BY PAY_RATE DESC;



--3
--Description: Gets list of companies labor cost in descending order

SELECT COMP_ID, SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS TOTAL_SAL
FROM PAID_BY INNER JOIN COMP_JOB
ON PAID_BY.JOB_CODE = COMP_JOB.JOB_CODE
INNER JOIN JOB
ON JOB.JOB_CODE = COMP_JOB.JOB_CODE
GROUP BY COMP_ID
ORDER BY TOTAL_SAL DESC;



--4
--Description: Gets all jobs a person is currently holding and worked in the
past

SELECT PER_ID, JOB_TITLE, START_DATE, END_DATE
FROM JOB_HISTORY NATURAL JOIN JOB
WHERE PER_ID=2;


-- 5
-- Description: Gets the skills for each person.
SELECT PERSON.NAME, KNOWLEDGE_SKILL.KS_CODE 
FROM PERSON LEFT JOIN PERSON_SKILL ON PERSON.PER_ID = PERSON_SKILL.PER_ID 
LEFT JOIN KNOWLEDGE_SKILL ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE;


-- 6 
-- Description: Gets the skill gap between a worker and their job.
SELECT KS_CODE FROM PAID_BY LEFT JOIN JOB_SKILL ON PAID_BY.JOB_CODE = JOB_SKILL.JOB_CODE
WHERE PER_ID=2 
MINUS
SELECT KS_CODE FROM PERSON_SKILL WHERE PER_ID=2;


-- 7
-- a
-- Description: Gets required skills for each job.
SELECT JOB_TITLE, TITLE AS SKILL 
FROM JOB_SKILL LEFT JOIN JOB ON JOB_SKILL.JOB_CODE = JOB.JOB_CODE 
LEFT JOIN KNOWLEDGE_SKILL ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
WHERE IMPORTANCE='required';

-- 7
-- b
-- Description: Gets the core skill for each job category.
SELECT JOB_CATEGORY.TITLE AS JOB_CATEGORY_TITLE, KNOWLEDGE_SKILL.TITLE AS SKILL_TITLE
FROM JOB_CATEGORY LEFT JOIN KNOWLEDGE_SKILL ON JOB_CATEGORY.KS_CODE = KNOWLEDGE_SKILL.KS_CODE;


-- 8
-- Description: Gets the difference between skills required by a job and skills a person has.
SELECT JOB_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE 
FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL 
ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
WHERE JOB_CODE=91
MINUS
SELECT PERSON_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE 
FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL 
ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
WHERE PER_ID=5;


-- 9
-- Description: Gets the course that provides all the skills a person needs for a certain job.
SELECT DISTINCT C.TITLE, C.C_CODE
FROM COURSE C RIGHT JOIN COURSE_KNOWLEDGE S
ON C.C_CODE = S.C_CODE
WHERE NOT EXISTS (

  SELECT JOB_SKILL.KS_CODE
  FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL 
  ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
  WHERE JOB_CODE=44
  MINUS
  SELECT PERSON_SKILL.KS_CODE 
  FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL 
  ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
  WHERE PER_ID=176
  
  MINUS
  (SELECT KS_CODE
  FROM COURSE A RIGHT JOIN COURSE_KNOWLEDGE B
  ON A.C_CODE = B.C_CODE
  WHERE C.TITLE = A.TITLE)
);


-- 10
-- Description: Gets the earliest course date that provides all the skills a person needs for a 
-- certain job.
WITH COURSES AS (
  SELECT TITLE, SECTION.START_DATE, SECTION.END_DATE, SECTION.FORMAT, SECTION.OFFERED_BY FROM SECTION NATURAL JOIN (
    SELECT DISTINCT C.TITLE, C.C_CODE
    FROM COURSE C RIGHT JOIN COURSE_KNOWLEDGE S
    ON C.C_CODE = S.C_CODE
    WHERE NOT EXISTS (
    
      SELECT JOB_SKILL.KS_CODE
      FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL 
      ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
      WHERE JOB_CODE=44
      MINUS
      SELECT PERSON_SKILL.KS_CODE 
      FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL 
      ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
      WHERE PER_ID=176
      
      MINUS
      (SELECT KS_CODE
      FROM COURSE A RIGHT JOIN COURSE_KNOWLEDGE B
      ON A.C_CODE = B.C_CODE
      WHERE C.TITLE = A.TITLE)
    )
  )
)

SELECT TITLE, START_DATE, END_DATE, FORMAT, OFFERED_BY 
FROM COURSES 
WHERE START_DATE = (SELECT MIN(TO_DATE(START_DATE)) FROM COURSES);



-- 11 
-- Description: Gets the cheapest course that provides all the skills a person needs for a specific job.
WITH COURSES AS (
  SELECT TITLE, PRICE, SEC_ID FROM SECTION NATURAL JOIN (
    SELECT DISTINCT C.TITLE, C.C_CODE
    FROM COURSE C RIGHT JOIN COURSE_KNOWLEDGE S
    ON C.C_CODE = S.C_CODE
    WHERE NOT EXISTS (
    
      SELECT JOB_SKILL.KS_CODE
      FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL 
      ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
      WHERE JOB_CODE=44
      MINUS
      SELECT PERSON_SKILL.KS_CODE 
      FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL 
      ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
      WHERE PER_ID=176
      
      MINUS
      (SELECT KS_CODE
      FROM COURSE A RIGHT JOIN COURSE_KNOWLEDGE B
      ON A.C_CODE = B.C_CODE
      WHERE C.TITLE = A.TITLE)
    )
  )
)

SELECT TITLE, PRICE, SEC_ID FROM COURSES WHERE PRICE = (SELECT MIN(PRICE) FROM COURSES);


--16
--Description: Gets a list of people who miss only one skill for a specified job

SELECT NAME
FROM PERSON
WHERE NOT EXISTS (
     SELECT JOB_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE 
FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL 
ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
WHERE JOB_CODE=67
MINUS
SELECT PERSON_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE 
FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL 
ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
);


--17
--Description: Gets the skillID and the number of people in the missing-one
list for a given job in the ascending order of the people counts.

SELECT DISTINCT PERSON_SKILL.KS_CODE, COUNT(DISTINCT NAME) AS TOTAL_COUNT
FROM (
  SELECT NAME
FROM PERSON
WHERE NOT EXISTS (
     SELECT JOB_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE 
FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL 
ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
WHERE JOB_CODE=67
MINUS
SELECT PERSON_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE 
FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL 
ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE)
), PERSON_SKILL, JOB
WHERE JOB_CODE=91
GROUP BY PERSON_SKILL.KS_CODE
ORDER BY TOTAL_COUNT ASC;



--18
--Description: Gets the list of person who miss the least number of skills and 
reported the least number

SELECT NAME, COUNT(DISTINCT JOB_SKILL.KS_CODE) AS NUMB_SKILLS
FROM PERSON, JOB_SKILL
WHERE NOT EXISTS (
SELECT JOB_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE
FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL 
ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
WHERE JOB_CODE=23
MINUS
SELECT PERSON_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE
FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL 
ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE)
GROUP BY NAME;


-- 23
-- Description: Gets max salary or max number of employees of company
WITH COMP_PAYCHECKS AS (
  SELECT SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS SUM_SAL, COMP_NAME FROM PERSON LEFT JOIN PAID_BY NATURAL JOIN JOB NATURAL JOIN COMPANY
  ON PERSON.PER_ID = PAID_BY.PER_ID GROUP BY COMP_NAME
),

COMP_EMPLOYEE_COUNT AS 
(SELECT COMP_NAME, COUNT(*) AS NUMB_EMPLOYEES FROM 
  PERSON LEFT JOIN PAID_BY NATURAL JOIN JOB NATURAL JOIN COMPANY
  ON PERSON.PER_ID = PAID_BY.PER_ID GROUP BY COMP_NAME)

SELECT COMP_NAME, SUM_SAL, NUMB_EMPLOYEES FROM COMP_PAYCHECKS NATURAL JOIN COMP_EMPLOYEE_COUNT
WHERE SUM_SAL = 
(SELECT MAX(SUM_SAL) FROM COMP_PAYCHECKS) OR 
NUMB_EMPLOYEES = (SELECT MAX(NUMB_EMPLOYEES) FROM COMP_EMPLOYEE_COUNT);


-- 24
-- Description: Gets max salary or max number of employees of sector
WITH JOB_COUNT AS (
  SELECT COUNT(*) AS NUMB_JOBS, JOB_TITLE
  FROM COMP_JOB LEFT JOIN JOB
  ON COMP_JOB.JOB_CODE = JOB.JOB_CODE GROUP BY JOB_TITLE
),

JOB_DISTRIBUTION AS (
  SELECT CLUSTER_TITLE, JOB_COUNT.JOB_TITLE, JOB_COUNT.NUMB_JOBS
  FROM JOB_COUNT LEFT JOIN JOB 
  ON JOB_COUNT.JOB_TITLE = JOB.JOB_TITLE NATURAL JOIN JOB_SKILL NATURAL JOIN KNOWLEDGE_SKILL
),

SECTOR_PAYCHECKS AS (
  SELECT SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS SUM_SAL, CLUSTER_TITLE 
  FROM PAID_BY LEFT JOIN JOB 
  ON PAID_BY.JOB_CODE = JOB.JOB_CODE 
  NATURAL JOIN JOB_DISTRIBUTION
  GROUP BY CLUSTER_TITLE
),

SECTOR_EMPLOYEE_COUNT AS 
(SELECT CLUSTER_TITLE, COUNT(*) AS NUMB_EMPLOYEES 
  FROM PAID_BY LEFT JOIN JOB 
  ON PAID_BY.JOB_CODE = JOB.JOB_CODE 
  NATURAL JOIN JOB_DISTRIBUTION
  GROUP BY CLUSTER_TITLE)

SELECT CLUSTER_TITLE, SUM_SAL, NUMB_EMPLOYEES FROM SECTOR_PAYCHECKS NATURAL JOIN SECTOR_EMPLOYEE_COUNT
WHERE SUM_SAL = 
(SELECT MAX(SUM_SAL) FROM SECTOR_PAYCHECKS) OR 
NUMB_EMPLOYEES = (SELECT MAX(NUMB_EMPLOYEES) FROM SECTOR_EMPLOYEE_COUNT);
