DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS role_description;

CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  requester_id VARCHAR(255) NOT NULL,
  requester_password VARCHAR(255) NOT NULL,
  requester_email VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);


CREATE TABLE roles (
  id INT NOT NULL AUTO_INCREMENT,
  description VARCHAR(255),
  PRIMARY KEY(id)
);


CREATE TABLE role_description (
  role_id INT REFERENCES roles(id),
  software_id INT REFERENCES software_identification(id),
  can_run_simulator BIT,
  allow_privileged_request BIT,
  CONSTRAINT role_description UNIQUE(role_id, software_id)
);


CREATE TABLE user_roles (
  user_id INT NOT NULL REFERENCES users(id),
  role_id INT NOT NULL REFERENCES roles(id),
  CONSTRAINT user_role_unique UNIQUE (user_id, role_id)
);