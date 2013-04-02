# Copyright 2012 University of Pittsburgh
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may not
# use this file except in compliance with the License.  You may obtain a copy of
# the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
# License for the specific language governing permissions and limitations under
# the License.


'''
Created on Feb, 8, 2013

@author: Shawn Brown
'''
import os,sys
import paramiko
import datetime,dateutil.parser
import time
import math
from threading import Thread
from ApolloUtils import *

class FredSSHConn:
    def __init__(self,debug=False):
	self.machine = None
	self.username = None
	self.password = None
	self.privateKeyFile = None
	self._ssh = None
	self._transport = None
	self._sftp = None
	self.isConnectedSSH = False
	self.isConnectedSFTP = False
	self.name = None
	self.debug = debug
	self.runPBS = False
	self.getScratch = False
	self.remoteDir = None
	self.statusFileDir = None
	self.statusFile = None
	self.remoteTmpDir = None
	self.runScript = None
	self.runScriptName = None

    def _setup(self,configurationFile_="./apollofred.conf"):
	machine = None
	username = None
	password = ''
	pvKF = None
	with open(configurationFile_,"rb") as f:
	    for line in f:
		lineSplit = line.split()

		if lineSplit[0] == "machine":
		    machine = str(lineSplit[1])
		elif lineSplit[0] == "username":
		    username = str(lineSplit[1])
		elif lineSplit[0] == "password":
		    password = str(lineSplit[1])
		elif lineSplit[0] == "privateKeyFile":
		    pvKF = str(lineSplit[1])
		else:
		    raise RuntimeError("Unrecognized key %s in configuration file %s"\
				       %(lineSplit[0],configurationFile_))
	self._setupExplicit(machine=machine,username=username,password=password,privateKeyFile=pvKF)
	
    def _setupExplicit(self,machine="blacklight.psc.xsede.org",username='stbrown',
		       password='',privateKeyFile=None):
	self.machine = machine
	self.username = username
	self.password = password
	self.privateKeyFile = privateKeyFile

	if self.password == '' and privateKeyFile is None:
	    raise RuntimeError("Need to specifiy a privateKeyFile for authentication with no password")

      	self.isConnectedSSH = False
	self.isConnectedSFTP = False
	self.name = self.username + "@" + str(machine)

	### Allows us to specify machine specific stuff
	self.runPBS = False
	self.getScratch = False
	self.remoteDir = None
	self.pbsWorkDir = None
	self.pbsSubmit = "qsub"
	self.pbsQstat = "qstat"
	
	if self.machine == "blacklight.psc.xsede.org":
	    self.getScratch = True
	    self.runPBS = True
	    self.pbsSubmit = "module load torque; qsub"
	elif self.machine == "warhol.psc.edu":
	    self.getScratch = True
	    self.runPBS = True
	    self.pbsSubmit = "qsub"
	elif self.machine == "unicron.psc.edu":
	    self.remoteDir = "/media/scratch"
	    #self.statusFileDir = "/scratch/stbrown"
	    self.statusFileDir = "./"
	elif self.machine == "localhost":
	    self.remoteDir = "C:\Users\Shawn\Simulations\temp"
	    self.statusFileDir = "./"
	    
    def _connect(self):
	### open SSH connection
	self.ssh = paramiko.SSHClient()
	self.ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
	print self.machine
	print self.username
	print self.password

	if self.debug: print 'Establishing SSH connection %s'%(self.name)
	self.ssh.connect(self.machine,
			 username=self.username,
			 password=self.password,
			 key_filename=self.privateKeyFile)

	self.isConnectedSSH = True

	if self.debug: print 'SSH connection %s established'%(self.name)
	
	### open SFTP connection
	if self.debug: print 'Establishing SFTP connection %s'%(self.name)
	    
	self.transport = paramiko.Transport((self.machine,22))
	if self.privateKeyFile:
	    privateKey = paramiko.RSAKey.from_private_key_file(self.privateKeyFile)
	    self.transport.connect(username=self.username,
				   pkey = privateKey)
	else:
	    self.transport.connect(username=self.username,
				   password=self.password)

	self.sftp = paramiko.SFTPClient.from_transport(self.transport)
	self.isConnectedSFTP = True

	if self.debug: print 'SFTP connection %s established'%self.name
	
	if self.getScratch:
	    if self.debug: print 'On connection %s getting scratch directory'%self.name

	    self.remoteDir = self._getScratchDir()

	    if self.debug: print 'Got %s as scratch directory on connection %s'%(self.remoteDir,self.name)
    
    
    def _close(self):
	self.ssh.close()
	self.isConnectedSSH = False
	self.sftp.close()
	self.transport.close()
	self.isConnectedSFTP = False
    
    def _mkdir(self,remoteDirectoryName):
	if self.debug: print "Making %s directory on %s"%(remoteDirectoryName,self.name)
	
	if self.isConnectedSFTP is False:
	    raise RuntimeError("Trying to create a directory through %s before opening the SFTP connection"\
			       %self.name)
	if self.remoteDir is not None:
	    remoteDirectoryName = self.remoteDir + "/" + remoteDirectoryName

	if self.debug: print "Parsed remote directory is %s"%remoteDirectoryName
	
	try:
	    self.sftp.mkdir(remoteDirectoryName)
	except:
	    raise RuntimeError("Error creating directory %s on connection %s"\
			       %(remoteDirectoryName,self.name))
    
    def _sendFile(self,localFileName,remoteFileName=None):

	if remoteFileName is None:
	    remoteFileName = localFileName

	if self.remoteDir is not None:
	    remoteFileName = self.remoteDir + "/" + remoteFileName
	
	if self.isConnectedSFTP is False:
	    raise RuntimeError("Trying to send a file through %s before opening the SFTP connection"\
			       %self.name)
	
	try:
	    self.sftp.put(localFileName,remoteFileName)
	except:
	    raise RuntimeError("Error sending local file %s to remote file %s on connection %s"\
			       %(localFileName,remoteFileName,self.name))
	
    def _executeCommand(self,command):
	if self.isConnectedSSH is False:
	    raise RuntimeError("Attempting to execute a command on %s before establishing a connections"\
			       %self.name)
	stdin=None
	stdout=None
	stderr=None
	try:
	    stdin,stdout,stderr = self.ssh.exec_command(command)
	except:
	    print "There was an error exectuting the command on connections %s:"%self.name
	    print "%s"%command
	    print "stderr returned: " 
	    for line in stderr:
		print line
	    raise RuntimeError('Error with executing "%s" over %s'%(command,self.name))

	return stdout

    def _getScratchDir(self):
	remote_command = 'echo $SCRATCH'
	returnVal = self._executeCommand(remote_command)
	scratchString = ""
	try:
	    scratchString = returnVal.read().strip()
	except:
	    raise RuntimeError("Error in getting SCRATCH directory through connection %s"%self.name)
	return scratchString

    def _submitJob(self,tmpId):
	idString = "BAD"
	if self.runPBS:
	    idString = self._submitPBSJob(tmpId)
	else:
	    ### Generate the runID here and pass it into submit Job for threading
	    timeStamp = int(time.time())
	    idSuffix = self.machine + str(timeStamp)
	    t = Thread(target=self._submitDirectJob,args=(tmpId,idSuffix,))
	    t.start()
	    idString = idSuffix

	if idString == "BAD":
	    raise RuntimeError("There was a problem submitting the job through connection %s"%self.name)

	return idString
	
    def _submitDirectJob(self,tmpId,idSuffix):

	### set and update the status file
	self.statusFile = self.statusFileDir +"/.status_"+idSuffix
	self.remoteTmpDir = self.remoteDir + '/fred.tmp.' + str(tmpId)
	
	with open(self.statusFile,"wb") as f:
	    f.write("Running")
	remote_command = ""
	if self.remoteDir is not None:
	    remote_command += 'cd ' + self.remoteTmpDir + ';' 
	else:
	    remote_command += 'cd fred.tmp.'+str(tmpId) + ';'

	remote_command += 'setenv id ' + idSuffix + '; '
	remote_command += 'chmod a+x ./fred_run.csh; ./fred_run.csh'
	print remote_command
	retVal = self._executeCommand(remote_command)
	tempVal = retVal.readlines() ### Need this to block for me
	#print "Should not be here right away"
	with open(self.statusFile,"wb") as f:
	    f.write("Completed")
		
    def _submitPBSJob(self,tmpId):
	remote_command = ""
	if self.remoteDir is not None:
	    remote_command += 'cd ' + self.remoteDir + '/fred.tmp.' + str(tmpId) + '; '
	else:
	    remote_command += 'cd fred.tmp.'+str(tmpId) + '; '

	remote_command += self.pbsSubmit + " fred_submit.pbs"

	returnVal = self._executeCommand(remote_command)

	idString = ""
	try:
	    idString = returnVal.read().split('.')[0]
	except:
	    raise RuntimeError("Error in getting PBS ID through connection %s"%self.name)

	### Set PBSPBS directory
	self.pbsWorkDir = self.remoteDir + '/fred.tmp.' + str(tmpId)

	return idString
		
    def _getPBSQueueStatus(self,pbsID):
	remote_command = self.pbsQstat + " " + str(pbsID)
	pbsStatus = "U"
	
	unkFlag = False
	returnVal = None
	try:    
	    returnVal = self._executeCommand(remote_command)
	    statusList = returnVal.readlines()
	    pbsString = statusList[len(statusList)-1]
	    pbsStatus = pbsString.split()[4]
	except:
	    unkFlag = True
	    
	return pbsStatus
	
    def _getFredStatus(self,key):
	remote_command = "module load fred; fred_status -k " + str(key)

	status = "UNKNOWN"
	percent = "00"
	secondsRunning = 0
	date = ""

	
	returnVal = self._executeCommand(remote_command)
	returnString = returnVal.read()
	print "ReturnString = " + returnString 
	returnSplit = returnString.split()
	if len(returnSplit) > 1:
	    status = returnSplit[0]
	else:
	    status = "UNKNOWN"
	    date = str(datetime.datetime.now())
	    
	if status[:7] == "RUNNING":
	    percent = status.split('-')[1]
	    status = status.split('-')[0]
	    if self.pbsWorkDir:
		remoteCommand = "tail -1 " + self.pbsWorkDir + "/starttime"
		retTVal = self._executeCommand(remoteCommand).read()
		print "Return from FRED: = " + str(retTVal)
		if retTVal.strip() == "Nothing":
		    print "starttime nothing"
		    secondsRunning = -1.0
		else:
		    timeValTZ = dateutil.parser.parse(retTVal)
		    timeVal = timeValTZ.replace(tzinfo=None)
		    
		    now = datetime.datetime.now()
		    print "Time Val = " + str(timeVal) + " " + str(now)
		    secondsRunning = (now-timeVal).seconds
	    date = " ".join(returnSplit[2:5])
	    print "Date = " + date
	elif status == "FINISHED":
	    if self.pbsWorkDir:
                remoteCommand = "tail -1 " + self.pbsWorkDir + "/starttime"
                retTVal = self._executeCommand(remoteCommand).read()
                print "Return from FRED: = " + str(retTVal)
                if retTVal.strip() == "Nothing":
                    print "starttime nothing"
                    secondsRunning = -1.0
                else:
                    timeValTZ = dateutil.parser.parse(retTVal)
                    timeVal = timeValTZ.replace(tzinfo=None)

                    now = datetime.datetime.now()
                    print "Time Val = " + str(timeVal) + " " + str(now)
                    secondsRunning = (now-timeVal).seconds
		date = " ".join(returnSplit[2:5])
	elif status == "NOT":
	    secondsRunning = 0
	    status = "UNKNOWN"
	    
	return (status,date,secondsRunning)
	
    def _getStatus(self,key):
	pbsStatus = None
	status = "UNKNOWN"
	response = "Empty Response"
	if self.runPBS:
	    pbsSplit = str(key).split("_")
	    pbsID = pbsSplit[len(pbsSplit)-1]
	    pbsStatus = self._getPBSQueueStatus(pbsID)
	    print "PBS Status = " + str(pbsStatus)
	    if pbsStatus == "U":
		status = "COMPLETED"
		statTuple = self._getFredStatus(key)
		response = 'The run completed successfully at ' + statTuple[1]
	    elif pbsStatus == "Q":
		status = "QUEUED"
		response = 'The run is queued on ' + self.machine +\
			   ' at ' + datetime.datetime.now().strftime("%Y-%m-%d %H:%M")
	    elif pbsStatus == "R":
		status = "RUNNING"
		statTuple = self._getFredStatus(key)
		if statTuple[0] == "UNKNOWN":
		    response = "The run has started on %s at %s"\
			       %(self.machine,statTuple[1])
		else:
		    if statTuple[2] == -1.0:
			response = "The run has started on %s at %s"\
				   %(self.machine,statTuple[1])
		    else:
			response = "The run has been running for %g seconds on %s at %s"\
				   %(statTuple[2],self.machine,statTuple[1])
	    else:
		status = "UNKNOWN"
		response = "getStatus on connection %s returned an unknown response"\
			   %self.name		
		
	    return (status,response)
	else:
	    ### This is a direct Run
	    if self.statusFile is None:
		status = "Problem1"
		response = "get status on connection %s has run into a problem"\
			   +"polling a statusfile before it is set"%self.name
		return (status,response)
		
	    try:
		open(self.statusFile,"rb")
	    except IOError:
		status = "Problem2"
		response = "get status on connection %s cannot open file %s to check status"\
			   %(self.name,self.statusFile)
		return (status,response)

	    with open(self.statusFile,"rb") as f:
		status = f.readline()
	    print "Direct Status = %s"%status
	    if status == "Running":
		if self.remoteTmpDir:
		    remoteCommand = "tail -1 " + self.remoteTmpDir + "/starttime"
		else:
		    status = "Problem3"
		    response = "get status cannot find the remote temp directory %s on connection %s"\
			       %(self.remoteTmpDir,self.name)
		    return (status,response)
		    
		retTVal = self._executeCommand(remoteCommand).read()
		if retTVal.strip() == "Nothing":
		    print "starttime nothing"
		    secondsRunning = -1.0
		else:
		    timeValTZ = dateutil.parser.parse(retTVal)
		    timeVal = timeValTZ.replace(tzinfo=None)
		    
		    now = datetime.datetime.now()
		    print "Time Val = " + str(timeVal) + " " + str(now)
		    secondsRunning = (now-timeVal).seconds
		    
		    response = "The run has been running of %g seconds on %s at %s"\
			       %(secondsRunning,self.machine,datetime.datetime.now().strftime("%Y-%m-%d %H:%M"))
		    
		    
		    return (status,response)

	    elif status == "Completed":
		response = "The run has completed on %s at %s"\
			   %(self.machine,datetime.datetime.now().strftime("%Y-%m-%d %H:%M"))
		
		return (status,response)

	return (status,response)

    def createRunScript(self,Id_):
	if self.runPBS is True:
	    self.runScriptName = "fred_submit.pbs"
	    self.runScript = self.createPBSRunScript(Id_)
	else:
	    self.runScriptName = "fred_run.csh"
	    self.runScript = self.createLocalRunScript(Id_)

    def writeRunScript(self,Id_):
	self.createRunScript(Id_)
	with open(self.runScriptName,"wb") as f:
	    f.write("%s"%self.runScript)
    
    def createPBSRunScript(self,Id_):
	PBSList = []
	PBSList.append('#!/bin/csh\n')
	if self.machine == "blacklight.psc.xsede.org":
	    PBSList.append('#PBS -l ncpus=16\n')
	else:
	    PBSList.append('#PBS -l nodes=2:ppn=8\n')
	PBSList.append('#PBS -N fred.pbs.out\n')
	PBSList.append('#PBS -l walltime=30:00\n')
	PBSList.append('#PBS -j oe\n')
	if self.machine == "blacklight.psc.xsede.org":
	    PBSList.append('#PBS -q debug\n')
	PBSList.append('\n')
	if self.machine == "blacklight.psc.xsede.org":
	    PBSList.append('source /usr/share/modules/init/csh\n')
	    PBSList.append('source /etc/csh.cshrc.psc\n')
	PBSList.append('module load fred\n')
	PBSList.append('module load python\n')
	PBSList.append('cd $PBS_O_WORKDIR\n')
	PBSList.append('echo `date` > starttime\n')
	PBSList.append('### Get the PBS ID\n')
	PBSList.append("set words = `echo $PBS_JOBID | sed 's/\./ /g'`\n")
	PBSList.append("set id = $words[1]\n")
	PBSList.append('fred_job -p fred_input.params -n 8 -t 2 -m 8 -k %s$id\n'%Id_)
	PBSList.append('touch .dbloading\n')
	PBSList.append('python $FRED_HOME/bin/fred_to_apollo_parallel.py -k %s$id\n'%(Id_))
	PBSList.append('rm -rf .dbloading\n')
	#PBSList.append('echo COMPLETED > job_status')
	return ("").join(PBSList)

    def createLocalRunScript(self,Id_):
	LocalList = []
	LocalList.append('#!/bin/csh\n')
	LocalList.append('while ( -e /tmp/.lock1 && -e /tmp/.lock2)\n')
	LocalList.append('sleep 1\n')
	LocalList.append('end\n')

	LocalList.append('set lockfile = ""\n')
	LocalList.append('if( ! -e /tmp/.lock1 ) then\n')
	LocalList.append('   touch /tmp/.lock1\n')
	LocalList.append('   set lockfile = /tmp/.lock1\n')
	LocalList.append('else if ( ! -e /tmp.lock2 ) then\n')
	LocalList.append('   touch /tmp/.lock2\n')
	LocalList.append('  set lockfile = /tmp.lock2\n')
	LocalList.append('endif\n')

	LocalList.append('echo `date` > starttime\n')
	LocalList.append('### Generate Timestamp for ID\n')
	LocalList.append('fred_job -p fred_input.params -n 4 -t 2 -m 4 -k %s$id > out.run\n'%Id_)
	LocalList.append('touch .dbloading\n')
	LocalList.append('python $FRED_HOME/bin/fred_to_apollo_parallel.py -k %s$id >& out.db\n'%Id_)
	LocalList.append('rm -rf $lockfile\n')
	LocalList.append('rm -rf .dbloading\n')

	return ('').join(LocalList)
	
