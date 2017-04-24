-- Hillary Arurang
-- Eresha Polite 
-- Spring 2017

-- 1
-- Description: List a company’s workers by names. 
SELECT PERSON.NAME
FROM PAID_BY INNER JOIN PERSON
ON PAID_BY.PER_ID = PERSON.PER_ID
INNER JOIN JOB_LISTING
ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID
WHERE COMP_ID = 13;

-- 2
-- Description: List a company’s staff by salary in descending order
SELECT NAME, PAY_RATE 
FROM PERSON INNER JOIN PAID_BY
ON PERSON.PER_ID = PAID_BY.PER_ID
INNER JOIN JOB_LISTING
ON JOB_LISTING.LISTING_ID = PAID_BY.LISTING_ID
INNER JOIN JOB 
ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE
WHERE COMP_ID = 13 
AND PAY_TYPE = 'salary'
ORDER BY PAY_RATE DESC;

-- 3
-- Description: List companies’ labor cost (total salaries and wage rates by 1920 hours) in descending order.  
SELECT COMP_ID, SUM(NVL((PAY_RATE * HOURS) / 1920 , 0)) AS TOTAL_WAGE, 
SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS TOTAL_SAL
FROM PAID_BY INNER JOIN JOB_LISTING
ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID
INNER JOIN JOB
ON JOB.JOB_CODE = JOB_LISTING.JOB_CODE
GROUP BY COMP_ID
ORDER BY TOTAL_SAL DESC;

-- 4
-- Description: Find all the jobs a person is currently holding and worked in the past
SELECT PER_ID, JOB_TITLE, START_DATE, END_DATE
FROM JOB_HISTORY NATURAL JOIN JOB
WHERE PER_ID=2;

-- 5
-- Description: List a person’s knowledge/skills in a readable format.
SELECT PERSON.NAME, KNOWLEDGE_SKILL.KS_CODE 
FROM PERSON INNER JOIN PERSON_SKILL 
ON PERSON.PER_ID = PERSON_SKILL.PER_ID 
INNER JOIN KNOWLEDGE_SKILL 
ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
WHERE PERSON.PER_ID = 209;

-- 6 
-- Description: List the skill gap of a worker between his/her job(s) and his/her skills. 
SELECT KS_CODE 
FROM PAID_BY INNER JOIN JOB_LISTING 
ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID
INNER JOIN JOB_SKILL
ON JOB_SKILL.JOB_CODE = JOB_LISTING.JOB_CODE
WHERE PER_ID=2 

MINUS

-- ger person skills
SELECT KS_CODE FROM PERSON_SKILL WHERE PER_ID=2;

-- 7
-- A
-- Description: List the required knowledge/skills of a job/ a job category in a readable format. (two queries)
SELECT JOB_TITLE, TITLE AS SKILL 
FROM JOB_SKILL INNER JOIN JOB 
ON JOB_SKILL.JOB_CODE = JOB.JOB_CODE 
INNER JOIN KNOWLEDGE_SKILL 
ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
WHERE JOB.JOB_CODE = 23 AND IMPORTANCE='required';

-- 7
-- B
-- Description: List the required knowledge/skills of a job/ a job category in a readable format. (two queries)
SELECT JOB_CATEGORY.TITLE AS JOB_CATEGORY_TITLE, KNOWLEDGE_SKILL.TITLE AS SKILL_TITLE
FROM JOB_CATEGORY INNER JOIN KNOWLEDGE_SKILL 
ON JOB_CATEGORY.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
WHERE CATE_CODE=4;

-- 8
-- Description: List a person’s missing knowledge/skills for a specific job in a readable format.
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
-- Description: List the courses (course id and title) that each alone teaches all the missing knowledge/skills for a person to
-- pursue a specific job.
SELECT DISTINCT C.TITLE, C.C_CODE
FROM COURSE C RIGHT JOIN COURSE_KNOWLEDGE S
ON C.C_CODE = S.C_CODE
WHERE NOT EXISTS (

  SELECT JOB_SKILL.KS_CODE
  FROM JOB_SKILL 
  WHERE JOB_CODE=44
  MINUS
  SELECT PERSON_SKILL.KS_CODE 
  FROM PERSON_SKILL
  WHERE PER_ID=176
  
  MINUS
  (SELECT KS_CODE
  FROM COURSE A RIGHT JOIN COURSE_KNOWLEDGE B
  ON A.C_CODE = B.C_CODE
  WHERE C.TITLE = A.TITLE)
);

