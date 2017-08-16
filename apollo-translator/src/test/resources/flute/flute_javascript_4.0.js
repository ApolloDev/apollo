var apollo_version = "Apollo 4.0";
var flute_version = "FluTE 1.15";
var defaultHighRiskFractions = [0.089, 0.089, 0.212, 0.212, 0.0];
var defaultPregnantFractions = [0.0, 0.0, 0.02771, 0.02069, 0.0];
var defaultEssentialFraction = 0.0;
var highRiskFractions = [-1, -1, -1, -1, -1];
var pregnantFractions = [-1, -1, -1, -1, -1];
var essentialFraction = -1;
var defaultAntiviralEfficacies = {};
defaultAntiviralEfficacies["INFECTION"] = 0.3;
defaultAntiviralEfficacies["INFECTIOUSNESS"] = 0.62;
defaultAntiviralEfficacies["SICKNESS_REQUIRING_MEDICAL_ATTENTION"] = 0.6;

var ageRanges = [[0, 4], [5, 18], [19, 29], [30, 64], [65]];

function objectForSkippingTerm() {
    var retObj = new Object();
    retObj.print = false;
    return retObj;
}

function identity(p) {

    return p;
}

function getDaysBetween(startDate, endDate) {

    var startTimeMillis = startDate.toGregorianCalendar().getTimeInMillis();
    var endTimeMillis = endDate.toGregorianCalendar().getTimeInMillis();

    var daysBetween = Math.round((endTimeMillis - startTimeMillis) / 86400000);
    return daysBetween;

}

function getArraySchedule(startDate, scheduleElements) {

    // process first date separately

    var schedule = [];
    var totalDays = 0;
    var previousDate = startDate;
    var scheduleElement = scheduleElements.get(0);
    var currentDate = scheduleElement.getDateTime();

    var hoursBetween = getDaysBetween(previousDate, currentDate);
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

        var hoursBetween = getDaysBetween(previousDate, currentDate);

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

function getLocationCode(location) {
    var locationId = null;
    var locationCode = location.getApolloLocationCode();
    if (locationCode != null) {
        locationId = locationCode;
    }

    var locationDefinition = location.getLocationDefinition();
    if (locationDefinition != null) {
        if (locationDefinition.getLocationsIncluded().size() == 1) {
            locationId = locationDefinition.getLocationsIncluded().get(0);
        }
    }

    if (locationId == null) {
        throw "No location code was found";
    }

    return locationId;
}

function getLocation(location) {

    var obj = new Object();
    var datafile = "usa";

    var locationId = getLocationCode(location);
    var value = "datafile " + datafile;

    var onlyState;
    var onlyCounty;
    if (locationId.length() === 2) {
        if (locationId != 'US') {
            onlyState = parseInt(locationId);
            value += "\nonlystate " + onlyState;
        } else {
            throw "FluTE does not currently support running on the US";
        }
    } else if (locationId.length() === 5) {
        var state = locationId.substring(0, 2);
        var county = locationId.substring(2, 5);
        onlyState = +state;
        onlyCounty = +county;
        value += "\nonlystate " + onlyState;
        value += "\nonlycounty " + onlyCounty;
    }

    obj.value = value;
    return obj;
}

function controlStrategyResponseDelay(controlStrategyType, controlStrategies) {

    var largestResponseDelay = null;
    var largestResponseDelayCm = "";
    var largestResponseDelayCmType = "";
    for (var i = 0; i < controlStrategies.size(); i++) {
        var cm = controlStrategies.get(i);
        var responseDelay = cm.getControlMeasureResponseDelay().getValue();

        if (largestResponseDelay == null || (responseDelay > largestResponseDelay)) {
            largestResponseDelay = responseDelay;

            if (cm instanceof edu.pitt.apollo.types.v4_0_2.IndividualTreatmentControlMeasure) {
                // get treatment
                var treatment = cm.getIndividualTreatment();
                if (treatment instanceof edu.pitt.apollo.types.v4_0_2.Vaccination) {
                    largestResponseDelayCm = "infectiousDiseaseControlStrategy[" + i + "]";
                    largestResponseDelayCmType = "vaccination";
                } else if (treatment instanceof edu.pitt.apollo.types.v4_0_2.AntiviralTreatment) {
                    largestResponseDelayCm = "infectiousDiseaseControlStrategy[" + i + "]";
                    largestResponseDelayCmType = "antiviralTreatment";
                }
            } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.PlaceClosureControlMeasure) {
                largestResponseDelayCm = "infectiousDiseaseControlStrategy[" + i + "]";
                largestResponseDelayCmType = "placeClosure";
            } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.CaseQuarantineControlMeasure) {
                largestResponseDelayCm = "infectiousDiseaseControlStrategy[" + i + "]";
                largestResponseDelayCmType = "caseQuarantine";
                //            } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.BorderControlStrategy) {
                //                largestResponseDelayCm = "infectiousDiseaseControlStrategy[" + i + "]";
                //                largestResponseDelayCmType = "border";
                //            } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.TravelRestrictionControlStrategy) {
                //                largestResponseDelayCm = "infectiousDiseaseControlStrategy[" + i + "]";
                //                largestResponseDelayCmType = "travelRestriction";
            }
        }
    }

    if (controlStrategyType.equalsIgnoreCase(largestResponseDelayCmType)) {
        return Math.round(largestResponseDelay);
    } else {
        var obj = new Object();
        obj.print = true;
        obj.warnings = [];

        var warningString = "no-native-param#" + flute_version + " only allows a single 'responsedelay' for all control strategies, "
                + "but multiple control strategies were specified in the " + apollo_version + " InfectiousDiseaseScenario. The largest response "
                + "delay will be used. If two or more control strategies of different types are tied for the "
                + "largest response delay, the value will be determined using the following priority: Vaccination, AntiviralTreatment, PlaceClosure, "
                + "CaseQuarantine. If two or more control strategies of the same type have the largest response delay, "
                + "whichever has the larger index in the InfectiousDiseaseScenario control strategies list will be used. For this run the response delay "
                + "is set for " + largestResponseDelayCm + ".";
        obj.warnings.push(warningString.toString());
        return obj;
    }
}

function controlStrategyResponseThreshold(controlStrategyType, controlStrategies) {

    var largestResponseThreshold = null;
    var largestResponseThresholdCm = "";
    var largestResponseThresholdCmType = "";
    //    var foundReactiveTrigger = false;
    var foundNonPercentThreshold = false;
    var foundWrongOperator = true;
    var obj = new Object();
    obj.print = false;
    obj.warnings = [];

    for (var i = 0; i < controlStrategies.size(); i++) {
        var cm = controlStrategies.get(i);

        var currentCsType = "";
        var triggers = cm.getControlMeasureStartTime();
        var thresholdSum = 0;
        var numTriggersUsed = 0;
        for (var j = 0; j < triggers.size(); j++) {
            var trigger = triggers.get(j);
            if (trigger instanceof edu.pitt.apollo.types.v4_0_2.DiseaseSurveillanceTriggerDefinition) {
                var operator = trigger.getReactiveControlMeasureOperator();
                if (operator == 'GREATER_THAN_OR_EQUAL') {
                    var unit = trigger.getUnitOfMeasureForThreshold();
                    if (unit == 'PERCENT') {
                        numTriggersUsed = numTriggersUsed + 1;
                        var responseThresholdJ = trigger.getReactiveControlMeasureThreshold();
                        thresholdSum += responseThresholdJ;
                    } else if (!foundNonPercentThreshold) {
                        foundNonPercentThreshold = true;
                        obj.print = true;
                        var warningString = "no-native-param#" + flute_version + " can only use " + apollo_version + " reactive control measure thresholds that are measured in percent.";
                        obj.warnings.push(warningString.toString());
                    }
                }
            } else if (!foundWrongOperator) {
                foundWrongOperator = true;
                obj.print = true;
                var warningString = "no-native-param#" + flute_version + " can only use " + apollo_version + " trigger definitions that have a reactiveControlMeasureOperator equal to 'GREATER_THAN_OR_EQUAL_TO'.";
                obj.warnings.push(warningString.toString());
            }
        }

        var responseThreshold = (thresholdSum / numTriggersUsed) / 100.0; // convert into percent

        if (cm instanceof edu.pitt.apollo.types.v4_0_2.IndividualTreatmentControlStrategy) {
            var treatment = cm.getIndividualTreatment();
            if (treatment instanceof edu.pitt.apollo.types.v4_0_2.Vaccination) {
                currentCsType = "vaccination";
            } else if (treatment instanceof edu.pitt.apollo.types.v4_0_2.AntiviralTreatment) {
                currentCsType = "antiviralTreatment";
            }
        } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.PlaceClosureControlStrategy) {
            currentCsType = "placeClosure";
        } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.CaseQuarantineControlStrategy) {
            currentCsType = "caseQuarantine";
        }


        if (numTriggersUsed == 0) {
            if (currentCsType.equalsIgnoreCase(controlStrategyType)) {
                return obj;
            } else {
                continue;
            }
        }

        if (largestResponseThreshold == null || (responseThreshold > largestResponseThreshold)) {
            largestResponseThreshold = responseThreshold;
            largestResponseThresholdCm = "infectiousDiseaseControlStrategy[" + i + "]";
            largestResponseThresholdCmType = currentCsType;
        }

    }

    //    if (!foundReactiveTrigger) {
    //        return obj;
    //    }

    if (controlStrategyType.equalsIgnoreCase(largestResponseThresholdCmType)) {
        obj.value = largestResponseThreshold;
        obj.print = true;
        return obj;
    } else {
        obj.print = true;

        var warningString = "no-native-param#" + flute_version + " only allows a single 'responsethreshold' for all control strategies, "
                + "but multiple control strategies were specified in the " + apollo_version + " InfectiousDiseaseScenario. The largest response "
                + "threshold will be used. If two or more control strategies of different types are tied for the "
                + "largest response threshold, the value will be determined using the following priority: Vaccination, AntiviralTreatment, PlaceClosure, "
                + "CaseQuarantine. If two or more control strategies of the same type have the largest response threshold, "
                + "whichever has the larger index in the InfectiousDiseaseScenario control strategies list will be used. For this run the response threshold "
                + "is set for " + largestResponseThresholdCm + ".";

        obj.warnings.push(warningString.toString());
        return obj;
    }
}


