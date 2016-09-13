var apollo_version = "Apollo 3.0.2";
var ebola_version = "Lancet Ebola 1.0";

function identity(value) {
    return value;
}

function oneMinusValue(value) {
    return (1 - value).toFixed(5);
}

function getPopulationCount(populationFileLocation, censusData, targetState, obj) {
    var fracInState = 0;
    var fracInStateSet = false;
    var ageRangeGenderWarningSet = false;

    var censusDataCells = censusData.censusDataCells;
    for (var i = 0; i < censusDataCells.size(); i++) {
        var cell = censusDataCells.get(i);
        if (!ageRangeGenderWarningSet) {
            if (cell.getAgeRange() != null &&
                    cell.getGender() != null) {
                var warningsString = "no-one-to-one-mapping# " + ebola_version + " does not allow setting the initial SEIR counts by age range or gender. Instead, the fractions of each infection state will be summed over all census data cells";
                obj.warnings.push(warningsString.toString());
                ageRangeGenderWarningSet = true;
            }
        }

        if (cell.getInfectionState() == targetState) {
            fracInStateSet = true;
            fracInState += cell.getFractionInState();
        }
    }

    if (!fracInStateSet) {
        throw "No initial fraction " + targetState + " was specified in any PopulationImmunityAndInfectionCensusDataCell";
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

    var arr = fileContents.split('\n');
    var totalPop = 0;
    for (i = 0; i < arr.length; i++) {
        var line = arr[i].split(',');
        var state = line[0];
        var county = line[1];
        if (incits.length() == 2) {
            if (incits == 'US') {
                if (county == '0') {
                    totalPop += parseInt(line[4]);
                }
            } else if (incits == 'SL') {
                if (state.indexOf('SL') > -1) {
                    totalPop += parseInt(line[4]);
                }
            } else if (incits == state && county == '0') {
                totalPop = parseInt(line[4]);
                break;
            }
        } else if (incits.indexOf('SL') > -1) {
            if (incits == state) {
                totalPop = parseInt(line[4]);
                break;
            }
        } else if (incits.length() == 5) {
            var fullCounty = state + county;
            if (incits == fullCounty) {
                totalPop = parseInt(line[4]);
                break;
            }
        }
    }

    var initCount = fracInState * totalPop;
    return initCount;
}

function getInitialSEIRCounts(populationFileLocation, censusData, targetState) {

    var obj = new Object();
    obj.warnings = [];

    var initCount = getPopulationCount(populationFileLocation, censusData, targetState, obj);

    initCount = initCount.toFixed(5);

    obj.value = initCount;
    return obj;
}

function getInitialInfectiousCount(populationFileLocation, censusData, dying, diseaseOutcomes) {

    var obj = new Object();
    obj.warnings = [];

    var cfr;
    var cfrSet = false;
    for (var i = 0; i < diseaseOutcomes.size(); i++) {

        var diseaseOutcomeWithProbability = diseaseOutcomes.get(i);
        var diseaseOutcome = diseaseOutcomeWithProbability.getDiseaseOutcome();
        if (diseaseOutcome.toString() == 'DEATH') {
            cfr = diseaseOutcomeWithProbability.probability.probability;
            cfrSet = true;
        }
    }

    if (!cfrSet) {
        throw "No disease outcome of DEATH was found in the disease outcomes with probabilities list when translating the initial infectious count.";
    }

    var initCount = getPopulationCount(populationFileLocation, censusData, "INFECTIOUS", obj);

    if (dying == "true") {
        initCount = initCount * cfr;
    } else {
        initCount = initCount * (1 - cfr);
    }

    initCount = initCount.toFixed(5);

    obj.value = initCount;
    return obj;
}

function getR0(reproductionNumber) {

    if (reproductionNumber.getExactValue() !== null) {
        return reproductionNumber.getExactValue();
    } else {
        var uncertainValue = reproductionNumber.getUncertainValue();
        return uncertainValue.getMean();
    }
}