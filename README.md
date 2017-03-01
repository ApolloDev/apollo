
<img align="left" src="https://github.com/ApolloDev/apollo/blob/wiki/img/apollo_logo.png"/>
#About
The goal of this project is to increase programmatic access to epidemic simulators, which are a class of simulation software specialized for modeling epidemics and control strategies (mitigation strategies).
<br><br><br><br><br><br>

#Apollo Projects

Currently, Apollo consists of four efforts:

1. Defining a syntax for representing infectious disease scenarios, called the _Apollo Schema_
1. Defining and developing a set of Web services, called the _Apollo Web Services_  
1. Developing a simple Web application that demonstrates the services offered by Apollo Web Services
1. Developing an ontology for epidemic simulation, called _Apollo-SV_ (Structured Vocabulary).


##Apollo Schema
The Apollo Schema defines a set of data types that in combination can define a complex infectious disease scenario for simulation.  The schema support representations of world-wide scenarios involving multiple pathogens, host species, and infectious disease control strategies.
 
##Apollo Web Services
The Apollo Web Services are SOAP Web services.  The defined service types are Broker, Library, Simulator, Visualizer, and Translator.  A single service, the Broker Service, is the single point of access for end-user applications to a set of epidemic simulators and visualizers that create graphs and movies of a simulator's results.  Currently, the Broker Service connects to the:
  * PSC Simulator Service, which provides access to the FRED agent-based simulator  
  * GAIA Service, which creates videos of the geographic spread of disease over time
  * Time-series Visualizer Service, which creates .png images of epidemic curves
  * Translator Service, which translates an infectious disease scenario represented as an instance of the Apollo Schema type into configuration file(s) of each simulator
  * Apollo Library Service, into which you can save or load infectious disease scenarios, control strategies, treatments, and other information encoded using the Apollo Schema's data types.  The Ebola Epidemic Chronology is an example of an application that uses the Apollo Library [http://www.epimodels.org/apolloLibraryViewer/ebola/epidemics]

##A Simple End-user Application that Uses the Apollo Web Services
We have developed a simple Web application to demonstrate the services offered by the Apollo Web Services.  We are hosting this application at [http://research.rods.pitt.edu/seua].

A video showing how the simple end-user application uses the Apollo Web Services is at http://www.youtube.com/watch?v=T4bfIaw2iUA.  A video of an earlier version of the Apollo Web Services and the simple end-user application is at http://www.youtube.com/watch?v=-QdvoPNK9MY.

##Apollo-SV
Apollo-SV, developed as an OWL specification, defines the terms and relations necessary for interoperation between epidemic simulators and public health application software that interface with these simulators.  The latest release of the ontology is always available at the following link: http://purl.obolibrary.org/obo/apollo_sv.owl.

The development version of the ontology is always available at: http://purl.obolibrary.org/obo/apollo_sv/dev/apollo_sv.owl.

For instructions on viewing Apollo-SV, click [https://github.com/ApolloDev/apollo/wiki/Apollo-SV-BasicsOfViewing here].

##Acknowledgements

<table border=0>
<tr>
  <td>
     The Apollo Software Development Team would like to thank PremiumSoft™ CyberTech Ltd. for supporting this open source project by providing the project with a free license for Navicat.
 </td>
  <td width="375"><a href="http://www.navicat.com"><img src="https://github.com/ApolloDev/apollo/blob/wiki/img/navicat.png" align="left" height="100" ></a>
  </td><tr></tr>
<tr style="background-color:white">
  <td>The Apollo Software Development Team would also like to thank JetBrains s.r.o for providing this open source project with free licenses for the amazing IntelliJ IDE.</td>
  <td width="375"><a href="http://www.jetbrains.com/idea/"><img src="https://github.com/ApolloDev/apollo/blob/wiki/img/logo_IntelliJIDEA.png" align="left" width="312" ></a></td>
<tr>
</table>
