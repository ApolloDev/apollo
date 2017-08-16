var apollo_version = "Apollo 4.0.2";
var seir_version = "SEIR 3.0";

function identity(value) {
    return value;
}

function getDaysBetween(startDate, endDate) {

    var startTimeMillis = startDate.toGregorianCalendar().getTimeInMillis();
    var endTimeMillis = endDate.toGregorianCalendar().getTimeInMillis();

    var daysBetween = Math.round((endTimeMillis - startTimeMillis) / 86400000);
    return daysBetween;

}

function getInitialSEIRCounts(populationFileLocation, censusData) {

    var censusDataCells = censusData.censusDataCells;
    var obj = new Object();
    obj.warnings = [];

    var fracSusceptible = 0;
    var fracExposed = 0;
    var fracInfectious = 0;
    var fracRecovered = 0;
    var fracSusceptibleSet = false;
    var fracExposedSet = false;
    var fracInfectiousSet = false;
    var ageRangeGenderWarningSet = false;
    for (var i = 0; i < censusDataCells.size(); i++) {
        var cell = censusDataCells.get(i);
        if (!ageRangeGenderWarningSet) {
            if (cell.getAgeRange() != null &&
                    cell.getGender() != null) {
                var warningsString = "no-one-to-one-mapping# " + seir_version + " does not allow setting the initial SEIR counts by age range or gender. Instead, the fractions of each infection state will be summed over all census data cells";
                obj.warnings.push(warningsString.toString());
                ageRangeGenderWarningSet = true;
            }
        }

        if (cell.getInfectionState() == "SUSCEPTIBLE") {
            fracSusceptibleSet = true;
            fracSusceptible += cell.getFractionInState();
        } else if (cell.getInfectionState() == "LATENT") {
            fracExposedSet = true;
            fracExposed += cell.getFractionInState();
        } else if (cell.getInfectionState() == "INFECTIOUS") {
            fracInfectiousSet = true;
            fracInfectious += cell.getFractionInState();
        } else if (cell.getInfectionState() == "RECOVERED") {
            fracRecovered += cell.getFractionInState();
        }
    }

    if (!fracSusceptibleSet) {
        throw "No initial fraction susceptible was specified in any PopulationImmunityAndInfectionCensusDataCell";
    } else if (!fracExposedSet && !fracInfectiousSet) {
        throw "No initial fraction exposed or infectious was specified in any PopulationImmunityAndInfectionCensusDataCell";
    }

    var fileContents = readFile(populationFileLocation);

    var incits = null;

    var location = censusData.getLocation();

    var locationCode = location.getApolloLocationCode();
    if (locationCode != null) {
        incits = locationCode;
    } else {

        var locationDefinition = location.getLocationDefinition();
        if (locationDefinition != null) {
            if (locationDefinition.getLocationsIncluded().size() == 1) {
                incits = locationDefinition.getLocationsIncluded().get(0);
            }
        }
    }

    if (incits.length() != 2 && incits.length() != 5) {
        throw seir_version + " does not currently support regions that are not states, counties, or the US";
    }

    var arr = fileContents.split('\n');
    var totalPop = 0;
    for (i = 0; i < arr.length; i++) {
        var line = arr[i].split(',');
        var state = line[3];
        var county = line[4];
        if (incits.length() == 2) {
            if (incits == 'US') {
                if (county == '000') {
                    totalPop += parseInt(line[7]);
                }
            } else if (incits == state && county == '000') {
                totalPop = parseInt(line[7]);
                break;
            }
        } else if (incits.length() == 5) {
            var fullCounty = state + county;
            if (incits == fullCounty) {
                totalPop = parseInt(line[7]);
                break;
            }
        }
    }

    var initSusceptible = fracSusceptible * totalPop;
    var initExposed = fracExposed * totalPop;
    var initInfectious = fracInfectious * totalPop;
    var initRecovered = fracRecovered * totalPop;

    obj.value = initSusceptible + "," + initExposed + "," + initInfectious + "," + initRecovered;
    return obj;
}

function getSchedule(scheduleElements, scheduleName, runLength) {

    var obj = new Object();
    obj.print = true;
    if (scheduleElements == null || scheduleElements.size() == 0) {
        throw "A null schedule was found in the getSchedule Javascript method";
    }

    var scheduleString = "";
    for (var i = 0; i < scheduleElements.size() - 1; i++) {
        scheduleString = scheduleString + scheduleElements.get(i) + ",";
    }
    scheduleString = scheduleString + scheduleElements.get(scheduleElements.size() - 1);

    if (scheduleElements.size() < runLength) {
        for (i = scheduleElements.size(); i < runLength; i++) {
            scheduleString = scheduleString + ",0";
        }

        obj.warnings = [];
        var warningString = "no-one-to-one-mapping#The " + scheduleName + " had a length less than the specified run length. Zeros were appended to the schedule so that the lengths match.";
        obj.warnings.push(warningString.toString());
    }

    obj.value = scheduleString;

    return obj;
}

function getVaccinationEfficacyDelay(vaccEffSinceDose) {

    if (vaccEffSinceDose == null) {
        throw "The VaccinationEfficacyConditionedOnTimeSinceDose was null";
    }

    var delay = 0.0;
    var efficacies = vaccEffSinceDose.getVaccinationEfficacyConditionedOnTimeSinceMostRecentDose();
    for (var i = 0; i < efficacies.size(); i++) {
        delay = delay + i * parseFloat(efficacies.get(i));
    }

    return delay;
}

function getR0(reproductionNumber) {

    if (reproductionNumber.getExactValue() !== null) {
        return reproductionNumber.getExactValue();
    } else {
        var uncertainValue = reproductionNumber.getUncertainValue();
        return uncertainValue.getMean();
    }
}