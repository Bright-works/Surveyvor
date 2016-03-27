package com.surveyvor.test.fonctionnel;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class GestionSondage {
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
  public void testGestionSondage() throws Exception {
    driver.get(baseUrl + "/Surveyvor/login.xhtml");
    driver.findElement(By.id("j_idt13:j_idt18")).click();
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("user.dirapp@gmail.com");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("toto");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("sarahboukris@hotmail.fr");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("djokernole");
    driver.findElement(By.id("j_idt34")).click();
    driver.findElement(By.id("j_idt29:nom")).clear();
    driver.findElement(By.id("j_idt29:nom")).sendKeys("");
    driver.findElement(By.id("j_idt29:j_idt36")).click();
    driver.findElement(By.id("j_idt29:nom")).clear();
    driver.findElement(By.id("j_idt29:nom")).sendKeys("Boukris");
    driver.findElement(By.linkText("Gérer mes sondages")).click();
    driver.findElement(By.cssSelector("label")).click();
    driver.findElement(By.name("j_idt13:j_idt23")).click();
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
    driver.findElement(By.id("myform:table:0:j_idt43")).click();
    driver.findElement(By.id("form2:message")).clear();
    driver.findElement(By.id("form2:message")).sendKeys("bonjour");
    driver.findElement(By.id("form2:j_idt46")).click();
    driver.findElement(By.id("myform:table:0:j_idt44")).click();
    driver.findElement(By.linkText("26")).click();
    driver.findElement(By.id("j_idt28:j_idt29")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
    driver.findElement(By.id("myform:table:1:j_idt46")).click();
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
