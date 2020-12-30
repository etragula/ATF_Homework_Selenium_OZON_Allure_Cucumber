package ru.appline.framework.ozon.pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import ru.appline.framework.ozon.utils.Product;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.appline.framework.ozon.managers.DriverManager.getDriver;

public class ResultsOfSearchPage extends BasePage {

    @FindBy(xpath = "//a[@data-widget='cart']//span[1]")
    WebElement sizeOfBucket;

    @FindBy(xpath = "//div[contains(text(), 'Цена')]/..//input[@qa-id='range-to']")
    WebElement limitTheMaxPrice;

    @FindBy(xpath = "//div[contains(text(), 'Цена')]/..//input[@qa-id='range-from']")
    WebElement limitTheMinPrice;

    @FindBy(xpath = "//div[contains(@style, 'grid-column-start: span 12;')]")
    List<WebElement> listOfProducts;

    @FindBy(xpath = "//div[@data-widget = 'searchResultsFiltersActive']//span")
    List<WebElement> searchResultsActiveFilters;

    public BucketPage goToBucket() {
        goToTheBucket();
        return app.getBucketPage();
    }

    private void addProduct(WebElement product) {
        try {
            getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            if (product.findElements(By.xpath(".//span[text()='Express']")).size() == 0) {
                clickElement(product.findElement(By.xpath(".//div[text()='В корзину']")));
                Product.addAllAddedProductsToTheList(product);
                waitAddingProduct();
            }
        } catch (NoSuchElementException e) {
        } finally {
            getDriver().manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        }
    }

    private void waitAddingProduct() {
        for (int i = 0; i < 23; i++) {
            if (fromStringToInteger(sizeOfBucket.getText()) == Product.getListOfAllAddedProducts().size()) return;
        }
        sleepForInterval(300);
    }

    private void clickButtonShowAll(WebElement element) {
        try {
            getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            action.moveToElement(element.findElement(By.xpath(".//span[contains(text(),'Посмотреть все')]")))
                    .click().build().perform();
        } catch (NoSuchElementException e) {
        } finally {
            getDriver().manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        }
    }

    private void checkActiveFilterForPrice(WebElement element, int limitValue) {
        try {
            clearAndFill(element, "" + limitValue);
            element.sendKeys(Keys.ENTER);
            for (int i = 0; i < 23; i++) {
                for (WebElement filter : searchResultsActiveFilters) {
                    if (filter.getText().contains("" + String.valueOf(limitValue).charAt(0)
                            + String.valueOf(limitValue).charAt(1))) return;
                }
                sleepForInterval(300);
            }
        } catch (StaleElementReferenceException e) {
        }
    }

    private void checkActiveFilterForCheckBoxes(WebElement element, String checkBoxName) {
        try {
            action.moveToElement(element).click().build().perform();
            for (int i = 0; i < 23; i++) {
                for (WebElement filter : searchResultsActiveFilters) {
                    if (filter.getText().contains(checkBoxName)) return;
                }
                sleepForInterval(300);
            }
        } catch (StaleElementReferenceException e) {
        }
    }

    public ResultsOfSearchPage searchingFor(String whatToSearch) {
        searchFor(whatToSearch);
        return this;
    }

    public ResultsOfSearchPage limitThePrice(String rangeToLimit, int limitValue) {
        if ((!rangeToLimit.equalsIgnoreCase("максимальной") &&
                !rangeToLimit.equalsIgnoreCase("минимальной"))
                || limitValue < 0) {
            Assert.fail("Выберете ограничение (максимальной|минимальной) цены!" +
                    "\nЗначение не может быть отрицательным!");
        }
        WebElement range = rangeToLimit.equalsIgnoreCase("максимальной") ? limitTheMaxPrice : limitTheMinPrice;
        checkActiveFilterForPrice(range, limitValue);
        return this;
    }

    public ResultsOfSearchPage addProductsToTheBucket(int howManyProductsToAdd, String allOrOddOrEven) {
        int dividend = 1;
        int quotient = 0;
        quotient = allOrOddOrEven.equalsIgnoreCase("нечетных") ? 1 : 0;
        for (WebElement product : listOfProducts) {
            if (Product.getListOfAllAddedProducts().size() == howManyProductsToAdd) break;
            if (dividend % 2 == quotient || allOrOddOrEven.equalsIgnoreCase("")) addProduct(product);
            dividend++;
        }
        return this;
    }

    public ResultsOfSearchPage selectValueOfRoundCheckBoxes(String checkBoxName, boolean value) {
        if (checkBoxName.equals("")) Assert.fail("Введите имя чек-бокса!");
        WebElement checkBox = getDriver().findElement(By.xpath("//div[@value = '" + checkBoxName + "']//input"));
        if (checkBox.isSelected() ^ value) checkActiveFilterForCheckBoxes(checkBox, checkBoxName);
        return this;
    }

    public ResultsOfSearchPage selectValueOfSquareCheckBoxes(String blockName, String checkBoxName, boolean value) {
        if (checkBoxName.equals("") || blockName.equals("")) Assert.fail("Введите имена категории и чек-бокса!");
        WebElement checkBoxBlock = getDriver().findElement(By.xpath("//div[contains(text(), '" + blockName + "')]/.."));
        clickButtonShowAll(checkBoxBlock);
        WebElement checkBox = checkBoxBlock.findElement(By.xpath(".//span[text()='" + checkBoxName + "']//..//..//input"));
        if (checkBox.isSelected() ^ value) checkActiveFilterForCheckBoxes(checkBox, checkBoxName);
        return this;
    }
}