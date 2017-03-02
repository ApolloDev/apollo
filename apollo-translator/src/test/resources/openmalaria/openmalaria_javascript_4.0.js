var hostIdToNameMap = {};
hostIdToNameMap[7173] = "arabiensis";
hostIdToNameMap[7165] = "gambiae";
hostIdToNameMap[62324] = "funestus";

var namepsace = new Object();
var namespaceList = Array();
namepsace.id = "om";
namepsace.namespace = "http://openmalaria.org/schema/scenario_32";
namespaceList.push(namepsace);

function identity(xPathString, p) {

    return getSingleReturnObject(xPathString, p);
}

function getTimeIntervalsBetween(startDate, endDate) {

    // for open malaria, assume the base time interval is 5 days

    var startTimeMillis = startDate.toGregorianCalendar().getTimeInMillis();
    var endTimeMillis = endDate.toGregorianCalendar().getTimeInMillis();

    var daysBetween = Math.round((endTimeMillis - startTimeMillis) / 432000000); // number of millis in 5 days
    return daysBetween;

}

function getSingleReturnObject(xPathString, value) {
    var xpathObjectArray = new Array();

    var xpathObject = new Object();

    xpathObject.xpath = xPathString;
    xpathObject.value = value;
    xpathObjectArray.push(xpathObject);

    return getMultiReturnObject(xpathObjectArray);
}

function getMultiReturnObject(xpathObjectArray) {
    var returnObject = new Object();
    returnObject.xPathObjectArray = xpathObjectArray;
    returnObject.namespaces = namespaceList;
    return returnObject;
}

function callRestWebService(url) {

    // variables used for the connection, best to import them via the table in a ToGeneric Pass
    var urlString = url;
    var urlParameters = "attribute=value";
    var httpMethod = "GET"; //or GET
    var username = "";
    var password = "";
    //var encoding = uToBase64(username + ":" + password);

    // In case of GET, the url parameters have to be added to the URL
    if (httpMethod == "GET") {
        var url = new Packages.java.net.URL(urlString);
        var connection = url.openConnection();
        // connection.setRequestProperty("Authorization", "Basic " + encoding);
        // connection.setRequestMethod(httpMethod);
    }
    // In case of POST, the url parameters have to be transfered inside the body
    if (httpMethod == "POST") {
        // open the connection
        var url = new URL(urlString);
        var connection = url.openConnection();
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        connection.setRequestMethod(httpMethod);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        //connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
        connection.setUseCaches(false);
        var os = new DataOutputStream(connection.getOutputStream());
        os.writeBytes(urlParameters);
        os.flush();
        os.close();
    }

    //get the result and print it out
    var responseCode = connection.getResponseCode();

    var is = connection.getInputStream();
    var isr = new Packages.java.io.InputStreamReader(is);
    var br = new Packages.java.io.BufferedReader(isr);
    var response = new Packages.java.lang.StringBuffer();
    var line;
    while ((line = br.readLine()) != null) {
        response.append(line);
    }
    br.close();

    var responseString = response.toString();
    // responseString = responseString.replace("3_0_0", "3_0_2");

    //printline("Sending " + httpMethod + " Request to URL: " + urlString);
    //printline("Response Code: " + responseCode);

    connection.disconnect();
    return responseString;
}


function getModel(xPathString, softwareIdentification, modelPathsFile) {

    // EVENTUALLY THIS WILL LOAD FROM LIBRARY
    // FOR NOW, USING FILES

    // var responseString = callRestWebService();
    // var response = parseApolloObjectFromXml(responseString, "Response", "http://services-common.apollo.pitt.edu/v3_0_2/");

    //var xml = response.getResponseBody().get(0);
    // printline(xml);

    var softwareVersion = softwareIdentification.getSoftwareVersion();

    var fileContents = readFile(modelPathsFile);
    var arr = fileContents.split('\n');
    var value = "<defaultWrapper>";
    for (i = 1; i < arr.length; i++) { // skip header
        var line = arr[i].split(',');
        var version = line[0];
        if (softwareVersion == version) {
            var filepath = line[1];
            var xml = readFile(filepath);
            value += xml;
            break;
        }
    }

    value += "</defaultWrapper>";

    return getSingleReturnObject(xPathString, value);

}

function getScientificNameFromNcbiTaxonId(taxonId) {

    return hostIdToNameMap[taxonId];
}

function getTotalEIR(infectionAcquisitions) {
    var totalEIR = 0;
    for (var i = 0; i < infectionAcquisitions.size(); i++) {
        var infectionAcquisition = infectionAcquisitions.get(i);
        var eir = parseFloat(infectionAcquisition.getInoculationRate().getValue());
        totalEIR += eir;
    }

    return totalEIR;
}

function getScaledAnnualEIR(xPathString, infectionAcquisitions) {
    var totalEIR = getTotalEIR(infectionAcquisitions);

    return getSingleReturnObject(xPathString, totalEIR);
}

