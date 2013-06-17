/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var stateCountyListMap = {};
var countyStateMap = {};
var stateNameMap = {};
var countyNameMap = {};
var stateIdList = new Array(); // this will be a sorted list of all state IDs
var storedSelectedCountyLocations = new Array();
var storedSelectedStateLocations = new Array();
var storedSelectedCountyFractions = {};
var storedSelectedStateFractions = {};
var storedUsFractions;
var usSelected = false;
var storedFractionsForAllRegions = {};
var storedFractionsForAllRegionsJson = "";
var outerDivBorderColor = "#D0D0D0";

function loadStateAndCountyLocations() {
    $.ajax({
        type: "GET",
        url: "simulation/population/location_data_request.php?requestType=states",

        async: false, /* If set to non-async, browser shows page as "Loading.."*/
        cache: false,
        timeout:50000, /* Timeout in ms */

        success: function(jasonObj, statusText){ /* called when request to barge.php completes */


            jasonObj = $.parseJSON(jasonObj);
            for (var stateName in jasonObj) {
                
                var stateId = jasonObj[stateName];
                stateCountyListMap[stateId] = [];
                stateNameMap[stateId] = stateName;
                stateIdList.push(stateId);
                
                var countyList = loadCountyLocationsForState(stateId);
                countyList = $.parseJSON(countyList);
                for (var countyId in countyList) {
                    var countyName = countyList[countyId];
                    stateCountyListMap[stateId].push(countyId);
                    countyNameMap[countyId] = countyName;
                    countyStateMap[countyId] = stateId;
                }
            }
  
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            addmsg(textStatus + " (" + errorThrown + ")");
            setTimeout(
                poll, /* Try again after.. */
                15000); /* milliseconds (15seconds) */
        }
    });
}

function loadCountyLocationsForState(state) {
    var returnString;
    $.ajax({
        type: "GET",
        url: "simulation/population/location_data_request.php?requestType=counties&state=" + state,

        async: false, /* If set to non-async, browser shows page as "Loading.."*/
        cache: false,
        timeout:50000, /* Timeout in ms */

        success: function(jasonObj, statusText){ /* called when request to barge.php completes */

            returnString = jasonObj;
            
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            addmsg(textStatus + " (" + errorThrown + ")");
            setTimeout(
                poll, /* Try again after.. */
                15000); /* milliseconds (15seconds) */
        }
    });
    
    return returnString;
}

