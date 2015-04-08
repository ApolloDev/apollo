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

INSERT INTO `software_identification` VALUES (1,'UPitt','Translator','1.0','translator','http://localhost:8080/translator-service-war-3.0.0-SNAPSHOT/services/translatorservice?wsdl',1, NULL, NULL, NULL, NULL),
											 (2,'UPitt','SEIR','3.0','simulator','http://localhost:8080/pitt-simulator-service-war-3.0.0-SNAPSHOT/services/pittsimulatorservice?wsdl',1, NULL, NULL, NULL, NULL),
											 (3,'UPitt,PSC,CMU','FRED','2.0.1_i','simulator','http://gaia.pha.psc.edu:13500/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
											 (4,'UPitt','Time Series Visualizer','1.0','visualizer','http://localhost:8080/visualizer-service-war-3.0.0-SNAPSHOT/services/visualizerservice?wsdl',1, NULL, NULL, NULL ,NULL),
											 (5,'PSC','GAIA','1.0','visualizer','http://gaia.pha.psc.edu:13501/gaia?wsdl',1, NULL, NULL, NULL, NULL),
											 (6,'Chao-FredHutchinsonCancerCenter','FluTE','1.15','simulator','http://gaia.pha.psc.edu:13500/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
											 (7,'UPitt','Anthrax','1.0','simulator','http://localhost:8080/pitt-simulator-service-war-3.0.0-SNAPSHOT/services/pittsimulatorservice?wsdl',1, NULL, NULL, NULL, NULL),
                                             (8,'PSC','CLARA','0.5','simulator','http://gaia.pha.psc.edu:13500/pscsimu?wsdl',1, NULL, NULL, NULL, NULL),
                                             (9,'Steve Bellan','Lancet Ebola','1.0','simulator','http://localhost:8080/pitt-simulator-service-war-3.0.0-SNAPSHOT/services/pittsimulatorservice?wsdl',1, 'Creative Commons Attribution 4.0 International License', '4.0', 'http://creativecommons.org/licenses/by/4.0/', 'The Lancet Ebola simulator was used to produce results for the following publication:\n\nBellan SE, JRC Pulliam, J Dushoff, and LA Meyers. Asymptomatic infection, natural immunity, and Ebola dynamics. Letter, submitted to Lancet.\n\nThe original source code for the simulator is available at https://github.com/ICI3D/Ebola. The code was changed for use with the Apollo Web Services to do the following:\n\n - Read initial compartment sizes, transmission and disease parameters from an external input file\n - Produce time series output files for each compartment\n - Compute a newly exposed time series from the cumulative exposed time series'),
											 (10,'UPitt','Data Service','1.0','data','http://localhost:8080/data-service-war-3.0.0-SNAPSHOT/services/dataservice?wsdl',1, NULL, NULL, NULL, NULL),
											 (11,'UPitt', 'Broker Service','3.0.0','broker','http://localhost:8080/broker-service-war-3.0.0-SNAPSHOT/services/brokerservice?wsdl',1, NULL, NULL, NULL ,NULL);

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
INSERT INTO run_data_description (label) values ('RunSimulationMessage JSON, run_simulation_message.json '); /* 37*/
INSERT INTO run_data_description (label) values ('RunVisualizationMessage JSON for Time Series Visualizer, run_visualization_message.json '); /* 38*/
INSERT INTO run_data_description (label) values ('RunVisualizationMessage JSON for GAIA Visualizer, run_visualization_message.json '); /* 39*/
INSERT INTO run_data_description (label) values ('Anthrax text configuration file, config.txt '); /* 40*/
INSERT INTO run_data_description (label) values ('Anthrax verbose configuration file, verbose.html'); /* 41 */
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for susceptible, susceptible.txt '); /* 42*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for exposed, exposed.txt '); /* 43*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for recovered, recovered.txt '); /* 44*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for newly_deceased, newly_deceased.txt '); /* 45*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for prophylactics_given, prophylactics_given.txt ');/* 46*/
INSERT INTO run_data_description (label) values ('TimeSeriesVisualizer output url, treatment.png'); /* 47 */
INSERT INTO run_data_description (label) values ('TimeSeriesVisualizer output url, newly_deceased.png'); /* 48 */
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for asymptomatic, asymptomatic.txt '); /* 49*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for symptomatic, symptomatic.txt '); /* 50*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for fulminant, fulminant.txt '); /* 51*/
INSERT INTO run_data_description (label) values ('Anthrax Simulator log file for dead, dead.txt '); /* 52*/
INSERT INTO run_data_description (label) values ('TimeSeriesVisualizer output url, disease_states.png'); /* 53 */
INSERT INTO run_data_description (label) values ('CLARA text configuration file, config.txt '); /* 54*/
INSERT INTO run_data_description (label) values ('CLARA verbose configuration file, verbose.html'); /* 55 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for susceptible, susceptible.txt '); /* 56 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for exposed, exposed.txt '); /* 57 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for infectious, infectious.txt '); /* 58 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for temp_immuned, temp_immuned.txt '); /* 59 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for recovered, recovered.txt '); /* 60 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for immuned, immuned.txt '); /* 61 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for newly_exposed, newly_exposed.txt '); /* 62 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for mosquito_susceptible, mosquito_susceptible.txt '); /* 63 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for mosquito_exposed, mosquito_exposed.txt '); /* 64 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for mosquito_infectious, mosquito_infectious.txt '); /* 65 */
INSERT INTO run_data_description (label) values ('TSV CLARA Simulator log file for mosquito_newly_exposed, mosquito_newly_exposed.txt '); /* 66 */
INSERT INTO run_data_description (label) values ('TimeSeriesVisualizer output url, prevalence_mosquito.png'); /* 67 */
INSERT INTO run_data_description (label) values ('TimeSeriesVisualizer output url, incidence_mosquito.png'); /* 68 */
INSERT INTO run_data_description (label) values ('Lancet Ebola text configuration file, config.txt '); /* 69*/
INSERT INTO run_data_description (label) values ('Lancet Ebola verbose configuration file, verbose.html'); /* 70 */
INSERT INTO run_data_description (label) values ('TSV Lancet Ebola Simulator log file for susceptible, susceptible.txt '); /* 71 */
INSERT INTO run_data_description (label) values ('TSV Lancet Ebola Simulator log file for exposed, exposed.txt '); /* 72 */
INSERT INTO run_data_description (label) values ('TSV Lancet Ebola Simulator log file for infectious, infectious.txt '); /* 73 */
INSERT INTO run_data_description (label) values ('TSV Lancet Ebola Simulator log file for recovered, recovered.txt '); /* 74 */
INSERT INTO run_data_description (label) values ('TSV Lancet Ebola Simulator log file for newly_exposed, newly_exposed.txt '); /* 75 */
INSERT INTO run_data_description (label) values ('Run Data Service message JSON, run_data_service_message.json '); /* 76*/
INSERT INTO run_data_description (label) values ('Batch inputs file with run IDs, batch_inputs_with_run_ids.txt '); /* 77*/
INSERT INTO run_data_description (label) values ('FRED ODS required output file for model validation, ods_required_output_for_model_validation.csv'); /* 78*/
INSERT INTO run_data_description (label) values ('FluTE ODS required output file for model validation, ods_required_output_for_model_validation.csv'); /* 79*/
INSERT INTO run_data_description (label) values ('SEIR ODS required output file for model validation, ods_required_output_for_model_validation.csv'); /* 80*/

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
	(5, 5, '0'),
	
	(6, 1, 'URL'), 
	(6, 2, 'prevalence.png'),
	(6, 3, 'IMAGE'),
	(6, 4, '4'),
	(6, 5, '0'),
	
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
	(36, 5, '0'),
	
	(37, 1, 'TEXT'), 
	(37, 2, 'run_simulation_message.json'),
	(37, 3, 'RUN_SIMULATION_MESSAGE'),
	(37, 4, '0'),
	(37, 5, '1'),
	
	(38, 1, 'TEXT'), 
	(38, 2, 'run_visualization_message.json'),
	(38, 3, 'RUN_VISUALIZATION_MESSAGE'),
	(38, 4, '0'),
	(38, 5, '4'),
	
	(39, 1, 'TEXT'), 
	(39, 2, 'run_visualization_message.json'),
	(39, 3, 'RUN_VISUALIZATION_MESSAGE'),
	(39, 4, '0'),
	(39, 5, '5'),
	
	(40, 1, 'TEXT'), 
	(40, 2, 'config.txt'),
	(40, 3, 'CONFIGURATION_FILE'),
	(40, 4, '1'),
	(40, 5, '7'),
	
	(41, 1, 'TEXT'), 
	(41, 2, 'verbose.html'),
	(41, 3, 'CONFIGURATION_FILE'),
	(41, 4, '1'),
	(41, 5, '7'),

	(42, 1, 'TEXT'), 
	(42, 2, 'susceptible.txt'),
	(42, 3, 'SIMULATOR_LOG_FILE'),
	(42, 4, '7'),
	(42, 5, '4'),
	
	(43, 1, 'TEXT'), 
	(43, 2, 'exposed.txt'),
	(43, 3, 'SIMULATOR_LOG_FILE'),
	(43, 4, '7'),
	(43, 5, '4'),
	
	(44, 1, 'TEXT'), 
	(44, 2, 'recovered.txt'),
	(44, 3, 'SIMULATOR_LOG_FILE'),
	(44, 4, '7'),
	(44, 5, '4'),
    
	(45, 1, 'TEXT'), 
	(45, 2, 'newly_deceased.txt'),
	(45, 3, 'SIMULATOR_LOG_FILE'),
	(45, 4, '7'),
	(45, 5, '4'),
    
	(46, 1, 'TEXT'), 
	(46, 2, 'prophylactics_given.txt'),
	(46, 3, 'SIMULATOR_LOG_FILE'),
	(46, 4, '7'),
	(46, 5, '4'),
    
	(47, 1, 'URL'), 
	(47, 2, 'treatment.png'),
	(47, 3, 'IMAGE'),
	(47, 4, '4'),
	(47, 5, '0'),
    
	(48, 1, 'URL'), 
	(48, 2, 'newly_deceased.png'),
	(48, 3, 'IMAGE'),
	(48, 4, '4'),
	(48, 5, '0'),
    
   	(49, 1, 'TEXT'), 
	(49, 2, 'asymptomatic.txt'),
	(49, 3, 'SIMULATOR_LOG_FILE'),
	(49, 4, '7'),
	(49, 5, '4'),
    
   	(50, 1, 'TEXT'), 
	(50, 2, 'symptomatic.txt'),
	(50, 3, 'SIMULATOR_LOG_FILE'),
	(50, 4, '7'),
	(50, 5, '4'),
    
   	(51, 1, 'TEXT'), 
	(51, 2, 'fulminant.txt'),
	(51, 3, 'SIMULATOR_LOG_FILE'),
	(51, 4, '7'),
	(51, 5, '4'),  
    
   	(52, 1, 'TEXT'), 
	(52, 2, 'dead.txt'),
	(52, 3, 'SIMULATOR_LOG_FILE'),
	(52, 4, '7'),
	(52, 5, '4'),
    
	(53, 1, 'URL'), 
	(53, 2, 'disease_states.png'),
	(53, 3, 'IMAGE'),
	(53, 4, '4'),
	(53, 5, '0'),
    
    (54, 1, 'TEXT'), 
	(54, 2, 'config.txt'),
	(54, 3, 'CONFIGURATION_FILE'),
	(54, 4, '1'),
	(54, 5, '8'),
    
    (55, 1, 'TEXT'), 
	(55, 2, 'verbose.html'),
	(55, 3, 'CONFIGURATION_FILE'),
	(55, 4, '1'),
	(55, 5, '8'),
    
   	(56, 1, 'TEXT'), 
	(56, 2, 'susceptible.txt'),
	(56, 3, 'SIMULATOR_LOG_FILE'),
	(56, 4, '8'),
	(56, 5, '4'),
    
   	(57, 1, 'TEXT'), 
	(57, 2, 'exposed.txt'),
	(57, 3, 'SIMULATOR_LOG_FILE'),
	(57, 4, '8'),
	(57, 5, '4'),
	
   	(58, 1, 'TEXT'), 
	(58, 2, 'infectious.txt'),
	(58, 3, 'SIMULATOR_LOG_FILE'),
	(58, 4, '8'),
	(58, 5, '4'),
    
   	(59, 1, 'TEXT'), 
	(59, 2, 'temp_immuned.txt'),
	(59, 3, 'SIMULATOR_LOG_FILE'),
	(59, 4, '8'),
	(59, 5, '4'),
    
   	(60, 1, 'TEXT'), 
	(60, 2, 'recovered.txt'),
	(60, 3, 'SIMULATOR_LOG_FILE'),
	(60, 4, '8'),
	(60, 5, '4'),
    
   	(61, 1, 'TEXT'), 
	(61, 2, 'immuned.txt'),
	(61, 3, 'SIMULATOR_LOG_FILE'),
	(61, 4, '8'),
	(61, 5, '4'),
    
   	(62, 1, 'TEXT'), 
	(62, 2, 'newly_exposed.txt'),
	(62, 3, 'SIMULATOR_LOG_FILE'),
	(62, 4, '8'),
	(62, 5, '4'),
    
   	(63, 1, 'TEXT'), 
	(63, 2, 'mosquito_susceptible.txt'),
	(63, 3, 'SIMULATOR_LOG_FILE'),
	(63, 4, '8'),
	(63, 5, '4'),
    
   	(64, 1, 'TEXT'), 
	(64, 2, 'mosquito_exposed.txt'),
	(64, 3, 'SIMULATOR_LOG_FILE'),
	(64, 4, '8'),
	(64, 5, '4'),
    
   	(65, 1, 'TEXT'), 
	(65, 2, 'mosquito_infectious.txt'),
	(65, 3, 'SIMULATOR_LOG_FILE'),
	(65, 4, '8'),
	(65, 5, '4'),
    
   	(66, 1, 'TEXT'), 
	(66, 2, 'mosquito_newly_exposed.txt'),
	(66, 3, 'SIMULATOR_LOG_FILE'),
	(66, 4, '8'),
	(66, 5, '4'),
    
   	(67, 1, 'URL'), 
	(67, 2, 'prevalence_mosquito.png'),
	(67, 3, 'IMAGE'),
	(67, 4, '4'),
	(67, 5, '0'),
    
   	(68, 1, 'URL'), 
	(68, 2, 'incidence_mosquito.png'),
	(68, 3, 'IMAGE'),
	(68, 4, '4'),
	(68, 5, '0'),
    
    (69, 1, 'TEXT'), 
	(69, 2, 'config.txt'),
	(69, 3, 'CONFIGURATION_FILE'),
	(69, 4, '1'),
	(69, 5, '9'),
    
    (70, 1, 'TEXT'), 
	(70, 2, 'verbose.html'),
	(70, 3, 'CONFIGURATION_FILE'),
	(70, 4, '1'),
	(70, 5, '9'),
    
   	(71, 1, 'TEXT'), 
	(71, 2, 'susceptible.txt'),
	(71, 3, 'SIMULATOR_LOG_FILE'),
	(71, 4, '9'),
	(71, 5, '4'),
    
   	(72, 1, 'TEXT'), 
	(72, 2, 'exposed.txt'),
	(72, 3, 'SIMULATOR_LOG_FILE'),
	(72, 4, '9'),
	(72, 5, '4'),
    
   	(73, 1, 'TEXT'), 
	(73, 2, 'infectious.txt'),
	(73, 3, 'SIMULATOR_LOG_FILE'),
	(73, 4, '9'),
	(73, 5, '4'),
    
    (74, 1, 'TEXT'), 
	(74, 2, 'recovered.txt'),
	(74, 3, 'SIMULATOR_LOG_FILE'),
	(74, 4, '9'),
	(74, 5, '4'),
    
    (75, 1, 'TEXT'), 
	(75, 2, 'newly_exposed.txt'),
	(75, 3, 'SIMULATOR_LOG_FILE'),
	(75, 4, '9'),
	(75, 5, '4'),
    
	(76, 1, 'TEXT'), 
	(76, 2, 'run_data_service_message.json'),
	(76, 3, 'RUN_DATA_SERVICE_MESSAGE'),
	(76, 4, '0'),
	(76, 5, '10'),
    
    (77, 1, 'TEXT'), 
	(77, 2, 'batch_inputs_with_run_ids.txt'),
	(77, 3, 'CONFIGURATION_FILE'),
	(77, 4, '11'),
	(77, 5, '0'),
    
    (78, 1, 'TEXT'), 
	(78, 2, 'ods_required_output_for_model_validation.csv'),
	(78, 3, 'SIMULATOR_LOG_FILE'),
	(78, 4, '3'),
	(78, 5, '0'),
    
    (79, 1, 'TEXT'), 
	(79, 2, 'ods_required_output_for_model_validation.csv'),
	(79, 3, 'SIMULATOR_LOG_FILE'),
	(79, 4, '6'),
	(79, 5, '0'),
    
    (80, 1, 'TEXT'), 
	(80, 2, 'ods_required_output_for_model_validation.csv'),
	(80, 3, 'SIMULATOR_LOG_FILE'),
	(80, 4, '2'),
	(80, 5, '0');
    
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


	