package config;

import driver.Brower;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shirley Li on 2016/1/13.
 */
public  class Context {

    protected Brower brower;
    protected GetfromYaml page;

    protected static Logger logger = Logger.getLogger(Context.class);
    private static Map<String, String> context = new ConcurrentHashMap<String, String>();
    private final String runningEnv = "RunningEnv";

    public Context(String pageName) throws Exception {
        page = new GetfromYaml(pageName);

        brower = Brower.getInstance();
    }

    //线程加锁
    public void RunningEnv(String env) {
        context.put(runningEnv, env);  //为了避免同步操作时对整个map对象进行锁定从而提高并发访问能力,采用concurrenthashmap
        logger.info("测试运行的环境: [" + runningEnv + " = " + env + " ]");
    }

}
