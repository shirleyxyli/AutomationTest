package service;

//运行环境
public class IsService {

    //浏览器
    public static final String FIREFOX = "firefox";
    public static final String CHROME = "chrome";
    public static final String IE = "IE";

    //本地
    public static final String LOCAL = "local";

    //远程
    public static final  String Remote ="http";

    public static boolean isChrome(String browserName) {
        return browserName.equalsIgnoreCase(CHROME);
    }

    public static boolean isFirefox(String browserName) {
        return browserName.equalsIgnoreCase(FIREFOX);
    }

    public static boolean isIe(String browserName) {
        return browserName.equalsIgnoreCase(IE);
    }


    //是否远程环境运行
    public static boolean isRunAtLocalDriver(String runAt) {

        if (runAt.indexOf("http") >= 0)
            return false;

        return true;
    }

    //是否本地环境运行
    public static boolean isLocalEnv(String env) {

        if (env.indexOf(LOCAL) >= 0)
            return true;

        return false;
    }
}
