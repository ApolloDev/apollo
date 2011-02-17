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
 * Date utility functions 
 *
 * @author Yang Hu <yah14@pitt.edu>
 */
// Create a Date object from string like 12-24-2009 (MM-dd-yyyy)
function dateFromString(dateStr) {
	var myDateArray = dateStr.split("-");
	var year = parseInt( myDateArray[2] );
	var month = parseInt ( myDateArray[0] - 1 );
	var day = parseInt( myDateArray[1] );
	
//	console.log(year + month + day);
	
	var dateObj = new Date( year, month , day);
	
//	console.log(dateObj);
	
	return dateObj;
}

// Convert a Date object to a string like 10-01-2009 (MM-dd-yyyy)
function dateToShortString(date) {
//	date.setDate(date.getDate() + 1);

	var year = date.getFullYear();
	var month = date.getMonth() + 1;// Date object using 0 for Jan.
	var day = date.getDate();// Date using 1 for the first day
	
	var monthStr = month.toString();
	if (month <= 9)
		monthStr = "0" + monthStr;
	
	var dayStr = day.toString();
	if (day <= 9)
		dayStr = "0" + dayStr;
	
//	console.log(year + month + day);
	
	return monthStr + '-' + dayStr + '-' + year.toString();
}

// Get the date object for the next m days.
function dateToNextDays(currentDate, days){
	var date = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate());
	date.setDate(date.getDate() + days);
	
	return date;
}

//Get the date (in string) for the next m days.
function dateToNextDaysStr(currentDate, days){
	var date = dateToNextDays(currentDate, days);
	return dateToShortString(date);
}