function controlStrategyAscertainmentFraction(controlStrategyType, controlStrategies) {

    var obj = new Object();
    obj.warnings = [];
    obj.print = false;
    var smallestAscertainmentFraction = null;
    var smallestAscertainmentFractionCm = "";
    var smallestAscertainmentFractionCmType = "";
    //    var foundReactiveTrigger = false;
    var foundNonSymptomaticCaseDef = false;
    for (var i = 0; i < controlStrategies.size(); i++) {
        var cm = controlStrategies.get(i);

        var currentCsType = "";
        var triggers = cm.getControlMeasureStartTime();
        var fractionSum = 0;
        var numTriggersUsed = 0;
        for (var j = 0; j < triggers.size(); j++) {
            var trigger = triggers.get(j);
            if (trigger instanceof edu.pitt.apollo.types.v4_0_2.DiseaseSurveillanceTriggerDefinition) {
                var diseaseSurveillanceCapacility = trigger.getDiseaseSurveillanceCapability();
                var caseDefinition = diseaseSurveillanceCapacility.getCaseDefinition();
                if (caseDefinition == 'SYMPTOMATIC') {
                    numTriggersUsed = numTriggersUsed + 1;
                    var fractionJ = diseaseSurveillanceCapacility.getSensitivityOfCaseDetection();
                    fractionSum += fractionJ;
                } else if (!foundNonSymptomaticCaseDef) {
                    foundNonSymptomaticCaseDef = true;
                    obj.print = true;
                    var warningString = "no-native-param#" + flute_version + " can only use reactive control strategy disease surveillance capabilities that have the disease outcome SYMPTOMATIC.";
                    obj.warnings.push(warningString.toString());
                }
            }
        }

        var ascertainmentFraction = fractionSum / numTriggersUsed;

        if (cm instanceof edu.pitt.apollo.types.v4_0_2.IndividualTreatmentControlStrategy) {
            var treatment = cm.getIndividualTreatment();
            if (treatment instanceof edu.pitt.apollo.types.v4_0_2.Vaccination) {
                currentCsType = "vaccination";
            } else if (treatment instanceof edu.pitt.apollo.types.v4_0_2.AntiviralTreatment) {
                currentCsType = "antiviralTreatment";
            }
        } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.PlaceClosureControlStrategy) {
            currentCsType = "placeClosure";
            //                } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.CaseQuarantineControlStrategy) {
            //                    smallestAscertainmentFractionCm = "infectiousDiseaseControlStrategy[" + i + "]";
            //                    smallestAscertainmentFractionCmType = "caseQuarantine";
            //                } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.BorderControlMeasure) {
            //                    smallestAscertainmentFractionCm = "infectiousDiseaseControlStrategy[" + i + "]";
            //                    smallestAscertainmentFractionCmType = "border";
            //                } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.TravelRestrictionControlMeasure) {
            //                    smallestAscertainmentFractionCm = "infectiousDiseaseControlStrategy[" + i + "]";
            //                    smallestAscertainmentFractionCmType = "travelRestriction";
        }

        if (numTriggersUsed == 0) {
            if (currentCsType.equalsIgnoreCase(controlStrategyType)) {
                return obj;
            } else {
                continue;
            }
        }

        if (smallestAscertainmentFraction == null || (ascertainmentFraction < smallestAscertainmentFraction)) {
            smallestAscertainmentFraction = ascertainmentFraction;
            smallestAscertainmentFractionCm = "infectiousDiseaseControlStrategy[" + i + "]";
            smallestAscertainmentFractionCmType = currentCsType;
        }


    }

    //    if (!foundReactiveTrigger) {
    //        return obj;
    //    }

    if (controlStrategyType.equalsIgnoreCase(smallestAscertainmentFractionCmType)) {
        obj.print = true;
        obj.value = smallestAscertainmentFraction;
        return obj;
    } else {
        obj.print = true;
        obj.warnings = [];

        var warningString = "no-native-param#" + flute_version + " only allows a single 'ascertainmentfraction' for all control strategies, "
                + "but multiple control strategies were specified in the " + apollo_version + " InfectiousDiseaseScenario. The smallest ascertainment "
                + "fraction will be used. If two or more control strategies of different types are tied for the "
                + "smallest ascertainment fraction, the value will be determined using the following priority: Vaccination, AntiviralTreatment, PlaceClosure."
                + " If two or more control strategies of the same type have the smallest ascertainment fraction, "
                + "whichever has the larger index in the InfectiousDiseaseScenario control strategies list will be used. For this run the ascertainment fraction "
                + "is set for " + smallestAscertainmentFractionCm + ".";
        obj.warnings.push(warningString.toString());
        return obj;
    }
}


function controlStrategyAscertainmentDelay(controlStrategyType, controlStrategies) {

    var obj = new Object();
    obj.print = false;
    obj.warnings = [];
    var largestAscertainmentDelay = null;
    var largestAscertainmentDelayCm = "";
    var largestAscertainmentDelayCmType = "";
    //    var foundReactiveTrigger = false;
    var foundNonSymptomaticCaseDef = false;
    for (var i = 0; i < controlStrategies.size(); i++) {
        var cm = controlStrategies.get(i);

        var currentCsType = "";
        var triggers = cm.getControlMeasureStartTime();
        var delaySum = 0;
        var numTriggersUsed = 0;
        for (var j = 0; j < triggers.size(); j++) {
            var trigger = triggers.get(j);
            if (trigger instanceof edu.pitt.apollo.types.v4_0_2.DiseaseSurveillanceTriggerDefinition) {
                var diseaseSurveillanceCapacility = trigger.getDiseaseSurveillanceCapability();
                var caseDefinition = diseaseSurveillanceCapacility.getCaseDefinition();
                if (caseDefinition == 'SYMPTOMATIC') {
                    numTriggersUsed = numTriggersUsed + 1;
                    var delayJ = diseaseSurveillanceCapacility.getTimeDelayOfCaseDetection().value;
                    delaySum += parseFloat(delayJ);
                } else if (!foundNonSymptomaticCaseDef) {
                    foundNonSymptomaticCaseDef = true;
                    obj.print = true;
                    var warningString = "no-native-param#" + flute_version + " can only use reactive control strategy disease surveillance capabilities that have the disease outcome SYMPTOMATIC.";
                    obj.warnings.push(warningString.toString());
                }
            }
        }

        //        if (numTriggersUsed == 0) {
        //            continue;
        //        }

        var ascertainmentDelay = delaySum / numTriggersUsed;
        //        foundReactiveTrigger = true;


        if (cm instanceof edu.pitt.apollo.types.v4_0_2.IndividualTreatmentControlStrategy) {
            var treatment = cm.getIndividualTreatment();
            if (treatment instanceof edu.pitt.apollo.types.v4_0_2.Vaccination) {
                currentCsType = "vaccination";
            } else if (treatment instanceof edu.pitt.apollo.types.v4_0_2.AntiviralTreatment) {
                currentCsType = "antiviralTreatment";
            }
        } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.PlaceClosureControlStrategy) {
            currentCsType = "placeClosure";
            //                } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.CaseQuarantineControlStrategy) {
            //                    largestAscertainmentDelayCm = "infectiousDiseaseControlStrategy[" + i + "]";
            //                    largestAscertainmentDelayCmType = "caseQuarantine";
            //                } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.BorderControlMeasure) {
            //                    largestAscertainmentDelayCm = "infectiousDiseaseControlStrategy[" + i + "]";
            //                    largestAscertainmentDelayCmType = "border";
            //                } else if (cm instanceof edu.pitt.apollo.types.v4_0_2.TravelRestrictionControlMeasure) {
            //                    largestAscertainmentDelayCm = "infectiousDiseaseControlStrategy[" + i + "]";
            //                    largestAscertainmentDelayCmType = "travelRestriction";
        }

        if (numTriggersUsed == 0) {
            if (currentCsType.equalsIgnoreCase(controlStrategyType)) {
                return obj;
            } else {
                continue;
            }
        }

        if (largestAscertainmentDelay == null || (ascertainmentDelay > largestAscertainmentDelay)) {
            largestAscertainmentDelay = ascertainmentDelay;
            largestAscertainmentDelayCm = "infectiousDiseaseControlStrategy[" + i + "]";
            largestAscertainmentDelayCmType = currentCsType;
        }

    }

    //    if (!foundReactiveTrigger) {
    //        return obj;
    //    }

    if (controlStrategyType.equalsIgnoreCase(largestAscertainmentDelayCmType)) {
        return largestAscertainmentDelay;
    } else {
        obj.print = true;
        obj.warnings = [];

        var warningString = "no-native-param#" + flute_version + " only allows a single 'ascertainmentdelay' for all control strategies, "
                + "but multiple control strategies were specified in the " + apollo_version + " InfectiousDiseaseScenario. The largest ascertainment "
                + "delay will be used. If two or more control strategies of different types are tied for the "
                + "largest ascertainment delay, the value will be determined using the following priority: Vaccination, AntiviralTreatment, PlaceClosure."
                + " If two or more control strategies of the same type have the largest ascertainment delay, "
                + "whichever has the larger index in the InfectiousDiseaseScenario control strategies list will be used. For this run the ascertainment delay "
                + "is set for " + largestAscertainmentDelayCm + ".";
        obj.warnings.push(warningString.toString());
        return obj;
    }
}

