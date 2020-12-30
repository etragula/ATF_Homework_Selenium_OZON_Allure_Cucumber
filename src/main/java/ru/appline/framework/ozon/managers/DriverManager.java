package ru.appline.framework.ozon.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static ru.appline.framework.ozon.utils.PropertyConstants.TYPE_BROWSER;
import static ru.appline.framework.ozon.utils.PropertyConstants.PATH_TO_DRIVER;

public class DriverManager {

    private static PropertyManager properties = PropertyManager.getPropertyManager();

    private static WebDriver driver;

    private DriverManager() {
    }

    private static void initDriver() {
        switch (properties.getProperty(TYPE_BROWSER)) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", properties.getProperty(PATH_TO_DRIVER));
                driver = new FirefoxDriver();
                break;
            default:
                System.setProperty("webdriver.chrome.driver", properties.getProperty(PATH_TO_DRIVER));
                driver = new ChromeDriver();
        }
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public static void quitDriver() {
        driver.quit();
        driver = null;
    }
}
