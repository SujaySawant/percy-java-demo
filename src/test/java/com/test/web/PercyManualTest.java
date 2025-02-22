package com.test.web;

import io.percy.selenium.Percy;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;

import static java.util.Optional.empty;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

public class PercyManualTest {

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub.browserstack.com/wd/hub";
//    private static final String TEST_URL = "https://asccw.playngonetwork.com/casino/ContainerLauncher?pid=2&gid=bookofdead&lang=en_GB&practice=1&channel=desktop&demo=2";
//    private static final String TEST_URL = "https://www.whatismyip.com";
    private static final String TEST_URL = "https://www.chanel.com/fr";

    private WebDriver driver;
    private Percy percy;

    @BeforeMethod(alwaysRun = true)
    public void setup(Method m) throws MalformedURLException {
        Map<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("projectName", "BrowserStack Percy");
        browserstackOptions.put("buildName", "Percy build");
        browserstackOptions.put("sessionName", m.getName());
        browserstackOptions.put("os", "Windows");
        browserstackOptions.put("osVersion", "11");
        browserstackOptions.put("browserVersion", "latest");
        browserstackOptions.put("userName", USERNAME);
        browserstackOptions.put("accessKey", ACCESS_KEY);
//        browserstackOptions.put("seleniumVersion", "4.0.0");
//        browserstackOptions.put("seleniumCdp", "true");
        browserstackOptions.put("idleTimeout", "300");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("bstack:options", browserstackOptions);

        driver = new RemoteWebDriver(new URL(HUB_URL), capabilities);
        percy = new Percy(driver);
    }

    @Test
    public void openHomePage2() throws InterruptedException {
//        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120), Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get(TEST_URL);
        //code to checkout page
        Thread.sleep(10000);
//        jsExecutor.executeScript("document.querySelectorAll('img')." + "forEach(img => {img.src = img.getAttribute('data-src') ?? img.src})");
//        percy.snapshot("Home Page", Arrays.asList(375), 1024, false, "#vehicle-image {transform: scale(5.20955) !important;}");
        percy.snapshot("Home Page", Arrays.asList(1280), 1024, false);
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
        driver.quit();
    }

}