function createPopulationHtmlTree() {
    
    var textBoxWidth = "50px";
    var outerDivWidth = "215px"; 
    var innerDivWidth = "140px";
    var fractionsBackgroundColor = "#F5FAFF"; // for outer div and li elements
    var liBackgroundColor = "#E0F0FF";

    var susceptibleFraction =  "0.94859";
    var infectedFraction = "0.00538";
    var infectiousFraction = "0.00603";
    var immuneFraction = "0.04";
    
    var html = "";
    html = " <li style=\"background-color: " + liBackgroundColor + "\"><span id=\"span-name-US\">United States</span>"
    + "<ul rel=\"open\">"
    + "<div id=\"div-US\" style=\"width: " + outerDivWidth + "; text-align: left; background-color: " + fractionsBackgroundColor + "; padding: 7px; border: 1px solid; border-color: " + outerDivBorderColor + "\">"
    + "<li style=\"background-color: " + fractionsBackgroundColor + "\" id=\"li-entire-US\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Select United States</div><input type=\"checkbox\" id=\"check-US\" onClick=\"changeUsSelected()\"></li>"
    + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Fraction susceptible:</div><input type=\"text\" id=\"susceptible-US\" style=\"width: " + textBoxWidth + "\" value=\"" + susceptibleFraction + "\" onChange=\"checkFractions('US', 'United States', false)\"></input></li>"
    + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Fraction infected:</div><input type=\"text\" id=\"infected-US\" style=\"width: " + textBoxWidth + "\" value=\"" + infectedFraction + "\" onChange=\"checkFractions('US', 'Unites States', false)\"></input></li>"
    + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Fraction infectious:</div><input type=\"text\" id=\"infectious-US\" style=\"width: " + textBoxWidth + "\" value=\"" + infectiousFraction + "\" onChange=\"checkFractions('US', 'United States', false)\"></input></li>"
    + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Fraction immune:</div><input type=\"text\" id=\"immune-US\" style=\"width: " + textBoxWidth + "\" value=\"" + immuneFraction + "\" onChange=\"checkFractions('US', 'United States', false)\"></input></li>"
    + "<span id=\"span-fractions-sum-name-US\">Sum of fractions: </span><span id=\"span-fractions-sum-US\">1</span>"
    + "</div>"
    + "<li style=\"background-color: " + liBackgroundColor + "\"><span id=\"span-states-US\">States</span>"
    + "<ul rel=\"open\">";

    for (var index in stateIdList) {
        var stateId = stateIdList[index];
        var stateName = stateNameMap[stateId];
    
        html += "<li style=\"background-color: " + liBackgroundColor + "\"><span id=\"span-name-" + stateId + "\">" +  stateName + "</span>"
        + "<ul id=\"ul-" + stateId + "\">" 
        + "<div id=\"div-" + stateId + "\" style=\"width: " + outerDivWidth + "; text-align: left; background-color: " + fractionsBackgroundColor + "; padding: 7px; border: 1px solid; border-color: " + outerDivBorderColor + "\">"
        + "<li style=\"background-color: " + fractionsBackgroundColor + "\" id=\"li-" + stateId +  "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Select " + stateName + "</div><input type=\"checkbox\" id=\"check-" 
        + stateId +  "\" onClick=\"stateSelectOnClick('" + stateId + "','" + stateName + "','true')\"></li>"
        + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Fraction susceptible:</div><input type=\"text\" id=\"susceptible-" + stateId + "\" style=\"width: " + textBoxWidth + "\" value=\"" + susceptibleFraction + "\" onChange=\"checkFractions('" + stateId + "', '" + stateName + "', false)\"></input></li>"
        + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Fraction infected:</div><input type=\"text\" id=\"infected-" + stateId + "\" style=\"width: " + textBoxWidth + "\" value=\"" + infectedFraction + "\" onChange=\"checkFractions('" + stateId + "', '" + stateName + "', false)\"></input></li>"
        + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Fraction infectious:</div><input type=\"text\" id=\"infectious-" + stateId + "\" style=\"width: " + textBoxWidth + "\" value=\"" + infectiousFraction + "\" onChange=\"checkFractions('" + stateId + "', '" + stateName + "', false)\"></input></li>"
        + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Fraction immune:</div><input type=\"text\" id=\"immune-" + stateId + "\" style=\"width: " + textBoxWidth + "\" value=\"" + immuneFraction + "\" onChange=\"checkFractions('" + stateId + "', '" + stateName + "', false)\"></input></li>"
        + "<span id=\"span-fractions-sum-name-" + stateId + "\">Sum of fractions: </span><span id=\"span-fractions-sum-" + stateId + "\">1</span>"
        + "</div>"
        + "<li style=\"background-color: " + liBackgroundColor + "\"><span id=\"span-counties-" + stateId + "\">Counties</span>"
        + "<ul>";

        var countyDataForState = stateCountyListMap[stateId];
        for (var id in countyDataForState) {
            var countyId = countyDataForState[id];
            var countyName = countyNameMap[countyId];
            
            if (countyName == '0') {
                continue;
            }

            html += "<li style=\"background-color: " + liBackgroundColor + "\"><span id=\"span-name-" + countyId + "\">" + countyName + "</span>"
            + "<ul id=\"ul-" + countyId + "\">"    
            + "<div id=\"div-" + countyId + "\" style=\"width: " + outerDivWidth + "; text-align: left; background-color: " + fractionsBackgroundColor + "; padding: 7px; border: 1px solid; border-color: " + outerDivBorderColor + "\">"
            + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Select " + countyName + "</div><input type=\"checkbox\" id=\"check-" 
            + countyId + "\" onClick=\"countySelectOnClick('" + countyId + "','" + stateId +  "','"
            + stateName + "','" + countyName + "')\"></li>"
            + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Fraction susceptible:</div><input type=\"text\" id=\"susceptible-" + countyId + "\" style=\"width: " + textBoxWidth + "\" value=\"" + susceptibleFraction + "\" onChange=\"checkFractions('" + countyId + "', '" + countyName + ", " + stateName + "', false)\"></input></li>"
            + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Fraction infected:</div><input type=\"text\" id=\"infected-" + countyId + "\" style=\"width: " + textBoxWidth + "\" value=\"" + infectedFraction + "\" onChange=\"checkFractions('" + countyId + "',  '" + countyName + ", " + stateName + "', false)\"></input></li>"
            + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Fraction infectious:</div><input type=\"text\" id=\"infectious-" + countyId + "\" style=\"width: " + textBoxWidth + "\" value=\"" + infectiousFraction + "\" onChange=\"checkFractions('" + countyId + "',  '" + countyName + ", " + stateName + "', false)\"></input></li>"
            + "<li style=\"background-color: " + fractionsBackgroundColor + "\"><div style=\"display: inline-block; width: " + innerDivWidth + "\">Fraction immune:</div><input type=\"text\" id=\"immune-" + countyId + "\" style=\"width: " + textBoxWidth + "\" value=\"" + immuneFraction + "\" onChange=\"checkFractions('" + countyId + "',  '" + countyName + ", " + stateName + "', false)\"></input></li>"
            + "<span id=\"span-fractions-sum-name-" + countyId + "\">Sum of fractions: </span><span id=\"span-fractions-sum-" + countyId + "\">1</span>"
            + "</div>"
            +"</ul>"
            +"</li>";
        }
        
        html += "</ul>" + "</li>" + "</ul>" + "</li>";
    }
    
    html += "</ul></li></ul></li>";

    return html;
}

