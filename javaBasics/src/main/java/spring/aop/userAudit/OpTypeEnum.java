package spring.aop.userAudit;


/**
 * 审计操作的类型
 */
public enum OpTypeEnum {

    login("登录"),
    logout("登出"),
    insert("插入"),
    update("更新"),
    delete("删除"),
    submit("提交"),
    approve("审批"),
    upload("上传"),
    imports("导入"),
    export("导出"),
    print(" 打印"),
    cancel("取消");

    private String display;


    OpTypeEnum(String display) {
        this.display = display;
    }

    public String display() {
        return display;
    }


    public static OpTypeEnum fromName(String name) {
        for (OpTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static OpTypeEnum fromId(Integer id) {
        for (OpTypeEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }
}
