package com.test.web;

import io.percy.selenium.Percy;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEquals;

public class LocalWebTest {

    private WebDriver driver;
    private Percy percy;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        driver = new ChromeDriver();
        percy = new Percy(driver);
    }

//    @Test
    public void bStackDemoLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://bstackdemo.com");
        wait.until(elementToBeClickable(By.id("signin"))).click();
        wait.until(elementToBeClickable(By.cssSelector("#username .css-19bqh2r"))).click();
        driver.findElement(By.id("react-select-2-option-0-3")).click();
        driver.findElement(By.cssSelector("#password .css-19bqh2r")).click();
        driver.findElement(By.id("react-select-3-option-0-0")).click();
        driver.findElement(By.id("login-btn")).click();
        String username = wait.until(presenceOfElementLocated(By.className("username"))).getText();
        assertEquals(username, "fav_user", "Incorrect username");
    }

    @Test
    public void bStackDemoLogin1() throws InterruptedException {
        driver.get("https://asccw.playngonetwork.com/casino/ContainerLauncher?pid=2&gid=bookofdead&lang=en_GB&practice=1&channel=desktop&demo=2");
        Thread.sleep(30000);
        WebElement canvas = driver.findElement(By.tagName("canvas"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                canvas,
                "preserveDrawingBuffer",
                "true");
        Thread.sleep(5000);
        System.out.println("Canvas preserveDrawingBuffer attribute value: " + canvas.getAttribute("preserveDrawingBuffer"));
        percy.screenshot("");
        percy.snapshot("Home Page", Arrays.asList(1280), 1024, false);
    }
    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        driver.quit();
    }

}
