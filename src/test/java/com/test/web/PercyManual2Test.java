package com.test.web;

import io.percy.selenium.Percy;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

public class PercyManual2Test {

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub.browserstack.com/wd/hub";
    private static final String TEST_URL = "https://www.browserstack.com/docs";

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
        browserstackOptions.put("idleTimeout", "300");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("bstack:options", browserstackOptions);

        driver = new RemoteWebDriver(new URL(HUB_URL), capabilities);
        percy = new Percy(driver);
    }

    @Test
    public void openHomePage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2), Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get(TEST_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".site-home-intro")));
        Map<String, Object> options = new HashMap<>();
        options.put("scope", "//*[@class='site-home-intro']");
        options.put("widths", Arrays.asList(375, 1280, 1920));
        options.put("minHeight", 980);
        options.put("testCase", "openHomePage");
        percy.snapshot("Documentation", options);
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
        driver.quit();
    }

}
