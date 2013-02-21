import os,sys,string
import MySQLdb
import MySQLdb.cursors

_runTable = "run"
_populationAxisTable = "population_axis"
_simulatedPopulationTable = "simulated_population"
_simulatedPopulationAxisValueTable = "simulated_population_axis_value"
_timeSeriesTable = "time_series"

class ApolloSimulatorOutput:
    def __init__(self,runID_):
	self.runID = runID_

	### here are the structures used to define the total output of an Apollo data unit

	### Initiate the database
	self.apolloDB = ApolloDB()
	self.apolloDB.connect()

	### Get the ID from the database for this runID
	SQLStatement = 'SELECT * from '+_runTable+' WHERE label = "' + self.runID + '"'
	self._dbRunIndex = self.apolloDB.query(SQLStatement)[0]['id']
	#print str(self.dbRunIndex)

	### Get the Simulated Populations
	self.simulatedPopulationsDict = {}
	SQLStatement = "SELECT * FROM " + _simulatedPopulationTable
	results = self.apolloDB.query(SQLStatement)
	for row in results:
	    self.simulatedPopulationsDict[row['label']] = row['id']

	### Get the Simulated Populations Axis Values
	self.simulatedPopulationAxisValueDict = {}
	SQLStatement = "SELECT * FROM " + _simulatedPopulationAxisValueTable
	results = self.apolloDB.query(SQLStatement)
	for row in results:
	    self.simulatedPopulationAxisValueDict[(row['population_id'],row['axis_id'])] = \
					row['value']						 
	
    ##def getAvailableAxis(self):
##	fullAxisDict = self.apolloDB.populationAxis()

##	### now

    def printSimulatedPopulations(self):
	print "Simulated Populations for run: " + str(self.runID)
	for population,id in self.simulatedPopulationsDict.items():
	    print population + ": id = " + str(id)

    
    def getTimeSeriesForSimulatedPopulation(self,simPopulation):
	tsList = []
	if not simPopulation in self.simulatedPopulationsDict.keys():
	    raise RuntimeError("Requested %s simulated population that"\
			       "doesn't exist in this Apollo output runID:%s"\
			       %(simPopulation,self.runID))

	popID = self.simulatedPopulationsDict[simPopulation]
	SQLStatement = 'SELECT time_step,pop_count from ' + _timeSeriesTable +\
		       ' WHERE run_id = "' + str(self._dbRunIndex) + '"'+\
		       ' and population_id = "' + str(popID) + '" ORDER BY time_step'

	#print SQLStatement
	tsResults = self.apolloDB.query(SQLStatement)
	for row in tsResults:
	    tsList.append(row['pop_count'])

    #def getNewlyInfectedTimeSeries(self,location):
	

	
			  
		     

    
class ApolloDB:
    
    def __init__(self,host_="warhol-fred.psc.edu",
		 user_="apolloext",dbname_="apollo"):

	self._host = host_
	self._user = user_
	self._dbname = dbname_
	self._conn = None
	self._DictCursor = None
	self._RegularCursor = None
	self.populationAxis = None


    def connect(self):
	if self._conn is not None:
	    print "Connection to Apollo Database has already been established"
	    return
	try:
	    self._conn = MySQLdb.connect(host=self._host,
					    user=self._user,
					    db=self._dbname)
	    self._conn.autocommit(True)
	except MySQLdb.Error, e:
	    print "Problem connecting to Apollo database: %d %s"%(e.args[0],e.args[1])
            sys.exit(1)
	    
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
	    #print "Executiing " + SQLString
            self._cursor.execute(SQLString)
	    #self._conn.commit()
        except MySQLdb.Error, e:
            print "Error in Query %s to Apollo: %d: %s"%(SQLString,e.args[0],e.args[1])
            sys.exit(1)
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
    print str(apolloDB.getRuns())
    print "Closing connection to database"
    apolloDB.close()
    print "Closing successful"

    apolloOut = ApolloSimulatorOutput("UPitt,PSC,CMU_FRED_2.0.1_230619")
    apolloOut.getTimeSeriesForSimulatedPopulation("susceptible in 42003")
    #apolloOut.printSimulatedPopulations()

############
# Main hook
############

if __name__=="__main__":
    main()
	

    
