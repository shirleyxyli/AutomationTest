package service;

//���л���
public class IsService {

    //�����
    public static final String FIREFOX = "firefox";
    public static final String CHROME = "chrome";
    public static final String IE = "IE";

    //����
    public static final String LOCAL = "local";

    //Զ��
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


    //�Ƿ�Զ�̻�������
    public static boolean isRunAtLocalDriver(String runAt) {

        if (runAt.indexOf("http") >= 0)
            return false;

        return true;
    }

    //�Ƿ񱾵ػ�������
    public static boolean isLocalEnv(String env) {

        if (env.indexOf(LOCAL) >= 0)
            return true;

        return false;
    }
}
