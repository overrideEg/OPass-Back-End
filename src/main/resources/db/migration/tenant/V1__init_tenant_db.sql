;
create table app_setting
(
    id          bigint not null auto_increment,
    created_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    about_ar    varchar(255),
    about_en    varchar(255),
    about_tr    varchar(255),
    address     varchar(255),
    email       varchar(255),
    latitude    double precision,
    longitude   double precision,
    website     varchar(255),
    creator_id  bigint,
    modifier_id bigint,
    primary key (id)
) engine = InnoDB;
create table attendance
(
    id            bigint not null auto_increment,
    created_at    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at    DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    att_status    varchar(255),
    att_type      varchar(255),
    scan_date     date,
    scan_time     time,
    creator_id    bigint,
    modifier_id   bigint,
    employee_id   bigint,
    work_shift_id bigint,
    primary key (id)
) engine = InnoDB;
create table branch
(
    id                          bigint not null auto_increment,
    created_at                  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at                  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    allowed_early_leave_minutes integer,
    allowed_late_leave_minutes  integer,
    allowed_late_minutes        integer,
    max_over_time_hours         integer,
    name_ar                     varchar(255),
    name_en                     varchar(255),
    name_tr                     varchar(255),
    phone_number                varchar(255),
    creator_id                  bigint,
    modifier_id                 bigint,
    primary key (id)
) engine = InnoDB;
create table branch_days_off
(
    branch_id bigint not null,
    days_off  integer
) engine = InnoDB;
create table city
(
    id          bigint not null auto_increment,
    created_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    name_ar     varchar(255),
    name_en     varchar(255),
    name_tr     varchar(255),
    creator_id  bigint,
    modifier_id bigint,
    country_id  bigint,
    primary key (id)
) engine = InnoDB;
create table company
(
    id             bigint not null auto_increment,
    created_at     DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at     DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    database_name  varchar(255),
    description_ar varchar(255),
    description_en varchar(255),
    description_tr varchar(255),
    enabled        bit,
    name_ar        varchar(255),
    name_en        varchar(255),
    name_tr        varchar(255),
    phone_number   varchar(255),
    time_zone      varchar(255),
    website        varchar(255),
    creator_id     bigint,
    modifier_id    bigint,
    country_id     bigint,
    primary key (id)
) engine = InnoDB;
create table country
(
    id          bigint not null auto_increment,
    created_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    name_ar     varchar(255),
    name_en     varchar(255),
    name_tr     varchar(255),
    creator_id  bigint,
    modifier_id bigint,
    primary key (id)
) engine = InnoDB;
create table currency
(
    id               bigint not null auto_increment,
    created_at       DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at       DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    default_currency bit,
    fraction_name_ar varchar(255),
    fraction_name_en varchar(255),
    fraction_name_tr varchar(255),
    icon             varchar(255),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    creator_id       bigint,
    modifier_id      bigint,
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
    id          bigint not null auto_increment,
    created_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    day         integer,
    from_hour   time,
    to_hour     time,
    creator_id  bigint,
    modifier_id bigint,
    primary key (id)
) engine = InnoDB;
create table department
(
    id                          bigint not null auto_increment,
    created_at                  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at                  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    allowed_early_leave_minutes integer,
    allowed_late_leave_minutes  integer,
    allowed_late_minutes        integer,
    max_over_time_hours         integer,
    name_ar                     varchar(255),
    name_en                     varchar(255),
    name_tr                     varchar(255),
    phone_number                varchar(255),
    creator_id                  bigint,
    modifier_id                 bigint,
    primary key (id)
) engine = InnoDB;
create table department_days_off
(
    department_id bigint not null,
    days_off      integer
) engine = InnoDB;
create table employee
(
    id                   bigint not null auto_increment,
    created_at           DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at           DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
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
    telephone2           varchar(255),
    website              varchar(255),
    contract_end_date    datetime(6),
    contract_start_date  datetime(6),
    created_user_id      bigint,
    firing_date          datetime(6),
    gender               varchar(255),
    name_ar              varchar(255),
    name_en              varchar(255),
    name_tr              varchar(255),
    salary               double precision,
    ssn                  varchar(255),
    status               varchar(255),
    creator_id           bigint,
    modifier_id          bigint,
    branch_id            bigint,
    city_id              bigint,
    country_id           bigint,
    department_id        bigint,
    primary key (id)
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
create table employee_shifts
(
    employee_id  bigint not null,
    workshift_id bigint not null
) engine = InnoDB;
create table employee_user_type
(
    employee_id bigint not null,
    user_type   varchar(255)
) engine = InnoDB;
create table faq
(
    id          bigint not null auto_increment,
    created_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    answer_ar   varchar(255),
    answer_en   varchar(255),
    answer_tr   varchar(255),
    question_ar varchar(255),
    question_en varchar(255),
    question_tr varchar(255),
    creator_id  bigint,
    modifier_id bigint,
    primary key (id)
) engine = InnoDB;
create table hibernate_sequence
(
    next_val bigint
) engine = InnoDB;
insert into hibernate_sequence
values (1);
create table hr_permissions
(
    id               bigint not null auto_increment,
    created_at       DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at       DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    absence_allowed  bit,
    date             date,
    description_ar   longtext,
    description_en   longtext,
    description_tr   longtext,
    minutes_early_go integer,
    minutes_late     integer,
    creator_id       bigint,
    modifier_id      bigint,
    employee_id      bigint,
    primary key (id)
) engine = InnoDB;
create table hr_settins
(
    id                       bigint not null auto_increment,
    created_at               DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at               DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    absence_day_deduction    double precision,
    delay_hour_deduction     double precision,
    over_time_addition       double precision,
    work_on_day_off_addition double precision,
    creator_id               bigint,
    modifier_id              bigint,
    primary key (id)
) engine = InnoDB;
create table official_holiday
(
    id          bigint not null auto_increment,
    created_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    from_date   date,
    name_ar     varchar(255),
    name_en     varchar(255),
    name_tr     varchar(255),
    to_date     date,
    creator_id  bigint,
    modifier_id bigint,
    primary key (id)
) engine = InnoDB;
create table plan_details
(
    id          bigint not null auto_increment,
    created_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    price       double precision,
    creator_id  bigint,
    modifier_id bigint,
    currency_id bigint,
    primary key (id)
) engine = InnoDB;
create table qr_machine
(
    id              bigint not null auto_increment,
    created_at      DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at      DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    change_duration bigint,
    is_static       bit,
    mac_address     varchar(255),
    name_ar         varchar(255),
    name_en         varchar(255),
    name_tr         varchar(255),
    creator_id      bigint,
    modifier_id     bigint,
    branch_id       bigint,
    department_id   bigint,
    primary key (id)
) engine = InnoDB;
create table report_category
(
    id          bigint not null auto_increment,
    created_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    name_ar     varchar(255),
    name_en     varchar(255),
    name_tr     varchar(255),
    creator_id  bigint,
    modifier_id bigint,
    primary key (id)
) engine = InnoDB;
create table report_definition
(
    id                 bigint not null auto_increment,
    created_at         DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at         DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    file_name          varchar(255),
    file_path          varchar(255),
    jasper_file_name   varchar(255),
    name_ar            varchar(255),
    name_en            varchar(255),
    name_tr            varchar(255),
    size               bigint,
    creator_id         bigint,
    modifier_id        bigint,
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
    id              bigint not null auto_increment,
    created_at      DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at      DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    date            datetime(6),
    from_ip_address varchar(255),
    name            varchar(255),
    request_body    varchar(255),
    request_headers varchar(255),
    request_method  varchar(255),
    request_name    varchar(255),
    creator_id      bigint,
    modifier_id     bigint,
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
    created_at       DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at       DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
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
    creator_id       bigint,
    modifier_id      bigint,
    employee_id      bigint,
    primary key (id)
) engine = InnoDB;
create table salary_calculation
(
    id             bigint not null auto_increment,
    created_at     DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at     DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    description_ar longtext,
    description_en longtext,
    description_tr longtext,
    from_date      datetime(6),
    to_date        datetime(6),
    creator_id     bigint,
    modifier_id    bigint,
    branch_id      bigint,
    department_id  bigint,
    employee_id    bigint,
    primary key (id)
) engine = InnoDB;
create table subscription
(
    id                   bigint not null auto_increment,
    created_at           DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at           DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    from_date            datetime(6),
    to_date              datetime(6),
    creator_id           bigint,
    modifier_id          bigint,
    company_id           bigint,
    subscription_plan_id bigint,
    primary key (id)
) engine = InnoDB;
create table subscription_plan
(
    id                   bigint not null auto_increment,
    created_at           DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at           DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    duration_days        integer,
    from_no_of_employees integer,
    name_ar              varchar(255),
    name_en              varchar(255),
    name_tr              varchar(255),
    to_no_of_employees   integer,
    creator_id           bigint,
    modifier_id          bigint,
    primary key (id)
) engine = InnoDB;
create table subscription_plan_plan_details
(
    subscription_plan_id bigint not null,
    plan_details_id      bigint not null
) engine = InnoDB;
create table terms_and_conditions
(
    id          bigint not null auto_increment,
    created_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at  DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    body_ar     longtext,
    body_en     longtext,
    body_tr     longtext,
    title_ar    varchar(255),
    title_en    varchar(255),
    title_tr    varchar(255),
    creator_id  bigint,
    modifier_id bigint,
    primary key (id)
) engine = InnoDB;
create table user
(
    id           bigint       not null,
    company_id   bigint,
    email        varchar(255),
    employee_id  bigint,
    full_name_ar varchar(255),
    full_name_en varchar(255),
    full_name_tr varchar(255),
    image        varchar(255),
    mac_address  varchar(255),
    password     varchar(255) not null,
    username     varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create table user_roles
(
    user_id bigint not null,
    roles   varchar(255)
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
    created_at       DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    updated_at       DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    from_hour        time,
    to_hour          time,
    update_date_time bigint,
    creator_id       bigint,
    modifier_id      bigint,
    primary key (id)
) engine = InnoDB;
create table work_shift_custom_shift_hours
(
    work_shift_id         bigint not null,
    custom_shift_hours_id bigint not null
) engine = InnoDB;
alter table employee
    add constraint UK_fopic1oh5oln2khj8eat6ino0 unique (email);
alter table employee
    add constraint UK_4ts03wxs8exmr93khm543lt4x unique (mobile);
alter table employee
    add constraint UK_f35rkopwr25n69dtp946lt3rh unique (ssn);
alter table employee_optional_branches
    add constraint UK2oqns5tujd685w36dyyi06a24 unique (employee_id, branch_id);
alter table employee_shifts
    add constraint UKi3s1x6lxlq5wcmrn1vx27trmm unique (employee_id, workshift_id);
alter table qr_machine
    add constraint UK_6jigeaaed0soa2suvrph78och unique (mac_address);
alter table subscription_plan_plan_details
    add constraint UK_aoier6jccoccpmludc02a0pvd unique (plan_details_id);
alter table user
    add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);
alter table user
    add constraint UK_nxb9sa2vxscd9ik979vy5ae00 unique (mac_address);
alter table user
    add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);
