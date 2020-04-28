alter table branch
    add column update_date_time bigint;
create table branch_aud
(
    id                          bigint  not null,
    rev                         integer not null,
    revtype                     tinyint,
    allowed_early_leave_minutes integer,
    allowed_late_leave_minutes  integer,
    allowed_late_minutes        integer,
    max_over_time_hours         integer,
    name_ar                     varchar(255),
    name_en                     varchar(255),
    name_tr                     varchar(255),
    phone_number                varchar(255),
    update_date_time            bigint,
    primary key (id, rev)
) engine = InnoDB;
create table branch_days_off_aud
(
    rev       integer not null,
    branch_id bigint  not null,
    days_off  integer not null,
    revtype   tinyint,
    primary key (rev, branch_id, days_off)
) engine = InnoDB;
alter table department
    add column update_date_time bigint;
create table department_aud
(
    id                          bigint  not null,
    rev                         integer not null,
    revtype                     tinyint,
    allowed_early_leave_minutes integer,
    allowed_late_leave_minutes  integer,
    allowed_late_minutes        integer,
    max_over_time_hours         integer,
    name_ar                     varchar(255),
    name_en                     varchar(255),
    name_tr                     varchar(255),
    phone_number                varchar(255),
    update_date_time            bigint,
    primary key (id, rev)
) engine = InnoDB;
create table department_days_off_aud
(
    rev           integer not null,
    department_id bigint  not null,
    days_off      integer not null,
    revtype       tinyint,
    primary key (rev, department_id, days_off)
) engine = InnoDB;
alter table employee
    add column job_title varchar(255);
alter table employee
    add column update_date_time bigint;
create table employee_aud
(
    id                   bigint  not null,
    rev                  integer not null,
    revtype              tinyint,
    attendance_exception bit,
    birth_date           datetime(6),
    email                varchar(255),
    mobile               varchar(255),
    contract_end_date    datetime(6),
    contract_start_date  datetime(6),
    created_user_id      bigint,
    firing_date          datetime(6),
    gender               varchar(255),
    job_title            varchar(255),
    name_ar              varchar(255),
    name_en              varchar(255),
    name_tr              varchar(255),
    salary               double precision,
    ssn                  varchar(255),
    status               varchar(255),
    update_date_time     bigint,
    branch_id            bigint,
    department_id        bigint,
    primary key (id, rev)
) engine = InnoDB;
create table employee_days_off_aud
(
    rev         integer not null,
    employee_id bigint  not null,
    days_off    integer not null,
    revtype     tinyint,
    primary key (rev, employee_id, days_off)
) engine = InnoDB;
create table employee_optional_branches_aud
(
    rev         integer not null,
    employee_id bigint  not null,
    branch_id   bigint  not null,
    revtype     tinyint,
    primary key (rev, employee_id, branch_id)
) engine = InnoDB;
create table employee_shifts_aud
(
    rev          integer not null,
    employee_id  bigint  not null,
    workshift_id bigint  not null,
    revtype      tinyint,
    primary key (rev, employee_id, workshift_id)
) engine = InnoDB;
create table employee_user_type_aud
(
    rev         integer      not null,
    employee_id bigint       not null,
    user_type   varchar(255) not null,
    revtype     tinyint,
    primary key (rev, employee_id, user_type)
) engine = InnoDB;
alter table branch_aud
    add constraint FK6jkm88122iurk8et84u29phc2 foreign key (rev) references revinfo (rev);
alter table branch_days_off_aud
    add constraint FK9of2m2nr96yuk7oc5lv4w3fuw foreign key (rev) references revinfo (rev);
alter table department_aud
    add constraint FKdrxjxvx2qlyxtsq8teb2fgqy8 foreign key (rev) references revinfo (rev);
alter table department_days_off_aud
    add constraint FK6of6mi3ko5gxgaqhkn96u8er5 foreign key (rev) references revinfo (rev);
alter table employee_aud
    add constraint FK118cwnbfk1ny0ttu4bfqmeh8q foreign key (rev) references revinfo (rev);
alter table employee_days_off_aud
    add constraint FKl5kgvlawdkkrm3f697ks53pwl foreign key (rev) references revinfo (rev);
alter table employee_optional_branches_aud
    add constraint FKjw3aolhuw27bdnxuhd7l71y5b foreign key (rev) references revinfo (rev);
alter table employee_shifts_aud
    add constraint FK9icyl32o4jldpsqy3eq6fwkby foreign key (rev) references revinfo (rev);
alter table employee_user_type_aud
    add constraint FKldd8hm9h0e0gjkfkwrgc31v18 foreign key (rev) references revinfo (rev);
