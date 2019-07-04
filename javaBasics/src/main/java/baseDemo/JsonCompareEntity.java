package baseDemo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.baseDemo.ReadFile;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


/**
 * 利用Json比较实体的内容
 */
public class JsonCompareEntity {

    /**
     * 比较json内容，提供给对外调用
     *
     * @param beforeJson  json1
     * @param afterJson   json2
     * @param relEntities 对应字段映射关系
     * @return 返回List描述结果
     */
    private static List<String> compareEntitys(String beforeJson, String afterJson, List<RelEntity> relEntities) {
        //返回值的容器
        List<String> resultList = new ArrayList<>();
        if (relEntities == null || relEntities.size() == 0) {
            return resultList;
        }
        JSONObject beforeObject;
        JSONObject afterObject;
        try {
            beforeObject = JSONArray.parseObject(beforeJson);
            afterObject = JSONArray.parseObject(afterJson);
        } catch (Exception e) {
            resultList.add("JSON数据格式异常，转换失败");
            return resultList;
        }
        try {
            entityContrast(beforeObject, afterObject, relEntities, resultList);
        } catch (Exception e) {
            e.printStackTrace();
            resultList.clear();
            resultList.add("JSON数据对比出现未知异常");
            return resultList;
        }
        return resultList;
    }

    /**
     * 对象的对比方法
     *
     * @param beforeSrc   对象1
     * @param afterSrc    对象2
     * @param relEntities 映射关系
     * @param resultList  返回容器
     */
    private static void entityContrast(Object beforeSrc, Object afterSrc, List<RelEntity> relEntities, List<String> resultList) {
        //映射关系字段
        for (RelEntity relEntity : relEntities) {
            String[] beforeStr = relEntity.getPartyA().split("#");
            String[] afterStr = relEntity.getPartyB().split("#");
            LinkedList<String> beforeList = strTurnLinkedList(beforeStr[0]);
            LinkedList<String> afterList = strTurnLinkedList(afterStr[0]);
            LinkedList<String> beforeSort = new LinkedList<>();
            LinkedList<String> afterSort = new LinkedList<>();
            if (beforeStr.length > 1) {
                beforeSort = strTurnLinkedList(beforeStr[1]);
            }
            if (afterStr.length > 1) {
                afterSort = strTurnLinkedList(afterStr[1]);
            }
            analysisJson(beforeSrc, afterSrc,
                    beforeList, afterList, beforeSort, afterSort, relEntity, resultList, null);
        }
    }

    /**
     * JSON数据比较
     */
    private static void analysisJson(Object beforeData, Object afterData,
                                     LinkedList<String> beforeLinkedList, LinkedList<String> afterLinkedList,
                                     LinkedList<String> beforeSort, LinkedList<String> afterSort,
                                     RelEntity relEntity, List<String> resultList, Object sortpath) {

        Object beforeObj = getTargetDataByLinkedList(beforeData, beforeLinkedList, false, 0);
        Object afterObj = getTargetDataByLinkedList(afterData, afterLinkedList, false, 0);
        if (beforeObj instanceof JSONArray) {
            for (int i = 0; i < ((JSONArray) beforeObj).size(); i++) {
                Object beforeObject = ((JSONArray) beforeObj).get(i);
                Object objEntry = getTargetDataByLinkedList(beforeObject, beforeLinkedList, true, 0);
                //获取排序字段
                Object sort1 = getTargetDataByLinkedList(beforeObject, beforeSort, true, 0);
                //显示下标层级
//                if (sortpath != null && sort1 != null) {
//                    sortpath = sortpath.toString() + "_" + sort1.toString();
//                }
                //1.前一个是数组，后一个也是数组
                Object obj = null;
                if (afterObj instanceof JSONArray) {
                    for (int j = 0; j < ((JSONArray) afterObj).size(); j++) {
                        Object afterObject = ((JSONArray) afterObj).get(j);
                        Object sort2 = getTargetDataByLinkedList(afterObject, afterSort, true, 0);
                        if (sort1 != null && sort2 != null) {
                            if (sort1.toString().equals(sort2.toString())) {
                                obj = afterObject;
                                break;
                            }
                        } else {
                            if (i == j) {
                                obj = ((JSONArray) afterObj).get(i);
                                break;
                            }
                        }
                    }
                    if (obj != null) {
                        obj = getTargetDataByLinkedList(obj, afterLinkedList, true, 0);
                    }
                }
                if (objEntry instanceof JSONArray || obj instanceof JSONArray) {
                    String beforeFirstKey = null, afterFirstKey = null;
                    if (objEntry instanceof JSONArray) {
                        beforeFirstKey = beforeLinkedList.removeFirst();
                    }
                    if (obj instanceof JSONArray) {
                        afterFirstKey = afterLinkedList.removeFirst();
                    }
                    LinkedList<String> newBefore = new LinkedList<>();
                    newBefore.addFirst(beforeSort.getLast());
                    LinkedList<String> newAfter = new LinkedList<>();
                    newAfter.addFirst(afterSort.getLast());
                    analysisJson(objEntry, obj, beforeLinkedList, afterLinkedList, newBefore, newAfter, relEntity, resultList, sort1);
                    if (objEntry instanceof JSONArray) {
                        beforeLinkedList.addFirst(beforeFirstKey);
                    }
                    if (obj instanceof JSONArray) {
                        afterLinkedList.addFirst(afterFirstKey);
                    }
                    continue;
                }
                String sort;
                if (sortpath == null) {
                    sort = sort1 == null ? null : sort1.toString();
                } else {
                    sort = sortpath.toString();
                }
                objectTypeCompare(sort, objEntry, obj, relEntity, resultList);
//                objectTypeCompare(sort1 == null ? null : sort1.toString(), objEntry, obj, relEntity, resultList);
            }
            return;
        } else if (afterObj instanceof JSONArray) {
            //3.前一个是对象，后一个是集合，直接循环比较
            for (int i = 0; i < ((JSONArray) afterObj).size(); i++) {
                Object afterObject = ((JSONArray) afterObj).get(i);
                Object objEntry = getTargetDataByLinkedList(afterObject, afterLinkedList, true, 0);
                if (objEntry instanceof JSONArray) {
                    String firstKey = afterLinkedList.removeFirst();
                    analysisJson(beforeObj, objEntry, beforeLinkedList, afterLinkedList, beforeSort, afterSort, relEntity, resultList, null);
                    afterLinkedList.addFirst(firstKey);
                    continue;
                }
                objectTypeCompare(null, beforeObj, objEntry, relEntity, resultList);
            }
            return;
        }
        //4.前一个和后一个都是对象
        objectTypeCompare(null, beforeObj, afterObj, relEntity, resultList);
    }