//function infectiousPeriod(infectiousPeriod) {
//    var obj = new Object();
//    obj.warnings = [];
//    if (parseFloat(infectiousPeriod) != 6) {
//        var warningString = "no-native-param# " + flute_version + " uses a fixed infectious period of 6.0 days.";
//        obj.warnings.push(warningString.toString());
//        return obj;
//    } else {
//        var warningString = "no-one-to-one-mapping# " + flute_version + " uses a fixed infectious period of 6.0 days.";
//        obj.warnings.push(warningString.toString());
//        return obj;
//    }
//}

function asympInfectionFraction(fraction) {

    var obj = new Object();
    obj.warnings = [];
    if (parseFloat(fraction) != 0.33) {

        var warningString = "no-native-param# " + flute_version + " uses a fixed asymptomatic infection fraction of 0.33.";
        obj.warnings.push(warningString.toString());
        return obj;
    } else {

        var warningString = "no-one-to-one-mapping# " + flute_version + " uses a fixed asymptomatic infection fraction of 0.33.";
        obj.warnings.push(warningString.toString());
        return obj;
    }
}

function timeStepUnit(unit) {

    if (unit != 'DAY') {
        var obj = new Object();
        obj.warnings = [];
        var warningString = "no-native-param# FluTE " + flute_version + " uses a constant time step unit of days.";
        obj.warnings.push(warningString.toString());
        return obj;
    } else {
        return objectForSkippingTerm();
    }
}

function timeStepValue(value) {

    if (parseFloat(value) != 0.5) {
        var obj = new Object();
        obj.warnings = [];
        var warningString = "no-native-param#FluTE " + flute_version + " uses a constant internal of 0.5 units per time step.";
        obj.warnings.push(warningString.toString());
        return obj;
    } else {
        return objectForSkippingTerm();
    }
}

function getSeedInfectedFrac(censusData) {

    var obj = new Object();
    obj.warnings = [];
    var warningsString;
//    var locationId = getLocationCode(censusData.getLocation());
    var censusDataCells = censusData.getCensusDataCells();

//    var population;
//    if (locationId == '06037') {
//        // this population comes from summing the populations in the 'la-tracts.dat' file used by FluTE
//        population = 11095039;
//    } else {
//        throw "FluTE does not currently support locations other than Los Angeles County, CA (06037)";
//    }

    var fracExposed = 0, fracInfectious = 0;
    var fracExposedSet = false, fracInfectiousSet = false;
    var ageRangeGenderWarningSet = false;
    for (var i = 0; i < censusDataCells.size(); i++) {

        var cell = censusDataCells.get(i);
        if (!ageRangeGenderWarningSet) {
            if (cell.getAgeRange() != null ||
                    cell.getSex() != null) {
                warningsString = "no-one-to-one-mapping# " + flute_version + " does not allow setting the initial infected by age range or gender. Instead, the fractions of LATENT and INFECTIOUS will be summed over all census data cells";
                obj.warnings.push(warningsString.toString());
                ageRangeGenderWarningSet = true;
            }
        }

        if (cell.getInfectionState() == "LATENT") {
            fracExposedSet = true;
            fracExposed += cell.getFractionInState();
        } else if (cell.getInfectionState() == "INFECTIOUS") {
            fracInfectiousSet = true;
            fracInfectious += cell.getFractionInState();
        }
    }

    if (!fracExposedSet && !fracInfectiousSet) {
        throw "No initial fraction exposed or infectious was specified in any PopulationImmunityAndInfectionCensusDataCell";
    }

    obj.print = true;
    obj.value = fracExposed + fracInfectious;
    return obj;
}

function preexistingImmunityByAge(censusDataCells) {

    var obj = new Object();
    obj.warnings = [];
    var warningsString;

    var totalFracRecovered = 0;
    var totalFracRecoveredFound = false;
    var fracRecoveredByAgeRange = [0, 0, 0, 0, 0];
    var ageRangesThatWereSet = [0, 0, 0, 0, 0];
    var genderWarningSet = false;
    var incorrectAgeRangeUnitWarningSet = false;
    for (var i = 0; i < censusDataCells.size(); i++) {

        var cell = censusDataCells.get(i);

        if (cell.getInfectionState() == "RECOVERED") {
            if (!genderWarningSet) {
                if (cell.getSex() != null) {
                    warningsString = "no-one-to-one-mapping# " + flute_version + " does not allow setting the preexisting immunity by gender. The fractions of RECOVERED having different genders but similar age ranges will be summed.";
                    obj.warnings.push(warningsString.toString());
                    genderWarningSet = true;
                }
            }

            if (cell.getAgeRange() == null) {
                //                warningsString = "no-one-to-one-mapping# " + flute_version + " requires preexisting immunity to be specified by 5 age ranges (0-4, 5-18, 19-29, 30-64, 65+). The RECOVERED fraction not associated with an age range will be used to set any of the 5 required age ranges that were not specified in the scenario.";  
                //                obj.warnings.push(warningsString.toString());
                totalFracRecovered += cell.getFractionInState(); // add in case there was more than 1 gender
                totalFracRecoveredFound = true;
            } else {
                var ageRange = cell.getAgeRange();
                // check bounds
                var lowerBound = ageRange.getLowerBound();
                var upperBound = ageRange.getUpperBound();

                // find if ageRange bounds are valid
                for (var j = 0; j < ageRanges.length; j++) {
                    if (lowerBound == ageRanges[j][0]) {
                        if ((j < 4 && upperBound == ageRanges[j][1]) || j >= 4) {

                            // check units of time
                            var unitOfTimeLowerBound = ageRange.getUnitOftimeForLowerBound();
                            var unitOfTimeUpperBound = ageRange.getUnitOfTimeForUpperBound();
                            if (unitOfTimeLowerBound != 'YEAR' || (j < 4 && unitOfTimeUpperBound != 'YEAR')) {
                                if (!incorrectAgeRangeUnitWarningSet) {
                                    if (cell.getAgeRange() == null) {
                                        warningsString = "no-one-to-one-mapping# " + flute_version + " requires preexisting immunity to be specified by age ranges defined in years. An age group is present in the scenario that has the correct bounds but is not defined in terms of years and will not be used.";
                                        obj.warnings.push(warningsString.toString());
                                    }
                                }
                            } else {
                                fracRecoveredByAgeRange[j] += cell.getFractionInInfectionState();
                                ageRangesThatWereSet[j] = 1;
                                break;
                            }

                        }
                    }
                }
            }
        }
    }

    var valueString = "";
    for (i = 0; i < ageRangesThatWereSet.length; i++) {
        if (ageRangesThatWereSet[i] == 0) {
            var ageRangeString;
            if (i < 4) {
                ageRangeString = "(" + ageRanges[i][0] + "-" + ageRanges[i][1] + ")";
            } else {
                ageRangeString = "(" + ageRanges[i][0] + "+)";
            }

            var explanationString;
            if (totalFracRecoveredFound) {
                explanationString = "The fraction for this age range will be set to the RECOVERED fraction specified in the scenario that is not associated with an age range.";
            } else {
                explanationString = "The fraction for this age range will be set to zero.";
            }

            warningsString = "no-one-to-one-mapping# No RECOVERED fraction was set in the scenario for the age range " + ageRangeString + ". " + explanationString;
            obj.warnings.push(warningsString.toString());
            fracRecoveredByAgeRange[i] = totalFracRecovered;
        }

        valueString = valueString + " " + fracRecoveredByAgeRange[i];
    }

    valueString = valueString.substr(1);
    obj.value = valueString;
    obj.print = true;

    return obj;

}

function preexistingImmunityLevel() {

    return "1.0";
}

function setFluteFractions(targetPopulationsAndPrioritizations) {

    var csTargetPopsAndPrioritizations = targetPopulationsAndPrioritizations.getControlMeasureTargetPopulationsAndPrioritization();
    if (csTargetPopsAndPrioritizations != null && csTargetPopsAndPrioritizations.size() > 0) {

        for (var i = 0; i < csTargetPopsAndPrioritizations.size(); i++) {
            var targetPriorityPopulation = csTargetPopsAndPrioritizations.get(i);
            var fractionToPrioritize = targetPriorityPopulation.getFractionOfTargetPopulationToPrioritize();
            var targetPopulationDefinition = targetPriorityPopulation.getTargetPopulationDefinition();
            if (targetPopulationDefinition != null) {
                var ageRange = targetPopulationDefinition.getAgeRange();
                var otherStrat = targetPopulationDefinition.getOtherStratification();
                if (ageRange != null && otherStrat != null) {
                    var lb = ageRange.getLowerBound().getFiniteBoundary();
                    var ub = ageRange.getUpperBound().getFiniteBoundary();
                    var unitlb = ageRange.getUnitOfTimeForLowerBound();
                    var unitub = ageRange.getUnitOfTimeForUpperBound();

                    if (unitlb == 'YEAR' && unitub == 'YEAR') {
                        if (lb == 17 && ub == 75 && otherStrat == 'ESSENTIAL_WORKFORCE') {
                            essentialFraction = fractionToPrioritize;
                        } else if (lb == 0 && ub == 4) {
                            if (otherStrat == 'HIGH_RISK') {
                                highRiskFractions[0] = fractionToPrioritize;
                            } else if (otherStrat == 'PREGNANT') {
                                pregnantFractions[0] = fractionToPrioritize;
                            }
                        } else if (lb == 5 && ub == 18) {
                            if (otherStrat == 'HIGH_RISK') {
                                highRiskFractions[1] = fractionToPrioritize;
                            } else if (otherStrat == 'PREGNANT') {
                                pregnantFractions[1] = fractionToPrioritize;
                            }
                        } else if (lb == 19 && ub == 29) {
                            if (otherStrat == 'HIGH_RISK') {
                                highRiskFractions[2] = fractionToPrioritize;
                            } else if (otherStrat == 'PREGNANT') {
                                pregnantFractions[2] = fractionToPrioritize;
                            }
                        } else if (lb == 30 && ub == 64) {
                            if (otherStrat == 'HIGH_RISK') {
                                highRiskFractions[3] = fractionToPrioritize;
                            } else if (otherStrat == 'PREGNANT') {
                                pregnantFractions[3] = fractionToPrioritize;
                            }
                        } else if (lb == 65) {
                            if (otherStrat == 'HIGH_RISK') {
                                highRiskFractions[4] = fractionToPrioritize;
                            } else if (otherStrat == 'PREGNANT') {
                                pregnantFractions[4] = fractionToPrioritize;
                            }
                        } else if (lb == 19 && ub == 64) {
                            if (otherStrat == 'PREGNANT') {
                                pregnantFractions[2] = fractionToPrioritize;
                                pregnantFractions[3] = fractionToPrioritize;
                            }
                        }
                    }
                } else {
                    continue;
                }
            }
        }
    }
}

