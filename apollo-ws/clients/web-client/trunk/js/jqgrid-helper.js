/**
 * utility functions for jqgrid 
 */

var jqgridHelper = new function() {
	this.findDataByValue = function(grid, colName, colValue){
		var rowDataSet = grid.getRowData ()
		var data = new Array();
		for (var idx = 0; idx < rowDataSet.length; idx = idx + 1){
			var rowData = rowDataSet[idx];
			var currentValue = rowData[colName]; 
			if (currentValue == colValue)
				data.push(rowData);
		}
		return data;
	};
	
	this.findIdByValue = function(grid, colName, colValue){
		var rowDataSet = grid.getRowData ()
		var ids = new Array();
		for (var idx = 0; idx < rowDataSet.length; idx = idx + 1){
			var rowData = rowDataSet[idx];
			var currentValue = rowData[colName]; 
			if (currentValue == colValue)
				ids.push(rowData.id);
		}
		return ids;
	};
	
//	this.restoreRow2 = function(rowid){
//		$(this).restoreRow(rowid);
//
//		//clear th ecss tags
//		var currSelection = $(this).find('#' + rowid);
//
//		currSelection.removeClass('ui-state-highlight');
//		currSelection.removeClass('ui-state-hover');
//
//		currSelection.children('td').each(function(){
//			$(this).removeClass('ui-state-highlight');
//			$(this).removeClass('ui-state-hover');
//		});
		
		//clear the cell edit state
//		var rowData = $(this).getRowData(rowid);
		// 6 is the predefined column in the tree grid
//		var colCount = $(this).getGridParam('colModel').length;
		
//		alert(rowData);
//		for (var idx = 0; idx < colCount; idx ++){
//			var ret = $(this).getCell(rowid, idx);
//			ret = $(this).restoreCell(rowid, idx);
//			ret = $(this).saveCell(rowid, idx);
//		}
//	}
};

//(function($) {
//    $.jgrid.extend({
//    	restoreRow2: jqgridHelper.restoreRow2
//        onUnHighlight: GridErrorUnHighlight,
//    });
//})(jQuery);
