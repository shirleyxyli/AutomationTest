package config;

public class NoConfigException extends Exception {

    public NoConfigException(Exception ex, String page, String element) {
        super("读取配置["+page+"."+element+"]出错！！ 格式不对、或值写错",ex);
    }
}
