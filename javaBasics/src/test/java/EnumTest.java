import org.junit.Test;
import utils.excel.annotation.ExcelConfEnum;

public class EnumTest {

    @Test
    public void test1(){

        System.out.println(ExcelConfEnum.EXPORT_DATA.name());
        System.out.println(ExcelConfEnum.EXPORT_DATA.ordinal());
        System.out.println(ExcelConfEnum.EXPORT_DATA.getIndex());
        System.out.println(ExcelConfEnum.ONLY_EXPORT.getIndex());

    }

}
