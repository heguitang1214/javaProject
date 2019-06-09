package spi;

/**
 * Created by heguitang on 2019/6/9.
 */
public class UrlFilter implements Filter {
    @Override
    public void invoke() {
        System.out.println("is Url Filter......");
    }
}
