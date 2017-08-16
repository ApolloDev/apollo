package edu.pitt.apollo.libraryclient;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseExplicitException;
import edu.pitt.apollo.db.exceptions.library.NoLibraryItemException;
import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.library_service_types.v4_0_2.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v4_0_2.CatalogEntry;
import edu.pitt.apollo.library_service_types.v4_0_2.LibraryCollection;
import edu.pitt.apollo.library_service_types.v4_0_2.LibraryItemContainer;
import edu.pitt.apollo.types.v4_0_2.IndividualTreatmentControlMeasure;
import edu.pitt.apollo.types.v4_0_2.InfectiousDiseaseScenario;
import edu.pitt.apollo.types.v4_0_2.PlaceClosureControlMeasure;

import javax.xml.datatype.DatatypeConfigurationException;
import java.text.ParseException;

/**
 * Created by nem41 on 1/5/17.
 */
public class LibraryDBClient {

    private static final String USER = "auto_user";
    private static final int ROLE = 2;

    private static AddLibraryItemContainerResult addInfectiousDiseaseScenario(LibraryDbUtils dbUtils) throws DatatypeConfigurationException, ParseException, UserNotAuthorizedException, ApolloDatabaseException {
        InfectiousDiseaseScenario scenario = ExampleInfectiousDiseaseScenario.getScenario();
        LibraryItemContainer lic = new LibraryItemContainer();
        lic.setLibraryItem(scenario);

        CatalogEntry entry = new CatalogEntry();
        entry.setDisplayName("2009 H1N1 Allegheny County R0 = 1.3");
        entry.setTextualIdentifier("2009 H1N1 Allegheny County R0 = 1.3");
        lic.setCatalogEntry(entry);

        return dbUtils.addLibraryItem(lic, USER, "Adding H1N1 scenario for Allegheny County in 2009", ROLE);
    }

    private static int updateInfectiousDiseaseScenario(LibraryDbUtils dbUtils) throws DatatypeConfigurationException, ParseException, UserNotAuthorizedException, ApolloDatabaseException, NoLibraryItemException, ApolloDatabaseExplicitException {
        InfectiousDiseaseScenario scenario = ExampleInfectiousDiseaseScenario.getScenario();
        LibraryItemContainer lic = new LibraryItemContainer();
        lic.setLibraryItem(scenario);

        CatalogEntry entry = new CatalogEntry();
        entry.setDisplayName("2009 H1N1 Allegheny County R0 = 1.3");
        entry.setTextualIdentifier("2009 H1N1 Allegheny County R0 = 1.3");
        lic.setCatalogEntry(entry);

        return dbUtils.updateLibraryItem(101, ROLE, lic, USER, "Adding H1N1 scenario for Allegheny County in 2009");
    }

    private static AddLibraryItemContainerResult addInfectiousDiseaseScenarioCollection(LibraryDbUtils dbUtils, int member) throws UserNotAuthorizedException, ApolloDatabaseException {
        LibraryCollection collection = new LibraryCollection();
        collection.setDescription("H1N1 Infectious Disease Scenarios");
        collection.setJavaClassNameOfMembers("edu.pitt.apollo.types.v4_0_2.InfectiousDiseaseScenario");
        collection.setName("H1N1 Infectious Disease Scenarios");
        collection.getMembersOfCollection().add(member);

        LibraryItemContainer lic = new LibraryItemContainer();
        lic.setLibraryItem(collection);

        CatalogEntry entry = new CatalogEntry();
        entry.setDisplayName("H1N1 Infectious Disease Scenarios");
        entry.setTextualIdentifier("H1N1 Infectious Disease Scenarios");
        entry.setJavaClassName("edu.pitt.apollo.library_service_types.v4_0_2.LibraryCollection");
        lic.setCatalogEntry(entry);

        return dbUtils.addLibraryItem(lic, USER, "Adding H1N1 Infectious Disease Scenario Collections", ROLE);
    }

    private static AddLibraryItemContainerResult addVaccinationControlMeasureToLibrary(LibraryDbUtils dbUtils) throws UserNotAuthorizedException, ApolloDatabaseException {

        IndividualTreatmentControlMeasure strategy = ExampleVaccinationControlStrategy.getStrategy();
        LibraryItemContainer lic = new LibraryItemContainer();
        lic.setLibraryItem(strategy);

        CatalogEntry entry = new CatalogEntry();
        entry.setDisplayName("The vaccination control strategy used by Allegheny County to mitigate the spread of H1N1 for the 2009 Influenza season.");
        entry.setTextualIdentifier("The vaccination control strategy used by Allegheny County to mitigate the spread of H1N1 for the 2009 Influenza season.");

        lic.setCatalogEntry(entry);

        return dbUtils.addLibraryItem(lic, USER, "Adding vaccination control strategy", ROLE);

    }

