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
    this.model_urls = null;

    //for paramGrid
    this.gridId = '#west-grid';

    //for statusbar
    this.statusBar = '#status-div-3';
};

var diseaseExchange = new function(){
    this.tab_input = null;
    this.tab_return = null;

    //for paramGrid
    this.gridId = '#disease-grid';

//for statusbar
//    this.statusBar = '#status-div';
};
//-----

//--- Global Var
var maintab;
var lastEditId;
var finishedSimulators;
var numSimulators;
var combinedRunId;
var numberOfVisualizations;
var numberOfVisualizationsFinished;
var firstMessage = true;
var lastActiveTabId = null;
var currentActiveTabId = null;

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

var isRowEditable = function(id) {
    
    if (id == 0 || id == 3 || id == 7 || id == 14 || id == 20) {
        return false
    } else {
        return true;
    }
}

function loadParamGrid(){
    //clear up
    clearParamGrid();
        
    outerwidth=$('#west-div').width();
	
    //TODO : make it into a map
    //    var diseaseName = '';
    //    if (parseInt(snomed) == InfluenzaId)
    //        diseaseName = 'Influenza';
    //    else if (parseInt(snomed) == AnthraxId)
    //        diseaseName = 'Anthrax';
	
    //left panel
    paramGrid.jqGrid({
        url: "apollo_param.php",
        editurl: "edit.php",//dummy edit url
        datatype: "json",
        height: "auto",
        pager: false,
        loadui: "disable",
        //cellEdit: true,
        colNames: ["id", "Parameter Name", "Value", "url", "extra", "tooltip"],
        colModel: [
        {
            name: "id",
            width:0,
            hidden:true, 
            key:true
        },

        {
            name: "pname", 
            width: 325, 
            resizable: false, 
            sortable:false
        },

        {
            name: "value", 
            width: 100, 
            resizable: false, 
            editable:true, 
            sortable: false,
            edittype:"text", 
            editoptions:{
                size:10,
                maxlength: 15
            }
        },
        {
            name: "url",
            width:0,
            hidden:true
        },

        {
            name: "extra",
            width:0,
            hidden:true
        },
        
        {
            name: "tooltip",
            width:0,
            hidden:true
        }
        ],
        treeGrid: true,
        caption: 'Simulator Configuration',
        ExpandColumn: "pname",
        autowidth: true,
        rowNum: 36,
        ExpandColClick: true,
        shrinkToFit: true,
        treeIcons: {
            leaf:'ui-icon-blank'
        },
        
        loadComplete: function(data){
            var rowIDs = jQuery("#west-grid").getDataIDs();
            //        $(".jqgrow").addClass("ui-state-hover").css("background", "none !important");
            for (var i=0;i<rowIDs.length;i=i+1){ 
               
                var cellData = $("#west-grid").jqGrid('getCell', i, 'tooltip');
                $("#west-grid").jqGrid('setCell', i,'pname','','',{
                    'title':cellData
                });

                var rowData = $(this).getRowData(i);
                
                var trElement = jQuery("#"+ rowIDs[i],jQuery('#west-grid'));
        
                if (i==5) {
                    var cm = paramGrid.jqGrid('getColProp','value');
                    cm.edittype = 'select';
                    cm.editoptions = {
                        value: "day:day;hour:hour;millisecond:millisecond;minute:minute;month:month;second:second;year:year",
                        dataInit: function(elem) {
                            $(elem).width(75);  // set the width which you need
                        }
                    };
                    paramGrid.jqGrid('editRow', i);
                    cm.edittype = 'text';
                    cm.editoptions = null;
                }
        
                // Red       
                if (!isRowEditable(i)) {
                    trElement.removeClass('ui-widget-content');
                    trElement.addClass('Color_Red');
                }

            //                // Cyan        
            //                if (rowData.Estado == 2) {
            //                    trElement.removeClass('ui-widget-content');
            //                    trElement.addClass('Color_Cyan');
            //                }
            }
        },
        
        
        //	    treeIcons: {leaf:'ui-icon-document-b'},
        ondblClickRow: function(rowid, iRow, iCol, e) { // open a new tab when double click
	
            if (isRowEditable(rowid  - 1)) {
  
                var treedata = $(this).getRowData(rowid);
	        
                //don't open a tab if the url is empty
                if (treedata.url == '' || treedata.url == null || treedata.url == undefined){
                    var extraLength = treedata.extra.length;
                    if (treedata.isLeaf === 'true'){
                        $(this).editRow(rowid, true);
                        lastEditId = rowid;
                    }
                }else{
                    
                    // check if tab exists
                    var tabId = getTabIndex(treedata.pname);
                    if (tabId != null) {
                        $('#tabs').tabs({
                            active: tabId
                        });
                    } else {
                        $( "<li><a href='" + treedata.url + "'>" + treedata.pname + "</a><span class=\"ui-icon ui-icon-close\" title=\"Close tab\" style=\"cursor:pointer\" onclick=\"onCloseClicked(this)\"></span></li>" )
                        .appendTo( "#tabs .ui-tabs-nav" );
                        $( "#tabs" ).tabs( "refresh" );
                        $("#tabs").tabs( "option", "active", -1 );
                    }
                    
                //                    var st = "#t" + treedata.id;
                //                    if($(st).html() != null ) {
                //                        maintab.tabs('select',st);
                //                    } else {
                //                        maintab.tabs('add', st, treedata.pname);
                //                        $(st,"#tabs").load(treedata.url);
                //                    }
                }
            }
        },
        gridComplete: function(){
            //            //enable the button
            $('#create').button( "option", "disabled", false );
        //	
        //            //gird load finish
        //            $(dataExchange.statusBar).html('Load finished!');
        //			
        //            //enable the legend
        //            $('#param-legend').show();
        //			
        //            //hide the model selection img
        //            $('#select-img').hide();
        //            alert(outerwidth);
        },
        onSelectRow : function (rowid, status){
            
            //            paramGrid.jqGrid('resetSelection');
            if (lastEditId != -1) {
                $(this).saveRow(lastEditId);
                lastEditId = -1;
            }
        },
        
        beforeSelectRow: function(rowid, e) {
            //            alert(rowid);
            return isRowEditable(rowid - 1);
        }
    });
};
//
//function loadDiseaseGrid(snomed, modelType){
//    //clear up
//    //clear up
//    diseaseGrid = $(diseaseExchange.gridId);
//    if (diseaseGrid.GridUnload)
//        diseaseGrid.GridUnload();
//    //get the empty div by id
//    diseaseGrid = $(diseaseExchange.gridId);
//        
//    outerwidth=$('#west-div').width();
//	
//    //TODO : make it into a map
//    var diseaseName = '';
//    if (parseInt(snomed) == InfluenzaId)
//        diseaseName = 'Influenza';
//    else if (parseInt(snomed) == AnthraxId)
//        diseaseName = 'Anthrax';
//	
//    //left panel
//    diseaseGrid.jqGrid({
//        url: "apollo_param.php?model=" + modelType + "&snomed=" + snomed,
//        editurl: "edit.php",//dummy edit url
//        datatype: "json",
//        height: "auto",
//        pager: false,
//        loadui: "disable",
//        //cellEdit: true,
//        colNames: ["id", "Parameter Name", "Value", "url", "extra"],
//        colModel: [
//        {
//            name: "id",
//            width:0,
//            hidden:true, 
//            key:true
//        },
//
//        {
//            name: "pname", 
//            width:600, 
//            resizable: false, 
//            sortable:false
//        },
//
//        {
//            name: "value", 
//            width:300, 
//            resizable: false, 
//            editable:true, 
//            edittype:"text", 
//            editoptions:{
//                size:10,
//                maxlength: 15
//            }
//        },
//        {
//            name: "url",
//            width:0,
//            hidden:true
//        },
//
//        {
//            name: "extra",
//            width:0,
//            hidden:true
//        }
//        ],
//        treeGrid: true,
//        //        caption: diseaseName + ' ' + modelType + ' Model Parameters',
//        ExpandColumn: "pname",
//        autowidth: false,
//        //	    rowNum: 200,
//        ExpandColClick: true,
//        shrinkToFit: true,
//        treeIcons: {
//            leaf:'ui-icon-blank'
//        },
//        //	    treeIcons: {leaf:'ui-icon-document-b'},
//        ondblClickRow: function(rowid, iRow, iCol, e) { // open a new tab when double click
//	
//            
//        
//            var treedata = $(this).getRowData(rowid);
//	        
//            //don't open a tab if the url is empty
//            if (treedata.url == '' || treedata.url == null || treedata.url == undefined){
//                var extraLength = treedata.extra.length;
//                if (treedata.isLeaf === 'true'){
//                    $(this).editRow(rowid, true);
//                    lastEditId = rowid;
//                }
//            }else{
//                var st = "#t" + treedata.id;
//                if($(st).html() != null ) {
//                    maintab.tabs('select',st);
//                } else {
//                    maintab.tabs('add', st, treedata.pname);
//                    $(st,"#tabs").load(treedata.url);
//                }
//            }
//        },
//        gridComplete: function(){
//        //                    //enable the button
//        //                    $('#create').button( "option", "disabled", false );
//        //        	
//        //                    //gird load finish
//        //                    $(dataExchange.statusBar).html('Load finished!');
//        //        			
//        //                  //enable the legend
//        //                    $('#param-legend').show();
//        //        			
//        //                    //hide the model selection img
//        //                    $('#select-img').hide();
//        },
//        onSelectRow : function (rowid, status){
//            
//            if (lastEditId != -1){
//                $(this).saveRow(lastEditId);
//                lastEditId = -1;
//            }
//        }
//    }).setGridWidth(outerwidth-20);
//};

