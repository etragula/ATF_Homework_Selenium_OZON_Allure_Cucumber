package ru.appline.framework.ozon.managers;

import java.util.concurrent.TimeUnit;

import static ru.appline.framework.ozon.utils.PropertyConstants.*;
import static ru.appline.framework.ozon.managers.DriverManager.getDriver;
import static ru.appline.framework.ozon.managers.DriverManager.quitDriver;

public class InitManager {

    public static PropertyManager properties = PropertyManager.getPropertyManager();

    public static void initFramework() {
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(properties.getProperty(PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(properties.getProperty(IMPLICITY_WAIT)), TimeUnit.SECONDS);
        getDriver().get(properties.getProperty(APP_URL));
    }

    public static void quitFramework() {
        quitDriver();
    }
}
