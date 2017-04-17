-- 3
-- Description: List companies’ labor cost (total salaries and wage rates by 1920 hours) in descending order. 
SELECT COMP_ID, SUM(nvl((PAY_RATE * HOURS) / 1920 , 0)) AS TOTAL_WAGE, 
SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS TOTAL_SAL
FROM PAID_BY INNER JOIN JOB_LISTING
ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID
INNER JOIN JOB
ON JOB.JOB_CODE = JOB_LISTING.JOB_CODE
GROUP BY COMP_ID
ORDER BY TOTAL_SAL DESC;

-- 5
-- Description: Gets the skills for each person.
SELECT PERSON.NAME, KNOWLEDGE_SKILL.KS_CODE 
FROM PERSON LEFT JOIN PERSON_SKILL ON PERSON.PER_ID = PERSON_SKILL.PER_ID 
LEFT JOIN KNOWLEDGE_SKILL ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE;

-- 6 
-- Description: Gets the skill gap between a worker and their job.
-- get job skills of person
SELECT KS_CODE 
FROM PAID_BY INNER JOIN JOB_LISTING 
ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID
INNER JOIN JOB_SKILL
ON JOB_SKILL.JOB_CODE = JOB_LISTING.JOB_CODE
WHERE PER_ID=2 

MINUS

-- get person skills 
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

-- Description: Gets a list of people and their email who are qualified for a specific job. 
--15
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
WITH PERSON_REQUIRED_SKILL_CNT AS (
  SELECT PER_ID, COUNT(KS_CODE) AS SKILL_COUNT
  FROM PERSON_SKILL NATURAL JOIN JOB_SKILL
  WHERE JOB_CODE = 31
  GROUP BY PER_ID
),

MISSING_ONE as (
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

-- 21
-- Description: Finds people who once held a job of specific job category.
select name 
from job_history inner join person
on person.per_id = job_history.per_id
inner join job_listing 
on job_history.listing_id = job_listing.listing_id
inner join job
on job_listing.job_code = job.job_code
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
on job_history.per_id = unemployed.per_id
inner join job_listing
on job_listing.listing_id = job_history.listing_id
inner join job
on job_listing.job_code = job.job_code
inner join job_category
on job.cate_code = job_category.cate_code 
where job_category.title = 'Computer User Support Specialists';

-- 23
-- Description: Gets max salary or max number of employees of company

-- gets sum of salary or wages for each company
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

-- gets number of employees for each company
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
-- Description: Gets max salary or max number of employees of sector

-- total paycheck by sector
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

-- number of employees by sector
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
-- Finds the average salary increase for workers in a specific sector. 

-- Gets the previous salary of employees in the Software Engineering primary sector
with previous_sal as (
  -- get the previous salary or get the previous pay rate * hours
  select name, sum(nvl(pay_rate,0) + nvl(hours * pay_rate, 0)) as old_sal
  from person 
  inner join job_history 
  on person.per_id = job_history.per_id
  inner join job_listing
  on job_history.listing_id = job_listing.listing_id
  inner join job
  on job.job_code = job_listing.job_code
  inner join company
  on job_listing.comp_id = company.comp_id
  where primary_sector = 'Software Engineering'
  group by name
),

-- Gets the current salary of employees in the Software Engineering primary sector
current_sal as (
  -- get the current salary or get the current pay rate * hours
  select name, sum(nvl(pay_rate,0) + nvl(hours * pay_rate, 0)) as new_sal 
  from person 
  inner join paid_by 
  on person.per_id = paid_by.per_id
  inner join job_listing
  on job_listing.listing_id = paid_by.listing_id
  inner join job
  on job_listing.job_code = job.job_code
  inner join company
  on job_listing.comp_id = company.comp_id
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
-- Description: Find a job category that has the largest difference between
-- vacancies and the number of jobless people who are qualified for the jobs of this category.

-- jobs with openings not numb of openings 
with openings as (
  select job_code, count(job_code) as numb_openings
  from job_listing
  natural join (
    -- all job listings
    select job.job_code
    from job inner join job_listing
    on job.job_code = job_listing.job_code
    minus
    -- filled job listings
    select job.job_code
    from paid_by inner join job_listing
    on paid_by.listing_id = job_listing.listing_id
    inner join job
    on job_listing.job_code = job.job_code)
    group by job_code
),

-- people who do not have a job
unemployed as (
  select per_id, name 
  from person
  minus 
  select person.per_id, person.name
  from paid_by inner join person
  on paid_by.per_id = person.per_id
),

-- number skills each unemployed person has in common with an opening
numbSkillsByPerson as (
  select unemployed.name, openings.job_code, count(job_skill.ks_code) as numbPerSkills
  from unemployed inner join person_skill
  on unemployed.per_id = person_skill.per_id
  inner join job_skill 
  on person_skill.ks_code = job_skill.ks_code
  inner join openings
  on job_skill.job_code = openings.job_code
  group by name, openings.job_code
),

-- number of skills required for each opening 
numbSkillsByJob as (
  select openings.job_code, count(job_skill.ks_code) as numbJobSkills
  from job_skill inner join openings
  on job_skill.job_code = openings.job_code
  group by openings.job_code
),

-- unemployed ppl that are qualified for an opening
qualified as (
  select numbSkillsByPerson.name, numbSkillsByJob.job_code, count(numbSkillsByJob.job_code) as numb_qualified
  from numbSkillsByPerson inner join  numbSkillsByJob
  on numbSkillsByPerson.job_code = numbSkillsByJob.job_code
  where (numbSkillsByJob.numbJobSkills - numbSkillsByPerson.numbPerSkills) = 0
  group by numbSkillsByPerson.name, numbSkillsByJob.job_code
),

-- sum(vacancies - qualified) according to job cateogry 
differences as (
  select cate_code, sum(openings.numb_openings - qualified.numb_qualified) as diff
  from openings natural join qualified natural join job
  group by cate_code
)  

-- select max difference
select cate_code
from differences 
where diff = (select max(diff) from differences);


