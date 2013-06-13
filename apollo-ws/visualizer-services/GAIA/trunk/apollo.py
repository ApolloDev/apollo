import os,sys,string
import MySQLdb
import MySQLdb.cursors

_runTable = "run"
_populationAxisTable = "population_axis"
_simulatedPopulationTable = "simulated_population"
_simulatedPopulationAxisValueTable = "simulated_population_axis_value"
_timeSeriesTable = "time_series"

class ApolloSimulatorOutput:
    def __init__(self,runID_,logger_=None):
	self.runID = runID_
        self.logger = logger_
	### here are the structures used to define the total output of an Apollo data unit

	### Initiate the database
        try:
            self.apolloDB = ApolloDB(logger_=self.logger)
            self.apolloDB.connect()
        except:
            raise
        print "HERE JACKASS"
        
	### Get the ID from the database for this runID
	SQLStatement = 'SELECT * from '+_runTable+' WHERE label = "' + self.runID + '"'
        try:
            self._dbRunIndex = self.apolloDB.query(SQLStatement)[0]['id']
        except:
            self.logger.update('DB_NOID_FOUND')
            raise

    def printSimulatedPopulations(self):
	print "Simulated Populations for run: " + str(self.runID)
	for population,id in self.simulatedPopulationsDict.items():
	    print population + ": id = " + str(id)

    def getMaxDayfromTimeSeriesForRunID(self):
	results = self.apolloDB.query('select max(time_step) from time_series '\
				      +'where run_id = %d'%(self._dbRunIndex))
	return int(results[0]['max(time_step)'])
    
    def getNewlyInfectedTimeSeriesForBlocks(self):
	tsDict = {}
	maxTime = self.getMaxDayfromTimeSeriesForRunID() 
	SQLStatement = 'select substring(p.label,18) as incits, t.time_step, t.pop_count '\
		       + 'from time_series t, simulated_population p '\
		       + 'where t.run_id = %d and t.population_id = p.id and p.label like '%(self._dbRunIndex)\
		       + '"newly exposed in %%" and length(substring(p.label,18)) > 6'
        
        try:
            results = self.apolloDB.query(SQLStatement)
        except:
            self.logger.update('DB_POP_FAILED')
            raise
        
        if len(results) == 0:
            self.logger.update('DB_POP_ZERO')
            raise
        
	for row in results:
	    if tsDict.has_key(row['incits']) is False:
		tsDict[row['incits']] = [0 for x in range(0,maxTime+1)]
	    tsDict[row['incits']][int(row['time_step'])] = int(row['pop_count'])
	return tsDict
	
    def getNewlyInfectedTimeSeriesWithinLocation(self,location):
	### STB. This function is deprecated
	tsDict = {}
	fipsTranslate = {}
	### get the populationAxis value for "location"
	LocationAxisID = self.apolloDB._populationAxis()['location']
	### Get distince populations
	SQLStatement = 'SELECT population_id,value from simulated_population_axis_value '\
		 	' where value like "%'+str(location) + '%"'
	popResults = self.apolloDB.query(SQLStatement)
	
	for row in popResults:
	    fipsTranslate[row['population_id']] = row['value']
	### Get all of the Results for this time series
	SQLStatement = 'select * from time_series where run_id = "'+str(self._dbRunIndex)+'" '\
		      +'and population_id in (select id from (select p.id '\
		      +'from simulated_population p, simulated_population_axis_value v, '\
		      +'population_axis a where p.id = v.population_id and '\
		      +'v.value like "newly exposed" and v.axis_id = a.id and '\
		      +'a.label like "disease_state" and p.label like "% '+location+'%") as x)'\
		      +' order by population_id, time_step'
	tsResults = self.apolloDB.query(SQLStatement)
	print SQLStatement
	print "Length = " + str(len(tsResults))
	for row in tsResults:
	    ## Get the fips code for this population
	    fipsCode = fipsTranslate[row['population_id']]
	    if tsDict.has_key(fipsCode) is False:
		tsDict[fipsCode] = []
	    tsDict[fipsCode].append(row['pop_count'])
	
	return tsDict
					  		   
class ApolloDB:
    
    def __init__(self,host_="warhol-fred.psc.edu",
		 user_="apolloext",dbname_="apollo",logger_=None):

	self._host = host_
	self._user = user_
	self._dbname = dbname_
	self._conn = None
	self._DictCursor = None
	self._RegularCursor = None
	self.populationAxis = None
        self.logger = logger_


    def connect(self):
	if self._conn is not None:
	    print "Connection to Apollo Database has already been established"
	    return
	try:
	    self._conn = MySQLdb.connect(host=self._host,
					    user=self._user,
					    db=self._dbname)
	    self._conn.autocommit(True)
            self.logger.update('DB_CONNECT_SUCCESS')
	except MySQLdb.Error, e:
	    print "Problem connecting to Apollo database: %d %s"%(e.args[0],e.args[1])
            self.logger.update('DB_CONNECT_FAILED')
            raise
            
	
	self._cursor = self._conn.cursor(MySQLdb.cursors.DictCursor)
	self.populationAxis = self._populationAxis()
	
    def close(self):
	if self._conn is None:
	    raise RuntimeError("A connection to the Apollo database"\
			       "has not been made before close is made")

	self._conn.close()

    def query(self,SQLString):
	if self._conn is None:
	    raise RuntimeError("A connection to the Apollo database "\
			       "has not been made before queury is made")
    
        try:
            self._cursor.execute(SQLString)
        except:
            self.logger.update('DB_QUERY_FAILED',message=SQLString)
            raise
        
        rows = self._cursor.fetchall()
	
        return rows

    def insertID(self):
	return self._conn.insert_id()

    def _populationAxis(self):
	populationAxisDict = {}
	SQLStatement = "SELECT * from " + _populationAxisTable

	result = self.query(SQLStatement)

	for row in result:
	    populationAxisDict[row["label"]] = row["id"]

	return populationAxisDict

    def getRuns(self):
	runList = []
	SQLStatement = "SELECT * from " + _runTable
	result = self.query(SQLStatement)
	for row in result:
	    runList.append(row['label'])
	return runList
    
def main():

    apolloDB = ApolloDB()
    print "Testing connection to Apollo database"
    apolloDB.connect()
    print "Connection to Database successful"
    #apolloDB.populationAxis()
    runList = apolloDB.getRuns()
    print "Closing connection to database"
    apolloDB.close()
    print "Closing successful"

    print "RunList 0: " + runList[0]
    apolloOut = ApolloSimulatorOutput(runList[0])
    #apolloOut.getNewlyInfectedTimeSeriesWithinLocation("42003")
    #apolloOut.printSimulatedPopulations()

############
# Main hook
############

if __name__=="__main__":
    main()
	

    