function addCountyToSelected(countyId) {

    storedSelectedCountyLocations.push(countyId);
}

function removeCountyFromSelected(countyId) {
    
    var index = storedSelectedCountyLocations.indexOf(countyId);
    if (index > -1) {
        storedSelectedCountyLocations.splice(index, 1);
    }
}

function addStateToSelected(stateId) {
    
    storedSelectedStateLocations.push(stateId);
}

function removeStateFromSelected(stateId) {
    
    var index = storedSelectedStateLocations.indexOf(stateId);
    if (index > -1) {
        storedSelectedStateLocations.splice(index, 1);
    }
}

// changes or resets the colors which indicate errors in the fractions
function changeFractionErrorColors(regionId, textcolor, bordercolor) {
    $("#div-" + regionId).css({
        "border-color":bordercolor
    });
    
    $("#span-name-" + regionId).css({
        "color":textcolor
    });
    
    $("#span-fractions-sum-name-" + regionId).css({
        "color":textcolor
    });
    
    $("#span-fractions-sum-" + regionId).css({
        "color":textcolor
    });
//    
//    $("#span-name-US").css({
//        "color":textcolor
//    });
    
//    if (regionId != "US") {
//        $("#span-states-US").css({
//            "color":textcolor
//        });
//    }
    
//    if (regionId.length == 5) {
//        var stateId = countyStateMap[regionId];
//        $("#span-name-" + stateId).css({
//            "color":textcolor
//        });
//        $("#span-counties-" + stateId).css({
//            "color":textcolor
//        });
//    }
}

// this is called when changing a fraction in a textbox
function checkFractionValue(regionId, regionName, diseaseState, print) {
    
    // don't need to use BigDecimal here because we are only checking if
    // the value is a number between 0 and 1; parseFloat works fine
    // and won't give an error if the value is not a number
    var value = parseFloat($("#" + diseaseState + "-" + regionId).val());
    if (!isNumber(value) || !isFraction(value)) {
        if (print) {
            addmsg("<b>ERROR:</b> The value entered for the " + diseaseState + " fraction for region <i>" + regionName + "</i> is not a valid fraction between 0 and 1.")
        }
        changeFractionErrorColors(regionId, 'red', 'red');
        
        if (!isNumber(value)) {
            return 'not_number';
        }
        if (!isFraction(value)) {
            return 'not_fraction';
        }
    }
    
    return true;
}

function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function isFraction(n) {
    return (n <= 1.0 && n >= 0.0);
}

function displayFractionSum(regionId) {
    var susceptible = new BigDecimal($("#susceptible-" + regionId).val());
    var infected = new BigDecimal($("#infected-" + regionId).val());
    var infectious = new BigDecimal($("#infectious-" + regionId).val());
    var immune = new BigDecimal($("#immune-" + regionId).val());
    var sum = susceptible.add(infected.add(infectious.add(immune))).floatValue();
    // update sum of fractions
    setFractionSumTextValue(regionId, sum);
    return sum;
}

function checkFractionSums(regionId, regionName, print) {
    
    var sum = displayFractionSum(regionId);

    if (sum != 1.0) {
        if (print) {
            addmsg("<b>ERROR: </b> The fractions for region <i>" + regionName + "</i> do not add to 1. ");
        }
        changeFractionErrorColors(regionId, 'red', 'red');
        return false;
    }
    
    return true;
}

