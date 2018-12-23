package utilsTest;

import entry.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.excel.ExportExcel;
import utils.excel.ImportExcel;
import utils.excel.annotation.ExcelConfEnum;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelTest {

    private static Logger log = LoggerFactory.getLogger(ExcelTest.class);

    @Test
    public void exportExcelTest() throws IOException {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("姓名" + i);
            user.setAge(i);
            list.add(user);
        }
        new ExportExcel(null, User.class, ExcelConfEnum.EXPORT_DATA.getIndex()).setDataList(list)
                .writeFile("D:/用户信息.xlsx").dispose();
        System.out.println("导出完成！");
    }


    @Test
    public void importExcelTest() throws Exception {
        ImportExcel importExcel = new ImportExcel(new File("D:/用户信息.xlsx"), 1, 0);
        List<User> list = importExcel.getDataList(User.class);
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void importExcelTest1() throws Exception {
        ImportExcel importExcel = new ImportExcel("D:/用户信息.xlsx", 1);
        List<User> list = importExcel.getDataList(User.class);
        for (User user : list) {
            System.out.println(user);
        }
    }


}
