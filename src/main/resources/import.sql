INSERT INTO role(name) VALUE ('ROLE_ADMIN');
INSERT INTO role(name) VALUE ('ROLE_USER');
INSERT INTO user(name, password, email) VALUES ('admin', 'admin', 'admin@admin.cz');
INSERT INTO user(name, password, email) VALUES ('anton', 'anton', 'anton@test.cz');
INSERT INTO user_role(users_id, roles_id) VALUES ('1', '1');
INSERT INTO user_role(users_id, roles_id) VALUES ('2', '2');
INSERT INTO category(name) VALUES ('hudba');
INSERT INTO category(name) VALUES ('lifestyle')