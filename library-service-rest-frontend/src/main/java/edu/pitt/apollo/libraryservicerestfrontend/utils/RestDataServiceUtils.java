package edu.pitt.apollo.libraryservicerestfrontend.utils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

public class RestDataServiceUtils {

    static HashMap<String, String> locationCodeMap = new HashMap<>();
    static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
    //static Properties configurationProperties = null;


    public static List<BigInteger> getListOfGroupIds(String listAsString)
    {

        String[] groupIdsSplit = listAsString.split(",");
        List<BigInteger> groupIdsAsList = new ArrayList<>();
        for(String idAsString : groupIdsSplit)
        {
            groupIdsAsList.add(new BigInteger(idAsString));
        }
        return groupIdsAsList;
    }

}
