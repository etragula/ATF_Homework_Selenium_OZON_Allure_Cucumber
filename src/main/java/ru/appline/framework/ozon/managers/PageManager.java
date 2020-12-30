package ru.appline.framework.ozon.managers;

import ru.appline.framework.ozon.pages.StartPage;
import ru.appline.framework.ozon.pages.BucketPage;
import ru.appline.framework.ozon.pages.ResultsOfSearchPage;

public class PageManager {

    private static PageManager pageManager;

    StartPage startPage;
    ResultsOfSearchPage resultsOfSearchPage;
    BucketPage bucketPage;

    private PageManager() {
    }

    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    public ResultsOfSearchPage getResultsOfSearchPage() {
        if (resultsOfSearchPage == null) {
            resultsOfSearchPage = new ResultsOfSearchPage();
        }
        return resultsOfSearchPage;
    }

    public BucketPage getBucketPage() {
        if (bucketPage == null) {
            bucketPage = new BucketPage();
        }
        return bucketPage;
    }
}
