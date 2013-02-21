/*

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

*/

/**
 * Base functions for apollo
 *
 * @author Yang Hu <yah14@pitt.edu>
 */

//----- global exchange area
//TODO : event communication?
	//TODO : on tab destory
//TODO : js access control?
var dataExchange = new function(){
	this.tab_input = null;
	this.tab_return = null;

	//for paramGrid
	this.gridId = '#west-grid';

	//for statusbar
	this.statusBar = '#status-div';
};
//-----

//--- Global Var
var maintab;
var lastEditId;

var InfluenzaId = 442696006;
var AnthraxId = 21927003;
//---

function clearParamGrid(){
	//unload grid first
	paramGrid = $(dataExchange.gridId);
	if (paramGrid.GridUnload)
		paramGrid.GridUnload();
	//get the empty div by id
	paramGrid = $(dataExchange.gridId);
	
	//hide the legend
	$('#param-legend').hide();
	
	//show the model selection img
	$('#select-img').show();
}

function loadParamGrid(snomed, modelType){
	//clear up
	clearParamGrid();
	
	//TODO : make it into a map
	var diseaseName = '';
	if (parseInt(snomed) == InfluenzaId)
		diseaseName = 'Influenza';
	else if (parseInt(snomed) == AnthraxId)
		diseaseName = 'Anthrax';
	
	//left panel
	paramGrid.jqGrid({
	    url: "apollo_param.php?model=" + modelType + "&snomed=" + snomed,
	    editurl: "edit.php",//dummy edit url
	    datatype: "json",
	    height: "auto",
	    pager: false,
	    loadui: "disable",
	    //cellEdit: true,
	    colNames: ["id", "", "Parameter Name", "Value", "url", "extra"],
	    colModel: [
	        {name: "id",width:1,hidden:true, key:true},
	        {name: "registered", width:50, resizable: false},
	        {name: "pname", width:600, resizable: true, sortable:false},
	        {name: "value", width:200, resizable: true, editable:true, 
	        	edittype:"text", editoptions:{
	        		size:10,
	        		maxlength: 15
	        	}
	        },
			{name: "url",width:1,hidden:true},
			{name: "extra",width:1,hidden:true}
	    ],
	    treeGrid: true,
		caption: diseaseName + ' ' + modelType + ' Model Parameters',
	    ExpandColumn: "pname",
	    autowidth: true,
//	    rowNum: 200,
	    ExpandColClick: true,
	    shrinkToFit: true,
	    treeIcons: {leaf:'ui-icon-blank'},
//	    treeIcons: {leaf:'ui-icon-document-b'},
	    ondblClickRow: function(rowid, iRow, iCol, e) { // open a new tab when double click
	
	        var treedata = $(this).getRowData(rowid);
	        
			//don't open a tab if the url is empty
			if (treedata.url == '' || treedata.url == null || treedata.url == undefined){
				var extraLength = treedata.extra.length;
				if (treedata.isLeaf === 'true'){
					$(this).editRow(rowid, true);
					lastEditId = rowid;
				}
			}else{
				var st = "#t" + treedata.id;
				if($(st).html() != null ) {
					maintab.tabs('select',st);
				} else {
					maintab.tabs('add', st, treedata.pname);
					$(st,"#tabs").load(treedata.url);
				}
			}
	    },
		gridComplete: function(){
			//enable the button
			$('#create').button( "option", "disabled", false );
	
			//gird load finish
			$(dataExchange.statusBar).html('Load finished!');
			
			//enable the legend
			$('#param-legend').show();
			
			//hide the model selection img
			$('#select-img').hide();
		},
		onSelectRow : function (rowid, status){
			if (lastEditId != -1){
				$(this).saveRow(lastEditId);
				lastEditId = -1;
			}
		}
	});
};

function createOrSelectInsturctionTab(){
	//add instruction tab
	
	//create the ins tab
	var insId = "#instruction";
	
	if($(insId).html() != null ) {
		//select the tab
		maintab.tabs('select', insId);
	}else{	
		maintab.tabs('add', insId, 'Help');
		//load the ins tab
		$(insId, "#tabs").load('instructions.html');
	}
}

function adjustMainDivSize(){
	var topHeight = $("#top").height();
	var footerHeight = $("#footer").height();
	
	//set the correct main height
	var winHeight = $(window).height();
	$('#main').height(winHeight - topHeight - footerHeight - 10);
}

//trigger the resize event make sure the whole web page to 100%
function bottomBlankFix(){
	$(window).trigger('resize');
}

