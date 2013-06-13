import time
import os,sys

ServiceCodes = {
    'STARTED':101,
    'CALLRECV':102,
    'RUNNING':103,
    'DB_ERROR':104,
    'UNKNOWN':105,
    'COMPLETED':106,
    'DB_SUCCESS':107,
    'DB_NOID_FOUND':108,
    'DB_CONNECT_SUCCESS':109,
    'DB_CONNECT_FAILED':110,
    'DUPLICATE_ID':111,
    'DB_POP_FAILED':112,
    'DB_POP_ZERO':113,
    'DB_QUERY_FAILED':114,
    'GAIA_FILE_CREATION_FAILED':115,
    'GAIA_FILE_CREATION_COMPLETE':116,
    'CALLING_GAIA':117,
    'GAIA_FAILED':118,
    'GAIA_SUCCESS':119,
    'FILE_MOVE_FAILED':120
    }

ServiceDescriptions = {
    ServiceCodes['STARTED']:'Started Logging',
    ServiceCodes['CALLRECV']:'Incoming Call recieved',
    ServiceCodes['RUNNING']:'The service is Running',
    ServiceCodes['DB_ERROR']:'There has been an error in working accessing the ApolloDB',
    ServiceCodes['UNKNOWN']:'Unknown Status Code',
    ServiceCodes['COMPLETED']:'Completed creating visualizaton',
    ServiceCodes['DB_SUCCESS']:'Successfully obtained data from the ApolloDB',
    ServiceCodes['DB_NOID_FOUND']:'No ID found in ApolloDB',
    ServiceCodes['DB_CONNECT_SUCCESS']:'Successfully connected to the ApolloDB',
    ServiceCodes['DB_CONNECT_FAILED']:'Failed to connect to the ApolloDB',
    ServiceCodes['DUPLICATE_ID']:'Tried this request again while it is still running',
    ServiceCodes['DB_POP_FAILED']:'Failed getting the population from the ApolloDB',
    ServiceCodes['DB_POP_ZERO']:'ApolloDB query returned zero population',
    ServiceCodes['DB_QUERY_FAILED']:'ApolloDB query failed',
    ServiceCodes['GAIA_FILE_CREATION_FAILED']:'GAIA input file creation failed',
    ServiceCodes['GAIA_FILE_CREATION_COMPLETE']:'GAIA input file creation successful',
    ServiceCodes['CALLING_GAIA']:'Sent Call to the GAIA webservice',
    ServiceCodes['GAIA_FAILED']:'GAIA Service Call failed',
    ServiceCodes['GAIA_SUCCESS']:'GAIA Service return successfully',
    ServiceCodes['FILE_MOVE_FAILED']:'Moving movie file to web directory failed'
    }

ServiceTypes = {
    ServiceCodes['STARTED']:'running',
    ServiceCodes['CALLRECV']:'running',
    ServiceCodes['RUNNING']:'running',
    ServiceCodes['DB_ERROR']:'failed',
    ServiceCodes['UNKNOWN']:'running',
    ServiceCodes['COMPLETED']:'completed',
    ServiceCodes['DB_SUCCESS']:'running',
    ServiceCodes['DB_NOID_FOUND']:'failed',
    ServiceCodes['DB_CONNECT_SUCCESS']:'running',
    ServiceCodes['DB_CONNECT_FAILED']:'failed',
    ServiceCodes['DUPLICATE_ID']:'running',
    ServiceCodes['DB_POP_FAILED']:'failed',
    ServiceCodes['DB_POP_ZERO']:'failed',
    ServiceCodes['DB_QUERY_FAILED']:'failed',
    ServiceCodes['GAIA_FILE_CREATION_FAILED']:'failed',
    ServiceCodes['GAIA_FILE_CREATION_COMPLETE']:'running',
    ServiceCodes['CALLING_GAIA']:'running',
    ServiceCodes['GAIA_FAILED']:'failed',
    ServiceCodes['GAIA_SUCCESS']:'running',
    ServiceCodes['FILE_MOVE_FAILED']:'failed'
    }
class Log:
    def __init__(self, logFileName_=None,logLevel_ = "Normal", buffered_=False,append_=False):

        self.logFile = None
        try:
            if append_:
                self.logFile = open(logFileName_,"ab")
            else:
                self.logFile = open(logFileName_,"wb")
        except IOError:
            ### This error will prevent the server from starting, as it should
            raise RuntimeError("Cannot open the specified log file %s"%self.logFile)
        
        self.logLevel = logLevel_
        self.buffered = buffered_
        self.moniker = "Service-Wide"
        self.lastMessage = None

    def start(self):
        startTime = time.strftime("%Y-%m-%d %H:%M:%S")
        self.update('STARTED')
    
    def __del__(self):
        self.logFile.close()

    def setMoniker(self,moniker):
        self.moniker = moniker
        
    def update(self,status = "NOTHING",  message = None):
        thisServiceCode = ServiceCodes['UNKNOWN']
        
        if ServiceCodes.has_key(status):
            thisServiceCode = ServiceCodes[status]
            
        timestamp = time.strftime("%Y-%m-%d %H:%M:%S")
        thisMessage = ""
        if message is None:
            thisMessage = "%s %s Code: %d, %s\n"%(timestamp,self.moniker,
                                                  thisServiceCode,
                                                  ServiceDescriptions[thisServiceCode])
        else:
            thisMessage = "%s %s Code: %d, %s message: %s\n"%(timestamp,self.moniker,
                                                              thisServiceCode,
                                                              ServiceDescriptions[thisServiceCode],
                                                              message)
        self.logFile.write("%s"%thisMessage)
        self.lastMessage = (status,thisMessage)
        
        if not self.buffered: self.logFile.flush()

    def updateAndExit(self,status = "NOTHING"):
        self.update(status)
        sys.exit()

    def pollStatus(self):
        return self.lastMessage

    def pollStatusCode(self):
        return ServiceCodes[self.lastMessage[0]]

    def pollStatusType(self):
        return ServiceTypes[self.pollStatusCode()]
    
class ThreadLog(Log):
    def __init__(self,logFileName_=None,logLevel_="Normal",id_=None,buffered_=False):
        Log.__init__(self,logFileName_,logLevel_,buffered_,append_=True)
        self.moniker = str(id_)
        
    

    

                 

