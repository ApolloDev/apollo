/*
 * Copyright 2016 nem41.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.pitt.apollo.examples;

import edu.pitt.apollo.types.v4_0_2.*;
import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.isg.objectserializer.XMLSerializer;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;

import java.math.BigInteger;
import java.util.Arrays;

/**
 *
 * @author nem41
 */
public class ExampleSimulatorCountOutput {
	
	public static void main(String[] args) throws SerializationException, edu.pitt.isg.objectserializer.exceptions.SerializationException {
		
		int[][] ageRanges = {{0, 1}, {2, 10}, {10, 20}, {20, 80}};
		
		SimulatorCountOutput output = new SimulatorCountOutput();
		output.setSimulatorCountOutputSpecificationId(BigInteger.ONE);
		output.setSpatialGranularity(SpatialGranularityEnum.ADMIN_2);
		output.setSpeciesToCount("9606");
		output.setTemporalGranularity(TemporalGranularityEnum.EACH_SIMULATION_TIMESTEP);
		
		CategoricalVariableNode root = new CategoricalVariableNode();
		root.setName(CategoricalVariableEnum.GENDER);
		
		CategoryValueNode male = new CategoryValueNode();
		GenderCategoryDefinition maleCatDef = new GenderCategoryDefinition();
		maleCatDef.setSex(GenderEnum.M);
		male.setCategoryDefinition(maleCatDef);
		
		CategoricalVariableNode maleAgeRange = new CategoricalVariableNode();
		
		for (int[] ageRange : ageRanges) {
			CategoryValueNode node = new CategoryValueNode();
			AgeRangeCategoryDefinition ageRangeCatDef = new AgeRangeCategoryDefinition();
			//TODO: update to use Bounds
			/*ageRangeCatDef.setLowerBound(new BigInteger(Integer.toString(ageRange[0])));
			ageRangeCatDef.setUpperBound(new BigInteger(Integer.toString(ageRange[1])));*/
			ageRangeCatDef.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
			ageRangeCatDef.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
			node.setCategoryDefinition(ageRangeCatDef);
			node.setValue(BigInteger.ONE);
			maleAgeRange.getCategories().add(node);
		}
		male.setNextCategoricalVariable(maleAgeRange);
		male.setCategoryDefinition(maleCatDef);
		
		CategoryValueNode female = new CategoryValueNode();
		GenderCategoryDefinition femaleCatDef = new GenderCategoryDefinition();
		femaleCatDef.setSex(GenderEnum.F);
		female.setCategoryDefinition(femaleCatDef);
		
		CategoricalVariableNode femaleAgeRange = new CategoricalVariableNode();
		
		for (int[] ageRange : ageRanges) {
			CategoryValueNode node = new CategoryValueNode();
			AgeRangeCategoryDefinition ageRangeCatDef = new AgeRangeCategoryDefinition();
			//TODO: update to use Bounds
		/*	ageRangeCatDef.setLowerBound(new BigInteger(Integer.toString(ageRange[0])));
			ageRangeCatDef.setUpperBound(new BigInteger(Integer.toString(ageRange[1])));*/
			ageRangeCatDef.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
			ageRangeCatDef.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
			node.setCategoryDefinition(ageRangeCatDef);
			node.setValue(BigInteger.ONE);
			femaleAgeRange.getCategories().add(node);
		}
		female.setNextCategoricalVariable(femaleAgeRange);
		female.setCategoryDefinition(femaleCatDef);
		
		root.getCategories().add(male);
		root.getCategories().add(female);
		
		VariableCategoryValueTree tree = new VariableCategoryValueTree();
		tree.setRootVariableNode(root);
		
		output.setCountArray(tree);
		
		System.out.println(new XMLSerializer(Arrays.asList(ApolloClassList.classList)).serializeObject(tree));
	}
	
}
