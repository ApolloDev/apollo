DROP TABLE IF EXISTS run;
DROP TABLE IF EXISTS simulated_population;
DROP TABLE IF EXISTS population_axis;
DROP TABLE IF EXISTS simulated_population_axis_value;
DROP TABLE IF EXISTS time_series;
DROP TABLE IF EXISTS visualizer_output;

CREATE TABLE run (
  id    INT(8) NOT NULL AUTO_INCREMENT,
  label VARCHAR(255),
  md5HashOfConfigurationFile CHAR(32),
  configurationFile TEXT,
  PRIMARY KEY (id)
);

CREATE TABLE visualizer_output (
  id INT(8) NOT NULL AUTO_INCREMENT,
  simulatorRunId INT(8) NOT NULL REFERENCES run (id),
  URL VARCHAR(1024),
  PRIMARY KEY (id)
);

CREATE TABLE simulated_population (
  id               INT(8) NOT NULL AUTO_INCREMENT,
  label            VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE population_axis (
  id    INT(8)       NOT NULL AUTO_INCREMENT,
  label VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE simulated_population_axis_value (
  population_id INT(8)       NOT NULL REFERENCES simulated_population (id),
  axis_id       INT(8)       NOT NULL REFERENCES population_axis (id),
  value         VARCHAR(255) NOT NULL
);

CREATE TABLE time_series (
  run_id        INT(8) NOT NULL REFERENCES run (id),
  population_id INT(8) NOT NULL REFERENCES simulated_population (id),
  time_step     INT(8) NOT NULL,
  pop_count     FLOAT  NOT NULL
);

INSERT INTO population_axis (label) values ("disease_state");
INSERT INTO population_axis (label) values ("location");
