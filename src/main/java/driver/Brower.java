package driver;

/*create by shirley Li on 2016/1/14*/

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import config.GetfromYaml;
import config.NoConfigException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.util.List;

public class Brower {

    public static Brower instance;

    private WebDriver driver;
    private WebDriverWait wait;
    private static Logger logger = Logger.getLogger(Brower.class);


    public static Brower getInstance() throws MalformedURLException {
        if (instance == null) {
            instance = new Brower();
        }
        return instance;
    }

    //打开浏览器
    public void open() throws Exception {
        //配置环境
        GetfromYaml env = new GetfromYaml("RunEvnConfig/BrowserEnv.yaml");
        String runningEnv = env.getConf("RealEnv", "RealBrowser");
        open(runningEnv); //打开指定的浏览器
    }

    /**
     * 当打开浏览器后delete cookies
     */
    public void open(String runningEnv) throws Exception {
        logger.info("Running on :" + runningEnv);

        driver = BrowserDriver.Mdriver(runningEnv);
        deleteAllCookies();

        if (isReady())
            driver.manage().window().maximize();
    }

    //删除cookies
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }


    //打开url
    public void go(String url) {
        driver.get(url);
    }

    //输入框输入文本
    public void inputText(By by, String text) {
        driver.findElement(by).clear(); //清空输入框
        driver.findElement(by).sendKeys(text);
    }

    //执行js
    public void inputUEditorText(String text) {
        String script = "editor.setContent('<p>" + text + "</p>');";
        ((JavascriptExecutor) driver).executeScript(script);
    }

    public String readText(By by) {
        return driver.findElement(by).getText();
    }  //获取文本

    public void click(By by) {
        driver.findElement(by).click();
    }  //点击操作

    public String getTitle() {
        return driver.getTitle();
    }  //获取title

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }   //获取当前的url

    public void back() {
        driver.navigate().back();
    }  //浏览器后退

    public void forward() {
        driver.navigate().forward();
    }  //浏览器前进

    public void refresh() {
        driver.navigate().refresh();
    }  //刷新浏览器

    public void maximize() {
        driver.manage().window().maximize();
    }  //最大化浏览器窗口

    public void close() {
        driver.close();
    }  //关闭浏览器

    public void quit() {
        driver.quit();  //退出浏览器
    }

    //等待
    public void wait(int second) {
        driver.manage().timeouts().implicitlyWait(second, TimeUnit.SECONDS);
    }

    public void absolutelyWait(int second) throws InterruptedException {
        Thread.sleep(second * 1000);
    }

    //等待元素
    public void waitFor(By element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    //选择文本
    public void selectText(By target, String text) {
        Select select = new Select(driver.findElement(target));
        for (WebElement ops : select.getOptions()) {
            if (ops.getText().equals(text)) {
                ops.click();
                return;
            }
        }
    }

    //获取超链接文本
    public void logAllHyperLinkText() {

        List<WebElement> links = driver.findElements(By.tagName("a"));

        for (WebElement ops : links) {
            logger.info(ops.getText());
        }

    }

    //点击超链接文本
    public void clickLinkText(String text) {
        By target = By.linkText(text);
        driver.findElement(target).click();
    }

    //跳转到第一个窗口
    public void switchFirstWindow() {
        for (String winHandle : driver.getWindowHandles()) {

            switchToWindow(winHandle);
            return;
        }
    }

    //跳转到指定窗口
    private void switchToWindow(String winHandle) {
        logger.info("switched to " + winHandle);
        driver.switchTo().window(winHandle);
        logger.info(" on tittle : " + getTitle());
    }

    //跳转到最后一个窗口
    public void switchLastWindow() {
        int size = driver.getWindowHandles().size();
        if (size <= 1)
            return;

        for (String winHandle : driver.getWindowHandles()) {
            switchToWindow(winHandle);
        }
    }

    //进入Iframe
    public void switchFrame(String frameName) {
        driver.switchTo().frame(frameName);
    }

    public void switchFrame(By frameXpath) {
        WebElement ifame = driver.findElement(frameXpath);
        driver.switchTo().frame(ifame);
    }

    //退出iframe
    public void switchBack() {
        driver.switchTo().defaultContent();
    }

    public void uploadFile(By uploadBtn, String uploadFilePath) throws InterruptedException {
        WebElement upload = driver.findElement(uploadBtn);

        upload.sendKeys(uploadFilePath);
        absolutelyWait(1);
    }

    //返回当时页面的数据
    public void viewPageSource() {
        logger.info(driver.getPageSource());
    }

    public boolean isReady() {

        return driver != null;
    }

    //获取value值
    public String readValue(By by) {
        return getAttribute(by, "value");
    }

    //获取属性
    private String getAttribute(By by, String attribute) {
        return driver.findElement(by).getAttribute(attribute);
    }

    //点击alert框
    public void clickAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public void screenShot(String filePath) throws Exception{
        //这里等待页面加载完成
        Thread.sleep(5000);
        //下面代码是得到截图并保存在D盘下
        File screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenShotFile,new File(filePath));
    }
}
