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

<title>Apollo-WS Client</title>
<?php
//set php in strict mode
ini_set ( 'error_reporting', E_ALL | E_STRICT );
?>

<script src="js/jquery-1.4.4.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.8.9.custom.js" type="text/javascript"></script>

<!-- JQuery Plugins -->
<script src="js/jquery.form-2.36.js" type="text/javascript"></script>
<script src="js/jquery.layout-1.3.0.js" type="text/javascript"></script>
<!-- jqGrid -->
<script src="js/jquery.jqGrid.js" type="text/javascript"></script>

<!-- Highchart -->
<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/modules/exporting.js"></script>

<!--Misc -->
<script type="text/javascript" src="js/debug-helper.js"></script>
<script type="text/javascript" src="js/jqgrid-helper.js"></script>

<!-- Base -->
<script type="text/javascript" src="js/apollo.js"></script>

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

</head>
<body>

<div id="top" class="ui-widget-header">
<div id="header">

<h1 class="title"><a href=".">Apollo-WS Client</a></h1>
</div>
<div id="nav">
<ul class="nav">
	<li><a id="instruction-link" href="javascript:createOrSelectInsturctionTab();">Help</a></li>
	<li><a href="disclaimer.html">SNOMED-CT Notice</a></li>
	<li><a href="http://code.google.com/p/apollo">Project Homepage</a></li>
	<li><a
		href="http://research.rods.pitt.edu/ApolloModelService/services/ServiceManagerPort?wsdl">WSDL</a></li>
	<li><a
		href="http://research.rods.pitt.edu/ApolloModelService/services/ServiceManagerPort?xsd=servicemanager_schema1.xsd">Schema</a></li>
</ul>

</div>
</div>

<div id="main">

<div id="LeftPane" class="ui-layout-west ui-widget ui-widget-content">
<div id="west-div">
	<div class="ui-widget-header ui-corner-all" style="margin:2px;padding:2px">
	Model Selection
	</div>
	<div id="jurisdiction-div" class="drop-box-div">
	<div>Jurisdiction:</div>
	<select id="jurisdiction-combo">
		<option value="UNDEF" selected="selected">--Please Select--</option>
		<option value="allegheny">Allegheny PA</option>
	</select>
	</div>
	<div id="snomed-ct-div" class="drop-box-div">
	<div>Disease/SNOMED-CT Code:</div>
	<select id="snomed-ct-combo" style="width: 120px;">
		<option value="UNDEF" selected="selected">--Please Select--</option>
		<option value="Infulenza">Influenza/0001</option>
		<option value="Anthrax">Anthrax/0002</option>
	</select>
	</div>
	<div id="model-div" class="drop-box-div">
	<div>Models:</div>
	<select id="model-combo" style="width: 120px;">
		<option value="UNDEF" selected="selected">--Please Select--</option>
	</select>
	</div>

	<img id="select-img" src="images/select.png" />
	<table id="west-grid"></table>

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
	<li><a href="#tabs-1">Welcome!</a></li>
</ul>
	<div id="tabs-1">
	Welcome to the Apollo Web Service Client! <br />
	Complete the model selection panel on the left to begin. <br />
	</div>
</div>
</div>
<!-- CenterPane -->

<div class="ui-layout-south">
<div>
<form id="apollo-form" action="apollo_exec.php" method="post"
	style="padding: 10px;">
<button id="create">Run Epidemic Model</button>
</form>
</div>
<div id="status-div"
	style="height: 40px; font-size: 12px; display: hidden; padding: 10px;"></div>
</div>
<!-- ButtomPane-->

</div>
<!-- End main -->

<div id="footer">
<div class="footer">
	&copy; 2011 University of Pittsburgh	</div>
</div>

</body>
</html>