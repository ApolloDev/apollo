#!/usr/users/4/stbrown/bin/python 
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
from SimulatorService_services import runRequest
'''
Created on Nov 27, 2012

This is an example GenericEpidemicModelService implementation.

@author: John Levander
'''

from ZSI.wstools import logging
from SimulatorService_services_server import SimulatorService
from ZSI.ServiceContainer import AsServer
from ApolloFactory import *
from ApolloUtils import *
from SimulatorService_services import *
import os,sys
import shutil
#import fred
import random
import datetime
import paramiko
from fredUtils import FredSSHConn,infectiousPeriod,incubationPeriod

fredConn = FredSSHConn()

class FredWebService(SimulatorService):
        _wsdl = "".join(open("simulatorservice.wsdl").readlines())
        
        factory = ApolloFactory()
        utils = ApolloUtils()       
        
        def soap_getSupportedLocations(self, ps, **kw):
            response = SimulatorService.soap_getSupportedLocations(self, ps, **kw)
            
            spl = self.factory.new_SupportedPopulationLocation()
            
            spl._populationLocation = "42003"
            spl._populationLocationDescription = "Allegheny County"
            
            response._supportedPopluationLocations.append(spl)
            return response
        
        #this method checks on the status of a job, indicated by the jobs runId
        def soap_getRunStatus(self, ps, **kw):
            response = SimulatorService.soap_getRunStatus(self, ps, **kw)
            #get the run id of the job 
            request = ps.Parse(getRunStatusRequest.typecode)
            runId = request._runId
	    	   	    
            status, message = fredConn._getStatus(runId)
            response._runStatus = self.factory.new_RunStatus()
            print "Fred status is:" + status
            response._runStatus._status = status.lower()
            print "Fred message is:" + message
            response._runStatus._message = message
            
            return response
        
        
        #this method runs an epidemic model
        def soap_run(self, ps, **kw):
	    fredConn._setup()
            response = SimulatorService.soap_run(self, ps, **kw)
            request = ps.Parse(runRequest.typecode)
            #extract the epidemic model input object from the request
            print "received run request"
            cfg = request._simulatorConfiguration
            
            #get the number of time steps in the simulation
            run_length = cfg._simulatorTimeSpecification._runLength
        
            #log the EpidemicModelInput request to a log file
            self.utils.log_simulatorConfiguration(cfg)

            ### STB CREATE FRED INPUT FILE
            ### do some error checking for this version of the model
            if cfg._simulatorTimeSpecification._timeStepUnit.lower() != 'days':
                    print ('This verison of FRED only supports time_step_unit of days')
                    print ('Apollo sent %s'%(cfg._simulatorTimeSpecification._timeStepUnit))
                    #### Put in Error Handling, have to ask John L.
                    response._runId = -401
		    return response
            if cfg._simulatorTimeSpecification._timeStepValue != 1:
                    print ('This version of FRED only supports a time_step_value of 1')
                    print ('Apollo sent %d'%(cfg._simulatorTimeSpecification._timeStepValue))
		    response._runId = -402
		    return response
		
	    lenLoc = len(cfg._populationInitialization._populationLocation)
	    print "lenLoc = " + str(lenLoc)
	    if lenLoc != 5 and lenLoc != 2:
		print ('This version of the FRED only supports 2 (state) or 5 (county) NSIDS codes')
		response._runId = -403
		return response
	    #### Put in Error Handling, have to ask John L.
	    
            #print ('WARNING: This service is currently ignoring infectious_period')
            #print ('WARNING: This service is currently ignoring latent_period')

            ### Make a random directory name so that multiple calls can be made
            randID = random.randint(0,100000)
            tempDirName = "fred.tmp.%d"%(randID)
            try:
                    os.mkdir(tempDirName)
            except:
                    print("Cannot make temporary FRED directory %s"%(tempDirName))
		    response._runId = -404
		    return response

            os.chdir(tempDirName)
            doVacc = False
            with open('fred_input.params','wb') as f:
                    f.write('days = %d\n'%cfg._simulatorTimeSpecification._runLength)
                    f.write('symp[0] = %g\n'%cfg._disease._asymptomaticInfectionFraction)
                    f.write('R0 = %g\n'%cfg._disease._reproductionNumber)
                    f.write('primary_cases_file[0] = fred_initial_population_0.txt\n')
                    f.write('residual_immunity_ages[0] = 2 0 100\n')
		    f.write('fips = %s\n'%cfg._populationInitialization._populationLocation)
		    f.write('days_latent[0] = %s\n'\
			    %(incubationPeriod(float(cfg._disease._latentPeriod))))
		    f.write('days_incubating[0] = %s\n'\
			    %(incubationPeriod(float(cfg._disease._latentPeriod))))
		    f.write('days_infectious[0] = %s\n'\
			    %(infectiousPeriod(float(cfg._disease._infectiousPeriod))))
		    f.write('days_symptomatic[0] = %s\n'\
			    %(infectiousPeriod(float(cfg._disease._infectiousPeriod))))	     	       
                    totPop = self.utils.getTotalPopCount(cfg)
                    numImmune = self.utils.getPopCountGivenLocationAndDiseaseState(cfg, "ignoreed", "recovered")
                    percent_immune = float(numImmune)/float(totPop)
                    f.write('residual_immunity_values[0] = 1 %g\n'%(percent_immune))
		    numExposed = float(self.utils.getPopCountGivenLocationAndDiseaseState(cfg,"ignoreed","exposed"))
		    numInfectious = float(self.utils.getPopCountGivenLocationAndDiseaseState(cfg,"ignoreed","infectious"))
		    fractionExposed = numExposed/(numExposed+numInfectious)
		    fractionInfectious = 1.0 - fractionExposed
		    f.write('advanced_seeding = exposed:%1.2f;infectious:%1.2f\n'%(fractionExposed,fractionInfectious))
		    f.write('track_infection_events = 1\n')
		    if sum(cfg._vaccinationControlMeasure._vaccineSupplySchedule) > 0.0:
                        doVacc = True
                        f.write("##### VACCINE PARAMETERS\n")
                        f.write("enable_behaviors = 1\n")
                        f.write("enable_vaccination = 1\n")
                        f.write("number_of_vaccines = 1\n")
			f.write("accept_vaccine_enabled = 1\n")
			f.write("accept_vaccine_strategy_distribution = 7 %g %g 0 0 0 0 0\n"\
				%(1.0-cfg._vaccinationControlMeasure._vaccineCmCompliance,\
				  cfg._vaccinationControlMeasure._vaccineCmCompliance))
                        f.write("### VACCINE 1\n")
                        f.write("vaccine_number_of_doses[0] = 1\n")
                        # Setting this to infinity for now
                        # need to implement production as a time map as well
                        f.write("vaccine_total_avail[0] = 300000000\n")
                        f.write("vaccine_additional_per_day[0] = 300000000\n")
                        f.write("vaccine_starting_day[0] = 0\n")
                        f.write("##### Vaccine 1 Dose 0\n")
                        f.write("vaccine_next_dosage_day[0][0] = 0\n")
                        f.write("vaccine_dose_efficacy_ages[0][0] = 2 0 100\n")
                        f.write("vaccine_dose_efficacy_values[0][0] = 1 %g\n"\
                                %cfg._vaccinationControlMeasure._vaccineEfficacy)
                        f.write("vaccine_dose_efficacy_delay_ages[0][0] = 2 0 100\n")
                        f.write("vaccine_dose_efficacy_delay_values[0][0] = 1 %d\n"\
                                %cfg._vaccinationControlMeasure._vaccineEfficacyDelay)
                        f.write("vaccination_capacity_file = fred-vaccination-schedule_0.txt\n")

                        with open('fred-vaccination-schedule_0.txt','wb') as g:
                            vaccineSupplySchedule = cfg._vaccinationControlMeasure._vaccineSupplySchedule
                            vaccineAdminSchedule = cfg._vaccinationControlMeasure._vaccinationAdminSchedule

                            for day in range(0,len(vaccineSupplySchedule)) :
                                ## Hack for now, because I don't have the resolution on production in FRED
                                g.write("%d %d\n"%(day,min(vaccineSupplySchedule[day],vaccineAdminSchedule[day])))

            with open('fred_initial_population_0.txt','wb') as f:
                    f.write('#line_format\n')
                    numExposed = self.utils.getPopCountGivenLocationAndDiseaseState(cfg, "ignored", "exposed")
		    numInfectious = self.utils.getPopCountGivenLocationAndDiseaseState(cfg, "ignored", "infectious")
                    #f.write('0 0 %d\n'%(cfg._disease_dynamics._pop_count[2]))
                    f.write('0 0 %d\n'%(numExposed + numInfectious))

		     ### if vaccination comes through, parse this
            with open('starttime','wb') as f:
		    f.write('Nothing')

	    idPrefix = cfg._simulatorIdentification._simulatorDeveloper +\
		       "_" + cfg._simulatorIdentification._simulatorName +\
		       "_" + cfg._simulatorIdentification._simulatorVersion + "_"
	    
            fredConn._connect()

            ### Write the PBS submission Script
            with open('fred_submit.pbs','wb') as f:
                    f.write('#!/bin/csh\n')
		    if fredConn.machine == "blacklight.psc.xsede.org":
                        f.write('#PBS -l ncpus=16\n')
		    else:
			f.write('#PBS -l nodes=2:ppn=8\n')
                    f.write('#PBS -N fred.pbs.out\n')
                    f.write('#PBS -l walltime=30:00\n')
                    f.write('#PBS -j oe\n')
	     	    if fredConn.machine == "blacklight.psc.xsede.org":
                   	 f.write('#PBS -q debug\n')
                    f.write('\n')
		    if fredConn.machine == "blacklight.psc.xsede.org":
                        f.write('source /usr/share/modules/init/csh\n')
                        f.write('source /etc/csh.cshrc.psc\n')
                    f.write('module load fred\n')
		    f.write('module load python\n')
                    f.write('cd $PBS_O_WORKDIR\n')
		    f.write('echo `date` > starttime\n')
                    f.write('### Get the PBS ID\n')
                    f.write("set words = `echo $PBS_JOBID | sed 's/\./ /g'`\n")
                    f.write("set id = $words[1]\n")
                    f.write('fred_job -p fred_input.params -n 8 -t 2 -m 8 -k %s$id\n'%idPrefix)
		    f.write('touch .dbloading\n')
                    f.write('python $FRED_HOME/bin/fred_to_apollo_parallel.py -k %s$id\n'%(idPrefix))
                    f.write('rm -rf .dbloading\n')
                    #f.write('echo COMPLETED > job_status')
                    
            ### for a direct Job.. write the csh script to run FRED
	    with open('fred_run.csh','wb') as f:
		f.write('#!/bin/csh\n')
		f.write('echo `date` > starttime\n')
		f.write('### Generate Timestamp for ID\n')
		f.write('fred_job -p fred_input.params -n 4 -t 2 -m 4 -k %s$id > out.run\n'%idPrefix)
		f.write('touch .dbloading\n')
		f.write('python $FRED_HOME/bin/fred_to_apollo_parallel.py -k %s$id >& out.db\n'%idPrefix)
		f.write('rm -rf .dbloading\n')

            os.chdir('../')
            #fredConn._setup("warhol.psc.edu",username="stbrown",privateKeyFile="./id_rsa")
            #fredConn._connect()
            #print "Created Connection"
            fredConn._mkdir(tempDirName)
            fredConn._sendFile(tempDirName+'/fred_submit.pbs')
	    fredConn._sendFile(tempDirName+'/fred_run.csh')
            fredConn._sendFile(tempDirName+'/fred_input.params')
            fredConn._sendFile(tempDirName+'/fred_initial_population_0.txt')
            fredConn._sendFile(tempDirName+'/starttime')		
	    if doVacc:
		    fredConn._sendFile(tempDirName+"/fred-vaccination-schedule_0.txt")

	    ### we do not need temp directory anymore
	    shutil.rmtree(tempDirName)
	
            print "Running FRED"
            sys.stdout.flush()

            #returnVal = fredConn._submitPBSJob(randID)
	    returnVal = fredConn._submitJob(randID)
            print returnVal 

	    ### parse the run ID
	    response._runId = idPrefix + str(returnVal)
            
            #ssh.close()
            #sftp.close()
            
            return response
        
#logger = logging.getLogger("");
#logger.setLevel(0)

#run a webserver on 8087
AsServer(port=8087, services=[FredWebService('fred'), ])

