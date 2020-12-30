package ru.appline.framework.ozon;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm",
                "progress",
                "summary"},
        features = "src/test/resources/features/",
        glue = "ru/appline/framework/ozon/steps",
        tags = "@ozonTest"
)

public class CucumberRunner {
}