def infectiousPeriod(newAverage=4.1,gamma=2.378084,
		     lamb=2.405191,shift=2.0,thresh=0.05):
    pdfList = []
    cdfList = []
    thisAverage = 4.4 # days This is the mean of the Weibull distrubtion
    aveShift = newAverage-thisAverage
    for i in range(0,20):
	pdfList.append(ShiftedWeibull(float(i),gamma,lamb,shift+aveShift))

    #print(str(pdfList))

    sum = 0.0
    for i in range(0,len(pdfList)):
	sum += pdfList[i]
	if sum+thresh >= 1.0:
	    cdfList.append(1.0)
	    break
	else:
	    cdfList.append(sum)

    cdfString = " %d"%len(cdfList)
    for x in cdfList:
	cdfString += " %1.2f"%x

    return cdfString

def incubationPeriod(newAverage=1.9,gamma=2.2126752,
		     lamb=1.67,shift=0.5,thresh=0.05):
    pdfList = []
    cdfList = []
    thisAverage = 1.984 # days This is the mean of the fitted Weibull distrubiton
    aveShift = newAverage-thisAverage
    for i in range(0,10):
	pdfList.append(ShiftedWeibull(float(i),gamma,lamb,shift+aveShift))

    sum = 0.0
    for i in range(0,len(pdfList)):
	sum += pdfList[i]
	if sum+thresh >= 1.0:
	    cdfList.append(1.0)
	    break
	else:
	    cdfList.append(sum)
	    
    cdfString = " %d"%len(cdfList)
    for x in cdfList:
	cdfString += " %1.2f"%x

    return cdfString
