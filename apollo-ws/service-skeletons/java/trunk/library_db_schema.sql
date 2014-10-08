DROP TABLE IF EXISTS catalog_of_uuids CASCADE;
DROP TABLE IF EXISTS library_objects CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS public_versions CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP FUNCTION IF EXISTS increment_version();
DROP FUNCTION IF EXISTS set_date();
DROP CAST IF EXISTS (VARCHAR AS json);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    user_name TEXT NOT NULL,
    hash_of_user_password_and_salt TEXT NOT NULL,
    salt TEXT NOT NULL,
    user_email TEXT
);

CREATE TABLE roles (
  id SERIAL PRIMARY KEY,
  description TEXT
);

INSERT INTO roles (description) VALUES ('Editor');
INSERT INTO roles (description) VALUES ('Read only');

CREATE TABLE user_roles (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users (id),
    role_id INT REFERENCES roles (id),
    CONSTRAINT user_role_unique UNIQUE (user_id, role_id)
);

CREATE TABLE catalog_of_uuids (
    uuid SERIAL PRIMARY KEY,
    description TEXT NOT NULL
);

CREATE TABLE library_objects (
    id SERIAL PRIMARY KEY,
    catalog_uuid INT REFERENCES catalog_of_uuids (uuid) NOT NULL,
    version INT NOT NULL,
    date TIMESTAMP WITH TIME ZONE NOT NULL,
    json_of_library_object JSON NOT NULL,
    user_id INT REFERENCES users (id) NOT NULL
);

CREATE TABLE public_versions (
    id SERIAL PRIMARY KEY,
    catalog_uuid INT REFERENCES catalog_of_uuids (uuid) NOT NULL,
    item_id INT REFERENCES library_objects (id) NOT NULL
);

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    item_id INT REFERENCES library_objects (id) NOT NULL,
    date TIMESTAMP WITH TIME ZONE NOT NULL,
    comment TEXT NOT NULL,
    user_id INT REFERENCES users (id) NOT NULL

);

CREATE FUNCTION increment_version() RETURNS TRIGGER AS '
DECLARE x INT;
BEGIN 
    x := (SELECT version FROM library_objects WHERE catalog_uuid = NEW.catalog_uuid);
    if (x IS NULL) THEN
        NEW.version = 1;
    ELSE
        NEW.version = (x + 1);
    END IF;
    
    RETURN NEW;
END' LANGUAGE 'plpgsql';
    
CREATE TRIGGER version_trigger
BEFORE INSERT ON library_objects
FOR EACH ROW EXECUTE PROCEDURE increment_version();

CREATE FUNCTION set_date() RETURNS TRIGGER AS '
BEGIN
    NEW.date = now();
    RETURN NEW;
END' LANGUAGE 'plpgsql';

CREATE TRIGGER item_date_trigger
BEFORE INSERT ON library_objects
FOR EACH ROW EXECUTE PROCEDURE set_date();

CREATE TRIGGER comments_date_trigger
BEFORE INSERT ON comments
FOR EACH ROW EXECUTE PROCEDURE set_date();

CREATE CAST (VARCHAR AS json) WITHOUT FUNCTION AS implicit;