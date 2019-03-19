package designPatterns.decorator.iodemo;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 装饰流FilterInputStream，输出大写内容
 */
public class UpperCaseInputStream extends FilterInputStream {

    protected UpperCaseInputStream(InputStream in) {
        super(in);
    }

    public int read() throws IOException {
        int c = super.read();
        return c == -1 ? c : Character.toUpperCase((char) (c));
    }

    public int read(byte[] b, int offset, int len) throws IOException {
        int result = super.read(b, offset, len);
        for (int i = 0; i < result; i++) {
            b[i] = (byte) Character.toUpperCase((char) (b[i]));
        }

        return result;
    }
}