def latentPeriod(newAverage=1.9,gamma=2.2126752,lamb=1.67,shift=0.5):
    incubationPeriod(newAverage,gamma,lamb,shift)

def ShiftedWeibull(x,gamma,lamb,shift=0.0):
    xShift = x-shift
    if xShift <= 0.0:
	return 0
    else:
	return ((gamma/lamb)\
		*math.pow((xShift/lamb),(gamma-1.0))\
		*math.exp(-pow((xShift/lamb),gamma)))
class FredInputFileSet:
    def __init__(self,cfg_,inpDir_,fipsCountDict_,srvc_=None):
	self.fileList = []
	self.cfg = cfg_
	self.inpDir = inpDir_
	self.srvc = srvc_
	self.fipsCountDict = fipsCountDict_
	self.initParamsStarted = False
	self.fileNames = {'paramsFile':'fred_input.params',
			  'seedFile':'fred_initial_population_0.txt',
			  'vaccCapFile':'fred-vaccination-schedule_0.txt'}
	#self.create_param()
	#self.parse_control_measures()

    def createParam(self):
	apolloUtils = ApolloUtils()
	### Start the params file
	os.chdir(self.inpDir)
	#get the number of time steps in the simulation
	run_length = self.cfg._simulatorTimeSpecification._runLength
	totPop = float(self.fipsCountDict[self.cfg._populationInitialization._populationLocation])
	fractionRecovered = float(apolloUtils.getPopFractionGivenLocationAndDiseaseState(self.cfg, "ignoreed", "recovered"))	
	fractionExposed = float(apolloUtils.getPopFractionGivenLocationAndDiseaseState(self.cfg,"ingnoreed","exposed"))
	fractionInfectious = float(apolloUtils.getPopFractionGivenLocationAndDiseaseState(self.cfg,"ingnoreed","infectious"))
	#print "fractions:  %g %g %g"%(fractionRecovered,fractionExposed,fractionInfectious)
	
	
	with open(self.fileNames['paramsFile'],"wb") as f:
	    self.fileList.append(self.fileNames['paramsFile'])
	    f.write('#FRED PARAM FILE\n')
	    f.write('days = %d\n'%self.cfg._simulatorTimeSpecification._runLength)
	    f.write('symp[0] = %g\n'%self.cfg._disease._asymptomaticInfectionFraction)
	    f.write('R0 = %g\n'%self.cfg._disease._reproductionNumber)
	    f.write('primary_cases_file[0] = fred_initial_population_0.txt\n')
	    f.write('residual_immunity_ages[0] = 2 0 100\n')
	    f.write('fips = %s\n'%self.cfg._populationInitialization._populationLocation)
	    f.write('days_latent[0] = %s\n'\
		    %(incubationPeriod(float(self.cfg._disease._latentPeriod))))
	    f.write('days_infectious[0] = %s\n'\
		    %(infectiousPeriod(float(self.cfg._disease._infectiousPeriod))))
	    f.write('days_symptomatic[0] = %s\n'\
		    %(infectiousPeriod(float(self.cfg._disease._infectiousPeriod))))	     	       
	    f.write('residual_immunity_values[0] = 1 %g\n'%(fractionRecovered))
	    
	    numExposed = float(fractionExposed * totPop)
	    numInfectious = float(fractionInfectious * totPop)
	    ratioExposed = numExposed/(numExposed+numInfectious)
	    ratioInfectious = 1.0 - ratioExposed
	    f.write('advanced_seeding = exposed:%1.2f;infectious:%1.2f\n'%(ratioExposed,ratioInfectious))
	    f.write('track_infection_events = 1\n')

	with open(self.fileNames['seedFile'],"wb") as f:
	    self.fileList.append(self.fileNames['seedFile'])
	    f.write('#line_format\n')
	    numExposed = int(totPop*fractionExposed)
	    numInfectious = int(totPop*fractionInfectious)
	    #f.write('0 0 %d\n'%(cfg._disease_dynamics._pop_count[2]))
	    f.write('0 0 %d\n'%(numExposed + numInfectious))
	self.initParamsStarted = True
	os.chdir('..')

    def parseControlMeasures(self):
	
	### Parse the fixed control measures first
	for controlMeasure in self.cfg._controlMeasures._fixedStartTimeControlMeasures:
	    print "Control Dir = " + str(dir(controlMeasure._controlMeasure))
	    if hasattr(controlMeasure._controlMeasure,'_vaccination') is True:
		self.parseFixedVaccinationControlMeasure(controlMeasure)
	    elif hasattr(controlMeasure._controlMeasure,'_schoolClosureDuration') is True:
		self.parseFixedSchoolClosureControlMeasure(controlMeasure)
	    elif hasattr(controlMeasure._controlMeasure,'_antiviralTreatment') is True:
		self.parseFixedAntiviralControlMeasure(controlMeasure)
	for controlMeasure in self.cfg._controlMeasures._reactiveControlMeasures:
	    print dir(controlMeasure._controlMeasure)
	    if hasattr(controlMeasure._controlMeasure,'_schoolClosureDuration') is True:
		self.parseReactiveSchoolClosureControlMeasure(controlMeasure)

    def parseReactiveSchoolClosureControlMeasure(self,controlMeasure):
	if self.initParamsStarted is False:
	    raise RuntimeError('Calling parseReativeSchoolClosureControlMeasure before'+\
			       'initial parameters file has been made')
	os.chdir(self.inpDir)
	with open(self.fileNames['paramsFile'],'ab') as f:
	    if controlMeasure._controlMeasure._schoolClosureTargetFacilities == "all":
		f.write('school_closure_policy = global\n')
	    else:
		f.write('school_closure_policy = reactive\n')
	    f.write('school_closure_period = %d\n'%controlMeasure._controlMeasure._schoolClosureDuration)
	    f.write('school_closure_delay = %d\n'%controlMeasure._controlMeasure._controlMeasureResponseDelay)
	    f.write('school_closure_threshold = %g\n'%controlMeasure._controlMeasureReactiveTriggersDefinition._reactiveControlMeasureThreshold)

	os.chdir('..')
		    
    def parseFixedSchoolClosureControlMeasure(self,controlMeasure):
	if self.initParamsStarted is False:
	    raise RuntimeError('Calling parseFixedSchoolClosureControlMeasure before'+\
			       'initial parameters file has been made')
	os.chdir(self.inpDir)
	with open(self.fileNames['paramsFile'],'ab') as f:
	    f.write("school_closure_policy = global\n")
	    f.write("school_closure_period = %d\n"%controlMeasure._controlMeasure._schoolClosureDuration)
	    f.write("school_closure_delay = %d\n"%controlMeasure._controlMeasure._controlMeasureResponseDelay)
	    f.write("school_closure_day = %d\n"%controlMeasure._controlMeasureFixedStartTime._fixedStartTime)
	os.chdir('..')

    def parseFixedAntiviralControlMeasure(self,controlMeasure):
	if self.initParamsStarted is False:
	    raise RuntimeError('Calling parseFixedSchoolClosureControlMeasure before'+\
			       'initial parameters file has been made')
	os.chdir(self.inpDir)
	with open(self.fileNames['paramsFile'],'ab') as f:
	    f.write("##### ANTIVIRAL DEFINITIONS ####\n")
	    f.write("enable_antivirals = 1\n")
	    f.write("number_antivirals = 1\n")
	    f.write("av_disease[0] = 0\n")
	    if len(controlMeasure._controlMeasure._controlMeasureTargetPopulationsAndPrioritization) > 1:
		print "!!WARNING!! FRED Apollo webservice only supports on Target Population for"
		print "            Antivirals, using only the first list entry."
	    if controlMeasure._controlMeasure._controlMeasureTargetPopulationsAndPrioritization[0]._targetPopulationDefinition._diseaseStates[0] == "newly sick":
		f.write("av_prophylaxis[0] = 0\n")
		f.write("av_prob_symptomatics[0] = %g\n"%controlMeasure._controlMeasure._controlMeasureCompliance)
	    else:
		f.write("av_prophylaxis[0] = 1\n")

	    f.write("av_start_day[0] = %d\n"%controlMeasure._controlMeasureFixedStartTime._fixedStartTime)
	    if len(set(controlMeasure._controlMeasure._antiviralTreatmentAdministrationCapacity)) > 1:
		print "!!WARNING!! FRED only supports a uniform schedule for antivirals"
		print "            Only using the first entry in antiviralTreatementAdministrationCapacity"
	    if len(set(controlMeasure._controlMeasure._antiviralSupplySchedule)) > 1:
		print "!!WARNING!! FRED only supports a uniform schedule for antivirals"
		print "            Only using the first entry in antiviralSupplySchedule"

	    avRate = min(controlMeasure._controlMeasure._antiviralTreatmentAdministrationCapacity[0],controlMeasure._controlMeasure._antiviralSupplySchedule[0])
	    f.write("av_total_avail[0] = 30000000\n")
	    f.write("av_additional_per_day[0] = %d\n"%avRate)
	    f.write("av_initial_stock[0] = 0\n")
	    f.write("av_course_length[0] = %d\n"%controlMeasure._controlMeasure._antiviralTreatment._numDosesInTreatmentCourse)
	    f.write("av_reduce_susceptibility[0] = 0.0\n")
	    f.write("av_reduce_infectivity[0] = %g\n"%controlMeasure._controlMeasure._antiviralTreatment._antiviralTreatmentEfficacy._efficacyValues[0])
	    f.write("av_reduce_symptomatic_period[0] = %g\n"%controlMeasure._controlMeasure._antiviralTreatment._antiviralTreatmentEfficacy._efficacyValues[0])
	    os.chdir('..')
	    
    def parseFixedVaccinationControlMeasure(self,controlMeasure):
	if self.initParamsStarted is False:
	    raise RuntimeError('Calling parseFixedVaccinationControlMeasure before'+\
			       'initial parameters file has been made')

	os.chdir(self.inpDir)
	with open(self.fileNames['paramsFile'],'ab') as f:
	    ### Add pregnant and at_risk estimates for vaccination
	    f.write("pregnancy_prob_ages = 8 0 18 19 24 25 49 50 110\n")
	    f.write("pregnancy_prob_values = 4 0.0 0.0576 0.0314 0.0\n")
	    f.write("\n")
	    f.write("at_risk_ages[0] = 14 0 1 2 4 5 18 19 24 25 49 50 64 65 110\n")
	    f.write("at_risk_values[0] = 7 0.039 0.0883 0.1168 0.1235 0.1570 0.3056 0.4701\n")
	    f.write("##### VACCINE PARAMETERS\n")
	    f.write("enable_behaviors = 1\n")
	    f.write("enable_vaccination = 1\n")
	    f.write("number_of_vaccines = 1\n")
	    f.write("accept_vaccine_enabled = 1\n")
	    f.write("accept_vaccine_strategy_distribution = 7 %g %g 0 0 0 0 0\n"\
		    %(1.0-controlMeasure._controlMeasure._controlMeasureCompliance,\
		      controlMeasure._controlMeasure._controlMeasureCompliance))
	    f.write("### VACCINE 1\n")
	    f.write("vaccine_number_of_doses[0] = 1\n")
	    # Setting this to infinity for now
	    # need to implement production as a time map as well
	    f.write("vaccine_total_avail[0] = 300000000\n")
	    f.write("vaccine_additional_per_day[0] = 300000000\n")
	    f.write("vaccine_starting_day[0] = %d\n"\
		    %controlMeasure._controlMeasureFixedStartTime._fixedStartTime)
	    f.write("##### Vaccine 1 Dose 0\n")
	    f.write("vaccine_next_dosage_day[0][0] = 0\n")
	    f.write("vaccine_dose_efficacy_ages[0][0] = 2 0 100\n")
	    f.write("vaccine_dose_efficacy_values[0][0] = 1 %g\n"\
		    %controlMeasure._controlMeasure._vaccination._vaccinationEfficacy._efficacyValues[0])
	    f.write("vaccine_dose_efficacy_delay_ages[0][0] = 2 0 100\n")
	    ### This is not longer a part of the schema.
	    f.write("vaccine_dose_efficacy_delay_values[0][0] = 1 %d\n"\
		    %14.0) 
	    f.write("vaccination_capacity_file = fred-vaccination-schedule_0.txt\n")
	    ## MUST ADD POPULATION BASEDD Control measures
	    if controlMeasure._controlMeasure._controlMeasureNamedPrioritizationScheme == "ACIP":
		f.write("vaccine_prioritize_acip = 1\n")

	with open(self.fileNames['vaccCapFile'],"wb") as f:
	    self.fileList.append(self.fileNames['vaccCapFile'])
	    vaccineSupplySchedule = controlMeasure._controlMeasure._vaccineSupplySchedule
	    vaccineAdminSchedule = controlMeasure._controlMeasure._vaccinationAdministrationCapacity
	    
	    for day in range(0,len(vaccineSupplySchedule)) :
		## Hack for now, because I don't have the resolution on production in FRED
		f.write("%d %d\n"%(day,min(vaccineSupplySchedule[day],vaccineAdminSchedule[day])))

	os.chdir('..')

