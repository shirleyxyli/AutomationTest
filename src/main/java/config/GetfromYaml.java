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
//��ȡYaml�ļ�����
public class GetfromYaml {
    private Map map;
    private String absolutePath = null;
    private static Logger logger = Logger.getLogger(GetfromYaml.class);

    public GetfromYaml(String FilePath) throws Exception {
        File directory = new File("");//�趨Ϊ��ǰ�ļ���
        absolutePath = directory.getAbsolutePath();   //����·��

        FilePath = absolutePath + "/src/test/java/" + FilePath;  //����·��+�ļ�����·��

        YamlReader reader = new YamlReader(new FileReader(FilePath));
        //��ȡ YAML�ļ� into HashMaps, ArrayLists, and Strings
        Object object = reader.read();
        logger.debug(object);

        map = (Map) object;  //Map����
    }

    //��Key-Value��ƥ��
    public String getConf(String page, String element) throws NoConfigException {
        Map targetPage = (Map) map.get(page);  //����keyֵ�ҳ���Ӧ��valueֵ
        try {
            return targetPage.get(element).toString();
        } catch (Exception ex)  //���û�ж�Ӧ��value�ͱ��쳣
        {
            NoConfigException noConfigException = new NoConfigException(ex, page, element);
            logger.error(noConfigException);
            throw noConfigException;
        }
    }

    //��λҳ��Ԫ��id
    public By getId(String page, String id) throws NoConfigException {
        String conf = getConf(page, id);
        return By.id(conf);
    }

    //��λԪ��xpath
    public By getXPath(String page, String xPath) throws NoConfigException{
        String conf = getConf(page, xPath);
        return By.xpath(conf);
    }

    //��λԪ��css
    public By getCSS(String page, String css) throws NoConfigException{
        String conf = getConf(page, css);
        return By.cssSelector(conf);
    }

    //��λԪ��classname
    public By getClassName(String page, String className) throws NoConfigException{
        String conf = getConf(page, className);
        return By.className(conf);
    }

    //�����ļ�·��
    public String getFilePath(String page, String fileName) throws NoConfigException{
        String confPath = getConf(page, fileName);
        confPath = confPath.replaceAll("/", File.separator);

        String filePath = absolutePath + confPath;
        return filePath;
    }

    //����ͼƬ·��
    public String getImageFilePath(String fileName) {

        StringBuffer confPath = getDefaultPath();
        addSubFolder(confPath, fileName);

        String filePath = absolutePath + confPath.toString();
        return filePath;
    }

    //����Ĭ��·��
    private StringBuffer getDefaultPath() {
        StringBuffer confPath = new StringBuffer();

        addSubFolder(confPath, "src");
        addSubFolder(confPath, "main");
        addSubFolder(confPath, "resources");
        addSubFolder(confPath, "image");
        return confPath;
    }

    //�����ȡͼƬ
    public String getImageFilePath(String randomFolder,String randomFile) {

        StringBuffer confPath = getDefaultPath();
        addSubFolder(confPath, randomFolder);
        addSubFolder(confPath, randomFile);

        String filePath = absolutePath + confPath.toString();
        return filePath;
    }

    //����ļ���
    private void addSubFolder(StringBuffer confPath, String fileName) {
        confPath.append( File.separator + fileName);
    }

    //��ȡԶ��ͼƬ
    public String getDriverPath(String driverType, String driverName) {

        String confPath = File.separator + "drivers"
                + File.separator + driverType
                + File.separator + driverName;

        String filePath = absolutePath + confPath;
        return filePath;
    }

}
