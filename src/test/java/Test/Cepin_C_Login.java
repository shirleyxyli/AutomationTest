package Test;

import config.Context;
import config.GetfromYaml;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.net.MalformedURLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by shirley Li
 */
public class Cepin_C_Login extends Context {

    public Cepin_C_Login() throws Exception {
        super("Test/Cepin_C_Login.yaml");
    }

    @When("^C端web用\"(.*?)\"登录\"(.*?)\"环境$")
    public void c端web用_登录_环境(String userNum, String envName) throws Throwable {
        logger.info("用" + userNum + "登录" + envName + "环境");

        GetfromYaml env = new GetfromYaml("runEvnConfig/Diffenv.yaml");

        String url = env.getConf(envName, "url");
        openUrl(url); //打开C端

        input_in_userNum(userNum); //输入账号

        String password = env.getConf(envName, userNum);
        input_in_pwd(password); //输入密码

        click_the_login_buttom(); //点击登录
    }

    @Then("^登录成功")
    public void 登录成功() throws Throwable{
        login_success_result(); //判断是否登录成功
    }

    public void openUrl(String url) throws MalformedURLException {
        logger.info("打开浏览器到" + url);

        brower.go(url);
    }

    public void input_in_userNum(String userNum) throws Throwable {
        logger.info("输入C端账号" + userNum);

        brower.inputText(page.getId("CepinPage", "input_id_account"), userNum);
    }

    public void input_in_pwd(String password) throws Throwable {
        logger.info("输入C端密码" + password);

        brower.inputText(page.getId("CepinPage", "input_id_password"), password);
    }

    public void click_the_login_buttom() throws Exception {
        logger.info("点击C端登录");

        brower.click(page.getXPath("CepinPage", "login_xpath"));

    }

    public void login_success_result() throws Exception {
        logger.info("登录成功，进入测聘首页");

        brower.absolutelyWait(3);
        assertThat(brower.getTitle(), is("测聘网-专注精准测评的智能招聘平台"));

        logger.info("页面标题为：" + brower.getTitle());


    }
}
