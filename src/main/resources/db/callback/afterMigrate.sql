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
insert into category (name) values ('Manutenção');
insert into category (name) values ('Contas');
insert into category (name) values ('Trabalho');

insert into flow (code, description, value, note, date, type_id, category_id)
 values ('fa02997d-7ee1-476d-81f9-32bb31d7f1bd', 'Cartela de ovos', 35.90,'Urgente para ontem!','2023-01-22T20:52:25.3275285-03:00', 2, 1);

insert into flow (code, description, value, date, type_id, category_id)
 values ('e696ba92-bb09-4bcb-bfc9-c9fd68723d95', 'Plano de saúde', 50, '2023-01-27T07:50:25-03:00', 2, 2);

insert into flow (code, description, value, date, type_id, category_id)
 values ('954c70fa-3c55-4fe6-b8bc-381eb39297f1', 'Aluguel', 1000, '2023-01-20T10:24:09-03:00', 1, 3);

insert into flow (code, description, value, note, date, type_id, category_id)
 values ('0bdc7a71-70c8-4b2b-9ae5-c400ad8d52e1', 'Conta de luz', 91.47, 'Atrasado!','2023-01-27T10:30:25-03:00', 2, 5);

insert into flow (code, description, value, note, date, type_id, category_id)
 values ('5182d821-50d9-4ee0-ada6-ef76c4449902', 'Conta de água', 67.05, 'Atrasado demais!','2023-01-27T14:10:33-03:00', 2, 5);

insert into flow (code, description, value, date, type_id, category_id)
 values ('85edb5c7-2afd-4d29-a27b-837c9f58c087', 'Salário', 1600, '2023-01-02T18:00:00-03:00', 1, 6);

insert into flow (code, description, value, date, type_id, category_id)
 values ('c6537384-cf50-483e-be9e-35b7ed8647eb', 'Pente de memória DDR3', 75, '2022-12-17T19:00:00-03:00', 2, 4);

insert into flow (code, description, value, date, type_id, category_id)
 values ('48197008-16ea-42e1-9dd1-889d6ed302f4', 'Pente de memória DDR3', 80, '2022-12-20T16:40:10-03:00', 2, 4);