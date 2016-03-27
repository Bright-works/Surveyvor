package com.surveyvor.test.fonctionnel;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Inscription {
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
  public void testInscription() throws Exception {
    driver.get(baseUrl + "/Surveyvor/register.xhtml");
    driver.findElement(By.id("register:prenom")).clear();
    driver.findElement(By.id("register:prenom")).sendKeys("Sarah");
    driver.findElement(By.id("register:nom")).clear();
    driver.findElement(By.id("register:nom")).sendKeys("Boukris");
    driver.findElement(By.id("register:mail2")).clear();
    driver.findElement(By.id("register:mail2")).sendKeys("aaaaaaaaaa");
    driver.findElement(By.id("register:password2")).clear();
    driver.findElement(By.id("register:password2")).sendKeys("aaaaaaaaaaa");
    driver.findElement(By.id("register:confirmPassword")).clear();
    driver.findElement(By.id("register:confirmPassword")).sendKeys("zzzzzzzzzzzzz");
    // ERROR: Caught exception [ERROR: Unsupported command [selectFrame | undefined | ]]
    driver.findElement(By.cssSelector("div.recaptcha-checkbox-checkmark")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=2fem5pcrkz2e | ]]
    driver.findElement(By.cssSelector("td.rc-imageselect-tileselected > div.rc-image-tile-target > div.rc-image-tile-wrapper > img.rc-image-tile-33")).click();
    driver.findElement(By.xpath("(//img[contains(@src,'https://www.google.com/recaptcha/api2/payload?c=03AHJ_VuuP2TToIn2VUKB2i9az-Yg4-EV1KZMYtLNQcBCqWG2KchO3WX6bTP_vhYmnT7C1nu5YDV4u7YGKdblrBpxd145H4dMwICPRv8hh3I9nLFqVcYkFfdvrtS81Ft7f5QcGeE_a76YQAiC9beBazjWQusfufy124EL91g0YSVij516Rx5lMspAtY_IKKn9LeClwATgVC1azJxRbc013pz0OwjI7TWxUPCrHVbTOD9VPyoLgtK74eEEhreN5rtoFWq-L7gLosa29MeJ1I8mjfON_My7QU9Awg-5yd6EaYCRiFCc8D-d4eWfAw1M_fj148UcWnhG2QMik8KR7BEbFcxDXh8QOZp3jvI3GSncCa9HGy-8JfE1xLQnqWYPM1Du2pjAzJrzGV9heMTPYl4Nw5dtKQUCk9WGDdhA-necKKt4HZfZGMHQymJpbnTJhSKPoVbjeAa64SqDPb8JgC-c8uc-aghD2hVeclCT65YeIlfp0DF-LSzDkmtBd2wBHQauqwrlvQrj6UaOta6TbA0SNfPGEbR3oxKHWD0CfeekrnSI5rZxaIIh7B-vEaVinExI8h2IN3a3Nia4Dd8X95FMlcwzUcu2vy31vDxTAom-9981OfErfVh1Pq2HMXm_PHD5VlZfbuEVlYmdb8aTjnKYoU89M7E42Z61rDlASWqRX2XQndjv4VsgRm7yH0QZqxdPx6J6Bcdwa1wCXXtSb2AayganZ6XRK2vokfIH_j0F6VjzWbP-YGuhBc0c6xf8KZaSvQw7ByQq_vrHoh8vfg5UhKOJrtpviRxMUuxd2TjHfMWctDjXNixZ8aAmLXdoaDRs5OD3XpfPknyo8-nLYHbXC9vMkMwF3OPTzgOcWhdFcoMMJ1R9aQ5M4wY5ilaqRqCCZPPJKV_bfm22dQKUf3Za9k1g0ZdKEfWKhk8f9qQC87Ug-OaAXhb5Z3LoOfdqb3FrGqo-aD0R1Fuhw&k=6Lc70RkTAAAAADpDE2XNv4Y9losv-dbwsrur3-WE')])[5]")).click();
    driver.findElement(By.xpath("(//img[contains(@src,'https://www.google.com/recaptcha/api2/payload?c=03AHJ_VuuP2TToIn2VUKB2i9az-Yg4-EV1KZMYtLNQcBCqWG2KchO3WX6bTP_vhYmnT7C1nu5YDV4u7YGKdblrBpxd145H4dMwICPRv8hh3I9nLFqVcYkFfdvrtS81Ft7f5QcGeE_a76YQAiC9beBazjWQusfufy124EL91g0YSVij516Rx5lMspAtY_IKKn9LeClwATgVC1azJxRbc013pz0OwjI7TWxUPCrHVbTOD9VPyoLgtK74eEEhreN5rtoFWq-L7gLosa29MeJ1I8mjfON_My7QU9Awg-5yd6EaYCRiFCc8D-d4eWfAw1M_fj148UcWnhG2QMik8KR7BEbFcxDXh8QOZp3jvI3GSncCa9HGy-8JfE1xLQnqWYPM1Du2pjAzJrzGV9heMTPYl4Nw5dtKQUCk9WGDdhA-necKKt4HZfZGMHQymJpbnTJhSKPoVbjeAa64SqDPb8JgC-c8uc-aghD2hVeclCT65YeIlfp0DF-LSzDkmtBd2wBHQauqwrlvQrj6UaOta6TbA0SNfPGEbR3oxKHWD0CfeekrnSI5rZxaIIh7B-vEaVinExI8h2IN3a3Nia4Dd8X95FMlcwzUcu2vy31vDxTAom-9981OfErfVh1Pq2HMXm_PHD5VlZfbuEVlYmdb8aTjnKYoU89M7E42Z61rDlASWqRX2XQndjv4VsgRm7yH0QZqxdPx6J6Bcdwa1wCXXtSb2AayganZ6XRK2vokfIH_j0F6VjzWbP-YGuhBc0c6xf8KZaSvQw7ByQq_vrHoh8vfg5UhKOJrtpviRxMUuxd2TjHfMWctDjXNixZ8aAmLXdoaDRs5OD3XpfPknyo8-nLYHbXC9vMkMwF3OPTzgOcWhdFcoMMJ1R9aQ5M4wY5ilaqRqCCZPPJKV_bfm22dQKUf3Za9k1g0ZdKEfWKhk8f9qQC87Ug-OaAXhb5Z3LoOfdqb3FrGqo-aD0R1Fuhw&k=6Lc70RkTAAAAADpDE2XNv4Y9losv-dbwsrur3-WE')])[6]")).click();
    driver.findElement(By.xpath("(//img[contains(@src,'https://www.google.com/recaptcha/api2/payload?c=03AHJ_VuuP2TToIn2VUKB2i9az-Yg4-EV1KZMYtLNQcBCqWG2KchO3WX6bTP_vhYmnT7C1nu5YDV4u7YGKdblrBpxd145H4dMwICPRv8hh3I9nLFqVcYkFfdvrtS81Ft7f5QcGeE_a76YQAiC9beBazjWQusfufy124EL91g0YSVij516Rx5lMspAtY_IKKn9LeClwATgVC1azJxRbc013pz0OwjI7TWxUPCrHVbTOD9VPyoLgtK74eEEhreN5rtoFWq-L7gLosa29MeJ1I8mjfON_My7QU9Awg-5yd6EaYCRiFCc8D-d4eWfAw1M_fj148UcWnhG2QMik8KR7BEbFcxDXh8QOZp3jvI3GSncCa9HGy-8JfE1xLQnqWYPM1Du2pjAzJrzGV9heMTPYl4Nw5dtKQUCk9WGDdhA-necKKt4HZfZGMHQymJpbnTJhSKPoVbjeAa64SqDPb8JgC-c8uc-aghD2hVeclCT65YeIlfp0DF-LSzDkmtBd2wBHQauqwrlvQrj6UaOta6TbA0SNfPGEbR3oxKHWD0CfeekrnSI5rZxaIIh7B-vEaVinExI8h2IN3a3Nia4Dd8X95FMlcwzUcu2vy31vDxTAom-9981OfErfVh1Pq2HMXm_PHD5VlZfbuEVlYmdb8aTjnKYoU89M7E42Z61rDlASWqRX2XQndjv4VsgRm7yH0QZqxdPx6J6Bcdwa1wCXXtSb2AayganZ6XRK2vokfIH_j0F6VjzWbP-YGuhBc0c6xf8KZaSvQw7ByQq_vrHoh8vfg5UhKOJrtpviRxMUuxd2TjHfMWctDjXNixZ8aAmLXdoaDRs5OD3XpfPknyo8-nLYHbXC9vMkMwF3OPTzgOcWhdFcoMMJ1R9aQ5M4wY5ilaqRqCCZPPJKV_bfm22dQKUf3Za9k1g0ZdKEfWKhk8f9qQC87Ug-OaAXhb5Z3LoOfdqb3FrGqo-aD0R1Fuhw&k=6Lc70RkTAAAAADpDE2XNv4Y9losv-dbwsrur3-WE')])[8]")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
    driver.findElement(By.id("register:j_idt38")).click();
    driver.findElement(By.id("register:mail2")).clear();
    driver.findElement(By.id("register:mail2")).sendKeys("mail@mail.com");
    driver.findElement(By.id("register:nom")).clear();
    driver.findElement(By.id("register:nom")).sendKeys("");
    driver.findElement(By.id("register:j_idt38")).click();
    driver.findElement(By.id("register:nom")).clear();
    driver.findElement(By.id("register:nom")).sendKeys("boukris");
    // ERROR: Caught exception [ERROR: Unsupported command [selectFrame | undefined | ]]
    driver.findElement(By.cssSelector("div.recaptcha-checkbox-checkmark")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=o4g3dii7k287 | ]]
    driver.findElement(By.cssSelector("img.rc-image-tile-33")).click();
    driver.findElement(By.xpath("(//img[contains(@src,'https://www.google.com/recaptcha/api2/payload?c=03AHJ_VuvHo8xDHJkonJViD8ZJsxEq3pnm4ilxoZkgXS56Epdsf8xEX8QOJFoEaB8WR74TIuS36AcZttpv1XETWenhmmVx7CTmMUDYHgxHjCFU8kKxkTTJT1lpZiWgpZYDcRZvahNm2LKqNFwt4C3Z30-cTa9pS1LRnfiCpBk30_PAT_Nf7GgD4Hf_OzFUhvYC1JXDffEc0rex7HHa__WBX735eYS-NWPwQPf9ewadlwr51pjasBp-NBKhv-f_UmOxJ3KFenjBYGiJjnt3a-dhmdP51CL7xUi2bN0QxaaLJjsB8J9paoNfgIHzxL6YFJ_cOp-lgMZaj3yd6Ascymz8Z2-tq3rfE1NbNt0R5X0Vie4ts9hbPRWLscTyu21LDRI5nOCZAibFWn6pdbBPEZNUqg7YfO150ihv7t5n6DbNhC7TTjsqWwvxOwwQwZNMHoNGczZxQKjJzItrB9knV8toka-VZlsR-cRqBVSq4orgX2vWK99vR5yV2Xk4tHkLzvf1laDWPLKjs_H7M8T5orvYPacs44xd8ox-gVuxJY8OaTzrfKn6OCch2hfptvIZkGJKzcEJTCfUeLbfr7Y4INTxPK9PAn3BRRWRTUn_vAPQR1YisRqGQcq1vwdldpxY0ba3vdWJM98WsSFZix7rHcQYbwtoGLlVFaz5R2KD9Re7c5iYwjgJgjq1SUTQ-p5h3dpkJpwWqVjnk4ii1WHmDEE-NMhHy7P6KAcDwdNffOGSMUn3G5Eoh_-ILupsQYMXO8RCkprAnb8Sl6PcVBD-OZAT1u8w5zi-copAys_pXk4jhR5B0vm6ccyyvnv0JTO5VV9D16bKKLZ9pWe-PFXiWv_L-QRt8XYJJRaEdDBYQqXEV3OoHTutMJUzRyb78T4wtFXAC4-7Ei7CuxEUZb8ycAQ2rA06BFlfhRTIPV3O_-k03QIhbsLdz3QFaYiqF_bOMkGaghthOjz_n4Z3&k=6Lc70RkTAAAAADpDE2XNv4Y9losv-dbwsrur3-WE')])[2]")).click();
    driver.findElement(By.xpath("(//img[contains(@src,'https://www.google.com/recaptcha/api2/payload?c=03AHJ_VuvHo8xDHJkonJViD8ZJsxEq3pnm4ilxoZkgXS56Epdsf8xEX8QOJFoEaB8WR74TIuS36AcZttpv1XETWenhmmVx7CTmMUDYHgxHjCFU8kKxkTTJT1lpZiWgpZYDcRZvahNm2LKqNFwt4C3Z30-cTa9pS1LRnfiCpBk30_PAT_Nf7GgD4Hf_OzFUhvYC1JXDffEc0rex7HHa__WBX735eYS-NWPwQPf9ewadlwr51pjasBp-NBKhv-f_UmOxJ3KFenjBYGiJjnt3a-dhmdP51CL7xUi2bN0QxaaLJjsB8J9paoNfgIHzxL6YFJ_cOp-lgMZaj3yd6Ascymz8Z2-tq3rfE1NbNt0R5X0Vie4ts9hbPRWLscTyu21LDRI5nOCZAibFWn6pdbBPEZNUqg7YfO150ihv7t5n6DbNhC7TTjsqWwvxOwwQwZNMHoNGczZxQKjJzItrB9knV8toka-VZlsR-cRqBVSq4orgX2vWK99vR5yV2Xk4tHkLzvf1laDWPLKjs_H7M8T5orvYPacs44xd8ox-gVuxJY8OaTzrfKn6OCch2hfptvIZkGJKzcEJTCfUeLbfr7Y4INTxPK9PAn3BRRWRTUn_vAPQR1YisRqGQcq1vwdldpxY0ba3vdWJM98WsSFZix7rHcQYbwtoGLlVFaz5R2KD9Re7c5iYwjgJgjq1SUTQ-p5h3dpkJpwWqVjnk4ii1WHmDEE-NMhHy7P6KAcDwdNffOGSMUn3G5Eoh_-ILupsQYMXO8RCkprAnb8Sl6PcVBD-OZAT1u8w5zi-copAys_pXk4jhR5B0vm6ccyyvnv0JTO5VV9D16bKKLZ9pWe-PFXiWv_L-QRt8XYJJRaEdDBYQqXEV3OoHTutMJUzRyb78T4wtFXAC4-7Ei7CuxEUZb8ycAQ2rA06BFlfhRTIPV3O_-k03QIhbsLdz3QFaYiqF_bOMkGaghthOjz_n4Z3&k=6Lc70RkTAAAAADpDE2XNv4Y9losv-dbwsrur3-WE')])[5]")).click();
    driver.findElement(By.xpath("(//img[contains(@src,'https://www.google.com/recaptcha/api2/payload?c=03AHJ_VuvHo8xDHJkonJViD8ZJsxEq3pnm4ilxoZkgXS56Epdsf8xEX8QOJFoEaB8WR74TIuS36AcZttpv1XETWenhmmVx7CTmMUDYHgxHjCFU8kKxkTTJT1lpZiWgpZYDcRZvahNm2LKqNFwt4C3Z30-cTa9pS1LRnfiCpBk30_PAT_Nf7GgD4Hf_OzFUhvYC1JXDffEc0rex7HHa__WBX735eYS-NWPwQPf9ewadlwr51pjasBp-NBKhv-f_UmOxJ3KFenjBYGiJjnt3a-dhmdP51CL7xUi2bN0QxaaLJjsB8J9paoNfgIHzxL6YFJ_cOp-lgMZaj3yd6Ascymz8Z2-tq3rfE1NbNt0R5X0Vie4ts9hbPRWLscTyu21LDRI5nOCZAibFWn6pdbBPEZNUqg7YfO150ihv7t5n6DbNhC7TTjsqWwvxOwwQwZNMHoNGczZxQKjJzItrB9knV8toka-VZlsR-cRqBVSq4orgX2vWK99vR5yV2Xk4tHkLzvf1laDWPLKjs_H7M8T5orvYPacs44xd8ox-gVuxJY8OaTzrfKn6OCch2hfptvIZkGJKzcEJTCfUeLbfr7Y4INTxPK9PAn3BRRWRTUn_vAPQR1YisRqGQcq1vwdldpxY0ba3vdWJM98WsSFZix7rHcQYbwtoGLlVFaz5R2KD9Re7c5iYwjgJgjq1SUTQ-p5h3dpkJpwWqVjnk4ii1WHmDEE-NMhHy7P6KAcDwdNffOGSMUn3G5Eoh_-ILupsQYMXO8RCkprAnb8Sl6PcVBD-OZAT1u8w5zi-copAys_pXk4jhR5B0vm6ccyyvnv0JTO5VV9D16bKKLZ9pWe-PFXiWv_L-QRt8XYJJRaEdDBYQqXEV3OoHTutMJUzRyb78T4wtFXAC4-7Ei7CuxEUZb8ycAQ2rA06BFlfhRTIPV3O_-k03QIhbsLdz3QFaYiqF_bOMkGaghthOjz_n4Z3&k=6Lc70RkTAAAAADpDE2XNv4Y9losv-dbwsrur3-WE')])[9]")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
    driver.findElement(By.id("register:j_idt38")).click();
    driver.findElement(By.id("register:password2")).click();
    driver.findElement(By.id("register:password2")).click();
    driver.findElement(By.id("register:password2")).clear();
    driver.findElement(By.id("register:password2")).sendKeys("djokernole");
    driver.findElement(By.id("register:confirmPassword")).clear();
    driver.findElement(By.id("register:confirmPassword")).sendKeys("djokernole");
    driver.findElement(By.id("register:mail2")).click();
    driver.findElement(By.id("register:mail2")).click();
    driver.findElement(By.id("register:mail2")).clear();
    driver.findElement(By.id("register:mail2")).sendKeys("sarahboukris@hotmail.fr");
    // ERROR: Caught exception [ERROR: Unsupported command [selectFrame | undefined | ]]
    driver.findElement(By.cssSelector("div.recaptcha-checkbox-checkmark")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=xjqtsxh7kkue | ]]
    driver.findElement(By.cssSelector("td.rc-imageselect-tileselected > div.rc-image-tile-target > div.rc-image-tile-wrapper > img.rc-image-tile-33")).click();
    driver.findElement(By.cssSelector("td.rc-imageselect-tileselected > div.rc-image-tile-target > div.rc-image-tile-wrapper > img.rc-image-tile-33")).click();
    driver.findElement(By.xpath("(//img[contains(@src,'https://www.google.com/recaptcha/api2/payload?c=03AHJ_VuvNKXcVHSolvCsp-QPlMM3Ml50zlh8_A9BS39WZsB2uBYEUQFs77o-Ms72jBNzAo-6Uxov2eW6w7odDfnjYejzgOp8xITmzkthBBvV-uvSQPIod1Ki3h5Bb9l93f0E_D2IFI6FNe97s-dvmOOx7YGnQFqIirk3jP5O8O7T37oLqCDMV19oJSD3SOi39PSVvFNKesqko9rmARTgnyOnYVjmgTebbCakrUD_Z-PzW5wwWkAD6sL78o8PP9tL51sn4Kp90naltNoJaQrciSppOHSlMWNCU4FnhagQY-dgSd02USXwdLg8wOLRl1DTzdFBeS-WVLytwc_XBAZkaWBJEeJoW3G2bDaGzX3SlDDdazr-IN6YgC00oUJye2HFYbTCbsgsT81B13jSLVQLRAqY-4PyupDpCoMDoEReKsYyJ9gaLtcJxgNSwuaX-gFH_ouS9ddXjMsm5c1bRdLAi5sEt45jb9jjPR93EchUjfaHgmO5nhofsrTKNr2o5kpEfZJSSOz_psJxpjc_vS_I0FztTKe6aaMyn1M5u2A_hteuIW7wpCEA1yUtWoiBsjqwKSquXxOjHrV-fpCPeKW3aeeSbECeQwVUlkaxjlLn5-xbqAF05hYGfyDiyLhhT4LWSU1ySJ-B2dZAyb02IKoRyXSc-PeVBX0n-ItkOysXqY6AYfdpETdxI9WiFC1-SJPNKtdhyUxgtn8yuuuSmKnIRf-pRxz1_4CFAklud8uZiWpSdEoxXeAYskgEJDI_Lof86BlM_A7kfE0M6NlWf_QTPE1QPc3DGUEgWjsdm_CB7rSCgGw850DgKOPC26BNKxrtP7lGQyCrNmHT5x64cUdIyafRvHRCgqX6kDq3A49E34j8UOfRnu7pgPEdaS0v-Ky3qbH9SIJcPYLuoKF54awLu1zzUHmtuf5NoGGeTCheGSd_ItR2eXyzVfrI&k=6Lc70RkTAAAAADpDE2XNv4Y9losv-dbwsrur3-WE')])[5]")).click();
    driver.findElement(By.xpath("(//img[contains(@src,'https://www.google.com/recaptcha/api2/payload?c=03AHJ_VuvNKXcVHSolvCsp-QPlMM3Ml50zlh8_A9BS39WZsB2uBYEUQFs77o-Ms72jBNzAo-6Uxov2eW6w7odDfnjYejzgOp8xITmzkthBBvV-uvSQPIod1Ki3h5Bb9l93f0E_D2IFI6FNe97s-dvmOOx7YGnQFqIirk3jP5O8O7T37oLqCDMV19oJSD3SOi39PSVvFNKesqko9rmARTgnyOnYVjmgTebbCakrUD_Z-PzW5wwWkAD6sL78o8PP9tL51sn4Kp90naltNoJaQrciSppOHSlMWNCU4FnhagQY-dgSd02USXwdLg8wOLRl1DTzdFBeS-WVLytwc_XBAZkaWBJEeJoW3G2bDaGzX3SlDDdazr-IN6YgC00oUJye2HFYbTCbsgsT81B13jSLVQLRAqY-4PyupDpCoMDoEReKsYyJ9gaLtcJxgNSwuaX-gFH_ouS9ddXjMsm5c1bRdLAi5sEt45jb9jjPR93EchUjfaHgmO5nhofsrTKNr2o5kpEfZJSSOz_psJxpjc_vS_I0FztTKe6aaMyn1M5u2A_hteuIW7wpCEA1yUtWoiBsjqwKSquXxOjHrV-fpCPeKW3aeeSbECeQwVUlkaxjlLn5-xbqAF05hYGfyDiyLhhT4LWSU1ySJ-B2dZAyb02IKoRyXSc-PeVBX0n-ItkOysXqY6AYfdpETdxI9WiFC1-SJPNKtdhyUxgtn8yuuuSmKnIRf-pRxz1_4CFAklud8uZiWpSdEoxXeAYskgEJDI_Lof86BlM_A7kfE0M6NlWf_QTPE1QPc3DGUEgWjsdm_CB7rSCgGw850DgKOPC26BNKxrtP7lGQyCrNmHT5x64cUdIyafRvHRCgqX6kDq3A49E34j8UOfRnu7pgPEdaS0v-Ky3qbH9SIJcPYLuoKF54awLu1zzUHmtuf5NoGGeTCheGSd_ItR2eXyzVfrI&k=6Lc70RkTAAAAADpDE2XNv4Y9losv-dbwsrur3-WE')])[9]")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
    driver.findElement(By.id("register:j_idt38")).click();
    driver.findElement(By.id("register:password2")).click();
    driver.findElement(By.id("register:password2")).clear();
    driver.findElement(By.id("register:password2")).sendKeys("djokernole");
    driver.findElement(By.id("register:confirmPassword")).clear();
    driver.findElement(By.id("register:confirmPassword")).sendKeys("djokernole");
    driver.findElement(By.id("register:mail2")).click();
    driver.findElement(By.id("register:mail2")).clear();
    driver.findElement(By.id("register:mail2")).sendKeys("sarahboukris1@hotmail.fr");
    // ERROR: Caught exception [ERROR: Unsupported command [selectFrame | undefined | ]]
    driver.findElement(By.cssSelector("div.recaptcha-checkbox-checkmark")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=8e4fczrc8vb9 | ]]
    driver.findElement(By.cssSelector("td.rc-imageselect-tileselected > div.rc-image-tile-target > div.rc-image-tile-wrapper > img.rc-image-tile-33")).click();
    driver.findElement(By.id("recaptcha-verify-button")).click();
    driver.findElement(By.cssSelector("td.rc-imageselect-tileselected > div.rc-image-tile-target > div.rc-imageselect-checkbox")).click();
    driver.findElement(By.cssSelector("td.rc-imageselect-tileselected > div.rc-image-tile-target > div.rc-image-tile-wrapper > img.rc-image-tile-33")).click();
    driver.findElement(By.xpath("(//img[contains(@src,'https://www.google.com/recaptcha/api2/payload?c=03AHJ_VutIck2CF7Ux2FWG1vYyVaV9g7zZZ1xV5hOzmXslAS_yn0-H-QYrz5vGlkgpBedelm63MUKiH9P5jtcdVdLuv209ZNxGxgWdpL8gVZvyzeiJwKgKKfYweUQJhHRYTvIf6OdcoNMyyoLPa2QEtoSidgavKSNHyqFLdYsKmqm8ocRmQQ_fGsl9eHiXsMMWDo7Uox0uVgK8f0AbHSpLiQ_RVIO6Z1g7bmw3YZ68bZ15U2g5ANUASJR-lxKYp9yI2SLeimOeUrfugOzmD0nNead7FEPCTfbQdlGELIPzfmtOyfLw77Ih3kyMLX4_14Gao2yNzUEa0HMvcgUNUON9JddkuAuLpX8TKwtHD5bXG1DS1Qid9ZgS3cCHS299dardS6zF7Mhys9_uLF9lYp1YZk9xISvhOdocCvmoxdbdkQAETOjYAW0HMqHeFJKzri7UIu1JhzFfTzTjXOHdyMiKYSYJ2EJdGb1MZhCMFHVIEGK_1CWaj06NFThXXU2xw67cYpyL9hkMBs-Mht07F7bXmFoR8C28bzyyMFR3ly1br5y4fSEj043XvsR901jELNvu-Ws86i9qYNBgWsA4As031qbfMnX_aM4RvWayH9G7E_NOWy7VtQTsUAffjwB44J4bbNTcucQkhu5xbBYyxG_LvcwoDShLPJqgP-TGJdX1G_9lk1LQ7iTiwscHVVOomIz88HfOM1Rsj1I2AAVKEAc-ITKGmv75E1TUuku7_kTKx6hBiZvWN34cQ5NHoDeZVS3RsiLs-S9ZAwvQ8PatvGclVRaMr2S-NjgQWTl6YrfvMiC6b7qXm9FzQ-JB6HF1C7Qlv82StXH3raBByNcjHX-BE7s8hNa7Y4xdvpxjqlvI0oyvtF6-iFZV-mh1Dnrzg7RGqYC4UusUh_0-Q63Xqg8iO2-HAKYdnsFoS1A2q3YJf8GG7e_bXeZbA5_Z85jL9Q654lYBjevsbR5b&k=6Lc70RkTAAAAADpDE2XNv4Y9losv-dbwsrur3-WE')])[4]")).click();
    driver.findElement(By.xpath("(//img[contains(@src,'https://www.google.com/recaptcha/api2/payload?c=03AHJ_VutIck2CF7Ux2FWG1vYyVaV9g7zZZ1xV5hOzmXslAS_yn0-H-QYrz5vGlkgpBedelm63MUKiH9P5jtcdVdLuv209ZNxGxgWdpL8gVZvyzeiJwKgKKfYweUQJhHRYTvIf6OdcoNMyyoLPa2QEtoSidgavKSNHyqFLdYsKmqm8ocRmQQ_fGsl9eHiXsMMWDo7Uox0uVgK8f0AbHSpLiQ_RVIO6Z1g7bmw3YZ68bZ15U2g5ANUASJR-lxKYp9yI2SLeimOeUrfugOzmD0nNead7FEPCTfbQdlGELIPzfmtOyfLw77Ih3kyMLX4_14Gao2yNzUEa0HMvcgUNUON9JddkuAuLpX8TKwtHD5bXG1DS1Qid9ZgS3cCHS299dardS6zF7Mhys9_uLF9lYp1YZk9xISvhOdocCvmoxdbdkQAETOjYAW0HMqHeFJKzri7UIu1JhzFfTzTjXOHdyMiKYSYJ2EJdGb1MZhCMFHVIEGK_1CWaj06NFThXXU2xw67cYpyL9hkMBs-Mht07F7bXmFoR8C28bzyyMFR3ly1br5y4fSEj043XvsR901jELNvu-Ws86i9qYNBgWsA4As031qbfMnX_aM4RvWayH9G7E_NOWy7VtQTsUAffjwB44J4bbNTcucQkhu5xbBYyxG_LvcwoDShLPJqgP-TGJdX1G_9lk1LQ7iTiwscHVVOomIz88HfOM1Rsj1I2AAVKEAc-ITKGmv75E1TUuku7_kTKx6hBiZvWN34cQ5NHoDeZVS3RsiLs-S9ZAwvQ8PatvGclVRaMr2S-NjgQWTl6YrfvMiC6b7qXm9FzQ-JB6HF1C7Qlv82StXH3raBByNcjHX-BE7s8hNa7Y4xdvpxjqlvI0oyvtF6-iFZV-mh1Dnrzg7RGqYC4UusUh_0-Q63Xqg8iO2-HAKYdnsFoS1A2q3YJf8GG7e_bXeZbA5_Z85jL9Q654lYBjevsbR5b&k=6Lc70RkTAAAAADpDE2XNv4Y9losv-dbwsrur3-WE')])[6]")).click();
    driver.findElement(By.cssSelector("img.rc-image-tile-33")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
    driver.findElement(By.id("register:j_idt38")).click();
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("user.dirapp@gmail.com");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("toto");
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
