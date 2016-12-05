var apollo_version = "Apollo 4.0";
var fred_version = "FRED 2.0.2";

function isInt(n) {
    return typeof n === 'number' && n % 1 == 0;
}

function getException(exception) {
    var exceptionVar = new Object();
    exceptionVar.exception = exception;
    return exceptionVar;
}

function identity(value) {
    return value;
}

function conditionalIdentity(controlStrategy, value) {
    return value;
}

function oneMinusValue(value) {
    return (1 - value).toFixed(2);
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

function getINCITS(location) {
    /* defaulting to the first included location for now */
    var locationCode = location.getApolloLocationCode();
    if (locationCode != null) {
        return locationCode;
    }

    var locationDefinition = location.getLocationDefinition();
    if (locationDefinition != null) {
        if (locationDefinition.getLocationsIncluded().size() == 1) {
            return locationDefinition.getLocationsIncluded().get(0);
        }
    }

    return -1;
}

function dateFromString(s) {
    var bits = s.split(/[-T:]/g);
    var d = new Date(bits[0], bits[1] - 1, bits[2]);
    d.setHours(0, 0, 0);

    return d;
}

function getStartDate(date) {

    date = date.toString();
    date = date.substring(0, 10);
    return date;

}

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
//            console.log(msg);
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
//            console.log(msg);
            redistributed.push(1.0);
        }
    }
    redistributed[1] += 3.0 * l[0];
    redistributed[0] = 0.0;
    if (redistributed[1] > 1.0) {
        var msg = 'redistribute of probability in the zeroth bin resulted in overflow!'
//        console.log(msg);
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
//        console.log(msg);
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

// THIS IS OLD AND SHOULD NOT BE USED
//function infectiousPeriodWithParams(newAverage)
//// , gamma, lamb, shift, thresh)
//{
//
//    // if(typeof(newAverage)==='undefined') newAverage = 4.1;
//    /* if(typeof(gamma)==='undefined') */gamma = 2.378084;
//    /* if(typeof(lamb)==='undefined') */lamb = 2.405191;
//    /* if(typeof(shift)==='undefined') */shift = 2.0;
//    /* if(typeof(thresh)==='undefined') */thresh = 0.10;
//    /* if(typeof(maxDays)==='undefined') */maxDays = 100;
//
//    var pdfList = new Array();
//    var cdfList = new Array();
//
//    var thisAverage = 4.4; /*
//     * days This is the mean of the Weibull distrubtion
//     */
//    var aveShift = parseFloat(newAverage) - thisAverage;
//
//    for (var i = 0; i < 21; i++) {
//        pdfList.push(ShiftedWeibull(parseFloat(i), gamma, lamb, shift
//                + aveShift))
//    }
//
//    var sum = 0.0;
//
//    for (var i = 0; i < pdfList.length; i++) {
//        // java.lang.System.out.println(pdfList[i]);
//        sum += pdfList[i];
//        //console.log(Math.round(pdfList[i]*10000)/10000);
//        if ((sum + thresh >= 1.0) || (i >= maxDays) ||
//                ((i > 0) && (pdfList[i] > 0.0) &&
//                        (Math.round(pdfList[i] * 10000) / 10000 == Math.round(pdfList[i - 1] * 10000) / 10000))) {
//            cdfList.push(1.0);
//            break;
//        } else {
//            cdfList.push(sum);
//        }
//    }
//
//    var cdfString = cdfList.length;
//
//    for (var x = 0; x < cdfList.length; x++) {
//        /* cdfString += " %1.2f"%x */
//        cdfString += " " + cdfList[x].toFixed(6);
//    }
//    return cdfString;
//
//}


// THIS IS OLD AND SHOULD NOT BE USED
//function incubationPeriodWithParams(newAverage, gamma, lamb, shift, thresh, maxdays) {
//    var pdfList = new Array();
//    var cdfList = new Array();
//
//    var thisAverage = 1.984; // days This is the mean of the fitted Weibull
//    // distrubiton
//    aveShift = parseFloat(newAverage) - thisAverage;
//
//    for (var i = 0; i < maxdays + 1; i++) {
//        pdfList.push(ShiftedWeibull(parseFloat(i), gamma, lamb, shift
//                + aveShift))
//    }
//
//    var sum = 0.0;
//    for (var i = 0; i < pdfList.length; i++) {
//        sum += pdfList[i];
//        if ((sum + thresh >= 1.0) || (i >= maxdays) ||
//                ((i > 0) && (pdfList[i] > 0.0) &&
//                        (Math.round(pdfList[i] * 10000) / 10000 == Math.round(pdfList[i - 1] * 10000) / 10000))) {
//            cdfList.push(1.0);
//            break;
//        } else {
//            cdfList.push(sum);
//        }
//    }
//
//    var cdfString = cdfList.length;
//    for (var x = 0; x < cdfList.length; x++) {
//        cdfString += " " + cdfList[x].toFixed(2);
//    }
//
//    return cdfString;
//}
//
//function incubationPeriod(newAverage) {
//    return incubationPeriodWithParams(newAverage, 2.2126752, 1.67, 0.5, 0.10, 100);
//}

//function ShiftedWeibull(x, gamma, lamb, shift) {
//    if (typeof (shift) === 'undefined')
//        shift = 0.0;
//
//    var xShift = x - shift;
//    if (xShift <= 0.0)
//        return 0;
//    else
//        return ((gamma / lamb) * Math.pow((xShift / lamb), (gamma - 1.0)) * Math
//                .exp(-Math.pow((xShift / lamb), gamma)));
//}

function getFractionRecovered(dataCells) {

    //    if ((psa.getDescription().getAgeRanges().size()
    //        + psa.getDescription().getGenders().size() > 0)) {
    //    // error!!!
    //    }

    for (var i = 0; i < dataCells.size(); i++) {

        var cell = dataCells.get(i);

        if (cell.getAgeRange() != null &&
                cell.getGender() != null) {
            // error!!!
            throw "FRED does not support specifying an age range for a PopulationImmunityAndInfectionCensusDataCell";
        }

        if (cell.getInfectionState() == "RECOVERED") {
            return "1 " + cell.getFractionInState();
        }
    }

}

function getExposedAndInfectious(dataCells) {

    var obj = new Object();
    obj.warnings = [];

    var ageRangeGenderWarningSet = false;
    var fracExposed = 0, fracInfectious = 0;
    var fracExposedSet = false, fracInfectiousSet = false;
    for (var i = 0; i < dataCells.size(); i++) {

        var cell = dataCells.get(i);
        if (!ageRangeGenderWarningSet) {
            if (cell.getAgeRange() != null &&
                    cell.getGender() != null) {
                var warningsString = "no-one-to-one-mapping# " + fred_version + " does not allow setting the initial infected by age range or gender. Instead, the fractions of LATENT and INFECTIOUS will be summed over all census data cells";
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

    var ratioExposed = parseFloat(fracExposed)
            / (parseFloat(fracExposed) + parseFloat(fracInfectious));
    var ratioInfectious = 1.0 - ratioExposed;

    obj.value = "exposed:" + ratioExposed.toFixed(2) + ";" + "infectious:"
            + ratioInfectious.toFixed(2);

    return obj;
}

function getVaccineStrategyDistribution(value) {
    return "7 " + Number((1 - value)).toFixed(5) + " " + value + " 0 0 0 0 0";
}

function getACIP(vaccinationControlStrategy) {
    if (vaccinationControlStrategy.targetPopulationsAndPrioritizations.getControlMeasureNamedPrioritizationScheme() != null &&
            vaccinationControlStrategy.targetPopulationsAndPrioritizations.getControlMeasureNamedPrioritizationScheme() == 'ACIP') {
        return 1;
    } else {
        return 0;
    }
}

function getVaccineEfficacy(vaccinationControlStrategy) {
    return "1 "
            + vaccinationControlStrategy.getIndividualTreatment()
            .getVaccinationEfficacies().get(0)
            .getAverageVaccinationEfficacy();

}

function getProphylaxis(targetPopulationsAndPrioritization) {
    if (targetPopulationsAndPrioritization.size() == 0) {
        return "1";
    }
    if (targetPopulationsAndPrioritization.size() > 1) {
        //        return getException("More than one target priority population for antiviral treatment was passed. Only one is supported");
        throw "More than one target priority population for antiviral treatment was passed. Only one is supported";
    }

    var diseaseOutcome = targetPopulationsAndPrioritization.get(0).getTargetPopulationDefinition().getDiseaseOutcome();
    //    if (infectionStates.size() != 1) {
    //        throw "Only one disease outcome is supported for the target priority population in antiviral treatment";
    //    }

    if (diseaseOutcome == "SYMPTOMATIC") {
        return "0";
    } else {
        return "1";
    }
}

function getSchoolClosureControlStrategyPolicy(closeIndependently) {

    if (!closeIndependently)
        return "global";
    else
        return "reactive";
}

function getSchoolClosureControlStrategyThreshold(triggers) {
    var obj = new Object();
    obj.print = true;
    var foundThreshold = false;
    var printedPercentWarning = false;
    for (var i = 0; i < triggers.size(); i++) {
        var trigger = triggers.get(i);
        if (trigger instanceof edu.pitt.apollo.types.v4_0.DiseaseSurveillanceTriggerDefinition) {
            var unit = trigger.getUnitOfMeasureForThreshold();
            if (unit == 'PERCENT') {
                foundThreshold = true;
                var threshold = trigger.getReactiveControlMeasureThreshold();
                obj.value = threshold / 100;
                break;
            } else {
                if (!printedPercentWarning) {
                    var warningString = "no-native-param#  " + fred_version + " can only use " + apollo_version + " thresholds that have a unit of PERCENT";
                    obj.warnings.push(warningString.toString());
                    printedPercentWarning = true;
                }
            }
        }
    }

    if (!foundThreshold) {
        obj.print = false;
    }

    return obj;
}

function getSchoolClosureControlStrategyDay(triggers) {
    var obj = new Object();
    obj.print = true;
    var foundTime = false;
    for (var i = 0; i < triggers.size(); i++) {
        var trigger = triggers.get(i);
        if (trigger instanceof edu.pitt.apollo.types.v4_0.TemporalTriggerDefinition) {
            var startTime = trigger.getTimeSinceTimeScaleZero();
            if (startTime instanceof edu.pitt.apollo.types.v4_0.FixedDuration) {
                var unitOfTime = startTime.getUnitOfTime();
                if (unitOfTime == 'DAY') {
                    var val = startTime.getValue();
                    if (isInt(val)) {
                        obj.value = val.toString();
                    } else {
                        obj.value = Math.round(val);
                        var warningString = "no-native-param#  The control strategy start time value is specified as a double, but " + fred_version + " requires an integer. The rounded value will be used.";
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

    //    if (typeof (schoolClosureControlStrategy.controlStrategyStartTime.startTimeRelativeToScenarioDate) != 'undefined') {
    //        o.print = true;
    //        o.value = schoolClosureControlStrategy.controlStrategyStartTime.startTimeRelativeToScenarioDate;
    //    } else {
    //        o.print = false;
    //    }
    return obj;
}

function getControlStrategyDay(triggers, type) {

    var obj = new Object();
    obj.print = true;
    obj.warnings = [];
    var foundTime = false;
    for (var i = 0; i < triggers.size(); i++) {
        var trigger = triggers.get(i);
        if (trigger instanceof edu.pitt.apollo.types.v4_0.TemporalTriggerDefinition) {
            var startTime = trigger.getTimeSinceTimeScaleZero();
            if (startTime instanceof edu.pitt.apollo.types.v4_0.FixedDuration) {
                var unitOfTime = startTime.getUnitOfTime();
                if (unitOfTime == 'DAY') {
                    var val = startTime.getValue();
                    if (isInt(val)) {
                        obj.value = val.toString();
                    } else {
                        obj.value = Math.round(val);
                        var warningString = "no-native-param#  The control strategy start time value is specified as a double, but " + fred_version + " requires an integer. The rounded value will be used.";
                        obj.warnings.push(warningString.toString());
                    }
                    foundTime = true;
                    break;
                }
            }
        }
    }

    if (!foundTime) {
        obj.warnings = [];
        var warningString = "no-native-param#  No " + apollo_version + " control strategy start time for the " + type + " control strategy contains a start time that is measured in days. A default of zero will be used.";
        obj.warnings.push(warningString.toString());
        obj.value = 0;
    }

    return obj;
}

function getSchedule(scheduleElements, startDate) {
    var schedule = getArraySchedule(startDate, scheduleElements);
    var tot = 0;
    for (var i = 0; i < schedule.length; i++) {
        tot += schedule[i];
    }
    return tot;
}

//function getTotalNumAv(scheduleElements, startDate) {
//    var supply = antiviralTreatmentControlStrategy.treatmentSystemLogistics.get(0).supplySchedulePerDay;
//    var tot = 0;
//    for (var i = 0; i < supply.size(); i++) {
//        tot += parseInt(supply.get(i));
//    }
//    return tot;
//}

function getFREDStyleCapacitySchedule(antiviralTreatmentControlStrategy) {

    var supply = antiviralTreatmentControlStrategy.treatmentSystemLogistics.get(0).antiviralSupplySchedulePerDay;
    var capacity = antiviralTreatmentControlStrategy.treatmentSystemLogistics.get(0).antiviralAdminSchedulePerDay;
    var length = capacity.size();
    var stockpile = 0;
    var result = "[";

    for (var i = 0; i < length; i++) {
        var deliveredToday = supply.get(i);
        var capacityToday = capacity.get(i);
        var giveToday = 0, addToStockpile = 0;
        // on day X hand out up to capacity if possible
        if (capacityToday >= deliveredToday) {
            giveToday = deliveredToday;
        } else {
            giveToday = capacityToday;
            addToStockPile = deliveredToday - capacityToday;
        }
        stockpile += addToStockPile;
        result += giveToday + ",";
    }
    return result;
}

function getAvgOfSchedule(scheduleElements, startDate) {
    var schedule = getArraySchedule(startDate, scheduleElements);
    if (schedule.length > 0) {
        var tot = getSchedule(scheduleElements, startDate);
        return tot / schedule.length;
    } else
        return 0;
}

function readPopFile(populationFileLocation,
        populationInfectionAndImmunityCensus) {
    // for ( var i = 0; i < populationStates.populationStates.getDescription()
    // .getDiseaseStates().size(); i++) {
    // if (populationStates.populationStates.getDescription()
    // .getDiseaseStates().get(i) == "EXPOSED") {
    // fracExposed = populationStates.populationStates.getValues().get(i);
    // }
    // }


    var fracExposed, fracInfectious;

    var censusData = populationInfectionAndImmunityCensus.censusData;
    var censusDataCells = censusData.censusDataCells;

    for (var i = 0; i < censusDataCells.size(); i++) {

        if (censusDataCells.get(i).getInfectionState() == "LATENT") {

            fracExposed = censusDataCells.get(i).getFractionInState();
        }
    }



    // for ( var i = 0; i < populationStates.populationStates.getDescription()
    // .getDiseaseStates().size(); i++) {
    // if (populationStates.populationStates.getDescription()
    // .getDiseaseStates().get(i) == "INFECTIOUS") {
    // fracInfectious = populationStates.populationStates.getValues().get(
    // i);
    // }
    // }
    //
    for (var i = 0; i < censusDataCells.size(); i++) {
        if (censusDataCells.get(i).getInfectionState() == "INFECTIOUS") {
            fracInfectious = censusDataCells.get(i)
                    .getFractionInState();

        }
    }

    // for ( var i = 0; i < populationStates.populationStates.getDescription()
    // .getDiseaseStates().size(); i++) {
    // if (populationStates.populationStates.getDescription()
    // .getDiseaseStates().get(i) == "INFECTIOUS") {
    // fracInfectious = populationStates.populationStates.getValues().get(
    // i);
    // }
    // }
    //


    var incits = null;
    //
    // var location = populationStates.getLocation();
    var location = censusData.getLocation();
    //
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
    //
    var fileContents = readFile(populationFileLocation);

    var totalPop = 0;
    var arr = fileContents.split('\n');
    for (var i = 0; i < arr.length; i++) {
        var line = arr[i].split(' ');
        if (line[0] == incits || (incits == "US" && line[0].length == 2)) {

            totalPop += parseInt(line[1]);
        }


    }

    var numExposed = fracExposed * totalPop;
    var numInfectious = fracInfectious * totalPop;
    var ratioExposed = numExposed / (numExposed + numInfectious);
    var ratioInfectious = 1.0 - ratioExposed;
    return "0 0 " + (parseInt(numExposed) + parseInt(numInfectious));

}

/* NOT USED YET, ASK SHAWN */
function setDelay() {
    var preemptively_vaccinated = vaccinationControlStrategy.vaccination.vaccinationEfficacies
            .get(0).vaccinationEfficaciesByTimeSinceMostRecentDose.get(0)
            .getVaccinationEfficacyByTimeSinceMostRecentDose();

    for (var i = preemptively_vaccinated.size() - 1; i >= 0; i--) {
        resultString += "-" + i + " " + preemptively_vaccinated.get(i) + '\n';
    }

}

function writeVaccSchedule(logisticalSystemNode, runLength, startDate) {
    var obj = new Object();
    obj.print = true;
    var resultString = "";
    var totalSurplus = 0;

    var useToday = 0;

    var supplyScheduleElements = logisticalSystemNode.getOutputSchedule().getScheduleElements();
    var administrationScheduleElements = logisticalSystemNode.getChildren().get(0).getCapacitySchedule().getScheduleElements();

    // convert the schedule elements into arrays and use the legacy code

    var capacity = getArraySchedule(startDate, administrationScheduleElements);
    var supply = getArraySchedule(startDate, supplyScheduleElements);


    var implicitly_timeed_preemptive_array = undefined;

    //if (passwordField.indexOf("=") > -1) {
    //    implicitly_timeed_preemptive_array = passwordField.split("=")[1];
    //    var preemptive_vacc_schedule = implicitly_timeed_preemptive_array
    //            .split(",");
    //    for (var i = preemptive_vacc_schedule.length - 1; i >= 0; i--) {
    //        resultString += "-" + (i + 1) + " " + preemptive_vacc_schedule[i]
    //                + "\n";
    //    }
    //}

    for (var i = 0; i < supply.length; i++) {

        supplyVal = supply[i];
        capacityVal = capacity[i];

        if (supplyVal >= capacityVal) {
            totalSurplus = supplyVal - capacityVal;
            resultString += i + " " + capacityVal;
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
            resultString += i + " " + useToday;
        }
        resultString += "\n";
    }

    if (supply.length < runLength) {
        for (i = supply.length; i < runLength; i++) {
            resultString += i + " " + "0";
            resultString += "\n";
        }
        obj.warnings = [];
        var warningString = "no-one-to-one-mapping#The vaccination administration capacity had a length less than the specified run length. Zeros were appended to the schedule so that the lengths match.";
        obj.warnings.push(warningString.toString());
    }
    obj.value = resultString;

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
