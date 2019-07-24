INSERT INTO role(name) VALUES('ROLE_ADMIN');
INSERT INTO role(name) VALUES('ROLE_USER');
INSERT INTO user(email,username,password,role) values('pan@pawel.com','panpawel','piesek12','ROLE_ADMIN');
SELECT * FROM INFORMATION_SCHEMA.CONSTRAINTS WHERE TABLE_NAME = 'USERS'