create table `users`(
`login` varchar(20)  not null,
`password` varchar(30) not null,
`firstName` varchar(30) not null,
`lastName` varchar(30) not null,
`email` varchar(20) not null,
`status` enum('NEW','BLOKED','BENNET','ACTIVE'),
`created` timestamp not null default current_timestamp,
`updated` timestamp not null default current_timestamp on update current_timestamp,
primary key(`login`),
unique index(`login`),
unique index(`email`)
)engine=InnoDB default charset=UTF8;
