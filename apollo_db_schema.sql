/*  Comments starting with "-- HSQLDB: " are commands that are interpreted when
this script is used for the ApolloDbUtils unit tests (which use HSQLDB).

All commands listed after "-- HSQLDB: END;" will NOT be run when this script is
used in the unit tests. These commands are MySQL-specific and will not work with HSQLDB.
 */

DROP VIEW IF EXISTS run_data_description_view;
DROP VIEW IF EXISTS translator_output_content_view;
DROP VIEW IF EXISTS software_input;

DROP TABLE IF EXISTS run_status;
DROP TABLE IF EXISTS run_status_description;
DROP TABLE IF EXISTS simulation_group_definition;
DROP TABLE IF EXISTS run_data;

DROP TABLE IF EXISTS role_description;

DROP TABLE IF EXISTS run_data_content;
DROP TABLE IF EXISTS run_data_description;
DROP TABLE IF EXISTS run_data_description_axis_value;
DROP TABLE IF EXISTS run_data_description_axis;
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

INSERT INTO `software_identification` VALUES (1,'UPitt','Translator','1.0','translator','http://localhost:8080/translator-service-war-3.1.0-SNAPSHOT/services/translatorservice?wsdl',1, NULL, NULL, NULL, NULL),
											 (2,'UPitt','SEIR','3.0','simulator','http://localhost:8080/pitt-simulator-service-war-3.1.0-SNAPSHOT/services/pittsimulatorservice?wsdl',1, NULL, NULL, NULL, NULL),
											 (3,'UPitt,PSC,CMU','FRED','2.0.1_i','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
											 (4,'UPitt','Time Series Visualizer','1.0','visualizer','http://localhost:8080/visualizer-service-war-3.1.0-SNAPSHOT/services/visualizerservice?wsdl',1, NULL, NULL, NULL ,NULL),
											 (5,'PSC','GAIA','1.0','visualizer','http://gaia.pha.psc.edu:13501/gaia?wsdl',1, NULL, NULL, NULL, NULL),
											 (6,'Chao-FredHutchinsonCancerCenter','FluTE','1.15','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
											 (7,'UPitt','Anthrax','1.0','simulator','http://localhost:8080/pitt-simulator-service-war-3.1.0-SNAPSHOT/services/pittsimulatorservice?wsdl',1, NULL, NULL, NULL, NULL),
                                             (8,'PSC','CLARA','0.5','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                                             (9,'Steve Bellan','Lancet Ebola','1.0','simulator','http://localhost:8080/pitt-simulator-service-war-3.1.0-SNAPSHOT/services/pittsimulatorservice?wsdl',1, 'Creative Commons Attribution 4.0 International License', '4.0', 'http://creativecommons.org/licenses/by/4.0/', 'The Lancet Ebola simulator was used to produce results for the following publication:\n\nBellan SE, JRC Pulliam, J Dushoff, and LA Meyers. Asymptomatic infection, natural immunity, and Ebola dynamics. Letter, submitted to Lancet.\n\nThe original source code for the simulator is available at https://github.com/ICI3D/Ebola. The code was changed for use with the Apollo Web Services to do the following:\n\n - Read initial compartment sizes, transmission and disease parameters from an external input file\n - Produce time series output files for each compartment\n - Compute a newly exposed time series from the cumulative exposed time series'),
											 (10,'UPitt','Data Service','1.0','data','http://localhost:8080/data-service-war-3.1.0-SNAPSHOT/services/dataservice?wsdl',1, NULL, NULL, NULL, NULL),
											 (11,'UPitt', 'Broker Service','3.1.0','broker','http://localhost:8080/broker-service-war-3.1.0-SNAPSHOT/services/apolloservice?wsdl',1, NULL, NULL, NULL ,NULL),
                       (12,'any', 'any','any','endUserApplication','not_applicable',1, NULL, NULL, NULL ,NULL),
                       (13,'UPitt', 'Run Manager','3.1.0','runManager','http://localhost:8080/run-manager-service-rest-frontend-3.1.0-SNAPSHOT/',1, NULL, NULL, NULL ,NULL),
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

CREATE TABLE run_data_content
 (
  id INT NOT NULL AUTO_INCREMENT,
  binary_content LONGBLOB,
  text_content LONGTEXT,
  md5_hash_of_content CHAR(32) NOT NULL,
  md5_collision_id INT NOT NULL DEFAULT 1,
  PRIMARY KEY(id),
  CONSTRAINT run_data_content_unique UNIQUE (md5_hash_of_content, md5_collision_id)
);

-- HSQLDB: ALTER TABLE run_data_content ALTER COLUMN id RESTART WITH 1;

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
  software_id INT NOT NULL,
  requester_id INT NOT NULL,
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

CREATE TABLE run_data_description (
  id INT NOT NULL AUTO_INCREMENT,
  label VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

-- HSQLDB: ALTER TABLE run_data_description ALTER COLUMN id RESTART WITH 1;

CREATE TABLE run_data_description_axis (
  id INT NOT NULL AUTO_INCREMENT,
  label VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

-- HSQLDB: ALTER TABLE run_data_description_axis ALTER COLUMN id RESTART WITH 1;

CREATE TABLE run_data (
  id INT NOT NULL AUTO_INCREMENT,
  run_id INT NOT NULL,
  description_id INT NOT NULL REFERENCES run_data_description(id),
  content_id INT NOT NULL REFERENCES run_data_content(id),
  PRIMARY KEY (id),
  CONSTRAINT fk_run_data_run_id FOREIGN KEY (run_id) REFERENCES run (id),
  CONSTRAINT run_data_unique UNIQUE (run_id, description_id, content_id)
);

-- HSQLDB: ALTER TABLE run_data ALTER COLUMN id RESTART WITH 1;

INSERT INTO run_data_description_axis (label) values ('format');
INSERT INTO run_data_description_axis (label) values ('label');
INSERT INTO run_data_description_axis (label) values ('type');
INSERT INTO run_data_description_axis (label) values ('source_software_id');
INSERT INTO run_data_description_axis (label) values ('destination_software_id');


CREATE TABLE run_data_description_axis_value (
  run_data_description_id INT NOT NULL REFERENCES run_data_description (id),
  run_data_description_axis_id INT NOT NULL REFERENCES run_data_description_axis (id),
  value VARCHAR(255) NOT NULL
);

INSERT INTO run_data_description (label) values ('FRED text configuration file, config.txt'); /* 1 */
INSERT INTO run_data_description (label) values ('FRED text configuration file, fred_initial_population_0.txt'); /* 2 */
INSERT INTO run_data_description (label) values ('FRED text configuration file, fred-vaccination-schedule_0.txt'); /* 3 */
INSERT INTO run_data_description (label) values ('FluTE text configuration file, config.txt'); /* 4 */
INSERT INTO run_data_description (label) values ('TimeSeriesVisualizer output url, incidence.png'); /* 5 */
INSERT INTO run_data_description (label) values ('TimeSeriesVisualizer output url, prevalence.png'); /* 6 */
INSERT INTO run_data_description (label) values ('GAIA input file, gaia.zip'); /* 7 */
INSERT INTO run_data_description (label) values ('Verbose HTML file for FRED, verbose.html'); /* 8 */
INSERT INTO run_data_description (label) values ('SEIR Simulator log file for newly-exposed, newly_exposed.txt '); /* 9*/
INSERT INTO run_data_description (label) values ('SEIR Simulator log file for susceptible, susceptible.txt '); /* 10*/
INSERT INTO run_data_description (label) values ('SEIR Simulator log file for exposed, exposed.txt '); /* 11*/
INSERT INTO run_data_description (label) values ('SEIR Simulator log file for infectious, infectious.txt '); /* 12*/
INSERT INTO run_data_description (label) values ('SEIR Simulator log file for recovered, recovered.txt '); /* 13*/
INSERT INTO run_data_description (label) values ('SEIR Simulator log file for antivirals given, av_given.txt '); /* 14*/
INSERT INTO run_data_description (label) values ('SEIR Simulator log file for vaccines given, vacc_gave_to.txt '); /* 15*/
INSERT INTO run_data_description (label) values ('Verbose HTML for SEIR, verbose.html '); /* 16*/
INSERT INTO run_data_description (label) values ('SEIR text configuration file, config.txt '); /* 17*/
INSERT INTO run_data_description (label) values ('FluTE verbose configuration file, verbose.html'); /* 18 */
INSERT INTO run_data_description (label) values ('SEIR verbose configuration file, verbose.html'); /* 19 */
INSERT INTO run_data_description (label) values ('TSV FluTE Simulator log file for newly-exposed, newly_exposed.txt '); /* 20 */
INSERT INTO run_data_description (label) values ('TSV FluTE Simulator log file for susceptible, susceptible.txt '); /* 21 */
INSERT INTO run_data_description (label) values ('TSV FluTE Simulator log file for exposed, exposed.txt '); /* 22 */
INSERT INTO run_data_description (label) values ('TSV FluTE Simulator log file for infectious, infectious.txt '); /* 23 */
INSERT INTO run_data_description (label) values ('TSV FluTE Simulator log file for recovered, recovered.txt '); /* 24*/
INSERT INTO run_data_description (label) values ('TSV FluTE Simulator log file for antivirals given, av_given.txt '); /* 25*/
INSERT INTO run_data_description (label) values ('TSV FluTE Simulator log file for vaccines given, vacc_gave_to.txt '); /* 26*/
INSERT INTO run_data_description (label) values ('TSV FRED Simulator log file for newly-exposed, newly_exposed.txt '); /* 27*/
INSERT INTO run_data_description (label) values ('TSV FRED Simulator log file for susceptible, susceptible.txt '); /* 28*/
INSERT INTO run_data_description (label) values ('TSV FRED Simulator log file for exposed, exposed.txt '); /* 29 */
INSERT INTO run_data_description (label) values ('TSV FRED Simulator log file for infectious, infectious.txt '); /* 30 */
INSERT INTO run_data_description (label) values ('TSV FRED Simulator log file for recovered, recovered.txt '); /* 31 */
INSERT INTO run_data_description (label) values ('TSV FRED Simulator log file for antivirals given, av_given.txt '); /* 32 */
INSERT INTO run_data_description (label) values ('TSV FRED Simulator log file for vaccines given, vacc_gave_to.txt '); /* 33*/
INSERT INTO run_data_description (label) values ('GAIA FluTE Simulator log file for newly-exposed, newly_exposed.txt '); /* 34 */
INSERT INTO run_data_description (label) values ('GAIA FRED Simulator log file for newly-exposed, newly_exposed.txt '); /* 35*/
INSERT INTO run_data_description (label) values ('GAIA movie, movie.ogg '); /* 36*/
INSERT INTO run_data_description (label) values ('RunMessage JSON, run_message.json '); /* 37*/
INSERT INTO run_data_description (label) values ('Anthrax text configuration file, config.txt '); /* 38*/
INSERT INTO run_data_description (label) values ('Anthrax verbose configuration file, verbose.html'); /* 39 */
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for susceptible, susceptible.txt '); /* 40*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for exposed, exposed.txt '); /* 41*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for recovered, recovered.txt '); /* 42*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for newly_deceased, newly_deceased.txt '); /* 43*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for prophylactics_given, prophylactics_given.txt ');/* 44*/
INSERT INTO run_data_description (label) values ('TimeSeriesVisualizer output url, treatment.png'); /* 45 */
INSERT INTO run_data_description (label) values ('TimeSeriesVisualizer output url, newly_deceased.png'); /* 46 */
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for asymptomatic, asymptomatic.txt '); /* 47*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for symptomatic, symptomatic.txt '); /* 48*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for fulminant, fulminant.txt '); /* 49*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for dead, dead.txt '); /* 50*/
INSERT INTO run_data_description (label) values ('TimeSeriesVisualizer output url, disease_states.png'); /* 51 */
INSERT INTO run_data_description (label) values ('CLARA text configuration file, config.txt '); /* 52*/
INSERT INTO run_data_description (label) values ('CLARA verbose configuration file, verbose.html'); /* 53 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for susceptible, susceptible.txt '); /* 54 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for exposed, exposed.txt '); /* 55 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for infectious, infectious.txt '); /* 56 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for temp_immuned, temp_immuned.txt '); /* 57 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for recovered, recovered.txt '); /* 58 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for immuned, immuned.txt '); /* 59 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for newly_exposed, newly_exposed.txt '); /* 60 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for mosquito_susceptible, mosquito_susceptible.txt '); /* 61 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for mosquito_exposed, mosquito_exposed.txt '); /* 62 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for mosquito_infectious, mosquito_infectious.txt '); /* 63 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for mosquito_newly_exposed, mosquito_newly_exposed.txt '); /* 64 */
INSERT INTO run_data_description (label) values ('TimeSeriesVisualizer output url, prevalence_mosquito.png'); /* 65 */
INSERT INTO run_data_description (label) values ('TimeSeriesVisualizer output url, incidence_mosquito.png'); /* 66 */
INSERT INTO run_data_description (label) values ('Lancet Ebola text configuration file, config.txt '); /* 67*/
INSERT INTO run_data_description (label) values ('Lancet Ebola verbose configuration file, verbose.html'); /* 68 */
INSERT INTO run_data_description (label) values ('TSV Lancet Ebola Simulator log file for susceptible, susceptible.txt '); /* 69 */
INSERT INTO run_data_description (label) values ('TSV Lancet Ebola Simulator log file for exposed, exposed.txt '); /* 70 */
INSERT INTO run_data_description (label) values ('TSV Lancet Ebola Simulator log file for infectious, infectious.txt '); /* 71 */
INSERT INTO run_data_description (label) values ('TSV Lancet Ebola Simulator log file for recovered, recovered.txt '); /* 72 */
INSERT INTO run_data_description (label) values ('TSV Lancet Ebola Simulator log file for newly_exposed, newly_exposed.txt '); /* 73 */
INSERT INTO run_data_description (label) values ('Batch inputs file with run IDs, batch_inputs_with_run_ids.txt '); /* 74*/
INSERT INTO run_data_description (label) values ('FRED ODS required output file for model validation, ods_required_output_for_model_validation.csv'); /* 75*/
INSERT INTO run_data_description (label) values ('FluTE ODS required output file for model validation, ods_required_output_for_model_validation.csv'); /* 76*/
INSERT INTO run_data_description (label) values ('SEIR ODS required output file for model validation, ods_required_output_for_model_validation.csv'); /* 77*/
INSERT INTO run_data_description (label) values ('RunSimulationsMessage JSON, run_simulations_message.json '); /* 78*/
INSERT INTO run_data_description (label) values ('Open Malaria R0063 configuration file, config.xml '); /* 79*/
INSERT INTO run_data_description (label) values ('Data Service Request Message, data_retrieval_request_message.json '); /* 80 */
INSERT INTO run_data_description (label) values ('Open Malaria R0063 Output File, om_native_output_json.json '); /* 81 */
INSERT INTO run_data_description (label) values ('Open Malaria R0065 configuration file, config.xml '); /* 82*/
INSERT INTO run_data_description (label) values ('Open Malaria R0068 configuration file, config.xml '); /* 83*/
INSERT INTO run_data_description (label) values ('Open Malaria R0111 configuration file, config.xml '); /* 84*/
INSERT INTO run_data_description (label) values ('Open Malaria R0115 configuration file, config.xml '); /* 85*/
INSERT INTO run_data_description (label) values ('Open Malaria R0121 configuration file, config.xml '); /* 86*/
INSERT INTO run_data_description (label) values ('Open Malaria R0125 configuration file, config.xml '); /* 87*/
INSERT INTO run_data_description (label) values ('Open Malaria R0131 configuration file, config.xml '); /* 88*/
INSERT INTO run_data_description (label) values ('Open Malaria R0132 configuration file, config.xml '); /* 89*/
INSERT INTO run_data_description (label) values ('Open Malaria R0133 configuration file, config.xml '); /* 90*/
INSERT INTO run_data_description (label) values ('Open Malaria R0670 configuration file, config.xml '); /* 91*/
INSERT INTO run_data_description (label) values ('Open Malaria R0674 configuration file, config.xml '); /* 92*/
INSERT INTO run_data_description (label) values ('Open Malaria R0678 configuration file, config.xml '); /* 93*/
INSERT INTO run_data_description (label) values ('Open Malaria R0065 Output File, om_native_output_json.json '); /* 94 */
INSERT INTO run_data_description (label) values ('Open Malaria R0068 Output File, om_native_output_json.json '); /* 95 */
INSERT INTO run_data_description (label) values ('Open Malaria R0111 Output File, om_native_output_json.json '); /* 96 */
INSERT INTO run_data_description (label) values ('Open Malaria R0115 Output File, om_native_output_json.json '); /* 97 */
INSERT INTO run_data_description (label) values ('Open Malaria R0121 Output File, om_native_output_json.json '); /* 98 */
INSERT INTO run_data_description (label) values ('Open Malaria R0125 Output File, om_native_output_json.json '); /* 99 */
INSERT INTO run_data_description (label) values ('Open Malaria R0131 Output File, om_native_output_json.json '); /* 100 */
INSERT INTO run_data_description (label) values ('Open Malaria R0132 Output File, om_native_output_json.json '); /* 101 */
INSERT INTO run_data_description (label) values ('Open Malaria R0133 Output File, om_native_output_json.json '); /* 102 */
INSERT INTO run_data_description (label) values ('Open Malaria R0670 Output File, om_native_output_json.json '); /* 103 */
INSERT INTO run_data_description (label) values ('Open Malaria R0674 Output File, om_native_output_json.json '); /* 104 */
INSERT INTO run_data_description (label) values ('Open Malaria R0678 Output File, om_native_output_json.json '); /* 105 */
INSERT INTO run_data_description (label) values ('Open Malaria base configuration file, config.xml '); /* 106*/
INSERT INTO run_data_description (label) values ('Open Malaria base Output File, om_native_output_json.json '); /* 107 */







INSERT INTO run_data_description_axis_value (run_data_description_id, run_data_description_axis_id, value) values
	(1, 1, 'TEXT'), 
	(1, 2, 'config.txt'),
	(1, 3, 'CONFIGURATION_FILE'),
	(1, 4, '1'),
	(1, 5, '3'),

	(2, 1, 'TEXT'),
	(2, 2, 'fred_initial_population_0.txt'),
	(2, 3, 'CONFIGURATION_FILE'),
	(2, 4, '1'),
	(2, 5, '3'),
	
	(3, 1, 'TEXT'),
	(3, 2, 'fred-vaccination-schedule_0.txt'),
	(3, 3, 'CONFIGURATION_FILE'),
	(3, 4, '1'),
	(3, 5, '3'),
	
	(4, 1, 'TEXT'), 
	(4, 2, 'config.txt'),
	(4, 3, 'CONFIGURATION_FILE'),
	(4, 4, '1'),
	(4, 5, '6'),
	
	(5, 1, 'URL'), 
	(5, 2, 'incidence.png'),
	(5, 3, 'IMAGE'),
	(5, 4, '4'),
	(5, 5, '12'),
	
	(6, 1, 'URL'), 
	(6, 2, 'prevalence.png'),
	(6, 3, 'IMAGE'),
	(6, 4, '4'),
	(6, 5, '12'),
	
	(7, 1, 'zip'), 
	(7, 2, 'gaia.zip'),
	(7, 3, 'CONFIGURATION_FILE'),
	(7, 4, '3'),
	(7, 5, '5'),
	
	(8, 1, 'TEXT'), 
	(8, 2, 'verbose.html'),
	(8, 3, 'CONFIGURATION_FILE'),
	(8, 4, '1'),
	(8, 5, '3'),
	
	
	(9, 1, 'TEXT'), 
	(9, 2, 'newly_exposed.txt'),
	(9, 3, 'SIMULATOR_LOG_FILE'),
	(9, 4, '2'),
	(9, 5, '4'),
	
	(10, 1, 'TEXT'), 
	(10, 2, 'susceptible.txt'),
	(10, 3, 'SIMULATOR_LOG_FILE'),
	(10, 4, '2'),
	(10, 5, '4'),
	
	(11, 1, 'TEXT'), 
	(11, 2, 'exposed.txt'),
	(11, 3, 'SIMULATOR_LOG_FILE'),
	(11, 4, '2'),
	(11, 5, '4'),
	
	(12, 1, 'TEXT'), 
	(12, 2, 'infectious.txt'),
	(12, 3, 'SIMULATOR_LOG_FILE'),
	(12, 4, '2'),
	(12, 5, '4'),
	
	(13, 1, 'TEXT'), 
	(13, 2, 'recovered.txt'),
	(13, 3, 'SIMULATOR_LOG_FILE'),
	(13, 4, '2'),
	(13, 5, '4'),
	
	(14, 1, 'TEXT'), 
	(14, 2, 'av_administered.txt'),
	(14, 3, 'SIMULATOR_LOG_FILE'),
	(14, 4, '2'),
	(14, 5, '4'),
	
	(15, 1, 'TEXT'), 
	(15, 2, 'vacc_administered.txt'),
	(15, 3, 'SIMULATOR_LOG_FILE'),
	(15, 4, '2'),
	(15, 5, '4'),
	
	(16, 1, 'TEXT'), 
	(16, 2, 'verbose.html'),
	(16, 3, 'CONFIGURATION_FILE'),
	(16, 4, '1'),
	(16, 5, '2'),
	
	(17, 1, 'TEXT'), 
	(17, 2, 'config.txt'),
	(17, 3, 'CONFIGURATION_FILE'),
	(17, 4, '1'),
	(17, 5, '2'),
	
	(18, 1, 'TEXT'), 
	(18, 2, 'verbose.html'),
	(18, 3, 'CONFIGURATION_FILE'),
	(18, 4, '1'),
	(18, 5, '6'),
	
	(19, 1, 'TEXT'), 
	(19, 2, 'verbose.html'),
	(19, 3, 'CONFIGURATION_FILE'),
	(19, 4, '2'),
	(19, 5, '6'),
	
	(20, 1, 'TEXT'), 
	(20, 2, 'newly_exposed.txt'),
	(20, 3, 'SIMULATOR_LOG_FILE'),
	(20, 4, '6'),
	(20, 5, '4'),
	
	(21, 1, 'TEXT'), 
	(21, 2, 'susceptible.txt'),
	(21, 3, 'SIMULATOR_LOG_FILE'),
	(21, 4, '6'),
	(21, 5, '4'),
	
	(22, 1, 'TEXT'), 
	(22, 2, 'exposed.txt'),
	(22, 3, 'SIMULATOR_LOG_FILE'),
	(22, 4, '6'),
	(22, 5, '4'),
	
	(23, 1, 'TEXT'), 
	(23, 2, 'infectious.txt'),
	(23, 3, 'SIMULATOR_LOG_FILE'),
	(23, 4, '6'),
	(23, 5, '4'),
	
	(24, 1, 'TEXT'), 
	(24, 2, 'recovered.txt'),
	(24, 3, 'SIMULATOR_LOG_FILE'),
	(24, 4, '6'),
	(24, 5, '4'),
	
	(25, 1, 'TEXT'), 
	(25, 2, 'av_administered.txt'),
	(25, 3, 'SIMULATOR_LOG_FILE'),
	(25, 4, '6'),
	(25, 5, '4'),
	
	(26, 1, 'TEXT'), 
	(26, 2, 'vacc_administered.txt'),
	(26, 3, 'SIMULATOR_LOG_FILE'),
	(26, 4, '6'),
	(26, 5, '4'),
	
	(27, 1, 'TEXT'), 
	(27, 2, 'newly_exposed.txt'),
	(27, 3, 'SIMULATOR_LOG_FILE'),
	(27, 4, '3'),
	(27, 5, '4'),
	
	(28, 1, 'TEXT'), 
	(28, 2, 'susceptible.txt'),
	(28, 3, 'SIMULATOR_LOG_FILE'),
	(28, 4, '3'),
	(28, 5, '4'),
	
	(29, 1, 'TEXT'), 
	(29, 2, 'exposed.txt'),
	(29, 3, 'SIMULATOR_LOG_FILE'),
	(29, 4, '3'),
	(29, 5, '4'),
	
	(30, 1, 'TEXT'), 
	(30, 2, 'infectious.txt'),
	(30, 3, 'SIMULATOR_LOG_FILE'),
	(30, 4, '3'),
	(30, 5, '4'),
	
	(31, 1, 'TEXT'), 
	(31, 2, 'recovered.txt'),
	(31, 3, 'SIMULATOR_LOG_FILE'),
	(31, 4, '3'),
	(31, 5, '4'),
	
	(32, 1, 'TEXT'), 
	(32, 2, 'av_administered.txt'),
	(32, 3, 'SIMULATOR_LOG_FILE'),
	(32, 4, '3'),
	(32, 5, '4'),
	
	(33, 1, 'TEXT'), 
	(33, 2, 'vacc_administered.txt'),
	(33, 3, 'SIMULATOR_LOG_FILE'),
	(33, 4, '3'),
	(33, 5, '4'),
	
	(34, 1, 'TEXT'), 
	(34, 2, 'newly_exposed.txt'),
	(34, 3, 'SIMULATOR_LOG_FILE'),
	(34, 4, '6'),
	(34, 5, '5'),
	
	(35, 1, 'TEXT'), 
	(35, 2, 'newly_exposed.txt'),
	(35, 3, 'SIMULATOR_LOG_FILE'),
	(35, 4, '3'),
	(35, 5, '5'),
	
	(36, 1, 'URL'), 
	(36, 2, 'movie.ogg'),
	(36, 3, 'MOVIE'),
	(36, 4, '5'),
	(36, 5, '12'),
	
	(37, 1, 'TEXT'), 
	(37, 2, 'run_message.json'),
	(37, 3, 'RUN_MESSAGE'),
	(37, 4, '12'),
	(37, 5, '1'),
	
	/*(38, 1, 'TEXT'), 
	(38, 2, 'run_visualization_message.json'),
	(38, 3, 'RUN_VISUALIZATION_MESSAGE'),
	(38, 4, '12'),
	(38, 5, '4'),
	
	(39, 1, 'TEXT'), 
	(39, 2, 'run_visualization_message.json'),
	(39, 3, 'RUN_VISUALIZATION_MESSAGE'),
	(39, 4, '12'),
	(39, 5, '5'),*/
	
	(38, 1, 'TEXT'), 
	(38, 2, 'config.txt'),
	(38, 3, 'CONFIGURATION_FILE'),
	(38, 4, '1'),
	(38, 5, '7'),
	
	(39, 1, 'TEXT'), 
	(39, 2, 'verbose.html'),
	(39, 3, 'CONFIGURATION_FILE'),
	(39, 4, '1'),
	(39, 5, '7'),

	(40, 1, 'TEXT'), 
	(40, 2, 'susceptible.txt'),
	(40, 3, 'SIMULATOR_LOG_FILE'),
	(40, 4, '7'),
	(40, 5, '4'),
	
	(41, 1, 'TEXT'), 
	(41, 2, 'exposed.txt'),
	(41, 3, 'SIMULATOR_LOG_FILE'),
	(41, 4, '7'),
	(41, 5, '4'),
	
	(42, 1, 'TEXT'), 
	(42, 2, 'recovered.txt'),
	(42, 3, 'SIMULATOR_LOG_FILE'),
	(42, 4, '7'),
	(42, 5, '4'),
    
	(43, 1, 'TEXT'), 
	(43, 2, 'newly_deceased.txt'),
	(43, 3, 'SIMULATOR_LOG_FILE'),
	(43, 4, '7'),
	(43, 5, '4'),
    
	(44, 1, 'TEXT'), 
	(44, 2, 'prophylactics_given.txt'),
	(44, 3, 'SIMULATOR_LOG_FILE'),
	(44, 4, '7'),
	(44, 5, '4'),
    
	(45, 1, 'URL'), 
	(45, 2, 'treatment.png'),
	(45, 3, 'IMAGE'),
	(45, 4, '4'),
	(45, 5, '12'),
    
	(46, 1, 'URL'), 
	(46, 2, 'newly_deceased.png'),
	(46, 3, 'IMAGE'),
	(46, 4, '4'),
	(46, 5, '12'),
    
   	(47, 1, 'TEXT'), 
	(47, 2, 'asymptomatic.txt'),
	(47, 3, 'SIMULATOR_LOG_FILE'),
	(47, 4, '7'),
	(47, 5, '4'),
    
   	(48, 1, 'TEXT'), 
	(48, 2, 'symptomatic.txt'),
	(48, 3, 'SIMULATOR_LOG_FILE'),
	(48, 4, '7'),
	(48, 5, '4'),
    
   	(49, 1, 'TEXT'), 
	(49, 2, 'fulminant.txt'),
	(49, 3, 'SIMULATOR_LOG_FILE'),
	(49, 4, '7'),
	(49, 5, '4'),  
    
   	(50, 1, 'TEXT'), 
	(50, 2, 'dead.txt'),
	(50, 3, 'SIMULATOR_LOG_FILE'),
	(50, 4, '7'),
	(50, 5, '4'),
    
	(51, 1, 'URL'), 
	(51, 2, 'disease_states.png'),
	(51, 3, 'IMAGE'),
	(51, 4, '4'),
	(51, 5, '12'),
    
    (52, 1, 'TEXT'), 
	(52, 2, 'config.txt'),
	(52, 3, 'CONFIGURATION_FILE'),
	(52, 4, '1'),
	(52, 5, '8'),
    
    (53, 1, 'TEXT'), 
	(53, 2, 'verbose.html'),
	(53, 3, 'CONFIGURATION_FILE'),
	(53, 4, '1'),
	(53, 5, '8'),
    
   	(54, 1, 'TEXT'), 
	(54, 2, 'susceptible.txt'),
	(54, 3, 'SIMULATOR_LOG_FILE'),
	(54, 4, '8'),
	(54, 5, '4'),
    
   	(55, 1, 'TEXT'), 
	(55, 2, 'exposed.txt'),
	(55, 3, 'SIMULATOR_LOG_FILE'),
	(55, 4, '8'),
	(55, 5, '4'),
	
   	(56, 1, 'TEXT'), 
	(56, 2, 'infectious.txt'),
	(56, 3, 'SIMULATOR_LOG_FILE'),
	(56, 4, '8'),
	(56, 5, '4'),
    
   	(57, 1, 'TEXT'), 
	(57, 2, 'temp_immuned.txt'),
	(57, 3, 'SIMULATOR_LOG_FILE'),
	(57, 4, '8'),
	(57, 5, '4'),
    
   	(58, 1, 'TEXT'), 
	(58, 2, 'recovered.txt'),
	(58, 3, 'SIMULATOR_LOG_FILE'),
	(58, 4, '8'),
	(58, 5, '4'),
    
   	(59, 1, 'TEXT'), 
	(59, 2, 'immuned.txt'),
	(59, 3, 'SIMULATOR_LOG_FILE'),
	(59, 4, '8'),
	(59, 5, '4'),
    
   	(60, 1, 'TEXT'), 
	(60, 2, 'newly_exposed.txt'),
	(60, 3, 'SIMULATOR_LOG_FILE'),
	(60, 4, '8'),
	(60, 5, '4'),
    
   	(61, 1, 'TEXT'), 
	(61, 2, 'mosquito_susceptible.txt'),
	(61, 3, 'SIMULATOR_LOG_FILE'),
	(61, 4, '8'),
	(61, 5, '4'),
    
   	(62, 1, 'TEXT'), 
	(62, 2, 'mosquito_exposed.txt'),
	(62, 3, 'SIMULATOR_LOG_FILE'),
	(62, 4, '8'),
	(62, 5, '4'),
    
   	(63, 1, 'TEXT'), 
	(63, 2, 'mosquito_infectious.txt'),
	(63, 3, 'SIMULATOR_LOG_FILE'),
	(63, 4, '8'),
	(63, 5, '4'),
    
   	(64, 1, 'TEXT'), 
	(64, 2, 'mosquito_newly_exposed.txt'),
	(64, 3, 'SIMULATOR_LOG_FILE'),
	(64, 4, '8'),
	(64, 5, '4'),
    
   	(65, 1, 'URL'), 
	(65, 2, 'prevalence_mosquito.png'),
	(65, 3, 'IMAGE'),
	(65, 4, '4'),
	(65, 5, '12'),
    
   	(66, 1, 'URL'), 
	(66, 2, 'incidence_mosquito.png'),
	(66, 3, 'IMAGE'),
	(66, 4, '4'),
	(66, 5, '12'),
    
    (67, 1, 'TEXT'), 
	(67, 2, 'config.txt'),
	(67, 3, 'CONFIGURATION_FILE'),
	(67, 4, '1'),
	(67, 5, '9'),
    
    (68, 1, 'TEXT'), 
	(68, 2, 'verbose.html'),
	(68, 3, 'CONFIGURATION_FILE'),
	(68, 4, '1'),
	(68, 5, '9'),
    
   	(69, 1, 'TEXT'), 
	(69, 2, 'susceptible.txt'),
	(69, 3, 'SIMULATOR_LOG_FILE'),
	(69, 4, '9'),
	(69, 5, '4'),
    
   	(70, 1, 'TEXT'), 
	(70, 2, 'exposed.txt'),
	(70, 3, 'SIMULATOR_LOG_FILE'),
	(70, 4, '9'),
	(70, 5, '4'),
    
   	(71, 1, 'TEXT'), 
	(71, 2, 'infectious.txt'),
	(71, 3, 'SIMULATOR_LOG_FILE'),
	(71, 4, '9'),
	(71, 5, '4'),
    
    (72, 1, 'TEXT'), 
	(72, 2, 'recovered.txt'),
	(72, 3, 'SIMULATOR_LOG_FILE'),
	(72, 4, '9'),
	(72, 5, '4'),
    
    (73, 1, 'TEXT'), 
	(73, 2, 'newly_exposed.txt'),
	(73, 3, 'SIMULATOR_LOG_FILE'),
	(73, 4, '9'),
	(73, 5, '4'),
    
	/*(76, 1, 'TEXT'), 
	(76, 2, 'data_retrieval_request_message.json'),
	(76, 3, 'DATA_RETRIEVAL_REQUEST_MESSAGE'),
	(76, 4, '12'),
	(76, 5, '10'),*/
    
  (74, 1, 'TEXT'), 
	(74, 2, 'batch_inputs_with_run_ids.txt'),
	(74, 3, 'CONFIGURATION_FILE'),
	(74, 4, '11'),
	(74, 5, '12'),
    
  (75, 1, 'TEXT'), 
	(75, 2, 'ods_required_output_for_model_validation.csv'),
	(75, 3, 'SIMULATOR_LOG_FILE'),
	(75, 4, '3'),
	(75, 5, '12'),
    
  (76, 1, 'TEXT'), 
	(76, 2, 'ods_required_output_for_model_validation.csv'),
	(76, 3, 'SIMULATOR_LOG_FILE'),
	(76, 4, '6'),
	(76, 5, '12'),
    
  (77, 1, 'TEXT'), 
	(77, 2, 'ods_required_output_for_model_validation.csv'),
	(77, 3, 'SIMULATOR_LOG_FILE'),
	(77, 4, '2'),
	(77, 5, '12'),

  (78, 1, 'TEXT'), 
  (78, 2, 'run_simulations_message.json'),
  (78, 3, 'RUN_SIMULATIONS_MESSAGE'),
  (78, 4, '12'),
  (78, 5, '1'),

  (79, 1, 'TEXT'), 
  (79, 2, 'config.xml'),
  (79, 3, 'CONFIGURATION_FILE'),
  (79, 4, '1'),
  (79, 5, '14'),

  (80, 1, 'TEXT'), 
  (80, 2, 'data_retrieval_request_message.json'),
  (80, 3, 'RUN_MESSAGE'),
  (80, 4, '12'),
  (80, 5, '10'),

  (81, 1, 'TEXT'), 
  (81, 2, 'om_native_output.json'),
  (81, 3, 'TEXT'),
  (81, 4, '12'),
  (81, 5, '14'),

  (82, 1, 'TEXT'), 
  (82, 2, 'config.xml'),
  (82, 3, 'CONFIGURATION_FILE'),
  (82, 4, '1'),
  (82, 5, '15'),

  (83, 1, 'TEXT'), 
  (83, 2, 'config.xml'),
  (83, 3, 'CONFIGURATION_FILE'),
  (83, 4, '1'),
  (83, 5, '16'),

  (84, 1, 'TEXT'), 
  (84, 2, 'config.xml'),
  (84, 3, 'CONFIGURATION_FILE'),
  (84, 4, '1'),
  (84, 5, '17'),

  (85, 1, 'TEXT'), 
  (85, 2, 'config.xml'),
  (85, 3, 'CONFIGURATION_FILE'),
  (85, 4, '1'),
  (85, 5, '18'),

  (86, 1, 'TEXT'), 
  (86, 2, 'config.xml'),
  (86, 3, 'CONFIGURATION_FILE'),
  (86, 4, '1'),
  (86, 5, '19'),

  (87, 1, 'TEXT'), 
  (87, 2, 'config.xml'),
  (87, 3, 'CONFIGURATION_FILE'),
  (87, 4, '1'),
  (87, 5, '20'),

  (88, 1, 'TEXT'), 
  (88, 2, 'config.xml'),
  (88, 3, 'CONFIGURATION_FILE'),
  (88, 4, '1'),
  (88, 5, '21'),

  (89, 1, 'TEXT'), 
  (89, 2, 'config.xml'),
  (89, 3, 'CONFIGURATION_FILE'),
  (89, 4, '1'),
  (89, 5, '22'),

  (90, 1, 'TEXT'), 
  (90, 2, 'config.xml'),
  (90, 3, 'CONFIGURATION_FILE'),
  (90, 4, '1'),
  (90, 5, '23'),

  (91, 1, 'TEXT'), 
  (91, 2, 'config.xml'),
  (91, 3, 'CONFIGURATION_FILE'),
  (91, 4, '1'),
  (91, 5, '24'),

  (92, 1, 'TEXT'), 
  (92, 2, 'config.xml'),
  (92, 3, 'CONFIGURATION_FILE'),
  (92, 4, '1'),
  (92, 5, '25'),

  (93, 1, 'TEXT'), 
  (93, 2, 'config.xml'),
  (93, 3, 'CONFIGURATION_FILE'),
  (93, 4, '1'),
  (93, 5, '26'),

  (94, 1, 'TEXT'), 
  (94, 2, 'om_native_output.json'),
  (94, 3, 'TEXT'),
  (94, 4, '12'),
  (94, 5, '15'),

  (95, 1, 'TEXT'), 
  (95, 2, 'om_native_output.json'),
  (95, 3, 'TEXT'),
  (95, 4, '12'),
  (95, 5, '16'),

  (96, 1, 'TEXT'), 
  (96, 2, 'om_native_output.json'),
  (96, 3, 'TEXT'),
  (96, 4, '12'),
  (96, 5, '17'),

  (97, 1, 'TEXT'), 
  (97, 2, 'om_native_output.json'),
  (97, 3, 'TEXT'),
  (97, 4, '12'),
  (97, 5, '18'),

  (98, 1, 'TEXT'), 
  (98, 2, 'om_native_output.json'),
  (98, 3, 'TEXT'),
  (98, 4, '12'),
  (98, 5, '19'),

  (99, 1, 'TEXT'), 
  (99, 2, 'om_native_output.json'),
  (99, 3, 'TEXT'),
  (99, 4, '12'),
  (99, 5, '20'),

  (100, 1, 'TEXT'), 
  (100, 2, 'om_native_output.json'),
  (100, 3, 'TEXT'),
  (100, 4, '12'),
  (100, 5, '21'),

  (101, 1, 'TEXT'), 
  (101, 2, 'om_native_output.json'),
  (101, 3, 'TEXT'),
  (101, 4, '12'),
  (101, 5, '22'),

  (102, 1, 'TEXT'), 
  (102, 2, 'om_native_output.json'),
  (102, 3, 'TEXT'),
  (102, 4, '12'),
  (102, 5, '23'),

  (103, 1, 'TEXT'), 
  (103, 2, 'om_native_output.json'),
  (103, 3, 'TEXT'),
  (103, 4, '12'),
  (103, 5, '24'),

  (104, 1, 'TEXT'), 
  (104, 2, 'om_native_output.json'),
  (104, 3, 'TEXT'),
  (104, 4, '12'),
  (104, 5, '25'),

  (105, 1, 'TEXT'), 
  (105, 2, 'om_native_output.json'),
  (105, 3, 'TEXT'),
  (105, 4, '12'),
  (105, 5, '26'),

  (106, 1, 'TEXT'), 
  (106, 2, 'config.xml'),
  (106, 3, 'CONFIGURATION_FILE'),
  (106, 4, '1'),
  (106, 5, '27'),

  (107, 1, 'TEXT'), 
  (107, 2, 'om_native_output.json'),
  (107, 3, 'TEXT'),
  (107, 4, '12'),
  (107, 5, '27');

    
CREATE VIEW run_data_description_view AS
SELECT
	des.id as run_data_description_id,
	axis_value1.value AS format,
	axis_value2.value AS label,
	axis_value3.value AS type,
	axis_value4.value AS source_software,
	axis_value5.value AS destination_software 
FROM
	run_data_description des,
	run_data_description_axis axis1,
	run_data_description_axis axis2,
	run_data_description_axis axis3,
	run_data_description_axis axis4,
	run_data_description_axis axis5,
	run_data_description_axis_value axis_value1,
	run_data_description_axis_value axis_value2,
	run_data_description_axis_value axis_value3,
	run_data_description_axis_value axis_value4,
	run_data_description_axis_value axis_value5
WHERE
	des.id = axis_value1.run_data_description_id AND
	des.id = axis_value2.run_data_description_id AND
	des.id = axis_value3.run_data_description_id AND
	des.id = axis_value4.run_data_description_id AND
	des.id = axis_value5.run_data_description_id AND
	axis_value1.run_data_description_axis_id = axis1.id AND
	axis_value2.run_data_description_axis_id = axis2.id AND
	axis_value3.run_data_description_axis_id = axis3.id AND
	axis_value4.run_data_description_axis_id = axis4.id AND
	axis_value5.run_data_description_axis_id = axis5.id AND
	axis1.id = 1 AND
	axis2.id = 2 AND
	axis3.id = 3 AND
	axis4.id = 4 AND
	axis5.id = 5;
	

CREATE VIEW translator_output_content_view AS
SELECT
    rd.run_id,
	rddv.label,
    rddv.source_software,
    rddv.destination_software,
	rdc.text_content 
FROM
	run_data_content rdc,
	run_data rd,
	run_data_description_view rddv 
WHERE
	rd.content_id = rdc.id AND
	rddv.run_data_description_id = rd.description_id AND
	rddv.source_software = 1;

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

create view software_input as
SELECT
    rd.run_id,
    rddav3.value AS destination_software,
	rddav1.value AS label,
    rdc.text_content
FROM
	run_data_content rdc,
	run_data rd,
	run_data_description_axis_value rddav1,
    run_data_description_axis_value rddav2,
    run_data_description_axis_value rddav3
WHERE
	rddav1.run_data_description_axis_id = 2 AND
    rddav2.run_data_description_axis_id = 3 AND
    rddav3.run_data_description_axis_id = 5 AND
    rd.description_id = rddav3.run_data_description_id AND
    rd.description_id = rddav2.run_data_description_id AND
	rd.description_id = rddav1.run_data_description_id AND
	rdc.id = rd.content_id;
	
-- HSQLDB: CREATE TRIGGER run_creation_timestamp BEFORE INSERT ON run REFERENCING NEW AS newrow FOR EACH ROW SET newrow.created = NOW();
	
-- HSQLDB: END;
	
CREATE TRIGGER run_creation_timestamp BEFORE INSERT ON run 
FOR EACH ROW
SET NEW.created = NOW();

CREATE TRIGGER run_status_update_timestamp BEFORE UPDATE ON run_status 
FOR EACH ROW
SET NEW.last_time_updated = NOW();


	