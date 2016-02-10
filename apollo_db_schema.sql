/*  Comments starting with "-- HSQLDB: " are commands that are interpreted when
this script is used for the ApolloDbUtils unit tests (which use HSQLDB).

All commands listed after "-- HSQLDB: END;" will NOT be run when this script is
used in the unit tests. These commands are MySQL-specific and will not work with HSQLDB.
 */

DROP VIEW IF EXISTS translator_output_content_view;
DROP VIEW IF EXISTS software_input;

DROP TABLE IF EXISTS run_status;
DROP TABLE IF EXISTS run_status_description;
DROP TABLE IF EXISTS simulation_group_definition;

DROP TABLE IF EXISTS role_description;

DROP TABLE IF EXISTS simulated_population;
DROP TABLE IF EXISTS population_axis;
DROP TABLE IF EXISTS simulated_population_axis_value;
DROP TABLE IF EXISTS time_series;
DROP TABLE IF EXISTS run;


DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

DROP TABLE IF EXISTS software_identification;
DROP TABLE IF EXISTS simulation_groups;

CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  requester_id VARCHAR(255) NOT NULL,
  hash_of_user_password_and_salt VARCHAR(255) NOT NULL,
  salt VARCHAR(255) NOT NULL,
  user_email VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

-- HSQLDB: ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

INSERT INTO users VALUES (1, 'default_software_admin', 'dummy_hash', 'dummy_salt', 'dummy_email');

CREATE TABLE software_identification (
  id INT NOT NULL AUTO_INCREMENT,
  developer VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  version VARCHAR(255) NOT NULL,
  service_type VARCHAR(255) NOT NULL,
  wsdl_url TEXT NOT NULL,
  admin_id INT NOT NULL REFERENCES users(id),
  license_name TEXT,
  license_version TEXT,
  license_url TEXT,
  license_attribution TEXT,
  PRIMARY KEY (id)
);

-- HSQLDB: ALTER TABLE software_identification ALTER COLUMN id RESTART WITH 1;

