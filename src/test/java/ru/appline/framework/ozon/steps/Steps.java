package ru.appline.framework.ozon.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.То;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import ru.appline.framework.ozon.managers.PageManager;

public class Steps {

    private PageManager app = PageManager.getPageManager();

    @Когда("^Переход на страницу Ozon$")
    public void getMainPage() {
        app.getStartPage();
    }

    @То("^Поиск по запросу \"([^\"]*)\"$")
    public void searchForProduct(String whatToSearch) {
        app.getStartPage().searchingFor(whatToSearch);
    }

    @Тогда("^Ограничиение \"([^\"]*)\" цены значением - (\\d+) рублей$")
    public void limitThePrice(String rangeToLimit, int limitValue) {
        app.getResultsOfSearchPage().limitThePrice(rangeToLimit, limitValue);
    }

    @Тогда("^Выбор значения для чебокса в блоке$")
    public void selectCheckBoxesValue(DataTable dataTable) {
        dataTable.cells().forEach(
                raw -> {
                    if (raw.get(0).equals("-")) {
                        app.getResultsOfSearchPage()
                                .selectValueOfRoundCheckBoxes(raw.get(1), Boolean.parseBoolean(raw.get(2)));
                    } else {
                        app.getResultsOfSearchPage()
                                .selectValueOfSquareCheckBoxes(raw.get(0), raw.get(1), Boolean.parseBoolean(raw.get(2)));
                    }
                }
        );
    }

    @Тогда("^Добавление в корзину (\\d+) \"([^\"]*)\" товаров$")
    public void addProducts(int howManyProductsToAdd, String allOrOddOrEven) {
        app.getResultsOfSearchPage().addProductsToTheBucket(howManyProductsToAdd, allOrOddOrEven);
    }

    @Тогда("^Переход в корзину$")
    public void goToTheBucket() {
        app.getResultsOfSearchPage().goToBucket();
    }

    @Тогда("^Проверка товаров в корзине$")
    public void checkProducts() {
        app.getBucketPage().checkProductsInTheBucket();
    }

    @Тогда("^Удаление всех товаров из корзины$")
    public void deleteAllProducts() {
        app.getBucketPage().deleteAllProductsInTheBucket();
    }

}
