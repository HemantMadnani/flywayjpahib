create table user(
user_id int primary key auto_increment,
full_name varchar(50),
email varchar(100),
password varchar(50) ,
active tinyint(1) DEFAULT 1
)engine=MyISAM;



