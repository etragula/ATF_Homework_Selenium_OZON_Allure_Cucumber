package ru.appline.framework.ozon.steps;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.appline.framework.ozon.managers.InitManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static ru.appline.framework.ozon.managers.DriverManager.getDriver;

public class Hooks {

    @Before
    public void doBefore() {
        InitManager.initFramework();
    }

    @After
    public void doAfter(Scenario scenario) {
        if (scenario.isFailed()) {
            Allure.addAttachment("failureScreenshot", "image/png", addScreenshot(), "png");
        }
//        InitManager.quitFramework();
    }

    private static InputStream addScreenshot() {
        byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        return new ByteArrayInputStream(screenshot);
    }
}
