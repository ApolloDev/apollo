var apollo_version = "Apollo 4.0";
var clara_version = "CLARA 0.5";
function identity(value) {
    return value;
}

function oneMinus(value) {
    return 1 - value;
}

function oneMinusFractionToPercent(value) {
    return (1 - value) * 100;
}

function isInt(n) {
    return typeof n === 'number' && n % 1 == 0;
}

function fractionToPercent(value) {
    return value * 100;
}

function addCarriageReturn(value) {
    return "\r";
}

function getCoverRadius(controlStrategyType, controlStrategies) {
    var smallestCoverRadius = null;
    var smallestCoverRadiusCm = "";
    var smallestCoverRadiusCmType = "";
    var coverRadiusForThisCm;

    var currentCmType;
    for (var i = 0; i < controlStrategies.size(); i++) {
        var cm = controlStrategies.get(i);

        if (cm instanceof edu.pitt.apollo.types.v4_0_1.ContainerReductionControlMeasure) {
            currentCmType = "indoorResidualSpraying";
        } else if (cm instanceof edu.pitt.apollo.types.v4_0_1.LarvicideControlMeasure) {
            currentCmType = "larvicide";
        } else if (cm instanceof edu.pitt.apollo.types.v4_0_1.IndoorResidualSprayingVectorControlMeasure) {
            currentCmType = "containerReduction";
        } else {
            continue;
        }

        var coverRadius = cm.getCoverRadius().getValue();

        if (controlStrategyType.equalsIgnoreCase(currentCmType)) {
            coverRadiusForThisCm = coverRadius;
        }

        if (smallestCoverRadius == null || (coverRadius < smallestCoverRadius)) {
            smallestCoverRadius = coverRadius;

            if (cm instanceof edu.pitt.apollo.types.v4_0_1.IndoorResidualSprayingVectorControlMeasure) {
                smallestCoverRadiusCm = "infectiousDiseaseControlStrategy[" + i + "]";
                smallestCoverRadiusCmType = "indoorResidualSpraying";
            } else if (cm instanceof edu.pitt.apollo.types.v4_0_1.LarvicideControlMeasure) {
                smallestCoverRadiusCm = "infectiousDiseaseControlStrategy[" + i + "]";
                smallestCoverRadiusCmType = "larvicide";
            } else if (cm instanceof edu.pitt.apollo.types.v4_0_1.ContainerReductionControlMeasure) {
                smallestCoverRadiusCm = "infectiousDiseaseControlStrategy[" + i + "]";
                smallestCoverRadiusCmType = "containerReduction";
            }
        }

    }

    if (controlStrategyType.equalsIgnoreCase(smallestCoverRadiusCmType)) {
        return Math.round(smallestCoverRadius);
    } else {

        var warningType = "";
        if (coverRadiusForThisCm == smallestCoverRadius) {
            warningType = "no-one-to-one-mapping";
        } else {
            warningType = "no-native-param";
        }

        var obj = new Object();
        obj.print = true;
        obj.warnings = [];

        var warningString = warningType + "#" + clara_version + " only allows a single 'cover-radius' for all control strategies, "
                + "but multiple control strategies were specified in the " + apollo_version + " InfectiousDiseaseScenario. The smallest cover "
                + "radius will be used. If two or more control strategies of different types are tied for the "
                + "smallest cover radius, the value will be determined using the following priority: IndoorResidualSpraying, Larvicide, ContainerReduction. "
                + "If two or more control strategies of the same type have the smallest cover radius, "
                + "whichever has the larger index in the InfectiousDiseaseScenario control strategies list will be used. For this run the cover radius "
                + "is set for " + smallestCoverRadiusCm + ".";
        obj.warnings.push(warningString.toString());
        return obj;
    }
}

function getControlStrategyTime(triggers) {
    var obj = new Object();
    obj.print = true;
    var foundTime = false;
    for (var i = 0; i < triggers.size(); i++) {
        var trigger = triggers.get(i);
        if (trigger instanceof edu.pitt.apollo.types.v4_0_1.TemporalTriggerDefinition) {
            var startTime = trigger.getTimeSinceTimeScaleZero();
            if (startTime instanceof edu.pitt.apollo.types.v4_0_1.FixedDuration) {
                var unitOfTime = startTime.getUnitOfTime();
                if (unitOfTime == 'DAY') {
                    var val = startTime.getValue();
                    if (isInt(val)) {
                        obj.value = val.toString();
                    } else {
                        obj.value = Math.round(val);
                        var warningString = "no-native-param#  The control strategy time value is specified as a double, but " + clara_version + " requires an integer. The rounded value will be used.";
                        obj.warnings.push(warningString.toString());
                    }
                    foundTime = true;
                    break;
                }
            }
        }
    }

    if (!foundTime) {
        obj.print = false;
    }

    return obj;
}

function getEnumValue(enumVal) {
    return "\"" + enumVal.value() + "\"";
}

