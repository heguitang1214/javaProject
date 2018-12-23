package spring.aop.userAudit;


import java.time.LocalDateTime;

/**
 * 用户审计对应的实体类数据库:admin_test下的表用户审计对应的实体类
 */
public class UserAudit {

    /**
     * 主键ID
     */
    private Integer id = 0;
    /**
     * 创建人
     */
    private String creator = "";
    /**
     * 数据添加时间
     */
    private LocalDateTime createAt = LocalDateTime.now();
    /**
     * 子系统
     */
    private String module = "";
    /**
     * 操作类型枚举修改，删除，插入，上传
     */
    private OpTypeEnum opTypeEnum;
    /**
     * 操作内容
     */
    private String action = "";
    /**
     * 操作ip
     */
    private String ip = "";
    /**
     * 输入参数
     */
    private String inParam = "";
    /**
     * 输出参数
     */
    private String outParam = "";
    /**
     * 操作的url
     */
    private String urlPath = "";
    /**
     * ua信息
     */
    private String uaInfo = "";

    /**
     * 执行时长 毫秒
     */
    private Long executionTime = 0L;
    /**
     * 处理类全名
     */
    private String className = "";
    /**
     * 调用的方法名
     */
    private String methodName = "";
    /**
     * 错误信息
     */
    private String error = "";
    /**
     * 备注
     */
    private String remark = "";


    UserAudit() {
    }

    public Integer getId() {
        return id.intValue();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getInParam() {
        return inParam;
    }

    public void setInParam(String inParam) {
        this.inParam = inParam;
    }

    public String getOutParam() {
        return outParam;
    }

    public void setOutParam(String outParam) {
        this.outParam = outParam;
    }

    public String getUaInfo() {
        return uaInfo;
    }

    public void setUaInfo(String uaInfo) {
        this.uaInfo = uaInfo;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public OpTypeEnum getOpTypeEnum() {
        return opTypeEnum;
    }

    public void setOpTypeEnum(OpTypeEnum opTypeEnum) {
        this.opTypeEnum = opTypeEnum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


    @Override
    public String toString() {
        return "{" +
                "creator='" + creator + '\'' +
                ", createAt=" + createAt +
                ", module='" + module + '\'' +
                ", opTypeEnum=" + opTypeEnum +
                ", action='" + action + '\'' +
                ", ip='" + ip + '\'' +
                ", inParam='" + inParam + '\'' +
                ", outParam='" + outParam + '\'' +
                ", urlPath='" + urlPath + '\'' +
                ", uaInfo='" + uaInfo + '\'' +
                ", executionTime=" + executionTime +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", error='" + error + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}