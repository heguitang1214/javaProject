package reflect;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个list分别存obj1,obj2此属性名的值
 */
public class CompareEntity {

    /**
     * 比较两个实体属性值
     *
     * @param obj1        进行属性比较的对象1
     * @param obj2        进行属性比较的对象2
     * @param compareAttr 选择要比较的属性
     * @return 属性差异比较结果map
     */
    private static Map<String, Map<String, Object>> compareEntitys(Object obj1, Object obj2, List<String> compareAttr) {
        //返回值的容器
        Map<String, Map<String, Object>> resultMap = new LinkedHashMap<>();
        entityContrast(obj1, obj2, "E", resultMap, compareAttr);
        return resultMap;
    }

    /**
     * 实体的对比方法
     *
     * @param obj1      对象1
     * @param obj2      对象2
     * @param resultMap 返回Map结果
     */
    private static void entityContrast(Object obj1, Object obj2, String number, Map<String, Map<String, Object>> resultMap, List<String> excludeAttr) {
        try {
            //只有两个对象都是同一类型的才有可比性
            if (obj1.getClass().equals(obj2.getClass())) {
                Class clazz = obj1.getClass();
                String className = "";//clazz.getName().split("\\$")[1];
                //获取object的属性描述
                PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();
                List<String> baseType = Arrays.asList("java.lang.Integer", "java.lang.String", "java.lang.Boolean",
                        "java.math.BigDecimal", "java.time.LocalDateTime");
                // 这里就是所有的属性了
                for (PropertyDescriptor pd : propertyDescriptors) {
                    //获取属性名
                    String name = pd.getName();
                    if (excludeAttr != null && excludeAttr.contains(name)) {
                        continue;
                    }
                    // get方法
                    Method readMethod = pd.getReadMethod();
                    // 在obj1上调用get方法等同于获得obj1的属性值
                    Object objBefore = readMethod.invoke(obj1);
                    // 在obj2上调用get方法等同于获得obj2的属性值
                    Object objAfter = readMethod.invoke(obj2);
                    Type type = readMethod.getGenericReturnType();
                    System.out.println("类型为:" + type.getTypeName());
                    if (objBefore == null) {
                        addResultMap(resultMap, className + "_" + number + "_" + name, null, objAfter);
                    } else {
                        if (baseType.contains(type.getTypeName())) {
                            if (!objBefore.equals(objAfter)) {
                                addResultMap(resultMap, className + "_" + number + "_" + name, objBefore, objAfter);
                            }
                        } else if (type.getTypeName().contains("List")) {
                            List<Object> beforeList = (List<Object>) objBefore;
                            List<Object> afterList = (List<Object>) objAfter;
                            for (int i = 0; i < beforeList.size(); i++) {
                                entityContrast(beforeList.get(i), afterList.get(i), i + "", resultMap, excludeAttr);
                            }
                        } else if (type.getTypeName().contains("$")) {
                            //todo 实体的处理方式
                            entityContrast(objBefore, objAfter, "E", resultMap, excludeAttr);
                        } else {
                            //未知类型的处理方式
                            if (!objBefore.equals(objAfter)) {
                                addResultMap(resultMap, className + "_" + number + "_" + name, objBefore, objAfter);
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("ERROR", e.getMessage());
            resultMap.clear();
            resultMap.put("ERROR", m);
        }
    }

    /**
     * 返回值的封装
     *
     * @param resultMap 返回的Map
     * @param attrName  属性名
     * @param objBefore 属性值1
     * @param objAfter  属性值2
     */
    private static void addResultMap(Map<String, Map<String, Object>> resultMap, String attrName, Object objBefore, Object objAfter) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("objBefore", objBefore);
        map.put("objAfter", objAfter);
        resultMap.put(attrName, map);
    }


    private Object getObj(Class<Object> clazz, String nodeName, Object source) throws Exception {
        Method method = clazz.getMethod("get" + Character.toUpperCase(nodeName.substring(0, 1).toCharArray()[0]) + nodeName.substring(1));
        Object obj = null;
        //clazz.cast(source) 就是将一个对象装换为类或者接口。
        obj = method.invoke(clazz.cast(source), new Object[0]);
        return obj;
    }

    static class Dept {
        private Integer deptId;
        private String deptName;

        public Dept(Integer deptId, String deptName) {
            this.deptId = deptId;
            this.deptName = deptName;
        }

        public Integer getDeptId() {
            return deptId;
        }

        public void setDeptId(Integer deptId) {
            this.deptId = deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }
    }

    static class Entity {
        private Integer jobId;
        private String jobName;
        private Dept dept;

        public Dept getDept() {
            return dept;
        }

        public void setDept(Dept dept) {
            this.dept = dept;
        }

        Entity(Integer jobId, String jobName) {
            this.jobId = jobId;
            this.jobName = jobName;
        }

        public Integer getJobId() {
            return jobId;
        }

        public void setJobId(Integer jobId) {
            this.jobId = jobId;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        @Override
        public String toString() {
            return "Entity{" +
                    "jobId=" + jobId +
                    ", jobName='" + jobName + '\'' +
                    '}';
        }


    }

    static class TestBean {
        private Integer id;
        private String name;
        private Integer age;
        private Boolean aBoolean;
        private BigDecimal bigDecimal;
        private LocalDateTime localDateTime;
        private List<Entity> entityList;
        private Entity entity;

        public Boolean getaBoolean() {
            return aBoolean;
        }

        public void setaBoolean(Boolean aBoolean) {
            this.aBoolean = aBoolean;
        }

        public BigDecimal getBigDecimal() {
            return bigDecimal;
        }

        public void setBigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        public Entity getEntity() {
            return entity;
        }

        public void setEntity(Entity entity) {
            this.entity = entity;
        }

        public List<Entity> getEntityList() {
            return entityList;
        }

        public void setEntityList(List<Entity> entityList) {
            this.entityList = entityList;
        }

        public TestBean(Integer id, String name, Integer age, Boolean aBoolean, BigDecimal bigDecimal, LocalDateTime localDateTime) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.aBoolean = aBoolean;
            this.bigDecimal = bigDecimal;
            this.localDateTime = localDateTime;
        }

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

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "TestBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", entityList=" + entityList +
                    '}';
        }
    }

    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.of(2018, 10, 10, 10, 10);

        TestBean test1 = new TestBean(1, "大明125", 186, null, new BigDecimal("100"), time);
        Entity entity11 = new Entity(100, "CTO");
        Entity entity12 = new Entity(200, "CEO");
//        Dept dept1 = new Dept(12345, "技术部");
//        entity11.setDept(dept1);
        test1.setEntityList(Arrays.asList(entity11, entity12));
        test1.setEntity(entity11);

        TestBean test2 = new TestBean(12, "大明12", 186, false, new BigDecimal("1000"), null);
        Entity entity21 = new Entity(100, "CFO");
        Entity entity22 = new Entity(400, "CMO");
//        Dept dept2 = new Dept(1235, "人事部");
//        entity21.setDept(dept2);
        test2.setEntityList(Arrays.asList(entity21, entity22));
        test2.setEntity(entity21);

        List<String> column = Arrays.asList("1234", "23456", "entity");
        System.out.println(test1);

//        Type mySuperClass = test1.getClass().getGenericSuperclass();
//        Type tmp = ((ParameterizedType)mySuperClass).getActualTypeArguments()[0];
//        System.out.println(tmp);


        Map<String, Map<String, Object>> resultMap = compareEntitys(test1, test2, column);
        System.out.println("返回结果:" + resultMap);
        System.out.println("==========================================================================");
        Set<String> keySet = resultMap.keySet();
        for (String key : keySet) {
            Map<String, Object> value = resultMap.get(key);
            System.out.println("字段[" + key + "]不相同,A方的值是[" + value.get("objBefore") + "],B方的值是[" + value.get("objAfter") + "]");
        }
        System.out.println("==========================================================================");

    }


}
