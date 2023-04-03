
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.failsafe.Timeout;

import java.time.temporal.ChronoUnit;
//https://www.webscrapingapi.com/java-web-scraping
//https://www.htmlgoodies.com/html5/web-page-scraping-with-jsoup/
//https://stackoverflow.com/questions/45943856/get-results-from-all-the-pages-using-jsoup
//https://stackoverflow.com/questions/50677760/selenium-clear-command-doesnt-clear-the-element
//https://www.ibm.com/docs/en/i/7.3?topic=extension-changing-your-java-code-use-socket-factories
//https://www.tutorialkart.com/java/how-to-get-current-date-in-yyyy-mm-dd-format-in-java/

import java.lang.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.net.SocketFactory;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;
import javax.swing.text.StyleConstants.CharacterConstants;

import org.openqa.selenium.chrome.*;
import java.io.*;

public class FlightFinder {
        private String origin;
        private String destination;

        private String departureYear;
        private String departureMonth;
        private String departureMonthName;
        private String departureDay;

        private String arrivalYear;
        private String arrivalMonth;
        private String arrivalMonthName;
        private String arrivalDay;

        private String todayYear;
        private String todayMonth;
        private String todayMonthName;
        private String todayDay;
        private int arrivalInt = 20230415;
        private int departureInt = 20230419;
        private int todayInt;

        // can only book 330 days in advance

        public void populateDateFields() {
                // For Arrival Date
                arrivalYear = Integer.toString(arrivalInt);
                arrivalMonth = Integer.toString(arrivalInt);
                arrivalDay = Integer.toString(arrivalInt);

                arrivalYear = arrivalYear.substring(0, 4);
                arrivalMonth = arrivalMonth.substring(4, 6);
                arrivalDay = arrivalDay.substring(6, 8);

                // For Departure Date
                departureYear = Integer.toString(departureInt);
                departureMonth = Integer.toString(departureInt);
                departureDay = Integer.toString(departureInt);

                departureYear = departureYear.substring(0, 4);
                departureMonth = departureMonth.substring(4, 6);
                departureDay = departureDay.substring(6, 8);

                // For Today
                todayYear = Integer.toString(todayInt);
                todayMonth = Integer.toString(todayInt);
                todayDay = Integer.toString(todayInt);

                todayYear = todayYear.substring(0, 4);
                todayMonth = todayMonth.substring(4, 6);
                todayDay = todayDay.substring(6, 8);
        }

        public boolean verifyAndPopulateDates() {
                getCurrentDate();
                populateDateFields();

                // Translate month symbol to word
                arrivalMonthName = convertMonth(arrivalMonth);
                departureMonthName = convertMonth(departureMonth);
                todayMonthName = convertMonth(todayMonth);

                // Check that dates make sense
                if (todayMonthName.equals("INVALID")) {
                        return false;
                }
                if (todayInt > departureInt || todayInt > arrivalInt || arrivalInt > departureInt) {
                        return false;
                }

                // Check that dates aren't over 330 days in advance
                LocalDate now = LocalDate.now();
                LocalDate dept = LocalDate.of((int) Integer.valueOf(departureYear),
                                (int) Integer.valueOf(departureMonth), (int) Integer.valueOf(departureDay));
                long days = ChronoUnit.DAYS.between(now, dept);
                if (days > 330) {
                        return false;
                }
                return true;
        }

        public void clickDates(String desiredMonth, String desiredYear, String month, String year, String ariaLabel,
                        WebDriver driver) throws Exception {

                var desiredDate = driver.findElements(By.xpath("//button[@aria-label='" + ariaLabel + "']"));
                String altLabelIn = ariaLabel + " selected, current check in date.";
                String altLabelOut = ariaLabel + " selected, current check out date.";
                List<WebElement> nextMonth = driver
                                .findElements(By.cssSelector(
                                                ".uitk-button.uitk-button-medium.uitk-button-only-icon.uitk-layout-flex-item.uitk-button-paging"));

                if (desiredDate.size() == 0) {
                        try {
                                nextMonth.get(0).click();
                                desiredDate = driver
                                                .findElements(By.xpath("//button[@aria-label='" + ariaLabel + "']"));

                        } catch (Exception e) {
                        }
                        while (desiredDate.size() == 0) {
                                desiredDate = driver
                                                .findElements(By.xpath("//button[@aria-label='" + ariaLabel + "']"));

                                if (desiredDate.size() == 0) {
                                        desiredDate = driver
                                                        .findElements(By.xpath(
                                                                        "//button[@aria-label='" + altLabelIn + "']"));

                                }
                                if (desiredDate.size() == 0) {
                                        desiredDate = driver
                                                        .findElements(By.xpath(
                                                                        "//button[@aria-label='" + altLabelOut + "']"));

                                }
                                Thread.sleep(100);
                                if (desiredDate.size() == 0) {
                                        nextMonth.get(1).click();
                                        Thread.sleep(200);
                                }
                        }
                }
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", desiredDate.get(0));
        }

