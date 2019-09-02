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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class Task01AuthTests {
    private WebDriver driver;
    private String baseUrl="http://automationpractice.com/index.php";


    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.setProperty("webdriver.gecko.driver","D:/Java/Lesson02/qaja07HWroot/driver/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testUntitledTestCase() {
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

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.quit();

    }

}

