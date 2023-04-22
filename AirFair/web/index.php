<?php

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Log\LoggerInterface;
use DI\Container;
use DI\Bridge\Slim\Bridge;
use Monolog\Handler\StreamHandler;
use Monolog\Level;
use Monolog\Logger;
use Slim\Factory\AppFactory;
use Slim\Views\Twig;

$email = $_POST['email'];
$localAirport = $_POST['local-airport'];
$destAirport = $_POST['dest-airport'];
$departDate = $_POST['depart-date'];
$returnDate = $_POST['return-date'];
$maxPrice = $_POST['max-price'];


header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST');

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include_once("index.html"); 


$dburl = parse_url(getenv('DATABASE_URL'));


$dbname = ltrim($dburl['path'], '/');
$user = $dburl['user'];
$password = $dburl['pass'];
$port = $dburl['port'];
$host = $dburl['host'];

$dsn = "pgsql:host=$host;dbname=$dbname;user=$user;port=$port;password=$password";

$db = new PDO($dsn);

if($db && !is_null($email)){
  echo "Thanks!";

  $sql = "INSERT INTO requests (email, localair, destair, arrival, return, maxprice) VALUES (?, ?, ?, ?, ?, ?)";

  $st = $db->prepare($sql);
  $st->execute(array($email, $localAirport, $destAirport, $departDate, $returnDate, $maxPrice));
}
?>