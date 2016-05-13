package config;

import com.esotericsoftware.yamlbeans.YamlReader;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

/**
 * Created by shirley L on 2016/1/13.
 */
//获取Yaml文件的类
public class GetfromYaml {
    private Map map;
    private String absolutePath = null;
    private static Logger logger = Logger.getLogger(GetfromYaml.class);

    public GetfromYaml(String FilePath) throws Exception {
        File directory = new File("");//设定为当前文件夹
        absolutePath = directory.getAbsolutePath();   //绝对路径

        FilePath = absolutePath + "/src/test/java/" + FilePath;  //绝对路径+文件所在路径

        YamlReader reader = new YamlReader(new FileReader(FilePath));
        //读取 YAML文件 into HashMaps, ArrayLists, and Strings
        Object object = reader.read();
        logger.debug(object);

        map = (Map) object;  //Map对象
    }

    //（Key-Value）匹配
    public String getConf(String page, String element) throws NoConfigException {
        Map targetPage = (Map) map.get(page);  //根据key值找出对应的value值
        try {
            return targetPage.get(element).toString();
        } catch (Exception ex)  //如果没有对应的value就报异常
        {
            NoConfigException noConfigException = new NoConfigException(ex, page, element);
            logger.error(noConfigException);
            throw noConfigException;
        }
    }

    //定位页面元素id
    public By getId(String page, String id) throws NoConfigException {
        String conf = getConf(page, id);
        return By.id(conf);
    }

    //定位元素xpath
    public By getXPath(String page, String xPath) throws NoConfigException{
        String conf = getConf(page, xPath);
        return By.xpath(conf);
    }

    //定位元素css
    public By getCSS(String page, String css) throws NoConfigException{
        String conf = getConf(page, css);
        return By.cssSelector(conf);
    }

    //定位元素classname
    public By getClassName(String page, String className) throws NoConfigException{
        String conf = getConf(page, className);
        return By.className(conf);
    }

    //配置文件路径
    public String getFilePath(String page, String fileName) throws NoConfigException{
        String confPath = getConf(page, fileName);
        confPath = confPath.replaceAll("/", File.separator);

        String filePath = absolutePath + confPath;
        return filePath;
    }

    //配置图片路径
    public String getImageFilePath(String fileName) {

        StringBuffer confPath = getDefaultPath();
        addSubFolder(confPath, fileName);

        String filePath = absolutePath + confPath.toString();
        return filePath;
    }

    //配置默认路径
    private StringBuffer getDefaultPath() {
        StringBuffer confPath = new StringBuffer();

        addSubFolder(confPath, "src");
        addSubFolder(confPath, "main");
        addSubFolder(confPath, "resources");
        addSubFolder(confPath, "image");
        return confPath;
    }

    //随机获取图片
    public String getImageFilePath(String randomFolder,String randomFile) {

        StringBuffer confPath = getDefaultPath();
        addSubFolder(confPath, randomFolder);
        addSubFolder(confPath, randomFile);

        String filePath = absolutePath + confPath.toString();
        return filePath;
    }

    //添加文件夹
    private void addSubFolder(StringBuffer confPath, String fileName) {
        confPath.append( File.separator + fileName);
    }

    //获取远程图片
    public String getDriverPath(String driverType, String driverName) {

        String confPath = File.separator + "drivers"
                + File.separator + driverType
                + File.separator + driverName;

        String filePath = absolutePath + confPath;
        return filePath;
    }

}