INSERT INTO `software_identification` VALUES (1,'UPitt','Translator','1.0','translator','http://localhost:8080/translator-service-war-4.0-SNAPSHOT/services/translatorservice?wsdl',1, NULL, NULL, NULL, NULL),
											 (2,'UPitt','SEIR','3.0','simulator','http://localhost:8080/pitt-simulator-service-war-4.0-SNAPSHOT/services/pittsimulatorservice?wsdl',1, NULL, NULL, NULL, NULL),
											 (3,'UPitt,PSC,CMU','FRED','2.0.1_i','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
											 (4,'UPitt','Time Series Visualizer','1.0','visualizer','http://localhost:8080/visualizer-service-war-4.0-SNAPSHOT/services/visualizerservice?wsdl',1, NULL, NULL, NULL ,NULL),
											 (5,'PSC','GAIA','1.0','visualizer','http://gaia.pha.psc.edu:13501/gaia?wsdl',1, NULL, NULL, NULL, NULL),
											 (6,'Chao-FredHutchinsonCancerCenter','FluTE','1.15','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
											 (7,'UPitt','Anthrax','1.0','simulator','http://localhost:8080/pitt-simulator-service-war-4.0-SNAPSHOT/services/pittsimulatorservice?wsdl',1, NULL, NULL, NULL, NULL),
                                             (8,'PSC','CLARA','0.5','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                                             (9,'Steve Bellan','Lancet Ebola','1.0','simulator','http://localhost:8080/pitt-simulator-service-war-4.0-SNAPSHOT/services/pittsimulatorservice?wsdl',1, 'Creative Commons Attribution 4.0 International License', '4.0', 'http://creativecommons.org/licenses/by/4.0/', 'The Lancet Ebola simulator was used to produce results for the following publication:\n\nBellan SE, JRC Pulliam, J Dushoff, and LA Meyers. Asymptomatic infection, natural immunity, and Ebola dynamics. Letter, submitted to Lancet.\n\nThe original source code for the simulator is available at https://github.com/ICI3D/Ebola. The code was changed for use with the Apollo Web Services to do the following:\n\n - Read initial compartment sizes, transmission and disease parameters from an external input file\n - Produce time series output files for each compartment\n - Compute a newly exposed time series from the cumulative exposed time series'),
											 (10,'UPitt','Data Service','1.0','data','http://localhost:8080/data-service-war-4.0-SNAPSHOT/services/dataservice?wsdl',1, NULL, NULL, NULL, NULL),
											 (11,'UPitt', 'Broker Service','4.0','broker','http://localhost:8080/broker-service-war-4.0-SNAPSHOT/services/apolloservice?wsdl',1, NULL, NULL, NULL ,NULL),
                       (12,'any', 'any','any','endUserApplication','not_applicable',1, NULL, NULL, NULL ,NULL),
                       (13,'UPitt', 'Run Manager','4.0','runManager','http://localhost:8080/run-manager-service-rest-frontend-4.0-SNAPSHOT/',1, NULL, NULL, NULL ,NULL),
                       (14,'Swiss TPH','OpenMalaria','R0063','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (15,'Swiss TPH','OpenMalaria','R0065','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (16,'Swiss TPH','OpenMalaria','R0068','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (17,'Swiss TPH','OpenMalaria','R0111','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (18,'Swiss TPH','OpenMalaria','R0115','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (19,'Swiss TPH','OpenMalaria','R0121','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (20,'Swiss TPH','OpenMalaria','R0125','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (21,'Swiss TPH','OpenMalaria','R0131','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (22,'Swiss TPH','OpenMalaria','R0132','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (23,'Swiss TPH','OpenMalaria','R0133','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (24,'Swiss TPH','OpenMalaria','R0670','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (25,'Swiss TPH','OpenMalaria','R0674','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (26,'Swiss TPH','OpenMalaria','R0678','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                       (27,'Swiss TPH','OpenMalaria','base','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL);










CREATE TABLE roles (
  id INT NOT NULL AUTO_INCREMENT,
  description VARCHAR(255),
  PRIMARY KEY(id)
);

-- HSQLDB: ALTER TABLE roles ALTER COLUMN id RESTART WITH 1;

INSERT INTO roles VALUES (1, 'Can run SEIR without privileged request');
INSERT INTO roles VALUES (2, 'Can run SEIR with privileged request');
INSERT INTO roles VALUES (3, 'Can view cached SEIR results');

INSERT INTO roles VALUES (4, 'Can run FRED without privileged request');
INSERT INTO roles VALUES (5, 'Can run FRED with privileged request');
INSERT INTO roles VALUES (6, 'Can view cached FRED results');

INSERT INTO roles VALUES (7, 'Can run FluTE without privileged request');
INSERT INTO roles VALUES (8, 'Can run FluTE with privileged request');
INSERT INTO roles VALUES (9, 'Can view cached FluTE results');

INSERT INTO roles VALUES (10, 'Can run TSV without privileged request');
INSERT INTO roles VALUES (11, 'Can run TSV with privileged request');
INSERT INTO roles VALUES (12, 'Can view cached TSV results');

INSERT INTO roles VALUES (13, 'Can run GAIA without privileged request');
INSERT INTO roles VALUES (14, 'Can run GAIA with privileged request');
INSERT INTO roles VALUES (15, 'Can view cached GAIA results');

INSERT INTO roles VALUES (16, 'Can run Anthrax without privileged request');
INSERT INTO roles VALUES (17, 'Can run Anthrax with privileged request');
INSERT INTO roles VALUES (18, 'Can view cached Anthrax results');

INSERT INTO roles VALUES (19, 'Can run CLARA without privileged request');
INSERT INTO roles VALUES (20, 'Can run CLARA with privileged request');
INSERT INTO roles VALUES (21, 'Can view cached CLARA results');

INSERT INTO roles VALUES (22, 'Can run Lancet Ebola without privileged request');
INSERT INTO roles VALUES (23, 'Can run Lancet Ebola with privileged request');
INSERT INTO roles VALUES (24, 'Can view cached Lancet Ebola results');

INSERT INTO roles VALUES (25, 'Can run Data Service without privileged request');
INSERT INTO roles VALUES (26, 'Can view cached Data Service results');

INSERT INTO roles VALUES (27, 'Can run Open Malaria R0063 without privileged request');
INSERT INTO roles VALUES (28, 'Can run Open Malaria R0063 with privileged request');
INSERT INTO roles VALUES (29, 'Can view cached Open Malaria R0063 results');

INSERT INTO roles VALUES (30, 'Can run Open Malaria R0065 without privileged request');
INSERT INTO roles VALUES (31, 'Can run Open Malaria R0065 with privileged request');
INSERT INTO roles VALUES (32, 'Can view cached Open Malaria R0065 results');

INSERT INTO roles VALUES (33, 'Can run Open Malaria R0068 without privileged request');
INSERT INTO roles VALUES (34, 'Can run Open Malaria R0068 with privileged request');
INSERT INTO roles VALUES (35, 'Can view cached Open Malaria R0068 results');

INSERT INTO roles VALUES (36, 'Can run Open Malaria R0111 without privileged request');
INSERT INTO roles VALUES (37, 'Can run Open Malaria R0111 with privileged request');
INSERT INTO roles VALUES (38, 'Can view cached Open Malaria R0111 results');

INSERT INTO roles VALUES (39, 'Can run Open Malaria R0115 without privileged request');
INSERT INTO roles VALUES (40, 'Can run Open Malaria R0115 with privileged request');
INSERT INTO roles VALUES (41, 'Can view cached Open Malaria R0115 results');

INSERT INTO roles VALUES (42, 'Can run Open Malaria R0121 without privileged request');
INSERT INTO roles VALUES (43, 'Can run Open Malaria R0121 with privileged request');
INSERT INTO roles VALUES (44, 'Can view cached Open Malaria R0121 results');

INSERT INTO roles VALUES (45, 'Can run Open Malaria R0125 without privileged request');
INSERT INTO roles VALUES (46, 'Can run Open Malaria R0125 with privileged request');
INSERT INTO roles VALUES (47, 'Can view cached Open Malaria R0125 results');

INSERT INTO roles VALUES (48, 'Can run Open Malaria R0131 without privileged request');
INSERT INTO roles VALUES (49, 'Can run Open Malaria R0131 with privileged request');
INSERT INTO roles VALUES (50, 'Can view cached Open Malaria R0131 results');

INSERT INTO roles VALUES (51, 'Can run Open Malaria R0132 without privileged request');
INSERT INTO roles VALUES (52, 'Can run Open Malaria R0132 with privileged request');
INSERT INTO roles VALUES (53, 'Can view cached Open Malaria R0132 results');

INSERT INTO roles VALUES (54, 'Can run Open Malaria R0133 without privileged request');
INSERT INTO roles VALUES (55, 'Can run Open Malaria R0133 with privileged request');
INSERT INTO roles VALUES (56, 'Can view cached Open Malaria R0133 results');

INSERT INTO roles VALUES (57, 'Can run Open Malaria R0670 without privileged request');
INSERT INTO roles VALUES (58, 'Can run Open Malaria R0670 with privileged request');
INSERT INTO roles VALUES (59, 'Can view cached Open Malaria R0670 results');

INSERT INTO roles VALUES (60, 'Can run Open Malaria R0674 without privileged request');
INSERT INTO roles VALUES (61, 'Can run Open Malaria R0674 with privileged request');
INSERT INTO roles VALUES (62, 'Can view cached Open Malaria R0674 results');

INSERT INTO roles VALUES (63, 'Can run Open Malaria R0678 without privileged request');
INSERT INTO roles VALUES (64, 'Can run Open Malaria R0678 with privileged request');
INSERT INTO roles VALUES (65, 'Can view cached Open Malaria R0678 results');

INSERT INTO roles VALUES (66, 'Can run Open Malaria base without privileged request');
INSERT INTO roles VALUES (67, 'Can run Open Malaria base with privileged request');
INSERT INTO roles VALUES (68, 'Can view cached Open Malaria base results');

CREATE TABLE role_description (
  role_id INT REFERENCES roles(id),
  software_id INT REFERENCES software_identification(id),
  can_run_software BIT,
  allow_privileged_request BIT,
  CONSTRAINT role_description UNIQUE(role_id, software_id)
);

INSERT INTO role_description VALUES(1, 2, 1, 0);
INSERT INTO role_description VALUES(2, 2, 1, 1);
INSERT INTO role_description VALUES(3, 2, 0, 0);

INSERT INTO role_description VALUES(4, 3, 1, 0);
INSERT INTO role_description VALUES(5, 3, 1, 1);
INSERT INTO role_description VALUES(6, 3, 0, 0);

INSERT INTO role_description VALUES(7, 6, 1, 0);
INSERT INTO role_description VALUES(8, 6, 1, 1);
INSERT INTO role_description VALUES(9, 6, 0, 0);

INSERT INTO role_description VALUES(10, 4, 1, 0);
INSERT INTO role_description VALUES(11, 4, 1, 1);
INSERT INTO role_description VALUES(12, 4, 0, 0);

INSERT INTO role_description VALUES(13, 5, 1, 0);
INSERT INTO role_description VALUES(14, 5, 1, 1);
INSERT INTO role_description VALUES(15, 5, 0, 0);

INSERT INTO role_description VALUES(16, 7, 1, 0);
INSERT INTO role_description VALUES(17, 7, 1, 1);
INSERT INTO role_description VALUES(18, 7, 0, 0);

INSERT INTO role_description VALUES(19, 8, 1, 0);
INSERT INTO role_description VALUES(20, 8, 1, 1);
INSERT INTO role_description VALUES(21, 8, 0, 0);

INSERT INTO role_description VALUES(22, 9, 1, 0);
INSERT INTO role_description VALUES(23, 9, 1, 1);
INSERT INTO role_description VALUES(24, 9, 0, 0);

INSERT INTO role_description VALUES(25, 10, 1, 0);
INSERT INTO role_description VALUES(26, 10, 0, 0);

INSERT INTO role_description VALUES(27, 14, 1, 0);
INSERT INTO role_description VALUES(28, 14, 1, 1);
INSERT INTO role_description VALUES(29, 14, 0, 0);

INSERT INTO role_description VALUES(30, 15, 1, 0);
INSERT INTO role_description VALUES(31, 15, 1, 1);
INSERT INTO role_description VALUES(32, 15, 0, 0);

INSERT INTO role_description VALUES(33, 16, 1, 0);
INSERT INTO role_description VALUES(34, 16, 1, 1);
INSERT INTO role_description VALUES(35, 16, 0, 0);

INSERT INTO role_description VALUES(36, 17, 1, 0);
INSERT INTO role_description VALUES(37, 17, 1, 1);
INSERT INTO role_description VALUES(38, 17, 0, 0);

INSERT INTO role_description VALUES(39, 18, 1, 0);
INSERT INTO role_description VALUES(40, 18, 1, 1);
INSERT INTO role_description VALUES(41, 18, 0, 0);

INSERT INTO role_description VALUES(42, 19, 1, 0);
INSERT INTO role_description VALUES(43, 19, 1, 1);
INSERT INTO role_description VALUES(44, 19, 0, 0);

INSERT INTO role_description VALUES(45, 20, 1, 0);
INSERT INTO role_description VALUES(46, 20, 1, 1);
INSERT INTO role_description VALUES(47, 20, 0, 0);

INSERT INTO role_description VALUES(48, 21, 1, 0);
INSERT INTO role_description VALUES(49, 21, 1, 1);
INSERT INTO role_description VALUES(50, 21, 0, 0);

INSERT INTO role_description VALUES(51, 22, 1, 0);
INSERT INTO role_description VALUES(52, 22, 1, 1);
INSERT INTO role_description VALUES(53, 22, 0, 0);

INSERT INTO role_description VALUES(54, 23, 1, 0);
INSERT INTO role_description VALUES(55, 23, 1, 1);
INSERT INTO role_description VALUES(56, 23, 0, 0);

INSERT INTO role_description VALUES(57, 24, 1, 0);
INSERT INTO role_description VALUES(58, 24, 1, 1);
INSERT INTO role_description VALUES(59, 24, 0, 0);

INSERT INTO role_description VALUES(60, 25, 1, 0);
INSERT INTO role_description VALUES(61, 25, 1, 1);
INSERT INTO role_description VALUES(62, 25, 0, 0);

INSERT INTO role_description VALUES(63, 26, 1, 0);
INSERT INTO role_description VALUES(64, 26, 1, 1);
INSERT INTO role_description VALUES(65, 26, 0, 0);

INSERT INTO role_description VALUES(66, 27, 1, 0);
INSERT INTO role_description VALUES(67, 27, 1, 1);
INSERT INTO role_description VALUES(68, 27, 0, 0);

CREATE TABLE user_roles (
  user_id INT NOT NULL REFERENCES users(id),
  role_id INT NOT NULL REFERENCES roles(id),
  CONSTRAINT user_role_unique UNIQUE (user_id, role_id)
);

CREATE TABLE simulation_groups (
  id INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY(id)
);

-- HSQLDB: ALTER TABLE simulation_groups ALTER COLUMN id RESTART WITH 1;

CREATE TABLE run (
  id INT NOT NULL AUTO_INCREMENT,
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
  md5_hash_of_run_message CHAR(32) NOT NULL,
  md5_collision_id INT NOT NULL DEFAULT 1,
  software_id INT,
  requester_id INT NOT NULL,
  file_store_directory_hash CHAR(32) NOT NULL,
  simulation_group_id INT,
  last_service_to_be_called int,
  PRIMARY KEY (id),
  CONSTRAINT run_unique UNIQUE (md5_hash_of_run_message, md5_collision_id),
  CONSTRAINT fk_software_id FOREIGN KEY (software_id) REFERENCES software_identification(id),
  CONSTRAINT fk_requester_id FOREIGN KEY (requester_id) REFERENCES users (id),
  CONSTRAINT fk_simulation_group_id FOREIGN KEY (simulation_group_id) REFERENCES simulation_groups (id),
  CONSTRAINT fk_last_service_to_be_called FOREIGN KEY (last_service_to_be_called) REFERENCES software_identification (id)
);

-- HSQLDB: ALTER TABLE run ALTER COLUMN id RESTART WITH 1;

CREATE TABLE run_status_description (
  id INT NOT NULL AUTO_INCREMENT,
  status VARCHAR(32) NOT NULL,
  PRIMARY KEY (id)
);

-- HSQLDB: ALTER TABLE run_status_description ALTER COLUMN id RESTART WITH 1;

INSERT INTO run_status_description (status) VALUES ('exiting');
INSERT INTO run_status_description (status) VALUES ('held');
INSERT INTO run_status_description (status) VALUES ('queued');
INSERT INTO run_status_description (status) VALUES ('called_translator');
INSERT INTO run_status_description (status) VALUES ('called_visualizer');
INSERT INTO run_status_description (status) VALUES ('called_simulator');
INSERT INTO run_status_description (status) VALUES ('translating');
INSERT INTO run_status_description (status) VALUES ('translation_completed');
INSERT INTO run_status_description (status) VALUES ('initializing');
INSERT INTO run_status_description (status) VALUES ('log_files_written');
INSERT INTO run_status_description (status) VALUES ('loading_run_config_into_database');
INSERT INTO run_status_description (status) VALUES ('loaded_run_config_into_database');
INSERT INTO run_status_description (status) VALUES ('staging');
INSERT INTO run_status_description (status) VALUES ('running');
INSERT INTO run_status_description (status) VALUES ('moving');
INSERT INTO run_status_description (status) VALUES ('waiting');
INSERT INTO run_status_description (status) VALUES ('completed');
INSERT INTO run_status_description (status) VALUES ('failed');
INSERT INTO run_status_description (status) VALUES ('unauthorized');
INSERT INTO run_status_description (status) VALUES ('unknown_runid');
INSERT INTO run_status_description (status) VALUES ('run_terminated');
INSERT INTO run_status_description (status) VALUES ('authentication_failure');

CREATE TABLE run_status (
  id INT NOT NULL AUTO_INCREMENT,
  run_id INT NOT NULL,
  status_id INT NOT NULL,
  message TEXT,
  last_time_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT fk_run_id FOREIGN KEY (run_id) REFERENCES run (id),
  CONSTRAINT fk_status_id FOREIGN KEY (status_id) REFERENCES run_status_description(id),
  CONSTRAINT run_status UNIQUE(run_id)
);

-- HSQLDB: ALTER TABLE run_status ALTER COLUMN id RESTART WITH 1;

CREATE TABLE simulation_group_definition (
  simulation_group_id INT NOT NULL REFERENCES simulation_groups(id),
  run_id INT NOT NULL,
  CONSTRAINT fk_sim_group_def_run_id FOREIGN KEY (run_id) REFERENCES run (id),
  CONSTRAINT simulation_group_definition_unique UNIQUE (simulation_group_id, run_id)
);

CREATE TABLE simulated_population (
  id INT NOT NULL AUTO_INCREMENT,
  label VARCHAR(255),
  PRIMARY KEY (id)
);

-- HSQLDB: ALTER TABLE simulated_population ALTER COLUMN id RESTART WITH 1;

CREATE TABLE population_axis (
  id INT NOT NULL AUTO_INCREMENT,
  label VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

-- HSQLDB: ALTER TABLE population_axis ALTER COLUMN id RESTART WITH 1;

CREATE TABLE simulated_population_axis_value (
  population_id INT NOT NULL REFERENCES simulated_population (id),
  axis_id INT NOT NULL REFERENCES population_axis (id),
  value VARCHAR(255) NOT NULL
);

CREATE TABLE time_series (
  run_id INT NOT NULL REFERENCES run (id),
  population_id INT NOT NULL REFERENCES simulated_population (id),
  time_step INT NOT NULL,
  pop_count FLOAT  NOT NULL
);

INSERT INTO population_axis (label) values ('disease_state');
INSERT INTO population_axis (label) values ('location');
	
-- HSQLDB: CREATE TRIGGER run_creation_timestamp BEFORE INSERT ON run REFERENCING NEW AS newrow FOR EACH ROW SET newrow.created = NOW();
	
-- HSQLDB: END;
	
CREATE TRIGGER run_creation_timestamp BEFORE INSERT ON run 
FOR EACH ROW
SET NEW.created = NOW();

CREATE TRIGGER run_status_update_timestamp BEFORE UPDATE ON run_status 
FOR EACH ROW
SET NEW.last_time_updated = NOW();


	