alter table flow add column code varchar(36);
alter table flow add constraint unique_flow_code unique (code);