class FredBatchInputs:
    def __init__(self):
	self.cfgList = []
	self.inputDict = {}
	self.batchId = None

    def setupFromURL(self,url):
	### Get zip file
	#self.setupFromJSonZip(
	pass
    def setupFromJSonZip(self,jSonZipFile):
	import zipfile
	
	zf = zipfile.ZipFile(jsonZipFile)
	zf.extractall()

    def setupFromJsonListFile(self,jsonListFile_):
	try:
	    import jsonpickle
	except ImportError:
	    raise RuntimeError("ERROR: Need to have the jsonpickle module to use FREDBatchInputs.setupFromJsonFile")

	with open(jsonListFile_,"rb") as f:
	    for json in f.readlines():
		print "|"+str(json)+"|"
		jsonObj = jsonDict2Obj(jsonpickle.decode(json))
		print dir(jsonObj)
		self.cfgList.append(jsonDict2Obj(jsonObj))


	fipsCountDict = {}
	with open("2005_2009_ver2_pop_counts_by_fips.txt","rb") as f:
	    for line in f:
		fipsCountDict[line.split()[0]] = int(line.split()[1].strip())
		
	dirCount = 0
	for cfg in self.cfgList:
	    print dir(cfg)
	    fredDir = "fred.tmp.%d"%dirCount
	    if os.path.exists(fredDir) is False:
		os.mkdir(fredDir)
	    fredInput = FredInputFileSet(cfg,fredDir,fipsCountDict)
	    fredInput.createParam()
	    dirCount += 1

	
	