alter table work_shift_custom_shift_hours
    add constraint UK_5dfv48o95eu0jiyap3lt4t9d unique (custom_shift_hours_id);
alter table app_setting
    add constraint FK74lsf8mfkxpofu2cstnw2kovj foreign key (creator_id) references user (id);
alter table app_setting
    add constraint FKdk3hayglkual7ky6ao1630i26 foreign key (modifier_id) references user (id);
alter table attendance
    add constraint FK4h82m8eathl86u94kuha2usnn foreign key (creator_id) references user (id);
alter table attendance
    add constraint FKbw2q2413pvaygagwk6e7qcbeh foreign key (modifier_id) references user (id);
alter table attendance
    add constraint FKr7q0h8jfngkyybll6o9r3h9ua foreign key (employee_id) references employee (id);
alter table attendance
    add constraint FKfj4bkjnxb247ghct6vvlfwnis foreign key (work_shift_id) references work_shift (id);
alter table branch
    add constraint FK1gxi5vl5ufs2l8swye7g26n16 foreign key (creator_id) references user (id);
alter table branch
    add constraint FK4mavupar7e5wr8jeuda2d49up foreign key (modifier_id) references user (id);
alter table branch_days_off
    add constraint FKow2rw55b13362icw869litx9d foreign key (branch_id) references branch (id);
