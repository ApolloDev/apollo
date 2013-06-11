<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--

    Copyright (C) 2011 University of Pittsburgh

    This file is part of Apollo.

    Apollo is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation, either version 3 of
    the License, or (at your option) any later version.

    Apollo is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with Apollo.  If not, see <http://www.gnu.org/licenses/>.

-->

<!--
Index page for the Apollo Test Client
@author Yang Hu <yah14@pitt.edu>
-->

<?php
//require_once AROOT . '/simulation/population/state_county_data.php';
//
//$state_county_data = new state_county_data();
//$state_county_data->initializeStateCountyData();
//print $state_county_data->getJSONCountyListForState(42);
//    echo $_SERVER['REMOTE_ADDR']; 
?>  


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>Simple End-user Apollo App</title>

        <script src="js/jquery-1.4.4.js" type="text/javascript"></script>
        <script src="js/jquery-ui-1.8.9.custom.js" type="text/javascript"></script>

        <!-- JQuery Plugins -->
        <script src="js/jquery.form-2.36.js" type="text/javascript"></script>
        <script src="js/jquery.layout-1.3.0.js" type="text/javascript"></script>
        <!-- jqGrid -->
        <script src="js/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
        <!-- script src="js/jquery.jqGrid.js" type="text/javascript"></script -->

        <!-- Highchart -->
        <script type="text/javascript" src="js/highcharts.js"></script>
<!--        <script type="text/javascript" src="js/highstock.js"></script>-->
        <script type="text/javascript" src="js/modules/exporting.js"></script>
        <script type="text/javascript" src="tiny_mce/tiny_mce.js"></script>
        <script type="text/javascript" src="js/simpletreemenu.js">
        
            /***********************************************
             * Simple Tree Menu- © Dynamic Drive DHTML code library (www.dynamicdrive.com)
             * This notice MUST stay intact for legal use
             * Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
             ***********************************************/
        </script>

        <script language="javascript" type="text/javascript">
            tinyMCE.init({
                content_css : "css/tiny_mce_custom_content.css",
                theme : "advanced",
                mode : "textareas",
                theme_advanced_source_editor_wrap : true,
                readonly : true
            });

        </script>

        <!--Misc -->
        <script type="text/javascript" src="js/debug-helper.js"></script>
        <script type="text/javascript" src="js/jqgrid-helper.js"></script>

        <!-- Base -->
        <script type="text/javascript" src="js/apollo.js"></script>
        <script type="text/javascript" src="js/population-tree.js"></script>
        <script type="text/javascript" src="js/md5.js"></script>

        <!-- My Style -->
        <link rel="stylesheet" href="css/index.css" type="text/css" media="all" />
        <link rel="stylesheet" type="text/css" media="screen"
              href="themes/redmond/jquery-ui-1.8.2.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen"
              href="themes/ui.jqgrid.css" />
        <link rel="stylesheet" type="text/css" media="screen"
              href="themes/ui.multiselect.css" />
        <link rel="stylesheet" type="text/css" media="screen"
              href="css/apollo.css" />
        <link rel="stylesheet" type="text/css" media="screen"
              href="css/simpletree.css" />

    </head>
    <body>

        <div id="top" style="font-family: Segoe UI, Arial, sans-serif">
            <div id="header">

                <h1 class="title"><a href=".">Simple End-user Apollo App</a></h1>
            </div>
            <div id="nav" style="font-family: Segoe UI Light, Arial, sans-serif; padding-right: 20px">
                <ul class="nav">
                    <li><a id="instruction-link" href="javascript:createOrSelectInsturctionTab();">About</a></li>
                    <li><a href="http://code.google.com/p/apollo">Project Homepage</a></li>
                    <li><a 
                            href="http://research.rods.pitt.edu/apolloservice/services/apolloservice?wsdl">WSDL</a></li>
                    <li><a 
                            href="http://research.rods.pitt.edu/apolloservice/services/apolloservice?xsd=apollo-types.xsd">Schema</a></li>
                </ul>

            </div>
        </div>

        <div id="main">

            <div id="LeftPane" class="ui-layout-west ui-widget ui-widget-content">
                <div id="west-div">
                    <div id="model-selection-div" class="ui-widget-header ui-corner-all" style="margin:2px;padding:2px;">
                        Epidemic Simulator Selection
                    </div>
                    <!--                    <div id="jurisdiction-div" class="drop-box-div">
                                            <div>Jurisdiction:</div>
                                            <select id="jurisdiction-combo" style="width: 120px;">
                                                <option value="UNDEF" selected="selected">--Please Select--</option>
                                                <option value="42003">Allegheny, PA</option>
                                            </select>
                                        </div>
                                        <div id="snomed-ct-div" class="drop-box-div">
                                            <div>Disease/SNOMED-CT Code:</div>
                                            <select id="snomed-ct-combo" style="width: 220px;">
                                                <option value="UNDEF" selected="selected">--Please Select--</option>
                                                <option value="442696006">Influenza (H1N1)/442696006</option>
                                                <option value="21927003">Anthrax/21927003</option>
                                            </select>
                                        </div>-->

                    <div id="disease-div" class="drop-box-div" style="width: 250px">
                        <div style="text-align: left">Disease:</div>
                        <div style="text-align: left">
                            <select name="disease-combo" id="disease-combo" style="width: 175px;">
                                <option value="select">-- Please select --</option>
                                <option value="Influenza">Influenza</option>
                            </select>
                        </div>
                        <br>
                    </div>

                    <div id="model-div" class="drop-box-div" style="width: 350px">
                        <div style="text-align: left">Epidemic Simulators:</div>
                        <div style="text-align: left">
                            <select id="model-combo" style="width: 325px;" size="4" multiple="multiple" disabled>
                                <option value="select">Please select a disease...</option>
                            </select>
                        </div>
                        <br>
                    </div>