        public String convertMonth(String month) {
                if (month.equals("01")) {
                        return "Jan";
                }
                if (month.equals("02")) {
                        return "Feb";
                }
                if (month.equals("03")) {
                        return "Mar";
                }
                if (month.equals("04")) {
                        return "Apr";
                }
                if (month.equals("05")) {
                        return "May";
                }
                if (month.equals("06")) {
                        return "Jun";
                }
                if (month.equals("07")) {
                        return "Jul";
                }
                if (month.equals("08")) {
                        return "Aug";
                }
                if (month.equals("09")) {
                        return "Sep";
                }
                if (month.equals("10")) {
                        return "Oct";
                }
                if (month.equals("11")) {
                        return "Nov";
                }
                if (month.equals("12")) {
                        return "Dec";
                }
                return "INVALID";
        }

        public void searchExpedia() throws Exception {
                // Set fields
                origin = "San Francisco";
                destination = "Entebbe";

                // Set system properties
                System.setProperty("webdriver.chrome.driver", "/Users/kwadhwani/Downloads/chromedriver");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.setExperimentalOption("useAutomationExtension", false);
                options.addArguments("--remote-allow-origins=*");
                // Create a new instance of the Chrome driver
                WebDriver driver = new ChromeDriver(options);
                driver.manage().window().maximize();
                // Simulate Google search
                driver.get(
                                "https://www.google.com/search?q=expedia&ei=6UbGY-XGBc2eqtsP4ausmAE&ved=0ahUKEwil34i8g878AhVNj2oFHeEVCxMQ4dUDCBA&uact=5&oq=expedia&gs_lcp=Cgxnd3Mtd2l6LXNlcnAQAzIQCC4QgwEQxwEQsQMQ0QMQQzIECAAQQzIFCAAQgAQyCAgAEIAEELEDMgoIABCxAxCDARBDMgsIABCABBCxAxCDATIICAAQgAQQsQMyBQgAEIAEMgsIABCABBCxAxCDATILCAAQgAQQsQMQgwE6EAguELEDEIMBEMcBENEDEEM6FAguEIAEELEDEIMBEMcBENEDENQCOhEILhCABBCxAxCDARDHARDRAzoLCC4QgAQQsQMQgwE6CwguEIAEEMcBENEDOhAILhCxAxCDARDHARCvARBDOgcIABCxAxBDOhEILhCDARCvARDHARCxAxCABDoICAAQsQMQgwFKBAhBGABKBAhGGABQAFjKBWD-BmgAcAF4AIABRYgBtwKSAQE3mAEAoAEBwAEB&sclient=gws-wiz-serp");
                Thread.sleep(200);

                // Open Expedia Flights
                driver.get("https://www.expedia.com/Flights");
                Thread.sleep(200);

                // Input cities
                List<WebElement> locationsBeforeClick = driver
                                .findElements(By.cssSelector(".uitk-fake-input.uitk-form-field-trigger"));
                List<WebElement> locationsAfterClick = driver
                                .findElements(By.cssSelector(
                                                ".uitk-field-input.uitk-typeahead-input.uitk-typeahead-input-v2"));
                setFields(driver, locationsBeforeClick.get(0),
                                ".uitk-field-input.uitk-typeahead-input.uitk-typeahead-input-v2", 0, origin);
                Thread.sleep(200);

                setFields(driver, locationsBeforeClick.get(1),
                                ".uitk-field-input.uitk-typeahead-input.uitk-typeahead-input-v2", 0, destination);
                Thread.sleep(200);

                // Input dates
                List<WebElement> datesBeforeClick = driver
                                .findElements(By.cssSelector(".uitk-faux-input.uitk-form-field-trigger"));
                datesBeforeClick.get(0).click();
                Thread.sleep(200);

                String ariaLabel = (arrivalMonthName + " " + arrivalDay + ", " + arrivalYear);
                clickDates(arrivalMonth, arrivalYear, todayMonth, todayYear, ariaLabel, driver);

                Thread.sleep(200);

                ariaLabel = (departureMonthName + " " + departureDay + ", " + departureYear);
                clickDates(arrivalMonth, arrivalYear, todayMonth, todayYear, ariaLabel, driver);
                Thread.sleep(100);

                // Search
                var done = driver.findElements(By.cssSelector(
                                ".uitk-button.uitk-button-large.uitk-button-has-text.uitk-button-primary"));

                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", done.get(1));

                Thread.sleep(200);

                var submitSearch = driver.findElements(By.xpath("//button[@data-testid='submit-button']"));
                try {
                        executor.executeScript("arguments[0].click();", submitSearch.get(0));
                } catch (Exception e) {
                }
                ;

                List<WebElement> locationsBeforeClickPostSearch = driver
                                .findElements(By.cssSelector(".uitk-fake-input.uitk-form-field-trigger"));

                String originExpedia = locationsBeforeClickPostSearch.get(0).getAttribute("aria-label");
                String destExpedia = locationsBeforeClickPostSearch.get(1).getAttribute("aria-label");

                Thread.sleep(200);

                if (!originExpedia.contains(origin)) {

                        List<WebElement> dest = driver
                                        .findElements(By.cssSelector(".uitk-fake-input.uitk-form-field-trigger"));
                        dest.get(0).click();

                        var input = driver.findElements(By.xpath("//input[@placeholder='Flying from']"));
                        input.get(0).sendKeys(origin);
                        Thread.sleep(400);

                        var airport = driver.findElements(
                                        By.xpath("//button[@data-stid='typeahead-originInput-0-result-item-button']"));
                        // ".uitk-button.uitk-button-medium.uitk-button-fullWidth.uitk-button-typeahead.uitk-type-left.has-subtext"));
                        airport.get(0).click();
                        Thread.sleep(400);

                        submitSearch = driver.findElements(By.xpath("//button[@data-test-id='search-form-button']"));
                        executor.executeScript("arguments[0].click();", submitSearch.get(0));
                }

                if (!destExpedia.contains(destination)) {

                        List<WebElement> dest = driver
                                        .findElements(By.cssSelector(".uitk-fake-input.uitk-form-field-trigger"));
                        dest.get(1).click();

                        var input = driver.findElements(By.xpath("//input[@placeholder='Flying to']"));
                        input.get(0).sendKeys(destination);
                        Thread.sleep(400);

                        var airport = driver.findElements(By.cssSelector(
                                        ".uitk-typeahead-result-item.has-subtext"));
                        airport.get(0).click();
                        Thread.sleep(400);

                        submitSearch = driver.findElements(By.xpath("//button[@data-test-id='search-form-button']"));
                        executor.executeScript("arguments[0].click();", submitSearch.get(0));

                }
                scrapeFlightData(driver, executor);
        }

