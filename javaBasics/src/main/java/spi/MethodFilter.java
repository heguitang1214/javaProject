package spi;

/**
 * Created by heguitang on 2019/6/9.
 */
public class MethodFilter implements Filter {
    @Override
    public void invoke() {
        System.out.println("is Method Filter......");
    }
}