function setFractionSumTextValue(regionId, value) {
    $("#span-fractions-sum-" + regionId).text((value));
}

function checkFractions(regionId, regionName, print) {
    
    
    var fraction = checkFractionValue(regionId, regionName, 'susceptible', print);
    if (fraction == 'not_fraction' || fraction == 'not_number') {
        if (fraction == 'not_number') {
            setFractionSumTextValue(regionId, "NaN");
        } else if (fraction == 'not_fraction') {
            displayFractionSum(regionId);
        }
        return false;
    }
    
    fraction = checkFractionValue(regionId, regionName, 'infected', print);
    if (fraction == 'not_fraction' || fraction == 'not_number') {
        if (fraction == 'not_number') {
            setFractionSumTextValue(regionId, "NaN");
        } else if (fraction == 'not_fraction') {
            displayFractionSum(regionId);
        }
        return false;
    }
    
    fraction = checkFractionValue(regionId, regionName, 'infectious', print);
    if (fraction == 'not_fraction' || fraction == 'not_number') {
        if (fraction == 'not_number') {
            setFractionSumTextValue(regionId, "NaN");
        } else if (fraction == 'not_fraction') {
            displayFractionSum(regionId);
        }
        return false;
    }
    
    fraction = checkFractionValue(regionId, regionName, 'immune', print);
    if (fraction == 'not_fraction' || fraction == 'not_number') {
        if (fraction == 'not_number') {
            setFractionSumTextValue(regionId, "NaN");
        } else if (fraction == 'not_fraction') {
            displayFractionSum(regionId);
        }
        return false;
    }
    
    if (!checkFractionSums(regionId, regionName, print)) {
        return false;
    }
    
    changeFractionErrorColors(regionId, 'green', 'green')
    setTimeout(function(){
        changeFractionErrorColors(regionId, "black", outerDivBorderColor);
    }, 2000);
    
    return true;
}

function storeSelectedCountyFractions(countyId) {
    // at this point all fractions are valid
    
    var susceptible = (new BigDecimal($("#susceptible-" + countyId).val())).floatValue();
    var infected = (new BigDecimal($("#infected-" + countyId).val())).floatValue();
    var infectious = (new BigDecimal($("#infectious-" + countyId).val())).floatValue();
    var immune = (new BigDecimal($("#immune-" + countyId).val())).floatValue();
    
    var countyFractions = new Object();
    countyFractions.susceptible = susceptible;
    countyFractions.infected = infected;
    countyFractions.infectious = infectious;
    countyFractions.immune = immune;
    countyFractions.countyId = countyId;
    countyFractions.stateId = countyStateMap[countyId];
    
    storedSelectedCountyFractions[countyId] = countyFractions;
}

function storeSelectedStateFractions(stateId) {
    
    var susceptible = (new BigDecimal($("#susceptible-" + stateId).val())).floatValue();
    var infected = (new BigDecimal($("#infected-" + stateId).val())).floatValue();
    var infectious = (new BigDecimal($("#infectious-" + stateId).val())).floatValue();
    var immune = (new BigDecimal($("#immune-" + stateId).val())).floatValue();
    
    var stateFractions = new Object();
    stateFractions.susceptible = susceptible;
    stateFractions.infected = infected;
    stateFractions.infectious = infectious;
    stateFractions.immune = immune;
    
    storedSelectedStateFractions[stateId] = stateFractions;
}

function storeUsFractions() {
    
    storedUsFractions = new Object();
    var susceptible = (new BigDecimal($("#susceptible-US").val())).floatValue();
    var infected = (new BigDecimal($("#infected-US").val())).floatValue();
    var infectious = (new BigDecimal($("#infectious-US").val())).floatValue();
    var immune = (new BigDecimal($("#immune-US").val())).floatValue();
    storedUsFractions.susceptible = susceptible;
    storedUsFractions.infected = infected;
    storedUsFractions.infectious = infectious;
    storedUsFractions.immune = immune;
}

