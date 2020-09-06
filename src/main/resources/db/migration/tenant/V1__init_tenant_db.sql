create table app_setting
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    about_ar         varchar(255),
    about_en         varchar(255),
    about_tr         varchar(255),
    address          varchar(255),
    email            varchar(255),
    latitude         double precision,
    longitude        double precision,
    website          varchar(255),
    primary key (id)
) engine = InnoDB;
create table attendance
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    att_status       varchar(255),
    att_type         varchar(255),
    scan_date        date,
    scan_time        time,
    employee_id      bigint,
    work_shift_id    bigint,
    primary key (id)
) engine = InnoDB;
create table branch
(
    id                          bigint not null auto_increment,
    creation_date               DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date            DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    allowed_early_leave_minutes integer,
    allowed_late_leave_minutes  integer,
    allowed_late_minutes        integer,
    max_over_time_hours         integer,
    name_ar                     varchar(255),
    name_en                     varchar(255),
    name_tr                     varchar(255),
    phone_number                varchar(255),
    update_date_time            bigint,
    primary key (id)
) engine = InnoDB;
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
create table branch_days_off
(
    branch_id bigint not null,
    days_off  integer
) engine = InnoDB;
create table city
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    country_id       bigint,
    primary key (id)
) engine = InnoDB;
create table company
(
    id               bigint       not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    database_name    varchar(255) not null,
    description_ar   longtext,
    description_en   longtext,
    description_tr   longtext,
    enabled          bit,
    image            varchar(255),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    phone_number     varchar(255),
    time_zone        varchar(255),
    website          varchar(255),
    country_id       bigint,
    primary key (id)
) engine = InnoDB;
create table company_subscriptions
(
    company_id    bigint not null,
    subscriptions varchar(255)
) engine = InnoDB;
create table country
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    primary key (id)
) engine = InnoDB;
create table currency
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    default_currency bit,
    fraction_name_ar varchar(255),
    fraction_name_en varchar(255),
    fraction_name_tr varchar(255),
    icon             varchar(255),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    primary key (id)
) engine = InnoDB;
create table custom_shift_hours_aud
(
    id        bigint  not null,
    rev       integer not null,
    revtype   tinyint,
    day       integer,
    from_hour time,
    to_hour   time,
    primary key (id, rev)
) engine = InnoDB;
create table custom_shift_hours
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    day              integer,
    from_hour        time,
    to_hour          time,
    primary key (id)
) engine = InnoDB;
create table department
(
    id                          bigint not null auto_increment,
    creation_date               DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date            DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    allowed_early_leave_minutes integer,
    allowed_late_leave_minutes  integer,
    allowed_late_minutes        integer,
    max_over_time_hours         integer,
    name_ar                     varchar(255),
    name_en                     varchar(255),
    name_tr                     varchar(255),
    phone_number                varchar(255),
    update_date_time            bigint,
    primary key (id)
) engine = InnoDB;
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
create table department_days_off
(
    department_id bigint not null,
    days_off      integer
) engine = InnoDB;
create table employee
(
    id                   bigint not null auto_increment,
    creation_date        DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date     DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
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
    tenant               bigint,
    update_date_time     bigint,
    branch_id            bigint,
    department_id        bigint,
    user_id              bigint,
    primary key (id)
) engine = InnoDB;
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
    tenant               bigint,
    update_date_time     bigint,
    branch_id            bigint,
    department_id        bigint,
    user_id              bigint,
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
create table employee_days_off
(
    employee_id bigint not null,
    days_off    integer
) engine = InnoDB;
create table employee_optional_branches
(
    employee_id bigint not null,
    branch_id   bigint not null
) engine = InnoDB;
create table employee_optional_branches_aud
(
    rev         integer not null,
    employee_id bigint  not null,
    branch_id   bigint  not null,
    revtype     tinyint,
    primary key (rev, employee_id, branch_id)
) engine = InnoDB;
create table employee_shifts
(
    employee_id  bigint not null,
    workshift_id bigint not null
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
create table employee_user_type
(
    employee_id bigint not null,
    user_type   varchar(255)
) engine = InnoDB;
create table faq
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    answer_ar        varchar(255),
    answer_en        varchar(255),
    answer_tr        varchar(255),
    question_ar      varchar(255),
    question_en      varchar(255),
    question_tr      varchar(255),
    primary key (id)
) engine = InnoDB;
create table hr_permissions
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    absence_allowed  bit,
    date             date,
    description_ar   longtext,
    description_en   longtext,
    description_tr   longtext,
    minutes_early_go integer,
    minutes_late     integer,
    employee_id      bigint,
    primary key (id)
) engine = InnoDB;
create table hr_settins
(
    id                       bigint not null auto_increment,
    creation_date            DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date         DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    absence_day_deduction    double precision,
    delay_hour_deduction     double precision,
    over_time_addition       double precision,
    work_on_day_off_addition double precision,
    primary key (id)
) engine = InnoDB;
create table official_holiday
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    from_date        date,
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    to_date          date,
    primary key (id)
) engine = InnoDB;
create table plan_details
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    price            double precision,
    currency_id      bigint,
    primary key (id)
) engine = InnoDB;
create table qr_machine
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    change_duration  bigint,
    is_static        bit,
    mac_address      varchar(255),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    branch_id        bigint,
    department_id    bigint,
    primary key (id)
) engine = InnoDB;
create table report_category
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    primary key (id)
) engine = InnoDB;
create table report_definition
(
    id                 bigint not null auto_increment,
    creation_date      DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date   DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    file_name          varchar(255),
    file_path          varchar(255),
    jasper_file_name   varchar(255),
    name_ar            varchar(255),
    name_en            varchar(255),
    name_tr            varchar(255),
    size               bigint,
    report_category_id bigint,
    primary key (id)
) engine = InnoDB;
create table report_definition_params
(
    report_definition_id bigint       not null,
    parameter_type       varchar(255),
    parameter_name       varchar(255) not null,
    primary key (report_definition_id, parameter_name)
) engine = InnoDB;
create table rest_log
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    date             datetime(6),
    from_ip_address  varchar(255),
    name             varchar(255),
    request_body     varchar(255),
    request_headers  varchar(255),
    request_method   varchar(255),
    request_name     varchar(255),
    primary key (id)
) engine = InnoDB;
create table revinfo
(
    rev      integer not null auto_increment,
    revtstmp bigint,
    primary key (rev)
) engine = InnoDB;
create table salary
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    absence_days     integer,
    addition         double precision,
    deduction        double precision,
    early_go_minutes integer,
    from_date        date,
    late_minutes     integer,
    over_time_hours  integer,
    salary           double precision,
    to_date          date,
    total            double precision,
    total_hours      integer,
    employee_id      bigint,
    primary key (id)
) engine = InnoDB;
create table salary_calculation
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    description_ar   longtext,
    description_en   longtext,
    description_tr   longtext,
    from_date        datetime(6),
    to_date          datetime(6),
    branch_id        bigint,
    department_id    bigint,
    employee_id      bigint,
    primary key (id)
) engine = InnoDB;
create table subscription
(
    id                   bigint not null auto_increment,
    creation_date        DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date     DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    from_date            datetime(6),
    to_date              datetime(6),
    company_id           bigint,
    subscription_plan_id bigint,
    primary key (id)
) engine = InnoDB;
create table subscription_plan
(
    id                   bigint not null auto_increment,
    creation_date        DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date     DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    duration_days        integer,
    from_no_of_employees integer,
    name_ar              varchar(255),
    name_en              varchar(255),
    name_tr              varchar(255),
    to_no_of_employees   integer,
    primary key (id)
) engine = InnoDB;
create table subscription_plan_plan_details
(
    subscription_plan_id bigint not null,
    plan_details_id      bigint not null
) engine = InnoDB;
create table terms_and_conditions
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    body_ar          longtext,
    body_en          longtext,
    body_tr          longtext,
    title_ar         varchar(255),
    title_en         varchar(255),
    title_tr         varchar(255),
    primary key (id)
) engine = InnoDB;
create table user
(
    id             bigint       not null auto_increment,
    company_id     bigint,
    email          varchar(255),
    fcm_token      varchar(255),
    full_name_ar   varchar(255),
    full_name_en   varchar(255),
    full_name_tr   varchar(255),
    image          varchar(255),
    mac_address    varchar(255),
    password       varchar(255) not null,
    temporary_code varchar(255),
    tenant         bigint,
    username       varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create table user_roles
(
    user_id bigint not null,
    roles   varchar(255)
) engine = InnoDB;
create table user_mapping
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    company_id       bigint,
    username         varchar(255),
    primary key (id)
) engine = InnoDB;
create table work_shift_aud
(
    id               bigint  not null,
    rev              integer not null,
    revtype          tinyint,
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    from_hour        time,
    to_hour          time,
    update_date_time bigint,
    primary key (id, rev)
) engine = InnoDB;
create table work_shift_custom_shift_hours_aud
(
    rev                   integer not null,
    work_shift_id         bigint  not null,
    custom_shift_hours_id bigint  not null,
    revtype               tinyint,
    primary key (rev, work_shift_id, custom_shift_hours_id)
) engine = InnoDB;
create table work_shift
(
    id               bigint not null auto_increment,
    creation_date    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    last_update_date DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    from_hour        time,
    to_hour          time,
    update_date_time bigint,
    primary key (id)
) engine = InnoDB;
create table work_shift_custom_shift_hours
(
    work_shift_id         bigint not null,
    custom_shift_hours_id bigint not null
) engine = InnoDB;
alter table company
    add constraint UK_ee5uuyd3smhswaew3mw9r8anc unique (database_name);
