package edu.pitt.apollo.db;

import edu.pitt.apollo.JsonUtils;
import edu.pitt.apollo.Md5Utils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseRecordAlreadyExistsException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseUserPasswordException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import static edu.pitt.apollo.GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE;
import static edu.pitt.apollo.db.ApolloDbUtils.logger;
import edu.pitt.apollo.types.v3_0_0.AaaDummyType;
import edu.pitt.apollo.types.v3_0_0.AbioticEcosystem;
import edu.pitt.apollo.types.v3_0_0.AbioticEcosystemCensus;
import edu.pitt.apollo.types.v3_0_0.AbioticEcosystemElementCensusDescription;
import edu.pitt.apollo.types.v3_0_0.AbioticEcosystemEnum;
import edu.pitt.apollo.types.v3_0_0.AgeGroupEfficacy;
import edu.pitt.apollo.types.v3_0_0.AgeRangeCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.AntiviralTreatment;
import edu.pitt.apollo.types.v3_0_0.AntiviralTreatmentEfficacy;
import edu.pitt.apollo.types.v3_0_0.ApolloIndexableItem;
import edu.pitt.apollo.types.v3_0_0.ApolloIndexableItemTypeEnum;
import edu.pitt.apollo.types.v3_0_0.ApolloPathogenCode;
import edu.pitt.apollo.types.v3_0_0.ArrayAxis;
import edu.pitt.apollo.types.v3_0_0.ArrayDimensionsDefinition;
import edu.pitt.apollo.types.v3_0_0.BaseCaseResult;
import edu.pitt.apollo.types.v3_0_0.BayesianNetwork;
import edu.pitt.apollo.types.v3_0_0.BayesianNetworkStructureType;
import edu.pitt.apollo.types.v3_0_0.BehaviorEnum;
import edu.pitt.apollo.types.v3_0_0.BioticEcosystem;
import edu.pitt.apollo.types.v3_0_0.BorderControlStrategy;
import edu.pitt.apollo.types.v3_0_0.CartesianCircleLocationDefinition;
import edu.pitt.apollo.types.v3_0_0.CaseCount;
import edu.pitt.apollo.types.v3_0_0.CaseCountArray;
import edu.pitt.apollo.types.v3_0_0.CaseCountCategory;
import edu.pitt.apollo.types.v3_0_0.CaseDefinition;
import edu.pitt.apollo.types.v3_0_0.CaseDefinitionEnum;
import edu.pitt.apollo.types.v3_0_0.CaseList;
import edu.pitt.apollo.types.v3_0_0.CaseQuarantineControlStrategy;
import edu.pitt.apollo.types.v3_0_0.CaseRecord;
import edu.pitt.apollo.types.v3_0_0.CaseRecordCategoricalVariable;
import edu.pitt.apollo.types.v3_0_0.CaseRecordCategoricalVariables;
import edu.pitt.apollo.types.v3_0_0.CaseVariableAndValue;
import edu.pitt.apollo.types.v3_0_0.Category;
import edu.pitt.apollo.types.v3_0_0.CategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.Census;
import edu.pitt.apollo.types.v3_0_0.CensusData;
import edu.pitt.apollo.types.v3_0_0.ClaraDensityDependentMortalityFunction;
import edu.pitt.apollo.types.v3_0_0.CompartmentalModelPopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v3_0_0.ConditionalIndividualBehavior;
import edu.pitt.apollo.types.v3_0_0.ConditionalProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.ConditionalProbabilityTable;
import edu.pitt.apollo.types.v3_0_0.ConditioningVariable;
import edu.pitt.apollo.types.v3_0_0.ConditioningVariableEnum;
import edu.pitt.apollo.types.v3_0_0.ContactDefinition;
import edu.pitt.apollo.types.v3_0_0.ContactDefinitionEnum;
import edu.pitt.apollo.types.v3_0_0.ContactModelForCommunity;
import edu.pitt.apollo.types.v3_0_0.ContactModelForHousehold;
import edu.pitt.apollo.types.v3_0_0.ContactModelForPlace;
import edu.pitt.apollo.types.v3_0_0.ContactModelForSetting;
import edu.pitt.apollo.types.v3_0_0.ContainerReductionControlStrategy;
import edu.pitt.apollo.types.v3_0_0.ContaminatedThingCensus;
import edu.pitt.apollo.types.v3_0_0.ContaminatedThingCensusData;
import edu.pitt.apollo.types.v3_0_0.Contamination;
import edu.pitt.apollo.types.v3_0_0.ContaminationAcquisition;
import edu.pitt.apollo.types.v3_0_0.ContinuousParametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.ContinuousUniformDistribution;
import edu.pitt.apollo.types.v3_0_0.ControlStrategyTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v3_0_0.CountType;
import edu.pitt.apollo.types.v3_0_0.DecisionAlternative;
import edu.pitt.apollo.types.v3_0_0.DecisionAnalysis;
import edu.pitt.apollo.types.v3_0_0.DevelopmentalStageEnum;
import edu.pitt.apollo.types.v3_0_0.DiscreteNonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.DiscreteParametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.DiseaseOutcomeCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.DiseaseOutcomeEnum;
import edu.pitt.apollo.types.v3_0_0.DiseaseOutcomeWithLocationDateTime;
import edu.pitt.apollo.types.v3_0_0.DiseaseOutcomeWithProbability;
import edu.pitt.apollo.types.v3_0_0.DiseaseSurveillanceCapability;
import edu.pitt.apollo.types.v3_0_0.DiseaseSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v3_0_0.Distance;
import edu.pitt.apollo.types.v3_0_0.DoubleCount;
import edu.pitt.apollo.types.v3_0_0.DrugTreatment;
import edu.pitt.apollo.types.v3_0_0.DrugTreatmentEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types.v3_0_0.Duration;
import edu.pitt.apollo.types.v3_0_0.Ecosystem;
import edu.pitt.apollo.types.v3_0_0.Epidemic;
import edu.pitt.apollo.types.v3_0_0.EpidemicPeriod;
import edu.pitt.apollo.types.v3_0_0.EpidemicPeriodBoundaryDefinitionEnum;
import edu.pitt.apollo.types.v3_0_0.ExpectedUtility;
import edu.pitt.apollo.types.v3_0_0.FixedDuration;
import edu.pitt.apollo.types.v3_0_0.FractionOfThingContaminated;
import edu.pitt.apollo.types.v3_0_0.GammaDistribution;
import edu.pitt.apollo.types.v3_0_0.GeNIEXMLType;
import edu.pitt.apollo.types.v3_0_0.GenderCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.GenderEnum;
import edu.pitt.apollo.types.v3_0_0.GesInfectiousnessParameterSet;
import edu.pitt.apollo.types.v3_0_0.GesParametersForContactAndTransmission;
import edu.pitt.apollo.types.v3_0_0.Individual;
import edu.pitt.apollo.types.v3_0_0.IndividualBehavior;
import edu.pitt.apollo.types.v3_0_0.IndividualHumanBehavior;
import edu.pitt.apollo.types.v3_0_0.IndividualLifeCycle;
import edu.pitt.apollo.types.v3_0_0.IndividualMosquitoBehavior;
import edu.pitt.apollo.types.v3_0_0.IndividualMosquitoReproduction;
import edu.pitt.apollo.types.v3_0_0.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v3_0_0.IndividualTreatmentEnum;
import edu.pitt.apollo.types.v3_0_0.IndoorResidualSprayingVectorControlStrategy;
import edu.pitt.apollo.types.v3_0_0.Infection;
import edu.pitt.apollo.types.v3_0_0.InfectionAcquisitionFromContaminatedThing;
import edu.pitt.apollo.types.v3_0_0.InfectionAcquisitionFromInfectiousHost;
import edu.pitt.apollo.types.v3_0_0.InfectionStateEnum;
import edu.pitt.apollo.types.v3_0_0.InfectiousDisease;
import edu.pitt.apollo.types.v3_0_0.InfectiousDiseaseControlStrategy;
import edu.pitt.apollo.types.v3_0_0.InfectiousDiseaseDecisionModel;
import edu.pitt.apollo.types.v3_0_0.InfectiousDiseaseScenario;
import edu.pitt.apollo.types.v3_0_0.IntegerCount;
import edu.pitt.apollo.types.v3_0_0.Interval;
import edu.pitt.apollo.types.v3_0_0.IntervalBoundaryDefinitionEnum;
import edu.pitt.apollo.types.v3_0_0.LabTestAndResult;
import edu.pitt.apollo.types.v3_0_0.LarvicideControlStrategy;
import edu.pitt.apollo.types.v3_0_0.LibraryItem;
import edu.pitt.apollo.types.v3_0_0.LifeStageWithDurationAndMortality;
import edu.pitt.apollo.types.v3_0_0.Location;
import edu.pitt.apollo.types.v3_0_0.LocationDefinition;
import edu.pitt.apollo.types.v3_0_0.LocationPolygon;
import edu.pitt.apollo.types.v3_0_0.LogNormalDistribution;
import edu.pitt.apollo.types.v3_0_0.MeanMedianMinimumMaximum;
import edu.pitt.apollo.types.v3_0_0.MeanWithConfidenceInterval;
import edu.pitt.apollo.types.v3_0_0.MeanWithStandardDeviation;
import edu.pitt.apollo.types.v3_0_0.MortalityFunction;
import edu.pitt.apollo.types.v3_0_0.MultiGeometry;
import edu.pitt.apollo.types.v3_0_0.NamedMultiGeometry;
import edu.pitt.apollo.types.v3_0_0.NamedPrioritizationSchemeEnum;
import edu.pitt.apollo.types.v3_0_0.NonApolloParameter;
import edu.pitt.apollo.types.v3_0_0.NonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.OccupationEnum;
import edu.pitt.apollo.types.v3_0_0.OperatorEnum;
import edu.pitt.apollo.types.v3_0_0.OvipositionSiteCensus;
import edu.pitt.apollo.types.v3_0_0.ParameterValue;
import edu.pitt.apollo.types.v3_0_0.ParametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.PlaceCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.PlaceClosureControlStrategy;
import edu.pitt.apollo.types.v3_0_0.PlaceEnum;
import edu.pitt.apollo.types.v3_0_0.PlaceVisited;
import edu.pitt.apollo.types.v3_0_0.Population;
import edu.pitt.apollo.types.v3_0_0.PopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v3_0_0.PopulationCensusDescription;
import edu.pitt.apollo.types.v3_0_0.PopulationInfectionAndImmunityCensus;
import edu.pitt.apollo.types.v3_0_0.PopulationInfectionAndImmunityCensusData;
import edu.pitt.apollo.types.v3_0_0.PopulationInfectionAndImmunityCensusDataCell;
import edu.pitt.apollo.types.v3_0_0.PopulationInfectionSurvey;
import edu.pitt.apollo.types.v3_0_0.PopulationSerologySurvey;
import edu.pitt.apollo.types.v3_0_0.PopulationStratificationEnum;
import edu.pitt.apollo.types.v3_0_0.PopulationTreatmentCensus;
import edu.pitt.apollo.types.v3_0_0.PopulationTreatmentCensusData;
import edu.pitt.apollo.types.v3_0_0.PopulationTreatmentCensusDataCell;
import edu.pitt.apollo.types.v3_0_0.PreEpidemicEcosystemCensus;
import edu.pitt.apollo.types.v3_0_0.ProbabilisticParameter;
import edu.pitt.apollo.types.v3_0_0.ProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.ProbabilityValuePair;
import edu.pitt.apollo.types.v3_0_0.PrototypicalProbabilityFunction;
import edu.pitt.apollo.types.v3_0_0.Rate;
import edu.pitt.apollo.types.v3_0_0.RealDateSpanCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.RealTimePointCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.RealTimeSpanCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.Reference;
import edu.pitt.apollo.types.v3_0_0.RelativeRiskDataSet;
import edu.pitt.apollo.types.v3_0_0.ReproductionNumber;
import edu.pitt.apollo.types.v3_0_0.RequesterIdentification;
import edu.pitt.apollo.types.v3_0_0.RingIndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v3_0_0.ScenarioCartesianOrigin;
import edu.pitt.apollo.types.v3_0_0.SeasonalityFunctionParameters;
import edu.pitt.apollo.types.v3_0_0.SensitivityAnalysis;
import edu.pitt.apollo.types.v3_0_0.SensitivityAnalysisResult;
import edu.pitt.apollo.types.v3_0_0.SimulatorTimeRange;
import edu.pitt.apollo.types.v3_0_0.SimulatorTimeSpecification;
import edu.pitt.apollo.types.v3_0_0.SimulatorTypeEnum;
import edu.pitt.apollo.types.v3_0_0.SourceOfInfectionCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.SourceOfInfectionEnum;
import edu.pitt.apollo.types.v3_0_0.SpatialKernelFunctionParameters;
import edu.pitt.apollo.types.v3_0_0.TargetPopulationDefinition;
import edu.pitt.apollo.types.v3_0_0.TargetPopulationEnum;
import edu.pitt.apollo.types.v3_0_0.TargetPriorityPopulation;
import edu.pitt.apollo.types.v3_0_0.TemporalArrayDimensionsDefinition;
import edu.pitt.apollo.types.v3_0_0.TemporalTriggerDefinition;
import edu.pitt.apollo.types.v3_0_0.TimeAxisCategoryLabels;
import edu.pitt.apollo.types.v3_0_0.TimeDefinition;
import edu.pitt.apollo.types.v3_0_0.TimeScaleEnum;
import edu.pitt.apollo.types.v3_0_0.TimeSpanCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.TransmissionProbability;
import edu.pitt.apollo.types.v3_0_0.TransmissionTree;
import edu.pitt.apollo.types.v3_0_0.TravelRestrictionControlStrategy;
import edu.pitt.apollo.types.v3_0_0.Treatment;
import edu.pitt.apollo.types.v3_0_0.TreatmentContraindication;
import edu.pitt.apollo.types.v3_0_0.TreatmentEfficacy;
import edu.pitt.apollo.types.v3_0_0.TreatmentPreventableOutcomeEnum;
import edu.pitt.apollo.types.v3_0_0.TreatmentStateEnum;
import edu.pitt.apollo.types.v3_0_0.TreatmentSurveillanceCapability;
import edu.pitt.apollo.types.v3_0_0.TreatmentSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v3_0_0.TreatmentSystemLogistics;
import edu.pitt.apollo.types.v3_0_0.TriggerDefinition;
import edu.pitt.apollo.types.v3_0_0.UncertainDuration;
import edu.pitt.apollo.types.v3_0_0.UnconditionalProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.UnitOfDistanceEnum;
import edu.pitt.apollo.types.v3_0_0.UnitOfMeasureEnum;
import edu.pitt.apollo.types.v3_0_0.UnitOfTimeEnum;
import edu.pitt.apollo.types.v3_0_0.UtilityFunction;
import edu.pitt.apollo.types.v3_0_0.Vaccination;
import edu.pitt.apollo.types.v3_0_0.VaccinationEfficacyConditionedOnTimeSinceDose;
import edu.pitt.apollo.types.v3_0_0.VaccinationEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types.v3_0_0.VaccinationEfficacyInferred;
import edu.pitt.apollo.types.v3_0_0.VaccinationEfficacyMeasured;
import edu.pitt.apollo.types.v3_0_0.VaccinationEfficacyStudy;
import edu.pitt.apollo.types.v3_0_0.Vaccine;
import edu.pitt.apollo.types.v3_0_0.VaccineContraindications;
import edu.pitt.apollo.types.v3_0_0.VariableCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.VectorControlStrategy;
import edu.pitt.apollo.types.v3_0_0.WeibullDistribution;
import edu.pitt.apollo.types.v3_0_0.WithinGroupTransmissionProbability;
import edu.pitt.apollo.types.v3_0_0.WolbachiaControlStrategy;
import edu.pitt.apollo.types.v3_0_0.WolbachiaReleaseSiteEnum;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBMarshaller;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 6, 2014
 * Time: 11:10:59 AM
 * Class: BaseApolloDbUtils
 */
