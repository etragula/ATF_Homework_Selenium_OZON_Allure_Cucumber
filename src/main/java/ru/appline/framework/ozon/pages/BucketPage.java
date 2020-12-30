package ru.appline.framework.ozon.pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.appline.framework.ozon.utils.Product;

import java.util.List;

public class BucketPage extends BasePage {

    @FindBy(xpath = "//h1[contains(text(), 'Корзина пуста')]")
    WebElement bucketIsEmpty;

    @FindBy(xpath = "//span[contains(text(), 'Удалить выбранные')]")
    WebElement deleteAllSelected;

    @FindBy(xpath = "//div[contains(text(), 'Удалить')]")
    WebElement confirmDeleteAllSelected;

    @FindBy(xpath = "//span/span[contains(text() ,'Товары')]")
    WebElement amountOfProductInTheBucket;

    @FindBy(xpath = "//div[@data-widget='split']//label/../..")
    List<WebElement> listOfProductsInTheBucket;

    public ResultsOfSearchPage searchingFor(String whatToSearch) {
        searchFor(whatToSearch);
        return app.getResultsOfSearchPage();
    }

    public BucketPage checkProductsInTheBucket() {
        wait.until(ExpectedConditions.visibilityOfAllElements(listOfProductsInTheBucket));
        for (WebElement product : listOfProductsInTheBucket) Product.addProductsFromTheBucketToTheList(product);
        Assert.assertEquals("Количество добавленных товаров не совпадает с их количеством в корзине!",
                Product.getListOfAllAddedProducts().size(),
                fromStringToInteger(amountOfProductInTheBucket.getText()));
        Assert.assertTrue("Список добавленных товаров не совпадает со списком товаров в корзине!",
                Product.compareLists());
        createProductInfoFile();
        return this;
    }

    public BucketPage deleteAllProductsInTheBucket() {
        clickElement(deleteAllSelected);
        clickElement(confirmDeleteAllSelected);
        for (int i = 0; i < 23; i++) {
            if (bucketIsEmpty.isDisplayed()) {
                Product.getListOfAllAddedProducts().clear();
                Product.getListOfAllProductsInBucket().clear();
                return this;
            }
            sleepForInterval(200);
        }
        return this;
    }
}
