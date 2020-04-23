;
create table app_setting
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    about_ar         varchar(255),
    about_en         varchar(255),
    about_tr         varchar(255),
    address          varchar(255),
    email            varchar(255),
    latitude         double precision,
    longitude        double precision,
    website          varchar(255),
    primary key (id)
) engine = InnoDB
;
create table attendance
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    att_status       varchar(255),
    att_type         varchar(255),
    scan_date        date,
    scan_time        time,
    employee_id      bigint,
    work_shift_id    bigint,
    primary key (id)
) engine = InnoDB
;
create table branch
(
    id                          bigint not null auto_increment,
    creation_date               datetime(6),
    last_update_date            datetime(6),
    allowed_early_leave_minutes integer,
    allowed_late_leave_minutes  integer,
    allowed_late_minutes        integer,
    max_over_time_hours         integer,
    name_ar                     varchar(255),
    name_en                     varchar(255),
    name_tr                     varchar(255),
    phone_number                varchar(255),
    primary key (id)
) engine = InnoDB
;
create table branch_days_off
(
    branch_id bigint not null,
    days_off  integer
) engine = InnoDB
;
create table city
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    country_id       bigint,
    primary key (id)
) engine = InnoDB
;
create table company
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    database_name    varchar(255),
    description_ar   varchar(255),
    description_en   varchar(255),
    description_tr   varchar(255),
    enabled          bit,
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    phone_number     varchar(255),
    time_zone        varchar(255),
    website          varchar(255),
    country_id       bigint,
    primary key (id)
) engine = InnoDB
;
create table country
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    primary key (id)
) engine = InnoDB
;
create table currency
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    default_currency bit,
    fraction_name_ar varchar(255),
    fraction_name_en varchar(255),
    fraction_name_tr varchar(255),
    icon             varchar(255),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    primary key (id)
) engine = InnoDB
;
create table custom_shift_hours_aud
(
    id        bigint  not null,
    rev       integer not null,
    revtype   tinyint,
    day       integer,
    from_hour time,
    to_hour   time,
    primary key (id, rev)
) engine = InnoDB
;
create table custom_shift_hours
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    day              integer,
    from_hour        time,
    to_hour          time,
    primary key (id)
) engine = InnoDB
;
create table department
(
    id                          bigint not null auto_increment,
    creation_date               datetime(6),
    last_update_date            datetime(6),
    allowed_early_leave_minutes integer,
    allowed_late_leave_minutes  integer,
    allowed_late_minutes        integer,
    max_over_time_hours         integer,
    name_ar                     varchar(255),
    name_en                     varchar(255),
    name_tr                     varchar(255),
    phone_number                varchar(255),
    primary key (id)
) engine = InnoDB
;
create table department_days_off
(
    department_id bigint not null,
    days_off      integer
) engine = InnoDB
;
create table employee
(
    id                   bigint not null auto_increment,
    creation_date        datetime(6),
    last_update_date     datetime(6),
    attendance_exception bit,
    birth_date           datetime(6),
    address1             varchar(255),
    address2             varchar(255),
    area                 varchar(255),
    email                varchar(255),
    fax_number           varchar(255),
    map_location         varchar(255),
    mobile               varchar(255),
    region               varchar(255),
    state                varchar(255),
    street               varchar(255),
    telephone1           varchar(255),
    telephone2          varchar(255),
    website             varchar(255),
    contract_end_date   datetime(6),
    contract_start_date datetime(6),
    created_user_id     bigint,
    firing_date         datetime(6),
    name_ar             varchar(255),
    name_en             varchar(255),
    name_tr             varchar(255),
    salary              double precision,
    ssn                 varchar(255),
    status              varchar(255),
    user_type           varchar(255),
    branch_id           bigint,
    city_id             bigint,
    country_id          bigint,
    department_id       bigint,
    primary key (id)
) engine = InnoDB
;
create table employee_days_off
(
    employee_id bigint not null,
    days_off    integer
) engine = InnoDB
;
create table employee_shifts
(
    employee_id bigint not null,
    shift_id    bigint not null
) engine = InnoDB
;
create table faq
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    answer_ar        varchar(255),
    answer_en        varchar(255),
    answer_tr        varchar(255),
    question_ar      varchar(255),
    question_en      varchar(255),
    question_tr      varchar(255),
    primary key (id)
) engine = InnoDB
;
create table hibernate_sequence
(
    next_val bigint
) engine = InnoDB
;
insert into hibernate_sequence
values (1)
;
create table hr_permissions
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    absence_allowed  bit,
    date             date,
    description_ar   longtext,
    description_en   longtext,
    description_tr   longtext,
    hours_early_go   integer,
    hours_late       integer,
    employee_id      bigint,
    primary key (id)
) engine = InnoDB
;
create table hr_settins
(
    id                       bigint not null auto_increment,
    creation_date            datetime(6),
    last_update_date         datetime(6),
    absence_day_deduction    double precision,
    delay_hour_deduction     double precision,
    over_time_addition       double precision,
    work_on_day_off_addition double precision,
    primary key (id)
) engine = InnoDB
;
create table official_holiday
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    date             date,
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    primary key (id)
) engine = InnoDB
;
create table plan_details
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    price            double precision,
    currency_id      bigint,
    primary key (id)
) engine = InnoDB
;
create table qr_machine
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    change_duration  bigint,
    is_static        bit,
    mac_address      varchar(255),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    branch_id        bigint,
    department_id    bigint,
    primary key (id)
) engine = InnoDB
;
create table report_category
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    primary key (id)
) engine = InnoDB
;
create table report_definition
(
    id                 bigint not null auto_increment,
    creation_date      datetime(6),
    last_update_date   datetime(6),
    file_name          varchar(255),
    file_path          varchar(255),
    jasper_file_name   varchar(255),
    name_ar            varchar(255),
    name_en            varchar(255),
    name_tr            varchar(255),
    size               bigint,
    report_category_id bigint,
    primary key (id)
) engine = InnoDB
;
create table report_definition_params
(
    report_definition_id bigint       not null,
    parameter_type       varchar(255),
    parameter_name       varchar(255) not null,
    primary key (report_definition_id, parameter_name)
) engine = InnoDB
;
create table rest_log
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    date             datetime(6),
    from_ip_address  varchar(255),
    name             varchar(255),
    request_body     varchar(255),
    request_headers  varchar(255),
    request_method   varchar(255),
    request_name     varchar(255),
    primary key (id)
) engine = InnoDB
;
create table revinfo
(
    rev      integer not null auto_increment,
    revtstmp bigint,
    primary key (rev)
) engine = InnoDB
;
create table salary
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    addition         double precision,
    deduction        double precision,
    from_date        date,
    salary           double precision,
    to_date          datetime(6),
    total            double precision,
    employee_id      bigint,
    primary key (id)
) engine = InnoDB
;
create table salary_calculation
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    from_date        datetime(6),
    to_date          datetime(6),
    primary key (id)
) engine = InnoDB
;
create table subscription
(
    id                   bigint not null auto_increment,
    creation_date        datetime(6),
    last_update_date     datetime(6),
    from_date            datetime(6),
    to_date              datetime(6),
    company_id           bigint,
    subscription_plan_id bigint,
    primary key (id)
) engine = InnoDB
;
create table subscription_plan
(
    id                   bigint not null auto_increment,
    creation_date        datetime(6),
    last_update_date     datetime(6),
    duration_days        integer,
    from_no_of_employees integer,
    name_ar              varchar(255),
    name_en              varchar(255),
    name_tr              varchar(255),
    to_no_of_employees   integer,
    primary key (id)
) engine = InnoDB
;
create table subscription_plan_plan_details
(
    subscription_plan_id bigint not null,
    plan_details_id      bigint not null
) engine = InnoDB
;
create table terms_and_conditions
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    body_ar          longtext,
    body_en          longtext,
    body_tr          longtext,
    title_ar         varchar(255),
    title_en         varchar(255),
    title_tr         varchar(255),
    primary key (id)
) engine = InnoDB
;
create table user
(
    id          bigint       not null,
    company_id  bigint,
    email       varchar(255),
    employee_id bigint,
    image       varchar(255),
    mac_address varchar(255),
    password    varchar(255) not null,
    username    varchar(255) not null,
    primary key (id)
) engine = InnoDB
;
create table user_roles
(
    user_id bigint not null,
    roles   varchar(255)
) engine = InnoDB
;
create table work_shift_aud
(
    id        bigint  not null,
    rev       integer not null,
    revtype   tinyint,
    name_ar   varchar(255),
    name_en   varchar(255),
    name_tr   varchar(255),
    from_hour time,
    to_hour   time,
    primary key (id, rev)
) engine = InnoDB
;
create table work_shift_custom_shift_hours_aud
(
    rev                   integer not null,
    work_shift_id         bigint  not null,
    custom_shift_hours_id bigint  not null,
    revtype               tinyint,
    primary key (rev, work_shift_id, custom_shift_hours_id)
) engine = InnoDB
;
create table work_shift
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    from_hour        time,
    to_hour          time,
    primary key (id)
) engine = InnoDB
;
create table work_shift_custom_shift_hours
(
    work_shift_id         bigint not null,
    custom_shift_hours_id bigint not null
) engine = InnoDB
;
alter table employee
    add constraint UK_fopic1oh5oln2khj8eat6ino0 unique (email)
