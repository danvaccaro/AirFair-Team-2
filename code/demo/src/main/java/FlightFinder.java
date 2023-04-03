
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//https://www.webscrapingapi.com/java-web-scraping
//https://www.htmlgoodies.com/html5/web-page-scraping-with-jsoup/
//https://stackoverflow.com/questions/45943856/get-results-from-all-the-pages-using-jsoup
//https://stackoverflow.com/questions/50677760/selenium-clear-command-doesnt-clear-the-element
//https://www.ibm.com/docs/en/i/7.3?topic=extension-changing-your-java-code-use-socket-factories
//https://www.tutorialkart.com/java/how-to-get-current-date-in-yyyy-mm-dd-format-in-java/

import java.lang.*;
import java.net.HttpURLConnection;
import java.net.Socket;
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
import javax.swing.text.StyleConstants.CharacterConstants;

import org.openqa.selenium.chrome.*;
import java.io.*;

public class FlightFinder {
        private String origin;
        private String destination;
        private String departureDateString;
        private String arrivalDateString;

        private String departureYear;
        private String departureMonth;
        private String departureDay;

        private String arrivalYear;
        private String arrivalMonth;
        private String arrivalDay;

        private String todayYear;
        private String todayMonth;
        private String todayDay;

        private int departureInt;
        private int arrivalInt;
        private int todayInt;

        // can only book a year in advance
        public boolean verifyDate() {

                if (todayInt < departureInt || todayInt < arrivalInt || arrivalInt < departureInt) {
                        return false;
                }
                return true;
        }

