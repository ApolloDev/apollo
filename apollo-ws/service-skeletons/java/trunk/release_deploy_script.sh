set -e
echo -n "enter release version: "
read release_version
echo -n "enter new development version: "
read development_version
echo -n "enter subversion username: "
read svn_username
echo -n "enter subversion password: "
read -s svn_password

mvn release:prepare -DreleaseVersion=$release_version -DdevelopmentVersion=$development_version -Dtag=$release_version -Dusername=$svn_username -Dpassword=$svn_password  -DautoVersionSubmodules=true

mvn release:perform -Dgoals=deploy -DuseReleaseProfile=false

echo "success"