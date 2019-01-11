package entry;

import utils.excel.annotation.ExcelField;

/**
 * @Author heguitang
 * @Date 2019/1/7 14:35
 * @Version 1.0
 * @Desc
 */
public class ProvinceAndCity {

    @ExcelField(title="编号", align=2, sort=2)
    private String number;
    @ExcelField(title="描述", align=2, sort=2)
    private String desc;
    @ExcelField(title="编码", align=2, sort=2)
    private String code;
    @ExcelField(title="名称", align=2, sort=2)
    private String name;
    @ExcelField(title="上级编码", align=2, sort=2)
    private String parentCode;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public String toString() {
        return "ProvinceAndCity{" +
                "number='" + number + '\'' +
                ", desc='" + desc + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", parentCode='" + parentCode + '\'' +
                '}';
    }
}
