<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

require_once __DIR__ . '/util/apollo.inc';
//require_once AROOT . '/ChromePhp/ChromePhp.php';

$apollo = new apollo();

//try {
$client = new SoapClient($apollo->getWSDL(), array('trace' => true));


$runId = urldecode($_GET ['runId']);
$dev = $_GET['dev'];
$name = $_GET['name'];
$ver = $_GET['ver'];
$index = urldecode($_GET['modelIndex']);

$text = "";
if ($name != "FRED") {

    $RunAndSoftwareIdentification = new stdClass();
    $SoftwareIdentification = new stdClass();
    $SoftwareIdentification->softwareDeveloper = $dev;
    $SoftwareIdentification->softwareName = $name;
    $SoftwareIdentification->softwareVersion = $ver;

    $SoftwareIdentification->softwareType = 'simulator';


//    ChromePhp::log($SoftwareIdentification->softwareDeveloper);
//    ChromePhp::log($SoftwareIdentification->softwareName);
//    ChromePhp::log($SoftwareIdentification->softwareVersion);
//    ChromePhp::log($SoftwareIdentification->softwareType);

    $RunAndSoftwareIdentification->softwareId = $SoftwareIdentification;
    $RunAndSoftwareIdentification->runId = $runId;


//        if ($name == 'GAIA') {
//            $statText['status_normal'] = 'completed';
//            $statText['message_normal'] = 'run is completed';
//            $statText['status_novacc'] = 'null';
//            $statText['message_novacc'] = 'null';
//        } else {

    $response = $client->getConfigurationFileForRun(array('runAndSoftwareIdentification' => $RunAndSoftwareIdentification));

    $text = $response->configurationFile;
//    ChromePhp::log($text);

  
    if ($name != "FluTE") {
        $text = str_replace(" ", "&nbsp;&nbsp;&nbsp;", $text);
    } 
    $text = nl2br(urldecode($text));
} else {

    // connect directly to the database
    $dbhandle = mysql_connect("warhol-fred.psc.edu", "apolloint", "int4p011o")
            or die("Unable to connect to MySQL");

    $selected = mysql_select_db("apollo", $dbhandle)
            or die("Could not use database apollo");

//    ChromePhp::log($runId);
    $query = "SELECT configurationFile FROM run where label like '" . $runId . "'";
//    ChromePhp::log($query);
    $result = mysql_query($query);

    if ($result == FALSE) {
        $text = 'Query failed';
    } else {

        while ($row = mysql_fetch_array($result)) {
            $text = $row{'configurationFile'};
        }

        $text = str_replace(" ", "&nbsp;&nbsp;&nbsp;", $text);
        $text = nl2br(urldecode($text));
    }
    mysql_close($dbhandle);
}
//} catch (Exception $e) {
//    $statText['status'] = 'UNKNOWN';
//    $statText['message'] = 'Unable to get run status from the simulator web service';
//
//    $ret->data = $statText;
//    echo json_encode($ret);
//}

print $text;
?>


<!--<html><head></head>
    <body>
        <div id="formatted-text-<?php print $index; ?>"><?php print $text; ?></div>
    </body>
</html>-->