package utils.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 生成Excel文件的工具类
 *
 * @author libo
 */
public class ExcelUtil {


    public static void main(String[] args) {

//        List<SysUser> userList = new ArrayList<>();
//        List<Map<String,Object>> list=createExcelRecord(userList);
//        String columnNames[]={"姓名", "性别", "邮箱", "电话", "部门", "角色", "状态", "创建时间"};//列名
//        String keys[] = {"name", "gender", "email", "phone", "department", "role", "status", "createTime"};//map中的key
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        try {
//            ExcelUtil.createWorkBook(list,keys,columnNames).write(os);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        byte[] content = os.toByteArray();
//        InputStream is = new ByteArrayInputStream(content);
//        // 设置response参数，可以打开下载页面
//        res.reset();
//        res.setContentType("application/vnd.ms-excel;charset=utf-8");
//        res.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
//        ServletOutputStream out = res.getOutputStream();

    }


    private List<Map<String, Object>> createExcelRecord(List<SysUser> userList) {
        List<Map<String, Object>> listmap = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        SysUser user = null;
        for (int j = 0; j < userList.size(); j++) {
            user = userList.get(j);
            Map<String, Object> mapValue = new HashMap<>();
            mapValue.put("name", user.getName());
            mapValue.put("gender", (user.getGender() == 1) ? "男" : ((user.getGender() == 0) ? "女" : "保密"));
            mapValue.put("email", user.getEmail());
            mapValue.put("phone", user.getPhone());
//            mapValue.put("department", user.getDepartment().getName());
//            mapValue.put("role", user.getRole().getName());
            mapValue.put("status", user.getStatus() == 1 ? "启用" : "禁用");
            mapValue.put("createTime", user.getCreateTime().substring(0, 19));
            listmap.add(mapValue);
        }
        return listmap;
    }


    /**
     * 创建excel文档，
     *
     * @param list        数据
     * @param keys        list中map的key数组集合
     * @param columnNames excel的列名
     */
    public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String columnNames[]) {
        // 创建excel工作簿
        SXSSFWorkbook wb = new SXSSFWorkbook(100);//在内存中只保留100行记录,超过100就将之前的存储到磁盘里
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth(i, (int) (35.7 * 150));
        }
        // 创建第一行
        Row row = sheet.createRow(0);
        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());
        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);
        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (int i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow(i);
            // 在row行上创建一个方格
            for (int j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }


    static class SysUser {

        private Integer id;
        private String name;
        private Integer gender;
        private String email;
        private String loginPwd;
        private String phone;
        private String headImg;
        private Integer rolerd;
        private String createTime;
        private String lastLoginTime;
        private String updateTime;
        private Integer departmentId;
//        private Department department;
//        private Role role;
        private Integer status;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLoginPwd() {
            return loginPwd;
        }

        public void setLoginPwd(String loginPwd) {
            this.loginPwd = loginPwd;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public Integer getRolerd() {
            return rolerd;
        }

        public void setRolerd(Integer rolerd) {
            this.rolerd = rolerd;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Integer getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Integer departmentId) {
            this.departmentId = departmentId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }

}