    /**
     * 获取目标数据
     *
     * @param object     数据源
     * @param linkedList 路径
     * @param isSort     是否排序
     * @return 获取的数据
     */
    //todo 在isSort为true的条件下，不能保证排序链条的顺序没有发生改变，可以在调用这个方法的时候，进行处理
    private static Object getTargetDataByLinkedList(Object object, LinkedList<String> linkedList, boolean isSort, int number) {
        if (linkedList == null || linkedList.size() == 0) {
            return object;
        }
        if (object == null) {
            while (number != linkedList.size()) {
                linkedList.addLast(linkedList.removeFirst());
                ++number;
            }
            return null;
        }
        String firstData = linkedList.getFirst();
        if (object instanceof JSONObject) {
            Object obj = ((JSONObject) object).get(firstData);
            if (isSort) {
                if (linkedList.size() > 1) {
                    linkedList.addLast(linkedList.removeFirst());
                    ++number;
                }
            } else {
                linkedList.removeFirst();
            }
            object = getTargetDataByLinkedList(obj, linkedList, isSort, number);
        } else if (object instanceof JSONArray) {
//            JSONArray array = ((JSONArray) object);
//            if (array.size() == 1) {
//                Object obj = array.get(0);
//                Object o = ((JSONObject) obj).get(firstData);
//                linkedList.addLast(linkedList.removeFirst());
//                ++ number;
//                object = getTargetDataByLinkedList(o, linkedList, isSort, number);
//            } else {
//                while (number != linkedList.size()){
//                    linkedList.addLast(linkedList.removeFirst());
//                    ++ number;
//                }
//                return object;
//            }
            while (number != linkedList.size()) {
                linkedList.addLast(linkedList.removeFirst());
                ++number;
            }
            return object;

        }
        return object;
    }


