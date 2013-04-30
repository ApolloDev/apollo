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
    this.model_urls = {};

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
var combinedRunNumber;
var numberOfVisualizations;
var numberOfVisualizationsFinished;
var firstLinePrinted = true;

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
    
    if (id == 0 || id == 3 || id == 7 || id == 9 || id == 14 || id == 20 || id == 57) {
        return false
    } else {
        return true;
    }
}

var isRowBlue = function(id) {
    
    if (id == 0 || id == 3 || id == 7 || id == 14 || id == 20) {
        return true;
    } else {
        return false;
    }
}

var isRowTree = function(id) {
    
    if (id == 22 || id == 32 || id == 42 || id == 43 || id == 52) {
        return true;
    } else {
        return false;
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
    
    var createRadioButtonChecked = function(value) {
        return $("<input type='radio' name='radio1' id='reactiveCm' checked />").get();
    }
    var createRadioButton = function(value) {
        return $("<input type='radio' name='radio1' id='fixedCm' />").get();
    }
    
    var extractFromRadioButton = function(elem) {
        return $(elem).val();
    }
    
    paramGrid.jqGrid({
        url: "apollo_param.php",
        editurl: "edit.php",//dummy edit url
        datatype: "json",
        height: "auto",
        pager: false,
        loadui: "disable",
        //cellEdit: true,
        colNames: ["id", "Parameter Name", "Value&nbsp;&nbsp;&nbsp;", "url", "extra",  "tooltip"],
        colModel: [
        {
            name: "id",
            width:0,
            hidden:true, 
            key:true
        },

        {
            name: "pname", 
            width:370, 
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
        treeGridModel: 'nested',
        caption: 'Simulator Configuration',
        ExpandColumn: "pname",
        autowidth: true,
        rowNum: 65,
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
                    'title':cellData.toString().trim()
                });
                
                var rowData = $(this).getRowData(i);
               
                
                var trElement = jQuery("#"+ rowIDs[i],jQuery('#west-grid'));
        
                if (i==5) { // TIME STEP UNIT
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
                
                if (i==35) { // VACCINATION NAMED PRIORITIZATION SCHEME
                    var cm = paramGrid.jqGrid('getColProp','value');
                    cm.edittype = 'select';
                    cm.editoptions = {
                        value: "None:None;ACIP:ACIP;tpp:Described",
                        dataInit: function(elem) {
                            $(elem).width(88);  // set the width which you need
                        }
                    };
                    paramGrid.jqGrid('editRow', i);
                    cm.edittype = 'text';
                    cm.editoptions = null;
                }
                
                if (i==46 || i ==55) { // SCHOOL CLOSURE TARGET FACILITIES
                    var cm = paramGrid.jqGrid('getColProp','value');
                    cm.edittype = 'select';
                    cm.editoptions = {
                        value: "all:All;individual:Individual",
                        dataInit: function(elem) {
                            $(elem).width(75);  // set the width which you need
                        }
                    };
                    paramGrid.jqGrid('editRow', i);
                    cm.edittype = 'text';
                    cm.editoptions = null;
                }
                
                //                if (i==28) { // ANTIVIRAL NAMED PRIORITIZATION SCHEME
                //                    var cm = paramGrid.jqGrid('getColProp','value');
                //                    cm.edittype = 'select';
                //                    cm.editoptions = {
                //                        value: "none:None",
                //                        dataInit: function(elem) {
                //                            $(elem).width(75);  // set the width which you need
                //                        }
                //                    };
                //                    paramGrid.jqGrid('editRow', i);
                //                    cm.edittype = 'text';
                //                    cm.editoptions = null;
                //                }
                
                if (i == 22 || i == 32 || i ==42) {
                    var cm = paramGrid.jqGrid('getColProp','value');
                    cm.edittype = 'checkbox';
                    cm.editoptions = {
                        value:"True:False"
                    };
                    //                    cm.formatter = "checkbox";
                    //                    cm.editable = true;
                    //                    cm.formatoptions = {disabled: false};
                    
                    paramGrid.jqGrid('editRow', i);
                    cm.edittype = 'text';
                    cm.editoptions = null;
                }
                
                if (i == 43 || i == 52) {
                    
                    var customElement;
                    if (i == 43) {
                        customElement = createRadioButtonChecked;
                    } else {
                        customElement = createRadioButton;
                    }
                    
                    var cm = paramGrid.jqGrid('getColProp','value');
                    cm.edittype = 'custom';
                    cm.editoptions = {
                        custom_element: customElement,
                        custom_value: extractFromRadioButton
                    };
                    //                    cm.formatter = "checkbox";
                    //                    cm.editable = true;
                    //                    cm.formatoptions = {disabled: false};
                    
                    paramGrid.jqGrid('editRow', i);
                    cm.edittype = 'text';
                    cm.editoptions = null;
                }
                
        
                // Red       
                if (!isRowEditable(i) && isRowBlue(i)) {
                    trElement.removeClass('ui-widget-content');
                    trElement.addClass('Color_Red');
                }

            //                // Cyan        
            //                if (rowData.Estado == 2) {
            //                    trElement.removeClass('ui-widget-content');
            //                    trElement.addClass('Color_Cyan');
            //                }
            }
            
            
        //            $('input.myClass').prettyCheckboxes();
        },

        
        //	    treeIcons: {leaf:'ui-icon-document-b'},
        ondblClickRow: function(rowid, iRow, iCol, e) { // open a new tab when double click
        
   
        
            if (isRowEditable(rowid  - 1)) {
                
                if (rowid == 9) {
                    $(this).jqGrid('editRow', rowid, true, null, null, null, {}, function (rowid) {
                  
                        var value = paramGrid.jqGrid('getCell',rowid,'value');
                        changeDiseaseStateValues(value);
                  
                    }); 
                }
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
            }
        },
        
        gridComplete: function(){
            //            //enable the button
            $('#create').button( "option", "disabled", false );
            $(dataExchange.gridId).setGridWidth($('#model-selection-div').innerWidth());
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
            if (rowid !== lastEditId && !isRowTree(rowid)) { // make sure row is not a tree or the value column will be affected
                paramGrid.jqGrid('saveRow', lastEditId, null, null, null, function(){
                    var value = paramGrid.jqGrid('getCell',lastEditId,'value');
                    changeDiseaseStateValues(value);    
                }, null, null);
                lastEditId = rowid;
            }
        },
        
        beforeSelectRow: function(rowid, e) {
            //            alert(rowid);
            return isRowEditable(rowid - 1);
        }
    });
    
    var orgExpandNode = $.fn.jqGrid.expandNode,
    orgCollapseNode = $.fn.jqGrid.collapseNode;
    $.jgrid.extend({
        expandNode: function (rc) {
            //            alert('before expandNode: rowid="' + rc._id_ + '", name="' + rc.name + '"');
            $(dataExchange.gridId).setGridWidth($('#model-selection-div').innerWidth());
            return orgExpandNode.call(this, rc);
        },
        collapseNode: function (rc) {
            //            alert('before collapseNode: rowid="' + rc._id_ + '", name="' + rc.name + '"');
            $(dataExchange.gridId).setGridWidth($('#model-selection-div').innerWidth());
            return orgCollapseNode.call(this, rc);
        }
    });
    
};