function getHighRiskFraction(targetPopulationsAndPrioritizations) {

    setFluteFractions(targetPopulationsAndPrioritizations);

    var obj = new Object();
    obj.warnings = [];
    obj.print = true;
    var returnString = "";
    for (var i = 0; i < highRiskFractions.length; i++) {
        if (highRiskFractions[i] == -1) {
            var ageRangeString;
            if (i < 4) {
                ageRangeString = "(" + ageRanges[i][0] + "-" + ageRanges[i][1] + ")";
            } else {
                ageRangeString = "(" + ageRanges[i][0] + "+)";
            }
            var warningString = "no-apollo-param#No target prioirty population was specified for the age range " + ageRangeString + " and otherStratification=HIGH_RISK which is used in the " + flute_version + " 'highriskfraction' array. The default value for this age range is " + defaultHighRiskFractions[i] + ".";
            obj.warnings.push(warningString.toString());
            returnString = returnString + " " + defaultHighRiskFractions[i];
        } else {
            returnString = returnString + " " + highRiskFractions[i];
        }
    }

    returnString = returnString.substr(1, returnString.length);
    obj.value = returnString;
    return obj;
}

function getPregnantFraction(targetPopulationsAndPrioritizations) {

    setFluteFractions(targetPopulationsAndPrioritizations);

    var obj = new Object();
    obj.warnings = [];
    obj.print = true;
    var returnString = "";
    for (var i = 0; i < pregnantFractions.length; i++) {
        if (pregnantFractions[i] == -1) {
            var ageRangeString;
            if (i < 4) {
                ageRangeString = "(" + ageRanges[i][0] + "-" + ageRanges[i][1] + ")";
            } else {
                ageRangeString = "(" + ageRanges[i][0] + "+)";
            }
            var warningString = "no-apollo-param#No target prioirty population was specified for the age range " + ageRangeString + " and otherStratification=PREGNANT which is used in the " + flute_version + " 'pregnantfraction' array. The default value for this age range is " + defaultPregnantFractions[i] + ".";
            obj.warnings.push(warningString.toString());
            returnString = returnString + " " + defaultPregnantFractions[i];
        } else {
            returnString = returnString + " " + pregnantFractions[i];
        }
    }

    returnString = returnString.substr(1, returnString.length);
    obj.value = returnString;
    return obj;
}

function getEssentialFraction(targetPopulationsAndPrioritizations) {

    setFluteFractions(targetPopulationsAndPrioritizations);

    var obj = new Object();
    obj.warnings = [];
    obj.print = true;
    var returnString = "";

    if (essentialFraction == -1) {

        var warningString = "no-apollo-param#No target prioirty population was specified for the age range (17-50) and otherStratification=ESSENTIAL_WORKFORCE which maps to the " + flute_version + " 'essentialfraction' parameter. The default value is " + defaultEssentialFraction + ".";
        obj.warnings.push(warningString.toString());
        returnString = defaultEssentialFraction;
    } else {
        returnString = essentialFraction;
    }


    obj.value = returnString;
    return obj;
}

function vaccineBuildup(vaccEffByTimeSinceDose) {

    var obj = new Object();
    obj.warnings = [];
    var warningsString;
    var returnString = "0 0 " + "0 0 0.001 0.005 0.015 0.033 0.061 0.1 0.152 0.218 0.301 0.401 0.519 0.658 0.818 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1"; // the default value

    if (vaccEffByTimeSinceDose == null || vaccEffByTimeSinceDose.getVaccinationEfficacyConditionedOnTimeSinceMostRecentDose().size() == 0) {
        warningsString = "no-one-to-one-mapping# " + flute_version + " uses a 'vaccinebuildup' array, which represents daily increase in relative vaccination efficacy over 29 days from initial vaccination. The first two numbers are vaccine ID and the day of a single booster dose. This run is using the " + flute_version + " default vaccinebuildup (without a booster dose).";
        obj.warnings.push(warningsString.toString());
    } else {

        var vaccEffSinceDose = vaccEffByTimeSinceDose.getVaccinationEfficacyConditionedOnTimeSinceMostRecentDose();
        returnString = "0 0"; // vaccine ID followed by day of booster dose
        if (vaccEffSinceDose.size() <= 29) {
            var printedLengthWarning = false;
            var printedLastValueWarning = false;
            var length = vaccEffSinceDose.size();
            for (var i = 0; i < 29; i++) {
                if (i < length - 1) {
                    returnString = returnString + " " + vaccEffSinceDose.get(i);
                } else if (i == length - 1) {
                    var value = vaccEffSinceDose.get(i);
                    if (value < 1.0) {
                        if (!printedLastValueWarning) {
                            warningsString = "no-one-to-one-mapping# The value on the last day of the VaccinationEfficacyConditionedOnTimeSinceMostRecentDose array is less than one. This value should be one.";
                            obj.warnings.push(warningsString.toString());
                            printedLastValueWarning = true;
                        }
                    }
                    returnString = returnString + " " + vaccEffSinceDose.get(i);
                } else {
                    if (!printedLengthWarning) {
                        warningsString = "no-one-to-one-mapping# The vaccinationEfficaciesConditionedOnTimeSinceMostRecentDose array contained less than the 29 values required by " + flute_version + ", so the remaining values will be set to 1.";
                        obj.warnings.push(warningsString.toString());
                        printedLengthWarning = true;
                    }
                    returnString = returnString + " 1";
                }
            }
        } else {
            warningsString = "no-one-to-one-mapping# " + flute_version + " 'vaccinebuildup' array supports a maximum of 29 vaccine efficacy values. Since more than 29 values were specified in the control strategy, this run is using the " + flute_version + " default vaccinebuildup (without a booster dose).";
            obj.warnings.push(warningsString.toString());
        }
    }

    obj.value = returnString;
    obj.print = true;
    return obj;
}

function vaccineDoses(supplySchedule) {

    return "0 " + supplySchedule.get(0);
}

