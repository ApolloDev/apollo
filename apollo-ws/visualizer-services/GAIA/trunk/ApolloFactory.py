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
Created on Nov 29, 2012

@author: John Levander
'''

from VisualizerService_services_types import *

class ApolloFactory:
    
   
    def new_VisualizerResult(self):
        vr = ns1.VisualizerResult_Def(None).pyclass
        return vr
    
    def new_UrlOutputResource_Def(self):
        r = ns1.UrlOutputResource_Def(None).pyclass
        return r
    
    def new_VisualizerIdentification(self):
        vid = ns1.VisualizerIdentification_Def(None).pyclass
        return vid
    
    def new_Authentication(self):
        auth = ns1.Authentication_Def(None).pyclass
        return auth
    
    def new_VisualizationOptions(self):
        vopt = ns1.VisualizationOptions_Def(None).pyclass
        return vopt;
    
    def new_RunStatus(self):
        run_status = ns1.RunStatus_Def(None).pyclass()
        return run_status
    
    def new_VisualizerConfiguration(self):
        vc = ns1.VisualizerConfiguration_Def(None).pyclass()
        vc._visualizerIdentification = self.new_VisualizerIdentification()
        vc._authentication = self.new_Authentication()
        vc._visualizationOptions = self.new_VisualizationOptions()
        return vc
        
        
        
        
    