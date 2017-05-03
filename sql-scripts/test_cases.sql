--Query 13:

Test case 1:
Per_id = 7

Input:

SELECT DISTINCT TITLE
FROM  JOB_CATEGORY JC
WHERE EXISTS (
	SELECT KS_CODE
	FROM JOB_SKILL JB
	WHERE JC.KS_CODE=JB.KS_CODE
	MINUS
	SELECT KS_CODE
	FROM  PERSON_SKILL
	WHERE PER_ID=7
);

Expected result:
TITLE
Computer and Mathematical Occupations
Computer Occupations
Web Developers
Computer and Information Research Scientists
Computer Support Specialists
Information Security Analysts
Computer Systems Analysts
Computer User Support Specialists
Computer and Information Analysts

Test case 2:
Per_id = 176
Input:
SELECT DISTINCT TITLE
FROM  JOB_CATEGORY JC
WHERE EXISTS (
	SELECT KS_CODE
	FROM JOB_SKILL JB
	WHERE JC.KS_CODE=JB.KS_CODE
	MINUS
	SELECT KS_CODE
	FROM  PERSON_SKILL
	WHERE PER_ID=176
);

Expected result:
TITLE
Computer and Mathematical Occupations
Computer Occupations
Software Developers, Applications
Web Developers
Computer Programmers
Computer and Information Research Scientists
Computer Support Specialists
Software Developers and Programmers
Computer Systems Analysts
Computer User Support Specialists
Computer and Information Analysts

--Query14:

Test case 1:
Per_id = 7
Input:
WITH SKILL_QUALIFICATION AS (
	SELECT DISTINT TITLE
	FROM JOB_CATEGORY JC
	WHERE EXISTS (
		SELECT KS_CODE
		FROM JOB_SKILL JB 
		WHERE JC.KS_CODE=JB.KS_CODE
		MINUS
		SELECT KS_CODE
		FROM PERSON_SKILL
		WHERE PER_ID=7)
)
SELECT MAX(PAY_RATE)
FROM SKILL_QUALIFICATION NATURAL JOIN JOB;

Expected result:
MAX(PAY_RATE)
85000




Test case 2:
Per_id = 176
Input:

WITH SKILL_QUALIFICATION AS (
	SELECT DISTINT TITLE
	FROM JOB_CATEGORY JC
	WHERE EXISTS (
		SELECT KS_CODE
		FROM JOB_SKILL JB 
		WHERE JC.KS_CODE=JB.KS_CODE
		MINUS
		SELECT KS_CODE
		FROM PERSON_SKILL
		WHERE PER_ID=176)
)
SELECT MAX(PAY_RATE)
FROM SKILL_QUALIFICATION NATURAL JOIN JOB;

Expected result:
MAX(PAY_RATE)
85000


--Query18

Test case 1:
Job_code = 23
Input:

WITH COUNT_NEEDED_SKILLS AS (
	SELECT PER_ID, COUNT(DISTINCT KS_CODE) AS MISSING_AMOUNT
	FROM PERSON P NATURAL JOIN JOB_SKILL
	WHERE KS_CODE IN (
		SELECT KS_CODE
		FROM JOB_SKILL
		WHERE JOB_CODE=23
		MINUS
		SELECT KS_CODE
		FROM PERSON_SKILL
		WHERE PER_ID=P.PER_ID)
		GROUP BY PER_ID
)
SELECT PER_ID,  MISSING_AMOUNT
FROM COUNT_NEEDED_SKILLS
WHERE MISSING_AMOUNT = (
	SELECT MIN(MISSING_AMOUNT)
	FROM COUNT_NEEDED_SKILLS);

Expected result:
PER_ID		MISSING_AMOUNT
2			1
5			1
9			1
100			1
105			1
176			1
206			1
207			1
208			1
234			1

Test case 2:
Job_code = 64
Input:

WITH COUNT_NEEDED_SKILLS AS (
	SELECT PER_ID, COUNT(DISTINCT KS_CODE) AS MISSING_AMOUNT
	FROM PERSON P NATURAL JOIN JOB_SKILL
	WHERE KS_CODE IN (
		SELECT KS_CODE
		FROM JOB_SKILL
		WHERE JOB_CODE=64
		MINUS
		SELECT KS_CODE
		FROM PERSON_SKILL
		WHERE PER_ID=P.PER_ID)
		GROUP BY PER_ID
)
SELECT PER_ID,  MISSING_AMOUNT
FROM COUNT_NEEDED_SKILLS
WHERE MISSING_AMOUNT = (
	SELECT MIN(MISSING_AMOUNT)
	FROM COUNT_NEEDED_SKILLS);