function vaccineData(treatment) {

    var treatmentContraindications = treatment.getTreatmentContraindications();
    var vaccinationEfficacies = treatment.getVaccinationEfficacies();

    if (vaccinationEfficacies == null || vaccinationEfficacies.size() == 0) {
        throw "No vaccination efficacies were specified in the scenario";
    }

    var obj = new Object();
    obj.warnings = [];
    var warningsString = "";
    var returnString = '0 VEs VEi VEp AR0-6mo AR0-4 AR5-18 AR19-29 AR30-64 AR65+ PA19-64';

    for (var i = 0; i < vaccinationEfficacies.size(); i++) {
        var preventableOutcome = vaccinationEfficacies.get(i).getForTreatmentPreventableOutcome();
        if (preventableOutcome == "INFECTION") {
            returnString = returnString.replace("VEs", vaccinationEfficacies.get(i).getAverageVaccinationEfficacy());
        } else if (preventableOutcome == "INFECTIOUSNESS") {
            returnString = returnString.replace("VEi", vaccinationEfficacies.get(i).getAverageVaccinationEfficacy());
        } else if (preventableOutcome == "SICKNESS_REQUIRING_MEDICAL_ATTENTION") {
            returnString = returnString.replace("VEp", vaccinationEfficacies.get(i).getAverageVaccinationEfficacy());
        }
    }

    if (returnString.indexOf("VEs") != -1) {
        throw "No vaccination efficacy for prevention of infected specified";
    }

    if (returnString.indexOf("VEi") != -1) {
        warningsString = "no-apollo-param# There was no vaccination efficacy for the prevention of the outcome INFECTIOUS specified (used in the " + flute_version + " configuration-file parameter 'vaccinedata'). The " + flute_version + " default value is 0.4.";
        obj.warnings.push(warningsString.toString());
        returnString = returnString.replace("VEi", "0.4");
    }
    if (returnString.indexOf("VEp") != -1) {
        warningsString = "no-apollo-param# There was no vaccination efficacy for the prevention of the outcome SICKNESS_REQUIRING_MEDICAL_ATTENTION specified (used in the " + flute_version + " configuration-file parameter 'vaccinedata'). The " + flute_version + " default value is 0.6.";
        obj.warnings.push(warningsString.toString());
        returnString = returnString.replace("VEp", "0.6");
    }

    var AR0_6moFrac = -1;
    var AR0_4frac = -1;
    var AR5_18frac = -1;
    var AR19_29frac = -1;
    var AR30_64frac = -1;
    var AR65frac = -1;
    var PA19_64frac = -1;
    if (treatmentContraindications != null && treatmentContraindications.size() > 0) {
        for (i = 0; i < treatmentContraindications.size(); i++) {
            var treatmentContraindication = treatmentContraindications.get(i);
            var ageRange = treatmentContraindication.getSimulatorReferencablePopulation().getAgeRange();
            var fraction = treatmentContraindication.getFractionOfSimulatorReferencablePopulation();

            var lb = ageRange.getLowerBound();
            var ub = ageRange.getUpperBound();
            var lbUnit = ageRange.getUnitOfTimeForLowerBound();
            var ubUnit = ageRange.getUnitOfTimeForUpperBound();

            if (lbUnit == 'YEAR' && ubUnit == 'YEAR') {
                if (lb == 0 && ub == 4) {
                    AR0_4frac = (AR0_4frac == -1) ? fraction : AR0_4frac + fraction;
                } else if (lb == 5 && ub == 18) {
                    AR5_18frac = (AR5_18frac == -1) ? fraction : AR5_18frac + fraction;
                } else if (lb == 19 && ub == 29) {
                    AR19_29frac = (AR19_29frac == -1) ? fraction : AR19_29frac + fraction;
                } else if (lb == 30 && ub == 64) {
                    AR30_64frac = (AR30_64frac == -1) ? fraction : AR30_64frac + fraction;
                } else if (lb == 65) {
                    AR65frac = (AR65frac == -1) ? fraction : AR65frac + fraction;
                } else if (lb == 19 && ub == 64) {

                    var otherStratification = treatmentContraindication.getSimulatorReferencablePopulation().getOtherStratification();
                    if (otherStratification == 'PREGNANT') {
                        if (fraction > 0 && fraction < 1) {
                            warningsString = "no-one-to-one-mapping# " + flute_version + " can only set 0 or 100% of pregnant adults to be restricted from getting the vaccine. This run uses 100%";
                            obj.warnings.push(warningsString.toString());
                            PA19_64frac = 0; // 0 means restricted
                        } else if (fraction == 1.0) {
                            PA19_64frac = 0; // 0 means restricted
                        } else if (fraction == 0) {
                            PA19_64frac = 1; // 1 means no restriction
                        }
                    }
                }
            } else if (lbUnit == 'MONTH' && ubUnit == 'MONTH') {
                if (lb == 0 && ub == 6) {
                    AR0_6moFrac = (AR0_6moFrac == -1) ? fraction : AR0_6moFrac + fraction;
                }
            }
        }
    }

    if (AR0_6moFrac == -1) {
        warningsString = "no-apollo-param# There was no treatment contraindication fraction specified for the age range 0-6 months (used in the " + flute_version + " configuration-file parameter 'vaccinedata'). The " + flute_version + " default value is 1.0.";
        obj.warnings.push(warningsString.toString());
        returnString = returnString.replace("AR0-6mo", "1.0");
    } else if (AR0_6moFrac > 1) {
        throw "The total contraindications fraction for the age range (0 - 6) months was greater than 1.0";
    } else {
        returnString = returnString.replace("AR0-6mo", AR0_6moFrac);
    }

    if (AR0_4frac == -1) {
        warningsString = "no-apollo-param# There was no treatment contraindication fraction specified for the age range 0-4 years (used in the " + flute_version + " configuration-file parameter 'vaccinedata'). The " + flute_version + " default value is 0.1.";
        obj.warnings.push(warningsString.toString());
        returnString = returnString.replace("AR0-4", "0.1");
    } else if (AR0_4frac > 1) {
        throw "The total contraindications fraction for the age range (0 - 4) years was greater than 1.0";
    } else {
        returnString = returnString.replace("AR0-4", AR0_4frac);
    }

    if (AR5_18frac == -1) {
        warningsString = "no-apollo-param# There was no treatment contraindication fraction specified for the age range 5-18 years (used in the " + flute_version + " configuration-file parameter 'vaccinedata'). The " + flute_version + " default value is 0.1.";
        obj.warnings.push(warningsString.toString());
        returnString = returnString.replace("AR5-18", "0.1");
    } else if (AR5_18frac > 1) {
        throw "The total contraindications fraction for the age range (5 - 18) years was greater than 1.0";
    } else {
        returnString = returnString.replace("AR5-18", AR5_18frac);
    }

    if (AR19_29frac == -1) {
        warningsString = "no-apollo-param# There was no treatment contraindication fraction specified for the age range 19-29 years (used in the " + flute_version + " configuration-file parameter 'vaccinedata'). The " + flute_version + " default value is 0.1.";
        obj.warnings.push(warningsString.toString());
        returnString = returnString.replace("AR19-29", "0.1");
    } else if (AR19_29frac > 1) {
        throw "The total contraindications fraction for the age range (19 - 29) years was greater than 1.0";
    } else {
        returnString = returnString.replace("AR19-29", AR30_64frac);
    }

    if (AR30_64frac == -1) {
        warningsString = "no-apollo-param# There was no treatment contraindication fraction specified for the age range 30-64 years (used in the " + flute_version + " configuration-file parameter 'vaccinedata'). The " + flute_version + " default value is 0.1.";
        obj.warnings.push(warningsString.toString());
        returnString = returnString.replace("AR30-64", "0.1");
    } else if (AR30_64frac > 1) {
        throw "The total contraindications fraction for the age range (30 - 64) years was greater than 1.0";
    } else {
        returnString = returnString.replace("AR30-64", AR30_64frac);
    }

    if (AR65frac == -1) {
        warningsString = "no-apollo-param# There was no treatment contraindication fraction specified for the age range 65+ years (used in the " + flute_version + " configuration-file parameter 'vaccinedata'). The " + flute_version + " default value is 0.1.";
        obj.warnings.push(warningsString.toString());
        returnString = returnString.replace("AR65+", "0.1");
    } else if (AR65frac > 1) {
        throw "The total contraindications fraction for the age range (65+) years was greater than 1.0";
    } else {
        returnString = returnString.replace("AR65+", AR65frac);
    }

    if (PA19_64frac == -1) {
        warningsString = "no-apollo-param# There was no treatment contraindication fraction specified for pregnant individuals in the age range 19-64 years (used in the " + flute_version + " configuration-file parameter 'vaccinedata'). The " + flute_version + " syntax for this parameter is : 'Pregnant adults restricted from getting the vaccine (0 means restricted, 1 means no restriction)'. The " + flute_version + " default value is 1.";
        obj.warnings.push(warningsString.toString());
        returnString = returnString.replace("PA19-64", "1");
    } else if (PA19_64frac > 1) {
        throw "The total contraindications fraction for the age range (19 - 64) years and otherStratification=PREGNANT was greater than 1.0";
    } else {
        returnString = returnString.replace("PA19-64", PA19_64frac);
    }

    obj.value = returnString;
    obj.print = true;

    return obj;
}

function vaccineProduction(supplySchedule) {

    var obj = new Object();
    obj.print = true;
    obj.warnings = [];
    var returnVal = '0';
    var defaultRunLength = 180;
    var scheduleLength = Math.min(supplySchedule.size() - 1, defaultRunLength);

    if (supplySchedule.size() - 1 > defaultRunLength) {
        var warningString = "no-native-param#" + flute_version + " only supports running for a maximum of 180 days. The vaccine supply schedule was " + supplySchedule.size()
                + " days, and was truncated to 180 days. ";
        obj.warnings.push(warningString.toString());
    }

    for (var i = 1; i <= scheduleLength; i++) {
        returnVal = returnVal + ' ' + supplySchedule.get(i);
    }

    if (scheduleLength < defaultRunLength) {
        for (i = scheduleLength; i < defaultRunLength; i++) {
            returnVal = returnVal + ' 0';
        }
        var warningString = "no-one-to-one-mapping#" + flute_version + " configuration-file parameter 'vaccineproduction' requires a schedule of exactly 180 days. The " + apollo_version + " vaccine supply schedule was not long enough so zeroes were appended.";
        obj.warnings.push(warningString.toString());
    }
    obj.value = returnVal;

    return obj;
}

function vaccineDosesDaily(adminSchedule) {

    var allValuesTheSame = true;
    var value = parseFloat(adminSchedule.get(0));
    var avgVaccineAdmin = 0.0;
    for (var i = 0; i < adminSchedule.size(); i++) {
        var newValue = parseFloat(adminSchedule.get(i))
        allValuesTheSame = allValuesTheSame && (value == newValue);
        avgVaccineAdmin = avgVaccineAdmin + newValue;
    }

    avgVaccineAdmin = avgVaccineAdmin / adminSchedule.size();

    var returnValue = String(Math.round(avgVaccineAdmin));

    var obj = new Object();
    obj.print = true;
    obj.value = returnValue;
    obj.warnings = [];
    if (allValuesTheSame) {
        var warningString = "no-one-to-one-mapping#" + flute_version + " configuration-file parameter 'vaccinedosesdaily' represents average daily capacity.  This mapping is the average of the " + apollo_version + " administrationCapacityPerDay array for a vaccination control strategy. In this instance the average is equal to the capacity on each day, so there is no information lost in the translation.";
        obj.warnings.push(warningString.toString());
    } else {
        var warningString = "no-native-param#" + flute_version + " configuration-file parameter 'vaccinedosesdaily' represents average daily capacity.  This mapping is the average of the " + apollo_version + " administrationCapacityPerDay array for a vaccination control strategy.";
        obj.warnings.push(warningString.toString());
    }
    return obj;
}