;
alter table employee
    add constraint UK_4ts03wxs8exmr93khm543lt4x unique (mobile)
;
alter table employee
    add constraint UK_f35rkopwr25n69dtp946lt3rh unique (ssn)
;
alter table qr_machine
    add constraint UK_6jigeaaed0soa2suvrph78och unique (mac_address)
;
alter table subscription_plan_plan_details
    add constraint UK_aoier6jccoccpmludc02a0pvd unique (plan_details_id)
;
alter table user
    add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email)
;
alter table user
    add constraint UK_nxb9sa2vxscd9ik979vy5ae00 unique (mac_address)
;
alter table user
    add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username)
;
alter table work_shift_custom_shift_hours
    add constraint UK_5dfv48o95eu0jiyap3lt4t9d unique (custom_shift_hours_id)
;
alter table attendance
    add constraint FKr7q0h8jfngkyybll6o9r3h9ua foreign key (employee_id) references employee (id)
;
alter table attendance
    add constraint FKfj4bkjnxb247ghct6vvlfwnis foreign key (work_shift_id) references work_shift (id)
;
alter table branch_days_off
    add constraint FKow2rw55b13362icw869litx9d foreign key (branch_id) references branch (id)
;
alter table city
    add constraint FKrpd7j1p7yxr784adkx4pyepba foreign key (country_id) references country (id)