        public void searchExpedia() throws Exception {

                /*
                 * String str1="192.168.0.201";
                 * String str2="255.255.255.0";
                 * String[] command1 = { "netsh", "interface", "ip", "set", "address",
                 * "name=", "Local Area Connection" ,"source=static", "addr=",str1,
                 * "mask=", str2};
                 * Process pp = java.lang.Runtime.getRuntime().exec(command1);
                 */

                // Set fields
                origin = "Taipei";
                destination = "Shangai";

                // Set system properties
                System.setProperty("webdriver.chrome.driver", "/Users/kwadhwani/Downloads/chromedriver");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.setExperimentalOption("useAutomationExtension", false);
                options.addArguments("--remote-allow-origins=*");
                // Create a new instance of the Chrome driver
                WebDriver driver = new ChromeDriver(options);

                // Simulate Google search
                driver.get(
                                "https://www.google.com/search?q=expedia&ei=6UbGY-XGBc2eqtsP4ausmAE&ved=0ahUKEwil34i8g878AhVNj2oFHeEVCxMQ4dUDCBA&uact=5&oq=expedia&gs_lcp=Cgxnd3Mtd2l6LXNlcnAQAzIQCC4QgwEQxwEQsQMQ0QMQQzIECAAQQzIFCAAQgAQyCAgAEIAEELEDMgoIABCxAxCDARBDMgsIABCABBCxAxCDATIICAAQgAQQsQMyBQgAEIAEMgsIABCABBCxAxCDATILCAAQgAQQsQMQgwE6EAguELEDEIMBEMcBENEDEEM6FAguEIAEELEDEIMBEMcBENEDENQCOhEILhCABBCxAxCDARDHARDRAzoLCC4QgAQQsQMQgwE6CwguEIAEEMcBENEDOhAILhCxAxCDARDHARCvARBDOgcIABCxAxBDOhEILhCDARCvARDHARCxAxCABDoICAAQsQMQgwFKBAhBGABKBAhGGABQAFjKBWD-BmgAcAF4AIABRYgBtwKSAQE3mAEAoAEBwAEB&sclient=gws-wiz-serp");
                Thread.sleep(400);

                // Open Expedia Flights
                driver.get("https://www.expedia.com/Flights");
                Thread.sleep(400);

                // Input cities
                List<WebElement> locationsBeforeClick = driver
                                .findElements(By.cssSelector(".uitk-fake-input.uitk-form-field-trigger"));
                List<WebElement> locationsAfterClick = driver
                                .findElements(By.cssSelector(
                                                ".uitk-field-input.uitk-typeahead-input.uitk-typeahead-input-v2"));
                setFields(driver, locationsBeforeClick.get(0),
                                ".uitk-field-input.uitk-typeahead-input.uitk-typeahead-input-v2", 0, origin);
                Thread.sleep(800);

                setFields(driver, locationsBeforeClick.get(1),
                                ".uitk-field-input.uitk-typeahead-input.uitk-typeahead-input-v2", 0, destination);
                Thread.sleep(200);

                // Input dates
                List<WebElement> datesBeforeClick = driver
                                .findElements(By.cssSelector(".uitk-faux-input.uitk-form-field-trigger"));
                datesBeforeClick.get(0).click();
                Thread.sleep(1200);

                var arrivalDate = driver.findElements(By.cssSelector("[aria-label=\"Apr 12, 2023\"]"));
                arrivalDate.get(0).click();

                Thread.sleep(400);
                var departureDate = driver.findElements(By.cssSelector("[aria-label=\"Apr 14, 2023\"]"));
                departureDate.get(0).click();

                // Search
                var submitSearch = driver.findElements(By.cssSelector(
                                ".uitk-button.uitk-button-large.uitk-button-fullWidth.uitk-button-has-text.uitk-button-primary.uitk-button-floating-full-width"));
                submitSearch.get(0).submit();

                // While loop

                List<WebElement> locationsBeforeClickPostSearch = driver
                                .findElements(By.cssSelector(".uitk-fake-input.uitk-form-field-trigger"));

                String originExpedia = locationsBeforeClickPostSearch.get(0).getAttribute("aria-label");
                String destExpedia = locationsBeforeClickPostSearch.get(1).getAttribute("aria-label");

                System.out.println(originExpedia);
                System.out.println(destExpedia);

                Thread.sleep(200);

                while (!originExpedia.equals("Flying from Taipei (TPE-Taoyuan Intl.)")
                                || !destExpedia.equals("Berlin (BER - Brandenburg)")) {

                        List<WebElement> trial = driver
                                        .findElements(By.cssSelector(".uitk-layout-grid-item"));
                        trial.get(0).click();

                        setFields(driver, locationsBeforeClickPostSearch.get(0),
                                        ".uitk-field-input.uitk-typeahead-input.uitk-typeahead-input-v2", 0, "Taipei");
                        Thread.sleep(1000);
                        setFields(driver, locationsBeforeClickPostSearch.get(1),
                                        ".uitk-field-input.uitk-typeahead-input.uitk-typeahead-input-v2", 1, "Berlin");
                        Thread.sleep(1000);

                        List<WebElement> datesBeforeClick7 = driver
                                        .findElements(By.cssSelector(".uitk-faux-input.uitk-form-field-trigger"));
                        datesBeforeClick7.get(0).click();
                        Thread.sleep(1200);

                        departureDateString = "5";
                        var departureDate7 = driver
                                        .findElements(By.cssSelector("[data-day=\"" + departureDateString + "\"]"));
                        departureDate7.get(0).click();

                        Thread.sleep(400);

                        arrivalDateString = "14";
                        var arrivalDate7 = driver
                                        .findElements(By.cssSelector("[data-day=\"" + arrivalDateString + "\"]"));
                        arrivalDate7.get(0).click();

                        List<WebElement> l = driver
                                        .findElements(By.tagName("button"));

                        l.get(98).click();
                        Thread.sleep(400);
                        l.get(101).click();
                        Thread.sleep(400);
                }
        }

        public static void setFields(WebDriver driver, WebElement field, String fieldPostClickClass, int index,
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

                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date = dateObj.format(formatter);
                String[] arrOfStr = date.split("-", 5);

                todayYear = arrOfStr[0];
                todayMonth = arrOfStr[1];
                todayDay = arrOfStr[2];
        }

        public static void main(String[] args) throws Exception {
                new FlightFinder().searchExpedia();
        }
}