alter table city
    add constraint FK2lg2q8uwm0cfdh9v1fl4tstuh foreign key (creator_id) references user (id);
alter table city
    add constraint FKgbyeg5yximpue0od6swjgvxwb foreign key (modifier_id) references user (id);
alter table city
    add constraint FKrpd7j1p7yxr784adkx4pyepba foreign key (country_id) references country (id);
alter table company
    add constraint FK7t8613qhnhqj8xbsbk3dqubqw foreign key (creator_id) references user (id);
alter table company
    add constraint FKjg4vuf48qkk9j3ugbt51qxp7u foreign key (modifier_id) references user (id);
alter table company
    add constraint FKaa85rotlnir4w4xlj1nkilnws foreign key (country_id) references country (id);
alter table country
    add constraint FKry62bjrdeww844yf8586mdxw4 foreign key (creator_id) references user (id);
alter table country
    add constraint FKdumslqdg3foxtyxaev36lgxsi foreign key (modifier_id) references user (id);
alter table currency
    add constraint FKs66v4h2lap1efcf5aihypls1 foreign key (creator_id) references user (id);
alter table currency
    add constraint FKiutee72y34cu1lxcmsweg626k foreign key (modifier_id) references user (id);
alter table custom_shift_hours_aud
    add constraint FKtbne2pbap1qd3ibuoqypt4xgo foreign key (rev) references revinfo (rev);
