<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>

        <script type="text/javascript">
            
            function showSimulatorTypeText(reset) {
                
                val = document.getElementById("serviceTypeSelect").value;
                
                if (val == 'select' || reset) {
                    document.getElementById('developer').innerHTML = 'Developer:';
                    document.getElementById('name').innerHTML = 'Name:';
                    document.getElementById('version').innerHTML = 'Version:';
                    
                    document.getElementById('developer').className = 'reg-table-col disabled-text';
                    document.getElementById('name').className = 'reg-table-col disabled-text';
                    document.getElementById('version').className = 'reg-table-col disabled-text';
                    
                    val = 'select'; // change the value if resetting the form
                    
                } else {
                    if (val == 'simulator') {
                        document.getElementById('developer').innerHTML = 'Simulator Developer:';
                        document.getElementById('name').innerHTML = 'Simulator Name:';
                        document.getElementById('version').innerHTML = 'Simulator Version:';
                    } else if (val == 'visualizer') {
                        document.getElementById('developer').innerHTML = 'Visualizer Developer:';
                        document.getElementById('name').innerHTML = 'Visualizer Name:';
                        document.getElementById('version').innerHTML = 'Visualizer Version:';
                    }
                    
                    document.getElementById('developer').className = 'reg-table-col';
                    document.getElementById('name').className = 'reg-table-col';
                    document.getElementById('version').className = 'reg-table-col';
                }
                
                
                
                toggleEnabled('developer-input', val)
                toggleEnabled('developer', val);
                toggleEnabled('name', val);
                toggleEnabled('name-input', val);
                toggleEnabled('version', val);
                toggleEnabled('version-input', val);
            }
    
        </script>

        <script type="text/javascript">
            function afterload() 
            {   
                showSimulatorTypeText();
            }
        </script>


        <script type="text/javascript">
            function toggleEnabled(controlId, value) 
            {   
                var control = document.getElementById(controlId);
                if (value == 'select') {
                    control.disabled = true;
                } else {
                    control.disabled = false;
                }
            }
        </script>

        <link rel="stylesheet" type="text/css"
              href="css/apollo_reg_styles.css" />
    </head>
    <body onload="afterload()">

        <h1 class="title">Simple Apollo Registry App</h1>


        <?php
        define('AROOT', getcwd());
        require_once AROOT . '/apollo/apollo.inc';