alter table employee
    add constraint UK_4ts03wxs8exmr93khm543lt4x unique (mobile);
alter table employee_optional_branches
    add constraint UK2oqns5tujd685w36dyyi06a24 unique (employee_id, branch_id);
alter table employee_shifts
    add constraint UKi3s1x6lxlq5wcmrn1vx27trmm unique (employee_id, workshift_id);
alter table qr_machine
    add constraint UK_6jigeaaed0soa2suvrph78och unique (mac_address);
alter table subscription_plan_plan_details
    add constraint UK_aoier6jccoccpmludc02a0pvd unique (plan_details_id);
alter table user
    add constraint UK_nxb9sa2vxscd9ik979vy5ae00 unique (mac_address);
alter table user
    add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);
alter table user_mapping
    add constraint UK2e3wipoc2im8wsqvlwq23egv4 unique (username, company_id);
alter table work_shift_custom_shift_hours
    add constraint UK_5dfv48o95eu0jiyap3lt4t9d unique (custom_shift_hours_id);
alter table attendance
    add constraint FKr7q0h8jfngkyybll6o9r3h9ua foreign key (employee_id) references employee (id);
alter table attendance
    add constraint FKfj4bkjnxb247ghct6vvlfwnis foreign key (work_shift_id) references work_shift (id);
