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

var vaccineTemporalDistribution = new function (){
	//private functions
	function initControl(){
		// init buttons
		$('#vaccine-temporal-distribution-table-add').button().click(function(){
			if (vaccGrid.find('input').length > 0){
				alert('Please save your change first!');
				return;
			}
				
			var newRowId = vaccGrid.getDataIDs().max() + 1;
			
			var mylastrow = {
					name : 'name',
					value : 0
				};
			
			vaccGrid.addRowData(newRowId, mylastrow, 'last');
		});
		
		$('#vaccine-temporal-distribution-table-delete').button().click(function (){
			if (vaccGrid.find('input').length > 0){
				alert('Please save your change first!');
				return;
			}
			
			var rowid = vaccGrid.getGridParam('selrow');
			vaccGrid.delRowData( rowid );
		});
		
		$('#vaccine-temporal-distribution-submit').button().click(function (){
			if (vaccGrid.find('input').length > 0){
				alert('Please save your change first!');
				return;
			}
			
			var mainGrid = $(dataExchange.gridId);
			var rowData = jqgridHelper.findDataByValue(mainGrid, 'pname', 'Initial Compartment Sizes');
			if (rowData == null || rowData.length < 1)
				throw "Can't find Initial Compartment Sizes!";
			
			rowData = rowData[0];
			
			var currentRawData = vaccGrid.getRowData()
						
			var tgtRowId = rowData.id;
			rowData['extra'] = JSON.stringify(currentRawData);
			
			mainGrid.setRowData(tgtRowId, rowData);
		});
	}
	//end private functions
	
	//public functions
	var vaccGrid
	this.init = function() {
		try{
			//init common vars
//			deliveryGrid = $("#delivery-table");
			
			initControl();
			
			//the init mode is rate grid
			vaccGrid = initVaccGrid();
			
			var vacc = getVaccinationTemporalDistribution();
			
			loadVaccGrid(vacc);
			
			return $(this);
		}
		catch (err){
			alert(err.message);
		}
	};
	//end public function
};

vaccineTemporalDistribution.init();