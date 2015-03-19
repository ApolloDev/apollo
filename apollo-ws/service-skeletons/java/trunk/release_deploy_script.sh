set -e
echo -n "enter release version: "
read release_version
echo -n "enter new development version (-SNAPSHOT will be appended): "
read development_version
echo -n "enter subversion username: "
read svn_username
echo -n "enter subversion password: "
read -s svn_password

mvn release:prepare -DreleaseVersion=$release_version -DdevelopmentVersion="$development_version-SNAPSHOT" -Dtag=$release_version -Dusername=$svn_username -Dpassword=$svn_password  -DautoVersionSubmodules=true

mvn release:perform -Dgoals=deploy -DuseReleaseProfile=false

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=broker-service -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/broker-service/target/broker-service-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=broker-service-client -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/broker-service-client/target/broker-service-client-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=config-examples -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/config-examples/target/config-examples-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=db-common -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/db-common/target/db-common-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=library-service -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/library-service/target/library-service-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=library-service-client -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/library-service-client/target/library-service-client-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=services-common -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/services-common/target/services-common-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=simulator-service -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/simulator-service/target/simulator-service-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=simulator-service-client -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/simulator-service-client/target/simulator-service-client-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=syntheticpopulation-service -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/syntheticpopulation-service/target/syntheticpopulation-service-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=syntheticpopulation-service-client -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/syntheticpopulation-service-client/target/syntheticpopulation-service-client-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=translator-service-skeleton -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/translator-service-skeleton/target/translator-service-skeleton-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=visualizer-service -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/visualizer-service/target/visualizer-service-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=visualizer-service-client -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/visualizer-service-client/target/visualizer-service-client-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn deploy:deploy-file -DgroupId=edu.pitt.apollo -DartifactId=xsd-types -Dversion=$release_version -Dpackaging=jar -Dfile=./target/checkout/xsd-types/target/xsd-types-$release_version.jar -DrepositoryId=research.rods.pitt.edu -Durl=scp://research.rods.pitt.edu:/home/jdl/repo/

mvn clean

echo "You have successfully released and deployed Apollo Web Services v$release_version."