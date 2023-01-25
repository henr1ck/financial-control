delete from flow;
delete from type;
delete from category;

alter table flow auto_increment = 0;
alter table category auto_increment = 0;
alter table type auto_increment = 0;

insert into type (name) values ('Entrada');
insert into type (name) values ('Saída');

insert into category (name) values ('Alimentação');
insert into category (name) values ('Saúde');
insert into category (name) values ('Imóvel');
insert into category (name) values ('Limpeza');
insert into category (name) values ('Contas');