Expected result:
PER_ID 		MISSING_AMOUNT
2			1
4			1
5			1
7			1
100			1
176			1
206			1
207			1
208			1
209			1
234			1


--Query19

Test case 1:
Cate_code = 11
Input:

WITH COUNT_MISSING_SKILLS AS (
	SELECT PER_ID, COUNT(KS_CODE) AS MISSING_AMOUNT
	FROM PERSON P NATURAL JOIN JOB_CATEGORY
	WHERE KS_CODE IN (
		SELECT KS_CODE
		FROM JOB_CATEGORY
		WHERE CATE_CODE=11
		MINUS 
		SELECT KS_CODE
		FROM PERSON_SKILL
		WHERE PER_ID=P.PER_ID)
		GROUP BY PER_ID
)
SELECT PER_ID, MISSING_AMOUNT
FROM COUNT_MISSING_SKILLS
WHERE MISSING_AMOUNT <= 2
ORDER BY MISSING_AMOUNT ASC;

Expected result:
PER_ID		MISSING_AMOUNT
100			1
207			1
2			1
5			1
234			1
7			1
105			1
208			1
206			1

Test case 2:
Cate_code = 6
Input:

WITH COUNT_MISSING_SKILLS AS (
	SELECT PER_ID, COUNT(KS_CODE) AS MISSING_AMOUNT
	FROM PERSON P NATURAL JOIN JOB_CATEGORY
	WHERE KS_CODE IN (
		SELECT KS_CODE
		FROM JOB_CATEGORY
		WHERE CATE_CODE=6
		MINUS 
		SELECT KS_CODE
		FROM PERSON_SKILL
		WHERE PER_ID=P.PER_ID)
		GROUP BY PER_ID
)
SELECT PER_ID, MISSING_AMOUNT
FROM COUNT_MISSING_SKILLS
WHERE MISSING_AMOUNT <= 2
ORDER BY MISSING_AMOUNT ASC;

Expected results:
PER_ID		MISSING_AMOUNT
207			1
209			1
4			1
5			1
206			1
234			1
105			1
176			1
9			1
208			1
7			1


--Query20

Test case 1:
Cate_code = 11
Input:

WITH COUNT_MISSING_SKILLS(PER_ID, KS_CODE) AS (
	SELECT DISTINT PER_ID, KS_CODE
	FROM PERSON, JOB_CATEGORY
	WHERE CATE_CODE=11
	MINUS
	SELECT PER_ID, KS_CODE
	FROM PERSON_SKILL
),
MISSING_K AS (
	SELECT MIN(COUNT(KS_CODE))
	FROM JOB_CATEGORY NATURAL JOIN COUNT_MISSING_SKILLS
	WHERE CATE_CODE=11
	GROUP BY PER_ID
)
SELECT KS_CODE, COUNT(PER_ID) AS SKILLS_COUNT
FROM COUNT_MISSING_SKILLS CMS
WHERE EXISTS (
	SELECT PER_ID
	FROM COUNT_MISSING_SKILLS
	WHERE PER_ID=CMS.PER_ID
	GROUP BY PER_ID
	HAVING COUNT(KS_CODE) <= 2
)
GROUP BY KS_CODE
ORDER BY SKILLS_COUNT DESC;

Expected results:
KS_CODE		SKILLS_COUNT
435785			9


Test case 2:
Cate_code = 6
Input:

WITH COUNT_MISSING_SKILLS(PER_ID, KS_CODE) AS (
	SELECT DISTINT PER_ID, KS_CODE
	FROM PERSON, JOB_CATEGORY
	WHERE CATE_CODE=6
	MINUS
	SELECT PER_ID, KS_CODE
	FROM PERSON_SKILL
),
MISSING_K AS (
	SELECT MIN(COUNT(KS_CODE))
	FROM JOB_CATEGORY NATURAL JOIN COUNT_MISSING_SKILLS
	WHERE CATE_CODE=6
	GROUP BY PER_ID
)
SELECT KS_CODE, COUNT(PER_ID) AS SKILLS_COUNT
FROM COUNT_MISSING_SKILLS CMS
WHERE EXISTS (
	SELECT PER_ID
	FROM COUNT_MISSING_SKILLS
	WHERE PER_ID=CMS.PER_ID
	GROUP BY PER_ID
	HAVING COUNT(KS_CODE) <= 2
)
GROUP BY KS_CODE
ORDER BY SKILLS_COUNT DESC;

Expected results:
PER_ID		SKILLS_COUNT
435789			11
