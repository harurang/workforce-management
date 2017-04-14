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

-- 12
-- Provided query. 

-- 21
-- Description: Finds people who once held a job of specific job category.
select name 
from person inner join job_history 
on person.per_id = job_history.per_id
inner join job
on job_history.job_code = job.job_code
inner join job_category
on job.cate_code = job_category.cate_code 
where job_category.title = 'Computer User Support Specialists';

-- 22
-- Description: Finds unemployed people who once held a job of a specific job cateogry. 
with unemployed as (
  select per_id, name 
  from person
  minus 
  select person.per_id, person.name
  from paid_by inner join person
  on paid_by.per_id = person.per_id
)

select name 
from unemployed inner join job_history 
on unemployed.per_id = job_history.per_id
inner join job
on job_history.job_code = job.job_code
inner join job_category
on job.cate_code = job_category.cate_code 
where job_category.title = 'Computer User Support Specialists';


-- 23
-- Description: Gets max salary or max number of employees of company

-- gets sum of salary or wages for each company
WITH COMP_PAYCHECKS AS (
  SELECT SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS SUM_SAL, COMP_NAME 
  FROM JOB_LISTING INNER JOIN JOB
  ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE
  INNER JOIN COMP_JOB
  ON JOB.JOB_CODE = COMP_JOB.JOB_CODE
  INNER JOIN COMPANY
  ON COMP_JOB.COMP_ID = COMPANY.COMP_ID
  GROUP BY COMP_NAME
),

-- gets number of employees for each company
COMP_EMPLOYEE_COUNT AS (
SELECT COMP_NAME, COUNT(*) AS NUMB_EMPLOYEES FROM 
  JOB_LISTING INNER JOIN COMPANY
  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID
  GROUP BY COMP_NAME
)

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

-- 25
-- Test Data:
-- Job's associated with Software Enginnering sector: 31, 23
-- People assocaited with jobs 31 and 23: 2 (Jane), 7 (Vanessa), 234 (Lily)

-- Gets the previous salary of employees in the Database primary sector
with previous_sal as (
  -- get the previous salary or get the previous pay rate * hours
  select name, sum(nvl(pay_rate,0) + nvl(hours * pay_rate, 0)) as old_sal
  from person 
  inner join job_history 
  on person.per_id = job_history.per_id
  inner join job
  on job_history.job_code = job.job_code
  inner join comp_job
  on job.job_code = comp_job.job_code
  inner join company
  on comp_job.comp_id = company.comp_id
  where primary_sector = 'Software Engineering'
  group by name
),

-- Gets the current salary of employees in the Database primary sector
current_sal as (
  -- get the current salary or get the current pay rate * hours
  select name, sum(nvl(pay_rate,0) + nvl(hours * pay_rate, 0)) as new_sal 
  from person 
  inner join paid_by 
  on person.per_id = paid_by.per_id
  inner join job
  on paid_by.job_code = job.job_code
  inner join comp_job
  on job.job_code = comp_job.job_code
  inner join company
  on comp_job.comp_id = company.comp_id
  where primary_sector = 'Software Engineering'
  group by name
),

increase as (
  select previous_sal.name, new_sal/old_sal as ratio from previous_sal 
  inner join current_sal
  on previous_sal.name = current_sal.name
  where new_sal/old_sal > 1
)

select avg(ratio) as average_increase from increase;

 
-- 26
-- Not complete.
-- Description: Find a job category that has the largest difference between
-- vacancies and the number of jobless people who are qualified for the jobs of this category.

-- vacant jobs
with vacant_jobs as (
  select job_code, cate_code
  from job 
  minus
  select paid_by.job_code, job.cate_code
  from paid_by inner join job
  on paid_by.job_code = job.job_code
),

-- people who do not have a job
jobless as (
  select per_id, name 
  from person
  minus 
  select person.per_id, person.name
  from paid_by inner join person
  on paid_by.per_id = person.per_id
)

-- find qualified ppl per job 


-- how to find vacancies 
-- find qualified ppl 
max(vacancies - qualified ppl)

-- Get count of qualified people per job

-- for each job
SELECT JOB_CODE, COUNT(*) AS QUAL_PPL
FROM JOB A 
WHERE NOT EXISTS (
  -- get skills of each person 
  SELECT KS_CODE 
  FROM PERSON INNER JOIN PERSON_SKILL
  ON PERSON.PER_ID = PERSON_SKILL.PER_ID
  
  MINUS
  
  -- get skills of job
  SELECT KS_CODE 
  FROM JOB B INNER JOIN JOB_SKILL
  ON B.JOB_CODE = JOB_SKILL.JOB_CODE
  WHERE A.JOB_CODE = B.JOB_CODE)
GROUP BY JOB_CODE;
  



