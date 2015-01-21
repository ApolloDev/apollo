# /usr/bin/env python
import os, sys, string
import math
import optparse
from StringIO import StringIO
import apollo
import time
import glob, hashlib
import datetime
import random

class MyError(Exception):
	def __init__(self, value):
		self.value = value
	def __str__(self):
		return repr(self.value)

def FREDAverageOuts(outDir):
	try:
		import glob
		import ntpath
		
		outputFileList = glob.glob("{0}/blockseday*.txt".format(outDir))
		if len(outputFileList) == 0:
			raise MyError("There are no files in the output directory : {0}".format(outDir))
		
		outputs = {}
		# fipsList = []  # # list to hold the fips codes contained in this output set
		simDays = -1
		outputsAve = {}
		outputKeyOrdered = None
		
		firstRun = True
		outCount = 0
		for outFile in outputFileList:
			outFileKey = ntpath.basename(outFile)[10:-4]
			
			outputs[outFileKey] = []
			with open(outFile, "rb") as f:
				dayCount = 0
				print outFile
				# ## Read the header
				header = f.readline().strip().split(",")
				if outputKeyOrdered is None:
					outputKeyOrdered = header
				else:
					if outputKeyOrdered != header:
						raise MyError("Inconsistent output files")

				try:
					dayIndex = outputKeyOrdered.index("Day")
					fipsIndex = outputKeyOrdered.index("BlockGroup")
				except:
					raise MyError("Day Index not found, file has incorrect format")
				
				for head in header:
					if head not in outputKeyOrdered:
						outputKeyOrdered.append(head)
				
				# # first find out how many days are in this file
				last_day = int(f.readlines()[-1].split(",")[dayIndex])
				f.seek(0)
				f.readline()
				if last_day > simDays:
					simDays = last_day
				# if len(outputsAve) < simDays:
				# 	for i in range(len(outputsAve),simDays):
				# 		outputsAve.append([])
				for line in f:
					# print line
					lineSplit = line.strip().split(",")
					block = lineSplit[fipsIndex]
					day = int(lineSplit[dayIndex])
					# print "{0} {1}".format(block,day)
					
					# ## remove the masked items
					lineSplit[fipsIndex] = -9999999
					lineSplit[dayIndex] = -9999999
					
					cleanSplit = filter(lambda a: a != -9999999, lineSplit)
					
					if not outputsAve.has_key(block):
						outputsAve[block] = [[0.0 for j in range(len(outputKeyOrdered) - 2)] for i in range(simDays + 1)]
					else:
						if len(outputsAve[block]) < simDays:
							for i in range(len(outputsAve[block]), simDays + 1):
								outputsAve[block].append([0.0 for j in range(len(outputKeyOrdered) - 2)])
					for i in range(len(cleanSplit)):
						outputsAve[block][day][i] += float(cleanSplit[i])
		
		numberOfRuns = float(len(outputFileList))
		for block in outputsAve.keys():
			for i in range(len(outputsAve[block])):
				for j in range(len(outputsAve[block][i])):
					outputsAve[block][i][j] /= numberOfRuns
# 		for block, values in outputsAve.items():
# 			for value in values:
# 				print "Block = {0},{2}: {1}".format(block, value, values.index(value))
		# clean outputKeys before sending
		print "Before: {0}".format(outputKeyOrdered)
		outputKeyOrdered = filter(lambda a: a != "Day" and a != "BlockGroup", outputKeyOrdered)
		print "After: {0}".format(outputKeyOrdered)
		return simDays, outputKeyOrdered, outputsAve
					
	except Exception as e:
		print "Error in fred_to_apollo::FREDAverageOuts {0}".format(e)

	
def statusUpdate(status, message):
	statusFile = './starttime'
	with open(statusFile, "wb") as f:
		f.write("%s %s" % (status, message))

def error_exit(message):
	# statusUpdate("LOGERROR",message)
	sys.stderr.write(message)
	sys.exit(1)
	
def main():
	parser = optparse.OptionParser(usage="""
	%prog [--help][-k key][-r run_number][-t vis_type]
	-k or --key   Fred Key
	-r or --run   The run number of vizualize
				  ave = produce an average result
				  all = produce an input for all runs
   
	-w or --time  Turn on profiling
	-d or --debug Turn on debug printing
	""")

	# parser.add_option("-k","--key",type="string",
