import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

    private static Logger logger = LoggerFactory.getLogger(LogTest.class);


    @Test
    public void test(){
        logger.trace("trace级别日志......");
        logger.debug("debug级别日志......");
        logger.info("info级别日志......");
        logger.warn("warn级别日志......");
        logger.error("error级别日志......");
    }
}

//slf4j日志
//<dependencies>
//<dependency>
//<groupId>org.slf4j</groupId>
//<artifactId>slf4j-api</artifactId>
//<version>1.7.22</version>
//</dependency>
//</dependencies>

//logback依赖
//<dependencies>
//
//<dependency>
//<groupId>org.slf4j</groupId>
//<artifactId>slf4j-api</artifactId>
//<version>1.7.22</version>
//</dependency>
//
//<dependency>
//<groupId>ch.qos.logback</groupId>
//<artifactId>logback-classic</artifactId>
//<version>1.1.6</version>
//</dependency>
//
//</dependencies>