public abstract class BaseApolloDbUtils implements AutoCloseable {

	static Logger logger = LoggerFactory.getLogger(BaseApolloDbUtils.class);



	private static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
	protected final DataSource datasource;

	private static final String APOLLO_DIR;
	private static final String SALT_FILE_NAME = "salt.txt";
	protected static final String SYSTEM_SALT;
	private static final String USER_ID_TOKEN_SEPERATOR = "\\+";
	JsonUtils jsonUtils = new JsonUtils();
	Md5Utils md5Utils = new Md5Utils();
	Connection dbcon;
//	Properties properties;
//	private final boolean AUTO_COMMIT;

	public BaseApolloDbUtils(boolean autocommit, String resourceName) throws ApolloDatabaseException {
		if (!dataSourceMap.containsKey(resourceName)) {
			try {
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				dataSourceMap.put(resourceName, (DataSource) envCtx.lookup("jdbc/" + resourceName));
			} catch (NamingException e) {
				logger.error("Error initializing db resource:" + e.getMessage());
			}
		}

		datasource = dataSourceMap.get(resourceName);
		try {
			dbcon = datasource.getConnection();
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("SQLException initializing DB Utils: " + ex.getMessage());
		}

	}
//	public BaseApolloDbUtils(File databasePropertiesFile, boolean autocommit) throws IOException {
//		InputStream fis = new FileInputStream(databasePropertiesFile);
//		properties = new Properties();
//		properties.load(fis);
//		fis.close();
//		AUTO_COMMIT = autocommit;
//	}
//
//	public BaseApolloDbUtils(InputStream databasePropertiesInputStream, boolean autocommit) throws IOException {
//
//		properties = new Properties();
//		properties.load(databasePropertiesInputStream);
//		databasePropertiesInputStream.close();
//		AUTO_COMMIT = autocommit;
//	}

