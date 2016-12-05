var apollo_version = "Apollo 4.0";
var anthrax_version = "Anthrax 1.0";

function identity(value) {
    return value;
}

function getHoursBetween(startDate, endDate) {

    var startTimeMillis = startDate.toGregorianCalendar().getTimeInMillis();
    var endTimeMillis = endDate.toGregorianCalendar().getTimeInMillis();

    var daysBetween = Math.round((endTimeMillis - startTimeMillis) / 3600000);
    return daysBetween;

}

function getRunDuration(timeSpecification) {

    var obj = new Object();
    obj.print = true;
    obj.warnings = [];

    var duration = timeSpecification.runLength;
    var unit = timeSpecification.getUnitOfTimeForSimulatorTimeStep();
    if (unit.toString().equals("DAY")) {
        obj.value = (duration * 24).toFixed(0);
        var warningString = "no-one-to-one-mapping# " + apollo_version + " 'runLength' was specified in days. This value was multiplied by 24 to get the " + anthrax_version + " parameter 'run_duration' which must be specified in hours.";
        obj.warnings.push(warningString.toString());
    } else if (unit.toString.equals("HOUR")) {
        obj.value = duration;
    } else {
        throw "A unit of time other than DAY or HOUR was specified in the " + apollo_version + " 'runLength'";
    }

    return obj;
}

function getPopulationCount(populationFileLocation, censusData, type) {

    var censusDataCells = censusData.censusDataCells;
    var obj = new Object();
    obj.warnings = [];

    var fractionOfType = 0;
    var fractionOfTypeSet = false;
    var ageRangeGenderWarningSet = false;

    var compareType;
    if (type.toString().equals("ASYMPTOMATIC")) {
        compareType = "LATENT";
    } else if (type.toString().equals("UNEXPOSED")) {
        compareType = "SUSCEPTIBLE";
    }

    for (var i = 0; i < censusDataCells.size(); i++) {
        var cell = censusDataCells.get(i);
        if (!ageRangeGenderWarningSet) {
            if (cell.getAgeRange() !== null &&
                    cell.getGender() !== null) {
                var warningsString = "no-one-to-one-mapping# " + anthrax_version + " does not allow setting the initial counts by age range or gender. Instead, the fractions of each infection state will be summed over all census data cells";
                obj.warnings.push(warningsString.toString());
                ageRangeGenderWarningSet = true;
            }
        }

        if (cell.getInfectionState() !== null) {
            if (cell.getInfectionState().toString().equals(compareType.toString())) {
                fractionOfTypeSet = true;
                fractionOfType += cell.getFractionInState();
            }
        } else if (cell.getDiseaseState() !== null) {
            if (cell.getDiseaseState().toString().equals(compareType.toString())) {
                fractionOfTypeSet = true;
                fractionOfType += cell.getFractionInState();
            }
        }
    }

    if (!fractionOfTypeSet) {
        throw "No initial fraction " + compareType + " was specified in any PopulationImmunityAndInfectionCensusDataCell";
    }

    var fileContents = readFile(populationFileLocation);

    var incits = null;

    var location = censusData.getLocation();

    var locationCode = location.getApolloLocationCode();
    if (locationCode !== null) {
        incits = locationCode;
    } else {

        var locationDefinition = location.getLocationDefinition();
        if (locationDefinition !== null) {
            if (locationDefinition.getLocationsIncluded().size() === 1) {
                incits = locationDefinition.getLocationsIncluded().get(0);
            }
        }
    }

    if (incits.length() !== 2 && incits.length() !== 5) {
        throw anthrax_version + " does not currently support regions that are not states, counties, or the US";
    }

    var arr = fileContents.split('\n');
    var totalPop = 0;
    for (i = 0; i < arr.length; i++) {
        var line = arr[i].split(',');
        var state = line[3];
        var county = line[4];
        if (incits.length() === 2) {
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

            if (incits.toString() == fullCounty) {
                totalPop = parseInt(line[7]);
                break;
            }
        }
    }

    var initCountForType = fractionOfType * totalPop;

    obj.value = initCountForType;
    return obj;
}