function removeTab(tabindex) {
    // first activate previous tab
//    $("#tabs").tabs( "option", "active", tabindex - 1);
    //
    var tab = $( "#tabs" ).find( ".ui-tabs-nav li:eq(" + tabindex + ")" ).remove();
    // Find the id of the associated panel
    var panelId = tab.attr( "aria-controls" );
    // Remove the panel
    $( "#" + panelId ).remove();
    // Refresh the tabs widget
    $( "tabs" ).tabs( "refresh" );
}

function createOrSelectInstructionTab(){
    //add instruction tab

    var tabId = getTabIndex("Help");
    if(tabId != null) {
        //        //select the tab
        ////        maintab.tabs('select', insId);
        $('#tabs').tabs({
            active: tabId
        });
    } else {	
        //        maintab.tabs('add', insId, 'Help');
        //        //load the ins tab
        //        $(insId, "#tabs").load('instructions.html');
        $( "<li><a href='instructions.html'border=0>Help</a><span class=\"ui-icon ui-icon-close\" title=\"Close tab\" style=\"cursor:pointer\" onclick=\"onCloseClicked(this)\"></span></li>" )
        .appendTo( "#tabs .ui-tabs-nav" );
        $( "#tabs" ).tabs( "refresh" );
        $("#tabs").tabs( "option", "active", -1 );
    }
}

