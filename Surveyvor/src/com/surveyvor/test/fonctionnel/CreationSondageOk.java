package com.surveyvor.test.fonctionnel;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CreationSondageOk {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8081/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCreationSondageOk() throws Exception {
    driver.get(baseUrl + "/Surveyvor/index.xhtml");
    driver.findElement(By.id("j_idt10:connexion")).click();
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("user.dirapp@gmail.com");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("toto");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("sarahboukris@hotmail.fr");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("djokernole");
    driver.findElement(By.id("j_idt34")).click();
    driver.findElement(By.linkText("Créer un sondage")).click();
    driver.findElement(By.xpath("//table[@id='paramForm:visibility']/tbody/tr/td/div/div[2]/span")).click();
    driver.findElement(By.id("paramForm:visibility:0")).click();
    driver.findElement(By.id("paramForm:j_idt47")).click();
    driver.findElement(By.id("myform:title")).clear();
    driver.findElement(By.id("myform:title")).sendKeys("Quelle option de L3");
    driver.findElement(By.id("myform:desc")).clear();
    driver.findElement(By.id("myform:desc")).sendKeys("choisissez otre option de L3");
    driver.findElement(By.id("myform:question0")).clear();
    driver.findElement(By.id("myform:question0")).sendKeys("Choisissez votre option de L3");
    driver.findElement(By.id("myform:j_idt53")).clear();
    driver.findElement(By.id("myform:j_idt53")).sendKeys("web");
    driver.findElement(By.id("myform:j_idt55")).clear();
    driver.findElement(By.id("myform:j_idt55")).sendKeys("popo");
    driver.findElement(By.id("myform:j_idt61")).click();
    driver.findElement(By.id("form9:addToList")).clear();
    driver.findElement(By.id("form9:addToList")).sendKeys("sarahboukris@hotmail.fr");
    driver.findElement(By.id("form9:j_idt37")).click();
    driver.findElement(By.id("form9:j_idt44")).click();
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
