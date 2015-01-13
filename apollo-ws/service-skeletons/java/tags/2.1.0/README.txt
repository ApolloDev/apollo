Notes on building the Apollo project:

	1. Upon building, maven will output a dependency-reduced-pom.xml file for each non-war module. These do not need to be checked in to subversion so you should add "dependency-reduced-pom.xml" to your subversion ignore list. These files will then be ignored when executing the reslease_deploy script.