alter table branch_aud
    add constraint FK6jkm88122iurk8et84u29phc2 foreign key (rev) references revinfo (rev);
alter table branch_days_off_aud
    add constraint FK9of2m2nr96yuk7oc5lv4w3fuw foreign key (rev) references revinfo (rev);
alter table branch_days_off
    add constraint FKow2rw55b13362icw869litx9d foreign key (branch_id) references branch (id);
alter table city
    add constraint FKrpd7j1p7yxr784adkx4pyepba foreign key (country_id) references country (id);
alter table company
    add constraint FKaa85rotlnir4w4xlj1nkilnws foreign key (country_id) references country (id);
alter table company_subscriptions
    add constraint FKbtbrvgnfjur4qtcao3dvog46 foreign key (company_id) references company (id);
alter table custom_shift_hours_aud
    add constraint FKtbne2pbap1qd3ibuoqypt4xgo foreign key (rev) references revinfo (rev);
alter table department_aud
    add constraint FKdrxjxvx2qlyxtsq8teb2fgqy8 foreign key (rev) references revinfo (rev);
alter table department_days_off_aud
    add constraint FK6of6mi3ko5gxgaqhkn96u8er5 foreign key (rev) references revinfo (rev);
alter table department_days_off
    add constraint FK78rstmj8cbcxtnh0v2ee6v7ap foreign key (department_id) references department (id);
alter table employee
    add constraint FKcvhlsx8tao1rxt7mpxrot61jt foreign key (branch_id) references branch (id);
alter table employee
    add constraint FKbejtwvg9bxus2mffsm3swj3u9 foreign key (department_id) references department (id);
alter table employee
    add constraint FK6lk0xml9r7okjdq0onka4ytju foreign key (user_id) references user (id);