function vaccinePriorities(targetPopulationsAndPrioritizations) {

    var obj = new Object();
    obj.warnings = [];
    obj.print = true;

    var csTargetPopsAndPrioritizations = targetPopulationsAndPrioritizations.getControlMeasureTargetPopulationsAndPrioritization();
    if (csTargetPopsAndPrioritizations != null && csTargetPopsAndPrioritizations.size() > 0) {

        var returnString = "EW PW FMOI HRP HRSAC HRYA HROA HRE P SAC YA OA E";
        // essential workforce
        var EW = -1;
        var EWfraction = 0.0;
        // pregnant women
        var PW = -1;
        var PWprioritySet = false;
        var printPWWarning = false;
        // family member of infant
        var FMOI = -1;
        var FMOIfraction = 0.0;
        // preschoolers
        var HRP = -1;
        var P = -1;
        var Pfraction = 0.0;
        // school age children
        var HRSAC = -1;
        var SAC = -1;
        var SACfraction = 0.0;
        // young adults
        var HRYA = -1;
        var YA = -1;
        var YAfraction = 0.0;
        // older adults
        var HROA = -1;
        var OA = -1;
        var OAfraction = 0.0;
        // elderly
        var HRE = -1;
        var E = -1;
        var Efraction = 0.0;

        for (var i = 0; i < csTargetPopsAndPrioritizations.size(); i++) {
            var targetPriorityPopulation = csTargetPopsAndPrioritizations.get(i);
            var fractionToPrioritize = targetPriorityPopulation.getFractionOfTargetPopulationToPrioritize();
            var priority = targetPriorityPopulation.getPriority();
            var targetPopulationDefinition = targetPriorityPopulation.getTargetPopulationDefinition();
            var targetPopulationEnum = targetPriorityPopulation.getTargetPopulationEnum();
            
            // for reference, the required age ranges and fractions came from the
            // Analyses we will demo on SEUA or BioEcon.doc document in the Apollo Dropbox folder
            if (targetPopulationEnum != null) {
                if (targetPopulationEnum == 'HOUSEHOLD_MEMBER_OF_INFANT') {
                    FMOI = priority;
//                    FMOIfraction += fractionToPrioritize;
//                    if (FMOIfraction > 1) {
//                        throw "The household member of infant fraction set in the target populations and prioritizations was greater than 1";
//                    }
                }
            } else {
                var ageRange = targetPopulationDefinition.getAgeRange();
                if (ageRange != null) {
                    var otherStrat = targetPopulationDefinition.getOtherStratification();
                    var lb = ageRange.getLowerBound().getFiniteBoundary();
                    var ub = ageRange.getUpperBound().getFiniteBoundary();
                    var unitlb = ageRange.getUnitOfTimeForLowerBound();
                    var unitub = ageRange.getUnitOfTimeForUpperBound();

                    if (unitlb == 'YEAR' && unitub == 'YEAR') {
                        if (lb == 17 && ub == 75 && otherStrat != null && otherStrat == 'ESSENTIAL_WORKFORCE') {
                            EW = priority;

//                            EWfraction += fractionToPrioritize;
//                            if (EWfraction > 1) {
//                                throw "The essential workforce fraction set in the target populations and prioritizations was greater than 1";
//                            }
                        } else if (lb == 19 && ub == 64) {
                            if (otherStrat == 'PREGNANT') {
                                if (!PWprioritySet) {
                                    PWprioritySet = true;
                                    PW = priority;
                                } else if (PW != priority) {
                                    printPWWarning = true;
                                    if (priority < PW) {
                                        PW = priority;
                                    }
                                }
                            }
                        } else if (lb == 0 && ub == 4) {
                            if (otherStrat == 'HIGH_RISK') {
                                HRP = priority;
                            } else if (otherStrat == 'PREGNANT') {
                                if (!PWprioritySet) {
                                    PWprioritySet = true;
                                    PW = priority;
                                } else if (PW != priority) {
                                    printPWWarning = true;
                                    if (priority < PW) {
                                        PW = priority;
                                    }
                                }
                            } else {
                                P = priority;
                            }

//                            Pfraction += fractionToPrioritize;
//                            if (Pfraction > 1) {
//                                throw "The total fraction of preschoolers in the target populations and prioritizations was greater than 1";
//                            }
                        } else if (lb == 5 && ub == 18) {
                            if (otherStrat == 'HIGH_RISK') {
                                HRSAC = priority;
                            } else if (otherStrat == 'PREGNANT') {
                                if (!PWprioritySet) {
                                    PWprioritySet = true;
                                    PW = priority;
                                } else if (PW != priority) {
                                    printPWWarning = true;
                                    if (priority < PW) {
                                        PW = priority;
                                    }
                                }
                            } else {
                                SAC = priority;
                            }

//                            SACfraction += fractionToPrioritize;
//                            if (SACfraction > 1) {
//                                throw "The total fraction of school age children in the target populations and prioritizations was greater than 1";
//                            }
                        } else if (lb == 19 && ub == 29) {
                            if (otherStrat == 'HIGH_RISK') {
                                HRYA = priority;
                            } else if (otherStrat == 'PREGNANT') {
                                if (!PWprioritySet) {
                                    PWprioritySet = true;
                                    PW = priority;
                                } else if (PW != priority) {
                                    printPWWarning = true;
                                    if (priority < PW) {
                                        PW = priority;
                                    }
                                }
                            } else {
                                YA = priority;
                            }

//                            YAfraction += fractionToPrioritize;
//                            if (YAfraction > 1) {
//                                throw "The total fraction of young adults in the target populations and prioritizations was greater than 1";
//                            }
                        } else if (lb == 30 && ub == 64) {
                            if (otherStrat == 'HIGH_RISK') {
                                HROA = priority;
                            } else if (otherStrat == 'PREGNANT') {
                                if (!PWprioritySet) {
                                    PWprioritySet = true;
                                    PW = priority;
                                } else if (PW != priority) {
                                    printPWWarning = true;
                                    if (priority < PW) {
                                        PW = priority;
                                    }
                                }
                            } else {
                                OA = priority;
                            }

//                            OAfraction += fractionToPrioritize;
//                            if (OAfraction > 1) {
//                                throw "The total fraction of older adults in the target populations and prioritizations was greater than 1";
//                            }
                        } else if (lb == 65) {
                            if (otherStrat == 'HIGH_RISK') {
                                HRE = priority;
                            } else if (otherStrat == 'PREGNANT') {
                                if (!PWprioritySet) {
                                    PWprioritySet = true;
                                    PW = priority;
                                } else if (PW != priority) {
                                    printPWWarning = true;
                                    if (priority < PW) {
                                        PW = priority;
                                    }
                                }
                            } else {
                                E = priority;
                            }

//                            Efraction += fractionToPrioritize;
//                            if (Efraction > 1) {
//                                throw "The total fraction of elderly in the target populations and prioritizations was greater than 1";
//                            }
                        }
                    }
                } else {
                    continue;
                }
            }
        }

        if (printPWWarning) {
            var warningString = "no-one-to-one-mapping#" + flute_version + " only allows a single priority for pregant women, but multiple different priorities for different age groups of pregnant women were specified in the scenario. The highest priority will be used.";
            obj.warnings.push(warningString.toString());
        }

        if (EW == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for the age range 17 - 75 years and otherStratification=ESSENTIAL_WORKFORCE, corresponding to the " + flute_version + " definition of 'essential workforce'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("EW", "1");
        } else {
            returnString = returnString.replace("EW", EW);
        }

        if (PW == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for any of the 5 " + flute_version + " age ranges and with otherStratification=PREGNANT, corresponding to the " + flute_version + " definition of 'pregnant women'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("PW", "1");
        } else {
            returnString = returnString.replace("PW", PW);
        }

        if (HRP == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for the age range 0 - 4 years and otherStratification=HIGH_RISK, corresponding to the " + flute_version + " definition of 'high risk preschoolers'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("HRP", "1");
        } else {
            returnString = returnString.replace("HRP", HRP);
        }

        if (FMOI == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for the TargetPopulationDefinitionEnum value HOUSEHOLD_MEMBER_OF_INFANT, corresponding to the " + flute_version + " definition of 'family members of infants'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("FMOI", "1");
        } else {
            returnString = returnString.replace("FMOI", FMOI);
        }

        if (HRSAC == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for the age range 5 - 18 years and otherStratification=HIGH_RISK, corresponding to the " + flute_version + " definition of 'high risk school-age children'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("HRSAC", "1");
        } else {
            returnString = returnString.replace("HRSAC", HRSAC);
        }

        if (HRYA == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for the age range 19 - 29 years and otherStratification=HIGH_RISK, corresponding to the " + flute_version + " definition of 'high risk young adults'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("HRYA", "1");
        } else {
            returnString = returnString.replace("HRYA", HRYA);
        }

        if (HROA == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for the age range 30 - 64 years and otherStratification=HIGH_RISK, corresponding to the " + flute_version + " definition of 'high risk older adults'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("HROA", "1");
        } else {
            returnString = returnString.replace("HROA", HROA);
        }

        if (HRE == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for the age range 65+ years and otherStratification=HIGH_RISK, corresponding to the " + flute_version + " definition of 'high risk elderly'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("HRE", "1");
        } else {
            returnString = returnString.replace("HRE", HRE);
        }

        if (P == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for the age range 0 - 4 years, corresponding to the " + flute_version + " definition of 'preschoolers'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("P", "1");
        } else {
            returnString = returnString.replace("P", P);
        }

        if (SAC == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for the age range 5 - 18 years, corresponding to the " + flute_version + " definition of 'school-age children'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("SAC", "1");
        } else {
            returnString = returnString.replace("SAC", SAC);
        }

        if (YA == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for the age range 19 - 29 years, corresponding to the " + flute_version + " definition of 'young adults'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("YA", "1");
        } else {
            returnString = returnString.replace("YA", YA);
        }

        if (OA == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for the age range 30 - 64 years, corresponding to the " + flute_version + " definition of 'older adults'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("OA", "1");
        } else {
            returnString = returnString.replace("OA", OA);
        }

        if (E == -1) {
            var warningString = "no-apollo-param# There was no prioritization specified for the age range 65+ years, corresponding to the " + flute_version + " definition of 'elderly'. The " + flute_version + " default priorty is 1.";
            obj.warnings.push(warningString.toString());
            returnString = returnString.replace("E", "1");
        } else {
            returnString = returnString.replace("E", E);
        }

        obj.value = returnString;

    } else {
        var warningString = "no-one-to-one-mapping#No control strategy target populations and prioritizations were specified for the vaccination control strategy. " + flute_version + " parameter 'vaccinepriorities' is set to the default value.";
        obj.warnings.push(warningString.toString());
    }

    return obj;
}