# 			  help="The FRED key for the run you wish to visualize")
	parser.add_option("-o", "--outputdir", type="string", default="OUT")
	parser.add_option("-i", "--runId", type="string",
			  help="The Apollo RunId for this call")
	# parser.add_option("-r","--run",type="string",
	#				  help="The number of the run you would like to visualize (number,ave, or all)",
	#				  default=1)
	parser.add_option("-w", "--time", action="store_true",
					  default=False)
	parser.add_option("-d", "--debug", action="store_true",
					  default=False)
	parser.add_option("-H", "--dbHost", type="string", default="warhol-fred.psc.edu")
	parser.add_option("-D", "--dbName", type="string", default="test")
	parser.add_option("-U", "--dbUser", type="string", default="apolloext")
	parser.add_option("-P", "--dbPword", type="string")
	parser.add_option("-n", "--no_db", action="store_true", default=False)

	opts, args = parser.parse_args()

	DBHosts = [opts.dbHost]
	
	simulationRunId = opts.runId
	statusFile = "./starttime"

	if simulationRunId is None:
		error_exit("Need to specify an Apollo SimulationRunId to use this program")

	try:
		numberDays,outputKeys, averageOuts = FREDAverageOuts(opts.outputdir)
		#averageOutputs = FREDAverageOuts(opts.outputdir)
		#sys.exit()
		# numberDays = int(fred_run.get_param("days"))
		# outputsAve = fred_run.outputsAve
	except Exception as e:
		error_exit(str(e))

	try:
		apolloDBs = [ apollo.ApolloDB(host_=x, dbname_=opts.dbName, user_=opts.dbUser, password_=opts.dbPword) for x in DBHosts ]
		for apolloDB in apolloDBs:
			apolloDB.connect()
			for state,filename in apolloDB.stateToDataFileDict.items():
				sio=StringIO()
				csvsio = StringIO()
				csvsio.write("BlockGroup,Day,{0}".format(state))
				if state in outputKeys:
					for fips,days in averageOuts.items():
						for i in range(len(days)):
							#print "US{0} {1} {2}:1\n".format(fips,days[i][outputKeys.index(state)],i)
							sio.write("US{0} {1} {2}:1\n".format(fips,int(round(days[i][outputKeys.index(state)],0)),i))
							
				
				
				m = hashlib.md5()
				m.update(sio.getvalue())
				m5hash = m.hexdigest()
				runDataContentId = -1
				hashvar = apolloDB.checkMD5HashExistence(m5hash)
				if hashvar > -1:
					runDataContentId = hashvar
				else:
					SQLString = 'INSERT INTO run_data_content (text_content, md5_hash_of_content) values ("%s","%s")' % (sio.getvalue(), m5hash)
					if(opts.no_db is False):
						apolloDB.query(SQLString)
					runDataContentId = apolloDB.insertID()
				runDataDescriptionIdTS = apolloDB.getRunDataDescriptionId(label_=filename)

				SQLString = 'INSERT INTO run_data (run_id, description_id, content_id) values '\
					'("%s","%s","%s")' % (simulationRunId, runDataDescriptionIdTS, runDataContentId)
				
				if(opts.no_db is False):
					apolloDB.query(SQLString)
				
				if state == "C":
					runDataDescriptionIdGAIA = apolloDB.getRunDataDescriptionId(label_="newly_exposed.txt", destination_software_=5) 
					SQLString = "INSERT INTO run_data(run_Id,description_id,content_id) values ('%s','%d','%d')" % (simulationRunId, 
																													runDataDescriptionIdGAIA, 
																													runDataContentId)
					
					SQLString = "INSERT INTO run_data(run_Id,description_id,content_id) values ('%s','%d','%d')" % (simulationRunId, runDataDescriptionIdGAIA, runDataContentId)
					if(opts.no_db is False):
						apolloDB.query(SQLString)
					
				with open(filename,"wb") as f:
					f.write('{0}'.format(sio.getvalue()))
		
		
		apolloDB.close()
		statusUpdate("LOG_FILES_WRITTEN", "%s" % datetime.datetime.now().strftime("%a %b %d %H:%M:%S EDT %Y"))
		
	except Exception as e:
		error_exit(str(e))
		
if __name__ == '__main__':
	main()
			 
		