function getIndex(sender)
{   
    var currentLi = sender.parentNode;
    var index = null;
    var aElements = sender.parentNode.parentNode.getElementsByTagName("li");
    var aElementsLength = aElements.length;

    for (var i = 0; i < aElementsLength; i++)
    {
        console.log(aElements[i]);
        if (aElements[i] == currentLi) //this condition is never true
        {
            index = i;
            return index;
        }
    }
    
    return index;
}

function getTabIndexByAriaLabelledById(labelledbyId) {
    var id = null;
    $('#tabs ul li').each(function(i) {
        console.log(this);
        if (this.getAttribute("aria-labelledby") == lastActiveTabId) {
            id = i;
        }
    }); 
    
    return id;
}

function selectLastActiveTab() {
    
    console.log(lastActiveTabId);
    var id = getTabIndexByAriaLabelledById(lastActiveTabId);
    console.log(id);
    $("#tabs").tabs( "option", "active", id);
    
}

function onCloseClicked(elem) {
    selectLastActiveTab();
    var tabindex = getTabIndexByAriaLabelledById(currentActiveTabId);
    alert(tabindex);
    removeTab(tabindex);
//        alert('activiating tab ' + (tabindex - 1));
}

function getTabIndex(tabName) {
    var id;
    $('#tabs ul li a').each(function(i) {
        if (this.text == tabName) {
            id = i;
        }
    }); 
    
    return id;
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

function loadRegisteredModels(){
    $.ajax({
        type: "GET",
        url: "registered_models.php",

        async: true, /* If set to non-async, browser shows page as "Loading.."*/
        cache: false,
        timeout:50000, /* Timeout in ms */

        success: function(jasonObj, statusText){ /* called when request to barge.php completes */

            jasonObj = $.parseJSON(jasonObj);
            
            var model = $('#model-combo');
            model.val('UNDEF');
            //            model.attr('disabled', '');
            //            //			
            model.empty();
            
            for (var i=0; i <jasonObj.data.length; i++) {
            
                if (jasonObj.data[i].hasOwnProperty('simulatorIdentification')) {
                    var simDev = jasonObj.data[i].simulatorIdentification['simulatorDeveloper'];
                    var simName = jasonObj.data[i].simulatorIdentification['simulatorName'];
                    var simVer = jasonObj.data[i].simulatorIdentification['simulatorVersion'];
                    model.append('<option value="' + encodeURIComponent(JSON.stringify(jasonObj.data[i])) + '">' + simDev + ',' + simName + ',' + simVer + '</option>');
                }
            }
            
        //            var test = JSON.stringify(jasonObj.data[0]);
        //            model.append('<option value="' + encodeURIComponent(test.toString().replace('FRED', 'TEST')) + '">UPitt,PSC,CMU,TEST,2.0.1</option>');
        
       
        //            jasonObj = $.parseJSON(jasonObj);
        //            var diseaseStatesUrl = jasonObj.data['disease_states_url'];
        //            var incidenceUrl = jasonObj.data['incidence_url'];
        //            var dev = jasonObj.data['visualizerDeveloper'];
        //            var name = jasonObj.data['visualizerName'];
        //            var ver = jasonObj.data['visualizerVersion'];
        //
        //            waitForVisualizations(runId, dev, name, ver, diseaseStatesUrl, incidenceUrl);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            addmsg(textStatus + " (" + errorThrown + ")");
            setTimeout(
                poll, /* Try again after.. */
                15000); /* milliseconds (15seconds) */
        }
    });
}

jQuery(document).ready(function(){
    //    var jur = $('#jurisdiction-combo');
    //    jur.val('UNDEF');
    loadRegisteredModels();
    //    var snomed = $('#snomed-ct-combo');
    //    snomed.val('UNDEF');
    //    snomed.attr('disabled', 'disabled');
	
    var model = $('#model-combo');
    //        model.val('UNDEF');
    //            model.attr('disabled', '');
    //    //			
    //        model.empty();
			
    //    model.append('<option value="UNDEF" selected="selected">--Please Select--</option>');
    //    model.append('<option value="Compartment">Compartment</option>');
    //    model.append('<option value="FRED">FRED</option>');
    //        model.attr('disabled', 'disabled');
	
    //    jur.change(function(){
    //        //disable the create button
    //        $('#create').button( "option", "disabled", true );
    //		
    //        if ($(this).val() != 'UNDEF'){
    //            snomed.attr('disabled', '');
    //        }else {
    //            snomed.attr('disabled', 'disabled');
    //            snomed.val('UNDEF');
    //            model.attr('disabled', 'disabled');
    //            model.val('UNDEF');
    //			
    //            //unload grid
    //            clearParamGrid();
    //			
    //            //disable the run button
    //            $('#create').button( "option", "disabled", true );
    //        }
    //    });
	
    //    snomed.change(function(){
    //        //disable the create button
    //        $('#create').button( "option", "disabled", true );
    //		
    //        currVal = $(this).val();
    //
    //        clearParamGrid();
    //		
    //        if (currVal != 'UNDEF'){
    //            model.attr('disabled', '');
    //			
    //            model.empty();
    //			
    //            model.append('<option value="UNDEF" selected="selected">--Please Select--</option>');
    //			
    //            if (parseInt (currVal) == InfluenzaId){
    //                model.append('<option value="Compartment">Compartment</option>');
    //            //				model.append('<option value="AgentBased">AgentBased</option>');
    //            }else if (parseInt (currVal) == AnthraxId){
    //                model.append('<option value="Compartment">Compartment</option>');
    //            }
    //        }else {
    //            model.attr('disabled', 'disabled');
    //			
    //            model.val('UNDEF');
    //        }
    //    });
  
        
    //        $('#select-img').hide();
    //load model when model drop down change
    //    model.change(function(){
    //        //disable the create button
    //        $('#create').button( "option", "disabled", true );
    //		
    ////        var modelType = $(this).val();
    ////        if (modelType == 'UNDEF')
    ////            return;
    //
    //        //clear the tabs
    //        var tabLength = maintab.tabs('length');
    //        for (var i = 1; i <= tabLength; i++){
    //            maintab.tabs('remove', 1);
    //        }
    //		
    //        //hide the model selection img
    ////        $('#select-img').hide();
    //		
    //        //        var snomed = $('#snomed-ct-combo').val();
    //		
    //        loadParamGrid();
    //    //        loadDiseaseGrid(snomed, modelType);
    //    });
	
    //adjust the main content div size
    adjustMainDivSize();
    //TODO I don't know why there will exist some blank at the bottom
    setTimeout("bottomBlankFix()", 50);

    //If the User resizes the window, adjust the #container height
    $(window).resize(adjustMainDivSize);	
	
    //layout splitter
    $('#main').layout({
        resizerClass : 'ui-state-default',
        west__size : $('body').innerWidth() * 0.25, //width for the left panel
        west__resizable : false,
        west__closable : false,
        south__resizable : false,
        south__closable : false,
        west__onresize: function (pane, $Pane) {
            $(dataExchange.gridId).setGridWidth($('#model-selection-div').innerWidth());
        }
    });  
    
    loadParamGrid();
    //$.jgrid.defaults = $.extend($.jgrid.defaults,{loadui:"enable"});

    //center panel
    maintab = $('#tabs','#CenterPane').tabs({
        closable: true,
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
        },
        activate: function( event, ui ) {
            //            alert('You chose tab index '+ ui.newTab);
            //            console.log(ui.newTab.context.getAttribute("id"));
            lastActiveTabId = currentActiveTabId;
            currentActiveTabId = ui.newTab.context.getAttribute("id");
        //            var tabname = ui.tab.text;
        //            runMethod(tabname);
        }
    });
   
    
    function clearTabs() {
        for (var i = $('#tabs >ul >li').size() - 1; i >= 1; i--) {
            //            $('#tabs','#CenterPane').tabs('remove', i); // deprecated
            removeTab(i);
        }
    }
    
    function runMethod(tabindex) {
        
        switch (tabindex) {
            
            case "Disease states over time":
                $("#result", "#tabs").load('result.html');
                break;

            case "Incidence over time":
                $("#incidence", "#tabs").load('incidence.html');
                break;
                
            default:
                break;
        }
    };
    
    function addmsg(msg){
        /* Simple helper to add a div.
        type is the name of a CSS class (old/new/error).
        msg is the contents of the div */
        
        // copy messages from status divs 1 and 2 to 2 and 3, respectively
        var status3Html = $("#status-div-3").html();
        var status2Html = $("#status-div-2").html();
        
        $("#status-div-1").html(status2Html);
        $("#status-div-2").html(status3Html);
        $("#status-div-3").html(msg);
    //        var terminal = document.getElementById('terminal');
    //        terminal.value = terminal.value + "\n" + msg;
    //        var message;
    //        if (firstMessage == true) {
    //            message = msg;
    //            firstMessage = false;
    //        } else {
    //            message = CKEDITOR.instances.terminal.getData() + "<br>" + msg;
    //        }
    //
    //        var editor = CKEDITOR.instances.terminal;
    //        editor.setData(message, function() {
    //            editor.focus();
    //
    //            var s = editor.getSelection(); // getting selection
    //            var selected_ranges = s.getRanges(); // getting ranges
    //            var node = selected_ranges[0].startContainer; // selecting the starting node
    //            var parents = node.getParents(true);
    //
    //            node = parents[parents.length - 2].getFirst();
    //
    //            while (true) {
    //                var x = node.getNext();
    //                if (x == null) {
    //                    break;
    //                }
    //                node = x;
    //            }
    //
    //            s.selectElement(node);
    //            selected_ranges = s.getRanges();
    //            selected_ranges[0].collapse(false);  //  false collapses the range to the end of the selected node, true before the node.
    //            s.selectRanges(selected_ranges);  // putting the current selection there
    //        }
    //                
    //        );
    }
    
    function positionCursor(editor) {
        var s = editor.getSelection(); // getting selection
        var selected_ranges = s.getRanges(); // getting ranges
        var node = selected_ranges[0].startContainer; // selecting the starting node
        var parents = node.getParents(true);

        node = parents[parents.length - 2].getFirst();

        while (true) {
            var x = node.getNext();
            if (x == null) {
                break;
            }
            node = x;
        }

        s.selectElement(node);
        selected_ranges = s.getRanges();
        selected_ranges[0].collapse(false);  //  false collapses the range to the end of the selected node, true before the node.
        s.selectRanges(selected_ranges);  // putting the current selection there
    }
    
    function addZero(n) {
        
        return n<10? '0'+n:''+n;
    }
    
    function startVisualization(runId, simName, modelIndex, vizDev, vizName, vizVer) {
        
        $.ajax({
            type: "GET",
            url: "exec_visualization.php?runId=" + runId + "&vizDev=" + vizDev + "&vizName=" + vizName + "&vizVer=" + vizVer,

            async: true, /* If set to non-async, browser shows page as "Loading.."*/
            cache: false,
            timeout:50000, /* Timeout in ms */

            success: function(jasonObj, statusText){ /* called when request to barge.php completes */
                jasonObj = $.parseJSON(jasonObj);
                
                var urls = jasonObj.data['urls'];
                var dev = jasonObj.data['visualizerDeveloper'];
                var name = jasonObj.data['visualizerName'];
                var ver = jasonObj.data['visualizerVersion'];

                waitForVisualizations(runId, dev, name, ver, urls, simName, modelIndex, vizName);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                if (textStatus == 'timeout') {
                    var date = new Date();
                    addmsg(addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds()) 
                        + " <b> ERROR: </b>" + "Could not call run on " + vizName + ", please run epidemic simulator again.");
                //                }
                //                                setTimeout(
                //                                    poll, /* Try again after.. */
                //                                    15000); /* milliseconds (15seconds) */
                }
            }
        });
    }
    
    function waitForVisualizations(runId, dev, name, ver, urls, simName, modelIndex, vizName) {
        
        $.ajax({
            type: "GET",
            url: "poller.php?runId=" + runId + "&dev=" + dev + "&name=" + name + "&ver=" + ver + "&type=visualization",

            async: true, /* If set to non-async, browser shows page as "Loading.."*/
            cache: false,
            timeout:50000, /* Timeout in ms */

            success: function(jasonObj, statusText){ /* called when request to barge.php completes */
                jasonObj = $.parseJSON(jasonObj);
                //                alert(jasonObj);
                var status = jasonObj.data['status'];
                var message = jasonObj.data['message'];
                var date = new Date();
                
                addmsg(addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds()) + "<b> SIMULATOR: </b><i>" + simName + "</i>" + " <b>VISUALIZER STATUS: </b><i>" + status  + " </i><b>MESSAGE: </b><i>" + message + "</i>"); /* Add response to a .msg div (with the "new" class)*/

                if (status != 'completed') {
                    setTimeout(
                        function() {
                            waitForVisualizations(runId, dev, name, ver, urls, simName, modelIndex, vizName)
                        }, /* Request next message */
                        5000 /* ..after 1 seconds */
                        );
                } else {
                    
                    numberOfVisualizationsFinished += 1;
                    var urlList = new Object();
                    
                    for (var key in urls) {
                        
                        if (key == 'Disease states') {
                            urlList.disease_states = urls[key];
                        } else if (key == 'Incidence' || key == 'Combined incidence') {
                            urlList.incidence = urls[key];
                        } else {
                            urlList.gaia = urls[key];
                        }
                        
                    }
                    
                    var encUrls = JSON.stringify(urlList);
                    
                    // call function passed in as parameter
                    //                    $(dataExchange.statusBar).html('Web service call ' + statusText);

                    if (numberOfVisualizationsFinished == numberOfVisualizations) { // enable the button when the last visualization finishes
                        //enable the button
                        $('#create').button( "option", "disabled", false );
                    }

                    //fillin the result so that tab can get it
                    dataExchange.model_urls[modelIndex] = encUrls;

                    //create or select the tab for the result
                    var resultID = "#result-" + simName;
                    var incidenceID = "#incidence-" + simName;
                    var gaiaID = "#gaia-" + simName;
                    var combinedIncidenceID = "#combined-incidence";

                    for (key in urls) {


                        if (key == 'Disease states') {
                            //                            if($(resultID).html() != null ) {
                            ////                                //select the tab
                            ////                                maintab.tabs('select', resultID);
                            ////                                //clear current tab content
                            ////                                $(resultID).empty();
                            //                            } else {
                            //create the tab
                            // maintab.tabs('add', resultID, simName + ': Disease states over time'); // deprecated
                            $( "<li><a href='result.php?index=" + modelIndex + "'>" + simName + ": Disease states over time</a><span class=\"ui-icon ui-icon-close\" title=\"Close tab\" style=\"cursor:pointer\" onclick=\"onCloseClicked(this)\"></span></li>" )
                            .appendTo( "#tabs .ui-tabs-nav" );
                            $( "#tabs" ).tabs( "refresh" );
                            $("#tabs").tabs( "option", "active", -1 );
                        //                            }
                    
                    
                        //load the tab
                        //                            $(resultID, "#tabs").load('result.php?index=' + modelIndex);
                        } else if (key == 'Incidence') {
                            //                            if ($(incidenceID).html() != null) {
                            ////                                // select the tab
                            ////                                maintab.tabs('select', incidenceID);
                            ////                                //clear current tab content
                            ////                                $(incidenceID).empty();
                            //                            } else {
                            // create the tab
                            //  maintab.tabs('add', incidenceID, simName + ': Incidence over time'); // deprecated
                            $( "<li><a href='incidence.php?index=" + modelIndex + "'>" + simName + ": Incidence over time</a><span class=\"ui-icon ui-icon-close\" title=\"Close tab\" style=\"cursor:pointer\" onclick=\"onCloseClicked(this)\"></span></li>" )
                            .appendTo( "#tabs .ui-tabs-nav" );
                            $( "#tabs" ).tabs( "refresh" );
                            $("#tabs").tabs( "option", "active", -1 );
                        //                            }
                        
            
                        // load the tab
                        //                            $(incidenceID, "#tabs").load('incidence.php?index=' + modelIndex);
                        } else if (key == 'Combined incidence') {
                            //                            if ($(combinedIncidenceID).html() != null) {
                            //                                // select the tab
                            //                                maintab.tabs('select', combinedIncidenceID);
                            //                                //clear current tab content
                            //                                $(combinedIncidenceID).empty();
                            //                            } else {
                            // create the tab
                            //                                maintab.tabs('add', combinedIncidenceID, 'All simulators: Incidence over time'); // deprecated
                            $( "<li><a href='incidence.php?index=" + modelIndex + "'>All simulators: Incidence over time</a><span class=\"ui-icon ui-icon-close\" title=\"Close tab\" style=\"cursor:pointer\" onclick=\"onCloseClicked(this)\"></span></li>" )
                            .appendTo( "#tabs .ui-tabs-nav" );
                            $( "#tabs" ).tabs( "refresh" );
                            $("#tabs").tabs( "option", "active", -1 );
                        //                            }
                        
            
                        // load the tab
                        //                            $(combinedIncidenceID, "#tabs").load('incidence.php?index=' + modelIndex);
                        }
                    
                        if (simName == 'FRED' && key == 'GAIA animation of Allegheny County') {
                            //                            //                            if($(gaiaID).html() != null ) {
                            //                                //select the tab
                            //                                maintab.tabs('select', gaiaID);
                            //                                //clear current tab content
                            //                                $(gaiaID).empty();
                            //                            } else {
                            //create the tab
                            //                                maintab.tabs('add', gaiaID, simName + ': GAIA Visualization for Simulation'); // deprecated
                            $( "<li><a href='gaia.php?index=" + modelIndex + "'>" + simName + ": GAIA Visualization for Simulation</a><span class=\"ui-icon ui-icon-close\" title=\"Close tab\" style=\"cursor:pointer\" onclick=\"onCloseClicked(this)\"></span></li>" )
                            .appendTo( "#tabs .ui-tabs-nav" );
                            $( "#tabs" ).tabs( "refresh" );
                            $("#tabs").tabs( "option", "active", -1 );
                        //                            }
                    
                    
                        //load the tab
                        //                            $(gaiaID, "#tabs").load('gaia.php?index=' + modelIndex);
                        }
                 
                    }
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                if (textStatus == 'timeout') {
                    var date = new Date();
                    addmsg(addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds()) 
                        + "<b> ERROR: </b>" + "Could not call getStatus on " + vizName + ", retrying in 5 seconds.");
                    setTimeout(
                        function() {
                            waitForVisualizations(runId, dev, name, ver, urls, simName, modelIndex, vizName)
                        }, /* Request next message */
                        5000 /* ..after 1 seconds */
                        );
                }
            }
        });
        

    }
    
    function waitForSimulationsAndStartVisualizations(obj, modelIndex) {
        
        var runId = obj['runId'];
        var simDev = obj['simulatorDeveloper'];
        var simName = obj['simulatorName'];
        var simVer = obj['simulatorVersion'];
        
        /* This requests the url "msgsrv.php"
        When it complete (or errors)*/
        $.ajax({
            type: "GET",
            url: "poller.php?runId=" + runId + "&dev=" + simDev + "&name=" + simName + "&ver=" + simVer + "&type=simulator",

            async: true, /* If set to non-async, browser shows page as "Loading.."*/
            cache: false,
            timeout:50000, /* Timeout in ms */

            success: function(jasonObj, statusText){ /* called when request to barge.php completes */
                jasonObj = $.parseJSON(jasonObj);
                var status = jasonObj.data['status'];
                var message = jasonObj.data['message'];
                
                var date = new Date();
                
                addmsg(addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds()) + "<b> SIMULATOR: </b><i>" + simName + "</i>" + " <b>SIMULATOR STATUS: </b><i>" + status  + " </i><b>MESSAGE: </b><i>" + message + "</i>"); /* Add response to a .msg div (with the "new" class)*/
                
                if (status != 'completed') {
                    setTimeout(
                        function() {
                            waitForSimulationsAndStartVisualizations(obj, modelIndex)
                        }, /* Request next message */
                        5000 /* ..after 1 seconds */
                        );
                } else {
                    finishedSimulators++;
                    startVisualization(runId, simName, modelIndex, 'nick', 'viztest', '1.0');
                    if (simName == 'FRED') {
                        startVisualization(runId, simName, modelIndex, 'PSC', 'GAIA', '1.0');
                    }
                    //                    waitForVisualizations();
                                    
                    if (numSimulators > 1 && finishedSimulators == numSimulators) { // now all simulators have finished
                        startVisualization(combinedRunId, 'All simulators', numSimulators + 1, 'nick', 'viztest', '1.0');
                    }
                 
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                //                addmsg(textStatus);
                if (textStatus == 'timeout') {
                    var date = new Date();
                    addmsg(addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds()) 
                        + "<b> Error: </b>" + "Could not call getStatus on " + simName + ", retrying in 5 seconds.");
                    setTimeout(
                        function() {
                            waitForSimulationsAndStartVisualizations(obj, modelIndex)
                        }, /* Request next message */
                        5000 /* ..after 1 seconds */
                        );
                }
            }
        });
        
    }
	
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
        timeout:50000, /* Timeout in ms */

        beforeSubmit : function(formData, jqForm, options) {
            //put the parameters value here
            try{
                
                clearTabs();
                // get the current tree grid data
                var grid = $(dataExchange.gridId);

                //				var tmp = grid.find('input');
                //                if (grid.find('input').length != 0){
                //                    $(dataExchange.statusBar).html('Please save before create.');
                //                    return;
                //                }
                //
                var rowData = grid.getRowData(); 
                var timeStepUnit = $("#5_value").val(); // get the time step unit from the select
                rowData[4]['value'] = timeStepUnit; // set the time step unit to store the value instead of html
                var exportData = JSON.stringify(rowData);

                var snomed = $('#snomed-ct-combo').val();
                var simulatorArray = $('#model-combo').val().toString().split(",");
                
                var simDev = '';
                var simName = '';
                var simVer = '';
                
                for (var i = 0; i < simulatorArray.length; i++) {
                    var obj = $.parseJSON(decodeURIComponent(simulatorArray[i]));
                    simDev += "," + encodeURIComponent(obj.simulatorIdentification['simulatorDeveloper']);
                    simName += "," + encodeURIComponent(obj.simulatorIdentification['simulatorName']);
                    simVer += "," + encodeURIComponent(obj.simulatorIdentification['simulatorVersion']);
                }
                
                dataExchange.model_urls = new Array();
                
                simDev = simDev.substring(1);
                simName = simName.substring(1);
                simVer = simVer.substring(1);

                // push the data to the server
                formData.push({
                    name : 'SNOMED',
                    value : snomed
                },{
                    name : 'Parameters',
                    value : exportData
                },{
                    name : 'simulatorDeveloper',
                    value : simDev
                },{
                    name : 'simulatorName',
                    value : simName
                },{
                    name : 'simulatorVersion',
                    value : simVer
                });

                // set the waiting feedback
                $('#create').button( "option", "disabled", true );
                addmsg('Waiting for the server response..');

                return true;
            }catch (err){
                // set the error message
                addmsg("Error submitting form: " + err);
                return false;
            }

        },


        // success identifies the function to
        // invoke when the server response
        // has been received;
        success : function(jasonObj, statusText) {
            if (statusText != 'success') {// web server error
                addmsg('Server error: ' + statusText);
                return;
            }

            // web service error
            if (jasonObj.exception != null) {
                addmsg('Web service error: ' + jasonObj.exception);
                return;
            }

            numSimulators = jasonObj.data.length;
            finishedSimulators = 0;
            numberOfVisualizations = 0;
            numberOfVisualizationsFinished = 0;
            var allRunIds = '';
            if (numSimulators > 1) {
                numberOfVisualizations += 1; // one for the combined incidence
            }
            
            for (var i = 0; i < numSimulators; i++) {

                var simulatorObj = jasonObj.data[i];
                
                var runId = simulatorObj['runId'];
                var simName = simulatorObj['simulatorName'];
                allRunIds += ':' + runId;
                
                numberOfVisualizations += 1;
                if (simName == 'FRED') {
                    numberOfVisualizations += 1; // one for gaia
                }

                waitForSimulationsAndStartVisualizations(simulatorObj, i);
            }

            combinedRunId = allRunIds.substring(1);
            
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){

            if (textStatus == 'timeout') { 
                var date = new Date();
                addmsg(addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds()) 
                    + " <b> ERROR: </b>" + "Could not call run for simulators, please run epidemic simulators again.");
            //                setTimeout(
            //                    poll, /* Try again after.. */
            //                    15000); /* milliseconds (15seconds) */
            }
        }
    });

    //style the submit button
    $('#create').button({
        disabled : true //enable until everything is ready
    });
});