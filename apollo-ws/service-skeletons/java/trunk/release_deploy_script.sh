echo -n "enter release version: "
read release_version
echo -n "enter new development version: "
read development_version

mvn release:prepare -DdryRun=true -DreleaseVersion=$release_version -DdevelopmentVersion=$development_version