<!--                    <img id="select-img" src="images/select.png" />-->
                    <table id="west-grid"></table>
<!--                    <table id="disease-grid"></table>-->

                    <table id="param-legend" style="padding:10px;display:none;">
                        <thead>
                            <tr>
                                <th>Icon</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><div class="ui-icon ui-icon-squaresmall-minus"></div></td><td>Registered parameter</td>
                            </tr>
                            <tr>
                                <td><div class="ui-icon ui-icon-circlesmall-minus"></div></td><td>Unregistered parameter</td>
                            </tr>
                            <tr>
                                <td><div class="ui-icon ui-icon-pencil"></div></td><td>Complex parameter (double-click to edit)</td>
                            </tr>
                            <tr>
                                <td><div class="ui-icon ui-icon-triangle-1-e"></div></td><td>Click to expand</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- #LeftPane -->

            <!-- CenterPane -->
            <div id="CenterPane"
                 class="ui-layout-center ui-helper-reset ui-widget-content" style="overflow:auto;">
                <div id="switcher"></div>
                <div id="tabs" class="jqgtabs">
                    <ul>
                        <li><a href="#tabs-1">Welcome</a></li>
                    </ul>
                    <div id="tabs-1">

                        <div onClick="createOrSelectPopulationTab();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/></div>

                        <h1>Welcome to the Simple End-user Apollo App! </h1>
                        <h2>Usage:</h2>
                        <h3>Quick start guide</h3>
                        <ol>
                            <li>In the panel on the left under the "Epidemic Simulator Selection" section, select a disease from the "Disease" 
                                box. A list of epidemic simulators will appear in the “Epidemic Simulators” box in the same section.</li>

                            <li>In the "Epidemic Simulators" box, select one or more simulators to run. </li>

                            <li>Specify the model configuration in the “Simulator Configuration” section.</li>

                            <li>Click the "Run Epidemic Simulator(s)" button to run the selected simulators for the specified configuration.</li>
                        </ol>
                        (<i><b>Tip</b></i>: The SEIR simulator runs quickly, so stick with it while you are exploring the interface)<br/><br/>
                        <span style="color: #EE0000"><b>Note:</b> The Simple End User Apollo App is a research prototype.  Do not use it for decision making.</span>

                        <h3>Epidemic Simulator Selection section</h3>

                        When a disease is selected (currently the only supported disease is Influenza), the application connects to the Apollo 
                        Service to return a list of registered simulators.

                        <h3>Simulator Configuration section</h3>

                        Hovering over a parameter label in the "Simulator Configuration" section will display it’s definition and elucidation of 
                        the class in the <a href="http://apollo.googlecode.com/svn/apollo-sv/trunk/apollo-sv.owl" target="_blank">Apollo-SV ontology</a>.<br/><br/>

                        Not all parameters listed in the "Simulator Configuration" section can be used by each simulator. 
                        Currently, simulators will ignore the parameter(s) they cannot use.


                        <h3 style="font-weight: normal; text-decoration: underline">Supported Populations</h3>
                        The SEIR and FRED web services support all population locations that can be specified via this application.  The current version of the 
                        FluTE web service only supports running the FluTE simulator for Los Angeles County, California. If any population location other than 
                        Los Angeles County is specified, a default 2000 person population will be used by the FluTE simulator.

                        <h3 style="font-weight: normal; text-decoration: underline">Selecting control measures</h3>
                        Use the checkboxes in the "Control Measures" section to select control measures to be 
                        included in each simulation. 
                        <br/><br/>
                        Not all control measures are supported by each simulator. If a simulator does not support a selected 
                        control measure, it will still run but will not simulate the effects of that control measure. The reason for this 
                        is that this program is a research prototype still under development.

                        <br/><br/>
                        The following table
                        shows which control measures are currently able to be used with each simulator:

                        <table class="apollo-table" style="table-layout: fixed; width: 575px">
                            <tr>
                                <td style="width: 30px"></td>
                                <td style="width: 250px"></td>
                                <td colspan="3" style="height: 40px; border: 0px; text-align: center"><img src="./css/images/simulator.png"></img></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td class="apollo-table-td">SEIR</td>
                                <td class="apollo-table-td">FRED</td>
                                <td class="apollo-table-td">FluTE</td>
                            </tr>
                            <tr>
                                <td rowspan="6" style="vertical-align: middle"><img src="./css/images/control_measures.png"></img></td>
                                <td class="apollo-table-td" style="text-align: right;">Antiviral control measure</td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">&#x2713;</td>
                            </tr>
                            <tr>
                                <td class="apollo-table-td" style="text-align: right;">Vaccine control measure<br/>(no prioritization)</td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">&#x2713;</td>
                            </tr>
                            <tr>
                                <td class="apollo-table-td" style="text-align: right;">Vaccine control measure<br/>(ACIP prioritization)</td>
                                <td class="apollo-table-td"></td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td"></td>
                            </tr>
                            <tr>
                                <td class="apollo-table-td" style="text-align: right;">Vaccine control measure<br/>(described prioritization)</td>
                                <td class="apollo-table-td"></td>
                                <td class="apollo-table-td"></td>
                                <td class="apollo-table-td">&#x2713;</td>
                            </tr>
                            <tr>
                                <td class="apollo-table-td" style="text-align: right;">School closure control measure<br/>(reactive trigger)</td>
                                <td class="apollo-table-td"></td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">&#x2713;</td>
                            </tr>
                            <tr>
                                <td class="apollo-table-td" style="text-align: right;">School closure control measure<br/>(fixed start time)</td>
                                <td class="apollo-table-td"></td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">&#x2713;</td>
                            </tr>
                        </table>

                        <br/>
                        <b>Note:</b> If an unsupported vaccination prioritization scheme is specified in the configuration, 
                        the simulator will revert to using it’s default prioritization scheme.


                        <h2>Expected Simulator Run Times:</h2>
                        The following table shows the expected 90-day run times of non-cached simulator configurations:

                        <table class="apollo-table" style="table-layout: fixed; width: 700px">
                            <tr>
                                <td style="width: 30px"></td>
                                <td style="width: 200px"></td>
                                <td colspan="3" style="width: 400px; height: 40px; border: 0px; text-align: center"><img src="./css/images/simulator.png"></img></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td class="apollo-table-td">SEIR</td>
                                <td class="apollo-table-td">FRED</td>
                                <td class="apollo-table-td">FluTE</td>
                            </tr>
                            <tr>
                                <td rowspan="4" style="vertical-align: middle"><img src="./css/images/population_location.png"></img></td>
                                <td class="apollo-table-td" style="text-align: right">Los Angeles County, CA<br>(pop. 9,818,605)</td>
                                <td class="apollo-table-td">< 5 seconds</td>
                                <td class="apollo-table-td">35 minutes</td>
                                <td class="apollo-table-td">300 minutes</td>
                            </tr>
                            <tr>
                                <td class="apollo-table-td" style="text-align: right">Cook County, IL<br>(pop. 5,194,675)</td>
                                <td class="apollo-table-td">< 5 seconds</td>
                                <td class="apollo-table-td">16 minutes</td>
                                <td class="apollo-table-td">Not available<br>via web service</td>
                            </tr>
                            <tr>
                                <td class="apollo-table-td" style="text-align: right">New York County, NY<br>(pop. 1,585,873)</td>
                                <td class="apollo-table-td"><5 seconds</td>
                                <td class="apollo-table-td">6 minutes</td>
                                <td class="apollo-table-td">Not available<br>via web service</td>
                            </tr>
                            <tr>
                                <td class="apollo-table-td" style="text-align: right">Allegheny County, PA<br>(pop. 1,223,348)</td>
                                <td class="apollo-table-td"><5 seconds</td>
                                <td class="apollo-table-td">5 minutes</td>
                                <td class="apollo-table-td">Not available<br>via web service</td>
                            </tr>
                        </table>

                        <br/>
                        <b>Note:</b> The above run times do not include the time to create visualizations of the simulator output.  
                        The time series visualizations should run in under 60 seconds, while the GAIA visualization may add another 
                        5 to 20 minutes depending on the size of the population being visualized.

                        <h2>Output Displays:</h2>
                        The following table shows the output that this application displays after each simulation:


                        <table class="apollo-table" style="table-layout: fixed; width: 650px">
                            <tr>
                                <td style="width: 30px"></td>
                                <td style="width: 190px"></td>
                                <td colspan="3" style="height: 40px; border: 0px;  text-align: center"><img src="./css/images/simulator.png"></img></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td class="apollo-table-td">SEIR</td>
                                <td class="apollo-table-td">FRED</td>
                                <td class="apollo-table-td">FluTE</td>
                            </tr>
                            <tr>
                                <td rowspan="4" style="vertical-align: middle"><img src="./css/images/simulator_output.png"></img></td>
                                <td class="apollo-table-td" style="text-align: right;">GAIA movie</td>
                                <td class="apollo-table-td">simulator does not<br>output spatial data</td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">&#x2713;</td>
                            </tr>
                            <tr>
                                <td class="apollo-table-td" style="text-align: right">Disease states chart</td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">simulator does not<br>output disease states</td>
                            </tr>
                            <tr>
                                <td class="apollo-table-td" style="text-align: right;">Incidence chart**</td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">&#x2713;</td>
                            </tr>
                            <tr>
                                <td class="apollo-table-td" style="text-align: right;">Native configuration file</td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">&#x2713;</td>
                                <td class="apollo-table-td">&#x2713;</td>
                            </tr>
                        </table>

                        <br/>**The FluTE simulator reports symptomatic incidence, whereas the incidence reported by the other simulators is 
                        the total incidence.

                        <h2>For More Info:</h2>
                        There is more information about this research project at <a href="code.google.com/p/apollo">code.google.com/p/apollo</a>. 
                        It has an issue tracker for reporting bugs or offering suggestions (click the middle tab labeled "Issues").

                    </div>
                </div>
            </div>
            <!-- CenterPane -->

            <div class="ui-layout-south" id="south-div">
                <div>
                    <form id="apollo-form" action="simulation/apollo_exec.php" method="post"
                          style="padding: 10px;">
                        <button id="create" style="font-size: 16px">Run Epidemic Simulator(s)</button>
                    </form>
                </div>
                <div id="status-name-div" style="padding: 10px">Status:</div>
                <div style="padding: 10px; font-size: 12px">
                    <textarea id="statustextarea" style="width: 100%; height: 150px" readonly="true">

                    </textarea>
                </div>
                <!--                <div id="status-div-1"
                                     style="height: 20px; font-size: 12px; display: hidden; padding-left: 10px;"></div>
                                <div id="status-div-2"
                                     style="height: 20px; font-size: 12px; display: hidden; padding-left: 10px;"></div>
                                <div id="status-div-3"
                                     style="height: 20px; font-size: 12px; display: hidden; padding-left: 10px;"></div>-->
            </div>
            <!-- ButtomPane-->

        </div>
        <!-- End main -->

        <div id="footer">
            <div class="footer">
                &copy; 2013 University of Pittsburgh	</div>
        </div>

    </body>
</html>