	public abstract int addUser(String userName, String userPassword, String userEmail) throws ApolloDatabaseRecordAlreadyExistsException,
			ApolloDatabaseUserPasswordException, ApolloDatabaseException;



	@Override
	public void close() throws ApolloDatabaseException {
		try {
			dbcon.close();
		} catch (SQLException ex) {
			throw new ApolloDatabaseException("Could not close connection");
		}
	}





//	protected void establishDbConn() throws ClassNotFoundException, SQLException {
//
//		String dbClass = properties.getProperty("class");
//		String url = properties.getProperty("url");
//		String user = properties.getProperty("user");
//		String password = properties.getProperty("password");
//
//		try {
//			if (dbcon != null) {
//				dbcon.close();
//			}
//		} catch (SQLException e) {
//			// who cares, making a new one anyway
//		}
//		dbcon = null;
//		try {
//			Class.forName(dbClass);
//			logger.debug("Getting DB connection");
//			dbcon = DriverManager.getConnection(url, user, password);
//			dbcon.setAutoCommit(AUTO_COMMIT);
//		} catch (SQLException e) {
//			throw new SQLException("Error getting connection to database: " + url + " using username " + user
//					+ ".   Specific error was:\n" + e.getMessage());
//		}
//	}
	public Connection getConn() throws ClassNotFoundException, SQLException {

		boolean connIsValid = false;
		try {
			connIsValid = dbcon.isValid(1000);
		} catch (SQLException e1) {
			// who cares, we are making a new one anyway!
		}
		if (!connIsValid) {
			dbcon = datasource.getConnection();
		}

		return dbcon;
	}

//	public void closeConnection() throws ApolloDatabaseException {
//		if (dbcon != null) {
//			try {
//				dbcon.close();
//			} catch (SQLException ex) {
//				throw new ApolloDatabaseException("SQLException attempting to close database connection: "
//						+ ex.getMessage());
//			}
//		}
//	}
	protected String getHashOfUserPasswordAndSalt(String password, String salt) {

		String passwordAndSalt = password + salt + SYSTEM_SALT;
		return md5Utils.getMd5FromString(passwordAndSalt);
	}



	protected String getSaltForPassword() {
		Random random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	static {
		Map<String, String> env = System.getenv();
		String apolloDir = env.get(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (apolloDir != null) {
			if (!apolloDir.endsWith(File.separator)) {
				apolloDir += File.separator;
			}
			APOLLO_DIR = apolloDir;
			logger.info(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:"
					+ APOLLO_DIR);
		} else {
			logger.error(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE
					+ " environment variable not found!");
			APOLLO_DIR = "";
		}

		File saltFile = new File(APOLLO_DIR + SALT_FILE_NAME);
		Scanner saltFileScanner;
		try {
			saltFileScanner = new Scanner(saltFile);
		} catch (FileNotFoundException ex) {
			throw new ExceptionInInitializerError("File \"" + saltFile.getAbsolutePath() + "\" could not be found");
		}

		SYSTEM_SALT = saltFileScanner.nextLine();
		saltFileScanner.close();
	}
}