alter table custom_shift_hours
    add constraint FKpe1sdarrlfubh4kkptb86729y foreign key (creator_id) references user (id);
alter table custom_shift_hours
    add constraint FKj816cgywltlvd23vobwo17q5d foreign key (modifier_id) references user (id);
alter table department
    add constraint FK6dakp93f8mjn55i13gwhnm9j0 foreign key (creator_id) references user (id);
alter table department
    add constraint FK52h4gsl3ee5pdxnptk4yign6m foreign key (modifier_id) references user (id);
alter table department_days_off
    add constraint FK78rstmj8cbcxtnh0v2ee6v7ap foreign key (department_id) references department (id);
alter table employee
    add constraint FK6ehi96i33kvvwvfrng1li1mjh foreign key (creator_id) references user (id);
alter table employee
    add constraint FK54kkb0c22ruysd50qux9o2kye foreign key (modifier_id) references user (id);
alter table employee
    add constraint FKcvhlsx8tao1rxt7mpxrot61jt foreign key (branch_id) references branch (id);
alter table employee
    add constraint FK289qfli0oe8ae3qcuafi4q3tf foreign key (city_id) references city (id);
alter table employee
    add constraint FKivtkrlfso4toqek7i2rul2ggy foreign key (country_id) references country (id);
alter table employee
    add constraint FKbejtwvg9bxus2mffsm3swj3u9 foreign key (department_id) references department (id);
alter table employee_days_off
    add constraint FKbtnrafos5kppyrf3d4a0fe11g foreign key (employee_id) references employee (id);
alter table employee_optional_branches
    add constraint FKen18yi8vumfpwfidw15b06665 foreign key (branch_id) references branch (id);
alter table employee_optional_branches
    add constraint FKnath5xu6xtuey30gjbqsmwyuc foreign key (employee_id) references employee (id);
alter table employee_shifts
    add constraint FK8yghl6sh7ou8wy4jwueo6xu4r foreign key (workshift_id) references work_shift (id);
alter table employee_shifts
    add constraint FKpa5cdc7udnmyis0lh91m7qfb foreign key (employee_id) references employee (id);
alter table employee_user_type
    add constraint FK3ssl0vg1bkoqa575606k35qmq foreign key (employee_id) references employee (id);
alter table faq
    add constraint FKqcjwi7f9bd2vuwjo9rwrt0pg6 foreign key (creator_id) references user (id);
alter table faq
    add constraint FKlfe2exe95678ljeq2470jxxhi foreign key (modifier_id) references user (id);
alter table hr_permissions
    add constraint FKfmf7ligotwebgchn4mu2jx346 foreign key (creator_id) references user (id);
alter table hr_permissions
    add constraint FK68grisk7e7ej2jdrn0wx6xfh8 foreign key (modifier_id) references user (id);
alter table hr_permissions
    add constraint FK3gifnd8hobuhejyguqfjrvy8a foreign key (employee_id) references employee (id);
alter table hr_settins
    add constraint FK1xn7lco3nyy3kjh4ftao3dma7 foreign key (creator_id) references user (id);
alter table hr_settins
    add constraint FKofbp2995ufeb7tfwynus7idfe foreign key (modifier_id) references user (id);
alter table official_holiday
    add constraint FK66owkuodkn13hydvdhkim3mvd foreign key (creator_id) references user (id);
alter table official_holiday
    add constraint FKnulm1mhi1wwsqw273jrybpcoi foreign key (modifier_id) references user (id);
alter table plan_details
    add constraint FK2cxvgoe9nf82opu9faq7tjruw foreign key (creator_id) references user (id);
alter table plan_details
    add constraint FK3397ctdra4wo9d3bvvqa0y1b8 foreign key (modifier_id) references user (id);
alter table plan_details
    add constraint FKe0agn054hd4djs4glcyq5xvdg foreign key (currency_id) references currency (id);
alter table qr_machine
    add constraint FKh885fmqwokduduxh92xtgjty1 foreign key (creator_id) references user (id);
alter table qr_machine
    add constraint FKi636beqqn87f7vib4kxb1lqt3 foreign key (modifier_id) references user (id);
alter table qr_machine
    add constraint FK60mj082ixc5qju9y7i9qixla6 foreign key (branch_id) references branch (id);
alter table qr_machine
    add constraint FKhta5dwyoquq9kygdv4nol5o6s foreign key (department_id) references department (id);
