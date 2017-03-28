CREATE TABLE knowledge_skill(
  ks_code number,
  cluster_title varchar(70),
  level varchar(70),
  description varchar(70),
  primary key (ks_code),
  foreign key (cluster_title) references cluster(cluster_title),
  foreign key (level) references tier(level),
  foreign key (description) references nwcet(description)
);

CREATE TABLE course_knowledge(
   ks_code number,
   c_code number,
   foreign key (ks_code) references knowledge_skill(ks_code),
   foreign key (c_code) references course(c_code)
);


CREATE TABLE paid_by(
  per_id number,
  job_code number,
  foreign key (per_id) references person(per_id),
  foreign key (job_code) references job(job_code)
);


CREATE TABLE tier(
    level varchar(70),
    description varchar(70),
    primary key (level)
);
