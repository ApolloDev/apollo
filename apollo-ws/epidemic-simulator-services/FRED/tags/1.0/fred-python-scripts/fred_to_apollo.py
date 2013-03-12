import os,sys,string
import math
import optparse
from fred import FRED,FRED_RUN,FRED_Infection_Set,FRED_Locations_Set,FRED_People_Set
import apollo
import time

if __name__ == '__main__':
    parser = optparse.OptionParser(usage="""
    %prog [--help][-k key][-r run_number][-t vis_type]
    -k or --key   Fred Key
    -r or --run   The run number of vizualize
                  ave = produce an average result
                  all = produce an input for all runs
   
    -w or --time  Turn on profiling
    -d or --debug Turn on debug printing
    """)

    parser.add_option("-k","--key",type="string",
                      help="The FRED key for the run you wish to visualize")
    parser.add_option("-r","--run",type="string",
                      help="The number of the run you would like to visualize (number,ave, or all)",
                      default=1)
    parser.add_option("-w","--time",action="store_true",
                      default=False)
    parser.add_option("-d","--debug",action="store_true",
                      default=False)

    opts,args=parser.parse_args()

    ### This only currently works for allegheny county

    #### Initialize the FRED SIMS
    fred = FRED()
    
    key = opts.key

    run = opts.run

    try:
        int(run)
    except:
        if run != "ave" and run != "all":
            print "Invalid parameter for run, needs to be an integer, all or ave"
            sys.exit(1)

    fred_run = FRED_RUN(fred,key)
    numberDays = int(fred_run.get_param("days"))
    outputsAve = fred_run.outputsAve

    apolloDB = apollo.ApolloDB()
    apolloDB.connect()
    

    #for day in outputsAve:
#	print "Day = %d I = %d"%(day['Day'],day['I'])


    #ApolloConn = MySQLdb.connect(host="warhol-fred.psc.edu",
#				 user="apolloext",
#				 db="apollo")
    #ApolloCursor = ApolloConn.cursor()


    ## Fill in the run table
    SQLString = 'INSERT INTO run set label = "' + key + '"'
    apolloDB.query(SQLString)
    runInsertID = apolloDB.insertID()
#    print "Run ID = " + str(runInsertID)
#    sys.exit()
    #ApolloCursor.execute(SQLString)
    #runInsertID = ApolloConn.insert_id()


    stateList = {'S':'susceptible','E':'exposed','I':'infectious','R':'recovered'}
    #axisList = ['disease_status','location']
    locationList = ['42003'] # need to replace with an automatic way of getting this info
    stateInsertIDDict = {}
    #axisInsertIDDict = {}

    ### setup simulated_population_axis
    #for axis in axisList:
    #    ApolloCursor.execute('INSERT INTO population_axis set label = "%s"'%axis)
    #    axisInsertIDDict[axis] = ApolloConn.insert_id()
    axisInsertIDDict = apolloDB._populationAxis()
    #ApolloCursor.execute("SELECT * from population_axis")
    #axisResults = ApolloCursor.fetchall()

    #for row in axisResults:
#	axisInsertIDDict[row[1]] = row[0]