function getArraySchedule(startDate, scheduleElements) {

    // process first date separately

    var schedule = [];
    var totalDays = 0;
    var previousDate = startDate;
    var scheduleElement = scheduleElements.get(0);
    var currentDate = scheduleElement.getDateTime();

    var hoursBetween = getHoursBetween(previousDate, currentDate);
    if (hoursBetween > 0) {
        for (var i = 0; i < hoursBetween; i++) {
            totalDays++;
            schedule.push(0);
        }
    }

    totalDays++;
    schedule.push(parseInt(scheduleElement.getQuantity()));
    previousDate = currentDate;

    for (var i = 1; i < scheduleElements.size(); i++) {

        scheduleElement = scheduleElements.get(i);
        currentDate = scheduleElement.getDateTime();

        var hoursBetween = getHoursBetween(previousDate, currentDate);

        if (hoursBetween > 1) {
            for (var i = 0; i < hoursBetween - 1; i++) {
                totalDays++;
                schedule.push(0);
            }
        }

        totalDays++;
        schedule.push(parseInt(scheduleElement.getQuantity()));
        previousDate = currentDate;
    }

    return schedule;
}

function getSchedule(logisticalSystemNode, runLength, startDate) {

    var obj = new Object();
    obj.print = true;
    if (logisticalSystemNode === null) {
        throw "A null logisticalSystemNode was found in the getSchedule Javascript method";
    }

    var supplyScheduleElements = logisticalSystemNode.getOutputSchedule().getScheduleElements();
    var administrationScheduleElements = logisticalSystemNode.getChildren().get(0).getCapacitySchedule().getScheduleElements();

    // convert the schedule elements into arrays and use the legacy code

    var capacity = getArraySchedule(startDate, administrationScheduleElements);
    var supply = getArraySchedule(startDate, supplyScheduleElements);

    //var capacity = controlStrategyLogistics.administrationCapacityPerDay;
    //var supply = controlStrategyLogistics.supplySchedulePerDay;
    var scheduleString = "";
    var totalSurplus = 0;
    var useToday = 0;

    for (var i = 0; i < supply.length; i++) {

        var supplyVal = supply[i];
        var capacityVal = capacity[i];

        var valueForToday;
        if (supplyVal >= capacityVal) {
            totalSurplus = supplyVal - capacityVal;
            valueForToday = capacityVal;
        } else { // supply is less than capacity
            useToday = supplyVal;
            var amountExtraWeCanUse = capacityVal - useToday;
            if (totalSurplus <= amountExtraWeCanUse) {
                useToday += totalSurplus;
                totalSurplus = 0;
            } else { // totalSurplus > amount we can use
                useToday += amountExtraWeCanUse;
                totalSurplus -= amountExtraWeCanUse;
            }
            valueForToday = useToday;
        }

        var increment = (valueForToday);
      //  for (var j = 0; j < 24; j++) {
            scheduleString = scheduleString + increment + ",";
       // }
    }

    if (capacity.length < runLength) {
        for (i = capacity.length; i < runLength; i++) {
            scheduleString = scheduleString + "0,";
        }

        obj.warnings = [];
        var warningString = "no-one-to-one-mapping#The computed schedule had a length less than the specified run length. Zeros were appended to the schedule so that the lengths match.";
        obj.warnings.push(warningString.toString());
    }

    scheduleString = scheduleString.substring(0, scheduleString.length - 1);

    obj.value = scheduleString;
    return obj;
}

