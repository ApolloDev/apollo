SET SCHEMA 'public';
DROP TABLE IF EXISTS catalog_of_uris CASCADE;
DROP TABLE IF EXISTS library_objects CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS release_versions CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS comment_type CASCADE;
DROP FUNCTION IF EXISTS increment_version();
DROP FUNCTION IF EXISTS set_date();
DROP CAST IF EXISTS (VARCHAR AS json);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    user_name TEXT NOT NULL,
    hash_of_user_password_and_salt TEXT NOT NULL,
    salt TEXT NOT NULL,
    user_email TEXT,
    CONSTRAINT user_unique UNIQUE (user_name, hash_of_user_password_and_salt)
);

CREATE TABLE roles (
  id SERIAL PRIMARY KEY,
  description TEXT
);

INSERT INTO roles (description) VALUES ('committer');
INSERT INTO roles (description) VALUES ('reviewer');
INSERT INTO roles (description) VALUES ('readonly');

CREATE TABLE user_roles (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users (id),
    role_id INT REFERENCES roles (id),
    CONSTRAINT user_role_unique UNIQUE (user_id, role_id)
);

CREATE TABLE catalog_of_uris (
    id SERIAL PRIMARY KEY,
    uri TEXT NOT NULL UNIQUE
);

CREATE TABLE library_objects (
    id SERIAL PRIMARY KEY,
    catalog_uri_id INT REFERENCES catalog_of_uris (id) NOT NULL,
    version INT NOT NULL,
    date TIMESTAMP WITH TIME ZONE NOT NULL,
    json_of_library_object JSON NOT NULL,
    user_id INT REFERENCES users (id) NOT NULL,
    CONSTRAINT library_object_unique UNIQUE (catalog_uri_id, version)
);

CREATE TABLE release_versions (
    id SERIAL PRIMARY KEY,
    catalog_uri_id INT REFERENCES catalog_of_uris (id) NOT NULL UNIQUE,
    item_id INT REFERENCES library_objects (id) NOT NULL
);

CREATE TABLE comment_type (
  id SERIAL PRIMARY KEY,
  description TEXT NOT NULL
);

INSERT INTO comment_type (description) VALUES ('commit');
INSERT INTO comment_type (description) VALUES ('review');
INSERT INTO comment_type (description) VALUES ('release');

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    item_id INT REFERENCES library_objects (id) NOT NULL,
    date TIMESTAMP WITH TIME ZONE NOT NULL,
    comment TEXT NOT NULL,
    comment_type INT REFERENCES comment_type (id) NOT NULL,
    user_id INT REFERENCES users (id) NOT NULL

);

CREATE FUNCTION increment_version() RETURNS TRIGGER AS '
DECLARE x INT;
BEGIN 
    x := (SELECT MAX(version) FROM library_objects WHERE catalog_uri_id = NEW.catalog_uri_id);
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