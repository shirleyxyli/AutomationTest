package runEvnExample;


import config.Context;
import cucumber.api.java.en.Given;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import service.IsService;


@RunWith(Cucumber.class)
public class RunEvn extends Context {

    public RunEvn() throws Throwable {
        super("runEvnExample/baiduExample.yaml");
    }

    @Given("^在本地环境")
    public void 在本地环境() throws Throwable {
        logger.info("在本地环境运行");
        this.RunningEnv(IsService.LOCAL);
        brower.open();
    }

    @Given("^在远程环境")
    public void 在远程环境() throws  Throwable{
        logger.info("在远程环境运行");
        this.RunningEnv(IsService.Remote);
        brower.open("remote_firefox");
    }

    @Given("^关闭浏览器$")
    public void 关闭浏览器() throws Throwable {
        logger.info("关闭浏览器");

        brower.close();
    }

    @Given("^退出浏览器$")
    public void 退出浏览器() throws Throwable {
        logger.info("退出浏览器");

        brower.quit();
    }

}
