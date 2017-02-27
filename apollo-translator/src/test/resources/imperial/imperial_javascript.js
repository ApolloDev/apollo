/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function identity(p) {
    
    return p;
}

function identityAsDouble(p) {
	//convert to a double to 10 decimal places, and trim off trailing zeros
    var res = parseFloat((p).toFixed(10)).toString(); 
    //if there are NO trailing zeros at all (i.e. "1"), then add one (i.e. "1.0")
    if (res.indexOf(".") == -1) {
    	res = res + ".0";
    }
    return res;
}

function suscAge(populationStates) {
    
    var diseaseCensusResult = populationStates.get(0);
    var location = diseaseCensusResult.getLocation();
    var locationId = location.getLocationsIncluded().get(0);
    
    var diseaseStatesArray = diseaseCensusResult.getPopulationStates().getValues();
    
    var initSusceptible = diseaseStatesArray.get(0);
    
    var returnValue = "";
    
    for (var i = 0; i < 20; i++) {
        returnValue += "<susc band=\"" + i + "\" value=\"" + initSusceptible + "\"/>";
    }
    
    returnValue += "";
    return returnValue;
}

function ageGroups() {
    
    var result = "<band max=\"5\" min=\"0\"/>"
    +  "<band max=\"10\" min=\"5\"/>"
    +  "<band max=\"15\" min=\"10\"/>"
    +  "<band max=\"20\" min=\"15\"/>"
    +  "<band max=\"25\" min=\"20\"/>"
    +  "<band max=\"30\" min=\"25\"/>"
    +  "<band max=\"35\" min=\"30\"/>"
    +  "<band max=\"40\" min=\"35\"/>"
    +  "<band max=\"45\" min=\"40\"/>"
    +  "<band max=\"50\" min=\"45\"/>"
    +  "<band max=\"55\" min=\"50\"/>"
    +  "<band max=\"60\" min=\"55\"/>"
    +  "<band max=\"65\" min=\"60\"/>"
    +  "<band max=\"70\" min=\"65\"/>"
    +  "<band max=\"75\" min=\"70\"/>"
    +  "<band max=\"80\" min=\"75\"/>"
    +  "<band max=\"85\" min=\"80\"/>"
    +  "<band max=\"90\" min=\"85\"/>"
    +  "<band max=\"95\" min=\"90\"/>"
    +  "<band max=\"120\" min=\"95\"/>"

    return result;
}