#    print "Axis = " + str(axisInsertIDDict)
#    sys.exit()
    ### setup simulated_populations table
    for state in stateList.keys():
        for location in locationList:
            populationLabel = str(stateList[state]) + " in " + location
            SQLString = 'INSERT INTO simulated_population set label = "' + populationLabel + '"'
            #result = ApolloCursor.execute(SQLString)
	    apolloDB.query(SQLString)
            stateInsertIDDict[(state,location)] = apolloDB.insertID()
        

    ### Set up the simulated population axis value table
    for state in stateList.keys():
        for location in locationList:
            populationLabelID = stateInsertIDDict[(state,location)]
            axisID = axisInsertIDDict["disease_state"]
            apolloDB.query('INSERT INTO simulated_population_axis_value set ' +\
			   'population_id = "' + str(populationLabelID) + '", ' +\
			   'axis_id = "' + str(axisID) + '", ' +\
			   'value = "' + stateList[state]  + '"')
            axisID = axisInsertIDDict['location']
            apolloDB.query('INSERT INTO simulated_population_axis_value set ' +\
			   'population_id = "' + str(populationLabelID) + '", ' +\
			   'axis_id = "' + str(axisID) + '", ' +\
			   'value = "' + location  + '"')
                           
    for state in stateList.keys():
        for location in locationList:
            for day in outputsAve:
                populationID = stateInsertIDDict[(state,location)]
                SQLString = 'INSERT INTO time_series set '\
                    +'run_id = "' + str(runInsertID) + '", '\
                    +'population_id = "' + str(populationID) + '", '\
                    +'time_step = "' + str(day['Day']) +'", '\
                    +'pop_count = "' + str(day[state]) + '"'
		apolloDB.query(SQLString)
                #result = ApolloCursor.execute(SQLString)
	

    synth_pop_dir = fred_run.get_param("synthetic_population_directory")
    synth_pop_id = fred_run.get_param("synthetic_population_id")
    synth_pop_prefix = os.path.expandvars(synth_pop_dir + "/" + synth_pop_id + "/" + synth_pop_id)
    synth_pop_filename = synth_pop_prefix + "_synth_people.txt"
    synth_household_filename = synth_pop_prefix + "_synth_households.txt"
    
    try:
        open(synth_pop_filename,"r")
    except IOError:
        print "Problem opening FRED population file " + synth_pop_filename
        sys.exit(1)
	
    fred_population = FRED_People_Set(synth_pop_filename)
    
    numDays = fred_run.get_param("days")
    aveInfByBG = []
    fipsList = []
    for i in range(0,int(numDays)):
	aveInfByBG.append({})
    
    for infection_file in fred_run.infection_file_names:
	run_number = infection_file.split("infections")[1].split(".")[0]
	print "Run = " + run_number
	infSet = fred_run.get_infection_set(int(run_number))

	for i in range(0,len(infSet.infectionList)):
	    day = int(infSet.get('day',i))
	    infected_id = int(infSet.get('host',i))
	    #personRec = fred_population.people[int(infRec.infected_id)]
	    personFips = fred_population.get("stcotrbg",infected_id)
            #print personFips
            if personFips not in fipsList:
                fipsList.append(personFips)
	    if aveInfByBG[day].has_key(personFips) is False:
		aveInfByBG[day][personFips] = 0.0
	    aveInfByBG[day][personFips] += 1

    ### finish off averages
    numRuns = fred_run.number_of_runs
    sum = 0
    for day in range(0,len(aveInfByBG)):
	for fips in aveInfByBG[day].keys():
	    sum += aveInfByBG[day][fips]	    
	    aveInfByBG[day][fips] /= float(numRuns)
   
    print "sum = " + str(sum) 
    ### update simulated_population
    fipsInsertIDDict = {}
    #print 'fips list = ' + str(fipsList)
    for fips in fipsList:
        apolloDB.query('INSERT INTO simulated_population set label = "newly exposed in '+fips + '"')
        insertID = apolloDB.insertID() 
        fipsInsertIDDict[fips] = insertID
        axisID = axisInsertIDDict['disease_state']
        apolloDB.query('INSERT INTO simulated_population_axis_value set ' +\
		       'population_id = "' + str(insertID) + '", ' +\
		       'axis_id = "' + str(axisID) + '", ' +\
		       'value = "newly exposed"')
        axisID = axisInsertIDDict['location']

        apolloDB.query('INSERT INTO simulated_population_axis_value set ' +\
		       'population_id = "' + str(insertID) + '", ' +\
		       'axis_id = "' + str(axisID) + '", ' +\
		       'value = "' + fips + '"')

	
    
#    for day in range(0,len(aveInfByBG)):
    for day in range(0,numberDays):
        #if day > len(aveInfByBG):
        #for fips in fipsList:
        #    SQLString = 'INSERT INTO time_series set '\
        #             +'run_id = "' + str(runInsertID) + '", '\
        #             +'population_id = "' + str(fipsInsertIDDict[fips]) + '", '\
        #             +'time_step = "' + str(day) + '", '\
        #             +'pop_count = "0"'
        #    apolloDB.query(SQLString)
        #else:
        for fips in aveInfByBG[day].keys():
         #print "Day = " + str(day) + " and Fips: " + fips
        #    #print "ave = " + str(int(aveInfByBG[day][fips]))
            SQLString = 'INSERT INTO time_series set '\
                 +'run_id = "' + str(runInsertID) + '", '\
                 +'population_id = "' + str(fipsInsertIDDict[fips]) + '", '\
                 +'time_step = "' + str(day) + '", '\
                 +'pop_count = "' + str(int(aveInfByBG[day][fips])) + '"'
            apolloDB.query(SQLString)
                
    
    apolloDB.close()
    
    