function getInfectionSeedMatrix(censuses) {

    var obj = new Object();
    var foundInfectious = false;
    for (var i = 0; i < censuses.size(); i++) {
        var census = censuses.get(i);
        // var species = census.getPopulationSpecies();
        //if (species == "9606") {

        var val = "\"[2";
        var censusData = census.getCensusData();
        var location = censusData.getLocation();
        var circleDefinition = location.getCartesianCircleLocationDefinition();

        var x = circleDefinition.getEastWestOffsetFromCartesianCenter().getValue();
        val += " " + x;

        var y = circleDefinition.getNorthSouthOffsetFromCartesianCenter().getValue();
        val += " " + y;

        var radius = circleDefinition.getRadius().getValue();
        val += " " + radius;

        val += " 0";

        var censusDataCells = censusData.getCensusDataCells();
        for (var i = 0; i < censusDataCells.size(); i++) {
            var cell = censusDataCells.get(i);
            if (cell.getInfectionState() !== null) {
                if (cell.getInfectionState().toString().equals("LATENT")) {
                    val += " " + cell.getFractionInState() * 100;
                    foundInfectious = true;
                    break;
                }
            }
        }

        val += " 1]\"";

        obj.value = val;
        obj.print = true;
        return obj;
        //}
    }

    if (!foundInfectious) {
        throw "No PopulationInfectionAndImmunityCensus having a populationSpecies of 9606 and data cell of LATENT was found";
    }

//    obj.warnings = [];
//    var warningString = "no-native-param# No PopulationInfectionAndImmunityCensus having a populationSpecies of 9606 was found";
//    obj.warnings.push(warningString.toString());
//    return obj;

}

function getInfectionDayStart(censuses) {

    var obj = new Object();
    for (var i = 0; i < censuses.size(); i++) {
        var census = censuses.get(i);
        // var species = census.getPopulationSpecies();
        // if (species == "9606") {
        var val = census.getSimulatorTime();
        obj.value = val;
        obj.print = true;
        return obj;
        // }
    }

    obj.warnings = [];
    var warningString = "no-native-param# No PopulationInfectionAndImmunityCensus having a populationSpecies of 9606 was found";
    obj.warnings.push(warningString.toString());
    return obj;
}

function getInitialMosquitoInfectionRate(censuses) {

    var obj = new Object();
    for (var i = 0; i < censuses.size(); i++) {
        var census = censuses.get(i);
        // var species = census.getPopulationSpecies();
        //  if (species == "7159") {

        var censusData = census.getCensusData();

        var censusDataCells = censusData.getCensusDataCells();
        for (var i = 0; i < censusDataCells.size(); i++) {
            var cell = censusDataCells.get(i);
            if (cell.getInfectionState() !== null) {
                if (cell.getInfectionState().toString().equals("INFECTIOUS")) {
                    var val = cell.getFractionInState();
                    obj.value = (val * 100);
                    obj.print = true;
                    return obj;
                }
            }
        }
        //   }
    }

    throw "No PopulationInfectionAndImmunityCensus having a populationSpecies of 7159 and a data cell of INFECTIOUS was found";

//    obj.warnings = [];
//    var warningString = "no-native-param# No PopulationInfectionAndImmunityCensus having a populationSpecies of 7159 was found";
//    obj.warnings.push(warningString.toString());
//    return obj;

}

function getInitialPersonImmunityRate(censuses) {
    return 0.0;
    var obj = new Object();
    for (var i = 0; i < censuses.size(); i++) {
        var census = censuses.get(i);
        // var species = census.getPopulationSpecies();
        //  if (species == "9606") {

        var censusData = census.getCensusData();

        var censusDataCells = censusData.getCensusDataCells();
        for (var i = 0; i < censusDataCells.size(); i++) {
            var cell = censusDataCells.get(i);
            if (cell.getInfectionState() !== null) {
                if (cell.getInfectionState().toString().equals("RECOVERED")) {
                    var val = cell.getFractionInState();
                    obj.value = (val * 100);
                    obj.print = true;
                    return obj;
                }
            }
        }
        // }
    }

    throw "No PopulationInfectionAndImmunityCensus having a populationSpecies of 9606 and a data cell of RECOVERED was found";
//    obj.warnings = [];
//    var warningString = "no-native-param# No PopulationInfectionAndImmunityCensus having a populationSpecies of 9606 was found";
//    obj.warnings.push(warningString.toString());
//    return obj;
}

function getInputFileRoot(scenarioLocation) {

    var obj = new Object();

//    var census = censuses.get(0);
//    var censusLocation = census.getLocation();
    var locationDefinition = scenarioLocation.getLocationDefinition();
    var code = locationDefinition.getLocationsIncluded().get(0);
    if (code == "AU35005") {
//        var val = "\"inputs/500-tight\"";
        var val = "\"inputs/Cairns\"";
        obj.value = val;
        obj.print = true;
        return obj;
    }

    throw clara_version + " only supports the location Cairns, AU (ApolloLocationCode \"AU35005\")";
//    obj.warnings = [];
//    var warningString = "no-native-param# " + clara_version + " only supports the location Cairns, AU (ApolloLocationCode \"AU35005\")";
//    obj.warnings.push(warningString.toString());
//    return obj;

}

function getValueInQuotes(value) {

    return "\"" + value + "\"";
}