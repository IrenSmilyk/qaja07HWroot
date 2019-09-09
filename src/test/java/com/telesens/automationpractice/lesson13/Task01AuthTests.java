/*1) Используя Katalon Recorder протестировать корректный ввод логина и пароля для сайта http://automationpractice.com
        a) Сценарий:
        - перейти по ссылке http://automationpractice.com/index.php
        - cделать клик по ссылке вверху 'Sign in'
        - заполнить поля 'Email address' и 'Password'
        - нажать зеленую кнопку внизу 'Sign in'
        - убедиться, что в верхнем меню отображается гиперссылка с именем пользователя:  Contact us | Sign out | 'Username'
        - сделать выход, нажав в верхнем меню гиперссылку Sign out

        b) Экспортировать тест в код Java+TesnNg

        c) Запустить тест под управлением Maven*/

package com.telesens.automationpractice.lesson13;

import au.com.bytecode.opencsv.CSVReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class Task01AuthTests {
    private WebDriver driver;
    private String baseUrl = "http://automationpractice.com/index.php";
    Properties prop = new Properties();


    @BeforeClass(alwaysRun = true)
    public void setUp() {
        String driverFile = null;
        File file = new File("src/main/resources/automationpractice.properties");
        try (FileInputStream fis = new FileInputStream(file)) {
            prop.load(fis);
            driverFile = prop.getProperty("driver.exe");

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setProperty("webdriver.gecko.driver", driverFile);
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testAuthSuccessfull() {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("ismilyk@yopmail.com");
        driver.findElement(By.id("passwd")).click();
        driver.findElement(By.id("passwd")).clear();
        driver.findElement(By.id("passwd")).sendKeys("ismilyk20");
        driver.findElement(By.xpath("//button[@id='SubmitLogin']/span")).click();
        assertEquals(driver.findElement(By.xpath("//a[@title='View my customer account']/span[text()]")).getText(), "i s");
        driver.findElement(By.linkText("Sign out")).click();

    }

    @Test
    public void testAuthUnsuccessfull() {
        String csvUrl = null;
        File file = new File("src/main/resources/automationpractice.properties");
        try (FileInputStream fis = new FileInputStream(file)) {
            prop.load(fis);
            csvUrl = prop.getProperty("automation.auth.data.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.get(baseUrl);
        try (CSVReader reader = new CSVReader(new FileReader(csvUrl), ',', '"', 1)) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine != null) {
                    driver.findElement(By.linkText("Sign in")).click();
                    driver.findElement(By.id("email")).click();
                    driver.findElement(By.id("email")).clear();
                    driver.findElement(By.id("email")).sendKeys(nextLine[0]);
                    driver.findElement(By.id("passwd")).click();
                    driver.findElement(By.id("passwd")).clear();
                    driver.findElement(By.id("passwd")).sendKeys(nextLine[1]);
                    driver.findElement(By.xpath("//button[@id='SubmitLogin']/span")).click();
                    assertEquals(driver.findElement(By.xpath("//div[@class='alert alert-danger']/ol/li[text()]")).getText(), nextLine[2]);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCategoryWoman() {
        String name="Women";
        driver.get(baseUrl);
        driver.findElement(By.xpath("//a[@title='Women']")).click();
        String title=driver.getTitle();
        String [] titleArr=title.split("\\s+");
        Assert.assertEquals(titleArr[0],name);
        Assert.assertEquals(driver.findElement(By.xpath("//a[@title='Women']")).getText(),"WOMEN");
        Assert.assertEquals(driver.findElement(By.xpath("//h2[@class='title_block']")).getText(),"WOMEN");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@class='category-name']")).getText(),name);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='rte']/p[1]")).getText(),"You will find here all woman fashion collections.");
        String text=driver.findElement(By.xpath("//span[@class='cat-name']")).getText();
        Assert.assertEquals(text.trim(),"WOMEN");
        Assert.assertEquals(driver.findElement(By.xpath("//a[@class='selected']")).getText(),name);

    }

   /* @Test
    public void testAddressAdd() {
       driver.get(baseUrl);
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("ismilyk@yopmail.com");
        driver.findElement(By.id("passwd")).click();
        driver.findElement(By.id("passwd")).clear();
        driver.findElement(By.id("passwd")).sendKeys("ismilyk20");
        driver.findElement(By.xpath("//button[@id='SubmitLogin']/span")).click();
        driver.findElement(By.xpath("//a[@title='Addresses']")).click();
        driver.findElement(By.xpath("//a[@title='Add an address']")).click();


    }*/

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.quit();

    }

}

