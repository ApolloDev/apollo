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
 * Some methods to trim the array 
 *
 * @author Yang Hu <yah14@pitt.edu>
 */


var arrayHelper = new function(){
    // resize the array by the step, then fix the float to 0.00..1
    this.resize = function(srcAry, tgtStep, tgtPrecision) {
        var srcLength = srcAry.length;
        var srcIndex = 0;
        var tgtAry = new Array();
        while (srcIndex < srcLength){
            var srcValue = srcAry[srcIndex];
            srcValue = roundTo(srcValue, 3);
			
            tgtAry.push(srcValue);
            srcIndex = srcIndex + tgtStep;
        }
		
        return tgtAry;
    };
	
    this.cumulative = function (srcAry){
        var cAry = new Array();
        var cVal = 0;
        for (var idx = 0; idx < srcAry.length; idx++){
            cVal = cVal + srcAry[idx];
            cAry.push(cVal);
        }
		
        return cAry;
    };
    this.addDates = function(srcAry, date) {
            
        var firstDate = Date.parse(date);
        var newData = new Array(srcAry.length);
        for (var idx = 0; idx < srcAry.length; idx++){
            newData[idx] = new Array(2);
            newData[idx][0] = firstDate + 1000 * 60 * 60 * 24 * idx;
//        newData[idx][0] = idx + 1;
            newData[idx][1] = srcAry[idx];
        }
            
        return newData;
    };
        
    // round a float number to #digits
    // e.g. floatNum = 1.1234 digits = 3
    // return 1.123
    function roundTo(floatNum, digits) {
        if (digits <= 0)
            return floatNum;

        var base = 1;

        while (digits > 0) {
            base = base * 10;
            digits = digits - 1;
        }

        return Math.round(floatNum * base) / base;
    };
        

};
//
//Array.prototype.max = function() {
//    return Math.max.apply(null, this)
//}
//
//Array.prototype.min = function() {
//    return Math.min.apply(null, this)
//}