function getBeginTreatmentInterval(triggers) {

    var obj = new Object();
    obj.print = true;
    obj.warnings = [];

    var foundTrigger = false;
    var time;
    for (var i = 0; i < triggers.size(); i++) {
        var trigger = triggers.get(i);
        if (trigger instanceof edu.pitt.apollo.types.v4_0.TemporalTriggerDefinition) {
            var timeScale = trigger.getTimeScale();
            if (timeScale == "SIMULATOR_TIME_SCALE") {
                foundTrigger = true;
                var duration = trigger.getTimeSinceTimeScaleZero();
                time = Math.round(duration.value * 24).toFixed(0); // duration is assumed to be days for now, but time is hours
                var warningString = "no-one-to-one-mapping#The Apollo 2.0.1 'controlMeasureStartTime' was specified in days, but Anthrax 1.0 parameter 'run_duration' must be specified in hours.";
                obj.warnings.push(warningString.toString());
                obj.value = time;
            }
        }
    }

    if (!foundTrigger) {
        var warningString = "no-apollo-param#No TemporalTriggerDefinition with a time scale of SIMULATOR_TIME_SCALE was specified in the control strategy start times. A TemporalTriggerDefinition with a time scale of SIMULATOR_TIME_SCALE is required to set the " + anthrax_version + " parameter begin_treatment_interval.";
        obj.warnings.push(warningString.toString());
    }

    return obj;
}

function getTreatmentEfficacy(efficacies, outcome) {

    var obj = new Object();
    obj.print = true;
    obj.warnings = [];

    var foundValue = false;
    for (var i = 0; i < efficacies.size(); i++) {
        var efficacy = efficacies.get(i);
        var drugEfficaciesByOutcome = efficacy.getDrugEfficaciesConditionedOnCurrentDiseaseOutcome();
        var firstVariable = drugEfficaciesByOutcome.getFirstConditioningVariable();
        var category = firstVariable.getCategories().get(0);
        var categoryDefinition = category.getCategoryDefinition();
        if (categoryDefinition.getDiseaseOutcome().toString().equals(outcome.toString())) {
            var distribution = category.getUnconditionalProbabilityDistribution();
            var probabilityValuePair = distribution.getProbabilityValuePairs().get(0);
            var value = probabilityValuePair.getValue();
            obj.value = value;
            foundValue = true;
            break;
        }
    }

    if (!foundValue) {
        var warningString = "no-native-param#No drug treatment efficacy conditioned on a DiseaseOutcome of " + outcome + " was specified";
        obj.warnings.push(warningString.toString());
    }

    return obj;
}

function getPeriodMean(uncertainDuration) {

    var obj = new Object();
    obj.print = true;
    obj.warnings = [];

    var unitOfTime = uncertainDuration.getUnitOfTime();
    var mean = uncertainDuration.getProbabilityDistribution().getMean();
    if (unitOfTime == "DAY") {
        var adjMean = mean * 24;
        obj.value = adjMean;

        var warningString = "no-one-to-one-mapping#The " + apollo_version + " duration has a unit of time of DAY, but Anthrax " + anthrax_version + " requires a unit of time of HOUR. The specified mean of the probability distribution has been multiplied by 24 to convert it to hours.";
        obj.warnings.push(warningString.toString());
    } else if (unitOfTime == "HOUR") {
        obj.value = mean;
    } else {
        throw "The unit of time " + unitOfTime + " used in an InfectiousDisease period distribution is not supported";
    }

    return obj;
}

function getPeriodStdDev(uncertainDuration) {

    var obj = new Object();
    obj.print = true;
    obj.warnings = [];

    var unitOfTime = uncertainDuration.getUnitOfTime();
    var mean = uncertainDuration.getProbabilityDistribution().getStandardDeviation();
    if (unitOfTime == "DAY") {
        var adjMean = mean * 24;
        obj.value = adjMean;

        var warningString = "no-one-to-one-mapping#The " + apollo_version + " duration has a unit of time of DAY, but Anthrax " + anthrax_version + " requires a unit of time of HOUR. The specified standard deviation of the probability distribution has been multiplied by 24 to convert it to hours.";
        obj.warnings.push(warningString.toString());
    } else if (unitOfTime == "HOUR") {
        obj.value = mean;
    } else {
        throw "The unit of time " + unitOfTime + " used in an InfectiousDisease period distribution is not supported";
    }

    return obj;
}