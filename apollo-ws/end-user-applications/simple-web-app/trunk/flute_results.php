<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$md5RunId = urldecode($_GET ['md5RunId']);

$url = 'http://130.49.206.36:8080/apollo/' . $md5RunId . '/flute-results.txt';

$text = file_get_contents($url);
$text = nl2br($text);

print $text;
?>
