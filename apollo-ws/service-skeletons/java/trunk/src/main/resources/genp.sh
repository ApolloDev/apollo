FRDSRC=~/devapps/Apollo-FRED-WS-v2

wsdl2py -b -l -f simulatorservice.wsdl -o out
wsdl2dispatch -e -f simulatorservice.wsdl -o out
#cp out/*.py $FRDSRC
#cp simulatorservice.wsdl $FRDSRC
#cp -r input-types $FRDSRC
#cp -r base-types $FRDSRC
#cp *.xsd $FRDSRC
#ls $FRDSRC