function vaccinationFraction(stopTimes) {

    var obj = new Object();
    obj.print = true;
    obj.warnings = [];
    //    var foundTime = false;

    if (stopTimes.size() > 1) {
        var warningString = "no-one-to-one-mapping#Multiple stop time triggers were specified, but only one can be used to map to the " + flute_version + " parameter 'vaccinefraction'. The first trigger in the list having a unitOfMeasureForThreshold of PERCENT_OF_POPULATION_VACCINATED and reactiveControStrategyOperator GREATER_THAN_OR_EQUAL_TO will be used.";
        obj.warnings.push(warningString.toString());
    }
    for (var j = 0; j < stopTimes.size(); j++) {
        var stopTime = stopTimes.get(j);
        if (stopTime instanceof edu.pitt.apollo.types.v4_0_2.TreatmentSurveillanceTriggerDefinition) {

            //            foundTime = true;
            var foundTrigger = false;
            var unit = stopTime.getUnitOfMeasureForThreshold();
            if (unit != 'PERCENT_OF_POPULATION_VACCINATED') {
                continue;
            }

            foundTrigger = true;
            var threshold = stopTime.getReactiveControlMeasureThreshold();
            obj.value = (threshold / 100.0);
            break;
        }
    }

    if (!foundTrigger) {
        var warningString = "no-one-to-one-mapping#No trigger having a unitOfMeasureForThreshold of PERCENT_OF_POPULATION_VACCINATED and a reactiveControStrategyOperator of GREATER_THAN_OR_EQUAL_TO was found. " + flute_version + " parameter 'vaccinationfraction' is set to the default value.";
        obj.warnings.push(warningString.toString());
        obj.value = 0.7;
    }

    //    if (!foundTime) {
    //        var warningString = "no-one-to-one-mapping#No reactive stop time was specified for the vaccination control strategy. " + flute_version + " parameter 'vaccinationfraction' is set to the default value.";
    //        obj.warnings.push(warningString.toString());
    //        obj.value = 0.7;
    //    }

    return obj;
}

function antiviralDoses(supplySchedule) {
    return supplySchedule.get(0);
}

function antiviralEfficacy(efficacies, outcome) {

    var obj = new Object();
    obj.print = true;
    var foundEfficacy = false;
    for (var i = 0; i < efficacies.size(); i++) {
        var efficacy = efficacies.get(i);
        var preventableOutcome = efficacy.getForTreatmentPreventableOutcome();
        if (preventableOutcome == outcome) {
            obj.value = efficacy.getEfficacy();
            foundEfficacy = true;
            break;
        }
    }

    if (!foundEfficacy) {
        obj.warnings = [];
        var warningString = "no-apollo-param# There was no antiviral efficacy for the prevention of " + outcome + " specified. The " + flute_version + " default value of " + defaultAntiviralEfficacies[outcome] + " will be used";
        obj.warnings.push(warningString.toString());

        obj.value = defaultAntiviralEfficacies[outcome];
    }

    return obj;
}

function antiviralPolicy(namedPrioritizationScheme) {
    var obj = new Object();
    obj.print = true;

    if (namedPrioritizationScheme == null ||
            (namedPrioritizationScheme != 'TREAT_SICK_ONLY' &&
                    namedPrioritizationScheme != 'HHTAP' &&
                    namedPrioritizationScheme != 'HHTAP100' &&
                    namedPrioritizationScheme != 'NONE')) {

        obj.warnings = [];
        var warningString = "no-apollo-param# No valid named prioritization scheme was set for the antiviral control strategy. " + flute_version + " accepts the following named prioritization schemes for antiviral control strategies: 'none', 'treatmentonly', 'HHTAP', and 'HHTAP100'. Ths run is using the " + flute_version + " default value of 'none'";
        obj.warnings.push(warningString.toString());
        obj.value = 'none';
    } else {
        if (namedPrioritizationScheme == 'TREAT_SICK_ONLY') {
            obj.value = 'treatmentonly';
        } else if (namedPrioritizationScheme == 'NONE') {
            obj.value = 'none';
        } else {
            obj.value = namedPrioritizationScheme;
        }
    }

    return obj;
}

function antiviralDosesDaily(administrationCapacity) {

    var avg = 0.0;
    var allValuesTheSame = true;
    var value = parseFloat(administrationCapacity.get(0));
    for (var i = 0; i < administrationCapacity.size(); i++) {
        var newValue = parseFloat(administrationCapacity.get(i));
        allValuesTheSame = allValuesTheSame && (value == newValue)
        avg += newValue;
    }

    avg = avg / administrationCapacity.size();

    var returnValue = String(Math.round(avg));

    var obj = new Object();
    obj.print = true;
    obj.value = returnValue;
    obj.warnings = [];
    if (allValuesTheSame) {
        var warningString = "no-one-to-one-mapping#" + flute_version + " configuration-file parameter 'antiviraldosesdaily' represents average daily capacity.  This mapping is the average of the " + apollo_version + " administrationCapacityPerDay array for an antiviral treatment control strategy. In this instance the average is equal to the capacity on each day, so there is no information lost in the translation.";
        obj.warnings.push(warningString.toString());
    } else {
        var warningString = "no-native-param#" + flute_version + " configuration-file parameter 'antiviraldosesdaily' represents average daily capacity.  This mapping is the average of the " + apollo_version + " administrationCapacityPerDay array for an antiviral treatment control strategy.";
        obj.warnings.push(warningString.toString());
    }
    return obj;
}

//function ascertainmentFraction(controlMeasureStartTime) {
//    
//    if (controlMeasureStartTime.toString().contains("ReactiveStartTime")) {
//        
//        return controlMeasureStartTime.getTrigger().getAscertainmentFraction();
//        
//    } else {
//        return objectForSkippingTerm();
//    }
//}
//
//function ascertainmentDelay(controlMeasureStartTime) {
//    
//    if (controlMeasureStartTime.toString().contains("ReactiveStartTime")) {
//        
//        return controlMeasureStartTime.getTrigger().getAscertainmentDelay();
//        
//    } else {
//        return objectForSkippingTerm();
//    }
//}
//
//function responseThreshold(controlMeasureStartTime) {
//    
//    if (controlMeasureStartTime.toString().contains("ReactiveStartTime")) {
//        return controlMeasureStartTime.getTrigger().getReactiveControlMeasureThreshold();
//    } else {
//        return objectForSkippingTerm();
//    }
//}

function additionalParams() {

    var returnString = "seed 1\r\n";
    returnString += "label apollo_demo\r\n";
    v
    returnString += "logfile 1";

    return returnString;
}

function schoolClosurePolicy(schoolClosurePolicy) {

    if (schoolClosurePolicy == "ALL_SCHOOLS") {
        return "all";
    } else if (schoolClosurePolicy == "ALL_SCHOOLS_OF_ONE_LEVEL_IN_ONE_TRACT") {
        return "bytractandage";
    } else {
        var obj = new Objcet();
        obj.warnings = [];
        var warningString = "no-native-param#" + flute_version + " parameter 'schoolclosurepolicy' can take values 'none', 'all' (maps to " + apollo_version + " placeClass ALL_SCHOOLS), and 'bytractandage' (maps to " + apollo_version + " placeClass ALL_SCHOOLS_OF_ONE_LEVEL_IN_ONE_TRACT). This run will use the value 'none'.";
        obj.warnings.push(warningString.toString());
        obj.value = 'none';
        obj.print = true;
        return obj;
    }
}

function getR0(reproductionNumber) {

    if (reproductionNumber.getExactValue() !== null) {
        return reproductionNumber.getExactValue();
    } else {
        var uncertainValue = reproductionNumber.getUncertainValue();
        return uncertainValue.getMean();
    }
}


// THE FOLLOWING IS CODE FROM PSC








// constants for approximate gamma function
var gamma_g = 7;
var gamma_C = [
    0.99999999999980993, 676.5203681218851, -1259.1392167224028,
    771.32342877765313, -176.61502916214059, 12.507343278686905,
    -0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7
];
// approximate gamma function, converted to javascript from python
// code found here: http://sv.wikipedia.org/wiki/Gammafunktionen
function gamma(z) {
    if (z < 0.5) {
        return Math.PI / (Math.sin(Math.PI * z) * gamma(1 - z));
    }
    else {
        z -= 1;
        var x = gamma_C[0];
        for (var i = 1; i < gamma_g + 2; i++) {
            x += gamma_C[i] / (z + i);
        }
        var t = z + gamma_g + 0.5;
        return Math.sqrt(2 * Math.PI) * Math.pow(t, (z + 0.5)) * Math.exp(-t) * x;
    }
}

function weibull_lambda(mean, kappa) {
    var lambda = mean / gamma(1.0 + (1.0 / kappa));
    return lambda;
}

function trim_right(l, precision) {
    for (var i = l.length - 1; i > -1; i -= 1) {
        if (l[i] > 1.0 + precision) {
            var msg = 'probability exceeded 1.0!';
            // console.log(msg);
        }
        if (1.0 - l[i] > precision) {
            var trimmed = [];
            for (var j = 0; j < i + 1; j++) {
                trimmed[j] = l[j];
            }
            trimmed[i + 1] = 1.0;
            return trimmed;
        }
    }
}

function redistribute_probability_in_zeroth_bin(l) {
    var redistributed = l.slice(0)
    if (l[0] > 0.0) {
        if (l.length == 1) {
            msg = 'Supplied cdf should have at least three bins, ';
            msg += 'but length is ' + l.length;
            // console.log(msg);
            redistributed.push(1.0);
        }
    }
    redistributed[1] += 3.0 * l[0];
    redistributed[0] = 0.0;
    if (redistributed[1] > 1.0) {
        var msg = 'redistribute of probability in the zeroth bin resulted in overflow!'
        //console.log(msg);
        redistributed[1] = 1.0;
    }
    else if (redistributed[1] == 1.0) {
        redistributed = redistributed.slice(0, 2);
    }
    return redistributed;
}

