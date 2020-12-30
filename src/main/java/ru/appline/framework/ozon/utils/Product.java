package ru.appline.framework.ozon.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.appline.framework.ozon.utils.CommonFunctions.fromStringToInteger;

public class Product {

    private static List<Product> listOfAllProductsInBucket = new ArrayList<>();
    private static List<Product> listOfAllAddedProducts = new ArrayList<>();
    private String name;
    private int price;

    public Product() {
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
        listOfAllAddedProducts.add(this);
    }

    private static void sortLists() {
        listOfAllAddedProducts = listOfAllAddedProducts.stream()
                .sorted(Comparator.comparing(Product::getName))
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .collect(Collectors.toList());
        listOfAllProductsInBucket = listOfAllProductsInBucket.stream()
                .sorted(Comparator.comparing(Product::getName))
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .collect(Collectors.toList());
    }

    public static boolean compareLists() {
        if (getListOfAllProductsInBucket().size() == getListOfAllAddedProducts().size()) {
            for (int i = 0; i < listOfAllProductsInBucket.size(); i++) {
                if (!listOfAllProductsInBucket.get(i).getName().equals(listOfAllAddedProducts.get(i).getName()))
                    return false;
                if (listOfAllProductsInBucket.get(i).getPrice() != listOfAllAddedProducts.get(i).getPrice())
                    return false;
            }
        }
        return true;
    }

    public static void addAllAddedProductsToTheList(WebElement product) {
        new Product(
                product.findElement(By.xpath(".//div[2]/div/a")).getText(),
                fromStringToInteger(product.findElement(By.xpath(".//div[contains(@class, 'itemasdasda')]/span[1]")).getText()));
        sortLists();
    }

    public static void addProductsFromTheBucketToTheList(WebElement product) {
        Product newProduct = new Product();
        newProduct.setName(product.findElement(By.xpath(".//span[@style]")).getText());
        newProduct.setPrice(fromStringToInteger(product.findElement(By.xpath(".//div[contains(@style, 'bold')]//span")).getText()));
        Product.getListOfAllProductsInBucket().add(newProduct);
        sortLists();
    }

    public static List<Product> getListOfAllAddedProducts() {
        return listOfAllAddedProducts;
    }

    public static List<Product> getListOfAllProductsInBucket() {
        return listOfAllProductsInBucket;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