def jsonDict2Obj(d):
    if isinstance(d, list):
	d = [jsonDict2Obj(x) for x in d]
    if not isinstance(d, dict):
	return d
    class C(object):
	pass
    o = C()
    for k in d:
	o.__dict__['_'+k] = jsonDict2Obj(d[k])
    return o

def main():
    import random
    import time

    ### STB to do: Unit test parsings
    ## Test the FREDInputBatch
    fredBatch = FredBatchInputs()
    fredBatch.setupFromJsonListFile("sample_json_simulator_configuration.txt")
    
    sys.exit()
    ### Test on Blacklight
    fredConn = FredSSHConn(debug=True)
    fredConn._setup(machine="blacklight.psc.xsede.org",
		   username="stbrown",
		   privateKeyFile="./id_rsa")
    fredConn._connect()

    ### create a basic FRED test job to run for testing
    tempID = random.randint(0,100000)
    remoteDir = 'fred.tmp.%d'%tempID

    fredConn._mkdir(remoteDir)
    fredConn._sendFile('fred_test/fred_input.params',remoteDir+"/fred_input.params")
    fredConn._sendFile('fred_test/fred_submit.pbs',remoteDir+"/fred_submit.pbs")
    fredConn._sendFile('fred_test/fred_initial_population_0.txt',remoteDir+'/fred_initial_population_0.txt')

    pbsID = fredConn._submitPBSJob(tempID)

    print "PBS ID = " + str(pbsID)
    status = "NOTHING"
    while status != "COMPLETED":
	status,response = fredConn._getStatus(pbsID)
	print "Status = " + status
	print "Response = " + response
	time.sleep(10)
	
	   

    


### Main Hook

if __name__=="__main__":
    main()
