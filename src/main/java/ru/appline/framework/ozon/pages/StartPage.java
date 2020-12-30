package ru.appline.framework.ozon.pages;

public class StartPage extends BasePage {

    public BucketPage goToBucket() {
        goToTheBucket();
        return app.getBucketPage();
    }

    public ResultsOfSearchPage searchingFor(String whatToSearch) {
        searchFor(whatToSearch);
        return app.getResultsOfSearchPage();
    }
}
