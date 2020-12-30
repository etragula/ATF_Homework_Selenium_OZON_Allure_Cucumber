package ru.appline.framework.ozon.pages;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.framework.ozon.managers.PageManager;
import ru.appline.framework.ozon.utils.CommonFunctions;

import static ru.appline.framework.ozon.managers.DriverManager.getDriver;

public class BasePage extends CommonFunctions {

    @FindBy(xpath = "//input[@placeholder]")
    protected WebElement searchBlock;

    @FindBy(xpath = "//button[@type = 'submit']")
    protected WebElement searchButton;

    @FindBy(xpath = "//a[@data-widget='cart']")
    protected WebElement bucketButton;

    @FindBy(xpath = "//div[@data-widget='row']//strong")
    WebElement resultPageIsOpenedChecker;

    protected Actions action = new Actions(getDriver());

    protected PageManager app = PageManager.getPageManager();

    protected WebDriverWait wait = new WebDriverWait(getDriver(), 10, 1000);

    protected JavascriptExecutor js = (JavascriptExecutor) getDriver();

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
    }

    protected void searchFor(String whatToSearch) {
        if (whatToSearch.length() < 2) Assert.fail("Строка поиска должна содержать более одного символа!");
        clearAndFill(searchBlock, whatToSearch);
        clickElement(searchButton);
        wait.until(ExpectedConditions.textToBePresentInElement(resultPageIsOpenedChecker, whatToSearch.toLowerCase()));
    }

    protected void clickElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        action.moveToElement(element).click().build().perform();
    }

    protected void clearAndFill(WebElement element, String value) {
        if (value.equals("")) Assert.fail("Значение для заполнения не может быть пустым!");
        clickElement(element);
        while (!element.getAttribute("value").equals("")) {
            element.sendKeys(Keys.BACK_SPACE);
            element.sendKeys(Keys.DELETE);
        }
        element.sendKeys("" + value);
        Assert.assertTrue("Поле заполнено неверно!", element.getAttribute("value").equalsIgnoreCase(value));
    }

    protected void goToTheBucket() {
        clickElement(bucketButton);
        wait.until(ExpectedConditions.titleContains("корзина"));
    }

    protected void scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

}