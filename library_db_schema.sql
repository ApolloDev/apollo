SET SCHEMA 'public';
DROP TABLE IF EXISTS library_actions CASCADE;
DROP TABLE IF EXISTS library_item_action_history CASCADE;
DROP TABLE IF EXISTS library_item_container_urns CASCADE;
DROP TABLE IF EXISTS library_item_containers CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
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

CREATE TABLE library_item_container_urns (
    id SERIAL PRIMARY KEY
);

CREATE TABLE library_item_containers (
    id SERIAL PRIMARY KEY,
    urn_id INT REFERENCES library_item_container_urns (id) NOT NULL,
    version INT NOT NULL,
    is_latest_release_version BOOLEAN NOT NULL DEFAULT false,
    was_previously_released BOOLEAN NOT NULL DEFAULT false,
    date_created TIMESTAMP WITH TIME ZONE NOT NULL,
    json_representation JSONB NOT NULL,
    committer_id INT REFERENCES users (id) NOT NULL,
    CONSTRAINT unique_library_item_container UNIQUE (urn_id, version)
);

CREATE TABLE library_actions (
    id SERIAL PRIMARY KEY,
    action TEXT NOT NULL
);

INSERT INTO library_actions (action) VALUES ('added_item');
INSERT INTO library_actions (action) VALUES ('updated_item');
INSERT INTO library_actions (action) VALUES ('set_as_release_version');
INSERT INTO library_actions (action) VALUES ('removed_as_release_version');
INSERT INTO library_actions (action) VALUES ('added_reviewer_comment');

CREATE TABLE library_item_action_history (
    id SERIAL PRIMARY KEY,
    urn_id INT REFERENCES library_item_container_urns (id) NOT NULL,
    version INT NOT NULL,
    action_performed INT REFERENCES library_actions (id) NOT NULL,
    date_of_action TIMESTAMP WITH TIME ZONE NOT NULL
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
    item_id INT REFERENCES library_item_containers (id) NOT NULL,
    date_created TIMESTAMP WITH TIME ZONE NOT NULL,
    comment TEXT NOT NULL,
    comment_type INT REFERENCES comment_type (id) NOT NULL,
    user_id INT REFERENCES users (id) NOT NULL
);

CREATE FUNCTION increment_version() RETURNS TRIGGER AS '
DECLARE x INT;
BEGIN 
    x := (SELECT MAX(version) FROM library_item_containers WHERE urn_id = NEW.urn_id);
    if (x IS NULL) THEN
        NEW.version = 1;
    ELSE
        NEW.version = (x + 1);
    END IF;
    
    RETURN NEW;
END' LANGUAGE 'plpgsql';
    
CREATE TRIGGER version_trigger
BEFORE INSERT ON library_item_containers
FOR EACH ROW EXECUTE PROCEDURE increment_version();

CREATE FUNCTION set_date() RETURNS TRIGGER AS '
BEGIN
    NEW.date_created = now();
    RETURN NEW;
END' LANGUAGE 'plpgsql';

CREATE TRIGGER item_date_trigger
BEFORE INSERT ON library_item_containers
FOR EACH ROW EXECUTE PROCEDURE set_date();

CREATE TRIGGER comments_date_trigger
BEFORE INSERT ON comments
FOR EACH ROW EXECUTE PROCEDURE set_date();

CREATE CAST (VARCHAR AS json) WITHOUT FUNCTION AS implicit;