CREATE TABLE person (
  per_id varchar(3),
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
  per_id varchar(3),
  home varchar(30),
  work varchar(30)
  foreign key (per_id) references person(per_id)
);

CREATE TABLE job (
  job_code number,
  per_id varchar(3),
  detailed_occupation varchar(50),
  company_name varchar(50),
  pay_rate number,
  pay_type varchar(20),
  primary key (job_code),
  foreign key (per_id) references person(per_id)
);

CREATE TABLE course (
  c_code number,
  title varchar(50),
  description varchar(50),
  status varchar(20),
  retail_price varchar(30),
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

CREATE TABLE soc (
  detailed_occupation varchar(70),
  broad_group varchar(70),
  minor_group varchar(70),
  major_group varchar(70),
  primary key (detailed_occupation)
);

CREATE TABLE takes (
  sec_id number,
  per_id number,
  foreign key (sec_id) references section(sec_id),
  foreign key (per_id) references person(per_id)
);

CREATE TABLE job_history (
  per_id varchar(3),
  job_code number,
  start_date varchar(50),l
  end_date varchar(50),
  foreign key (per_id) references person(per_id),
  foreign key (job_code) references job(job_code)
);

CREATE TABLE naics (
  n_code number,
  title varchar(70),
  hierarchy number,
  primary key (n_code),
  foreign key (hierarchy) references naics(n_code)
);

CREATE TABLE nwcet (
  description varchar(70)
);

CREATE TABLE career_cluster (
  title varchar(70)
);

CREATE TABLE job_category (
  cate_code number,
  detailed_occupation varchar(70),
  parent_cate varchar(70),
  ks_code number,
  pay_high varchar(40),
  pay_low varchar(40)
  primary key (cate_code),
  foreign key (detailed_occupation) references soc(detailed_occupation),
  foreign key (ks_code) references knowledge_skill(ks_code)
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