//        require_once ROOT . '/chrome/ChromePhp.php';

        $requiredParameters = array('requester', 'password', 'developer', 'name', 'version', 'url');
        $errors = false;

        if (!empty($_POST)) {
            // if the form was submitted check for required parameters
            foreach ($requiredParameters as $val) {
                if (!isset($_POST[$val]) || strlen($_POST[$val]) < 1) {
                    echo 'Error: ' . $val . ' parameter not set.<br>';
                    $errors = true;
                }
            }

            // handle service-type separately since it should always have a value
            if (!isset($_POST['service-type']) || $_POST['service-type'] == 'select') {
                echo 'Error: ' . 'service type' . ' parameter not set.<br>';
                $errors = true;
            }
        }


        $serviceType = isset($_POST['service-type']) ? $_POST['service-type'] : '';
        ?>

        <table style="margin-left: auto; margin-right: auto">

            <form id="regForm" action="registration.php" method="post" style="text-align: center">


                <tr>
                    <td class="reg-table-col">Requester ID:</td> 
                    <td><input type="text" class="input-box" name="requester" value="<?php echo isset($_POST['requester']) ? $_POST['requester'] : '' ?>"></input></td>
                </tr>

                <tr>
                    <td class="reg-table-col">Requester Password:</td>
                    <td><input type="password" class="input-box" name="password" value="<?php echo isset($_POST['password']) ? $_POST['password'] : '' ?>"></input></td>
                </tr>

                <tr>
                    <td class="reg-table-col">Service Type:</td>
                    <td><select id="serviceTypeSelect" name="service-type" class="input-box" onchange="showSimulatorTypeText(false)">
                            <option value="select">Please Select</option>
                            <option value="simulator" <?php if (isset($_POST['service-type']) && $_POST['service-type'] == 'simulator')
            echo 'selected' ?>>Simulator</option>
                            <option value="visualizer" <?php if (isset($_POST['service-type']) && $_POST['service-type'] == 'visualizer')
                                    echo 'selected' ?>>Visualizer</option>
                        </select>
                </tr>

                <tr>
                    <td class="reg-table-col disabled-text" id="developer">Developer:</td>
                    <td><input type="text" class="input-box" name="developer" id="developer-input" value="<?php echo isset($_POST['developer']) ? $_POST['developer'] : '' ?>" disabled></input></td>
                </tr>

                <tr>
                    <td class="reg-table-col disabled-text" id="name">Name:</td>
                    <td><input type="text" class="input-box" id="name-input" name="name" value ="<?php echo isset($_POST['name']) ? $_POST['name'] : '' ?>" disabled></input></td>
                </tr>

                <tr>
                    <td class="reg-table-col disabled-text" id="version">Version:</td>
                    <td><input type="text" class="input-box" id="version-input" name="version" value="<?php echo isset($_POST['version']) ? $_POST['version'] : '' ?>" disabled></input></td>
                </tr>

                <tr>
                    <td class="reg-table-col" id="url">Service WSDL:</td>
                    <td><input type="text" class="input-box" name="url" value="<?php echo isset($_POST['url']) ? $_POST['url'] : '' ?>"></input></td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align: right">
                        <input type="submit" name="action" value="Register"></input>
                        <input type="submit" name="action" value="Unregister"></input>
                        <input type="reset" value="Reset" onclick="showSimulatorTypeText(true)"></input></td>
                </tr>

            </form>
        </table>

        <?php
        if (!$errors && !empty($_POST)) {

            $apollo = new apollo();
            $wsdl = $apollo->getWSDL();
            $client = new SoapClient($wsdl, array('trace' => true));

            // build the objects
            $ServiceRegistrationRecord = new stdClass();
            // authentication
            $Authentication = new stdClass();
            $Authentication->requesterId = $_POST['requester'];
            $Authentication->requesterPassword = $_POST['password'];
            $ServiceRegistrationRecord->authentication = $Authentication;
            // url
            $Url = $_POST['url'];
            $ServiceRegistrationRecord->url = $Url;
            // service record
            $ServiceRecord = new stdClass();
            $serviceType = $_POST['service-type'];

            if ($serviceType == 'simulator') {
                $SimulatorIdentification = new stdClass();
                $SimulatorIdentification->simulatorDeveloper = $_POST['developer'];
                $SimulatorIdentification->simulatorName = $_POST['name'];
                $SimulatorIdentification->simulatorVersion = $_POST['version'];
                $ServiceRecord->simulatorIdentification = $SimulatorIdentification;
            } else if ($serviceType == 'visualizer') {
                $VisualizerIdentification = new stdClass();
                $VisualizerIdentification->visualizerDeveloper = $_POST['developer'];
                $VisualizerIdentification->visualizerName = $_POST['name'];
                $VisualizerIdentification->visualizerVersion = $_POST['version'];
                $ServiceRecord->visualizerIdentification = $VisualizerIdentification;
            } else {
//                ChromePhp::log("Shouldn't be here");
            }

            $ServiceRegistrationRecord->serviceRecord = $ServiceRecord;

            $action = $_POST['action'];
            if ($action == 'Register') {
                $response = $client->registerService(array('serviceRegistrationRecord' => $ServiceRegistrationRecord));
                $success = $response->registrationSuccessful;
                $message = $response->message;
            } else if ($action == 'Unregister') {
                $response = $client->unRegisterService(array('serviceRegistrationRecord' => $ServiceRegistrationRecord));
                $success = $response->unRegistrationSuccessful;
                $message = $response->message;
            } else {
//                ChromePhp::log('Action type not recognized');
            }

            if ($success) {
                echo 'Success';
            } else {
                echo 'Failure';
            }
            echo '<br>';
            echo $message;
        }
        ?>


    </body>
</html>
