import os, sys, string
# import simWS
import MySQLdb
import MySQLdb.cursors
from logger import Log

# ## STB To Do Make this variable from the SimWS file
# from SimulatorService_v2_0_2_types import *

_runTable = "run"
_populationAxisTable = "population_axis"
_simulatedPopulationTable = "simulated_population"
_simulatedPopulationAxisValueTable = "simulated_population_axis_value"
_timeSeriesTable = "time_series"
_softwareIdentificationTable = "software_identification"

class ApolloDB:
	
	def __init__(self, host_="warhol-fred.psc.edu",
		 user_="apolloext", dbname_="apollo201", password_=None, logger_=None):

		self._host = host_
		self._user = user_
		self._dbname = dbname_
		self._password = password_
		self._conn = None
		self._DictCursor = None
		self._RegularCursor = None
		self.populationAxis = None
		if logger_ is None:
			self.logger = Log("./db.test.log")
			self.logger.start()
		else:
			self.logger = logger_

		self.stateToPopulationDict = {'S':'susceptible', 'E':'exposed', 'I':'infectious',
									  'R':'recovered', 'C':'newly exposed', 'V':'received vaccine control measure',
									  'Av':'received antiviral control measure', 'ScCl':'school that is closed'}

		self.stateToDataFileDict = {'S':'susceptible.txt', 'E':'exposed.txt', 'I':'infectious.txt',
									'R':'recovered.txt', 'C':'newly_exposed.txt', 'V':'vacc_administered.txt',
									'Av':'av_administered.txt'}
	def connect(self):
		if self._conn is not None:
			print "Connection to Apollo Database has already been established"
			return
		try:
			if self._password is None:
				self._conn = MySQLdb.connect(host=self._host, user=self._user, db=self._dbname)
			else:
				self._conn = MySQLdb.connect(host=self._host,
											 user=self._user,
											 passwd=self._password,
											 db=self._dbname)

			self._conn.autocommit(True)
			self.logger.update('DB_CONNECT_SUCCESS')
		except MySQLdb.Error, e:
			print "Problem connecting to Apollo database: %d %s" % (e.args[0], e.args[1])
			self.logger.update('DB_CONNECT_FAILED')
			raise e
			

		self._cursor = self._conn.cursor(MySQLdb.cursors.DictCursor)
		self.populationAxis = self._populationAxis()
		
	def close(self):
		if self._conn is None:
			raise RuntimeError("A connection to the Apollo database"\
							   "has not been made before close is made")

		self._conn.close()

	def query(self, SQLString):
		if self._conn is None:
			raise RuntimeError("A connection to the Apollo database "\
							   "has not been made before queury is made")
	
		try:
			self._cursor.execute(SQLString)
		except Exception as e:
			self.logger.update('DB_QUERY_FAILED', message="%s,%s" % (str(e), SQLString))
			raise
		
		rows = self._cursor.fetchall()
		return rows

	def insertID(self):
		return self._conn.insert_id()

	def _populationAxis(self):
		populationAxisDict = {}
		SQLStatement = "SELECT * from %s" % _populationAxisTable

		result = self.query(SQLStatement)

		for row in result:
			populationAxisDict[row["label"]] = row["id"]

		return populationAxisDict

	def getRuns(self):
		runList = []
		SQLStatement = "SELECT * from %s" % _runTable
		result = self.query(SQLStatement)
		for row in result:
			runList.append(row['label'])
		return runList

	def getTranslatorServiceKey(self):
		SQLStatement = 'SELECT * from %s where service_type = "translator"' % (_softwareIdentificationTable)
		result = self.query(SQLStatement)
		row = result[0]
		return row['id']
	
	def getSoftwareIdentificationId(self, name_="SEIR", version_="3.0"):
		SQLStatement = 'SELECT id from %s where name = "%s" and version = "%s"' % (_softwareIdentificationTable, name_, version_)
		result = self.query(SQLStatement)
		if len(result) == 0:
			self.logger.update('DB_QUERY_NO_RESULTS', message=SQLStatement)
			return -1
		return result[0]['id']
	
	
	
	
	def getSoftwareIdentificationForRunId(self, runId):
		SQLStatement = 'SELECT t1.name,t1.developer,t1.version from software_identification t1, '\
					   + 'run t2 where t1.id = t2.software_id and t2.id = %s' % str(runId)

		result = self.query(SQLStatement)
		if len(result) == 0:
			self.logger.update('DB_QUERY_NO_RESULTS', message=SQLStatement)
			return ('unknown', 'Could not find runId: %s in the ApolloDB')
		if len(result) > 1:
			self.logger.update('DB_QUERY_MORE_THAN_ONE', messsage=SQLStatement)
			raise

		return (result[0]['name'], result[0]['developer'], result[0]['version'])
	
	def getSimulatorServiceKey(self, developer, name, version):
		SQLStatement = 'SELECT * from %s where developer = "%s" and name = "%s"  and version = "%s"'\
			% (_softwareIdentificationTable, developer, name, version)
		result = self.query(SQLStatement)
		if len(result) == 0:
			self.logger.update('DB_QUERY_NO_RESULTS', message=SQLStatement)
			raise
		
		return result[0]['id']
	
	def getRunDataDescriptionId(self, label_, source_software_=3, destination_software_=4, format_="TEXT", type_="SIMULATOR_LOG_FILE"):
		try:
			SQLStatement = 'SELECT v.run_data_description_id FROM run_data_description_view v WHERE '\
				+ 'v.format = "%s" AND v.label = "%s" and v.type = "%s" and v.source_software = %s ' % (format_, label_, type_, str(source_software_))\
				+ 'and v.destination_software = %s' % (str(destination_software_))
				
			result = self.query(SQLStatement)
			if len(result) > 1:
				raise RuntimeError("getRunDataDescription returned more than one entry " + \
									   "for (%s,%d,%d,%s,%s)" % (label_, source_software_, destination_software_, format_, type_))
			
			return int(result[0]["run_data_description_id"])
		except Exception as e:
			print str(e)
			raise e
		
	def getRunDataContentFromRunIdAndLabel(self, simulationRunId_, label_, source_software_=3,
										   destination_software_=4, format_="TEXT", type_="SIMULATOR_LOG_FILE"):
		
		try:
			runDataDescriptionId = self.getRunDataDescriptionId(label_, source_software_, destination_software_, format_, type_)
			
			# ## First get the content id
			SQLStatement = 'SELECT content_id from run_data where run_Id = "%s" and description_Id = "%s"' % (simulationRunId_, runDataDescriptionId)
			result = self.query(SQLStatement)
			
			if len(result) > 1:
				raise RuntimeError('getRunDataContentFromRunIdAndLabel returned more than one entry '\
									   + 'for the content_id for (%s,%s)' % (simulationRunId_, label_))
			if len(result) == 0:
				return ""
			
			contentId = result[0]['content_id']
			
			# ## Now get the file content
			SQLStatement = 'SELECT text_content from run_data_content where id = "%s"' % str(contentId)

			result = self.query(SQLStatement)

			if len(result) == 0:
				return ""

			return result[0]['text_content']

		except Exception as e:
			raise e

	def checkMD5HashExistence(self, md5hash):
		try:
			
			SQLStatement = 'SELECT * from run_data_content where md5_hash_of_content = "%s"' % (md5hash)
			result = self.query(SQLStatement)
			if len(result) > 0:
				return result[0]['id']
			else:
				return -1
		
		except Exception as e:
			raise e

	def getSimulatorIdFromRunId(self, simulationRunId_):
		try:
			SQLStatement = 'SELECT software_id from run where id = "%s"' % str(simulationRunId_)
			result = self.query(SQLStatement)
			if len(result) > 1:
				raise RuntimeError("getSimulatorIdFromRunId: more than one run with simulationRunId %s" % str(simulationRunId_))
			return result[0]["software_id"]
		except Exception as e:
			raise e

	
	def getSimulationInputFilesForRunId(self, runId, translatorKey, simulatorKey):
		SQLStatement = 'SELECT rddv.label, rdc.text_content FROM run_data_content rdc, run_data rd, '\
					   + 'run_data_description_view rddv WHERE rd.content_id = rdc.id AND rd.run_id = %s ' % str(runId)\
					   + 'AND rddv.run_data_description_id = rd.description_id AND rddv.source_software = %s ' % str(translatorKey)\
					   + 'AND rddv.destination_software = %s' % str(simulatorKey)
		
		result = self.query(SQLStatement)
		if len(result) == 0:
			self.logger.update('DB_QUERY_NO_RESULTS', message=SQLStatement)
			return ('unknown', 'Could not find runId: %s in the ApolloDB' % str(runId))

		fileDict = {}
		for row in result:
			fileDict[row['label']] = row['text_content']

		return fileDict
		
	def getRunStatus(self, runId):
		SQLStatement = 'SELECT t2.status,t1.message from run_status t1, run_status_description t2 '\
					   + 'where t1.run_id = %s and t2.id = t1.status_id' % str(runId)
		result = self.query(SQLStatement)
		if len(result) == 0:
			self.logger.update('DB_QUERY_NO_RESULTS', message=SQLStatement)
			return ('unknown', 'Could not find runId: %s in the ApolloDB' % str(runId))

		if len(result) > 1:
			self.logger.update('DB_QUERY_MORE_THAN_ONE', messsage=SQLStatement)
			return ('failed', 'More than in entry for runID: %s in the ApolloDB' % str(runId))

		return (result[0]['status'], result[0]['message'])

	def getAllowedStatus(self):
		SQLStatement = "SELECT status from run_status_description"
		result = self.query(SQLStatement)
		return [x['status'] for x in result]
	
	def setRunStatus(self, runId, status, message=""):
		if status not in self.getAllowedStatus():
			self.logger.update('DB_STATUS_ERROR', message="Set Status: Status %s not allowed" % str(status))
			return -1

		thisStatus, thisMes = self.getRunStatus(runId)
		if thisStatus == "unknown":
			SQLStatement = ' INSERT into run_status (run_id,status_id,message) values '\
						 + '("%s",' % str(runId)\
						 + '(SELECT id from run_status_description where status = "%s"),' % status \
				 + '"%s") ' % message  
			self.query(SQLStatement)
		else:
			SQLStatement = 'UPDATE run_status set status_id = '\
						   + '(SELECT id from run_status_description where status = "%s" ),' % str(status)\
						   + 'message = "%s"  where run_id = %s' % (str(message), str(runId))
			
			result = self.query(SQLStatement)
		return 0
		
