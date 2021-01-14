create table `products`(
`product_name` varchar(14)not null,
`product_brand` varchar(14) not null,
`date_manufactore` date not null,
`end_date_second` date not null,
`end_date` date not null,
`created` timestamp not null default current_timestamp,
`updated` timestamp not null default current_timestamp on update current_timestamp,
primary key(`product_name`)
)engine=InnoDB default charset=UTF8;
