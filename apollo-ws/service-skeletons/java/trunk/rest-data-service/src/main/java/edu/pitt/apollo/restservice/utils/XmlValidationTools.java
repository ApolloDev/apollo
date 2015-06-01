package edu.pitt.apollo.restservice.utils;

//import edu.pitt.apollo.restservice.controller.HomeController;
import edu.pitt.apollo.restservice.exceptions.SchemaDefinitionNotFoundException;
import edu.pitt.apollo.restservice.exceptions.XsdNotFoundException;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dcs27 on 5/5/15.
 */
public class XmlValidationTools {

    public static boolean validateXMLSchemaXml(String xml) throws SchemaDefinitionNotFoundException, XsdNotFoundException{

//        ClassLoader cLoader = HomeController.class.getClassLoader();

        try{
            getSchemaDefinitionFromXml(xml);
        }catch(SchemaDefinitionNotFoundException sdnf)
        {
            throw sdnf;

        } catch (XsdNotFoundException xsdNotFound) {
            xsdNotFound.printStackTrace();
            throw xsdNotFound;
        }
        return true;
    }

    public static String getSchemaDefinitionFromXml(String xml) throws SchemaDefinitionNotFoundException, XsdNotFoundException

    {
        String xsdFilePath="";
        if(xml.contains("types.apollo.pitt.edu"))
        {
            System.out.println("Success: Schema definition found in provided XML.");

            xsdFilePath = getXsdVersionFromXml(xml);

        }
        else{
           SchemaDefinitionNotFoundException sdnf = new SchemaDefinitionNotFoundException();
            throw sdnf;
        }
        return xsdFilePath;
    }

    private static String getXsdVersionFromXml(String xml) throws XsdNotFoundException
    {
        String xsdVersion = "";
        String typesDeclarationFromXml = "";
        Pattern pattern = Pattern.compile("types.apollo.pitt.edu\\/[a-zA-Z0-9-_]*/");
        Matcher matcher = pattern.matcher(xml);

        if(matcher.find())
        {
            typesDeclarationFromXml = matcher.group(0);
        }

        String[] typesDeclarationSplit = typesDeclarationFromXml.split("/");

        xsdVersion = typesDeclarationSplit[1];

        String xsdFilePath = getFilePathForXsd(xsdVersion);

        return xsdFilePath;
    }

    private static String getFilePathForXsd(String xsdVersion) throws XsdNotFoundException
    {
        String xsdFilePath = "";
        ClassLoader cLoader = XmlValidationTools.class.getClassLoader();

        try{

            xsdVersion = xsdVersion.replace("_",".");

            String urlForFileAsString = "./apollo-xsd/apollo-types_"+xsdVersion+".xsd";
            String[] urlFileAsStringSplit = urlForFileAsString.split("_");


            URL xsdUrl = cLoader.getResource(urlForFileAsString);
            xsdFilePath= xsdUrl.getPath();
        }
        catch(Exception e)
        {
            throw new XsdNotFoundException(xsdVersion);
        }
        return xsdFilePath;
    }
}
