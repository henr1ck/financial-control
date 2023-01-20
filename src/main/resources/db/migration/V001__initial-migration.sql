create table category (
	id bigint auto_increment,
    name varchar(50) not null,

    primary key (id)
) engine=InnoDB charset=utf8mb4;

create table type (
	id bigint auto_increment,
    name varchar(50) not null,

    primary key (id)
) engine=InnoDB charset=utf8mb4;

create table flow (
	id bigint auto_increment,
    description varchar(50) not null,
    category_id bigint not null,
    type_id bigint not null,
    value decimal(12,2) not null,
    date timestamp not null,
    note varchar(200),

    primary key (id),
    constraint flow_category_fk foreign key (category_id) references category (id),
    constraint flow_type_fk foreign key (type_id) references type (id)
) engine=InnoDB charset=utf8mb4;