jQuery(document).ready(function(){
	var jur = $('#jurisdiction-combo');
	jur.val('UNDEF');
	
	var snomed = $('#snomed-ct-combo');
	snomed.val('UNDEF');
	snomed.attr('disabled', 'disabled');
	
	var model = $('#model-combo');
	model.val('UNDEF');
	model.attr('disabled', 'disabled');
	
	jur.change(function(){
		//disable the create button
		$('#create').button( "option", "disabled", true );
		
		if ($(this).val() != 'UNDEF'){
			snomed.attr('disabled', '');
		}else {
			snomed.attr('disabled', 'disabled');
			snomed.val('UNDEF');
			model.attr('disabled', 'disabled');
			model.val('UNDEF');
			
			//unload grid
			clearParamGrid();
			
			//disable the run button
			$('#create').button( "option", "disabled", true );
		}
	});
	
	snomed.change(function(){
		//disable the create button
		$('#create').button( "option", "disabled", true );
		
		currVal = $(this).val();

		clearParamGrid();
		
		if (currVal != 'UNDEF'){
			model.attr('disabled', '');
			
			model.empty();
			
			model.append('<option value="UNDEF" selected="selected">--Please Select--</option>');
			
			if (parseInt (currVal) == InfluenzaId){
				model.append('<option value="Compartment">Compartment</option>');
//				model.append('<option value="AgentBased">AgentBased</option>');
			}else if (parseInt (currVal) == AnthraxId){
				model.append('<option value="Compartment">Compartment</option>');
			}
		}else {
			model.attr('disabled', 'disabled');
			
			model.val('UNDEF');
		}
	});
	
	//load model when model drop down change
	model.change(function(){
		//disable the create button
		$('#create').button( "option", "disabled", true );
		
		var modelType = $(this).val();
		if (modelType == 'UNDEF')
			return;

		//clear the tabs
		var tabLength = maintab.tabs('length');
		for (var i = 1; i <= tabLength; i++){
			maintab.tabs('remove', 1);
		}
		
		//hide the model selection img
		$('#select-img').hide();
		
		var snomed = $('#snomed-ct-combo').val();
		
		loadParamGrid(snomed, modelType);
	});
	
	//adjust the main content div size
	adjustMainDivSize();
	//TODO I don't know why there will exist some blank at the bottom
	setTimeout("bottomBlankFix()", 1000);

	//If the User resizes the window, adjust the #container height
	$(window).resize(adjustMainDivSize);	
	
	//layout splitter
	$('#main').layout({
		resizerClass : 'ui-state-default',
		west__size : $('body').innerWidth() * 0.22, //width for the left panel
        west__onresize: function (pane, $Pane) {
        	$(dataExchange.gridId).setGridWidth($Pane.innerWidth() - 20);
		}
	});
	//$.jgrid.defaults = $.extend($.jgrid.defaults,{loadui:"enable"});

	//center panel
	maintab = $('#tabs','#CenterPane').tabs({
		cache: false,
        add: function(e, ui) {
            // append close thingy
            $(ui.tab).parents('li:first')
                .append('<span class="ui-tabs-close ui-icon ui-icon-close" title="Close Tab"></span>')
                .find('span.ui-tabs-close')
                .click(function() {
                    maintab.tabs('remove', $('li', maintab).index($(this).parents('li:first')[0]));
            });
            // select just added tab
            maintab.tabs('select', '#' + ui.panel.id);
        }
    });
	
	//
	//createOrSelectInsturctionTab();

	// bind form using ajaxForm
	$('#apollo-form').ajaxForm({
		// target identifies the element(s) to
		// update with the server response
		// target: '#apollo-chart',
		// dataType identifies the expected
		// content type of the server response
		dataType : 'json',

		beforeSubmit : function(formData, jqForm, options) {
			//put the parameters value here
			try{
				// get the current tree grid data
				var grid = $(dataExchange.gridId);

//				var tmp = grid.find('input');
				if (grid.find('input').length != 0){
					$(dataExchange.statusBar).html('Please save before create.');
					return;
				}
//
				var rowData = grid.getRowData();
				var exportData = JSON.stringify(rowData);

				var snomed = $('#snomed-ct-combo').val();
				var modelType = $('#model-combo').val();

				// push the data to the server
				formData.push({
					name : 'SNOMED',
					value : snomed
				},{
					name : 'ModelType',
					value : modelType
				},{
					name : 'Parameters',
					value : exportData
				});

				// set the waiting feedback
				$('#create').button( "option", "disabled", true );
				$(dataExchange.statusBar).html('Wating for the server response..');

				return true;
			}catch (err){
				// set the error message
				$(dataExchange.statusBar).html(err);
				return false;
			}

		},

		// success identifies the function to
		// invoke when the server response
		// has been received;
		success : function(jasonObj, statusText) {
			if (statusText != 'success') {// web server error
				$(dataExchange.statusBar).html('Server error: <br/>' + statusText);
				return;
			}

			// web service error
			if (jasonObj.exception != null) {
				$(dataExchange.statusBar).html('Web service error: <br/>' + jasonObj.exception);
				return;
			}

			$(dataExchange.statusBar).html('Web service call ' + statusText);

			//enable the button
			$('#create').button( "option", "disabled", false );

			//fillin the result so that tab can get it
			dataExchange.tab_input = jasonObj;

			//create or select the tab for the result
			var resultID = "#result";

			if($(resultID).html() != null ) {
				//select the tab
				maintab.tabs('select', resultID);
				//clear current tab content
				$(resultID).empty();
			} else {
				//create the tab
				maintab.tabs('add', resultID, 'Result');
			}
			//load the tab
			$(resultID, "#tabs").load('result.html');
		}
	});

	//style the submit button
	$('#create').button({
		disabled : true //enable until everything is ready
	});
});