function getAnnualEIRValues(xPathString, infectionAcquisitions) {

    var xpathObjectArray = new Array();

    // first get total eir
    var totalEIR = getTotalEIR(infectionAcquisitions);

    for (var i = 0; i < infectionAcquisitions.size(); i++) {
        var xpathObject = new Object();
        var infectionAcquisition = infectionAcquisitions.get(i);
        var eir = parseFloat(infectionAcquisition.getInoculationRate().getValue());
        eir = eir / totalEIR;

        var hostTaxonId = infectionAcquisition.infectedHost;
        var scientificName = getScientificNameFromNcbiTaxonId(hostTaxonId);
        var re = new RegExp("\\$HOST", 'g');
        var xPathStringReplaced = xPathString.replace(re, scientificName);

        xpathObject.xpath = xPathStringReplaced;
        xpathObject.value = eir;
        xpathObjectArray.push(xpathObject);
    }

    return getMultiReturnObject(xpathObjectArray);
}

function getLocationName(xPathString, location) {

    return getSingleReturnObject(xPathString, "test location");

}

function getITNDeploySchedule(xPathString, population, outputScheduleElements, startDate, location) {

    var value = "<defaultWrapper>";
    for (var i = 1; i < outputScheduleElements.size(); i++) {
        var scheduleElement = outputScheduleElements.get(i);
        currentDate = scheduleElement.getDateTime();
        var timeStepsBetween = getTimeIntervalsBetween(startDate, currentDate);
        var coverage = parseFloat(scheduleElement.getQuantity()) / population;

        value += "<deploy coverage=\"" + coverage + "\" time=\"" + timeStepsBetween + "\" />\n";
    }
    value += "</defaultWrapper>";

    return getSingleReturnObject(xPathString, value);

}

function getIRSDeploySchedule(xPathString, population, outputScheduleElements, startDate, location) {

    var value = "<defaultWrapper>";
    for (var i = 1; i < outputScheduleElements.size(); i++) {
        var scheduleElement = outputScheduleElements.get(i);
        currentDate = scheduleElement.getDateTime();
        var timeStepsBetween = getTimeIntervalsBetween(startDate, currentDate);
        var coverage = parseFloat(scheduleElement.getQuantity()) / population;

        value += "<deploy coverage=\"" + coverage + "\" time=\"" + timeStepsBetween + "\" />\n";
    }
    value += "</defaultWrapper>";

    return getSingleReturnObject(xPathString, value);

}

function getAgeGroups(xPathString, censusDataCells) {

    var returnVal = "<defaultWrapper>";

    var minimumLowerBound = 1000;
    var ageRangeNodes = "";
    for (var i = 0; i < censusDataCells.size(); i++) {

        var dataCell = censusDataCells.get(i);
        var ageGroupCatDef = dataCell.getAgeRange();
        var lowerBound = parseInt(ageGroupCatDef.getLowerBound().getFiniteBoundary());
        var upperBound = parseInt(ageGroupCatDef.getUpperBound().getFiniteBoundary());
        if (lowerBound < minimumLowerBound) {
            minimumLowerBound = lowerBound;
        }
        var popPercent = dataCell.getFractionInState() * 100;

        ageRangeNodes += "<group poppercent=\"" + popPercent + "\" upperbound=\"" + upperBound + "\"/>\n";

    }

    returnVal += "<ageGroup lowerbound=\"" + minimumLowerBound + "\">\n";
    returnVal += ageRangeNodes;
    returnVal += "</ageGroup></defaultWrapper>";

    return getSingleReturnObject(xPathString, returnVal);

}

function getMaximumAge(xPathString, censusDataCells) {
    var maximumAgeUpperBound = 0;
    for (var i = 0; i < censusDataCells.size(); i++) {

        var dataCell = censusDataCells.get(i);
        var ageGroupCatDef = dataCell.getAgeRange();
        var upperBound = ageGroupCatDef.getUpperBound().getFiniteBoundary();
        if (upperBound > maximumAgeUpperBound) {
            maximumAgeUpperBound = parseInt(upperBound);
        }
    }

    return getSingleReturnObject(xPathString, maximumAgeUpperBound.toString());
}

function getControlStrategyTemplate(xPathString, templateUrls, simulator) {

    var content = "";
    for (var i = 0; i < templateUrls.size(); i++) {

        var templateUrl = templateUrls.get(i);
        var softwares = templateUrl.getSoftwareIdentification();
        for (var j = 0; j < softwares.size(); j++) {

            var software = softwares.get(j);
            var name = software.getSoftwareName();
            var version = software.getSoftwareVersion();

            if (name == simulator.getSoftwareName() && version == simulator.getSoftwareVersion()) {

                var content = callRestWebService(templateUrl.getControlMeasureTemplateUrl());

            }
        }

    }

    var returnObject = getSingleReturnObject(xPathString, content);
    if (content == "") {
        returnObject.print = false;
    }

    return returnObject;
}