;
alter table company
    add constraint FKaa85rotlnir4w4xlj1nkilnws foreign key (country_id) references country (id)
;
alter table custom_shift_hours_aud
    add constraint FKtbne2pbap1qd3ibuoqypt4xgo foreign key (rev) references revinfo (rev)
;
alter table department_days_off
    add constraint FK78rstmj8cbcxtnh0v2ee6v7ap foreign key (department_id) references department (id)
;
alter table employee
    add constraint FKcvhlsx8tao1rxt7mpxrot61jt foreign key (branch_id) references branch (id)
;
alter table employee
    add constraint FK289qfli0oe8ae3qcuafi4q3tf foreign key (city_id) references city (id)
;
alter table employee
    add constraint FKivtkrlfso4toqek7i2rul2ggy foreign key (country_id) references country (id)
;
alter table employee
    add constraint FKbejtwvg9bxus2mffsm3swj3u9 foreign key (department_id) references department (id)
;
alter table employee_days_off
    add constraint FKbtnrafos5kppyrf3d4a0fe11g foreign key (employee_id) references employee (id)
;
alter table employee_shifts
    add constraint FKf3sver1ayrxow0ekmx0ynr28a foreign key (shift_id) references work_shift (id)
;
alter table employee_shifts
    add constraint FKpa5cdc7udnmyis0lh91m7qfb foreign key (employee_id) references employee (id)
;
alter table hr_permissions
    add constraint FK3gifnd8hobuhejyguqfjrvy8a foreign key (employee_id) references employee (id)
;
alter table plan_details
    add constraint FKe0agn054hd4djs4glcyq5xvdg foreign key (currency_id) references currency (id)
;
alter table qr_machine
    add constraint FK60mj082ixc5qju9y7i9qixla6 foreign key (branch_id) references branch (id)
;
alter table qr_machine
    add constraint FKhta5dwyoquq9kygdv4nol5o6s foreign key (department_id) references department (id)
;
alter table report_definition
    add constraint FKko7od89jyvjjd9hb8qwsjf44 foreign key (report_category_id) references report_category (id)
;
alter table report_definition_params
    add constraint FKenm5vd32id4ft0jan8ft3cm81 foreign key (report_definition_id) references report_definition (id)
;
alter table salary
    add constraint FKnlnv3jbyvbiu8ci59r3btlk00 foreign key (employee_id) references employee (id)
;
alter table subscription
    add constraint FK45i0k0ls0erwl77ei45ds25t8 foreign key (company_id) references company (id)
;
alter table subscription
    add constraint FKhn8hnxbdoi29nb4m7ojkocqfm foreign key (subscription_plan_id) references subscription_plan (id)
;
alter table subscription_plan_plan_details
    add constraint FKrsy21ydncno4c82k7uqc6tlgi foreign key (plan_details_id) references plan_details (id)
;
alter table subscription_plan_plan_details
    add constraint FKdx2qg3vw83ytg4vigpvb5bdsw foreign key (subscription_plan_id) references subscription_plan (id)
;
alter table user_roles
    add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references user (id)
;
alter table work_shift_aud
    add constraint FKqv2idp0w2y0ptqbgt9fe1jtx4 foreign key (rev) references revinfo (rev)
;
alter table work_shift_custom_shift_hours_aud
    add constraint FKbfyj19ra1wikubovws22kv6jm foreign key (rev) references revinfo (rev)
;
alter table work_shift_custom_shift_hours
    add constraint FKnx9etpf3qwy27ws8wee7hf3ug foreign key (custom_shift_hours_id) references custom_shift_hours (id)
;
alter table work_shift_custom_shift_hours
    add constraint FK3hcii5cpfbyjq0w3j2yuumbdi foreign key (work_shift_id) references work_shift (id)
