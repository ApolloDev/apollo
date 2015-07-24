package edu.pitt.apollo.db;


import edu.pitt.apollo.utilities.JsonUtils;
import edu.pitt.apollo.utilities.Md5Utils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseRecordAlreadyExistsException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseUserPasswordException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;

import static edu.pitt.apollo.GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE;
import static edu.pitt.apollo.db.ApolloDbUtils.logger;

import edu.pitt.apollo.types.v3_0_2.AaaDummyType;
import edu.pitt.apollo.types.v3_0_2.AbioticEcosystem;
import edu.pitt.apollo.types.v3_0_2.AbioticEcosystemCensus;
import edu.pitt.apollo.types.v3_0_2.AbioticEcosystemElementCensusDescription;
import edu.pitt.apollo.types.v3_0_2.AbioticEcosystemEnum;
import edu.pitt.apollo.types.v3_0_2.AgeGroupEfficacy;
import edu.pitt.apollo.types.v3_0_2.AgeRangeCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.AntiviralTreatment;
import edu.pitt.apollo.types.v3_0_2.AntiviralTreatmentEfficacy;
import edu.pitt.apollo.types.v3_0_2.ApolloIndexableItem;
import edu.pitt.apollo.types.v3_0_2.ApolloIndexableItemTypeEnum;
import edu.pitt.apollo.types.v3_0_2.ApolloPathogenCode;
import edu.pitt.apollo.types.v3_0_2.ArrayAxis;
import edu.pitt.apollo.types.v3_0_2.ArrayDimensionsDefinition;
import edu.pitt.apollo.types.v3_0_2.BaseCaseResult;
import edu.pitt.apollo.types.v3_0_2.BayesianNetwork;
import edu.pitt.apollo.types.v3_0_2.BayesianNetworkStructureType;
import edu.pitt.apollo.types.v3_0_2.BehaviorEnum;
import edu.pitt.apollo.types.v3_0_2.BioticEcosystem;
import edu.pitt.apollo.types.v3_0_2.BorderControlStrategy;
import edu.pitt.apollo.types.v3_0_2.CartesianCircleLocationDefinition;
import edu.pitt.apollo.types.v3_0_2.CaseCount;
import edu.pitt.apollo.types.v3_0_2.CaseCountArray;
import edu.pitt.apollo.types.v3_0_2.CaseCountCategory;
import edu.pitt.apollo.types.v3_0_2.CaseDefinition;
import edu.pitt.apollo.types.v3_0_2.CaseDefinitionEnum;
import edu.pitt.apollo.types.v3_0_2.CaseList;
import edu.pitt.apollo.types.v3_0_2.CaseQuarantineControlStrategy;
import edu.pitt.apollo.types.v3_0_2.CaseRecord;
import edu.pitt.apollo.types.v3_0_2.CaseRecordCategoricalVariable;
import edu.pitt.apollo.types.v3_0_2.CaseRecordCategoricalVariables;
import edu.pitt.apollo.types.v3_0_2.CaseVariableAndValue;
import edu.pitt.apollo.types.v3_0_2.Category;
import edu.pitt.apollo.types.v3_0_2.CategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.Census;
import edu.pitt.apollo.types.v3_0_2.CensusData;
import edu.pitt.apollo.types.v3_0_2.ClaraDensityDependentMortalityFunction;
import edu.pitt.apollo.types.v3_0_2.CompartmentalModelPopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v3_0_2.ConditionalIndividualBehavior;
import edu.pitt.apollo.types.v3_0_2.ConditionalProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.ConditionalProbabilityTable;
import edu.pitt.apollo.types.v3_0_2.ConditioningVariable;
import edu.pitt.apollo.types.v3_0_2.ConditioningVariableEnum;
import edu.pitt.apollo.types.v3_0_2.ContactDefinition;
import edu.pitt.apollo.types.v3_0_2.ContactDefinitionEnum;
import edu.pitt.apollo.types.v3_0_2.ContactModelForCommunity;
import edu.pitt.apollo.types.v3_0_2.ContactModelForHousehold;
import edu.pitt.apollo.types.v3_0_2.ContactModelForPlace;
import edu.pitt.apollo.types.v3_0_2.ContactModelForSetting;
import edu.pitt.apollo.types.v3_0_2.ContainerReductionControlStrategy;
import edu.pitt.apollo.types.v3_0_2.ContaminatedThingCensus;
import edu.pitt.apollo.types.v3_0_2.ContaminatedThingCensusData;
import edu.pitt.apollo.types.v3_0_2.Contamination;
import edu.pitt.apollo.types.v3_0_2.ContaminationAcquisition;
import edu.pitt.apollo.types.v3_0_2.ContinuousParametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.ContinuousUniformDistribution;
import edu.pitt.apollo.types.v3_0_2.ControlStrategyTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v3_0_2.CountType;
import edu.pitt.apollo.types.v3_0_2.DecisionAlternative;
import edu.pitt.apollo.types.v3_0_2.DecisionAnalysis;
import edu.pitt.apollo.types.v3_0_2.DevelopmentalStageEnum;
import edu.pitt.apollo.types.v3_0_2.DiscreteNonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.DiscreteParametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.DiseaseOutcomeCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.DiseaseOutcomeEnum;
import edu.pitt.apollo.types.v3_0_2.DiseaseOutcomeWithLocationDateTime;
import edu.pitt.apollo.types.v3_0_2.DiseaseOutcomeWithProbability;
import edu.pitt.apollo.types.v3_0_2.DiseaseSurveillanceCapability;
import edu.pitt.apollo.types.v3_0_2.DiseaseSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v3_0_2.Distance;
import edu.pitt.apollo.types.v3_0_2.DoubleCount;
import edu.pitt.apollo.types.v3_0_2.DrugTreatment;
import edu.pitt.apollo.types.v3_0_2.DrugTreatmentEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types.v3_0_2.Duration;
import edu.pitt.apollo.types.v3_0_2.Ecosystem;
import edu.pitt.apollo.types.v3_0_2.Epidemic;
import edu.pitt.apollo.types.v3_0_2.EpidemicPeriod;
import edu.pitt.apollo.types.v3_0_2.EpidemicPeriodBoundaryDefinitionEnum;
import edu.pitt.apollo.types.v3_0_2.ExpectedUtility;
import edu.pitt.apollo.types.v3_0_2.FixedDuration;
import edu.pitt.apollo.types.v3_0_2.FractionOfThingContaminated;
import edu.pitt.apollo.types.v3_0_2.GammaDistribution;
import edu.pitt.apollo.types.v3_0_2.GeNIEXMLType;
import edu.pitt.apollo.types.v3_0_2.GenderCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.GenderEnum;
import edu.pitt.apollo.types.v3_0_2.GesInfectiousnessParameterSet;
import edu.pitt.apollo.types.v3_0_2.GesParametersForContactAndTransmission;
import edu.pitt.apollo.types.v3_0_2.Individual;
import edu.pitt.apollo.types.v3_0_2.IndividualBehavior;
import edu.pitt.apollo.types.v3_0_2.IndividualHumanBehavior;
import edu.pitt.apollo.types.v3_0_2.IndividualLifeCycle;
import edu.pitt.apollo.types.v3_0_2.IndividualMosquitoBehavior;
import edu.pitt.apollo.types.v3_0_2.IndividualMosquitoReproduction;
import edu.pitt.apollo.types.v3_0_2.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v3_0_2.IndividualTreatmentEnum;
import edu.pitt.apollo.types.v3_0_2.IndoorResidualSprayingVectorControlStrategy;
import edu.pitt.apollo.types.v3_0_2.Infection;
import edu.pitt.apollo.types.v3_0_2.InfectionAcquisitionFromContaminatedThing;
import edu.pitt.apollo.types.v3_0_2.InfectionAcquisitionFromInfectiousHost;
import edu.pitt.apollo.types.v3_0_2.InfectionStateEnum;
import edu.pitt.apollo.types.v3_0_2.InfectiousDisease;
import edu.pitt.apollo.types.v3_0_2.InfectiousDiseaseControlStrategy;
import edu.pitt.apollo.types.v3_0_2.InfectiousDiseaseDecisionModel;
import edu.pitt.apollo.types.v3_0_2.InfectiousDiseaseScenario;
import edu.pitt.apollo.types.v3_0_2.IntegerCount;
import edu.pitt.apollo.types.v3_0_2.Interval;
import edu.pitt.apollo.types.v3_0_2.IntervalBoundaryDefinitionEnum;
import edu.pitt.apollo.types.v3_0_2.LabTestAndResult;
import edu.pitt.apollo.types.v3_0_2.LarvicideControlStrategy;
import edu.pitt.apollo.types.v3_0_2.LibraryItem;
import edu.pitt.apollo.types.v3_0_2.LifeStageWithDurationAndMortality;
import edu.pitt.apollo.types.v3_0_2.Location;
import edu.pitt.apollo.types.v3_0_2.LocationDefinition;
import edu.pitt.apollo.types.v3_0_2.LocationPolygon;
import edu.pitt.apollo.types.v3_0_2.LogNormalDistribution;
import edu.pitt.apollo.types.v3_0_2.MeanMedianMinimumMaximum;
import edu.pitt.apollo.types.v3_0_2.MeanWithConfidenceInterval;
import edu.pitt.apollo.types.v3_0_2.MeanWithStandardDeviation;
import edu.pitt.apollo.types.v3_0_2.MortalityFunction;
import edu.pitt.apollo.types.v3_0_2.MultiGeometry;
import edu.pitt.apollo.types.v3_0_2.NamedMultiGeometry;
import edu.pitt.apollo.types.v3_0_2.NamedPrioritizationSchemeEnum;
import edu.pitt.apollo.types.v3_0_2.NonApolloParameter;
import edu.pitt.apollo.types.v3_0_2.NonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.OccupationEnum;
import edu.pitt.apollo.types.v3_0_2.OperatorEnum;
import edu.pitt.apollo.types.v3_0_2.OvipositionSiteCensus;
import edu.pitt.apollo.types.v3_0_2.ParameterValue;
import edu.pitt.apollo.types.v3_0_2.ParametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.PlaceCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.PlaceClosureControlStrategy;
import edu.pitt.apollo.types.v3_0_2.PlaceEnum;
import edu.pitt.apollo.types.v3_0_2.PlaceVisited;
import edu.pitt.apollo.types.v3_0_2.Population;
import edu.pitt.apollo.types.v3_0_2.PopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v3_0_2.PopulationCensusDescription;
import edu.pitt.apollo.types.v3_0_2.PopulationInfectionAndImmunityCensus;
import edu.pitt.apollo.types.v3_0_2.PopulationInfectionAndImmunityCensusData;
import edu.pitt.apollo.types.v3_0_2.PopulationInfectionAndImmunityCensusDataCell;
import edu.pitt.apollo.types.v3_0_2.PopulationInfectionSurvey;
import edu.pitt.apollo.types.v3_0_2.PopulationSerologySurvey;
import edu.pitt.apollo.types.v3_0_2.PopulationStratificationEnum;
import edu.pitt.apollo.types.v3_0_2.PopulationTreatmentCensus;
import edu.pitt.apollo.types.v3_0_2.PopulationTreatmentCensusData;
import edu.pitt.apollo.types.v3_0_2.PopulationTreatmentCensusDataCell;
import edu.pitt.apollo.types.v3_0_2.PreEpidemicEcosystemCensus;
import edu.pitt.apollo.types.v3_0_2.ProbabilisticParameter;
import edu.pitt.apollo.types.v3_0_2.ProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.ProbabilityValuePair;
import edu.pitt.apollo.types.v3_0_2.PrototypicalProbabilityFunction;
import edu.pitt.apollo.types.v3_0_2.Rate;
import edu.pitt.apollo.types.v3_0_2.RealDateSpanCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.RealTimePointCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.RealTimeSpanCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.Reference;
import edu.pitt.apollo.types.v3_0_2.RelativeRiskDataSet;
import edu.pitt.apollo.types.v3_0_2.ReproductionNumber;
import edu.pitt.apollo.types.v3_0_2.RequesterIdentification;
import edu.pitt.apollo.types.v3_0_2.RingIndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v3_0_2.ScenarioCartesianOrigin;
import edu.pitt.apollo.types.v3_0_2.SeasonalityFunctionParameters;
import edu.pitt.apollo.types.v3_0_2.SensitivityAnalysis;
import edu.pitt.apollo.types.v3_0_2.SensitivityAnalysisResult;
import edu.pitt.apollo.types.v3_0_2.SimulatorTimeRange;
import edu.pitt.apollo.types.v3_0_2.SimulatorTimeSpecification;
import edu.pitt.apollo.types.v3_0_2.SimulatorTypeEnum;
import edu.pitt.apollo.types.v3_0_2.SourceOfInfectionCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.SourceOfInfectionEnum;
import edu.pitt.apollo.types.v3_0_2.SpatialKernelFunctionParameters;
import edu.pitt.apollo.types.v3_0_2.TargetPopulationDefinition;
import edu.pitt.apollo.types.v3_0_2.TargetPopulationEnum;
import edu.pitt.apollo.types.v3_0_2.TargetPriorityPopulation;
import edu.pitt.apollo.types.v3_0_2.TemporalArrayDimensionsDefinition;
import edu.pitt.apollo.types.v3_0_2.TemporalTriggerDefinition;
import edu.pitt.apollo.types.v3_0_2.TimeAxisCategoryLabels;
import edu.pitt.apollo.types.v3_0_2.TimeDefinition;
import edu.pitt.apollo.types.v3_0_2.TimeScaleEnum;
import edu.pitt.apollo.types.v3_0_2.TimeSpanCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.TransmissionProbability;
import edu.pitt.apollo.types.v3_0_2.TransmissionTree;
import edu.pitt.apollo.types.v3_0_2.TravelRestrictionControlStrategy;
import edu.pitt.apollo.types.v3_0_2.Treatment;
import edu.pitt.apollo.types.v3_0_2.TreatmentContraindication;
import edu.pitt.apollo.types.v3_0_2.TreatmentEfficacy;
import edu.pitt.apollo.types.v3_0_2.TreatmentPreventableOutcomeEnum;
import edu.pitt.apollo.types.v3_0_2.TreatmentStateEnum;
import edu.pitt.apollo.types.v3_0_2.TreatmentSurveillanceCapability;
import edu.pitt.apollo.types.v3_0_2.TreatmentSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v3_0_2.TreatmentSystemLogistics;
import edu.pitt.apollo.types.v3_0_2.TriggerDefinition;
import edu.pitt.apollo.types.v3_0_2.UncertainDuration;
import edu.pitt.apollo.types.v3_0_2.UnconditionalProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.UnitOfDistanceEnum;
import edu.pitt.apollo.types.v3_0_2.UnitOfMeasureEnum;
import edu.pitt.apollo.types.v3_0_2.UnitOfTimeEnum;
import edu.pitt.apollo.types.v3_0_2.UtilityFunction;
import edu.pitt.apollo.types.v3_0_2.Vaccination;
import edu.pitt.apollo.types.v3_0_2.VaccinationEfficacyConditionedOnTimeSinceDose;
import edu.pitt.apollo.types.v3_0_2.VaccinationEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types.v3_0_2.VaccinationEfficacyInferred;
import edu.pitt.apollo.types.v3_0_2.VaccinationEfficacyMeasured;
import edu.pitt.apollo.types.v3_0_2.VaccinationEfficacyStudy;
import edu.pitt.apollo.types.v3_0_2.Vaccine;
import edu.pitt.apollo.types.v3_0_2.VaccineContraindications;
import edu.pitt.apollo.types.v3_0_2.VariableCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.VectorControlStrategy;
import edu.pitt.apollo.types.v3_0_2.WeibullDistribution;
import edu.pitt.apollo.types.v3_0_2.WithinGroupTransmissionProbability;
import edu.pitt.apollo.types.v3_0_2.WolbachiaControlStrategy;
import edu.pitt.apollo.types.v3_0_2.WolbachiaReleaseSiteEnum;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
//	Connection dbcon;
//	Properties properties;
//	private final boolean AUTO_COMMIT;

    public BaseApolloDbUtils(boolean autocommit, String resourceName) throws ApolloDatabaseException {
        if (!dataSourceMap.containsKey(resourceName)) {
            try {
                Context initCtx = new InitialContext();
                Context envCtx = (Context) initCtx.lookup("java:comp/env");
                dataSourceMap.put(resourceName, (DataSource) envCtx.lookup("jdbc/" + resourceName));
            } catch (NamingException e) {

                logger.error("Error initializing db resource, falling back to default configuration:" + e.getMessage());
                try {
                    Properties properties = new Properties();
                    properties.load(new BufferedReader(new FileReader(APOLLO_DIR + "database.properties")));

                    com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds
                            = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();

                    ds.setServerName(properties.getProperty("server"));
                    ds.setPortNumber(Integer.valueOf(properties.getProperty("port")));
                    ds.setDatabaseName(properties.getProperty("database_name"));
                    ds.setUser(properties.getProperty("user"));
                    ds.setPassword(properties.getProperty("password"));
                    dataSourceMap.put(resourceName, ds);
                } catch (IOException ex) {
                    logger.error("Fallback failed, error creating default datasource!  Error was: " + ex.getMessage());
                }
            }

        }
        datasource = dataSourceMap.get(resourceName);
    }
//		try {
//			dbcon = datasource.getConnection();
//			connectionsOpen.addAndGet(1);
//			System.out.println("connections open: " + connectionsOpen.intValue());
//		} catch (SQLException ex) {
//			throw new ApolloDatabaseException("SQLException initializing DB Utils: " + ex.getMessage());
//		}


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
//	public Connection getConn() throws ClassNotFoundException, SQLException {
//
//		boolean connIsValid = false;
//		try {
//			connIsValid = dbcon.isValid(1000);
//		} catch (SQLException e1) {
//			// who cares, we are making a new one anyway!
//		}
//		if (!connIsValid) {
//			dbcon = datasource.getConnection();
//		}
//
//		return dbcon;
//	}

    @Override
    public void close() throws ApolloDatabaseException {
//		try {
//			dbcon.close();
//			connectionsOpen.addAndGet(-1);
//		} catch (SQLException ex) {
//			throw new ApolloDatabaseException("Could not close connection");
//		}
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
    }    static {
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
