package edu.pitt.apollo.apollolocationservicesdk.utilities;

import edu.pitt.apollo.apollolocationservicesdk.interfaces.ApolloLocationServiceInterface;
import edu.pitt.apollo.apollolocationservicesdk.types.ApolloLocationServiceFeature;
import edu.pitt.apollo.types.v4_0_1.SpatialGranularityEnum;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nem41 on 1/25/17.
 */
public class LocationNameUtility {

    private static String formatCountry(String countryName) {
        String name = countryName.replace(" (the)", "");
        name = name.replaceAll("\\(see.*", "");
        String[] nameArray = name.split("\\(the |\\)|,");
        if (nameArray.length > 1) {
            name = nameArray[1] + " " + nameArray[0];
        }
        return name;
    }

    private static void buildLineage(Set<String> adminLevelOne, Set<String> adminLevelTwo,
                                     ApolloLocationServiceFeature feature, ApolloLocationServiceInterface service) {

        if (feature == null) {
            return;
        }

        if(!feature.getLocationTypeName().equals("Planet") && !feature.getLocationTypeName().equals("Continent") && !feature.getLocationTypeName().equals("Sub-Continent Region")) {
            String name = feature.getLocationName();
            SpatialGranularityEnum spatialGranularity = feature.getAdminLevel();

            if (spatialGranularity.equals(SpatialGranularityEnum.ADMIN_2)) {
                String formattedName = formatCountry(name);
                adminLevelTwo.add(formattedName);
            } else if (spatialGranularity.equals(SpatialGranularityEnum.ADMIN_1)) {
                String formattedName = formatCountry(name);
                adminLevelOne.add(formattedName);
            }

            String encompassingCode = feature.getEncompassingRegionCode();
            ApolloLocationServiceFeature encompassingFeature = service.getFeatureFromLocationCode(encompassingCode);
            buildLineage(adminLevelOne, adminLevelTwo, encompassingFeature, service);
        }
    }


    public static String getLocationDescription(List<String> adminLocationList, ApolloLocationServiceInterface apolloLocationService) {
        StringBuilder sb = new StringBuilder();
        Set<String> adminLevelOne = new HashSet<String>();
        Set<String> adminLevelTwo = new HashSet<String>();
//        ArrayList<Lineage> lineageList = new ArrayList<>();
        ApolloLocationServiceFeature baseFeature = null;
        for (String locationCode : adminLocationList) {
            baseFeature = apolloLocationService.getFeatureFromLocationCode(locationCode);
            buildLineage(adminLevelOne, adminLevelTwo, baseFeature, apolloLocationService);
//            lineageList = apolloLocationSpatialInformationCache.getLineageMap().get(locationCode);
//            if (lineageList == null) {
//                getApolloLSNameAndPolygonEntry(locationCode, true);
//                lineageList = apolloLocationSpatialInformationCache.getLineageMap().get(locationCode);
//            }
//            if (lineageList != null) {
//                for (Lineage lineage : lineageList) {
//                    if (lineage.getAdminLevel().equals("2")) {
//                        String name = formatCountry(lineage.getName());
//                        adminLevelTwo.add(name);
//                    }
//                    if (lineage.getAdminLevel().equals("1")) {
//                        String name = formatCountry(lineage.getName());
//                        adminLevelOne.add(name);
//                    }
//                }
//            } else {
//                sb.append(", ");
//                sb.append(locationCode);
//                return sb.toString();
//            }
        }

        if (adminLocationList.size() == 1 && baseFeature != null && baseFeature.getAdminLevel() == SpatialGranularityEnum.ADMIN_3) {
            sb.append(baseFeature.getLocationName()).append(", ");
        }

        Object[] oneArray = adminLevelOne.toArray();
//		used to display admin level 2
        if (adminLevelTwo.size() == 1) {
            Object[] twoArray = adminLevelTwo.toArray();
            sb.append(twoArray[0].toString());
            sb.append(", ");
        }
        int index = 0;
        for (Object country : oneArray) {
            if (index > 0) {
                sb.append(" and ");
            }

            sb.append(country.toString());
            index++;
        }

//        if (lineageList.get(0).getAdminLevel().equals("3") && !lineageList.get(0).getName().contains("Reston")) {
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append(", ");
//            stringBuilder.append(formatCountry(lineageList.get(0).getName()));
//            stringBuilder.append(sb);
//            return stringBuilder.toString();
//        }


        return sb.toString();
    }

}
