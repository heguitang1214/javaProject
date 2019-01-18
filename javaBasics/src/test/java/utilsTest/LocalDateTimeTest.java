package utilsTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * @Author heguitang
 * @Date 2019/1/18 11:14
 * @Version 1.0
 * @Desc
 */
public class LocalDateTimeTest {

    public static void main(String[] args) {

        LocalDateTime time=LocalDateTime.now();

        long sa= time.atZone(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli();

        System.out.println(time);
        System.out.println(Instant.ofEpochMilli(sa).atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime());

//        ZoneOffset.ofHours(8)

    }

}
