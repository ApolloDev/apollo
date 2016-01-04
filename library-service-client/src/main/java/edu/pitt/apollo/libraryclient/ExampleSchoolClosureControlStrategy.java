package edu.pitt.apollo.libraryclient;

import edu.pitt.apollo.types.v4_0.ApolloPathogenCode;
import edu.pitt.apollo.types.v4_0.DiseaseOutcomeEnum;
import edu.pitt.apollo.types.v4_0.DiseaseSurveillanceCapability;
import edu.pitt.apollo.types.v4_0.DiseaseSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v4_0.FixedDuration;
import edu.pitt.apollo.types.v4_0.Location;
import edu.pitt.apollo.types.v4_0.OperatorEnum;
import edu.pitt.apollo.types.v4_0.PlaceClosureControlMeasure;
import edu.pitt.apollo.types.v4_0.PlaceEnum;
import edu.pitt.apollo.types.v4_0.UnitOfMeasureEnum;
import edu.pitt.apollo.types.v4_0.UnitOfTimeEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 19, 2015
 * Time: 11:18:07 AM
 * Class: ExampleSchoolClosureControlMeasure
 */
public class ExampleSchoolClosureControlStrategy {

	public static PlaceClosureControlMeasure getAllSchoolsControlMeasure() {

		PlaceClosureControlMeasure scm = new PlaceClosureControlMeasure();

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

		rt.setReactiveControlMeasureTest("Symptomatic Students");
		rt.setReactiveControlMeasureThreshold(1);
		rt.setReactiveControlMeasureOperator(OperatorEnum.GREATER_THAN_OR_EQUAL);
		rt.setDiseaseSurveillanceCapability(capability);
		rt.setUnitOfMeasureForThreshold(UnitOfMeasureEnum.PERCENT);
		rt.setDiseaseSurveillanceCapability(capability);
		scm.getControlMeasureStartTime().add(rt);

		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		responseDelay.setValue(2d);

		scm.setControlMeasureResponseDelay(responseDelay);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		standDownDelay.setValue(2d);

		scm.setControlMeasureStandDownDelay(standDownDelay);

		scm.setDescription("A school closure control strategy where all schools in the jurisdiction are closed to mitigate the spread of an infectious disease.");

		FixedDuration closurePeriod = new FixedDuration();
		closurePeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
		closurePeriod.setValue(56);

		scm.setClosurePeriod(closurePeriod);
		scm.setPlaceClass(PlaceEnum.ALL_SCHOOLS);

		return scm;
	}

	public static PlaceClosureControlMeasure getIndividualSchoolsControlMeasure() {
		PlaceClosureControlMeasure scm = new PlaceClosureControlMeasure();

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

		rt.setReactiveControlMeasureTest("Symptomatic Students");
		rt.setReactiveControlMeasureThreshold(1);
		rt.setReactiveControlMeasureOperator(OperatorEnum.GREATER_THAN_OR_EQUAL);
		rt.setDiseaseSurveillanceCapability(capability);
		rt.setUnitOfMeasureForThreshold(UnitOfMeasureEnum.PERCENT);
		rt.setDiseaseSurveillanceCapability(capability);
		scm.getControlMeasureStartTime().add(rt);

		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setValue(2d);
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		standDownDelay.setValue(2d);

		scm.setControlMeasureStandDownDelay(standDownDelay);

		scm.setControlMeasureResponseDelay(responseDelay);
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
