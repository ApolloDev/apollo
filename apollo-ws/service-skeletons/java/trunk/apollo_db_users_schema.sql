DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS role_description;

CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  requester_id VARCHAR(255) NOT NULL,
  hash_of_user_password_and_salt VARCHAR(255) NOT NULL,
  salt VARCHAR(255) NOT NULL,
  user_email VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);


CREATE TABLE roles (
  id INT NOT NULL AUTO_INCREMENT,
  description VARCHAR(255),
  PRIMARY KEY(id)
);

INSERT INTO roles VALUES (1, 'Can run SEIR without privileged request');
INSERT INTO roles VALUES (2, 'Can run SEIR with privileged request');
INSERT INTO roles VALUES (3, 'Can run FRED without privileged request');
INSERT INTO roles VALUES(4, 'Can run FRED with privileged request');


CREATE TABLE role_description (
  role_id INT REFERENCES roles(id),
  software_id INT REFERENCES software_identification(id),
  can_run_software BIT,
  allow_privileged_request BIT,
  CONSTRAINT role_description UNIQUE(role_id, software_id)
);

INSERT INTO role_description VALUES(1, 2, 1, 0);
INSERT INTO role_description VALUES(2, 2, 1, 1);
INSERT INTO role_description VALUES(3, 3, 1, 0);
INSERT INTO role_description VALUES(4, 3, 1, 1);

CREATE TABLE user_roles (
  user_id INT NOT NULL REFERENCES users(id),
  role_id INT NOT NULL REFERENCES roles(id),
  CONSTRAINT user_role_unique UNIQUE (user_id, role_id)
);