function changeDiseaseStateValues(popLocationValue) {
    
    var pop;
    if (popLocationValue.toLowerCase() == 'usa') {
        pop = 300000000;
    } else if (popLocationValue == '42003') { // allegheny
        pop = 1218494;
    } else if (popLocationValue == '53003') { // king county, wa
        pop = 1931249;
    } else if (popLocationValue == '06037') { // la county, ca
        pop = 9818605;
    } else {
        return;
    }
    
    paramGrid.jqGrid('setCell',11,'value', Math.round((pop * 0.94859)).toString(), '');
    paramGrid.jqGrid('setCell',12,'value', Math.round((pop * 0.00538)).toString(), '');
    paramGrid.jqGrid('setCell',13,'value', Math.round((pop * 0.00603)).toString(), '');
    paramGrid.jqGrid('setCell',14,'value', Math.round((pop * 0.04)).toString(), '');
}

function createOrSelectInsturctionTab(){
    //add instruction tab
	
    //create the ins tab
    var insId = "#instruction";
	
    if($(insId).html() != null ) {
        //select the tab
        maintab.tabs('select', insId);
    } else {	
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

function loadRegisteredModels(){
    $.ajax({
        type: "GET",
        url: "registered_models.php",

        async: true, /* If set to non-async, browser shows page as "Loading.."*/
        cache: false,
        timeout:50000, /* Timeout in ms */

        success: function(jasonObj, statusText){ /* called when request to barge.php completes */

            //            alert(jasonObj);
            jasonObj = $.parseJSON(jasonObj);
            
            var model = $('#model-combo');
            model.val('UNDEF');
            model.attr('disabled', '');
            //            //			
            model.empty();
            
            for (var i=0; i <jasonObj.data.length; i++) {
            
                if (jasonObj.data[i].hasOwnProperty('softwareIdentification')) {
                    var simDev = jasonObj.data[i].softwareIdentification['softwareDeveloper'];
                    var simName = jasonObj.data[i].softwareIdentification['softwareName'];
                    var simVer = jasonObj.data[i].softwareIdentification['softwareVersion'];
                    var softType = jasonObj.data[i].softwareIdentification['softwareType'];
                    if (softType.toLowerCase() == 'simulator') {
                        model.append('<option value="' + encodeURIComponent(JSON.stringify(jasonObj.data[i])) + '">' + simDev + ',' + simName + ',' + simVer + '</option>');
                    }
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

function clearRegisteredModels() {
    
    var model = $('#model-combo');
    model.attr('disabled', 'disabled');
    model.empty();
    model.append('<option value="select">Please select a disease...</option>');

}

jQuery(document).ready(function(){
    //    var jur = $('#jurisdiction-combo');
    //    jur.val('UNDEF');
        
    $('#disease-combo').change(function(){
        var diseaseName = this.options[this.selectedIndex].value;
        if (diseaseName != 'select') {
            loadRegisteredModels();  
        } else {
            clearRegisteredModels();
        }
    })
   
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
    loadParamGrid();
        
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
        west__size : $('body').innerWidth() * 0.29, //width for the left panel
        west__resizable : true,
        west__closable : false,
        south__resizable : true,
        south__closable : false,
        west__onresize: function (pane, $Pane) {
            $(dataExchange.gridId).setGridWidth($('#model-selection-div').innerWidth());
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
        },
        select: function(e, ui) {
            var tabname = ui.tab.text;
            runMethod(tabname);
        }
    });
    
    function clearTabs() {
        for (var i = $('#tabs','#CenterPane').tabs('length') - 1; i >= 1; i--) {
            $('#tabs','#CenterPane').tabs('remove', i);
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
        //        var status3Html = $("#status-div-3").html();
        //        var status2Html = $("#status-div-2").html();
        //        
        //        $("#status-div-1").html(status2Html);
        //        $("#status-div-2").html(status3Html);
        //        $("#status-div-3").html(msg);
        //        var psconsole = $('#statustextarea');
        //        psconsole.val(psconsole.val() + '\n' + msg);
        //        psconsole.scrollTop(
        //            psconsole[0].scrollHeight - psconsole.height()
        //            );
        if (firstLinePrinted) {
            firstLinePrinted = false;
        } else {
            msg = '<br>' + msg;
        }
        
        msg = msg + '<span id="caret_pos_holder"></span>';
        
        // the span helps set the caret position to the end
        tinyMCE.get('statustextarea').selection.select(tinyMCE.get('statustextarea').dom.select('span#caret_pos_holder')[0]); //select the span
        tinyMCE.get('statustextarea').dom.remove(tinyMCE.get('statustextarea').dom.select('span#caret_pos_holder')[0]); //remove the span
       
        tinyMCE.get('statustextarea').selection.setContent(msg);
        //        var height = document.getElementById('statustextarea' + '_ifr').scrollHeight;
        //        console.log(height);
        tinyMCE.get('statustextarea').getWin().scrollTo(0, 1000000);


    }
    
    function addZero(n) {
        
        return n<10? '0'+n:''+n;
    }
    function getConfigurationFileForRun(runId, simName, simDev, simVer, runNumber) {
        //        $.ajax({
        //            type: "GET",
        //            url: "configuration_file_text.php?runId=" + runId + "&dev=" + simDev + "&name=" + simName + "&ver=" + simVer,
        //
        //            async: true, /* If set to non-async, browser shows page as "Loading.."*/
        //            cache: false,
        //            timeout:50000, /* Timeout in ms */
        //
        //            success: function(text, statusText){ /* called when request to barge.php completes */
        //                jasonObj = $.parseJSON(jasonObj);
                
        var tabid;
        runNumber = runNumber.toString();
  
        if (runNumber.indexOf(" and ") !== -1) {
            var splitRunNumber = runNumber.split(" and ");
            tabid = "#formatted-text-" + simName + "-" + splitRunNumber[1]; // tab ids can't use commas
              
        } else {
            tabid = "#formatted-text-" + simName + "-" + runNumber; // tab ids can't use commas
 
        }
        tabid = tabid.replace(/\./g, "-"); // can't use periods
        console.log('tabid: ' + tabid);
                    
        if($(tabid).html() != null ) {
            //select the tab
            maintab.tabs('select', tabid);
            //clear current tab content
            $(tabid).empty();
        } else {
            //create the tab
                              
//            if (simName == 'FluTE') {
//                maintab.tabs('add', tabid, simName + ': Configuration file');
////            } else if (simName == 'FluTE' && runId.indexOf('nonverbose') > 0) {
////                maintab.tabs('add', tabid, simName + ': Non-verbose configuration file');
//            } else {
                maintab.tabs('add', tabid, simName + ' run ' +  runNumber + ': Configuration file');
//            }
        }
      
        $(tabid, "#tabs").load('configuration_file_text.php?modelIndex=' + encodeURIComponent(runId) + "&runId=" + encodeURIComponent(runId)
            + "&dev=" + simDev + "&name=" + simName + "&ver=" + simVer);
                
    //            },
    //            error: function(XMLHttpRequest, textStatus, errorThrown){
    //                if (textStatus == 'timeout') {
    //                    var date = new Date();
    //                    addmsg(addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds()) 
    //                        + " <b> ERROR: </b>" + "Could not call run on " + simName + ", please run epidemic simulator again.");
    //                //                }
    //                //                                setTimeout(
    //                //                                    poll, /* Try again after.. */
    //                //                                    15000); /* milliseconds (15seconds) */
    //                }
    //            }
    //        });
    }
    
    function loadFluteResultsFile(runId) {
        
        var md5RunId = calcMD5(runId);
        console.log(md5RunId);
        var tabid = "#flute-result-file"; // tab ids can't use commas
        
        if($(tabid).html() != null ) {
            //select the tab
            maintab.tabs('select', tabid);
            //clear current tab content
            $(tabid).empty();
        } else {
            //create the tab
            maintab.tabs('add', tabid,'FluTE: Results summary');
        }
        
        $(tabid, "#tabs").load('flute_results.php?md5RunId=' + md5RunId);
    }
    
    function startVisualization(runId, simName, runNumber, vizDev, vizName, vizVer, location) {
        
        $.ajax({
            type: "GET",
            url: "exec_visualization.php?runId=" + runId + "&vizDev=" + vizDev + "&vizName=" + vizName + "&vizVer=" + vizVer + "&location=" + location,

            async: true, /* If set to non-async, browser shows page as "Loading.."*/
            cache: false,
            timeout:50000, /* Timeout in ms */

            success: function(jasonObj, statusText){ /* called when request to barge.php completes */
                jasonObj = $.parseJSON(jasonObj);
                
                var urls = jasonObj.data['urls'];
                var dev = jasonObj.data['visualizerDeveloper'];
                var name = jasonObj.data['visualizerName'];
                var ver = jasonObj.data['visualizerVersion'];

                waitForVisualizations(runId, dev, name, ver, urls, simName, runNumber, vizName);
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
    
    function waitForVisualizations(runId, dev, name, ver, urls, simName, runNumber, vizName) {
        
        $.ajax({
            type: "GET",
            url: "poller.php?runId=" + runId + "&dev=" + dev + "&name=" + name + "&ver=" + ver + "&type=visualization",

            async: true, /* If set to non-async, browser shows page as "Loading.."*/
            cache: false,
            timeout:50000, /* Timeout in ms */

            success: function(jasonObj, statusText){ /* called when request to barge.php completes */
                jasonObj = $.parseJSON(jasonObj);
                //                alert(jasonObj);
                var status = jasonObj.data['status_normal'];
                var message = jasonObj.data['message_normal'];
                var date = new Date();
                
                addmsg(addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds()) + "<b> SIMULATOR: </b><i>" + simName + " </i><b>RUN ID: </b><i>" + runId  + " </i><b>VISUALIZER STATUS: </b><i>" + status  + " </i><b>MESSAGE: </b><i>" + message + "</i>"); /* Add response to a .msg div (with the "new" class)*/

                if (status != 'completed') {
                    setTimeout(
                        function() {
                            waitForVisualizations(runId, dev, name, ver, urls, simName, runNumber, vizName)
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
                    dataExchange.model_urls[runId.toString()] = encUrls;

                    // get run number

                    //create or select the tab for the result
                    var resultID;
                    runNumber = runNumber.toString();
                    var diseaseStatesRunNumber;
                    if (runNumber.indexOf(" and ") !== -1) {
                        var splitRunNumber = runNumber.split(" and ");
                        resultID = "#result-" + simName + "-" + splitRunNumber[1]; // tab ids can't use commas
                        diseaseStatesRunNumber = splitRunNumber[1];
                    } else {
                        resultID = "#result-" + simName + "-" + runNumber; // tab ids can't use commas
                        diseaseStatesRunNumber = runNumber;
                    }
                    
                    resultID = resultID.replace(/\./g, "-"); // can't use periods
                            
                    var incidenceID = "#incidence-" + simName + "-" + runNumber.replace(/ and /g, "");
                    incidenceID = incidenceID.replace(/\./g, "-"); // can't use periods
                    
                    var gaiaID = "#gaia-" + simName + "-" + runNumber;
                    gaiaID = gaiaID.replace(/\./g, "-"); // can't use periods
                    
                    var combinedIncidenceID = "#combined-incidence" + "-" + combinedRunNumber.replace(/ and /g, "");
                    combinedIncidenceID = combinedIncidenceID.replace(/\./g, "-"); // can't use periods

                    console.log('looping over urls');
                    for (key in urls) {


                        if (key == 'Disease states') {
                            if($(resultID).html() != null ) {
                                //select the tab
                                maintab.tabs('select', resultID);
                                //clear current tab content
                                $(resultID).empty();
                            } else {
                                //create the tab
                                maintab.tabs('add', resultID, simName + ' run ' +  diseaseStatesRunNumber + ': Disease states over time');
                            }
                    
                            console.log("requesting disease states page");
                            //load the tab
                            console.log(encodeURIComponent(runId));
                            $(resultID, "#tabs").load('result.php?index=' + encodeURIComponent(runId));
                        } else if (key == 'Incidence') {
                            if ($(incidenceID).html() != null) {
                                // select the tab
                                maintab.tabs('select', incidenceID);
                                //clear current tab content
                                $(incidenceID).empty();
                            } else {
                                // create the tab
                                maintab.tabs('add', incidenceID, simName + ' run ' + runNumber + ': Incidence over time');
                            }
                        
            
                            // load the tab
                            $(incidenceID, "#tabs").load('incidence.php?index=' + encodeURIComponent(runId));
                        } else if (key == 'Combined incidence') {
                            if ($(combinedIncidenceID).html() != null) {
                                // select the tab
                                maintab.tabs('select', combinedIncidenceID);
                                //clear current tab content
                                $(combinedIncidenceID).empty();
                            } else {
                                // create the tab
                                maintab.tabs('add', combinedIncidenceID, 'Runs ' + combinedRunNumber + ': Incidence over time');
                            }
                        
            
                            // load the tab
                            $(combinedIncidenceID, "#tabs").load('incidence.php?index=' + encodeURIComponent(runId));
                        }
                    
                        if (simName == 'FRED' && key == 'GAIA animation of Allegheny County') {
                            if($(gaiaID).html() != null ) {
                                //select the tab
                                maintab.tabs('select', gaiaID);
                                //clear current tab content
                                $(gaiaID).empty();
                            } else {
                                //create the tab
                                console.log('creating tab');
                                maintab.tabs('add', gaiaID, simName + ' ' + runNumber + ': GAIA Visualization for Simulation');
                            }
                                       
                            console.log(runId);
                            console.log(encodeURIComponent(runId));
                            //load the tab
                            $(gaiaID, "#tabs").load('gaia.php?index=' + encodeURIComponent(runId));
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
                            waitForVisualizations(runId, dev, name, ver, urls, simName, runNumber, vizName)
                        }, /* Request next message */
                        5000 /* ..after 1 seconds */
                        );
                }
            }
        });
        

    }
    
    function waitForSimulationsAndStartVisualizations(obj, runNumber, location) {
        
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
                var status_norm = jasonObj.data['status_normal'];
                var status_novacc = jasonObj.data['status_novacc'];
                var message_norm = jasonObj.data['message_normal'];
                var message_novacc = jasonObj.data['message_novacc'];
                
           
                
                var date = new Date();
                
                if (status_novacc == 'null') {
                    addmsg(addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds()) + "<b> SIMULATOR: </b><i>" + simName + "</i>" + " <b>RUN ID: </b><i>" + runId +  " </i><b>SIMULATOR STATUS: </b><i>" + status_norm  + " </i><b>MESSAGE: </b><i>" + message_norm + "</i>"); /* Add response to a .msg div (with the "new" class)*/
                } else {
                    var noVaccId = runId.split(";")[0];
                    var vaccId = runId.split(";")[1];
                    addmsg(addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds()) + "<b> SIMULATOR: </b><i>" + simName + " </i><b>RUN ID: </b><i>" + noVaccId + " </i><b>SIMULATOR STATUS: </b><i>" + status_norm  + " </i><b>MESSAGE: </b><i>" + message_norm + "</i>"); /* Add response to a .msg div (with the "new" class)*/
                    addmsg(addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds()) + "<b> SIMULATOR: </b><i>" + simName + " </i><b>RUN ID: </b><i>" + vaccId + " </i><b>SIMULATOR STATUS: </b><i>" + status_novacc  + " </i><b>MESSAGE: </b><i>" + message_novacc + "</i>"); /* Add response to a .msg div (with the "new" class)*/
                }
                
                if (status_norm != 'completed' || (status_novacc != 'null' && status_novacc != 'completed')) {
                    setTimeout(
                        function() {
                            waitForSimulationsAndStartVisualizations(obj, runNumber, location)
                        }, /* Request next message */
                        5000 /* ..after 1 seconds */
                        );
                } else {
                    finishedSimulators++;
                   
                    //                    if (simName != 'FRED') {
//                    if (simName == 'FluTE') {
//                        getConfigurationFileForRun(runId + ":", simName, simDev, simVer, runNumber); 
////                        getConfigurationFileForRun(runId + "::nonverbose", simName, simDev, simVer, runNumber + "_2"); 
//                    } else {
                    getConfigurationFileForRun(runId, simName, simDev, simVer, runNumber); 
//                    }
                    if (simName != 'FluTE') {
                        startVisualization(runId, simName, runNumber, 'nick', 'viztest', '1.0', location);
                    } else {
                        loadFluteResultsFile(runId);
                    }
                    //                    }
                    // not running gaia yet
                    if (simName == 'FRED' && location == '42003') {
                        if (runId.indexOf(";") !== -1) {
                            var runIds = runId.split(";");
                            var runNumbers = runNumber.split(" and ");
                            startVisualization(runIds[0], simName, runNumbers[1], 'PSC', 'GAIA', '1.0', location);
                            startVisualization(runIds[1], simName, runNumbers[0], 'PSC', 'GAIA', '1.0', location);
                        } else {
                            startVisualization(runId, simName, runNumber, 'PSC', 'GAIA', '1.0', location);
                        }
                    }
                    //                    waitForVisualizations();
                                    
                    console.log(finishedSimulators + "  " + numSimulators);
                    if (finishedSimulators == numSimulators && numberOfVisualizations == 0) {
                        $('#create').button( "option", "disabled", false );
                    }
                                    
                //                    if (numSimulators > 1 && finishedSimulators == numSimulators) { // now all simulators have finished
                //                        startVisualization(combinedRunId, 'All simulators', numSimulators + 1, 'nick', 'viztest', '1.0');
                //                    }
                 
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
                            waitForSimulationsAndStartVisualizations(obj, runNumber)
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
                var simulatorArray;
    
                if ($('#model-combo').val() == null) {
                    addmsg('<b>ERROR:</b> No epidemic simulator is selected');
                    return;
                } else {
                    simulatorArray = $('#model-combo').val().toString().split(",");
                }
                
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
                var vaccNamedPriScheme = $("#35_value").val();
                var useAvControlMeasure = $("#22_value").is(':checked');
                var useVaccControlMeasure = $("#32_value").is(':checked');
                var useSchoolClosure = $("#42_value").is(':checked');
                var scReactiveTargetFacilities = $("#46_value").val();
                var useSchoolReactiveCm = $("#43_value").is(':checked');
                var useSchoolFixedCm = $("#52_value").is(':checked');
                var scFixedTargetFacilities = $("#55_value").val();

                rowData[4]['value'] = timeStepUnit; // set the time step unit to store the value instead of html
                rowData[21]['value'] = useAvControlMeasure;
                rowData[31]['value'] = useVaccControlMeasure;
                rowData[34]['value'] = vaccNamedPriScheme;
                rowData[41]['value'] = useSchoolClosure;
                rowData[45]['value'] = scReactiveTargetFacilities;
                rowData[42]['value'] = useSchoolReactiveCm;
                rowData[51]['value'] = useSchoolFixedCm;
                rowData[54]['value'] = scFixedTargetFacilities;
                
                // replace the rows with the same parameter name with an adjusted one
                rowData[22]['pname'] = 'Antiviral Control Measure Compliance';
                rowData[23]['pname'] = 'Antiviral Treatment Response Delay';
                rowData[24]['pname'] = 'Antiviral Treatment Fixed Start Time'
                   
                rowData[32]['pname'] = 'Vaccination Control Measure Compliance';
                rowData[33]['pname'] = 'Vaccination Response Delay';
                rowData[34]['pname'] = 'Vaccination Named Prioritization Scheme';
                rowData[35]['pname'] = 'Vaccination Fixed Start Time';
                
                rowData[43]['pname'] = 'School Closure Reactive Compliance';
                rowData[44]['pname'] = 'School Closure Reactive Response Delay';
                rowData[45]['pname'] = 'School Closure Reactive Target Facilities';
                rowData[46]['pname'] = 'School Closure Reactive Duration';
                
                rowData[52]['pname'] = 'School Closure Fixed Compliance';
                rowData[53]['pname'] = 'School Closure Fixed Response Delay';
                rowData[54]['pname'] = 'School Closure Fixed Target Facilities';
                rowData[55]['pname'] = 'School Closure Fixed Duration';
                rowData[56]['pname'] = 'School Closure Fixed Start Time';
                
                var exportData = JSON.stringify(rowData);
                var snomed = $('#snomed-ct-combo').val();
            
                
                var simDev = '';
                var simName = '';
                var simVer = '';
                
                for (var i = 0; i < simulatorArray.length; i++) {
                    //                    alert(simulatorArray[i]);
                    var obj = $.parseJSON(decodeURIComponent(simulatorArray[i]));
                    simDev += "," + encodeURIComponent(obj.softwareIdentification['softwareDeveloper']);
                    simName += "," + encodeURIComponent(obj.softwareIdentification['softwareName']);
                    simVer += "," + encodeURIComponent(obj.softwareIdentification['softwareVersion']);
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
                $(dataExchange.statusBar).html('Waiting for the server response..');

                return true;
            }catch (err){
                // set the error message
                addmsg('<b>ERROR: </b>' + err);
                return false;
            }

        },


        // success identifies the function to
        // invoke when the server response
        // has been received;
        success : function(jasonObj, statusText) {
            if (statusText != 'success') {// web server error
                $(dataExchange.statusBar).html('Server error: ' + statusText);
                return;
            }

            console.log(jasonObj);
            // web service error
            if (jasonObj.exception != null) {
                $(dataExchange.statusBar).html('Web service error: ' + jasonObj.exception);
                return;
            }

            numSimulators = jasonObj.data.length;
            finishedSimulators = 0;
            numberOfVisualizations = 0;
            numberOfVisualizationsFinished = 0;
            var allRunIds = '';
            var allRunNums = '';
            //            if (numSimulators > 1) {
            //                numberOfVisualizations += 1; // one for the combined incidence
            //            }
            
            //            alert(numSimulators);
            for (var i = 0; i < numSimulators; i++) {

                var simulatorObj = jasonObj.data[i];
                
                var runId = simulatorObj['runId'];
                
                var runNumber = '';  
                if (runId.indexOf(";") !== -1) {
                    // multiple control measure run
                    var runs = runId.split(";");
                    var splitRunId1 = runs[0].split("_");
                    var splitRunId2 = runs[1].split("_");
                    runNumber = splitRunId2[splitRunId2.length - 1].trim() + ' and ' + splitRunId1[splitRunId1.length - 1].trim();
                } else {
                    var splitRunId =   runId.split("_");
                    runNumber = splitRunId[splitRunId.length - 1];
                }
                       
               
                console.log(runId);
                var simName = simulatorObj['simulatorName'];
                
               
                
                if (simName != 'FluTE') { // flute has no visualizations and shouldn't be in the combined variables
                    
                    allRunIds += ':' + runId;
                    allRunNums += ' and ' + runNumber;
                    numberOfVisualizations += 1;
                }
                
                var location = simulatorObj['location'];
                // not running gaia yet
                if (simName == 'FRED' && location == '42003') {
                    if (runId.indexOf(";") !== -1) {
                        numberOfVisualizations += 2; // two for gaia (one with control measure, one without)
                    } else {
                        numberOfVisualizations += 1; // one for gaia
                    }
                }

                
                waitForSimulationsAndStartVisualizations(simulatorObj, runNumber, location);
            }

            combinedRunId = allRunIds.substring(1);
            combinedRunNumber = allRunNums.substring(5);
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