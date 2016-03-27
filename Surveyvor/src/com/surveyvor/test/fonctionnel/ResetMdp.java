package com.surveyvor.test.fonctionnel;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ResetMdp {
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
  public void testResetMdp() throws Exception {
    driver.get(baseUrl + "/Surveyvor/login.xhtml");
    driver.findElement(By.linkText("Mot de passe oublié ?")).click();
    driver.findElement(By.id("password1")).clear();
    driver.findElement(By.id("password1")).sendKeys("sarahboukris@hotmail.fr");
    // ERROR: Caught exception [ERROR: Unsupported command [selectFrame | undefined | ]]
    driver.findElement(By.cssSelector("div.recaptcha-checkbox-checkmark")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=vxqhymdrztw9 | ]]
    driver.findElement(By.cssSelector("td.rc-imageselect-tileselected > div.rc-image-tile-target > div.rc-image-tile-wrapper > img.rc-image-tile-33")).click();
    driver.findElement(By.cssSelector("td.rc-imageselect-tileselected > div.rc-image-tile-target > div.rc-image-tile-wrapper > img.rc-image-tile-33")).click();
    driver.findElement(By.xpath("(//img[contains(@src,'https://www.google.com/recaptcha/api2/payload?c=03AHJ_VutTCPEo8WWGCbrfu02esh_6fZdsieDMy5025Y1iMMhHfDf5mpy0yA2JSmaiiemNHMlIlowDIJXpxiAi0L_HSqjsnqatYEeCJT0-qi7M1y-PH33VzK2FbJe_BpVe76v4pzpFoKY8MfRw23dWTLVuXvORivvUtCCUgeIdXC4kMQQ7JQiyHznqlfxl5BGt7lc4cEoB1Njg0WTx71Ulg15Hdr6x8V4XAnkDIDdKLCLxJzKcLI11DO_QUjbEkIWgV_zYVdUTqRgLyNiiEkHVpJ3vfFEPDOesrxMBtZnI54bpLTUoiK5A0hn4ErqNqmNBgis5Owulqyea2ofKiCtq70OPZN975EvqeSk09XW5mryiiAZ1ZNEpkqCwbAkpgOYo6B5ivZ4xHDKu-Rohm54bh0yVn7NeR85rZQHujRTeSqK4RSd6sV_93H_7-4pe1m_MZ8DTfQrwfWZNaJiGolAZCckgLu86qJZXaDDn5nl76X8acrRV3GWaX20Pf_IHY4iQ4oQFCSBVObEOP9jZ9cMJRRt49EyfOUfKN3lPuIDfGQzrdp7svjOGzs9F1B6DrXlKtykkFo0ZNx2dUW0UbpAdy8v2hQ-_aQfulcFHWA6fQU-nPN4aN76EzykVb42j8JhFAU4mWzjLDCt1K3bZfhXGwNaTPQo3ff25CVR-TOmKVJPxwMNRHIz6KXK8WoXba7yBTwT7TPHiPco5DwkHjTMv89x2k2S_-DS30-wsxLnCPGExevl35vlb7uz4QSk4gKTBcUfwisd73J4tc3SVPy2XCcxwNB92z-qfHSExhT3DdagY-yrT4tU7bsyqYRZtK2_nBQ5jaKRzRFPMvcraPWSrwrPHqzQ2Pq-qhWb7SLKmmEEv0qg5tjjKjdMu7SJ3sdyLsQrNrqvU4cO1qI-5AtyAd4fh8p6ZnetEZw&k=6Lc70RkTAAAAADpDE2XNv4Y9losv-dbwsrur3-WE')])[6]")).click();
    driver.findElement(By.xpath("(//img[contains(@src,'https://www.google.com/recaptcha/api2/payload?c=03AHJ_VutTCPEo8WWGCbrfu02esh_6fZdsieDMy5025Y1iMMhHfDf5mpy0yA2JSmaiiemNHMlIlowDIJXpxiAi0L_HSqjsnqatYEeCJT0-qi7M1y-PH33VzK2FbJe_BpVe76v4pzpFoKY8MfRw23dWTLVuXvORivvUtCCUgeIdXC4kMQQ7JQiyHznqlfxl5BGt7lc4cEoB1Njg0WTx71Ulg15Hdr6x8V4XAnkDIDdKLCLxJzKcLI11DO_QUjbEkIWgV_zYVdUTqRgLyNiiEkHVpJ3vfFEPDOesrxMBtZnI54bpLTUoiK5A0hn4ErqNqmNBgis5Owulqyea2ofKiCtq70OPZN975EvqeSk09XW5mryiiAZ1ZNEpkqCwbAkpgOYo6B5ivZ4xHDKu-Rohm54bh0yVn7NeR85rZQHujRTeSqK4RSd6sV_93H_7-4pe1m_MZ8DTfQrwfWZNaJiGolAZCckgLu86qJZXaDDn5nl76X8acrRV3GWaX20Pf_IHY4iQ4oQFCSBVObEOP9jZ9cMJRRt49EyfOUfKN3lPuIDfGQzrdp7svjOGzs9F1B6DrXlKtykkFo0ZNx2dUW0UbpAdy8v2hQ-_aQfulcFHWA6fQU-nPN4aN76EzykVb42j8JhFAU4mWzjLDCt1K3bZfhXGwNaTPQo3ff25CVR-TOmKVJPxwMNRHIz6KXK8WoXba7yBTwT7TPHiPco5DwkHjTMv89x2k2S_-DS30-wsxLnCPGExevl35vlb7uz4QSk4gKTBcUfwisd73J4tc3SVPy2XCcxwNB92z-qfHSExhT3DdagY-yrT4tU7bsyqYRZtK2_nBQ5jaKRzRFPMvcraPWSrwrPHqzQ2Pq-qhWb7SLKmmEEv0qg5tjjKjdMu7SJ3sdyLsQrNrqvU4cO1qI-5AtyAd4fh8p6ZnetEZw&k=6Lc70RkTAAAAADpDE2XNv4Y9losv-dbwsrur3-WE')])[8]")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
    driver.findElement(By.id("j_idt32")).click();
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("user.dirapp@gmail.com");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("toto");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("sarahboukris@hotmail.fr");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("djokernole");
    driver.findElement(By.id("j_idt34")).click();
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
