package edu.pitt.apollo.libraryclient;

import edu.pitt.apollo.types.v3_0_2.ApolloPathogenCode;
import edu.pitt.apollo.types.v3_0_2.DiseaseOutcomeEnum;
import edu.pitt.apollo.types.v3_0_2.DiseaseSurveillanceCapability;
import edu.pitt.apollo.types.v3_0_2.DiseaseSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v3_0_2.FixedDuration;
import edu.pitt.apollo.types.v3_0_2.Location;
import edu.pitt.apollo.types.v3_0_2.OperatorEnum;
import edu.pitt.apollo.types.v3_0_2.PlaceClosureControlStrategy;
import edu.pitt.apollo.types.v3_0_2.PlaceEnum;
import edu.pitt.apollo.types.v3_0_2.UnitOfMeasureEnum;
import edu.pitt.apollo.types.v3_0_2.UnitOfTimeEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 19, 2015
 * Time: 11:18:07 AM
 * Class: ExampleSchoolClosureControlStrategy
 */
public class ExampleSchoolClosureControlStrategy {

	public static PlaceClosureControlStrategy getAllSchoolsControlStrategy() {

		PlaceClosureControlStrategy scm = new PlaceClosureControlStrategy();

		Location location = new Location();
		location.setApolloLocationCode("1169");

		DiseaseSurveillanceTriggerDefinition rt = new DiseaseSurveillanceTriggerDefinition();

		DiseaseSurveillanceCapability capability = new DiseaseSurveillanceCapability();
		capability.setCaseDefinition(DiseaseOutcomeEnum.SYMPTOMATIC);
		capability.setLocation(location);

		ApolloPathogenCode code = new ApolloPathogenCode();
		code.setNcbiTaxonId("114727");
		capability.setPathogen(code);
		capability.setSensitivityOfCaseDetection(0.5);
		capability.setSpeciesOfCase("9606");
		capability.setSpecificityOfCaseDetection(1.0);

		FixedDuration caseDetectionTimeDelay = new FixedDuration();
		caseDetectionTimeDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		caseDetectionTimeDelay.setValue(2d);
		capability.setTimeDelayOfCaseDetection(caseDetectionTimeDelay);

		rt.setReactiveControlStrategyTest("Symptomatic Students");
		rt.setReactiveControlStrategyThreshold(1);
		rt.setReactiveControlStrategyOperator(OperatorEnum.GREATER_THAN_OR_EQUAL);
		rt.setDiseaseSurveillanceCapability(capability);
		rt.setUnitOfMeasureForThreshold(UnitOfMeasureEnum.PERCENT);
		rt.setDiseaseSurveillanceCapability(capability);
		scm.getControlStrategyStartTime().add(rt);

		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		responseDelay.setValue(2d);

		scm.setControlStrategyResponseDelay(responseDelay);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		standDownDelay.setValue(2d);

		scm.setControlStrategyStandDownDelay(standDownDelay);

		scm.setDescription("A school closure control strategy where all schools in the jurisdiction are closed to mitigate the spread of an infectious disease.");

		FixedDuration closurePeriod = new FixedDuration();
		closurePeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
		closurePeriod.setValue(56);

		scm.setClosurePeriod(closurePeriod);
		scm.setPlaceClass(PlaceEnum.ALL_SCHOOLS);

		return scm;
	}

	public static PlaceClosureControlStrategy getIndividualSchoolsControlStrategy() {
		PlaceClosureControlStrategy scm = new PlaceClosureControlStrategy();

		Location location = new Location();
		location.setApolloLocationCode("1169");

		DiseaseSurveillanceTriggerDefinition rt = new DiseaseSurveillanceTriggerDefinition();

		DiseaseSurveillanceCapability capability = new DiseaseSurveillanceCapability();
		capability.setCaseDefinition(DiseaseOutcomeEnum.SYMPTOMATIC);
		capability.setLocation(location);

		ApolloPathogenCode code = new ApolloPathogenCode();
		code.setNcbiTaxonId("114727");
		capability.setPathogen(code);
		capability.setSensitivityOfCaseDetection(0.5);
		capability.setSpeciesOfCase("9606");
		capability.setSpecificityOfCaseDetection(1.0);

		FixedDuration caseDetectionTimeDelay = new FixedDuration();
		caseDetectionTimeDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		caseDetectionTimeDelay.setValue(2d);
		capability.setTimeDelayOfCaseDetection(caseDetectionTimeDelay);

		rt.setReactiveControlStrategyTest("Symptomatic Students");
		rt.setReactiveControlStrategyThreshold(1);
		rt.setReactiveControlStrategyOperator(OperatorEnum.GREATER_THAN_OR_EQUAL);
		rt.setDiseaseSurveillanceCapability(capability);
		rt.setUnitOfMeasureForThreshold(UnitOfMeasureEnum.PERCENT);
		rt.setDiseaseSurveillanceCapability(capability);
		scm.getControlStrategyStartTime().add(rt);

		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setValue(2d);
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		standDownDelay.setValue(2d);

		scm.setControlStrategyStandDownDelay(standDownDelay);

		scm.setControlStrategyResponseDelay(responseDelay);
		scm.setDescription("A school closure control strategy where schools with high disease activity are closed to mitigate the spread of an infectious disease.");
		FixedDuration closurePeriod = new FixedDuration();
		closurePeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
		closurePeriod.setValue(56);

		scm.setClosurePeriod(closurePeriod);
		scm.setCloseIndividualPlacesIndependently(true);
		scm.setPlaceClass(PlaceEnum.ALL_SCHOOLS);

		return scm;
	}

}
