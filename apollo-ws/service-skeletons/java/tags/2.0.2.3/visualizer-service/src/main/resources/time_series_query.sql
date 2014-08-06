SELECT
	disease_states_and_locations_for_run.disease_state AS disease_state,
	disease_states_and_locations_for_run.location_code AS apollo_location_code,
	ts.time_step,
	ts.pop_count 
FROM
	time_series ts,
	run,
	(	SELECT
			spav1.population_id AS pid,
			spav1.value         AS disease_state,
			spav2.value         AS location_code 
		FROM
			simulated_population_axis_value spav1,
			simulated_population_axis_value spav2,
			(	SELECT
					DISTINCT(population_id) AS pid 
				FROM
					time_series ts,
					run r 
				WHERE
					ts.run_id = r.id AND
					r.label LIKE ?) AS p 
		WHERE
			spav2.population_id = spav1.population_id AND
			spav2.axis_id = 2 AND
			spav1.axis_id = 1 AND
			LENGTH(spav2.value) < 6 AND
			p.pid = spav1.population_id ) AS 
	disease_states_and_locations_for_run 
WHERE
	disease_states_and_locations_for_run.pid = ts.population_id AND
	run.id = ts.run_id AND
	run.label LIKE ?
ORDER BY
	disease_state,
	location_code,
	time_step