select * from cliente;
select * from cuenta;
select * from user;
select * from user_role;

drop table user_role;
drop table user;
drop table cuenta;
drop table cliente;

INSERT INTO user (username, password)
VALUES ('admin', '$2y$10$FgwVgyMFTWq7UKpaWUCsHuk2jLfmfWO88pvEMTMlB0Iko24K35kwC');
INSERT INTO user_role (username, role)
VALUES ('admin', 'ADMIN');
