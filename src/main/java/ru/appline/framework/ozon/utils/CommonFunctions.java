package ru.appline.framework.ozon.utils;

import io.qameta.allure.Allure;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ru.appline.framework.ozon.utils.Product.getListOfAllAddedProducts;

public class CommonFunctions {

    protected static int fromStringToInteger(String str) {
        return Integer.parseInt(str.replaceAll("\\D", ""));
    }

    protected void createProductInfoFile() {
        try {
            File file = new File("src/main/resources/ProductInfo.txt");
            if (!file.exists()) file.createNewFile();
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.printf("%s%89s%n%s%n", " Наименование товара", "Стоимость в рублях",
                    "-------------------------------------------------------------------------------------------------------------");
            for (int i = 0; i < getListOfAllAddedProducts().size(); i++) {
                if (i == 0)
                    printWriter.printf("%2d. %-86s%s%1.2f%n", i + 1, getListOfAllAddedProducts().get(i).getName(),
                            "max price - ", (float) getListOfAllAddedProducts().get(i).getPrice());
                else printWriter.printf("%2d. %-90s%15.2f%n", i + 1, getListOfAllAddedProducts().get(i).getName(),
                        (float) getListOfAllAddedProducts().get(i).getPrice());
            }
            printWriter.close();
            Allure.addAttachment("Файл со списком товаров:", "text/html",
                    new ByteArrayInputStream(Files.readAllBytes(Paths.get("src/main/resources/ProductInfo.txt"))),
                    "text");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void sleepForInterval(int interval) {
        try {
            Thread.sleep(interval);
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }
    }
}
