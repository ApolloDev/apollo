<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

require_once __DIR__ . '/../util/apollo.inc';
//require_once __DIR__ . '/../chromephp/ChromePhp.php';
$ret = new Response();

$apollo = new apollo();

try {
    $client = new SoapClient($apollo->getWSDL(), array('trace' => true));

    $parameters = new stdClass();
    $parameters->diseaseName = null;
    $parameters->infectiousPeriod = null;
    $parameters->latentPeriod = null;
    $parameters->reproductionNumber = null;
    $parameters->asymptomaticInfectionFraction = null;

    $apolloResponse = $client->queryForDiseaseConfiguration(array('parameters' => $parameters));
    $diseaseList = null;
    $test = (array) $apolloResponse;

    if (!empty($test)) {
        $diseaseList = $apolloResponse->diseases;
    }
} catch (Exception $e) {
    $ret->exception = $e;
}
?>


<html><head>

        <style type="text/css">

            .query-td {
                border: 1px;
                border-style: solid;
                border-color: black;
                border-collapse: collapse;

            }

        </style>


    </head>

    <h2>Previously specified strains:</h2>

    <table class="apollo-table" >
        <tr>
            <td></td>
            <td class="apollo-table-td" style="font-weight: bold">
                Strain Name
            </td>
            <td class="apollo-table-td" style="font-weight: bold">
                Infectious Period
            </td>
            <td class="apollo-table-td" style="font-weight: bold;">
                Latent Period
            </td>
            <td class="apollo-table-td" style="font-weight: bold">
                Reproduction Number
            </td>
            <td class="apollo-table-td" style="font-weight: bold">
                Asymptomatic Infection Fraction
            </td>
        </tr>
        <?php
        $i = 0;
        if ($diseaseList != null) {

            if (is_array($diseaseList)) {
                foreach ($diseaseList as $disease) {
                    $i = $i + 1;
                    echo "<tr>"
                    . "<td class=\"apollo-table-td\" style=\"font-weight: bold\">Strain " . $i . "</td>"
                    . "<td class=\"apollo-table-td\">" . $disease->diseaseName . "</td>"
                    . "<td class=\"apollo-table-td\">" . $disease->infectiousPeriod . "</td>"
                    . "<td class=\"apollo-table-td\">" . $disease->latentPeriod . "</td>"
                    . "<td class=\"apollo-table-td\">" . $disease->reproductionNumber . "</td>"
                    . "<td class=\"apollo-table-td\">" . $disease->asymptomaticInfectionFraction . "</td>"
                    . "</tr>";
                }
            } else {
                // only one object was returned
                echo "<tr>"
                . "<td class=\"apollo-table-td\" style=\"font-weight: bold\">Strain " . "1" . "</td>"
                . "<td class=\"apollo-table-td\">" . $diseaseList->diseaseName . "</td>"
                . "<td class=\"apollo-table-td\">" . $diseaseList->infectiousPeriod . "</td>"
                . "<td class=\"apollo-table-td\">" . $diseaseList->latentPeriod . "</td>"
                . "<td class=\"apollo-table-td\">" . $diseaseList->reproductionNumber . "</td>"
                . "<td class=\"apollo-table-td\">" . $diseaseList->asymptomaticInfectionFraction . "</td>"
                . "</tr>";
            }
        }
        ?>


    </table>

</html>