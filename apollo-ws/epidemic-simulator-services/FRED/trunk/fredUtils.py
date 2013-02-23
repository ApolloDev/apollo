import os,sys
import paramiko
import datetime,dateutil.parser

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

    def _setup(self,machine="blacklight.psc.xsede.org",username='stbrown',
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
	elif self.machine == "unicron.psc.edu":
	    self.remoteDir = "/media/scratch"
	
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
	    
	if status[:7] == "RUNNING":
	    percent = status.split('-')[1]
	    status = status.split('-')[0]
	    if self.pbsWorkDir:
		remoteCommand = "tail -1 " + self.pbsWorkDir + "/starttime"
		retTVal = self._executeCommand(remoteCommand)
		if retTVal.strip() == "Nothing":
		    print "starttime nothing"
		    secondsRunning = -1.0
		else:
		    timeValTZ = dateutil.parser.parse(self._executeCommand(remoteCommand))
		    timeVal = timeValTZ.replace(tzinfo=None)
		    
		    now = datetime.datetime.now()
		    print "Time Val = " + str(timeVal) + " " + str(now)
		    secondsRunning = (now-timeVal).seconds
	    date = " ".join(returnSplit[2:5])
	    print "Date = " + date
	elif status == "FINISHED":
	    if self.pbsWorkDir:
		remoteCommand = "tail -1 " + self.pbsWorkDir + "/starttime"
                
		retTVal = self._executeCommand(remoteCommand)
                if retTVal.strip() == "Nothing":
		    print "starttime nothing"
		    secondsRunning = -1.0
                else:
		    timeValTZ = dateutil.parser.parse(self._executeCommand(remoteCommand))
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
        pbsSplit = str(key).split("_")
        pbsID = pbsSplit[len(pbsSplit)-1]
	if self.runPBS:
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

	else:
	    ### This is a direct Run
	    response = "direct getStatus not implemented yet."

	return (status,response)
    
def main():
    import random
    import time
    
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