        public void scrapeFlightData(WebDriver driver, JavascriptExecutor executor) throws Exception {

                Duration t = Duration.ofSeconds(200);
                WebDriverWait w = new WebDriverWait(driver, t);
                w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@data-test-id='select-link']")));

                FileWriter myWriter = new FileWriter(
                                "/Users/kwadhwani/Downloads/demo/src/main/java/FlightInfo.txt");

                var flights = driver.findElements(By.xpath("//button[@data-test-id='select-link']"));
                for (int i = 0; i < 1; i++) {
                        // have to scroll
                        Actions action = new Actions(driver);

                        executor.executeScript("arguments[0].scrollIntoView();", flights.get(i));
                        Thread.sleep(200);
                        action.moveToElement(flights.get(i)).perform();
                        flights.get(i).click();

                        var details = driver.findElements(By.cssSelector(
                                        ".uitk-expando.uitk-expando-link.uitk-spacing.uitk-spacing-margin-inlinestart-six"));

                        executor.executeScript("arguments[0].scrollIntoView();", details.get(0));
                        Thread.sleep(200);
                        action.moveToElement(details.get(0)).perform();
                        details.get(0).click();

                        var flightInfo = driver.findElements(By.xpath("//div[@data-test-id='additional-info-0']"));
                        int counter = 0;
                        while (flightInfo.size() != 0) {
                                myWriter.write("Flight " + counter + ": " + flightInfo.get(0).getText()
                                                + "/endFlight/ \n");
                                counter++;
                                String character = Integer.toString(counter);
                                String id = "additional-info-" + character;
                                flightInfo = driver.findElements(By.xpath("//div[@data-test-id='" + id + "']"));
                        }
                        myWriter.write("/endOption/ \n\n");
                }
                myWriter.close();
        }

        public void setFields(WebDriver driver, WebElement field, String fieldPostClickClass, int index,
                        String input)
                        throws Exception {
                field.click();
                Thread.sleep(1000);
                Thread.sleep(400);
                List<WebElement> fieldPostClick = driver
                                .findElements(By.cssSelector(fieldPostClickClass));
                cleanFields(fieldPostClick.get(index));
                fieldPostClick.get(index).sendKeys(input);
                Thread.sleep(400);
                fieldPostClick.get(0).sendKeys(Keys.RETURN);
        }

        public static void cleanFields(WebElement element) {
                String inputText = element.getAttribute("value");

                if (inputText != null) {
                        for (int i = 0; i < inputText.length(); i++) {
                                element.sendKeys(Keys.BACK_SPACE);
                        }
                }
        }

        public void getCurrentDate() {
                LocalDate dateObj = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String date = dateObj.format(formatter);
                todayInt = (int) Integer.parseInt(date);
        }

        public static void main(String[] args) throws Exception {
                FlightFinder f = new FlightFinder();
                System.out.println(f.verifyAndPopulateDates());
                f.searchExpedia();
        }
}