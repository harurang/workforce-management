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

CREATE TABLE nwcet (
  nwcet_title varchar(70),
  primary key (nwcet_title)
);

CREATE TABLE career_tier (
    tier_level varchar(30),
    tier_description varchar(400),
    primary key (tier_level)
);

CREATE TABLE career_cluster (
  cluster_title varchar(70),
  primary key (cluster_title)
);

CREATE TABLE knowledge_skill (
  ks_code number,
  title varchar(70),
  cluster_title varchar(70),
  tier_level varchar(30),
  primary key (ks_code),
  foreign key (cluster_title) references career_cluster(cluster_title),
  foreign key (tier_level) references career_tier(tier_level),
  foreign key (title) references nwcet(nwcet_title)
);

CREATE TABLE job_category (
  cate_code number,
  parent_cate number,
  pay_range_high number,
  pay_range_low number,
  title varchar(70),
  ks_code number,
  primary key (cate_code),
  foreign key (parent_cate) references job_category(cate_code),
  foreign key (title) references soc(soc_title),
  foreign key (ks_code) references knowledge_skill(ks_code)
);

CREATE TABLE naics (
  n_code number,
  speciality varchar(200),
  hierarchy number,
  primary key (n_code),
  foreign key (hierarchy) references naics(n_code)
);

CREATE TABLE company (
  comp_id number,
  comp_name varchar(30),
  n_code number,
  city varchar(40),
  state varchar(2),
  zip number,
  website varchar(70),
  primary_sector varchar(40),
  primary key (comp_id),
  foreign key (n_code) references naics(n_code)
);

CREATE TABLE job (
  job_code number,
  pay_rate number,
  pay_type varchar(20),
  hours number,
  cate_code number,
  job_title varchar(70),
  primary key (job_code),
  foreign key (cate_code) references job_category(cate_code)
);

CREATE TABLE course(
    c_code number,
    title varchar(70),
    course_level varchar(70),
    description varchar(200),
    status varchar(70),
    prereq number,
    primary key (c_code),
    foreign key (prereq) references course(c_code)
);

CREATE TABLE section (
  sec_id number,
  start_date varchar(50),
  end_date varchar(50),
  format varchar(40),
  offered_by varchar(40),
  c_code number,
  price number,
  primary key (sec_id),
  foreign key (c_code) references course(c_code)
);

CREATE TABLE takes (
  per_id number,
  sec_id number,
  foreign key (sec_id) references section(sec_id),
  foreign key (per_id) references person(per_id)
);

CREATE TABLE job_history (
  start_date varchar(50),
  end_date varchar(50),
  job_code number,
  per_id number,
  foreign key (per_id) references person(per_id),
  foreign key (job_code) references job(job_code)
);

CREATE TABLE course_knowledge(
   ks_code number,
   c_code number,
   foreign key (ks_code) references knowledge_skill(ks_code),
   foreign key (c_code) references course(c_code)
);

CREATE TABLE job_listing (
    listing_id number,
    comp_id number,
    job_code number,
    primary key (listing_id),
    foreign key (comp_id) references company(comp_id),
    foreign key (job_code) references job(job_code)
);

CREATE TABLE paid_by(
  per_id number,
  listing_id number,
  foreign key (per_id) references person(per_id),
  foreign key (listing_id) references job_listing(listing_id)
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

