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


require __DIR__.'/../vendor/autoload.php';
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

// Passing true enables exceptions.
$phpmailer = new PHPMailer(true);


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


if($db){
  if (!is_null($email) && !is_null($localAirport) && !is_null($destAirport) && !is_null($departDate) && !is_null($returnDate) && !is_null($maxPrice)){

  $sql = "INSERT INTO requests (email, localair, destair, arrival, return, maxprice) VALUES (?, ?, ?, ?, ?, ?)";
  try {
  $st = $db->prepare($sql);
  $st->execute(array($email, $localAirport, $destAirport, $departDate, $returnDate, $maxPrice));


  echo '<script type="text/javascript">
  alert("Success! Time to start packing.");
  </script>';

  }

  catch (Exception $e) { 
    echo '<script type="text/javascript">
    alert("Please submit a valid request.");
    </script>';
 }
  }
}

try {
  // Configure SMTP
  $phpmailer->isSMTP();
  $phpmailer->SMTPAuth = true;
  $phpmailer->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;

  // ENV Credentials
  $phpmailer->Host = getenv("MAILERTOGO_SMTP_HOST", true);
  $phpmailer->Port = intval(getenv("MAILERTOGO_SMTP_PORT", true));
  $phpmailer->Username = getenv("MAILERTOGO_SMTP_USER", true);
  $phpmailer->Password = getenv("MAILERTOGO_SMTP_PASSWORD", true);
  $mailertogo_domain = getenv("MAILERTOGO_DOMAIN", true);


  // Mail Headers
  $phpmailer->setFrom("mailer@{$mailertogo_domain}", "Mailer");
  // Change to recipient email. Make sure to use a real email address in your tests to avoid hard bounces and protect your reputation as a sender.
  $phpmailer->addAddress("noreply@{$mailertogo_domain}", "kat.wadhwani@gmail.com");


  
  // Message
  $phpmailer->isHTML(true);
  $phpmailer->Subject = "Mailer To Go Test";
  $phpmailer->Body    = "<b>Hi</b>\nTest from Mailer To Go 😊\n";
  $phpmailer->AltBody = "Hi!\nTest from Mailer To Go 😊\n";

  // Send the Email
  $phpmailer->send();
  echo "Message has been sent";
} catch (Exception $e) {
  echo "Message could not be sent. Mailer Error: {$phpmailer->ErrorInfo}";
}


  $db = new PDO($dsn);
  $sql = "SELECT * FROM requests";
  if($db){
    if (!is_null($email) && !is_null($localAirport) && !is_null($destAirport) && !is_null($departDate) && !is_null($returnDate) && !is_null($maxPrice)){
  
    $sql = "SELECT * FROM requests";
    $st = $db->prepare($sql);

    $st->execute();
    $result = $st->fetchAll(PDO::FETCH_ASSOC);
  
      foreach ($result as $row) {
        $column1Value = $row['email'];
        $column2Value = $row['localair'];
        $column3Value = $row['destair'];
        $column4Value = $row['arrival'];
        $column5Value = $row['return'];
        $column6Value = $row['maxprice'];
        
        echo $column5Value;
      }
    }
  }


  

?>