/*  Comments starting with "-- HSQLDB: " are commands that are interpreted when
this script is used for the ApolloDbUtils unit tests (which use HSQLDB).

All commands listed after "-- HSQLDB: END;" will NOT be run when this script is
used in the unit tests. These commands are MySQL-specific and will not work with HSQLDB.
 */

DROP TABLE IF EXISTS run_status;
DROP TABLE IF EXISTS run_status_description;
DROP TABLE IF EXISTS simulation_group_definition;

DROP TABLE IF EXISTS run;

DROP TABLE IF EXISTS users;

DROP TABLE IF EXISTS software_identification;
DROP TABLE IF EXISTS simulation_groups;

CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  requester_id VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);

-- HSQLDB: ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

CREATE TABLE software_identification (
  id INT NOT NULL AUTO_INCREMENT,
  developer VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  version VARCHAR(255) NOT NULL,
  service_type VARCHAR(255) NOT NULL,
  wsdl_url TEXT NOT NULL,
  license_name TEXT,
  license_version TEXT,
  license_url TEXT,
  license_attribution TEXT,
  PRIMARY KEY (id)
);

-- HSQLDB: ALTER TABLE software_identification ALTER COLUMN id RESTART WITH 1;

INSERT INTO `software_identification` VALUES (1,'UPitt','Translator','1.0','translator','http://localhost:8080/translator-service-war-4.0.1-SNAPSHOT/services/translatorservice?wsdl', NULL, NULL, NULL, NULL),
											 (2,'UPitt','SEIR','3.0','simulator','http://localhost:8080/pitt-simulator-service-4.0.1-SNAPSHOT/ws', NULL, NULL, NULL, NULL),
											 (3,'UPitt,PSC,CMU','FRED','2.0.1_i','simulator','http://pscss.olympus.psc.edu:8097', NULL, NULL, NULL, NULL),
											 (4,'UPitt','Time Series Visualizer','1.0','visualizer','http://localhost:8080/visualizer-service-rest-frontend-4.0.1-SNAPSHOT/ws', NULL, NULL, NULL ,NULL),
											 (5,'PSC','GAIA','1.0','visualizer','http://gaia.pha.psc.edu:13501/gaia?wsdl', NULL, NULL, NULL, NULL),
											 (6,'Chao-FredHutchinsonCancerCenter','FluTE','1.15','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
											 (7,'UPitt','Anthrax','1.0','simulator','http://localhost:8080/pitt-simulator-service-4.0.1-SNAPSHOT/ws', NULL, NULL, NULL, NULL),
                        (8,'PSC','CLARA','0.5','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                        (9,'Steve Bellan','Lancet Ebola','1.0','simulator','http://localhost:8080/pitt-simulator-service-4.0.1-SNAPSHOT/ws', 'Creative Commons Attribution 4.0 International License', '4.0', 'http://creativecommons.org/licenses/by/4.0/', 'The Lancet Ebola simulator was used to produce results for the following publication:\n\nBellan SE, JRC Pulliam, J Dushoff, and LA Meyers. Asymptomatic infection, natural immunity, and Ebola dynamics. Letter, submitted to Lancet.\n\nThe original source code for the simulator is available at https://github.com/ICI3D/Ebola. The code was changed for use with the Apollo Web Services to do the following:\n\n - Read initial compartment sizes, transmission and disease parameters from an external input file\n - Produce time series output files for each compartment\n - Compute a newly exposed time series from the cumulative exposed time series'),
											 (10,'UPitt','Data Service','1.0','data','http://localhost:8080/data-service-war-4.0.1-SNAPSHOT/services/dataservice?wsdl', NULL, NULL, NULL, NULL),
											 (11,'UPitt', 'Broker Service','4.0','broker','http://localhost:8080/broker-service-war-4.0.1-SNAPSHOT/services/apolloservice?wsdl', NULL, NULL, NULL ,NULL),
                       (12,'any', 'any','any','endUserApplication','not_applicable', NULL, NULL, NULL ,NULL),
                       (13,'UPitt', 'Run Manager','4.0','runManager','http://localhost:8080/run-manager-service-rest-frontend-4.0.1-SNAPSHOT/', NULL, NULL, NULL ,NULL),
                       (14,'Swiss TPH','OpenMalaria','R0063','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (15,'Swiss TPH','OpenMalaria','R0065','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (16,'Swiss TPH','OpenMalaria','R0068','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (17,'Swiss TPH','OpenMalaria','R0111','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (18,'Swiss TPH','OpenMalaria','R0115','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (19,'Swiss TPH','OpenMalaria','R0121','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (20,'Swiss TPH','OpenMalaria','R0125','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (21,'Swiss TPH','OpenMalaria','R0131','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (22,'Swiss TPH','OpenMalaria','R0132','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (23,'Swiss TPH','OpenMalaria','R0133','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (24,'Swiss TPH','OpenMalaria','R0670','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (25,'Swiss TPH','OpenMalaria','R0674','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (26,'Swiss TPH','OpenMalaria','R0678','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (27,'Swiss TPH','OpenMalaria','base','simulator','http://gaia.pha.psc.edu:8099/pscsimu?wsdl', NULL, NULL, NULL, NULL),
                       (28,'UPitt', 'Query Service','4.0','queryService','http://127.0.0.1:5000', NULL, NULL, NULL ,NULL);

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
  CONSTRAINT fk_user_id FOREIGN KEY (requester_id) REFERENCES users (id),
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
INSERT INTO run_status_description (status) VALUES ('called_query_service');

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

	
-- HSQLDB: CREATE TRIGGER run_creation_timestamp BEFORE INSERT ON run REFERENCING NEW AS newrow FOR EACH ROW SET newrow.created = NOW();
	
-- HSQLDB: END;
	
CREATE TRIGGER run_creation_timestamp BEFORE INSERT ON run 
FOR EACH ROW
SET NEW.created = NOW();

CREATE TRIGGER run_status_update_timestamp BEFORE UPDATE ON run_status 
FOR EACH ROW
SET NEW.last_time_updated = NOW();


	