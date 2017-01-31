package edu.pitt.apollo.apollolocationservicesdk;

import edu.pitt.apollo.apollolocationservicesdk.interfaces.ApolloLocationServiceAsFileInterface;
import edu.pitt.apollo.apollolocationservicesdk.types.ApolloLocationServiceEntry;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by mas400 on 8/23/16.
 */
public class AbstractApolloLocationServiceAsFile implements ApolloLocationServiceAsFileInterface {
    protected HashMap<String, List<ApolloLocationServiceEntry>> nameToEntryMap;
    protected HashMap<Integer, ApolloLocationServiceEntry> codeToEntryMap;
    protected HashMap<Integer, String> locationTypeIdToName;
    protected HashMap<Character, List<ApolloLocationServiceEntry>> letterToEntryMap;
    public static boolean cacheIsDirty = false;

    @Override
    public String getGeoJsonFromLocationCode(int locationCode) {
        if (codeToEntryMap.containsKey(locationCode)) {
            return apolloLocationServiceEntryToGeoJson(codeToEntryMap.get(locationCode));
        }
        return null;
    }

    @Override
    public String getGeoJsonFromLocationName(String locationName, String encompassingRegion) {
        if (nameToEntryMap.containsKey(locationName)) {
            for (ApolloLocationServiceEntry entry : nameToEntryMap.get(locationName)) {
                int encompassingRegionCode = entry.getEncompassingRegionCode();
                String parent = "";
                if (encompassingRegionCode > 0)
                    parent = ApolloLocationService.stripAccents(codeToEntryMap.get(encompassingRegionCode).getLocationName());

                double distance = getDistanceBetweenLocations(parent, encompassingRegion);
                if (distance > 0.9) {
                    return apolloLocationServiceEntryToGeoJson(entry);
                }
            }
        } else {
            List<ApolloLocationServiceEntry> entriesByLetter = letterToEntryMap.get(locationName.toUpperCase().charAt(0));
            for (ApolloLocationServiceEntry entry : entriesByLetter) {
                String nameInMap = ApolloLocationService.stripAccents(entry.getLocationName());

                double distance = StringUtils.getJaroWinklerDistance(nameInMap, locationName);
                if (distance > 0.9) {
                    String geoJson = getGeoJsonFromLocationName(nameInMap, encompassingRegion);
                    if (geoJson != null) {
                        nameToEntryMap.put(locationName, nameToEntryMap.get(nameInMap));
                        letterToEntryMap.put(locationName.toUpperCase().charAt(0), nameToEntryMap.get(nameInMap));
                        cacheIsDirty = true;
                        return geoJson;
                    }
                }
            }
        }

        return null;
    }

    public void addToEntryMapByLetter(String locationName, ApolloLocationServiceEntry entry) {
        List<ApolloLocationServiceEntry> entries = new ArrayList<>();
        char firstLetter = locationName.toUpperCase().charAt(0);
        if (letterToEntryMap.containsKey(firstLetter)) {
            entries = letterToEntryMap.get(firstLetter);
        }
        entries.add(entry);
        letterToEntryMap.put(firstLetter, entries);
    }

    private static double getDistanceBetweenLocations(String locationA, String locationB) {
        if (locationA.contains(locationB))
            return 1.0;
        return StringUtils.getJaroWinklerDistance(locationA, locationB);
    }

    @Override
    public void addLocationCode(int locationCode, ApolloLocationServiceEntry entry) {
        codeToEntryMap.put(locationCode, entry);
    }

    @Override
    public void addLocationName(String locationName, ApolloLocationServiceEntry entry) {
        List<ApolloLocationServiceEntry> entries = new ArrayList<>();
        if (nameToEntryMap.containsKey(locationName)) {
            entries = nameToEntryMap.get(locationName);
        }
        entries.add(entry);

        nameToEntryMap.put(locationName, entries);
    }

    public void setLocationTypeNameAndId(int locationTypeId, String locationTypeName) {
        locationTypeIdToName.put(locationTypeId, locationTypeName);
    }

    public String getLocationTypeNameFromId(int locationTypeId) {
        return locationTypeIdToName.get(locationTypeId);
    }

    public List<Integer> getEncompassingRegions(int currentLocationCode) {
        List<Integer> encompassingRegions = new ArrayList<>();
        while (true) {
            ApolloLocationServiceEntry apolloLocationServiceEntry = codeToEntryMap.get(currentLocationCode);
            if (apolloLocationServiceEntry.getEncompassingRegionCode() > 0) {
                encompassingRegions.add(apolloLocationServiceEntry.getEncompassingRegionCode());
                currentLocationCode = apolloLocationServiceEntry.getEncompassingRegionCode();
            } else
                break;
        }
        return encompassingRegions;
    }

    public String apolloLocationServiceEntryToGeoJson(ApolloLocationServiceEntry entry) {

        StringBuilder geoJson = new StringBuilder();

        List<Integer> encompassingRegions = getEncompassingRegions(entry.getApolloLocationCode());

        geoJson.append("{\n\t\"type\": \"FeatureCollection\",\n");
        geoJson.append("\t\"features\": [\n");

        geoJson.append("{\n\t\"type\": \"Feature\",\n\t\"properties\": {\n");
        geoJson.append("\t\t\"gid\": \"").append(entry.getApolloLocationCode()).append("\",\n");
        geoJson.append("\t\t\"locationTypeName\": \"").append(getLocationTypeNameFromId(entry.getLocationTypeId())).append("\",\n");
        geoJson.append("\t\t\"lineage\": [\n");

        for (int i = encompassingRegions.size() - 1; i >= 0; i--) {
            int encompassingRegionId = encompassingRegions.get(i);

            ApolloLocationServiceEntry encompasingRegionEntry = codeToEntryMap.get(encompassingRegionId);
            geoJson.append("\t\t\t{\n");
            geoJson.append("\t\t\t\t\"name\": \"").append(encompasingRegionEntry.getLocationName()).append("\",\n");
            geoJson.append("\t\t\t\t\"gid\": \"").append(encompasingRegionEntry.getApolloLocationCode()).append("\",\n");
            geoJson.append("\t\t\t\t\"locationTypeName\": \"").append(getLocationTypeNameFromId(encompasingRegionEntry.getLocationTypeId())).append("\"\n");
            geoJson.append("\t\t\t},\n");
        }
        if (encompassingRegions.size() > 0)
            geoJson.deleteCharAt(geoJson.length() - 2);
        geoJson.append("\t\t],\n");

        geoJson.append("\t\t\"name\": \"").append(entry.getLocationName()).append("\",\n");
        geoJson.append("\t\t\"parentGid\": \"").append(entry.getEncompassingRegionCode()).append("\"\n");

        geoJson.append("\t}\n}");
        geoJson.append("]\n}");

        return geoJson.toString();
    }
}