function storeSelectedRegionFractions() {
    
    var errors = false;
    for (var id in storedSelectedCountyLocations) {
        var countyId = storedSelectedCountyLocations[id];
        var countyName = countyNameMap[countyId];
        var stateName = stateNameMap[countyStateMap[countyId]];
        if (checkFractions(countyId, countyName + ", " + stateName, 'true')) {
            storeSelectedCountyFractions(countyId);
        } else {
            errors = true;
        }
    }
    
    for (var id in storedSelectedStateLocations) {
        var stateId = storedSelectedStateLocations[id];
        var stateName = stateNameMap[stateId];
        if (checkFractions(stateId, stateName, 'true')) {
            storeSelectedStateFractions(stateId);
        } else {
            errors = true;
        }
    }
    
    if ($("#check-US").is(":checked")) {
        if (checkFractions("US", "United States", 'true')) {
            storeUsFractions();
        } else {
            errors = true;
        }
    }
    
    storedFractionsForAllRegions = new Object();
    storedFractionsForAllRegions.countyFractions = storedSelectedCountyFractions;
    storedFractionsForAllRegions.stateFractions = storedSelectedStateFractions;
    storedFractionsForAllRegions.usFractions = storedUsFractions;
    storedFractionsForAllRegionsJson = JSON.stringify(storedFractionsForAllRegions);
    
    if (!errors) {
        refreshMapData(storedFractionsForAllRegions);
        addmsg('Initial population fractions for selected regions were stored successfully.');
    } else {
        addmsg('<b>ERROR:</b> Initial population fractions for selected regions were NOT stored successfully.');
    }

}

function restoreSelectedRegions() {
    
    for (var id in storedSelectedStateLocations) {
        var stateId = storedSelectedStateLocations[id];
        $("#check-" + stateId).attr('checked', true);
        ddtreemenu.expandSubTree("population-tree", document.getElementById("ul-" + stateId));
        stateSelectOnClick(stateId, stateNameMap[stateId], true);
    }
    
    for (var id in storedSelectedCountyLocations) {
        var countyId = storedSelectedCountyLocations[id];
        $("#check-" + countyId).attr('checked', true);
        ddtreemenu.expandSubTree("population-tree", document.getElementById("ul-" + countyId));
        var stateId = countyStateMap[countyId];
        countySelectOnClick(countyId, stateId, null, countyNameMap[countyId])
    }
    
    if (usSelected) {
        $("#check-US").attr('checked', true);  
    } else {
        $("#check-US").attr('checked', false); 
    }
}

function restoreSelectedFractions() {
    
    for (var countyId in storedSelectedCountyFractions) {
        var susceptible = storedSelectedCountyFractions[countyId].susceptible;
        var infected = storedSelectedCountyFractions[countyId].infected;
        var infectious = storedSelectedCountyFractions[countyId].infectious;
        var immune = storedSelectedCountyFractions[countyId].immune;
        $("#susceptible-" + countyId).val(susceptible);
        $("#infected-" + countyId).val(infected);
        $("#infectious-" + countyId).val(infectious);
        $("#immune-" + countyId).val(immune);
    }
    
    for (var stateId in storedSelectedStateFractions) {
        susceptible = storedSelectedStateFractions[stateId].susceptible;
        infected = storedSelectedStateFractions[stateId].infected;
        infectious = storedSelectedStateFractions[stateId].infectious;
        immune = storedSelectedStateFractions[stateId].immune;
        $("#susceptible-" + stateId).val(susceptible);
        $("#infected-" + stateId).val(infected);
        $("#infectious-" + stateId).val(infectious);
        $("#immune-" + stateId).val(immune);
    }
    
    if (usSelected) {
        susceptible = storedUsFractions.susceptible;
        infected = storedUsFractions.infected;
        infectious = storedUsFractions.infectious;
        immune = storedUsFractions.immune;
        $("#susceptible-US").val(susceptible);
        $("#infected-US").val(infected);
        $("#infectious-US").val(infectious);
        $("#immune-US").val(immune);
    }
    
}

function loadStoredFractionsInMap() {
    refreshMapData(storedFractionsForAllRegions);
}

/////////////////////// SELECT BOX EVENTS /////////////////////////////////
// this is called when selecting a county box
function countySelectOnClick(checkboxid, stateId, stateName, location) {
                
    if ($("#check-" + checkboxid).is(":checked")) {
        addCountyToSelected(checkboxid);
    } else {            
        removeCountyFromSelected(checkboxid);
    }
}

// this is called when selecting a state box
function stateSelectOnClick(checkboxId, location, isState) {
                
    if ($("#check-" + checkboxId).is(":checked")) {
        addStateToSelected(checkboxId);
    } else {
        removeStateFromSelected(checkboxId);
    }
}

// this is called when selecting the US box
function changeUsSelected() {
    if ($("#check-US").is(":checked")) {
        usSelected = true;
    } else {
        usSelected = false;
    }
}

jQuery(document).ready(function(){
    
    loadStateAndCountyLocations();
});

