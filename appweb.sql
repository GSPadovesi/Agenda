/* Criar banco do projeto */
create database dbappweb;

/* Selecionar o banco dbappweb */
use dbappweb;

/* Mostrar os bancos */
show databases;

/* Criar table contatos */
create table contatos(
	idcon int primary key auto_increment,
    nome varchar(50) not null,
    fone varchar(15) not null,
    email varchar(50)
)

/* Mostrar as tables */
show tables;
describe contatos;

/* CRUD CREATE */
insert into contatos(nome,fone,email) values ("Teste","11 98745-7894","teste@email.com");

/* CRUD READ */
select * from contatos order by nome;

/* CRUD SELECT */
select * from contatos where idcon = 1;

/* CRUD UPDATE */
update contatos set nome="TESTE",fone="11 78543-7852",email="TESTE@email.com" where idcon = 2;

/* CRUD DELETE */
delete from contatos where idcon=7;