    /**
     * 对象类型匹配比对
     */
    private static void objectTypeCompare(String number, Object beforeObject, Object afterObject,
                                          RelEntity relEntity, List<String> resultList) {
        if (StringUtils.isBlank(number)) {
            number = relEntity.getPartyBDesc().split("\\.")[0];
        } else {
            number = relEntity.getPartyBDesc().split("\\.")[0] + number;
        }
        //特殊值判断处理
        if ("--".equals(beforeObject)) {
            if (afterObject == null || "".equals(afterObject)) {
                return;
            }
        }
        //去掉空值的判断
        if (beforeObject == null) {
            if (afterObject == null || "".equals(afterObject.toString())) {
                return;
            } else if (afterObject instanceof JSONArray) {
                if (((JSONArray) afterObject).size() == 0) {
                    return;
                }
            }
            resultList.add(number + "\t" + relEntity.getPartyADesc() + "\t" + null + "\t" + relEntity.getPartyBDesc() + "\t" + afterObject);
            return;
        }
        if (afterObject == null) {
            if ("".equals(beforeObject.toString())) {
                return;
            } else if (beforeObject instanceof JSONArray) {
                if (((JSONArray) beforeObject).size() == 0) {
                    return;
                }
            }
            resultList.add(number + "\t" + relEntity.getPartyADesc() + "\t" + beforeObject + "\t" + relEntity.getPartyBDesc() + "\t" + null);
            return;
        }
        //半刻“”==大道null
//        if ("".equals(beforeObject)) {
//            if ("0".equals(afterObject.toString())) {
//                return;
//            }
//        }
        //配置特殊规则
        Map<List<Object>, List<Object>> map = specialConf();
        if (map != null && map.size() > 0){
            Set<List<Object>> keyset = map.keySet();
            for (List<Object> o : keyset){
                List<Object> value = map.get(o);
                if (o.contains(beforeObject) && value.contains(afterObject)){
                    return;
                }
            }
        }
        //todo 括号的特殊处理
//        if (beforeObject.toString().contains("（")){
//            beforeObject = beforeObject.toString().replaceAll("（", "(");
//            beforeObject = beforeObject.toString().replaceAll("）", ")");
//        }
//        if (afterObject.toString().contains("（")){
//            afterObject = afterObject.toString().replaceAll("（", "(");
//            afterObject = afterObject.toString().replaceAll("）", ")");
//        }

//        时间的处理
        String regex_yM = "[0-9]{1}[0-9]{3}([-])\\d{1,2}";
        String regex_yM_ = "[0-9]{1}[0-9]{3}([./])\\d{1,2}";
        String regex_yMd = "[1-9]{1}[0-9]{3}([-])\\d{1,2}\\1\\d{1,2}";
        String regex_yMd_ = "[1-9]{1}[0-9]{3}([./])\\d{1,2}\\1\\d{1,2}";
        String regex_yMdHms = "[1-9]{1}[0-9]{3}([-])\\d{1,2}\\1\\d{1,2}\\s?\\d{1,2}[:]\\d{1,2}[:]\\d{1,2}";
        String regex_yMdHms_ = "[1-9]{1}[0-9]{3}([./])\\d{1,2}\\1\\d{1,2}\\s?\\d{1,2}[:]\\d{1,2}[:]\\d{1,2}";
        if ((beforeObject.toString().matches(regex_yM) && afterObject.toString().matches(regex_yM_) ||
                (beforeObject.toString().matches(regex_yMd) && afterObject.toString().matches(regex_yMd_)) ||
                (beforeObject.toString().matches(regex_yMdHms) && afterObject.toString().matches(regex_yMdHms_)))) {
            afterObject = afterObject.toString().replaceAll("[./]", "-");
        }

        if (!beforeObject.toString().equals(afterObject.toString())) {
            resultList.add(number + "\t" + relEntity.getPartyADesc() + "\t" + beforeObject + "\t" + relEntity.getPartyBDesc() + "\t" + afterObject);
        }
    }

    /**
     * 将字符串转为LinkedList，根据.拆分
     */
    private static LinkedList<String> strTurnLinkedList(String str) {
        LinkedList<String> linkedList = new LinkedList<>();
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return linkedList;
        }
        String[] strArr = str.split("\\.");
        for (String arr : strArr) {
            linkedList.addLast(arr);
        }
        return linkedList;
    }


    /**
     * 特殊规则
     */
    private static Map<List<Object>, List<Object>> specialConf() {
        Map<List<Object>, List<Object>> map = new HashMap<>();
//        map.put("--", Arrays.asList("", null));
//        map.put("", Arrays.asList("结清", "0"));
        map.put(Collections.singletonList(""), Arrays.asList(0, "结清", "销户", "未激活"));
        return map;
    }


    static class RelEntity {
        private String partyA;
        private String partyADesc;
        private String partyB;
        private String partyBDesc;

        RelEntity(String partyA, String partyADesc, String partyB, String partyBDesc) {
            this.partyA = partyA;
            this.partyADesc = partyADesc;
            this.partyB = partyB;
            this.partyBDesc = partyBDesc;
        }

        String getPartyA() {
            return partyA;
        }

        String getPartyADesc() {
            return partyADesc;
        }

        String getPartyB() {
            return partyB;
        }

        String getPartyBDesc() {
            return partyBDesc;
        }
    }


    public static void main(String[] args) throws Exception {
        //映射关系
        RelEntity relEntity_1 = new RelEntity("001001.line1.reportno#orderno", "报告编号", "reportBaseInfo.reportNo#serialNo", "报告的基本信息.报告编号");
        RelEntity relEntity_2 = new RelEntity("001001.line1.querytime#orderno", "查询请求时间", "reportBaseInfo.queryTime#serialNo", "报告的基本信息.查询请求时间");


        //半刻
//        List<String> list1 = ReadFile.readLineData("src/main/resources/ioConf/banke.txt");
        List<String> list1 = ReadFile.readLineData("javaBasics/src/main/resources/ioConf/test1.txt");
        //大道
//        List<String> list2 = ReadFile.readLineData("src/main/resources/ioConf/dadao.txt");
        List<String> list2 = ReadFile.readLineData("javaBasics/src/main/resources/ioConf/test2.txt");
        for (int i = 0; i < list1.size(); i++) {
//            if (i != 9){
//                continue;
//            }
            List<String> list = compareEntitys(list1.get(i), list2.get(i), Arrays.asList(relEntity_1, relEntity_2));
//            List<String> list = compareEntitys(list1.get(i), list2.get(i), Arrays.asList(relEntity_153));
//            System.out.println(i + 1);
            for (String str : list) {
                System.out.println(str);
            }
//            for (int j = 0; j < 5; j++) {
//                System.out.println();
//            }

        }

    }

}
