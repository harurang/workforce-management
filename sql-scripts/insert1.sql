INSERT INTO knowledge_skill
VALUES (435782, 'Database Development and Administration', 'Tier II', 'SQL');

INSERT INTO knowledge_skill
VALUES (435783, 'Digital Media'. 'Tier II', 'Javascript');

INSERT INTO knowledge_skill
VALUES (435784, 'Enterprise Systems Analysis and Integration', 'Tier II', 'SQL');

INSERT INTO knowledge_skill
VALUES (435785, 'Network Design and Administration', 'Tier II', 'PERL');

INSERT INTO knowledge_skill
VALUES (435786, 'Programming/Software Engineering', 'Tier II', 'Java' );

INSERT INTO knowledge_skill
VALUES (435787, 'Techinal Support', 'Tier I', null );

INSERT INTO knowledge_skill
VALUES (435788, 'Technical Writing', 'Tier I', null );

INSERT INTO knowledge_skill
VALUES (435789, 'Web Development and Administration', 'Tier II', 'CSS' );




INSERT INTO course_knowledge
VALUES (435782, 1543 );

INSERT INTO course_knowledge
VALUES (435783, 2106);

INSERT INTO course_knowledge
VALUES (435784, 2114);

INSERT INTO course_knowledge
VALUES (435785, 2345);

INSERT INTO course_knowledge
VALUES (435786, 3024);

INSERT INTO course_knowledge
VALUES (435786, 3120);

INSERT INTO course_knowledge
VALUES (435788, 4102);

INSERT INTO course_knowledge
VALUES (435789, 4208);




INSERT INTO paid_by
VALUES (2, 44);

INSERT INTO paid_by
VALUES (4, 91);

INSERT INTO paid_by
VALUES (5, 43);

INSERT INTO paid_by
VALUES (7, 31);

INSERT INTO paid_by
VALUES (9, 21);




INSERT INTO takes
VALUES (2, 321);

INSERT INTO takes
VALUES (4, 322);

INSERT INTO takes
VALUES (5, 323);

INSERT INTO takes
VALUES (7, 324);

INSERT INTO takes
VALUES (9, 325);





INSERT INTO job_history
VALUES ('02/10/2017', '03/10/2015', 44, 2);

INSERT INTO job_history
VALUES ('03/08/2017', '01/23/2014', 91, 4);

INSERT INTO job_history
VALUES ('01/14/2017', '05/16/2015', 43, 5);

INSERT INTO job_history
VALUES ('03/14/2017', '01/09/2016', 31, 7);

INSERT INTO job_history
VALUES ('02/21/2017', '04/21/2013', 21, 9);





INSERT INTO section
VALUES (321,'05/05/2014', '01/12/2014', 1543 );

INSERT INTO section
VALUES (322, '01/06/2014', '05/14/2014', 2106 );

INSERT INTO section
VALUES (323, '01/06/2014', '05/14/2014', 2114 );

INSERT INTO section
VALUES (324, '01/06/2015', '05/14/2015', 2345);

INSERT INTO section
VALUES (325, '08/12/2015', '12/08/2015', 3024);

INSERT INTO section
VALUES (326, '08/12/2015', '12/08/2015', 3120);

INSERT INTO section
VALUES (327, '08/12/2016', '12/08/2016', 4102);

INSERT INTO section
VALUES (328, '01/13/2016', '05/04/2016', 4208);

INSERT INTO section
VALUES (329, '01/13/2016', '05/04/2016', 4325);

INSERT INTO section
VALUES (330, '01/13/2016', '05/04/2016', 4786);





INSERT INTO course
VALUES (1543, 'Introduction to Web Development', 1000, 'Learn
the basics of web development. Languages such as CSS, HTML5, Javascript, etc.',
 'Open', '$200', null);

INSERT INTO course
VALUES (2106, 'Computer Architecture', 2000, 'Learn the what computer architecture
is and understand the functions that make up the architecture', 
'Open', '$200', 'CS-1543');

INSERT INTO course
VALUES (2114, 'Introduction to Programming Languages, 2000, 'Learn about the
core programming languages and what they consist of.', 'Open', '$200', 'CS-2106');

INSERT INTO course
VALUES (2345, 'Reverse Engineering', 2000, 'Learn how to work with existing
code and how to interpret them.', 'Open', '$300', null);

INSERT INTO course
VALUES (3024, 'Algorithms & Data Structures', 3000, 'Learn about different algorithms as well as
different data structures and ways to implement them', 'Open', '$300', 'CS-2114');

INSERT INTO course
VALUES (3120, 'Introduction to Networking', 3000, 'Learn about the depths of networking and how
computers depend on the network, learn terminolgy and the basics', 'Closed', '$300', null);

INSERT INTO course
VALUES (4102, 'Introduction to Database Systems', 4000, 'Learn the basis of SQL, Relational 
Algebra and how to implement them', 'Open', '$400', null);

INSERT INTO course
VALUES (4208, 'Introducion to Software Engineering', 4000, 'Focus in the fundamentals of 
computing & programming, using an exploratory problem-based approach', 'Closed', '$400', null);

INSERT INTO course
VALUES (4325, 'Computer Systems Principles and Programming', 4000, 'Systems programming in C, 
memory management, basics of machine organization, etc.', 'Open', '$400', null);

INSERT INTO course
VALUES (4786, 'Social and Mobile Computing', 4000, 'Topics in social computing. Consideration in
the design of mobile and ubiquitous computing systems.', 'Closed', '$400', null);





INSERT INTO tier
VALUES ('Tier III', 'Industry-specific technical skills, knowledge, and
abilities unique to individual industries and organization. Examples: 
knowledge of and compliance with companies and organization protocols, 
understanding and effective use of industry terminology, knowledge of and 
compliance with industry legal requirements, and knowledge of and compliance
with company and product standards.');

INSERT INTO tier
VALUES ('Tier II', 'Technical skills, knowledge, and abilities skills common 
to all jobs within a career cluster across all industries. Examples: 
proficient use of software and hardware tools, proficient use of Internet
techniques, understanding of hardware/system architecture, and troubleshooting
of software and hardware problems.');

INSERT INTO tier
VALUES ('Tier I', 'The set of foundation skills (SCANS), knowledge, abilities,
and personal qualities required of all workers to be successful in the
workplace. Examples: basic skills,
thinking skills, personal qualities, management of time and resources,
interpersonal skills, management and use of information, understanding and
management of systems, and use of technology.');
