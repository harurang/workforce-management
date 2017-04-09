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

SELECT TITLE, START_DATE, END_DATE, FORMAT, OFFERED_BY FROM COURSES WHERE START_DATE = (SELECT MIN(TO_DATE(START_DATE)) FROM COURSES);

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
-- Incorrect. Answer will be provided.
-- WITH COURSES AS (
--   SELECT TITLE, PRICE, SEC_ID FROM SECTION NATURAL JOIN (
--     SELECT DISTINCT C.TITLE, C.C_CODE
--     FROM COURSE C RIGHT JOIN COURSE_KNOWLEDGE S
--     ON C.C_CODE = S.C_CODE
--     WHERE NOT EXISTS (
--     
--       SELECT JOB_SKILL.KS_CODE
--       FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL 
--       ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
--       WHERE JOB_CODE=31
--       MINUS
--       SELECT PERSON_SKILL.KS_CODE 
--       FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL 
--       ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE
--       WHERE PER_ID=176
--       
--       MINUS
--       (SELECT KS_CODE
--       FROM COURSE A RIGHT JOIN COURSE_KNOWLEDGE B
--       ON A.C_CODE = B.C_CODE
--       WHERE C.TITLE = A.TITLE)
--     )
--   )
-- )
-- 
-- select course_knowledge.c_code
-- from job_skill natural join course_knowledge
-- where not exists (select * from courses); --and rownum <= 4;

-- DECLARE 
--   cnt VARCHAR2(4000);
-- 
-- BEGIN
--   SELECT COUNT(*)
--   INTO cnt
--   FROM courses;
-- 
--   IF( cnt = 0 ) 
--   THEN
--     insert into courses 
--   --ELSIF
--     --select * from soc
--     
--   END IF;
-- END;

-- 23
with comp_paychecks as (
  select sum(nvl(pay_rate,0) + nvl(hours * pay_rate, 0)) as sum_sal, comp_name from person left join paid_by natural join job natural join company
  on person.per_id = paid_by.per_id group by comp_name
),

comp_employee_count as 
(select comp_name, count(*) as numb_employees from 
  person left join paid_by natural join job natural join company
  on person.per_id = paid_by.per_id group by comp_name)

select comp_name, sum_sal, numb_employees from comp_paychecks natural join comp_employee_count
  where sum_sal = (select max(sum_sal) from comp_paychecks) or numb_employees = (select max(numb_employees) from comp_employee_count);