def main():

	apolloDB = ApolloDB(dbname_="test")
	print "Testing connection to Apollo database"
	apolloDB.connect()
	print "Connection to Database successful"
	# runList = apolloDB.getRuns()
	# print "Keys: Translator %d FRED Simulator %d"%\
	# 	  (apolloDB.getTranslatorServiceKey(),\
	# 	   apolloDB.getSimulatorServiceKey('UPitt,PSC,CMU','FRED','2.0.1_i'))
	# fileDict = apolloDB.getSimulationInputFilesForRunId('74',1,3)
	# for fileName,content in fileDict.items():
	# 	print "%s"%fileName
	print str(apolloDB.getAllowedStatus())
	print str(apolloDB.getRunStatus(2418))
	print str(apolloDB.getSoftwareIdentificationForRunId(14))
	apolloDB.setRunStatus(2418, "failed", "my Message")
	print str(apolloDB.getRunStatus(2418))
	apolloDB.setRunStatus(2418, "completed", "Completed at Thu, 10 Jul 2014 14:48:12 -0400")
	print str(apolloDB.getRunStatus(14))
			  
	print "Closing connection to database"
	apolloDB.close()
	print "Closing successful"

	# print "RunList 0: " + runList[0]
	# apolloOut = ApolloSimulatorOutput(runList[0])
	# apolloOut.getNewlyInfectedTimeSeriesWithinLocation("42003")
	# apolloOut.printSimulatedPopulations()

############
# Main hook
############

if __name__ == "__main__":
	main()

	
