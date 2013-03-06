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
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>Simple End-user Apollo App</title>

<!--        <script src="js/jquery-1.4.4.js" type="text/javascript"></script>-->
        <script src="js/jquery-1.9.1.js" type="text/javascript"></script>
<!--        <script src="js/jquery-ui-1.8.9.custom.js" type="text/javascript"></script>-->
        <script src="js/jquery-ui-1.10.1.custom.js" type="text/javascript"></script>

        <!-- JQuery Plugins -->
<!--        <script src="js/jquery.form-2.36.js" type="text/javascript"></script>-->
        <script src="js/jquery.form-3.28.js" type="text/javascript"></script>
        <script src="js/jquery.layout-1.3.0-latest.js" type="text/javascript"></script>
<!--        <script src="js/jquery.layout-1.2.0.js" type="text/javascript"></script>-->
        <!-- jqGrid -->
        <script src="js/grid.locale-en.js" type="text/javascript"></script>
<!--        <script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>-->
        <!-- script src="js/jquery.jqGrid.js" type="text/javascript"></script -->
        <script src="js/jquery.jqGrid.src-4.4.4.js" type="text/javascript"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>

        <!-- Highchart -->
        <script type="text/javascript" src="js/highcharts.js"></script>
<!--        <script type="text/javascript" src="js/highstock.js"></script>-->
        <script type="text/javascript" src="js/modules/exporting.js"></script>

        <!--Misc -->
        <script type="text/javascript" src="js/debug-helper.js"></script>
        <script type="text/javascript" src="js/jqgrid-helper.js"></script>

        <!-- Base -->
        <script type="text/javascript" src="js/apollo.js"></script>
        <script type="text/javascript" src="ckeditor/ckeditor.js"></script>

        <!-- My Style -->
        <link rel="stylesheet" href="css/index.css" type="text/css" media="all" />
        <!--        <link rel="stylesheet" type="text/css" media="screen"
                      href="themes/redmond/jquery-ui-1.8.2.custom.css" />-->
        <!--        <link rel="stylesheet" type="text/css" media="screen"
                      href="themes/ui.jqgrid.css" />-->
        <link rel="stylesheet" type="text/css" media="screen"
              href="themes/ui.multiselect.css" />
        <link rel="stylesheet" type="text/css" media="screen"
              href="themes/redmond/jquery-ui-1.10.1.custom.css" />
        <!--        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.7.1.custom.css" />-->
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />

        <link rel="stylesheet" type="text/css" media="screen"
              href="css/apollo.css" />

    </head>
    <body>

        <div id="top" style="font-family: Segoe UI, Arial, sans-serif">
            <div id="header">

                <h1 class="title"><a href=".">Simple End-user Apollo App</a></h1>
            </div>
            <div id="nav" style="font-family: Segoe UI Light, Arial, sans-serif; padding-right: 20px">
                <ul class="nav">
                    <li><a id="instruction-link" href="javascript:createOrSelectInstructionTab();">Help</a></li>
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

                    <div id="model-div" class="drop-box-div">
                        <div style="font-size: 5px">&nbsp;</div>
                        <div style="font-size: 16px; font-family: Segoe UI, Arial, sans-serif; ">Epidemic Simulators:</div>
                        <div style="font-size: 2px">&nbsp;</div>
                        <select id="model-combo" style="width: 240px;" size="4" multiple="multiple">
                            <option value="loading">Loading...</option>
                        </select>
                        <br></br>
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
                        Welcome to the Simple End-user Apollo App! <br /><br />
                        Complete the simulator selection and configuration panels on the left to begin. 
                        The simulators will model the effect of a vaccination control measure if all five 
                        parameters that represent a vaccination program are non zero. If you wish to exclude 
                        a vaccination program, simply set the compliance or efficacy to zero.<br /><br />

                        The source for the terms in the configuration panel is the Apollo-SV ontology. Hover the
                        mouse pointer over a term to see its definitions in the ontology.<br /><br /> 

                    </div>
                </div>
            </div>
            <!-- CenterPane -->

            <div class="ui-layout-south">
                <div>
                    <form id="apollo-form" action="apollo_exec.php" method="post"
                          style="padding: 10px;">
                        <button id="create" style="font-size: 16px">Run Epidemic Simulator(s)</button>
                    </form>
                </div>
                <div id="status-name-div" style="padding: 10px">Status:</div>
                <!--                <div id ='terminal-div' style="height: 200px">
                                    <textarea id="terminal" rows="3" cols="75"></textarea>
                                </div>-->
                <!--                <script>
                                    CKEDITOR.replace( 'terminal',
                                    {
                                        height:"150", 
                                        width:"600",
                                        readOnly: false,
                                        autoParagraph: false,
                                        on :
                                            {
                                            instanceReady : function ( evt )
                                            {
                                                // Hide the editor top bar.
                                                document.getElementById('cke_1_top').style.display = 'none';
                                                document.getElementById('cke_1_bottom').style.display = 'none';
                                            }
                                        }
                                    });
                                    
                                </script>-->
                <div id="status-div-1"
                     style="height: 20px; font-size: 12px; display: hidden; padding-left: 10px;"></div>
                <div id="status-div-2"
                     style="height: 20px; font-size: 12px; display: hidden; padding-left: 10px;"></div>
                <div id="status-div-3"
                     style="height: 20px; font-size: 12px; display: hidden; padding-left: 10px;"></div>
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