package driver;

import config.GetfromYaml;
import config.NoConfigException;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static service.IsService.*;

/**
 * Created by shirley Li on 2016/1/12.
 */
//启动浏览器
public class BrowserDriver {

    private static Logger logger = Logger.getLogger(BrowserDriver.class);

    public static WebDriver Mdriver(String BrowserEnv) throws Exception {

        WebDriver result = null;

        GetfromYaml env = new GetfromYaml("RunEvnConfig/BrowserEnv.yaml");  //读取浏览器环境文件

        DesiredCapabilities desirecap = BrowserCapabilities(BrowserEnv, env);  //加载了浏览器的配置设定


        String runAt = env.getConf(BrowserEnv, "RunAt");
       // logger.info("浏览器: " + runAt );  //打印日记


        //本地运行
        if (isRunAtLocalDriver(runAt)) {
            result = getLocalDriver(desirecap);
            logger.info("New Local Driver Start !!");
            return result;
        }
        //远程
        result = new RemoteWebDriver(new URL(runAt), desirecap);
        logger.info("New Remote WebDriver Start !!!");
        return result;
    }

    private static DesiredCapabilities BrowserCapabilities(String BrowserEnv, GetfromYaml env) throws NoConfigException {
        DesiredCapabilities desirecap = new DesiredCapabilities();
        //配置浏览器名称
        String browerName = env.getConf(BrowserEnv, "BrowserName");
        desirecap.setBrowserName(browerName);
        //版本
        String browerVersion = env.getConf(BrowserEnv, "Version");
        if (browerVersion != null && !browerVersion.equalsIgnoreCase("any"))
            desirecap.setVersion(browerVersion);

        String platform = env.getConf(BrowserEnv, "Platform");
        desirecap.setPlatform(getPlatform(platform));
        return desirecap;
    }

    private static WebDriver getLocalDriver(DesiredCapabilities desirecap) {
        WebDriver result = null;
        String browserName = desirecap.getBrowserName();

        //启动本机的火狐浏览器
        if (isFirefox(browserName)) {
            ProfilesIni allProfiles = new ProfilesIni();
            FirefoxProfile profile = allProfiles.getProfile("default");   //本机的profile已经配置好，然后用webdriver启动
            profile.setEnableNativeEvents(true); //启用默认情况下被firefox禁用的功能
            profile.setPreference("IsRelative", 0);  //设置profile中的一个preference

            result = new FirefoxDriver(profile); //使用特定的profile启动火狐浏览器
            return result;
        }
        //webdriver在启动浏览器时，启动的一个干净的没有任务、插件及cookies信息的浏览器

        if (isChrome(browserName)) {
            //通过webdriver.chrome.driver.系统属性实现
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
            result = new ChromeDriver();
            return result;
        }

        if (isIe(browserName)) {
            //通过webdriver.ie.driver.系统属性实现
            System.setProperty("webdriver.ie.driver", "C:\\Program Files (x86)\\Internet Explorer\\IEDriverServer.exe");
            DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
            ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            result = new InternetExplorerDriver(ieCapabilities);
            return result;
        }

        return result;
    }

    private static Platform getPlatform(String platform) {
        if (platform.equals("windows"))
            return Platform.WINDOWS;

        return Platform.ANY;  //其他操作平台
    }


}
