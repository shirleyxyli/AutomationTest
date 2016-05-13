package runEvnExample;

import config.Context;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;


public class BaiduExample extends Context {


    public BaiduExample() throws Exception {
        super("runEvnExample/baiduExample.yaml");
    }

    @Given("打开浏览器到baidu.com")
    public void go_to_baidu_com() throws Exception {
        logger.info("打开浏览器到baidu.com");

        String url = page.getConf("baiduPage", "url");
        brower.go(url);
    }

    @And("^输入 \"(.*?)\"$")
    public void input_in_text(String keyWord) throws Throwable {
        logger.info("输入 " + keyWord);

        brower.inputText(page.getId("baiduPage", "input_id"), keyWord);
    }


    @When("^点击百度一下$")
    public void he_click_the_search_buttom() throws Throwable {
        logger.info("点击百度一下");

        brower.click(page.getId("baiduPage", "searchBtn_id"));
    }


    @Then("^检验搜索结果包含\"(.*?)\"$")
    public void he_should_be_see_search_result(String result) throws Throwable {
        logger.info("检查搜索结果包含："+result);

        brower.absolutelyWait(10);
        String searchResult = brower.readText(page.getXPath("baiduPage","result_xpath"));
        assertThat(searchResult, anyOf(containsString(result)));
    }

    @Then("^保存截图$")
    public void save_screenshot() throws Throwable {
        logger.info("保存截图");

        brower.screenShot("D:/test.png");
    }

}
