import os,sys,string
import math
import optparse
import apollo
import time
import glob,hashlib
import datetime
import random
from multiprocessing import Process,Queue

def parseFips(state,county,tract):
    fipsString = ""
    if len(str(state)) == 1:
        fipsString = "0" + str(state)
    else:
        fipsString = str(state)
    
    if len(str(county)) == 1:
        fipsString += "00" + str(county)
    elif len(str(county)) == 2:
        fipsString += "0" + str(county)
    else:
        fipsString += str(county)
        
    fipsString += str(tract)
    return fipsString

def statusUpdate(status, message):
    statusFile = './starttime'
    with open(statusFile, "wb") as f:
        f.write("%s %s" % (status, message))

def error_exit(message):
	#statusUpdate("LOGERROR",message)
	sys.stderr.write(message)
	sys.exit(1)

if __name__ == '__main__':
    parser = optparse.OptionParser(usage="""
    %prog [--help]
    """)
    
    parser.add_option("-d","--debug",action="store_true")
    parser.add_option("-i","--runId",type="string",
		      help="The Apollo RunId for this call")
    
    parser.add_option("-H", "--dbHost", type="string", default="warhol-fred.psc.edu")
    parser.add_option("-D", "--dbName", type="string", default="test")
    parser.add_option("-U", "--dbUser", type="string", default="apolloext")
    parser.add_option("-P", "--dbPword", type="string")
    parser.add_option("-n", "--no_db", action="store_true", default=False)
    
    opts,args=parser.parse_args()
    
    try:
        apolloDB = apollo.ApolloDB(host_=opts.dbHost, dbname_=opts.dbName, user_=opts.dbUser, password_=opts.dbPword)
        apolloDB.connect()
        
    except Exception as e:
        error_exit(str(e))
    
    try:
        stateToDataFileDict = {'S':'susceptible.txt','E':'exposed.txt','I':'infectious.txt','T':'temp_immuned.txt',
                               'R':'recovered.txt','M':'immuned.txt','C':'newly_exposed.txt','MS':'mosquito_susceptible.txt',
                               'ME':'mosquito_exposed.txt','MI':'mosquito_infectious.txt','MC':'mosquito_newly_exposed.txt'}
    
        stateMap = {'Day':0,'S':2,'E':3,'I':4,'T':5,'R':7,'M':6,'C':8,'MS':9,'ME':10,'MI':11,'MC':12}
    
        location = "00000"
        fileList = []
        with open('Apollo/out-1.seir','rb') as f:
            for line in f.readlines()[0:]:
                fileList.append(line.split())
        
        softIdCLARA = apolloDB.getSoftwareIdentificationId(name_="CLARA",version_="0.5")
        softIdTS =    apolloDB.getSoftwareIdentificationId(name_="Time Series Visualizer",version_="1.0")
    
        if softIdCLARA == -1:
            print "CLARA ID not right"
            sys.exit()
        for stateId,filename in stateToDataFileDict.items():
            stateStringList = []
                ### Get the start day and offset
            #print fileList[0][0]
            startDay = int(fileList[0][0])
            for line in fileList:
                day = int(line[stateMap['Day']]) - startDay
                stateStringList.append("US%s %d %d:1\n"%(str(location),int(line[stateMap[stateId]]),day))
                
            stateString = "".join(stateStringList)
            if stateString == "":continue
            m = hashlib.md5()
            m.update(stateString)
            m5hash = m.hexdigest()
            hashvar = apolloDB.checkMD5HashExistence(m5hash)
            if hashvar > -1:
                runDataContentId = hashvar
            else:
                SQLString = 'INSERT INTO run_data_content  (text_content, md5_hash_of_content) values ("%s","%s")'%(stateString,m5hash)
                
                apolloDB.query(SQLString)
                runDataContentId = apolloDB.insertID()
            runDataDescriptionId = apolloDB.getRunDataDescriptionId(label_=filename,
                                                                    source_software_=softIdCLARA,
                                                                    destination_software_=softIdTS)
    
            SQLString = 'INSERT INTO run_data (run_id, description_id, content_id) values '\
                '("%s","%s","%s")'%(opts.runId,runDataDescriptionId,runDataContentId)
            print SQLString
            apolloDB.query(SQLString)
    			    
            with open(filename,'wb') as g:
                g.write(stateString)
            
        apolloDB.close()
        statusUpdate("LOG_FILES_WRITTEN", "%s" % datetime.datetime.now().strftime("%a %b %d %H:%M:%S EDT %Y")) 
    
    except Exception as e:
        error_exit(str(e))
    