alter table employee_aud
    add constraint FK118cwnbfk1ny0ttu4bfqmeh8q foreign key (rev) references revinfo (rev);
alter table employee_days_off_aud
    add constraint FKl5kgvlawdkkrm3f697ks53pwl foreign key (rev) references revinfo (rev);
alter table employee_days_off
    add constraint FKbtnrafos5kppyrf3d4a0fe11g foreign key (employee_id) references employee (id);
alter table employee_optional_branches
    add constraint FKen18yi8vumfpwfidw15b06665 foreign key (branch_id) references branch (id);
alter table employee_optional_branches
    add constraint FKnath5xu6xtuey30gjbqsmwyuc foreign key (employee_id) references employee (id);
alter table employee_optional_branches_aud
    add constraint FKjw3aolhuw27bdnxuhd7l71y5b foreign key (rev) references revinfo (rev);
alter table employee_shifts
    add constraint FK8yghl6sh7ou8wy4jwueo6xu4r foreign key (workshift_id) references work_shift (id);
alter table employee_shifts
    add constraint FKpa5cdc7udnmyis0lh91m7qfb foreign key (employee_id) references employee (id);
alter table employee_shifts_aud
    add constraint FK9icyl32o4jldpsqy3eq6fwkby foreign key (rev) references revinfo (rev);
alter table employee_user_type_aud
    add constraint FKldd8hm9h0e0gjkfkwrgc31v18 foreign key (rev) references revinfo (rev);
alter table employee_user_type
    add constraint FK3ssl0vg1bkoqa575606k35qmq foreign key (employee_id) references employee (id);
alter table hr_permissions
    add constraint FK3gifnd8hobuhejyguqfjrvy8a foreign key (employee_id) references employee (id);
alter table plan_details
    add constraint FKe0agn054hd4djs4glcyq5xvdg foreign key (currency_id) references currency (id);
alter table qr_machine
    add constraint FK60mj082ixc5qju9y7i9qixla6 foreign key (branch_id) references branch (id);
alter table qr_machine
    add constraint FKhta5dwyoquq9kygdv4nol5o6s foreign key (department_id) references department (id);
alter table report_definition
    add constraint FKko7od89jyvjjd9hb8qwsjf44 foreign key (report_category_id) references report_category (id);
alter table report_definition_params
    add constraint FKenm5vd32id4ft0jan8ft3cm81 foreign key (report_definition_id) references report_definition (id);
alter table salary
    add constraint FKnlnv3jbyvbiu8ci59r3btlk00 foreign key (employee_id) references employee (id);
alter table salary_calculation
    add constraint FKkplb4c70e3o3rbkolf1c6j2y2 foreign key (branch_id) references branch (id);
alter table salary_calculation
    add constraint FK7uxytcrisxrxpgf8ue166l9ir foreign key (department_id) references department (id);
alter table salary_calculation
    add constraint FK79ja34bct8v93daheht4dmhj7 foreign key (employee_id) references employee (id);
alter table subscription
    add constraint FK45i0k0ls0erwl77ei45ds25t8 foreign key (company_id) references company (id);
alter table subscription
    add constraint FKhn8hnxbdoi29nb4m7ojkocqfm foreign key (subscription_plan_id) references subscription_plan (id);
alter table subscription_plan_plan_details
    add constraint FKrsy21ydncno4c82k7uqc6tlgi foreign key (plan_details_id) references plan_details (id);
alter table subscription_plan_plan_details
    add constraint FKdx2qg3vw83ytg4vigpvb5bdsw foreign key (subscription_plan_id) references subscription_plan (id);
alter table user_roles
    add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references user (id);
alter table work_shift_aud
    add constraint FKqv2idp0w2y0ptqbgt9fe1jtx4 foreign key (rev) references revinfo (rev);
alter table work_shift_custom_shift_hours_aud
    add constraint FKbfyj19ra1wikubovws22kv6jm foreign key (rev) references revinfo (rev);
alter table work_shift_custom_shift_hours
    add constraint FKnx9etpf3qwy27ws8wee7hf3ug foreign key (custom_shift_hours_id) references custom_shift_hours (id);
alter table work_shift_custom_shift_hours
    add constraint FK3hcii5cpfbyjq0w3j2yuumbdi foreign key (work_shift_id) references work_shift (id);
