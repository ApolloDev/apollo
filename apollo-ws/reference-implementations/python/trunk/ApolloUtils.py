'''
Created on Nov 29, 2012

@author: John Levander
'''

from EpidemicSimulatorService_types import *
import logging

class ApolloUtils:
    logger = logging.getLogger("apollo-type-logger")
    hdlr = logging.FileHandler('apollo-type-logger.log')
    formatter = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
    hdlr.setFormatter(formatter)
    logger.addHandler(hdlr)
    logger.setLevel(logging.DEBUG)
    
    
    def log_epidemic_model_input(self, obj):
        self.logger.debug("-----epidemic_model_input-----")
        self.log_authentication(obj)
        self.log_simulator_configuration(obj)
        self.log_simulator_identification(obj)
        self.log_disease_dynamics(obj)
        self.log_antiviral_control_measure(obj)
        self.log_vaccination_control_measure(obj)
        self.logger.debug("-----end epidemic_model_input-----")
        return
    
    def log_authentication(self, obj):
        self.logger.debug("-----authentication")
        self.logger.debug("requester_id: %s", obj._authentication._requester_id)
        self.logger.debug("requester_password: %s", obj._authentication._requester_password)
        return
    
    def log_simulator_identification(self, obj):
        self.logger.debug("-----simulator_identification")
        self.logger.debug("simulator_developer: %s", obj._simulator_identification._simulator_developer)
        self.logger.debug("simulator_name: %s", obj._simulator_identification._simulator_name)
        self.logger.debug("simulaotr_version: %s", obj._simulator_identification._simulator_version)
        return
    
    def log_simulator_configuration(self, obj):
        self.logger.debug("-----simulator_configuration")
        self.logger.debug("time_step_unit: %s", obj._simulator_configuration._time_step_unit)
        self.logger.debug("time_step_value: %s", obj._simulator_configuration._time_step_value)
        self.logger.debug("run_length: %s", obj._simulator_configuration._run_length)
        self.logger.debug("pop_count: %s", obj._simulator_configuration._pop_count)
        self.logger.debug("disease: %s", obj._simulator_configuration._disease)
        self.logger.debug("population_location: %s", obj._simulator_configuration._population_location)
        #self.logger.debug("time_step_value: %s", obj._simulator_configuration._requester_id)
        return
    
    def log_disease_dynamics(self,obj):
        self.logger.debug("-----disease_dynamics")
        self.logger.debug("infectious_period: %s", obj._disease_dynamics._infectious_period)
        self.logger.debug("latent_period: %s", obj._disease_dynamics._latent_period)
        self.logger.debug("reproduction_number: %s", obj._disease_dynamics._reproduction_number)
        self.logger.debug("asymptomatic_infection_fraction: %s", obj._disease_dynamics._asymptomatic_infection_fraction)
        self.logger.debug("population: %s", obj._disease_dynamics._population)
        self.logger.debug("pop_count: %s", obj._disease_dynamics._pop_count)

        return
    
    def log_antiviral_control_measure(self, obj):
        self.logger.debug("-----antiviral_control_measure")
        self.logger.debug("antiviral_cm_compliance: %s", obj._antiviral_control_measure._antiviral_cm_compliance)
        self.logger.debug("antiviral_efficacy: %s", obj._antiviral_control_measure._antiviral_efficacy)
        self.logger.debug("antiviral_efficacy_delay: %s", obj._antiviral_control_measure._antiviral_efficacy_delay) 
        self.logger.debug("antiviral_supply_schedule: %s", obj._antiviral_control_measure._antiviral_supply_schedule)
        self.logger.debug("antiviral_admin_schedule: %s", obj._antiviral_control_measure._antiviral_admin_schedule)
        return
        return
    
    def log_vaccination_control_measure(self, obj):
        self.logger.debug("-----vaccination_control_measure")
        self.logger.debug("vaccine_cm_compliance: %s", obj._vaccination_control_measure._vaccine_cm_compliance)
        self.logger.debug("vaccine_efficacy: %s", obj._vaccination_control_measure._vaccine_efficacy)
        self.logger.debug("vaccine_efficacy_delay: %s", obj._vaccination_control_measure._vaccine_efficacy_delay) 
        self.logger.debug("vaccine_supply_schedule: %s", obj._vaccination_control_measure._vaccine_supply_schedule)
        self.logger.debug("vaccination_admin_schedule: %s", obj._vaccination_control_measure._vaccination_admin_schedule)
        return
   


