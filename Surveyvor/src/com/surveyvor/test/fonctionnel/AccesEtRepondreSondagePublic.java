package com.surveyvor.test.fonctionnel;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AccesEtRepondreSondagePublic {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass()
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8081/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAccesEtRepondreSondagePublic() throws Exception {
    driver.get(baseUrl + "/Surveyvor/index.xhtml");
    driver.findElement(By.id("j_idt28:j_idt29:j_idt32:searchCV")).clear();
    driver.findElement(By.id("j_idt28:j_idt29:j_idt32:searchCV")).sendKeys("");
    driver.findElement(By.id("j_idt28:j_idt29:j_idt32:searchCV")).clear();
    driver.findElement(By.id("j_idt28:j_idt29:j_idt32:searchCV")).sendKeys("projet");
    driver.findElement(By.id("j_idt28:j_idt29:j_idt32:searchCVButton")).click();
    driver.findElement(By.id("j_idt28:j_idt30:0:j_idt38")).click();
    driver.findElement(By.id("j_idt28:j_idt30:0:j_idt38")).click();
    driver.findElement(By.cssSelector("p")).click();
    driver.findElement(By.xpath("//tbody[@id='form1:j_idt42_data']/tr/td/div/div[2]/span")).click();
    driver.findElement(By.id("form1:j_idt50")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
  

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