function shifted_weibull_binned_cdf(shift, weibull_params,
        max_bins, precision, allow_probability_in_zeroth_bin) {

    if (shift == null || weibull_params == null) {
        var msg = 'Need to supply shift, lambda and kappa!'
        //console.log(msg);
        throw new Error(msg);
    }
    var k = weibull_params['kappa']
    var l = weibull_params['lambda']
    var cdf = [];
    for (var i = 0; i < max_bins; i++) {
        cdf[i] = 0.0;
    }
    for (var i = 0; i < Math.round(max_bins / precision); i++) {
        var x = precision * i;
        var p = 0.0;
        if (x >= shift) {
            p = 1.0 - Math.exp(-Math.pow((x - shift) / l, k));
        }
        cdf[Math.floor(x)] += p;
    }
    for (var i = 0; i < max_bins; i++) {
        cdf[i] *= precision;
    }
    cdf = trim_right(cdf, precision);
    if (allow_probability_in_zeroth_bin == false) {
        cdf = redistribute_probability_in_zeroth_bin(cdf);
    }
    return cdf;
}

function incubationPeriod(targetMean) {
    // fitted values that should produce the default value for targetMean
    // argument specified above, i.e.:
    var fitted_mean = 1.979024;
    if (targetMean == null) {
        targetMean = fitted_mean;
    }
    var shift = 0.5;
    if (targetMean < fitted_mean) {
        if (targetMean <= 1.0) {
            return '2 0.0 1.0';
        }
        else {
            shift += (1.0 - shift) * (1.0 - (targetMean / fitted_mean));
        }
    }
    var max_bins = Math.round(Math.pow(targetMean, 2));
    var kappa = 2.2126752;
    // lambda is set to give the targetMean holding shift and kappa constant
    // for the default targetMean, this should be: lambda=1.67
    var cdf = shifted_weibull_binned_cdf(shift, weibull_params = {
        'lambda': weibull_lambda(targetMean - shift, kappa),
        'kappa': kappa}, max_bins = 10, precision = 0.0001,
            allow_probability_in_zeroth_bin = false);
    var cdf_string = cdf.length + ' ' + cdf.join(' ');
    return cdf_string;
}

function infectiousPeriod(targetMean) {
    // fitted values that should produce the default value for targetMean
    // argument specified above, i.e.:
    var fitted_mean = 4.131811;
    if (targetMean == null) {
        targetMean = fitted_mean;
    }
    var shift = 2.0;
    if (targetMean < fitted_mean) {
        shift -= (shift * (1.0 - (targetMean / fitted_mean)));
    }
    var max_bins = Math.round(Math.pow(targetMean, 2));
    var kappa = 2.378084;
    // lambda is set to give the targetMean holding shift and kappa constant
    // for the default targetMean, this should be: lambda=2.405191
    var cdf = shifted_weibull_binned_cdf(shift, weibull_params = {
        'lambda': weibull_lambda(targetMean - shift, kappa),
        'kappa': kappa}, max_bins = max_bins, precision = 0.0001,
            allow_probability_in_zeroth_bin = true);
    var cdf_string = cdf.length + ' ' + cdf.join(' ');
    return cdf_string;
}




function buildTrajectories(daysLatent, daysIncubating, daysInfectious,
        infectivity, symptomaticity, options) {

    var symTrj = [];
    var infTrj = [];
    for (var d = 0; d < daysLatent; d++) {
        infTrj.push(0);
    }
    if (options.dependentIncubation != "Y") {
        for (var d = 0; d < daysIncubating; d++) {
            symTrj.push(0);
        }
    }
    else {
        for (var d = 0; d < (daysLatent + daysIncubating); d++) {
            symTrj.push(0);
        }
    }
    for (var d = 0; d < daysInfectious; d++) {
        infTrj.push(infectivity);
    }
    if (options.dependentIncubation != "Y") {
        for (var d = 0; d < (daysInfectious + daysLatent - daysIncubating); d++) {
            symTrj.push(symptomaticity);
        }
    }
    else {
        for (var d = 0; d < (daysInfectious - daysLatent - daysIncubating); d++) {
            symTrj.push(symptomaticity);
        }
    }
    return {'infTrj': infTrj, 'symTrj': symTrj};
}


function build_trajectory_profile(latentPROB, incubatingPROB, infectiousPROB,
        infectivityVAL, symptomaticityVAL, infectionTypePROB, options) {
// based on infection-trajectory-builder.py
// given natural history parameter distributions, builds a set of infectivity
// and symptomaticity trajectories that can be added to FRED params file for
// use with the intra_host_model[strain#] = 1
//
// J. DePasse 2011-08-23
//
// modified for use with PSC Flute, Eli Zenkov 2011-08-11

// Original Python options reproduced here for reference:
//
// parser.add_option("-l", "--latent", dest="latent", default="0 0.8 0.2",
//     help="Latent period probabilities (zero-indexed")
// 
// parser.add_option("-n", "--incubating", dest="incubating", default="0 0.3 0.5 0.2",
//     help="Incubation period probabilities (zero-indexed")
// 
// parser.add_option("-i", "--infectious", dest="infectious", default="0 0 0 0.3 0.4 0.2 0.1",
//     help="Infectious period probabilities (zero-indexed")
// 
// parser.add_option("-v", "--infectivity", dest="infectivity", default="0.5 1",
//     help="Infectivity values")
// 
// parser.add_option("-y", "--symptomaticity", dest="symptomaticity", default="0.5 1",
//     help="Symptomaticity values")
// 
// parser.add_option("-t", "--infectionType", dest="infectionType", default="0.3333 0.6666",
//     help="Probabilities for infection type (i.e., symptomatic/asymptomatic")
// 
// parser.add_option("-d", "--dependentIncubation", dest="dependentIncubation", default="Y",
//     help="Should incubation period always be greater than or equal to latent period? (Y|N)")


    trajectories = [];

    for (var daysLatent = 0; daysLatent < latentPROB.length; daysLatent++) {
        var latentProb = latentPROB[daysLatent];

        for (var daysIncubating = 0; daysIncubating < incubatingPROB.length;
                daysIncubating++) {

            var incubatingProb = incubatingPROB[daysIncubating];

            for (var daysInfectious = 0; daysInfectious < infectiousPROB.length;
                    daysInfectious++) {

                var infectiousProb = infectiousPROB[daysInfectious];

                for (var infectionType = 0; infectionType < infectionTypePROB.length;
                        infectionType++) {

                    var infectionTypeProb = infectionTypePROB[infectionType];
                    var infectivity = infectivityVAL[infectionType];
                    var symptomaticity = symptomaticityVAL[infectionType];

                    var trjProb = latentProb * incubatingProb * infectiousProb;
                    trjProb *= infectionTypeProb;

                    if (trjProb > 0) {
                        trajectories.push([
                            trjProb,
                            buildTrajectories(daysLatent, daysIncubating,
                                    daysInfectious, infectivity, symptomaticity, options)]);
                    }
                }
            }
        }
    }

    trajectories.sort(sortFunction);


    var trajectoriesPROB = [];
    var trjProbSum = 0;

    for (var t = 0; t < trajectories.length; t++) {
        trjProbSum += trajectories[t][0];
        trajectoriesPROB.push(trjProbSum);
    }

    trajectoriesPROB[trajectoriesPROB.length - 1] = 1.0;

    return {'trajectories': trajectories, 'trajectoriesPROB': trajectoriesPROB};

}

function sortFunction(a, b) {
    return a[0] - b[0];
}

function parseCdfToPdf(cdf_string) {
    var out_prob = [];
    cdf = cdf_string.split(" ").slice(1);
    for (var prob_item = 0; prob_item < cdf.length; prob_item++) {
        if (prob_item == 0) {
            out_prob.push(parseFloat(cdf[prob_item]));
        }
        else {
            out_prob.push(parseFloat(cdf[prob_item]) - parseFloat(cdf[prob_item - 1]));
        }
    }
    return out_prob;
}

function callFluteTrajectories(latentMean, infectiousMean) {
    return fluteTrajectories(incubationPeriod(latentMean), infectiousPeriod(infectiousMean));
}

//incubating_cdf deafault from SEUA(latent==2): 6 0 0.30929497095034175 0.7491442676127461 0.9640089638824945 0.998050129265157 1
//infectious_cdf default from SEUA(infectious==6): 15 0 0 0.008153855622262108 0.07381920072913184 0.220047221101578 0.4211390211778252 0.627841045157024 0.7956630524662738 0.9053593334281886 0.9634273293840823 0.9883249445233538 0.9969492758839874 0.9993530330124397 0.9998895372569839 1
function fluteTrajectories(incubating_cdf, infectious_cdf) {
    //FRED defaults from infection-trajectory-builder.sh
    var latentPROB = [0, 0.8, 0.2];
    var incubatingPROB = [0, 0.8, 0.2]; //default was: var incubatingPROB = [0.8, 0.2]; //but it must have leading zeros if dependentIncubation==N
    var infectiousPROB = [0, 0, 0, 0.3, 0.4, 0.2, 0.1];
    var infectivityVAL = [0.5, 1];
    var symptomaticityVAL = [0.5, 1];
    var infectionTypePROB = [0.3333, 0.6666];
    var options = {dependentIncubation: 'N'};    //default was: Y

    latentPROB = parseCdfToPdf(incubating_cdf);
    incubatingPROB = latentPROB;
    infectiousPROB = parseCdfToPdf(infectious_cdf);

    built = build_trajectory_profile(latentPROB, incubatingPROB, infectiousPROB, infectivityVAL, symptomaticityVAL, infectionTypePROB, options);

    s = '\npscmode 1\n';
    s += 'profileweights ' + built.trajectoriesPROB.length + '    ';
    s += built.trajectoriesPROB.join(' ');
    s += '\n';

    for (var t = 0; t < built.trajectories.length; t++) {
        traj = built.trajectories[t];
        s += 'infectivity ' + t + '  ' + traj[1].infTrj.length + '    ';
        s += traj[1].infTrj.join(' ');
        s += '\n';
    }

    s += '\n';

    for (var t = 0; t < built.trajectories.length; t++) {
        traj = built.trajectories[t];
        s += 'symptomaticity ' + t + '  ' + traj[1].infTrj.length + '    ';
        s += traj[1].symTrj.join(' ');
        s += '\n';
    }

    s += '\n';

    return s;
}