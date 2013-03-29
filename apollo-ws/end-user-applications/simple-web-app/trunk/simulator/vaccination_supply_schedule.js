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
 *
 * @author Yang Hu <yah14@pitt.edu>
 */

var vaccSupplySchedule = new function(){
    //private functions
    function onGridInsertClicked(){
        //if user is still editing the table
        if (deliveryGrid.find('input').length != 0){
            alert('You must finish editing before you can add. To finish editing, click on the active cell (it is highlighted in yellow) and press the enter key.!');
            return;
        }
		
        if (currMode != 'rate'){
            alert('For rate model only!');
            return;
        }
		
        //get current select id
        var currid = deliveryGrid.getGridParam('selrow');
        if (currid == null){
            alert('You must select a row before you can insert');
            return;
        }
		
        currid = parseInt(currid);
		
        //transfer current rate into an array
        var currentRawData = deliveryGrid.getRowData();
        //		logex2('currentRawData', currentRawData);
        //insert an new entry as its deep copy
        var newObject = jQuery.extend(true, {}, currentRawData[currid]);
        currentRawData.splice(currid, 0, newObject);
        //		logex2('currentRawData', currentRawData);
        //		logex2('currid', currid);
		
        //for every element after currid, increae the date for 1 day
        for (var idx = currid + 1; idx < currentRawData.length; idx++){
            var currrow = currentRawData[idx];
            var prerow = currentRawData[idx - 1];
			
            //			logex2('currrow', currrow);
            //			logex2('prerow', prerow);
			
            //increase only if the two date has no gap
            var nowDateStr = currrow.date;
            var nowDate = dateFromString(nowDateStr);

            var preDateStr = prerow.date;
            var preDate = dateFromString(preDateStr);
			
            var predictDate = dateToNextDays(nowDate, 1); 
			
            if (nowDate.getTime() == preDate.getTime()){
                currrow.day = parseInt( currrow.day ) + 1;
                currrow.date = dateToShortString(predictDate);
            }
            else{
                break;
            }
        }
		
        initRateGrid();
        loadRateAry(currentRawData);
		
        //focus to the new row
        //		logex2('currid', currid);
        deliveryGrid.editCell(currid + 2, 2, true);
		
    //update the chart
    //		var currentRawData = gridToRawArray();
    //		loadChart(currentRawData);
    };
    function onGridAddClicked(){
        //if user is still editing the table
        if (deliveryGrid.find('input').length != 0){
            alert('You must finish editing before you can add. To finish editing, click on the active cell (it is highlighted in yellow) and press the enter key.!');
            //	$(this).val(previousDate);
            return;
        }

        //get the lastrow
        var lastrowid = deliveryGrid.getDataIDs().max();
        var lastrow = deliveryGrid.getRowData(lastrowid);
		
        //generate a new row id
        var newRowId = lastrowid + 1;
		
        if (currMode == 'rate'){//update the delivery rate number
            var dateVal = lastrow.date;
            var lastDate = dateFromString(dateVal);
			
            var mylastrow = {
                day : parseInt(lastrow.day) + 1,
                date : dateToNextDaysStr(lastDate, 1),
                rate : parseInt (lastrow.rate)
            };
            deliveryGrid.addRowData(newRowId, mylastrow, 'last');
			
            var currentCount = parseInt( $('#vaccination-supply-delivery-rate-change').val() );
            currentCount++;
            $('#vaccination-supply-delivery-rate-change').val(currentCount);
        }else if (currMode == 'table'){
            var mylastrow = {
                delivery : parseInt(lastrow.delivery) + 1,
                day : parseInt(lastrow.day) + 1,
                quantity : parseInt(lastrow.quantity)
            };

            deliveryGrid.addRowData(newRowId, mylastrow, 'last');
			
            //update the deliveryCount
            var currentCount = parseInt( $('#vaccination-supply-delivery-number').val() );
            currentCount++;
            $('#vaccination-supply-delivery-number').val(currentCount);
        }
		
        //update the chart
        var currentRawData = gridToRawArray();
        loadChart(currentRawData);
    };
    function onGridDeleteClicked(){
        //if user is still editing the table
        if (deliveryGrid.find('input').length != 0){
            alert('You must finish editing before you can delete. To finish editing, click on the active cell (it is highlighted in yellow) and press the enter key.!');
            return;
        }
		
        if (currMode == 'rate'){//update the delivery rate number
            var currentCount = parseInt( $('#vaccination-supply-delivery-rate-change').val() );
            if (currentCount == 1)
                return;
			
            //get current selection id
            var rowid = deliveryGrid.getGridParam('selrow');
			
            if (rowid == null){
                alert('You must select a row before you can insert.');
                return;
            }
			
            deliveryGrid.delRowData( rowid );
			
            var currentCount = parseInt( $('#vaccination-supply-delivery-rate-change').val() );
            currentCount--;
            $('#vaccination-supply-delivery-rate-change').val(currentCount);
        }else if (currMode == 'table'){
            var ids = deliveryGrid.getDataIDs();
            if (ids.length == 0)
                return;
            //delete the last row @ buttom
            deliveryGrid.delRowData( ids.max() );
			
            //update the control
            var currentCount = parseInt( $('#vaccination-supply-delivery-number').val() );
            currentCount--;
            $('#vaccination-supply-delivery-number').val(currentCount);
        }
		
        //update the chart
        var currentRawData = gridToRawArray();
        loadChart(currentRawData);
    };
	
    function getVaccinationSchedule(){
        var mainGrid = $(dataExchange.gridId);
        var rowData = jqgridHelper.findDataByValue(mainGrid, 'pname', 'Vaccination Supply Schedule');

        if (rowData.length == 0)
            throw "Can't get Vaccination Supply Schedule";
		
        var rawData =  eval('(' + rowData[0].extra + ')');
		
        return rawData;
    }
	
    var chart;//global var for chart
    function initChart(rawData){		
        //chart
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'vaccination-supply-delivery-chart',
                defaultSeriesType: 'line',
                marginRight: 150,
                marginBottom: 60,
                height: 300,
                width: 475
            },
            title: {
                text: 'Delivery Schedule',
                x: -20 //center
            },
            subtitle: {
                text: 'Source: Apollo Webservice',
                x: -20
            },
            xAxis: {
                id : 'x-axis',
                title : {
                    margin: 10,
                    text: 'Days since first delivery'
                },
                labels: {
                    rotation: -90,
                    align: 'right',
                    style: {
                        font: 'normal 13px Verdana, sans-serif'
                    }
                }
            },
            yAxis: {
                title: {
                    text: '# doses delivered'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.series.name +'</b><br/>'+
                    'Day ' + this.x +': '+ this.y;
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -10,
                y: 100,
                borderWidth: 0
            },
            series: [{
                id : 'non-cumulative',
                name : 'Non-Cumulative'
            }, {
                id : 'cumulative',
                name : 'Cumulative'
            }
            ]
        });
		
        loadChart(rawData);
    };
	
    function loadChart(rawData){
        //		console.log(JSON.stringify(rawData));
		
        //chart data
        var data = new Array();
        var xAxis = new Array();

        //get the max length
        var maxLength = rawData.length;
		
        var step = Math.floor(maxLength / 20);
        if (step < 1)
            step = 1;
		
        //for Non-Cumulative
        var ncumData = arrayHelper.resize(rawData, step);
        //console.log('nc: ' + JSON.stringify(ncumData));
		
        //for Cumulative
        var cumData = arrayHelper.resize(arrayHelper.cumulative(rawData), step);
        //console.log('c: ' + JSON.stringify(cumData));
		
        //xAxis lable
        var index = 0;
        while (xAxis.length * step < maxLength){
            xAxis.push(index * step);
            index = index + 1;
        }
		
        var ncs = chart.get('non-cumulative');
        var cs = chart.get('cumulative');
        var xa = chart.get('x-axis');
		
        ncs.setData(ncumData);
        cs.setData(cumData);
        xa.setCategories(xAxis);
    }
	
    function initRateGrid(){
        //set current mode
        currMode = 'rate';
		
        //unload grid first
        deliveryGrid = $("#vaccination-supply-delivery-table");
        deliveryGrid.GridUnload();
        //get the empty div by id
        deliveryGrid = $("#vaccination-supply-delivery-table");		
		
        //init in rate mode
        deliveryGrid.jqGrid({
            //			data: mydata,
            datatype: "local",
            //rowNum: mydata.length,
            loadonce:true,
            colNames:['Day', 'Date', 'Rate'],
            colModel:[
            {
                name:'day', 
                index:'day', 
                width:156, 
                editable: false, 
                editoptions:{
                    size:25
                }, 
                sorttype:"int"
            },

            {
                name:'date', 
                index:'date', 
                width:157, 
                editable: false,
                editoptions:{
                    size:25
                },
                sorttype:"date" , 
                editrules:{
                    date:true
                }, 
                datefmt: 'm-d-Y', 
                hidden:true
            },

            {
                name:'rate', 
                index:'rate', 
                width:256, 
                editable: false,
                editoptions:{
                    size:25
                }, 
                sorttype:"int", 
                editrules:{
                    number:true
                }
            }		
            ],
            width: 470,
            cellEdit: true,
            cellsubmit: 'clientArray',
            beforeSaveCell : function (	rowid, cellname, value, iRow, iCol){
                if (iCol != 1) //We just want to change the day when date changes
                    return;
				
                var dateVal = $( "#vaccination-supply-date-of-first-delivery" ).val();
                var startDate = dateFromString(dateVal);
                var newDate = dateFromString(value);
				
                var diffDate = newDate - startDate;
				
                if (diffDate < 0){
                    return dateVal; // make it to the begin date if the date is too small
                }else
                    return value;
            },
            afterSaveCell : function(rowid, cellname, value, iRow, iCol){
                //				logex('after : ' + rowid + ' ' + cellname + ' ' + value + ' ' + iRow + ' ' + iCol);
				
                if (iCol == 1){ //We just want to change the day when date changes
                    var dateVal = $( "#vaccination-supply-date-of-first-delivery" ).val();
                    var startDate = dateFromString(dateVal);
                    var newDate = dateFromString(value);
					
                    var diffDate = newDate - startDate;
					
                    //update the day value
                    var rowData = deliveryGrid.getRowData(rowid);
                    var numDays = diffDate / 86400000;
                    rowData.day = Math.floor(numDays);
                    deliveryGrid.setRowData(rowid, rowData);
                }
				
                //update the chart
                var currentRawData = gridToRawArray();
                loadChart(currentRawData);
            }
        });
    };
	
    function loadRateRawAry(rawData){
        loadRateAry(toRateAry(rawData));
    }
	
    function loadRateAry(mydata){
        //insert
        for(var i=0;i<=mydata.length;i++) 
            deliveryGrid.addRowData (i, mydata[i]);
		
        //set the rate control (number of delivery)
        $('#vaccination-supply-delivery-rate-change').val(mydata.length - 1);
		
        //hide the table control
        $('#vaccination-supply-delivery-number-div').hide();
        //show the rate control
        $('#vaccination-supply-delivery-rate-div').show();
    }
	
    function initTableGrid(rawData){
        //set current mode
        currMode = 'table';
		
        //unload grid first
        deliveryGrid = $("#vaccination-supply-delivery-table");
        deliveryGrid.GridUnload();
        //get the empty div by id
        deliveryGrid = $("#vaccination-supply-delivery-table");		
		
        //init in table mode
        deliveryGrid.jqGrid({
            //			data: mydata,
            datatype: "local",
            loadonce:true,
            rowNum: -1, 
            //			rowTotal: -1, 
            //			rowTotal: mydata.length, 
            //			rowList : [20,30,50], 
            //			scroll:1,
            colNames:['Delivery #', 'Day', 'Quantity'],
            colModel:[
            {
                name:'delivery', 
                index:'delivery', 
                width:156, 
                editable: false, 
                editoptions:{
                    size:25
                }, 
                sorttype:"int"
            },

            {
                name:'day', 
                index:'day', 
                width:157, 
                editable: false, 
                editoptions:{
                    size:25
                }, 
                sorttype:"int"
            },

            {
                name:'quantity', 
                index:'quantity', 
                width:256, 
                editable: false, 
                editoptions:{
                    size:25
                }, 
                sorttype:"int"
            }		
            ],
            //			editurl:"edit.php",
            afterSaveCell : function(rowid, cellname, value, iRow, iCol){
                //update the raw data
				
                //update the chart
                var currentRawData = gridToRawArray();
                //				console.log(JSON.stringify(currentRawData));
                loadChart(currentRawData);
            },
            width: 470,
            cellEdit: true,
            cellsubmit: 'clientArray'
        });

        //construct data from parameter extra data
        var mydata = toTableAry(rawData);
		
        for(var i=0;i<=mydata.length;i++) 
            deliveryGrid.addRowData (i+1, mydata[i]);
		
        //set the rate control (number of delivery)
        $('#vaccination-supply-delivery-number').val(mydata.length);
		
        //show the table control
        $('#vaccination-supply-delivery-number-div').show();
        //hide the rate control
        $('#vaccination-supply-delivery-rate-div').hide();
    };
	
    function initControl(){
        var beginDatePicker = $( "#vaccination-supply-date-of-first-delivery" )
        beginDatePicker.datepicker();
        beginDatePicker.datepicker( "option", "dateFormat", "mm-dd-yy" );
		
        var previousDate = beginDatePicker.val();
		
        //reinit the rate table when first date changed
        beginDatePicker.change(function(){
            //if current model is table
            if (currMode == 'table')
                return;
			
            //if user is still editing the table
            if (deliveryGrid.find('input').length != 0){
                alert('You must finish editing before you can create a date. To finish editing, click on the active cell (it is highlighted in yellow) and press the enter key.!');
                $(this).val(previousDate);
                return;
            }
			
            previousDate = $(this).val();
			
            onRateMode();
        });
		
        //		$('#vaccination-supply-delivery-table-add').button().click(onGridAddClicked);
        //		$('#vaccination-supply-delivery-table-insert').button().click(onGridInsertClicked);
        //		$('#vaccination-supply-delivery-table-delete').button().click(onGridDeleteClicked);
        $('#vaccination-supply-delivery-table-add').button().click();
        $('#vaccination-supply-delivery-table-insert').button().click();
        $('#vaccination-supply-delivery-table-delete').button().click();
        $('#vaccination-supply-delivery-table-add').button().hide();
        $('#vaccination-supply-delivery-table-insert').button().hide();
        $('#vaccination-supply-delivery-table-delete').button().hide();
		
        $('#vaccination-supply-schedule-submit').button().click(function (){
            //if user is still editing the table
            //        if (deliveryGrid.find('input').length != 0){
            //            alert('You must finish editing before you can save. To finish editing, click on the active cell (it is highlighted in yellow) and press the enter key.');
            //            //	$(this).val(previousDate);
            //            return;
            //        }
            //			
            //        var mainGrid = $(dataExchange.gridId);
            //        var rowData = jqgridHelper.findDataByValue(mainGrid, 'pname', 'Vaccination Supply Schedule');
            //        if (rowData == null || rowData.length < 1)
            //            throw "Can't find Vaccination Supply Schedule Parameter!";
            //			
            //        rowData = rowData[0];
            //			
            //        var tgtRowId = rowData.id;
            //        var currentRawData = gridToRawArray();
            //        rowData['extra'] = JSON.stringify(currentRawData);
            //			
            //        //			console.log('currentRawData: ' + JSON.stringify(currentRawData) );
            //        //			console.log('rowData: ' + JSON.stringify(rowData) );
            //			
            //        mainGrid.setRowData(tgtRowId, rowData);
			
            //
            //			console.log('after: ' + JSON.stringify( mainGrid.getRowData(tgtRowId)) );
            });
        $('#vaccination-supply-schedule-submit').button().hide();
                
        $('#vaccination-supply-rate-mode').click(onRateMode);
        $('#vaccination-supply-table-mode').click(onTableMode);
    };
	
    function toRateAry(srcAry){
        var dateVal = $( "#vaccination-supply-date-of-first-delivery" ).val();
        var startDate = dateFromString( dateVal );

        var rateAry = new Array();

        if (srcAry.length == 0)
            return rateAry;

        var day = 0, date = startDate;
        var lastQuantity = srcAry[0];

        //put in the first entry of the first day as the beginning
        rateAry.push({
            day : day,
            date : dateToNextDaysStr(date, day),
            rate : lastQuantity
        });

        for (var idx = 0; idx < srcAry.length; idx = idx + 1){
            var quantity = srcAry[idx];
            if (quantity != lastQuantity){
                rateAry.push({
                    day : day,
                    date : dateToNextDaysStr(date, day),
                    rate : quantity
                });

                lastQuantity = quantity;
            }

            day = day + 1;
        }

        //put in the last entry to close it
        rateAry.push({
            day : srcAry.length,
            date : dateToNextDaysStr(date, srcAry.length + 1),
            rate : 0
        });
		
        //		console.log(srcAry.length);
        //		console.log(JSON.stringify(rateAry));

        return rateAry;
    };
	
    function toTableAry(srcAry){
        var tableAry = new Array();
		
        for (var idx = 0; idx < srcAry.length; ++idx){
            tableAry.push({
                delivery : idx,
                day : idx,
                quantity : srcAry[idx]
            });
        }
		
        return tableAry;
    };
	
    //when user select rate mode
    function onRateMode(){
        //get current raw array data
        var rawAry = gridToRawArray();
		
        //init with rate mode with the raw array data
        initRateGrid();
        loadRateRawAry(rawAry);
		
        //enable the insert function
        $('#vaccination-supply-delivery-table-insert').button( "option", "disabled", false );
    };
	
    //when user select table mode
    function onTableMode(){
        //get current raw array data
        var rawAry = gridToRawArray();
		
        //		logex2('rawAry.length', rawAry.length);
		
        //init with table mode with the raw array data
        initTableGrid(rawAry);
		
        //disable the insert function 
        $('#vaccination-supply-delivery-table-insert').button( "option", "disabled", true );
    };
	
    function gridToRawArray(){
        //get the raw array
        var rowData = deliveryGrid.getRowData();
        //		logex2('rowData', rowData);
        //		logex(JSON.stringify(rowData));
        var rawAry = new Array();
		
        //		console.log(currMode);
        //		console.log(JSON.stringify(rowData));
		
        if (currMode == 'rate'){
            //sort data by day, increasing order
            rowData.sort(function (left, right){
                return parseInt(left.day) >= parseInt(right.day);
            });
			
            //			console.log(rowData.length);
			
            for (var idx = 0; idx < rowData.length - 1; idx = idx + 1){
                var rateObj = rowData[idx];
				
                var beginDay = parseInt(rateObj.day);
                var endDay = parseInt(rowData[idx + 1].day);
				
                while(beginDay < endDay){
                    rawAry[beginDay] = parseInt(rateObj.rate);
                    beginDay = beginDay + 1;
                }
            }
			
        //			console.log('Rate: ' + rawAry.length);
        }else{
            for (var idx = 0; idx < rowData.length; idx = idx + 1){
                rawAry[idx] = parseInt(rowData[idx].quantity);
            }
			
        //append the end symbol at the end
        //			rawAry[idx] = 0;
        }
		
        //logex('rowData : ' + JSON.stringify(rowData));
        //logex('gridToRawArray : ' + JSON.stringify(rawAry));
		
        return rawAry;
    };
    //end private
	
    //public functions
	
    var deliveryGrid; //global var for deliveryGrid
    var currMode; //global var for current mode
	
    this.init = function() {
        try{
            //init common vars
            deliveryGrid = $("#vaccination-supply-delivery-table");
			
            initControl();
			
            var vacc = getVaccinationSchedule();
			
            //the init mode is rate grid
            //init with rate mode with the raw array data
            initRateGrid();
            loadRateRawAry(vacc);
			
            initChart(vacc);
			
            return $(this);
        }
        catch (err){
            alert(err.message);
        }
    };
//end public

};

var vaccsupplys = vaccSupplySchedule.init();