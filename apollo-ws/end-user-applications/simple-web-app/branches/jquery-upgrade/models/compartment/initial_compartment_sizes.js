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

var initCompartmentSize = new function (){
	// private functions
	function getCompartmentSizeRawData(){
		var mainGrid = $(dataExchange.gridId);
		var rowData = jqgridHelper.findDataByValue(mainGrid, 'pname', 'Initial Compartment Sizes');

		if (rowData == null || rowData.length < 1)
			throw "Can't get Initial Compartment Sizes";
		
		var rawData =  eval('(' + rowData[0].extra + ')');
		
		return rawData;
	}
	
	function initControl(){
		// init buttons
		$('#init-compartment-sizes-table-add').button().click(function(){
			if (comGrid.find('input').length > 0){
				alert('Please save your change first!');
				return;
			}
				
			var newRowId = comGrid.getDataIDs().max() + 1;
			
			var mylastrow = {
					name : 'name',
					value : 0
				};
			comGrid.addRowData(newRowId, mylastrow, 'last');
		});
		
		$('#init-compartment-sizes-table-delete').button().click(function (){
			if (comGrid.find('input').length > 0){
				alert('Please save your change first!');
				return;
			}
			
			var rowid = comGrid.getGridParam('selrow');
			comGrid.delRowData( rowid );
		});
		
		$('#init-compartment-sizes-submit').button().click(function (){
			if (comGrid.find('input').length > 0){
				alert('Please save your change first!');
				return;
			}
			
			var mainGrid = $(dataExchange.gridId);
			var rowData = jqgridHelper.findDataByValue(mainGrid, 'pname', 'Initial Compartment Sizes');
			if (rowData == null || rowData.length < 1)
				throw "Can't find Initial Compartment Sizes!";
			
			rowData = rowData[0];
			
			var currentRawData = comGrid.getRowData()
						
			var tgtRowId = rowData.id;
			rowData['extra'] = JSON.stringify(currentRawData);
			
			mainGrid.setRowData(tgtRowId, rowData);
		});
	};
	
	function initCompartmentGrid(){		
		// unload grid first
		$("#init-compartment-sizes-table").GridUnload();
		// get the empty div by id
		var grid = $("#init-compartment-sizes-table");		
		
		// init in rate mode
		grid.jqGrid({
			datatype: "local",
			colNames:['Name', 'Size'],
			colModel:[
				{name:'name', index:'name', width:156, editable: true, editoptions:{size:25}},
				{name:'value', index:'value', width:157, editable: true,
					editoptions:{size:25}, sorttype:"int", editrules:{number:true}}	
			],
			width: 470,
			cellEdit: true,
			cellsubmit: 'clientArray'
		});
		
		return grid;
	};
	
	function loadCompartmentGrid(rowData){
		for(var i = 0;i < rowData.length; i++){
			comGrid.addRowData (i+1, rowData[i]);
		}
	};
	// end private functions
	
	// public functions
	var comGrid;// global var for CompartmentGrid
	this.init = function() {
		try{
			// init common vars
			initControl();
			
			// the init the compartment grid
			comGrid = initCompartmentGrid();		
			// load the data into the grid
			loadCompartmentGrid(getCompartmentSizeRawData());
			
			return $(this);
		}
		catch (err){
			alert(err.message);
		}
	};
	// end public function
};

initCompartmentSize.init();