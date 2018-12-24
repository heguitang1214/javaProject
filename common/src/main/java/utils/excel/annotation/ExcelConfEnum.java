package utils.excel.annotation;

public enum ExcelConfEnum {

    EXPORT_DATA("EXCEL导出数据", 1),
    EXPORT_TEMPLATE("EXCEL导出模板", 2),
    ROW_ACCESS_WINDOW_SIZE("指定的内存中缓存记录数", 500),
    EXPORT_AND_IMPORT("导出和导入", 0),
    ONLY_EXPORT("仅导出", 1),
    ONLY_IMPORT("仅导入", 2),
    SHEET_NAME("sheet页名称", "exportd");

    private String display;
    private int index;
    private String desc;

    ExcelConfEnum(String display, int index) {
        this.display = display;
        this.index = index;
    }

    ExcelConfEnum(String display, String desc) {
        this.display = display;
        this.desc = desc;
    }


    public String display() {
        return this.display;
    }

    public int getIndex() {
        return this.index;
    }

    public String getDesc() {
        return this.desc;
    }

    public static ExcelConfEnum fromName(String name) {
        for (ExcelConfEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }


}