-- 10
-- Description: Suppose the skill gap of a worker and the requirement of a desired job can be covered by one course. Find the
-- “quickest” solution for this worker. Show the course, section information and the completion date.
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
-- Description: Find the cheapest course to make up one’s skill gap by showing the course to take and the cost (of the section
-- price). 
WITH COURSES AS (
  SELECT TITLE, PRICE, SEC_ID FROM SECTION NATURAL JOIN (
    SELECT DISTINCT C.TITLE, C.C_CODE
    FROM COURSE C RIGHT JOIN COURSE_KNOWLEDGE S
    ON C.C_CODE = S.C_CODE
    WHERE NOT EXISTS (
    
      SELECT JOB_SKILL.KS_CODE
      FROM JOB_SKILL 
      WHERE JOB_CODE=44
      MINUS
      SELECT PERSON_SKILL.KS_CODE 
      FROM PERSON_SKILL
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

-- 12
-- Provided query. 

--13
-- Provided query. 

--14
-- Provided query.

-- 15
-- Description: List all the names along with the emails of the persons who are qualified for a job. 
SELECT DISTINCT NAME, EMAIL 
FROM PERSON A INNER JOIN PERSON_SKILL B
ON A.PER_ID = B.PER_ID 
WHERE NOT EXISTS (

  -- get skills of specific job
  SELECT JOB_SKILL.KS_CODE
  FROM JOB_SKILL
  WHERE JOB_CODE=44
  
  MINUS
  
  -- get skills of person
  (SELECT KS_CODE
  FROM PERSON C INNER JOIN PERSON_SKILL D 
  ON C.PER_ID = D.PER_ID
  WHERE A.NAME = C.NAME)
);

-- 16
-- Description: When a company cannot find any qualified person for a job, a secondary solution is to find a person who is almost
-- qualified to the job. Make a “missing-one” list that lists people who miss only one skill for a specified job. 
WITH PERSON_REQUIRED_SKILL_CNT AS (
  SELECT PER_ID, COUNT(KS_CODE) AS SKILL_COUNT
  FROM PERSON_SKILL NATURAL JOIN JOB_SKILL
  WHERE JOB_CODE = 31
  GROUP BY PER_ID
)

SELECT PER_ID
FROM PERSON_REQUIRED_SKILL_CNT
WHERE SKILL_COUNT = (SELECT COUNT(*) - 1
FROM JOB_SKILL
WHERE JOB_CODE = 31);

-- 17
-- Description: List the skillID and the number of people in the missing-one list for a given job code in the ascending order of the
-- people counts. 
WITH PERSON_REQUIRED_SKILL_CNT AS (
  SELECT PER_ID, COUNT(KS_CODE) AS SKILL_COUNT
  FROM PERSON_SKILL NATURAL JOIN JOB_SKILL
  WHERE JOB_CODE = 31
  GROUP BY PER_ID
),

MISSING_ONE AS (
  SELECT PER_ID
  FROM PERSON_REQUIRED_SKILL_CNT
  WHERE SKILL_COUNT = (SELECT COUNT(*) - 1
  FROM JOB_SKILL
  WHERE JOB_CODE = 31)
),

MISSING_AND_SUPRLUS_SKILLS AS (
  SELECT KS_CODE
  FROM PERSON_SKILL A INNER JOIN MISSING_ONE
  ON A.PER_ID = MISSING_ONE.PER_ID
  
  WHERE EXISTS (
    -- get skills of job
    SELECT KS_CODE 
    FROM JOB_SKILL
    WHERE JOB_CODE = 31
    
    MINUS
    
    -- skills of people
    SELECT KS_CODE
    FROM MISSING_ONE
    INNER JOIN PERSON 
    ON MISSING_ONE.PER_ID = PERSON.PER_ID
    INNER JOIN PERSON_SKILL B
    ON PERSON.PER_ID = B.PER_ID
    WHERE A.KS_CODE = B.KS_CODE
  )
)

SELECT JOB_SKILL.KS_CODE, COUNT(JOB_SKILL.KS_CODE) AS NUMB_MISSING_SKILL
FROM JOB_SKILL INNER JOIN MISSING_AND_SUPRLUS_SKILLS
ON JOB_SKILL.KS_CODE = MISSING_AND_SUPRLUS_SKILLS.KS_CODE
WHERE JOB_SKILL.JOB_CODE = 31
GROUP BY JOB_SKILL.KS_CODE
ORDER BY NUMB_MISSING_SKILL ASC;

-- 18
-- Description: Suppose there is a new job that has nobody qualified. List the persons who miss the least number of skills and
-- report the “least number”. 
WITH NEEDED_SKILLS AS (
SELECT KS_CODE
FROM JOB_SKILL
WHERE JOB_CODE=23),

COUNT_NEEDED_SKILLS(PER_ID, MISSING_AMOUNT) AS (
SELECT PER_ID, COUNT(KS_CODE)
FROM PERSON P, NEEDED_SKILLS
WHERE KS_CODE IN (
SELECT KS_CODE 
FROM NEEDED_SKILLS
MINUS
SELECT KS_CODE 
FROM PERSON_SKILL
WHERE PER_ID=P.PER_ID)
GROUP BY PER_ID
)
SELECT PER_ID, MISSING_AMOUNT
FROM COUNT_NEEDED_SKILLS
WHERE MISSING_AMOUNT = (
SELECT MIN(MISSING_AMOUNT)
FROM COUNT_NEEDED_SKILLS)
ORDER BY PER_ID ASC;

-- 19
-- Description: For a specified job category and a given small number k, make a “missing-k” list that lists the people’s IDs and
-- the number of missing skills for the people who miss only up to k skills in the ascending order of missing skills. 
WITH MISSING_SKILLS AS (
SELECT KS_CODE
FROM JOB_CATEGORY
WHERE CATE_CODE=11),

COUNT_MISSING_SKILLS(PER_ID, MISSING_AMOUNT) AS (
SELECT PER_ID, COUNT(KS_CODE)
FROM PERSON P, MISSING_SKILLS
WHERE KS_CODE IN (
SELECT KS_CODE 
FROM MISSING_SKILLS

MINUS

SELECT KS_CODE
FROM PERSON_SKILL
WHERE PER_ID=P.PER_ID)
GROUP BY PER_ID
)
SELECT PER_ID, MISSING_AMOUNT
FROM COUNT_MISSING_SKILLS
WHERE MISSING_AMOUNT <=2
ORDER BY MISSING_AMOUNT ASC;

-- 20
-- Description: Given a job category code and its corresponding missing-k list specified in Question 19. Find every skill that is
-- needed by at least one person in the given missing-k list. List each skillID and the number of people who need it
-- in the descending order of the people counts.
WITH MISSING_SKILLS AS (
SELECT KS_CODE
FROM JOB_CATEGORY
WHERE CATE_CODE=11),

COUNT_MISSING_SKILLS(PER_ID, MISSING_AMOUNT) AS (
SELECT PER_ID, COUNT(KS_CODE)
FROM PERSON P, MISSING_SKILLS
WHERE KS_CODE IN (
SELECT KS_CODE 
FROM MISSING_SKILLS

MINUS

SELECT KS_CODE
FROM PERSON_SKILL
WHERE PER_ID=P.PER_ID)
GROUP BY PER_ID
),

MISSING_K AS (
SELECT PER_ID
FROM COUNT_MISSING_SKILLS
WHERE MISSING_AMOUNT <=2),

PERSON_SKILLS_COUNT(KS_CODE, SKILLS_COUNT) AS (
SELECT KS_CODE, COUNT(PER_ID)
FROM MISSING_K P, MISSING_SKILLS
WHERE KS_CODE IN (
SELECT KS_CODE
FROM MISSING_SKILLS

MINUS

SELECT KS_CODE
FROM PERSON_SKILL
WHERE PER_ID=P.PER_ID)
GROUP BY KS_CODE
)
SELECT KS_CODE, SKILLS_COUNT
FROM PERSON_SKILLS_COUNT
ORDER BY SKILLS_COUNT DESC;

-- 21
-- Description: In a local or national crisis, we need to find all the people who once held a job of the special job category
-- identifier. 
SELECT NAME 
FROM JOB_HISTORY INNER JOIN PERSON
ON PERSON.PER_ID = JOB_HISTORY.PER_ID
INNER JOIN JOB_LISTING 
ON JOB_HISTORY.LISTING_ID = JOB_LISTING.LISTING_ID
INNER JOIN JOB
ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE
INNER JOIN JOB_CATEGORY
ON JOB.CATE_CODE = JOB_CATEGORY.CATE_CODE 
WHERE JOB_CATEGORY.TITLE = 'Computer User Support Specialists';

-- 22
-- Description: Find all the unemployed people who once held a job of the given job identifier
WITH UNEMPLOYED AS (
  SELECT PER_ID, NAME 
  FROM PERSON
  MINUS 
  SELECT PERSON.PER_ID, PERSON.NAME
  FROM PAID_BY INNER JOIN PERSON
  ON PAID_BY.PER_ID = PERSON.PER_ID
)

SELECT NAME 
FROM UNEMPLOYED INNER JOIN JOB_HISTORY 
ON JOB_HISTORY.PER_ID = UNEMPLOYED.PER_ID
INNER JOIN JOB_LISTING
ON JOB_LISTING.LISTING_ID = JOB_HISTORY.LISTING_ID
INNER JOIN JOB
ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE
INNER JOIN JOB_CATEGORY
ON JOB.CATE_CODE = JOB_CATEGORY.CATE_CODE 
WHERE JOB_CATEGORY.TITLE = 'Computer User Support Specialists';

-- 23
-- Description: Find out the biggest employer in terms of number of employees or the total amount of salaries and wages paid to
-- employees. 

-- Gets sum of salary or wages for each company
WITH COMP_PAYCHECKS AS (
  SELECT SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS SUM_SAL, COMP_NAME 
  FROM PAID_BY INNER JOIN JOB_LISTING
  ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID
  INNER JOIN JOB
  ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE
  INNER JOIN COMPANY
  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID
  GROUP BY COMP_NAME
),

-- Gets number of employees for each company 
COMP_EMPLOYEE_COUNT AS (
SELECT COMP_NAME, COUNT(*) AS NUMB_EMPLOYEES 
  FROM PAID_BY INNER JOIN JOB_LISTING
  ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID
  INNER JOIN COMPANY
  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID
  GROUP BY COMP_NAME
)

SELECT COMP_NAME, SUM_SAL, NUMB_EMPLOYEES FROM COMP_PAYCHECKS NATURAL JOIN COMP_EMPLOYEE_COUNT
WHERE SUM_SAL = 
(SELECT MAX(SUM_SAL) FROM COMP_PAYCHECKS) OR 
NUMB_EMPLOYEES = (SELECT MAX(NUMB_EMPLOYEES) FROM COMP_EMPLOYEE_COUNT);

-- 24
-- Description: Find out the job distribution among business sectors; find out the biggest sector in terms of number of employees
-- or the total amount of salaries and wages paid to employees

-- Total paycheck by sector 
WITH SECTOR_PAYCHECKS AS (
  SELECT SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS SUM_SAL, PRIMARY_SECTOR 
  FROM PAID_BY INNER JOIN JOB_LISTING
  ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID
  INNER JOIN JOB 
  ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE
  INNER JOIN COMPANY
  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID
  GROUP BY PRIMARY_SECTOR
),

-- Number of employees by sector 
SECTOR_EMPLOYEE_COUNT AS (
  SELECT PRIMARY_SECTOR, COUNT(*) AS NUMB_EMPLOYEES 
  FROM PAID_BY INNER JOIN JOB_LISTING
  ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID
  INNER JOIN COMPANY
  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID
  GROUP BY PRIMARY_SECTOR
)

SELECT PRIMARY_SECTOR, SUM_SAL, NUMB_EMPLOYEES FROM SECTOR_PAYCHECKS NATURAL JOIN SECTOR_EMPLOYEE_COUNT
WHERE SUM_SAL = 
(SELECT MAX(SUM_SAL) FROM SECTOR_PAYCHECKS) OR 
NUMB_EMPLOYEES = (SELECT MAX(NUMB_EMPLOYEES) FROM SECTOR_EMPLOYEE_COUNT);

-- 25
-- Description: Find out the ratio between the people whose earnings increase and those whose earning decrease; find the average
-- rate of earning improvement for the workers in a specific business sector. 

-- Gets the previous salary of employees in the software engineering primary sector 
WITH PREVIOUS_SAL AS (
  -- get the previous salary or get the previous pay rate * hours
  SELECT NAME, SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS OLD_SAL
  FROM PERSON 
  INNER JOIN JOB_HISTORY 
  ON PERSON.PER_ID = JOB_HISTORY.PER_ID
  INNER JOIN JOB_LISTING
  ON JOB_HISTORY.LISTING_ID = JOB_LISTING.LISTING_ID
  INNER JOIN JOB
  ON JOB.JOB_CODE = JOB_LISTING.JOB_CODE
  INNER JOIN COMPANY
  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID
  WHERE PRIMARY_SECTOR = 'SOFTWARE ENGINEERING'
  GROUP BY NAME
),

-- gets the current salary of employes in the software engineering primary sector 
CURRENT_SAL AS (
  -- Get the current salary or get the current pay rate * hours 
  SELECT NAME, SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS NEW_SAL 
  FROM PERSON 
  INNER JOIN PAID_BY 
  ON PERSON.PER_ID = PAID_BY.PER_ID
  INNER JOIN JOB_LISTING
  ON JOB_LISTING.LISTING_ID = PAID_BY.LISTING_ID
  INNER JOIN JOB
  ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE
  INNER JOIN COMPANY
  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID
  WHERE PRIMARY_SECTOR = 'SOFTWARE ENGINEERING'
  GROUP BY NAME
),

INCREASE AS (
  SELECT PREVIOUS_SAL.NAME, NEW_SAL/OLD_SAL AS RATIO FROM PREVIOUS_SAL 
  INNER JOIN CURRENT_SAL
  ON PREVIOUS_SAL.NAME = CURRENT_SAL.NAME
  WHERE NEW_SAL/OLD_SAL > 1
)

SELECT AVG(RATIO) AS AVERAGE_INCREASE FROM INCREASE;
 
-- 26
-- Description: Find the leaf-node job categories that have the most openings due to lack of qualified workers. If there are many
-- opening jobs of a job category but at the same time there are many qualified jobless people. Then training cannot
-- help fill up this type of job. What we want to find is such a job category that has the largest difference between
-- vacancies (the unfilled jobs of this category) and the number of jobless people who are qualified for the jobs of
-- this category. 

-- Jobs with openings 
WITH OPENINGS AS (
  SELECT JOB_CODE, COUNT(JOB_CODE) AS NUMB_OPENINGS
  FROM JOB_LISTING
  NATURAL JOIN (
    -- all job listings
    SELECT JOB.JOB_CODE
    FROM JOB INNER JOIN JOB_LISTING
    ON JOB.JOB_CODE = JOB_LISTING.JOB_CODE
    MINUS
    -- filled job listings 
    SELECT JOB.JOB_CODE
    FROM PAID_BY INNER JOIN JOB_LISTING
    ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID
    INNER JOIN JOB
    ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE)
    GROUP BY JOB_CODE
),

-- people who do not have a job 
UNEMPLOYED AS (
  SELECT PER_ID, NAME 
  FROM PERSON
  MINUS 
  SELECT PERSON.PER_ID, PERSON.NAME
  FROM PAID_BY INNER JOIN PERSON
  ON PAID_BY.PER_ID = PERSON.PER_ID
),

-- Number skills each unemployed person has in common with each opening 
NUMB_SKILLS_BY_PERSON AS (
  SELECT UNEMPLOYED.NAME, OPENINGS.JOB_CODE, COUNT(JOB_SKILL.KS_CODE) AS NUMBPERSKILLS
  FROM UNEMPLOYED INNER JOIN PERSON_SKILL
  ON UNEMPLOYED.PER_ID = PERSON_SKILL.PER_ID
  INNER JOIN JOB_SKILL 
  ON PERSON_SKILL.KS_CODE = JOB_SKILL.KS_CODE
  INNER JOIN OPENINGS
  ON JOB_SKILL.JOB_CODE = OPENINGS.JOB_CODE
  GROUP BY NAME, OPENINGS.JOB_CODE
),

-- Number of skills required for each opening 
NUMB_SKILLS_BY_JOB AS (
  SELECT OPENINGS.JOB_CODE, COUNT(JOB_SKILL.KS_CODE) AS NUMBJOBSKILLS
  FROM JOB_SKILL INNER JOIN OPENINGS
  ON JOB_SKILL.JOB_CODE = OPENINGS.JOB_CODE
  GROUP BY OPENINGS.JOB_CODE
),

-- Unemployed ppl that are qualifeid for an opening 
QUALIFIED AS (
  SELECT NUMB_SKILLS_BY_PERSON.NAME, NUMB_SKILLS_BY_JOB.JOB_CODE, COUNT(NUMB_SKILLS_BY_JOB.JOB_CODE) AS NUMB_QUALIFIED
  FROM NUMB_SKILLS_BY_PERSON INNER JOIN  NUMB_SKILLS_BY_JOB
  ON NUMB_SKILLS_BY_PERSON.JOB_CODE = NUMB_SKILLS_BY_JOB.JOB_CODE
  WHERE (NUMB_SKILLS_BY_JOB.NUMBJOBSKILLS - NUMB_SKILLS_BY_PERSON.NUMBPERSKILLS) = 0
  GROUP BY NUMB_SKILLS_BY_PERSON.NAME, NUMB_SKILLS_BY_JOB.JOB_CODE
),

-- sum(vacancies - qualifeid) according to job category 
DIFFERENCES AS (
  SELECT CATE_CODE, SUM(OPENINGS.NUMB_OPENINGS - QUALIFIED.NUMB_QUALIFIED) AS DIFF
  FROM OPENINGS NATURAL JOIN QUALIFIED NATURAL JOIN JOB
  GROUP BY CATE_CODE
)  

-- select max difference
SELECT CATE_CODE
FROM DIFFERENCES 
WHERE DIFF = (SELECT MAX(DIFF) FROM DIFFERENCES);

-- 27
-- Description: Find the courses that can help most jobless people find a job by training them toward the jobs of this category that
-- have the most openings due to lack of qualified workers.

-- jobs with openings 
WITH OPENINGS AS (
  SELECT JOB_CODE, COUNT(JOB_CODE) AS NUMB_OPENINGS
  FROM JOB_LISTING
  NATURAL JOIN (
    -- all job listings 
    SELECT JOB.JOB_CODE
    FROM JOB INNER JOIN JOB_LISTING
    ON JOB.JOB_CODE = JOB_LISTING.JOB_CODE
    MINUS
    -- filled job listings 
    SELECT JOB.JOB_CODE
    FROM PAID_BY INNER JOIN JOB_LISTING
    ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID
    INNER JOIN JOB
    ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE)
    GROUP BY JOB_CODE
),

-- people who do not have a job
UNEMPLOYED AS (
  SELECT PER_ID, NAME 
  FROM PERSON
  MINUS 
  SELECT PERSON.PER_ID, PERSON.NAME
  FROM PAID_BY INNER JOIN PERSON
  ON PAID_BY.PER_ID = PERSON.PER_ID
),

-- number of skills each unemployed person has in common with each opening 
NUMB_SKILLS_BY_PERSON AS (
  SELECT UNEMPLOYED.NAME, OPENINGS.JOB_CODE, COUNT(JOB_SKILL.KS_CODE) AS NUMBPERSKILLS
  FROM UNEMPLOYED INNER JOIN PERSON_SKILL
  ON UNEMPLOYED.PER_ID = PERSON_SKILL.PER_ID
  INNER JOIN JOB_SKILL 
  ON PERSON_SKILL.KS_CODE = JOB_SKILL.KS_CODE
  INNER JOIN OPENINGS
  ON JOB_SKILL.JOB_CODE = OPENINGS.JOB_CODE
  GROUP BY NAME, OPENINGS.JOB_CODE
),

-- number of skills required for each opening 
NUMB_SKILLS_BY_JOB AS (
  SELECT OPENINGS.JOB_CODE, COUNT(JOB_SKILL.KS_CODE) AS NUMBJOBSKILLS
  FROM JOB_SKILL INNER JOIN OPENINGS
  ON JOB_SKILL.JOB_CODE = OPENINGS.JOB_CODE
  GROUP BY OPENINGS.JOB_CODE
),

-- unemployed ppl that are qualified for an opening 
QUALIFIED AS (
  SELECT NUMB_SKILLS_BY_PERSON.NAME, NUMB_SKILLS_BY_JOB.JOB_CODE, COUNT(NUMB_SKILLS_BY_JOB.JOB_CODE) AS NUMB_QUALIFIED
  FROM NUMB_SKILLS_BY_PERSON INNER JOIN  NUMB_SKILLS_BY_JOB
  ON NUMB_SKILLS_BY_PERSON.JOB_CODE = NUMB_SKILLS_BY_JOB.JOB_CODE
  WHERE (NUMB_SKILLS_BY_JOB.NUMBJOBSKILLS - NUMB_SKILLS_BY_PERSON.NUMBPERSKILLS) = 0
  GROUP BY NUMB_SKILLS_BY_PERSON.NAME, NUMB_SKILLS_BY_JOB.JOB_CODE
),

-- sum(vacancies - qualified) according to job category 
DIFFERENCES AS (
  SELECT CATE_CODE, SUM(OPENINGS.NUMB_OPENINGS - QUALIFIED.NUMB_QUALIFIED) AS DIFF
  FROM OPENINGS NATURAL JOIN QUALIFIED NATURAL JOIN JOB
  GROUP BY CATE_CODE
),

COURSES AS (
  SELECT C.TITLE, C.C_CODE, COUNT(DISTINCT E.PER_ID) NUMB_PPL_COURSE_QUALIFIES
  FROM COURSE C INNER JOIN COURSE_KNOWLEDGE S
  ON C.C_CODE = S.C_CODE
  INNER JOIN PERSON_SKILL
  ON PERSON_SKILL.KS_CODE = S.KS_CODE
  INNER JOIN UNEMPLOYED E
  ON PERSON_SKILL.PER_ID = E.PER_ID
  WHERE NOT EXISTS (
  
    -- get all skills required by job category 
    SELECT JOB_SKILL.KS_CODE
    FROM JOB_SKILL INNER JOIN JOB
    ON JOB_SKILL.JOB_CODE = JOB.JOB_CODE
    WHERE JOB.CATE_CODE = (
      SELECT CATE_CODE
      FROM DIFFERENCES 
      WHERE DIFF = (SELECT MAX(DIFF) FROM DIFFERENCES))
    MINUS
    -- get all skills an unemployed person has 
    SELECT PERSON_SKILL.KS_CODE  
    FROM UNEMPLOYED INNER JOIN PERSON_SKILL
    ON UNEMPLOYED.PER_ID = PERSON_SKILL.PER_ID
    
    MINUS
    -- get all the skills a course offers 
    (SELECT PERSON_SKILL.KS_CODE
    FROM COURSE A INNER JOIN COURSE_KNOWLEDGE B
    ON A.C_CODE = B.C_CODE
    INNER JOIN PERSON_SKILL
    ON PERSON_SKILL.KS_CODE = B.KS_CODE
    INNER JOIN UNEMPLOYED D
    ON PERSON_SKILL.PER_ID = D.PER_ID
    WHERE C.TITLE = A.TITLE
    AND D.PER_ID = E.PER_ID)
  )
  GROUP BY C.TITLE, C.C_CODE
)

SELECT TITLE, NUMB_PPL_COURSE_QUALIFIES
FROM COURSES
WHERE NUMB_PPL_COURSE_QUALIFIES = 
  (SELECT MAX(NUMB_PPL_COURSE_QUALIFIES) FROM COURSES);
