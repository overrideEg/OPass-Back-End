create table if not exists app_setting
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
create table if not exists attendance
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    att_type         varchar(255),
    early_go         bit,
    late_entrance    bit,
    over_time        bit,
    scan_date        datetime(6),
    scan_time        datetime(6),
    employee_id      bigint,
    primary key (id)
) engine = InnoDB
;
create table if not exists attendance_rules
(
    id                          bigint not null auto_increment,
    creation_date               datetime(6),
    last_update_date            datetime(6),
    allowed_early_leave_minutes integer,
    allowed_late_minutes        integer,
    max_over_time_hours         integer,
    primary key (id)
) engine = InnoDB
;
create table if not exists attendance_rules_days_off
(
    attendance_rules_id bigint not null,
    days_off            integer
) engine = InnoDB
;
create table if not exists branch
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    phone_number     varchar(255),
    primary key (id)
) engine = InnoDB
;
create table if not exists branch_days_off
(
    branch_id bigint not null,
    days_off  integer
) engine = InnoDB
;
create table if not exists city
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
create table if not exists company
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
    website          varchar(255),
    country_id       bigint,
    primary key (id)
) engine = InnoDB
;
create table if not exists country
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
create table if not exists currency
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
create table if not exists department
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    phone_number     varchar(255),
    primary key (id)
) engine = InnoDB
;
create table if not exists department_days_off
(
    department_id bigint not null,
    days_off      integer
) engine = InnoDB
;
create table if not exists employee
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
    telephone2           varchar(255),
    website              varchar(255),
    contract_end_date    datetime(6),
    contract_start_date  datetime(6),
    created_user_id      bigint,
    firing_date          datetime(6),
    name_ar              varchar(255),
    name_en              varchar(255),
    name_tr              varchar(255),
    ssn                  varchar(255),
    status               varchar(255),
    user_type            varchar(255),
    branch_id            bigint,
    city_id              bigint,
    country_id           bigint,
    department_id        bigint,
    primary key (id)
) engine = InnoDB
;
create table if not exists employee_shifts
(
    employee_id bigint not null,
    shift_id    bigint not null
) engine = InnoDB
;
create table if not exists faq
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
create table if not exists plan_details
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    price            double precision,
    currency_id      bigint,
    primary key (id)
) engine = InnoDB
;
create table if not exists qr_machine
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
create table if not exists report_detention
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    file_name        varchar(255),
    file_path        varchar(255),
    jasper_file_name varchar(255),
    name_ar          varchar(255),
    name_en          varchar(255),
    name_tr          varchar(255),
    size             bigint,
    primary key (id)
) engine = InnoDB
;
create table if not exists report_detention_params
(
    report_detention_id bigint       not null,
    parameter_type      varchar(255),
    parameter_name      varchar(255) not null,
    primary key (report_detention_id, parameter_name)
) engine = InnoDB
;
create table if not exists rest_log
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
create table if not exists subscription
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
create table if not exists subscription_plan
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
create table if not exists subscription_plan_plan_details
(
    subscription_plan_id bigint not null,
    plan_details_id      bigint not null
) engine = InnoDB
;
create table if not exists terms_and_conditions
(
    id               bigint not null auto_increment,
    creation_date    datetime(6),
    last_update_date datetime(6),
    body_ar          varchar(255),
    body_en          varchar(255),
    body_tr          varchar(255),
    title_ar         varchar(255),
    title_en         varchar(255),
    title_tr         varchar(255),
    primary key (id)
) engine = InnoDB
;
create table if not exists users
(
    id                 bigint      not null auto_increment,
    creation_date      datetime(6),
    last_update_date   datetime(6),
    company_id         bigint,
    email              varchar(255),
    encrypted_password varchar(255),
    image              varchar(255),
    mac_address        varchar(255),
    salt               varchar(255),
    token              varchar(255),
    user_id            varchar(255),
    user_name          varchar(15) not null,
    user_type          varchar(255),
    primary key (id)
) engine = InnoDB
;
create table if not exists work_shift
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
alter table users
    add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
;
alter table users
    add constraint UK_20nqacqigaop7erdjuc7qsfua unique (mac_address)
;
alter table users
    add constraint UK_k8d0f2n7n88w1a16yhua64onx unique (user_name)
;
alter table attendance
    add constraint FKr7q0h8jfngkyybll6o9r3h9ua foreign key (employee_id) references employee (id)
;
alter table attendance_rules_days_off
    add constraint FKogw9dtahceqlqpd9qsriodxbv foreign key (attendance_rules_id) references attendance_rules (id)
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
alter table employee_shifts
    add constraint FKf3sver1ayrxow0ekmx0ynr28a foreign key (shift_id) references work_shift (id)
;
alter table employee_shifts
    add constraint FKpa5cdc7udnmyis0lh91m7qfb foreign key (employee_id) references employee (id)
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
alter table report_detention_params
    add constraint FKix6901vwufram1cy1eqfmiebx foreign key (report_detention_id) references report_detention (id)
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