    private static int updateVaccinationControlMeasureToLibrary(LibraryDbUtils dbUtils, int urn) throws UserNotAuthorizedException, NoLibraryItemException, ApolloDatabaseException, ApolloDatabaseExplicitException {

        IndividualTreatmentControlMeasure strategy = ExampleVaccinationControlStrategy.getStrategy();
        LibraryItemContainer lic = new LibraryItemContainer();
        lic.setLibraryItem(strategy);

        CatalogEntry entry = new CatalogEntry();
        entry.setDisplayName("The vaccination control strategy used by Allegheny County to mitigate the spread of H1N1 for the 2009 Influenza season.");
        entry.setTextualIdentifier("The vaccination control strategy used by Allegheny County to mitigate the spread of H1N1 for the 2009 Influenza season.");

        lic.setCatalogEntry(entry);

        return dbUtils.updateLibraryItem(urn, ROLE, lic, USER, "Adding vaccination control strategy");

    }

    private static AddLibraryItemContainerResult addAntiviralControlMeasureToLibrary(LibraryDbUtils dbUtils) throws UserNotAuthorizedException, ApolloDatabaseException {

        IndividualTreatmentControlMeasure strategy = ExampleAntiviralControlStrategy.getAntiviralControlMeasure();
        LibraryItemContainer lic = new LibraryItemContainer();
        lic.setLibraryItem(strategy);

        CatalogEntry entry = new CatalogEntry();
        entry.setDisplayName("2009 Antiviral Control Strategy (Tamiflu)");
        entry.setTextualIdentifier("2009 Antiviral Control Strategy (Tamiflu)");

        lic.setCatalogEntry(entry);

        return dbUtils.addLibraryItem(lic, USER, "Adding antiviral control strategy", ROLE);

    }

    private static AddLibraryItemContainerResult addIndividualSchoolClosureControlMeasure(LibraryDbUtils dbUtils) throws UserNotAuthorizedException, ApolloDatabaseException {

        PlaceClosureControlMeasure strategy = ExampleSchoolClosureControlStrategy.getIndividualSchoolsControlMeasure();
        LibraryItemContainer lic = new LibraryItemContainer();
        lic.setLibraryItem(strategy);

        CatalogEntry entry = new CatalogEntry();
        entry.setDisplayName("A school closure control strategy where schools with high disease activity are closed to mitigate the spread of an infectious disease.");
        entry.setTextualIdentifier("A school closure control strategy where schools with high disease activity are closed to mitigate the spread of an infectious disease.");

        lic.setCatalogEntry(entry);

        return dbUtils.addLibraryItem(lic, USER, "Adding individual school control strategy", ROLE);

    }

    private static AddLibraryItemContainerResult addAllSchoolClosureControlMeasure(LibraryDbUtils dbUtils) throws UserNotAuthorizedException, ApolloDatabaseException {

        PlaceClosureControlMeasure strategy = ExampleSchoolClosureControlStrategy.getAllSchoolsControlMeasure();
        LibraryItemContainer lic = new LibraryItemContainer();
        lic.setLibraryItem(strategy);

        CatalogEntry entry = new CatalogEntry();
        entry.setDisplayName("A school closure control strategy where all schools in the jurisdiction are closed to mitigate the spread of an infectious disease.");
        entry.setTextualIdentifier("A school closure control strategy where all schools in the jurisdiction are closed to mitigate the spread of an infectious disease.");

        lic.setCatalogEntry(entry);

        return dbUtils.addLibraryItem(lic, USER, "Adding all schools control strategy", ROLE);

    }

    private static void setReleaseVersion(LibraryDbUtils dbUtils, int urn, int version) throws UserNotAuthorizedException, ApolloDatabaseExplicitException, ApolloDatabaseException, NoLibraryItemException {
        dbUtils.setReleaseVersion(urn, version, ROLE, USER, "Releasing H1N1 scenario for Allegheny County in 2009");
    }

    public static void main(String[] args) throws ApolloDatabaseException, ParseException, DatatypeConfigurationException, UserNotAuthorizedException, NoLibraryItemException, ApolloDatabaseExplicitException {
        LibraryDbUtils dbUtils = new LibraryDbUtils();

//        updateInfectiousDiseaseScenario(dbUtils);
        AddLibraryItemContainerResult result = addInfectiousDiseaseScenario(dbUtils);
        setReleaseVersion(dbUtils, result.getUrn(), 1);
        result = addInfectiousDiseaseScenarioCollection(dbUtils, result.getUrn());
        setReleaseVersion(dbUtils, result.getUrn(), 1);
//        addVaccinationControlMeasureToLibrary(dbUtils);
//        int version = updateVaccinationControlMeasureToLibrary(dbUtils, 103);
//        AddLibraryItemContainerResult result = addAntiviralControlMeasureToLibrary(dbUtils);
//        AddLibraryItemContainerResult result = addAllSchoolClosureControlMeasure(dbUtils);
    }



}
