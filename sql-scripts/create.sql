CREATE TABLE person (
  per_id number,
  name varchar(30),
  city varchar(30),
  street varchar(30),
  state varchar(2),
  zip_code varchar(10),
  email varchar(30),
  gender varchar(30),
  primary key (per_id)
);

CREATE TABLE phone_number (
  per_id number,
  home varchar(30),
  work varchar(30),
  foreign key (per_id) references person(per_id)
);

CREATE TABLE soc (
  soc_title varchar(70),
  primary key (soc_title)
);

CREATE TABLE job_category (
  cate_code number,
  parent_cate varchar(70),
  pay_range_high varchar(40),
  pay_range_low varchar(40),
  soc_title varchar(70),
  primary key (cate_code),
  foreign key (soc_title) references soc(soc_title)
);

CREATE TABLE job (
  job_code number,
  pay_rate number,
  pay_type varchar(20),
  cate_code number,
  job_title varchar(70),
  primary key (job_code),
  foreign key (cate_code) references job_category
);

CREATE TABLE course(
    c_code number,
    course_level varchar(70),
    description varchar(70),
    status varchar(70),
    price varchar(70),
    prereq varchar(70),
    primary key (c_code) 
);

CREATE TABLE section (
  sec_id number,
  c_code number,
  end_date varchar(50),
  start_date varchar(50),
  price varchar(20),
  format varchar(20),
  primary key (sec_id),
  foreign key (c_code) references course(c_code)
);

CREATE TABLE takes (
  sec_id number,
  per_id number,
  foreign key (sec_id) references section(sec_id),
  foreign key (per_id) references person(per_id)
);

CREATE TABLE job_history (
  per_id number,
  job_code number,
  start_date varchar(50),
  end_date varchar(50),
  foreign key (per_id) references person(per_id),
  foreign key (job_code) references job(job_code)
);

CREATE TABLE naics (
  n_code number,
  title varchar(200),
  hierarchy number,
  primary key (n_code),
  foreign key (hierarchy) references naics(n_code)
);

CREATE TABLE nwcet (
  description varchar(70),
  primary key (description)
);

CREATE TABLE career_cluster (
  cluster_title varchar(70),
  primary key (cluster_title)
);

CREATE TABLE company (
  comp_id number,
  comp_name varchar(30),
  n_code number,
  city varchar(40),
  state varchar(2),
  zip number,
  website varchar(70),
  primary key (comp_id),
  foreign key (n_code) references naics(n_code)
);

CREATE TABLE career_tier (
    tier_level varchar(30),
    description varchar(400),
    primary key (tier_level)
);

CREATE TABLE knowledge_skill (
  ks_code number,
  cluster_title varchar(70),
  tier_level varchar(30),
  description varchar(70),
  primary key (ks_code),
  foreign key (cluster_title) references career_cluster(cluster_title),
  foreign key (tier_level) references career_tier(tier_level),
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

CREATE TABLE person_skill (
  per_id number,
  ks_code number,
  foreign key (per_id) references person(per_id),
  foreign key (ks_code) references knowledge_skill(ks_code)
);

CREATE TABLE job_skill (
  job_code number,
  ks_code number,
  importance varchar(30),
  foreign key (job_code) references job(job_code),
  foreign key (ks_code) references knowledge_skill(ks_code)
);

CREATE TABLE comp_job (
    comp_id number,
    job_code number,
    foreign key (comp_id) references company(comp_id),
    foreign key (job_code) references job(job_code)
);
