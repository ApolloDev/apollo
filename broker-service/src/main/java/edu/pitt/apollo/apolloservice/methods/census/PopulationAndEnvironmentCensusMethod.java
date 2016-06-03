package edu.pitt.apollo.apolloservice.methods.census;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.simulator_service_types.v3_1_0.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.types.v3_1_0.Location;
import edu.pitt.apollo.types.v3_1_0.PopulationAndEnvironmentCensus;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 2:01:58 PM
 * Class: PopulationAndEnvironmentCensusMethod
 * IDE: NetBeans 6.9.1
 */
public abstract class PopulationAndEnvironmentCensusMethod {

    private static List<PopulationAndEnvironmentCensus> censusData;
    private static final String CENSUS_DATA_FILENAME = "fred_census_data.csv";

    private static void readCensusData() throws FileNotFoundException,
            DatatypeConfigurationException {
        File f = new File(ApolloServiceConstants.APOLLO_DIR + "/" + CENSUS_DATA_FILENAME);
        censusData = new ArrayList<PopulationAndEnvironmentCensus>();

        GregorianCalendar cal = new GregorianCalendar();
        XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        date.setMonth(1);
        date.setDay(1);
        date.setYear(2009);

        Scanner s = new Scanner(f);
        boolean onHeader = true;
        while (s.hasNextLine()) {
            if (onHeader) {
                onHeader = false;
                s.nextLine();
                continue;

            }
            String[] cols = s.nextLine().split("\t");

            PopulationAndEnvironmentCensus c = new PopulationAndEnvironmentCensus();
            c.setReferenceDate(date);
            c.setDescription(cols[1]);
            Location l = new Location();
            l.setApolloLocationCode(cols[0]);
            c.setLocation(l);
//            c.setNameOfAdministativeUnit(cols[1]);
            c.setNumberOfPeople(new BigInteger(cols[2]));
            c.setNumberOfSchools(new BigInteger(cols[3]));
            c.setNumberOfWorkplaces(new BigInteger(cols[4]));

            if (cols[0].length() == 2) { // state
                censusData.add(c);
            } else { // county
                PopulationAndEnvironmentCensus stateRoot = getPopulationAndEnvironmentCensusGivenINCITS(cols[0].substring(0, 2));
                stateRoot.getSubLocationCensuses().add(c);
            }
        }
        s.close();
    }

    protected static PopulationAndEnvironmentCensus getPopulationAndEnvironmentCensusGivenINCITS(
            String INCITS) {

        for (PopulationAndEnvironmentCensus c : censusData) {
            if (INCITS.length() == 2) { // state
                if (c.getLocation().getApolloLocationCode().equalsIgnoreCase(INCITS)) {
                    return c;
                }
            } else { // county
                if (c.getLocation().getApolloLocationCode().substring(0, 2).equalsIgnoreCase(INCITS.substring(0, 2))) {
                    // down the rabbit hole
                    for (PopulationAndEnvironmentCensus cc : c.getSubLocationCensuses()) {
                        if (cc.getLocation().getApolloLocationCode().equalsIgnoreCase(INCITS)) {
                            return cc;
                        }
                    }
                }
            }

        }

        return null;
    }

    protected static GetScenarioLocationCodesSupportedBySimulatorResult getScenarioLocationCodesSupportedBySimulator() {
        GetScenarioLocationCodesSupportedBySimulatorResult res = new GetScenarioLocationCodesSupportedBySimulatorResult();
        for (PopulationAndEnvironmentCensus c : censusData) {
            res.getLocationCodes().add(c.getLocation().getApolloLocationCode());
            for (PopulationAndEnvironmentCensus cc : c.getSubLocationCensuses()) {
                res.getLocationCodes().add(
                        cc.getLocation().getApolloLocationCode());
            }
        }

        return res;
    }

    static {
        try {
            try {
                readCensusData();
            } catch (DatatypeConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