alter table report_category
    add constraint FK300ei8opsdanax0m0shox9fqw foreign key (creator_id) references user (id);
alter table report_category
    add constraint FKk96wfd0yq9oe0l165gdyjihk foreign key (modifier_id) references user (id);
alter table report_definition
    add constraint FKkyffxxq30m9q6hbu5rmw4eaoy foreign key (creator_id) references user (id);
alter table report_definition
    add constraint FKi82tk0u4kyr7duvcjm3kssomo foreign key (modifier_id) references user (id);
alter table report_definition
    add constraint FKko7od89jyvjjd9hb8qwsjf44 foreign key (report_category_id) references report_category (id);
alter table report_definition_params
    add constraint FKenm5vd32id4ft0jan8ft3cm81 foreign key (report_definition_id) references report_definition (id);
alter table rest_log
    add constraint FKsrjuk5vf4n6g3udhnyx3wypqh foreign key (creator_id) references user (id);
alter table rest_log
    add constraint FKogsom6kkitogmo3qafjtpvsdx foreign key (modifier_id) references user (id);
alter table salary
    add constraint FKofd4qtp1d3fyof5ysxnwn02pw foreign key (creator_id) references user (id);
alter table salary
    add constraint FKbh8qsevp6404lv3esnpc6271v foreign key (modifier_id) references user (id);
alter table salary
    add constraint FKnlnv3jbyvbiu8ci59r3btlk00 foreign key (employee_id) references employee (id);
alter table salary_calculation
    add constraint FKn8xrl3rjlepyhpdp943s15akp foreign key (creator_id) references user (id);
alter table salary_calculation
    add constraint FK3mt0gyscdr1oh4ja5hlmunufh foreign key (modifier_id) references user (id);
alter table salary_calculation
    add constraint FKkplb4c70e3o3rbkolf1c6j2y2 foreign key (branch_id) references branch (id);
alter table salary_calculation
    add constraint FK7uxytcrisxrxpgf8ue166l9ir foreign key (department_id) references department (id);
alter table salary_calculation
    add constraint FK79ja34bct8v93daheht4dmhj7 foreign key (employee_id) references employee (id);
alter table subscription
    add constraint FK9jyyf9h6hvka0t7qyxr71rev8 foreign key (creator_id) references user (id);
alter table subscription
    add constraint FKln23tevo2fv7ih6v3r19w84dg foreign key (modifier_id) references user (id);
alter table subscription
    add constraint FK45i0k0ls0erwl77ei45ds25t8 foreign key (company_id) references company (id);
alter table subscription
    add constraint FKhn8hnxbdoi29nb4m7ojkocqfm foreign key (subscription_plan_id) references subscription_plan (id);
alter table subscription_plan
    add constraint FKf6hldvl9xjnwovietgsmk3g9t foreign key (creator_id) references user (id);
alter table subscription_plan
    add constraint FK79oq60g3qvkcnlulf86g3p2oi foreign key (modifier_id) references user (id);
alter table subscription_plan_plan_details
    add constraint FKrsy21ydncno4c82k7uqc6tlgi foreign key (plan_details_id) references plan_details (id);
alter table subscription_plan_plan_details
    add constraint FKdx2qg3vw83ytg4vigpvb5bdsw foreign key (subscription_plan_id) references subscription_plan (id);
alter table terms_and_conditions
    add constraint FKdmevfufvi6ut2lc198q84rsru foreign key (creator_id) references user (id);
alter table terms_and_conditions
    add constraint FKdqrifu2rqvc719p8o0yehr5mw foreign key (modifier_id) references user (id);
alter table user_roles
    add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references user (id);
alter table work_shift_aud
    add constraint FKqv2idp0w2y0ptqbgt9fe1jtx4 foreign key (rev) references revinfo (rev);
alter table work_shift_custom_shift_hours_aud
    add constraint FKbfyj19ra1wikubovws22kv6jm foreign key (rev) references revinfo (rev);
alter table work_shift
    add constraint FKp1cpjy42wypv2ycmm44ym7f6u foreign key (creator_id) references user (id);
alter table work_shift
    add constraint FKjxvc7kmmojauebodefwkd34fw foreign key (modifier_id) references user (id);
alter table work_shift_custom_shift_hours
    add constraint FKnx9etpf3qwy27ws8wee7hf3ug foreign key (custom_shift_hours_id) references custom_shift_hours (id);
alter table work_shift_custom_shift_hours
    add constraint FK3hcii5cpfbyjq0w3j2yuumbdi foreign key (work_shift_id) references work_shift (id)
