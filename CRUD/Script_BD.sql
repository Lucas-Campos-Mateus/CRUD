CREATE DATABASE crud DEFAULT CHARACTER SET UTF8 DEFAULT COLLATE utf8_general_ci;

create table pessoas(
id int auto_increment,
nome varchar(50) not null,
cpf varchar(11) not null unique key,
primary key(id)
)default charset = utf8;



