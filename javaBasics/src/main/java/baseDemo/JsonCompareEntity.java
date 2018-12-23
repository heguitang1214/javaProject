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
        RelEntity relEntity_3 = new RelEntity("001001.line1.reportcreatetime#orderno", "报告时间", "reportBaseInfo.reportCreateTime#serialNo", "报告的基本信息.报告获取时间");
        RelEntity relEntity_4 = new RelEntity("001002.line1.customername#orderno", "被查询者姓名", "reportBaseInfo.name#serialNo", "报告的基本信息.被查询人名称");
        RelEntity relEntity_5 = new RelEntity("001002.line1.certtype#orderno", "被查询者证件类型", "reportBaseInfo.certType#serialNo", "报告的基本信息.被查询人证件类型");
        RelEntity relEntity_6 = new RelEntity("001002.line1.certno#orderno", "被查询者证件号码", "reportBaseInfo.certNo#serialNo", "报告的基本信息.被查询人证件号码");
        RelEntity relEntity_7 = new RelEntity("001003.line1.operateorg#orderno", "查询操作机构", "reportBaseInfo.operateUser#serialNo", "报告的基本信息.查询操作人");
        RelEntity relEntity_8 = new RelEntity("001003.line1.queryreason#orderno", "查询原因", "reportBaseInfo.queryReason#serialNo", "报告的基本信息.查询原因");
        RelEntity relEntity_9 = new RelEntity("002001.line1.gender#orderno", "性别", "icrIdentity.gender#serialNo", "身份信息.性别");
        RelEntity relEntity_10 = new RelEntity("002001.line1.birthday#orderno", "出生日期", "icrIdentity.birthday#serialNo", "身份信息.出生日期");
        RelEntity relEntity_11 = new RelEntity("002001.line1.maritalstate#orderno", "婚姻状况", "icrIdentity.maritalState#serialNo", "身份信息.婚姻状况");
        RelEntity relEntity_12 = new RelEntity("002001.line1.mobile#orderno", "手机号码", "icrIdentity.mobile#serialNo", "身份信息.手机号码");
        RelEntity relEntity_13 = new RelEntity("002001.line3.officetelephoneno#orderno", "单位电话", "icrIdentity.officetelePhoneno#serialNo", "身份信息.单位电话");
        RelEntity relEntity_14 = new RelEntity("002001.line3.hometelephoneno#orderno", "住宅电话", "icrIdentity.hometelePhoneno#serialNo", "身份信息.住宅电话");
        RelEntity relEntity_15 = new RelEntity("002001.line3.edulevel#orderno", "学历", "icrIdentity.edulevel#serialNo", "身份信息.学历");
        RelEntity relEntity_16 = new RelEntity("002001.line3.edudegree#orderno", "学位", "icrIdentity.edudegree#serialNo", "身份信息.学位");
        RelEntity relEntity_17 = new RelEntity("002008.line1.postaddress#orderno", "通讯地址", "icrIdentity.postAddress#serialNo", "身份信息.通讯地址");
        RelEntity relEntity_18 = new RelEntity("002008.line1.registeredaddress#orderno", "户籍地址", "icrIdentity.registeredAddress#serialNo", "身份信息.户籍地址");
        RelEntity relEntity_19 = new RelEntity("002002.line1.name#orderno", "姓名", "icrSpouse.name#serialNo", "配偶信息.姓名");
        RelEntity relEntity_20 = new RelEntity("002002.line1.certtype#orderno", "证件类型", "icrSpouse.certType#serialNo", "配偶信息.证件类型");
        RelEntity relEntity_21 = new RelEntity("002002.line1.certno#orderno", "证件号码", "icrSpouse.certNo#serialNo", "配偶信息.证件号码");
        RelEntity relEntity_22 = new RelEntity("002003.line1.employer#orderno", "工作单位", "icrSpouse.employer#serialNo", "配偶信息.工作单位");
        RelEntity relEntity_23 = new RelEntity("002003.line1.telephoneno#orderno", "联系电话", "icrSpouse.telephoneNo#serialNo", "配偶信息.联系电话");
        RelEntity relEntity_24 = new RelEntity("002006.line1.employer#orderno", "工作单位", "icrProfessional.employer#serialNo", "职业信息.工作单位");
        RelEntity relEntity_25 = new RelEntity("002006.line1.employeraddress#orderno", "单位地址", "icrProfessional.employerAddress#serialNo", "职业信息.单位地址");
        RelEntity relEntity_26 = new RelEntity("002007.line1.occupation#orderno", "职业", "icrProfessional.occupation#serialNo", "职业信息.职业");
        RelEntity relEntity_27 = new RelEntity("002007.line1.industry#orderno", "行业", "icrProfessional.industry#serialNo", "职业信息.行业");
        RelEntity relEntity_28 = new RelEntity("002007.line1.duty#orderno", "职务", "icrProfessional.duty#serialNo", "职业信息.职务");
        RelEntity relEntity_29 = new RelEntity("002007.line1.title#orderno", "职称", "icrProfessional.title#serialNo", "职业信息.职称");
        RelEntity relEntity_30 = new RelEntity("002007.line1.startyear#orderno", "进入本单位年份", "icrProfessional.startYear#serialNo", "职业信息.进入本单位年份");
        RelEntity relEntity_31 = new RelEntity("002007.line1.gettime#orderno", "信息更新日期", "icrProfessional.getTime#serialNo", "职业信息.信息更新日期");
        RelEntity relEntity_32 = new RelEntity("002005.line1.address#orderno", "居住地址", "icrResidence.address#serialNo", "居住信息.居住地址");
        RelEntity relEntity_33 = new RelEntity("002005.line1.residencetype#orderno", "居住状况", "icrResidence.residenceType#serialNo", "居住信息.居住状况");
        RelEntity relEntity_34 = new RelEntity("002005.line1.gettime#orderno", "信息更新日期", "icrResidence.getTime#serialNo", "居住信息.信息更新日期");
        RelEntity relEntity_35 = new RelEntity("003001.line1.houseloancount#orderno", "个人住房贷款笔数", "icrCreditCue.houseLoanCount#serialNo", "信用提示.住房贷款笔数");
        RelEntity relEntity_36 = new RelEntity("003001.line1.houseloan2count#orderno", "个人商用房（包括商住两用）贷款笔数", "icrCreditCue.houseLoan2Count#serialNo", "信用提示.个人商用房（包括商住两用）贷款笔数");
        RelEntity relEntity_37 = new RelEntity("003001.line1.otherloancount#orderno", "其他贷款笔数", "icrCreditCue.otherLoanCount#serialNo", "信用提示.其他贷款笔数");
        RelEntity relEntity_38 = new RelEntity("003001.line1.firstloanopenmonth#orderno", "首笔贷款月份", "icrCreditCue.firstLoanOpenMonth#serialNo", "信用提示.首笔贷款发放月份");
        RelEntity relEntity_39 = new RelEntity("003001.line1.loancardcount#orderno", "贷记卡账户数", "icrCreditCue.loanCardCount#serialNo", "信用提示.贷记卡账户数");
        RelEntity relEntity_40 = new RelEntity("003001.line1.firstloancardopenmonth#orderno", "首张贷记卡发卡月份", "icrCreditCue.firstLoanCardOpenMonth#serialNo", "信用提示.首张贷记卡发卡月份");
        RelEntity relEntity_41 = new RelEntity("003001.line1.standardloancardcount#orderno", "准贷卡账户数", "icrCreditCue.standardLoanCardCount#serialNo", "信用提示.准贷记卡账户数");
        RelEntity relEntity_42 = new RelEntity("003001.line1.firststandardloancardopenmonth#orderno", "首张准贷记卡发卡月份", "icrCreditCue.firstStandardLoanCardOpenMonth#serialNo", "信用提示.首张准贷记卡发卡月份");
        RelEntity relEntity_43 = new RelEntity("003001.line1.announcecount#orderno", "本人声明数目", "icrCreditCue.announceCount#serialNo", "信用提示.本人声明数目");
        RelEntity relEntity_44 = new RelEntity("003001.line1.dissentcount#orderno", "异议标注数目", "icrCreditCue.dissentCount#serialNo", "信用提示.异议标注数目");
        RelEntity relEntity_45 = new RelEntity("003003.line1.financecorpcount#orderno", "发卡法人机构数", "icrUnpaidLoan.financeCorpCount#serialNo", "未结清贷款信息汇总.贷款法人机构数");
        RelEntity relEntity_46 = new RelEntity("003003.line1.financeorgcount#orderno", "发卡机构数", "icrUnpaidLoan.financeOrgCount#serialNo", "未结清贷款信息汇总.贷款机构数");
        RelEntity relEntity_47 = new RelEntity("003003.line1.accountcount#orderno", "账户数", "icrUnpaidLoan.accountCount#serialNo", "未结清贷款信息汇总.笔数");
        RelEntity relEntity_48 = new RelEntity("003003.line1.creditlimit#orderno", "授信总额", "icrUnpaidLoan.creditLimit#serialNo", "未结清贷款信息汇总.合同总额");
        RelEntity relEntity_49 = new RelEntity("003003.line1.balance#orderno", "担保本金余额", "icrUnpaidLoan.balance#serialNo", "未结清贷款信息汇总.余额");
        RelEntity relEntity_50 = new RelEntity("003003.line1.latest6monthusedavgamount#orderno", "最近6个月平均透支额度", "icrUnpaidLoan.latest6MonthUseDavgAmount#serialNo", "未结清贷款信息汇总.最近6个月平均应还款");
        RelEntity relEntity_51 = new RelEntity("003005.line1.financecorpcount#orderno", "发卡法人机构数", "icrUndestoryLoanCard.financeCorpCount#serialNo", "未销户贷记卡信息汇总.发卡法人机构数");
        RelEntity relEntity_52 = new RelEntity("003005.line1.financeorgcount#orderno", "发卡机构数", "icrUndestoryLoanCard.financeOrgCount#serialNo", "未销户贷记卡信息汇总.发卡机构数");
        RelEntity relEntity_53 = new RelEntity("003005.line1.accountcount#orderno", "账户数", "icrUndestoryLoanCard.accountCount#serialNo", "未销户贷记卡信息汇总.账户数");
        RelEntity relEntity_54 = new RelEntity("003005.line1.creditlimit#orderno", "授信总额", "icrUndestoryLoanCard.creditLimit#serialNo", "未销户贷记卡信息汇总.授信总额");
        RelEntity relEntity_55 = new RelEntity("003005.line1.maxcreditlimitperorg#orderno", "单家行最高授信额", "icrUndestoryLoanCard.maxCreditLimitPerOrg#serialNo", "未销户贷记卡信息汇总.单家行最高授信额");
        RelEntity relEntity_56 = new RelEntity("003005.line1.mincreditlimitperorg#orderno", "单家行最低授信额", "icrUndestoryLoanCard.minCreditLimitPerOrg#serialNo", "未销户贷记卡信息汇总.单家行最低授信额");
        RelEntity relEntity_57 = new RelEntity("003005.line1.usedcreditlimit#orderno", "已用额度", "icrUndestoryLoanCard.usedCreditLimit#serialNo", "未销户贷记卡信息汇总.已用额度");
        RelEntity relEntity_58 = new RelEntity("003005.line1.latest6monthusedavgamount#orderno", "最近6个月平均使用额度", "icrUndestoryLoanCard.latest6MonthUseDavgAmount#serialNo", "未销户贷记卡信息汇总.最近6个月平均使用额度");
        RelEntity relEntity_59 = new RelEntity("003006.line1.financecorpcount#orderno", "发卡法人机构数", "icrUndestoryStandardLoanCard.financeCorpCount#serialNo", "未销户准贷记卡信息汇总.发卡法人机构数");
        RelEntity relEntity_60 = new RelEntity("003006.line1.financeorgcount#orderno", "发卡机构数", "icrUndestoryStandardLoanCard.financeOrgCount#serialNo", "未销户准贷记卡信息汇总.发卡机构数");
        RelEntity relEntity_61 = new RelEntity("003006.line1.accountcount#orderno", "账户数", "icrUndestoryStandardLoanCard.accountCount#serialNo", "未销户准贷记卡信息汇总.账户数");
        RelEntity relEntity_62 = new RelEntity("003006.line1.creditlimit#orderno", "授信总额", "icrUndestoryStandardLoanCard.creditLimit#serialNo", "未销户准贷记卡信息汇总.授信总额");
        RelEntity relEntity_63 = new RelEntity("003006.line1.maxcreditlimitperorg#orderno", "单家行最高授信额", "icrUndestoryStandardLoanCard.maxCreditLimitPerOrg#serialNo", "未销户准贷记卡信息汇总.单家行最高授信额");
        RelEntity relEntity_64 = new RelEntity("003006.line1.mincreditlimitperorg#orderno", "单家行最低授信额", "icrUndestoryStandardLoanCard.minCreditLimitPerOrg#serialNo", "未销户准贷记卡信息汇总.单家行最低授信额");
        RelEntity relEntity_65 = new RelEntity("003006.line1.usedcreditlimit#orderno", "透支额度", "icrUndestoryStandardLoanCard.usedCreditLimit#serialNo", "未销户准贷记卡信息汇总.透支余额");
        RelEntity relEntity_66 = new RelEntity("003006.line1.latest6monthusedavgamount#orderno", "最近6个月平均透支额度", "icrUndestoryStandardLoanCard.latest6MonthUseDavgAmount#serialNo", "未销户准贷记卡信息汇总.最近6个月平均使用额度");
        RelEntity relEntity_67 = new RelEntity("003004.line1.count#orderno", "贷款逾期-笔数", "icrOverdueSummary.count#serialNo", "逾期(透支)信息汇总.贷款逾期笔数");
        RelEntity relEntity_68 = new RelEntity("003004.line1.months#orderno", "贷款逾期-月份数", "icrOverdueSummary.months#serialNo", "逾期(透支)信息汇总.贷款逾期月份数");
        RelEntity relEntity_69 = new RelEntity("003004.line1.highestoverdueamountpermon#orderno", "贷款逾期-单月最高预期总额", "icrOverdueSummary.highestOverdueAmountPermon#serialNo", "逾期(透支)信息汇总.贷款逾期单月最高逾期总额");
        RelEntity relEntity_70 = new RelEntity("003004.line1.maxduration#orderno", "贷款逾期-最长逾期月数", "icrOverdueSummary.maxDuration#serialNo", "逾期(透支)信息汇总.贷款逾期最长逾期月数");
        RelEntity relEntity_71 = new RelEntity("003004.line1.count2#orderno", "贷记卡逾期-账户数", "icrOverdueSummary.count2#serialNo", "逾期(透支)信息汇总.贷记卡逾期笔数");
        RelEntity relEntity_72 = new RelEntity("003004.line1.months2#orderno", "贷记卡逾期-月份数", "icrOverdueSummary.months2#serialNo", "逾期(透支)信息汇总.贷记卡逾期月份数");
        RelEntity relEntity_73 = new RelEntity("003004.line1.highestoverdueamountpermon2#orderno", "贷记卡逾期-单月最高逾期总额", "icrOverdueSummary.highestOverdueAmountPermon2#serialNo", "逾期(透支)信息汇总.贷记卡逾期单月最高逾期总额");
        RelEntity relEntity_74 = new RelEntity("003004.line1.maxduration2#orderno", "贷记卡逾期-最长逾期月数", "icrOverdueSummary.maxDuration2#serialNo", "逾期(透支)信息汇总.贷记卡逾期最长逾期月数");
        RelEntity relEntity_75 = new RelEntity("003004.line1.count3#orderno", "准贷记卡60天以上透支-账户数", "icrOverdueSummary.count3#serialNo", "逾期(透支)信息汇总.准贷记卡60天以上透支笔数");
        RelEntity relEntity_76 = new RelEntity("003004.line1.months3#orderno", "准贷记卡60天以上透支-月分数", "icrOverdueSummary.months3#serialNo", "逾期(透支)信息汇总.准贷记卡60天以上透支月份数");
        RelEntity relEntity_77 = new RelEntity("003004.line1.highestoverdueamountpermon3#orderno", "准贷记卡60天以上透支-单月最高透支余额", "icrOverdueSummary.highestOverdueAmountPermon3#serialNo", "逾期(透支)信息汇总.准贷记卡60天以上透支单月最高逾期总额");
        RelEntity relEntity_78 = new RelEntity("003004.line1.maxduration3#orderno", "准贷记卡60天以上透支-最长透支月数", "icrOverdueSummary.maxDuration3#serialNo", "逾期(透支)信息汇总.准贷记卡60天以上透支最长逾期月数");
        RelEntity relEntity_79 = new RelEntity("004003.line1.opendate#line1.orderno", "发放日期", "icrLoanInfo.openDate#serialNo", "贷款信息.发放日期");
        RelEntity relEntity_80 = new RelEntity("004003.line1.financeorg#line1.orderno", "发放机构", "icrLoanInfo.financeorg#serialNo", "贷款信息.贷款机构");
        RelEntity relEntity_81 = new RelEntity("004003.line1.creditlimitamount#line1.orderno", "发放金额", "icrLoanInfo.creditLimitAmount#serialNo", "贷款信息.合同金额");
        RelEntity relEntity_82 = new RelEntity("004003.line1.currency#line1.orderno", "币种", "icrLoanInfo.currency#serialNo", "贷款信息.币种");
        RelEntity relEntity_83 = new RelEntity("004003.line1.type#line1.orderno", "特殊交易类型", "icrLoanInfo.type#serialNo", "贷款信息.贷款种类细分");
        RelEntity relEntity_84 = new RelEntity("004003.line1.guaranteetype#line1.orderno", "担保方式", "icrLoanInfo.guaranteeType#serialNo", "贷款信息.担保方式");
        RelEntity relEntity_85 = new RelEntity("004003.line1.paymentcyc#line1.orderno", "期数<或有>", "icrLoanInfo.payMentCyc#serialNo", "贷款信息.还款期数");
        RelEntity relEntity_86 = new RelEntity("004003.line1.paymentrating#line1.orderno", "还款方式", "icrLoanInfo.payMentRating#serialNo", "贷款信息.还款频率");
        RelEntity relEntity_87 = new RelEntity("004003.line1.enddate#line1.orderno", "到期时间", "icrLoanInfo.endDate#serialNo", "贷款信息.到期日期");
        RelEntity relEntity_88 = new RelEntity("004003.line1.stateenddate#line1.orderno", "报告截止日期", "icrLoanInfo.stateendDate#serialNo", "贷款信息.状态截止日");
        RelEntity relEntity_89 = new RelEntity("004003.line1.badbalance#line1.orderno", "余额<或有>", "icrLoanInfo.badBalance#serialNo", "贷款信息.坏账");
        RelEntity relEntity_90 = new RelEntity("004003.line2.loanacctstate#line1.orderno", "账户状态", "icrLoanInfo.state#serialNo", "贷款信息.帐户状态");
        RelEntity relEntity_91 = new RelEntity("004003.line2.class5state#line1.orderno", "五级分类", "icrLoanInfo.class5State#serialNo", "贷款信息.五级分类");
        RelEntity relEntity_92 = new RelEntity("004003.line2.balance#line1.orderno", "本金余额", "icrLoanInfo.balance#serialNo", "贷款信息.本金余额");
        RelEntity relEntity_93 = new RelEntity("004003.line2.remainpaymentcyc#line1.orderno", "剩余还款期数", "icrLoanInfo.remainPayMentcyc#serialNo", "贷款信息.剩余还款期数");
        RelEntity relEntity_94 = new RelEntity("004003.line2.scheduledpaymentamount#line1.orderno", "本月应还款", "icrLoanInfo.scheduledPayMentAmount#serialNo", "贷款信息.本月应还款");
        RelEntity relEntity_95 = new RelEntity("004003.line2.scheduledpaymentdate#line1.orderno", "应还款日", "icrLoanInfo.scheduledPayMentDate#serialNo", "贷款信息.应还款日");
        RelEntity relEntity_96 = new RelEntity("004003.line2.actualpaymentamount#line1.orderno", "本月实还款", "icrLoanInfo.actualPayMentAmount#serialNo", "贷款信息.本月实还款");
        RelEntity relEntity_97 = new RelEntity("004003.line2.recentpaydate#line1.orderno", "最近一次还款日", "icrLoanInfo.recentPayDate#serialNo", "贷款信息.最近一次还款日期");
        RelEntity relEntity_98 = new RelEntity("004003.line3.curroverduecyc#line1.orderno", "当前逾期期数", "icrLoanInfo.currOverdueCyc#serialNo", "贷款信息.当前逾期期数");
        RelEntity relEntity_99 = new RelEntity("004003.line3.curroverdueamount#line1.orderno", "当前逾期金额", "icrLoanInfo.currOverdueAmount#serialNo", "贷款信息.当前逾期金额");
        RelEntity relEntity_100 = new RelEntity("004003.line3.overdue31to60amount#line1.orderno", "逾期31-60天未还本金", "icrLoanInfo.overdue31To60Amount#serialNo", "贷款信息.逾期31—60天未还本金");
        RelEntity relEntity_101 = new RelEntity("004003.line3.overdue61to90amount#line1.orderno", "逾期61-90天未还本金", "icrLoanInfo.overdue61To90Amount#serialNo", "贷款信息.逾期61－90天未还本金");
        RelEntity relEntity_102 = new RelEntity("004003.line3.overdue91to180amount#line1.orderno", "逾期91-180天未还本金", "icrLoanInfo.overdue91To180Amount#serialNo", "贷款信息.逾期91－180天未还本金");
        RelEntity relEntity_103 = new RelEntity("004003.line3.overdueover180amount#line1.orderno", "逾期180天以上未还本金", "icrLoanInfo.overdueOver180Amount#serialNo", "贷款信息.逾期180天以上未还本金");
        RelEntity relEntity_104 = new RelEntity("004003.line4.beginmonth#line1.orderno", "还款记录起始日期", "icrLoanInfo.beginMonth#serialNo", "贷款信息.还款起始月");
        RelEntity relEntity_105 = new RelEntity("004003.line4.endmonth#line1.orderno", "还款记录截止日期", "icrLoanInfo.endMonth#serialNo", "贷款信息.还款截止月");
        RelEntity relEntity_106 = new RelEntity("004003.line5.latest24state#line1.orderno", "还款记录（24格）", "icrLoanInfo.latest24State#serialNo", "贷款信息.24个月还款状态");
        RelEntity relEntity_107 = new RelEntity("004003.line8.type#line1.orderno", "特殊交易类型", "icrLoanInfo.icrSpecialTrade.type#serialNo", "贷款特殊信息.特殊交易类型");
        RelEntity relEntity_108 = new RelEntity("004003.line8.gettime#line1.orderno", "特殊交易-发生日期", "icrLoanInfo.icrSpecialTrade.getTime#serialNo", "贷款特殊信息.发生日期");
        RelEntity relEntity_109 = new RelEntity("004003.line8.changingmonths#line1.orderno", "特殊交易-变更月数", "icrLoanInfo.icrSpecialTrade.changingMonths#serialNo", "贷款特殊信息.变更月数");
        RelEntity relEntity_110 = new RelEntity("004003.line8.changingamount#line1.orderno", "特殊交易-发生金额", "icrLoanInfo.icrSpecialTrade.changingAmount#serialNo", "贷款特殊信息.发生金额");
        RelEntity relEntity_111 = new RelEntity("004003.line8.content#line1.orderno", "特殊交易-明细记录", "icrLoanInfo.icrSpecialTrade.content#serialNo", "贷款特殊信息.明细记录");
        RelEntity relEntity_112 = new RelEntity("004004.line1.sharecreditlimitamount#line1.orderno", "共享授信额度", "icrLoanCardInfo.shareCreditLimitAmount#serialNo", "信用卡信息.共享额度");
        RelEntity relEntity_113 = new RelEntity("004004.line2.usedcreditlimitamount#line1.orderno", "已用额度", "icrLoanCardInfo.usedCreditLimitAmount#serialNo", "信用卡信息.已用额度");
        RelEntity relEntity_114 = new RelEntity("004004.line2.latest6monthusedavgamount#line1.orderno", "最近6个月平均使用额度", "icrLoanCardInfo.latest6MonthUsedAvgAmount#serialNo", "信用卡信息.最近6个月平均使用额度");
        RelEntity relEntity_115 = new RelEntity("004004.line2.usedhighestamount#line1.orderno", "最大使用额度", "icrLoanCardInfo.usedHighestAmount#serialNo", "信用卡信息.最大使用额度");
        RelEntity relEntity_116 = new RelEntity("004004.line2.scheduledpaymentamount#line1.orderno", "本月应还款", "icrLoanCardInfo.scheduledPaymentAmount#serialNo", "信用卡信息.本月应还款");
        RelEntity relEntity_117 = new RelEntity("004004.line3.scheduledpaymentdate#line1.orderno", "账单日", "icrLoanCardInfo.scheduledPaymentDate#serialNo", "信用卡信息.账单日");
        RelEntity relEntity_118 = new RelEntity("004004.line3.actualpaymentamount#line1.orderno", "本月实还款", "icrLoanCardInfo.actualPaymentAmount#serialNo", "信用卡信息.本月实还款");
        RelEntity relEntity_119 = new RelEntity("004004.line3.recentpaydate#line1.orderno", "最近一次还款日期", "icrLoanCardInfo.recentPayDate#serialNo", "信用卡信息.最近一次还款日期");
        RelEntity relEntity_120 = new RelEntity("004004.line3.curroverduecyc#line1.orderno", "当前逾期期数", "icrLoanCardInfo.currOverdueCyc#serialNo", "信用卡信息.当前逾期期数");
        RelEntity relEntity_121 = new RelEntity("004004.line3.curroverdueamount#line1.orderno", "当前逾期金额", "icrLoanCardInfo.currOverdueAmount#serialNo", "信用卡信息.当前逾期金额");
        RelEntity relEntity_122 = new RelEntity("004004.line1.financeorg#line1.orderno", "发放机构", "icrLoanCardInfo.financeOrg#serialNo", "信用卡信息.发卡机构");
        RelEntity relEntity_123 = new RelEntity("004004.line1.currency#line1.orderno", "币种", "icrLoanCardInfo.currency#serialNo", "信用卡信息.币种");
        RelEntity relEntity_124 = new RelEntity("004004.line1.opendate#line1.orderno", "发放日期", "icrLoanCardInfo.openDate#serialNo", "信用卡信息.发卡日期");
        RelEntity relEntity_125 = new RelEntity("004004.line1.creditlimitamount#line1.orderno", "授信额度", "icrLoanCardInfo.creditLimitAmount#serialNo", "信用卡信息.授信额度");
        RelEntity relEntity_126 = new RelEntity("004004.line1.guaranteetype#line1.orderno", "担保方式", "icrLoanCardInfo.guaranteeType#serialNo", "信用卡信息.担保方式");
        RelEntity relEntity_127 = new RelEntity("004004.line1.stateenddate#line1.orderno", "报告截止日期", "icrLoanCardInfo.stateEndDate#serialNo", "信用卡信息.状态截止日");
        RelEntity relEntity_128 = new RelEntity("004004.line4.beginmonth#line1.orderno", "还款记录起始日期", "icrLoanCardInfo.beginMonth#serialNo", "信用卡信息.还款起始月");
        RelEntity relEntity_129 = new RelEntity("004004.line4.endmonth#line1.orderno", "还款记录截止日期", "icrLoanCardInfo.endMonth#serialNo", "信用卡信息.还款截止月");
        RelEntity relEntity_130 = new RelEntity("004004.line5.latest24state#line1.orderno", "还款记录（24格）", "icrLoanCardInfo.latest24State#serialNo", "信用卡信息.24个月还款状态");
        RelEntity relEntity_131 = new RelEntity("004004.line1.badbalance#line1.orderno", "余额<或有>", "icrLoanCardInfo.badBalance#serialNo", "信用卡信息.余额");
        RelEntity relEntity_132 = new RelEntity("004004.line2.loanacctstate#line1.orderno", "账户状态", "icrLoanCardInfo.state#serialNo", "信用卡信息.帐户状态");
        RelEntity relEntity_133 = new RelEntity("004005.line1.sharecreditlimitamount#line1.orderno", "共享授信额度", "icrStdLoanCardInfo.shareCreditLimitAmount#serialNo", "准贷记卡.共享额度");
        RelEntity relEntity_134 = new RelEntity("004005.line2.usedcreditlimitamount#line1.orderno", "透支余额", "icrStdLoanCardInfo.usedCreditLimitAmount#serialNo", "准贷记卡.已用额度");
        RelEntity relEntity_135 = new RelEntity("004005.line2.latest6monthusedavgamount#line1.orderno", "最近6个月平均透支余额", "icrStdLoanCardInfo.latest6MonthUsedAvgAmount#serialNo", "准贷记卡.最近6个月平均使用额度");
        RelEntity relEntity_136 = new RelEntity("004005.line2.usedhighestamount#line1.orderno", "最大透支余额", "icrStdLoanCardInfo.usedHighestAmount#serialNo", "准贷记卡.最大使用额度");
        RelEntity relEntity_137 = new RelEntity("004005.line2.scheduledpaymentamount#line1.orderno", "本月应还款", "icrStdLoanCardInfo.scheduledPaymentAmount#serialNo", "准贷记卡.本月应还款");
        RelEntity relEntity_138 = new RelEntity("004005.line2.scheduledpaymentdate#line1.orderno", "账单日", "icrStdLoanCardInfo.scheduledPaymentDate#serialNo", "准贷记卡.账单日");
        RelEntity relEntity_139 = new RelEntity("004005.line2.actualpaymentamount#line1.orderno", "本月实还款", "icrStdLoanCardInfo.actualPaymentAmount#serialNo", "准贷记卡.本月实还款");
        RelEntity relEntity_140 = new RelEntity("004005.line2.recentpaydate#line1.orderno", "最近一次还款日期", "icrStdLoanCardInfo.recentPayDate#serialNo", "准贷记卡.最近一次还款日期");
        RelEntity relEntity_141 = new RelEntity("004005.line1.financeorg#line1.orderno", "发放机构", "icrStdLoanCardInfo.financeOrg#serialNo", "准贷记卡.发卡机构");
        RelEntity relEntity_142 = new RelEntity("004005.line1.currency#line1.orderno", "币种", "icrStdLoanCardInfo.currency#serialNo", "准贷记卡.币种");
        RelEntity relEntity_143 = new RelEntity("004005.line1.opendate#line1.orderno", "发放日期", "icrStdLoanCardInfo.openDate#serialNo", "准贷记卡.发卡日期");
        RelEntity relEntity_144 = new RelEntity("004005.line1.creditlimitamount#line1.orderno", "授信额度", "icrStdLoanCardInfo.creditLimitAmount#serialNo", "准贷记卡.授信额度");
        RelEntity relEntity_145 = new RelEntity("004005.line1.guaranteetype#line1.orderno", "担保方式", "icrStdLoanCardInfo.guaranteeType#serialNo", "准贷记卡.担保方式");
        RelEntity relEntity_146 = new RelEntity("004005.line1.stateenddate#line1.orderno", "报告截止日期", "icrStdLoanCardInfo.stateEndDate#serialNo", "准贷记卡.状态截止日");
        RelEntity relEntity_147 = new RelEntity("004005.line3.beginmonth#line1.orderno", "还款记录起始日期", "icrStdLoanCardInfo.beginMonth#serialNo", "准贷记卡.还款起始月");
        RelEntity relEntity_148 = new RelEntity("004005.line3.endmonth#line1.orderno", "还款记录截止日期", "icrStdLoanCardInfo.endMonth#serialNo", "准贷记卡.还款截止月");
        RelEntity relEntity_149 = new RelEntity("004005.line4.latest24state#line1.orderno", "还款记录（24格）", "icrStdLoanCardInfo.latest24State#serialNo", "准贷记卡.24个月还款状态");
        RelEntity relEntity_150 = new RelEntity("004005.line1.badbalance#line1.orderno", "余额<或有>", "icrStdLoanCardInfo.badBalance#serialNo", "准贷记卡.余额");
        RelEntity relEntity_151 = new RelEntity("004005.line2.loanacctstate#line1.orderno", "账户状态", "icrStdLoanCardInfo.state#serialNo", "准贷记卡.帐户状态");
        RelEntity relEntity_152 = new RelEntity("004003.line7.month#line1.orderno", "逾期月份", "icrLoanInfo.icrLatest5yearOverdueDetail.month#serialNo", "逾期记录明细.逾期月份");
        RelEntity relEntity_153 = new RelEntity("004003.line7.lastmonths#line1.orderno", "逾期持续月份", "icrLoanInfo.icrLatest5yearOverdueDetail.lastMonths#serialNo", "逾期记录明细.逾期持续月数");
        RelEntity relEntity_154 = new RelEntity("004003.line7.amount#line1.orderno", "逾期金额", "icrLoanInfo.icrLatest5yearOverdueDetail.amount#serialNo", "逾期记录明细.逾期金额");
        RelEntity relEntity_155 = new RelEntity("003007.line1.count#orderno", "担保笔数", "icrGuaranteeSummary.count#serialNo", "对外担保信息汇总.担保笔数");
        RelEntity relEntity_156 = new RelEntity("003007.line1.amount#orderno", "担保金额", "icrGuaranteeSummary.amount#serialNo", "对外担保信息汇总.担保金额");
        RelEntity relEntity_157 = new RelEntity("003007.line1.balance#orderno", "担保本金余额", "icrGuaranteeSummary.balance#serialNo", "对外担保信息汇总.担保本金余额");
        RelEntity relEntity_158 = new RelEntity("004006.line1.contractmoney#line1.orderno", "担保信用卡授信额度", "icrGuarantee.contractMoney#serialNo", "对外贷款担保信息.担保贷款合同金额");
        RelEntity relEntity_159 = new RelEntity("004006.line1.guaranteebalance#line1.orderno", "担保信用卡已用额度", "icrGuarantee.guaranteeBalance#serialNo", "对外贷款担保信息.担保贷款本金余额");
        RelEntity relEntity_160 = new RelEntity("004006.line1.class5state#line1.orderno", "担保贷款五级分类", "icrGuarantee.class5State#serialNo", "对外贷款担保信息.担保贷款五级分类");
        RelEntity relEntity_161 = new RelEntity("004006.line1.organname#orderno", "担保信用卡发放机构", "icrCardGuarantee.organName#serialNo", "对外信用卡担保信息.担保信用卡发放机构");
        RelEntity relEntity_162 = new RelEntity("004006.line1.begindate#orderno", "担保信用卡发卡日期", "icrCardGuarantee.beginDate#serialNo", "对外信用卡担保信息.担保信用卡发卡日期");
        RelEntity relEntity_163 = new RelEntity("004006.line1.guananteemoney#orderno", "担保金额", "icrCardGuarantee.guananteeMoney#serialNo", "对外信用卡担保信息.担保金额");
        RelEntity relEntity_164 = new RelEntity("004006.line1.billingdate#orderno", "账单日", "icrCardGuarantee.billingDate#serialNo", "对外信用卡担保信息.账单日");
        RelEntity relEntity_165 = new RelEntity("008001.line1.orgsum1#orderno", "最近1个月内的查询机构数-贷款审批", "icrRecordSummary.orgSum1#serialNo", "查询记录汇总.最近1个月内的查询机构数(贷款审批)");
        RelEntity relEntity_166 = new RelEntity("008001.line1.orgsum2#orderno", "最近1个月内的查询机构数-信用卡审批", "icrRecordSummary.orgSum2#serialNo", "查询记录汇总.最近1个月内的查询机构数(信用卡审批)");
        RelEntity relEntity_167 = new RelEntity("008001.line1.recordsum1#orderno", "最近1个月内的查询次数-贷款审批", "icrRecordSummary.recordSum1#serialNo", "查询记录汇总.最近1个月内的查询次数(贷款审批)");
        RelEntity relEntity_168 = new RelEntity("008001.line1.recordsum2#orderno", "最近1个月内的查询次数-信用卡审批", "icrRecordSummary.recordSum2#serialNo", "查询记录汇总.最近1个月内的查询次数(信用卡审批)");
        RelEntity relEntity_169 = new RelEntity("008001.line1.recordsumself#orderno", "最近1个月内的查询次数-本人查询", "icrRecordSummary.recordSumSelf#serialNo", "查询记录汇总.最近1个月内的查询次数(本人查询次数)");
        RelEntity relEntity_170 = new RelEntity("008001.line1.towyearrecordsum1#orderno", "最近2年内的查询次数-贷后管理", "icrRecordSummary.towYearRecordSum1#serialNo", "查询记录汇总.最近2年内的查询次数(贷后管理)");
        RelEntity relEntity_171 = new RelEntity("008001.line1.towyearrecordsum2#orderno", "最近2年内的查询次数-担保资格审查", "icrRecordSummary.towYearRecordSum2#serialNo", "查询记录汇总.最近2年内的查询次数(担保资格审查)");
        RelEntity relEntity_172 = new RelEntity("008001.line1.towyearrecordsum3#orderno", "最近2年内的查询次数-特约商户实名审查", "icrRecordSummary.towYearRecordSum3#serialNo", "查询记录汇总.最近2年内的查询次数(特约商户实名审查)");
        RelEntity relEntity_173 = new RelEntity("004001.line1.latestrepaydate#orderno", "最近一次还款日期", "icrAssurerRepay.latestRepayDate#serialNo", "保证人代偿信息.最近一次还款日期");
        RelEntity relEntity_174 = new RelEntity("004002.line1.organname#orderno", "代偿机构", "icrAssurerRepay.organName#serialNo", "保证人代偿信息.代偿机构");
        RelEntity relEntity_175 = new RelEntity("004002.line1.latestassurerrepaydate#orderno", "最近一次代偿日期", "icrAssurerRepay.latestAssurerRepayDate#serialNo", "保证人代偿信息.最近一次代偿日期");
        RelEntity relEntity_176 = new RelEntity("004002.line1.money#orderno", "累计代偿金额", "icrAssurerRepay.money#serialNo", "保证人代偿信息.累计代偿金额");
        RelEntity relEntity_177 = new RelEntity("004002.line1.latestrepaydate#orderno", "最近一次还款日期", "icrAssurerRepay.latestRepayDate#serialNo", "保证人代偿信息.最近一次还款日期");
        RelEntity relEntity_178 = new RelEntity("004002.line1.balance#orderno", "余额", "icrAssurerRepay.balance#serialNo", "保证人代偿信息.余额");
        RelEntity relEntity_179 = new RelEntity("005011.line1.licensecode#orderno", "车牌号码", "icrVehicle.licenseCode#serialNo", "车辆交易和抵押记录.车牌号码");
        RelEntity relEntity_180 = new RelEntity("005011.line1.enginecode#orderno", "发动机号", "icrVehicle.engineCode#serialNo", "车辆交易和抵押记录.发动机号");
        RelEntity relEntity_181 = new RelEntity("005011.line1.brand#orderno", "品牌", "icrVehicle.brand#serialNo", "车辆交易和抵押记录.品牌");
        RelEntity relEntity_182 = new RelEntity("005011.line1.cartype#orderno", "车辆类型", "icrVehicle.carType#serialNo", "车辆交易和抵押记录.车辆类型");
        RelEntity relEntity_183 = new RelEntity("005011.line1.usecharacter#orderno", "使用性质", "icrVehicle.useCharacter#serialNo", "车辆交易和抵押记录.使用性质");
        RelEntity relEntity_184 = new RelEntity("005011.line1.state#orderno", "车辆状态", "icrVehicle.state#serialNo", "车辆交易和抵押记录.车辆状态");
        RelEntity relEntity_185 = new RelEntity("005011.line1.pledgeflag#orderno", "抵押标记", "icrVehicle.pledgeFlag#serialNo", "车辆交易和抵押记录.抵押标记");
        RelEntity relEntity_186 = new RelEntity("005011.line1.gettime#orderno", "信息更新日期", "icrVehicle.getTime#serialNo", "车辆交易和抵押记录.信息更新日期");
        RelEntity relEntity_187 = new RelEntity("005009.line1.competencyname#line1.orderno", "职业资格名称", "icrCompetence.competencyName#serialNo", "执业资格记录.执业资格名称");
        RelEntity relEntity_188 = new RelEntity("005009.line1.grade#line1.orderno", "等级", "icrCompetence.grade#serialNo", "执业资格记录.等级");
        RelEntity relEntity_189 = new RelEntity("005009.line1.awarddate#line1.orderno", "获得日期", "icrCompetence.awardDate#serialNo", "执业资格记录.获得日期");
        RelEntity relEntity_190 = new RelEntity("005009.line1.enddate#line1.orderno", "到期日期", "icrCompetence.endDate#serialNo", "执业资格记录.到期日期");
        RelEntity relEntity_191 = new RelEntity("005009.line1.revokedate#line1.orderno", "吊销日期", "icrCompetence.revokeDate#serialNo", "执业资格记录.吊销日期");
        RelEntity relEntity_192 = new RelEntity("005009.line1.organname#line1.orderno", "颁发机构", "icrCompetence.organName#serialNo", "执业资格记录.颁发机构");
        RelEntity relEntity_193 = new RelEntity("005009.line1.area#line1.orderno", "机构所在地", "icrCompetence.area#serialNo", "执业资格记录.机构所在地");
        RelEntity relEntity_194 = new RelEntity("005003.line1.court#line1.orderno", "执行法院", "icrForceExecution.court#serialNo", "强制执行记录.执行法院");
        RelEntity relEntity_195 = new RelEntity("005003.line1.casereason#line1.orderno", "执行案由", "icrForceExecution.caseReason#serialNo", "强制执行记录.执行案由");
        RelEntity relEntity_196 = new RelEntity("005003.line1.registerdate#line1.orderno", "立案日期", "icrForceExecution.registerDate#serialNo", "强制执行记录.立案日期");
        RelEntity relEntity_197 = new RelEntity("005003.line1.closedtype#line1.orderno", "结案方式", "icrForceExecution.closedType#serialNo", "强制执行记录.结案方式");
        RelEntity relEntity_198 = new RelEntity("005003.line2.casestate#line1.orderno", "案件状态", "icrForceExecution.casesTate#serialNo", "强制执行记录.案件状态");
        RelEntity relEntity_199 = new RelEntity("005003.line2.closeddate#line1.orderno", "结案日期", "icrForceExecution.closedDate#serialNo", "强制执行记录.结案日期");
        RelEntity relEntity_200 = new RelEntity("005003.line2.enforceobject#line1.orderno", "申请执行标的", "icrForceExecution.enforceBigDecimal#serialNo", "强制执行记录.申请执行标的");
        RelEntity relEntity_201 = new RelEntity("005003.line2.enforceobjectmoney#line1.orderno", "申请执行标的价值", "icrForceExecution.enforceBigDecimalMoney#serialNo", "强制执行记录.申请执行标的价值");
        RelEntity relEntity_202 = new RelEntity("005003.line2.alreadyenforceobject#line1.orderno", "已执行标的", "icrForceExecution.alreadyEnforceBigDecimal#serialNo", "强制执行记录.已执行标的");
        RelEntity relEntity_203 = new RelEntity("005003.line2.alreadyenforceobjectmoney#line1.orderno", "已执行标的金额", "icrForceExecution.alreadyEnforceBigDecimalMoney#serialNo", "强制执行记录.已执行标的金额");
        RelEntity relEntity_204 = new RelEntity("005005.line1.area#orderno", "参缴地", "icrAccFund.area#serialNo", "住房公积金参缴记录.参缴地");
        RelEntity relEntity_205 = new RelEntity("005005.line1.registerdate#orderno", "参缴日期", "icrAccFund.registerDate#serialNo", "住房公积金参缴记录.参缴日期");
        RelEntity relEntity_206 = new RelEntity("005005.line1.firstmonth#orderno", "初缴月份", "icrAccFund.firstMonth#serialNo", "住房公积金参缴记录.初缴月份");
        RelEntity relEntity_207 = new RelEntity("005005.line1.tomonth#orderno", "缴至月份", "icrAccFund.toMonth#serialNo", "住房公积金参缴记录.缴至月份");
        RelEntity relEntity_208 = new RelEntity("005005.line1.state#orderno", "缴费状态", "icrAccFund.state#serialNo", "住房公积金参缴记录.缴费状态");
        RelEntity relEntity_209 = new RelEntity("005005.line1.pay#orderno", "月缴存额", "icrAccFund.pay#serialNo", "住房公积金参缴记录.月缴存额");
        RelEntity relEntity_210 = new RelEntity("005005.line1.ownpercent#orderno", "个人缴存比例", "icrAccFund.ownPercent#serialNo", "住房公积金参缴记录.个人缴存比例");
        RelEntity relEntity_211 = new RelEntity("005005.line1.compercent#orderno", "单位缴存比例", "icrAccFund.comPercent#serialNo", "住房公积金参缴记录.单位缴存比例");
        RelEntity relEntity_212 = new RelEntity("005005.line2.organname#orderno", "缴费单位", "icrAccFund.organName#serialNo", "住房公积金参缴记录.缴费单位");
        RelEntity relEntity_213 = new RelEntity("005005.line2.gettime#orderno", "信息更新日期", "icrAccFund.getTime#serialNo", "住房公积金参缴记录.信息更新日期");
        RelEntity relEntity_214 = new RelEntity("005006.line1.area#line1.orderno", "参保地", "icrEndowmentInsuranceDeposit.area#serialNo", "养老保险金缴存记录.参保地");
        RelEntity relEntity_215 = new RelEntity("005006.line1.registerdate#line1.orderno", "参保日期", "icrEndowmentInsuranceDeposit.registerDate#serialNo", "养老保险金缴存记录.参保日期");
        RelEntity relEntity_216 = new RelEntity("005006.line1.monthduration#line1.orderno", "累计缴费月数", "icrEndowmentInsuranceDeposit.BigDecimalmonthDuration#serialNo", "养老保险金缴存记录.累计缴费月数");
        RelEntity relEntity_217 = new RelEntity("005006.line1.workdate#line1.orderno", "参加工作年份", "icrEndowmentInsuranceDeposit.workDate#serialNo", "养老保险金缴存记录.参加工作月份");
        RelEntity relEntity_218 = new RelEntity("005006.line1.state#line1.orderno", "缴费状态", "icrEndowmentInsuranceDeposit.state#serialNo", "养老保险金缴存记录.缴费状态");
        RelEntity relEntity_219 = new RelEntity("005006.line1.ownbasicmoney#line1.orderno", "个人缴费基数", "icrEndowmentInsuranceDeposit.BigDecimalownBasicMoney#serialNo", "养老保险金缴存记录.个人缴费基数");
        RelEntity relEntity_220 = new RelEntity("005006.line1.money#line1.orderno", "本月缴费金额", "icrEndowmentInsuranceDeposit.BigDecimalmoney#serialNo", "养老保险金缴存记录.本月缴费金额");
        RelEntity relEntity_221 = new RelEntity("005006.line1.gettime#line1.orderno", "信息更新日期", "icrEndowmentInsuranceDeposit.getTime#serialNo", "养老保险金缴存记录.信息更新日期");
        RelEntity relEntity_222 = new RelEntity("005006.line2.organname#line1.orderno", "缴费单位", "icrEndowmentInsuranceDeposit.organName#serialNo", "养老保险金缴存记录.缴费单位");
        RelEntity relEntity_223 = new RelEntity("005006.line2.reason#line1.orderno", "中断或终止缴费原因", "icrEndowmentInsuranceDeposit.pauseReason#serialNo", "养老保险金缴存记录.中断或终止缴费原因");
        RelEntity relEntity_224 = new RelEntity("006001.line1.content#line1.orderno", "声明内容", "icrAnnounceInfo.content#serialNo", "贷款本人声明.本人声明");
        RelEntity relEntity_225 = new RelEntity("006001.line1.gettime#line1.orderno", "添加日期", "icrAnnounceInfo.getTime#serialNo", "贷款本人声明.添加日期");
        RelEntity relEntity_226 = new RelEntity("005012.line1.companyno#line1.orderno", "电信运营商", "icrTelPayment.organName#serialNo", "电信缴费记录.电信运营商");
        RelEntity relEntity_227 = new RelEntity("005012.line1.busitype#line1.orderno", "业务类型", "icrTelPayment.type#serialNo", "电信缴费记录.业务类型");
        RelEntity relEntity_228 = new RelEntity("005012.line1.opendate#line1.orderno", "业务开通时间", "icrTelPayment.registerDate#serialNo", "电信缴费记录.业务开通日期");
        RelEntity relEntity_229 = new RelEntity("005012.line1.status#line1.orderno", "当前缴费状态", "icrTelPayment.state#serialNo", "电信缴费记录.当前缴费状态");
        RelEntity relEntity_230 = new RelEntity("005012.line1.owemoney#line1.orderno", "当前欠费金额", "icrTelPayment.arrearMoney#serialNo", "电信缴费记录.当前欠费金额");
        RelEntity relEntity_231 = new RelEntity("005012.line1.owenum#line1.orderno", "当前欠费月数", "icrTelPayment.arrearMonths#serialNo", "电信缴费记录.当前欠费月数");
        RelEntity relEntity_232 = new RelEntity("005012.line1.acctdate#line1.orderno", "记账年月", "icrTelPayment.getTime#serialNo", "电信缴费记录.记账年月");
        RelEntity relEntity_233 = new RelEntity("005012.line2.pay24months#line1.orderno", "最近24个月缴费记录", "icrTelPayment.status24#serialNo", "电信缴费记录.24个月缴费状态");
        RelEntity relEntity_234 = new RelEntity("005008.line1.personneltype#line1.orderno", "人员类别", "icrSalvation.personnelType#serialNo", "低保救助记录.人员类别");
        RelEntity relEntity_235 = new RelEntity("005008.line1.area#line1.orderno", "所在地", "icrSalvation.area#serialNo", "低保救助记录.所在地");
        RelEntity relEntity_236 = new RelEntity("005008.line1.organname#line1.orderno", "工作单位", "icrSalvation.organName#serialNo", "低保救助记录.工作单位");
        RelEntity relEntity_237 = new RelEntity("005008.line1.money#line1.orderno", "家庭月收入", "icrSalvation.money#serialNo", "低保救助记录.家庭月收入");
        RelEntity relEntity_238 = new RelEntity("005008.line1.registerdate#line1.orderno", "申请日期", "icrSalvation.registerDate#serialNo", "低保救助记录.申请日期");
        RelEntity relEntity_239 = new RelEntity("005008.line1.passdate#line1.orderno", "批准日期", "icrSalvation.passDate#serialNo", "低保救助记录.批准日期");
        RelEntity relEntity_240 = new RelEntity("005008.line1.gettime#line1.orderno", "信息更新日期", "icrSalvation.getTime#serialNo", "低保救助记录.信息更新日期");
        RelEntity relEntity_241 = new RelEntity("005010.line1.organname#line1.orderno", "奖励机构", "icrAdminAward.organName#serialNo", "行政奖励记录.奖励机构");
        RelEntity relEntity_242 = new RelEntity("005010.line1.content#line1.orderno", "奖励内容", "icrAdminAward.content#serialNo", "行政奖励记录.奖励内容");
        RelEntity relEntity_243 = new RelEntity("005010.line1.enddate#line1.orderno", "截止日期", "icrAdminAward.endDate#serialNo", "行政奖励记录.截止日期");
        RelEntity relEntity_244 = new RelEntity("005004.line1.begindate#line1.orderno", "生效时间", "icrAdminAward.beginDate#serialNo", "行政奖励记录.生效日期");
        RelEntity relEntity_245 = new RelEntity("005004.line1.organname#line1.orderno", "处罚机构", "icrAdminPunishment.organName#serialNo", "行政处罚记录.处罚机构");
        RelEntity relEntity_246 = new RelEntity("005004.line1.content#line1.orderno", "处罚内容", "icrAdminPunishment.content#serialNo", "行政处罚记录.处罚内容");
        RelEntity relEntity_247 = new RelEntity("005004.line1.money#line1.orderno", "处罚金额", "icrAdminPunishment.money#serialNo", "行政处罚记录.处罚金额");
        RelEntity relEntity_248 = new RelEntity("005004.line1.enddate#line1.orderno", "截止日期", "icrAdminPunishment.endDate#serialNo", "行政处罚记录.截止日期");
        RelEntity relEntity_249 = new RelEntity("005004.line1.result#line1.orderno", "行政复议结果", "icrAdminPunishment.result#serialNo", "行政处罚记录.行政复议结果");
        RelEntity relEntity_250 = new RelEntity("005010.line1.begindate#line1.orderno", "生效日期", "icrAdminPunishment.beginDate#serialNo", "行政处罚记录.生效日期");
        RelEntity relEntity_251 = new RelEntity("005001.line1.organname#line1.orderno", "主管税务机关", "icrTaxArrear.organName#serialNo", "欠税记录.主管税务机关");
        RelEntity relEntity_252 = new RelEntity("005001.line1.revenuedate#line1.orderno", "欠税统计时间", "icrTaxArrear.revenueDate#serialNo", "欠税记录.欠税统计日期");
        RelEntity relEntity_253 = new RelEntity("005001.line1.taxarreaamount#line1.orderno", "欠税总额", "icrTaxArrear.taxArreaAmount#serialNo", "欠税记录.欠税总额");
        RelEntity relEntity_254 = new RelEntity("005002.line1.court#line1.orderno", "立案法院", "icrCivilJudgement.court#serialNo", "民事判决记录.立案法院");
        RelEntity relEntity_255 = new RelEntity("005002.line1.casereason#line1.orderno", "案由", "icrCivilJudgement.caseReason#serialNo", "民事判决记录.案由");
        RelEntity relEntity_256 = new RelEntity("005002.line1.registerdate#line1.orderno", "立案日期", "icrCivilJudgement.registerDate#serialNo", "民事判决记录.立案日期");
        RelEntity relEntity_257 = new RelEntity("005002.line1.closedtype#line1.orderno", "结案方式", "icrCivilJudgement.closedType#serialNo", "民事判决记录.结案方式");
        RelEntity relEntity_258 = new RelEntity("005002.line2.caseresult#line1.orderno", "判决／调解结果", "icrCivilJudgement.caseResult#serialNo", "民事判决记录.判决/调解结果");
        RelEntity relEntity_259 = new RelEntity("005002.line2.casevalidatedate#line1.orderno", "判决／调解生效日期", "icrCivilJudgement.caseValidateDate#serialNo", "民事判决记录.判决/调解生效日期");
        RelEntity relEntity_260 = new RelEntity("005002.line2.suitobject#line1.orderno", "诉讼标的", "icrCivilJudgement.suitObject#serialNo", "民事判决记录.诉讼标的");
        RelEntity relEntity_261 = new RelEntity("005002.line2.suitobjectmoney#line1.orderno", "诉讼标的金额", "icrCivilJudgement.suitObjectMoney#serialNo", "民事判决记录.诉讼标的金额");
        RelEntity relEntity_262 = new RelEntity("005007.line1.area#line1.orderno", "发放地", "icrEndowmentInsuranceDeliver.area#serialNo", "养老保险金发放记录.发放地");
        RelEntity relEntity_263 = new RelEntity("005007.line1.retiretype#line1.orderno", "离退休类别", "icrEndowmentInsuranceDeliver.retireType#serialNo", "养老保险金发放记录.离退休类别");
        RelEntity relEntity_264 = new RelEntity("005007.line1.retireddate#line1.orderno", "离退休月份", "icrEndowmentInsuranceDeliver.retiredDate#serialNo", "养老保险金发放记录.离退休月份");
        RelEntity relEntity_265 = new RelEntity("005007.line1.workdate#line1.orderno", "参加工作月份", "icrEndowmentInsuranceDeliver.workDate#serialNo", "养老保险金发放记录.参加工作月份");
        RelEntity relEntity_266 = new RelEntity("005007.line1.money#line1.orderno", "本月实发养老金", "icrEndowmentInsuranceDeliver.money#serialNo", "养老保险金发放记录.本月实发养老金");
        RelEntity relEntity_267 = new RelEntity("005007.line1.pausereason#line1.orderno", "停发原因", "icrEndowmentInsuranceDeliver.pauseReason#serialNo", "养老保险金发放记录.停发原因");
        RelEntity relEntity_268 = new RelEntity("005007.line1.gettime1#line1.orderno", "信息更新日期", "icrEndowmentInsuranceDeliver.getTime#serialNo", "养老保险金发放记录.信息更新日期");
        RelEntity relEntity_269 = new RelEntity("005007.line2.organname#line1.orderno", "原单位名称", "icrEndowmentInsuranceDeliver.organName#serialNo", "养老保险金发放记录.原单位名称");
        RelEntity relEntity_270 = new RelEntity("007001.line1.content#orderno", "标注内容", "icrDissentInfo.content#serialNo", "贷款异议标注.异议标注");
        RelEntity relEntity_271 = new RelEntity("007001.line1.gettime#orderno", "添加日期", "icrDissentInfo.getTime#serialNo", "贷款异议标注.添加日期");
        RelEntity relEntity_272 = new RelEntity("008003.line1.querydate#orderno", "查询日期", "icrPersonRecordDetail.queryDate#serialNo", "个人查询记录明细.查询日期");
        RelEntity relEntity_273 = new RelEntity("008003.line1.querier#orderno", "查询操作员", "icrPersonRecordDetail.querier#serialNo", "个人查询记录明细.查询操作员");
        RelEntity relEntity_274 = new RelEntity("008003.line1.queryreason#orderno", "查询原因", "icrPersonRecordDetail.queryReason#serialNo", "个人查询记录明细.查询原因");
        RelEntity relEntity_275 = new RelEntity("008002.line1.querydate#orderno", "查询日期", "icrRecordDetail.queryDate#serialNo", "信贷审批查询记录明细.查询日期");
        RelEntity relEntity_276 = new RelEntity("008002.line1.querier#orderno", "查询操作员", "icrRecordDetail.querier#serialNo", "信贷审批查询记录明细.查询操作员");
        RelEntity relEntity_277 = new RelEntity("008002.line1.queryreason#orderno", "查询原因", "icrRecordDetail.queryReason#serialNo", "信贷审批查询记录明细.查询原因");
        RelEntity relEntity_278 = new RelEntity("003002.line1.count", "呆帐信息汇总笔数", "icrFellBackSummary.count", "逾期及违约信息概要.呆账信息笔数");
        RelEntity relEntity_279 = new RelEntity("003002.line1.balance", "呆帐信息汇总余额", "icrFellBackSummary.balance", "逾期及违约信息概要.呆账信息余额");
        RelEntity relEntity_280 = new RelEntity("003002.line1.count2", "资产处置信息汇总笔数", "icrFellBackSummary.count2", "逾期及违约信息概要.资产处置信息笔数");
        RelEntity relEntity_281 = new RelEntity("003002.line1.balance2", "资产处置信息汇总余额", "icrFellBackSummary.balance2", "逾期及违约信息概要.资产处置信息余额");
        RelEntity relEntity_282 = new RelEntity("003002.line1.count3", "保证人代偿信息汇总笔数", "icrFellBackSummary.count3", "逾期及违约信息概要.保证人代偿信息笔数呆帐信息汇总笔数");
        RelEntity relEntity_283 = new RelEntity("003002.line1.balance3", "保证人代偿信息汇总余额", "icrFellBackSummary.balance3", "逾期及违约信息概要.保证人代偿信息余额呆帐信息汇总余额");
        RelEntity relEntity_284 = new RelEntity("004001.line1.organname#orderno", "资产管理公司", "icrAssetDisposition.organName", "资产处置信息.资产管理公司");
        RelEntity relEntity_285 = new RelEntity("004001.line1.gettime#orderno", "债务接收日期", "icrAssetDisposition.getTime", "资产处置信息.债务接收日期");
        RelEntity relEntity_286 = new RelEntity("004001.line1.money#orderno", "接收的债权金额", "icrAssetDisposition.money", "资产处置信息.接收的债权金额");
        RelEntity relEntity_287 = new RelEntity("004001.line1.latestrepaydate#orderno", "最近一次还款日期", "icrAssetDisposition.latestRepayDate", "资产处置信息.最近一次还款日期");
        RelEntity relEntity_288 = new RelEntity("004001.line1.balance#orderno", "余额", "icrAssetDisposition.balance", "资产处置信息.余额");


        //半刻
//        List<String> list1 = ReadFile.readLineData("src/main/resources/ioConf/banke.txt");
        List<String> list1 = ReadFile.readLineData("src/main/resources/ioConf/test1.txt");
        //大道
//        List<String> list2 = ReadFile.readLineData("src/main/resources/ioConf/dadao.txt");
        List<String> list2 = ReadFile.readLineData("src/main/resources/ioConf/test2.txt");
        for (int i = 0; i < list1.size(); i++) {
//            if (i != 9){
//                continue;
//            }
            List<String> list = compareEntitys(list1.get(i), list2.get(i), Arrays.asList(relEntity_1, relEntity_2, relEntity_3, relEntity_4, relEntity_5, relEntity_6, relEntity_7, relEntity_8, relEntity_9, relEntity_10, relEntity_11, relEntity_12, relEntity_13, relEntity_14, relEntity_15, relEntity_16, relEntity_17, relEntity_18, relEntity_19, relEntity_20, relEntity_21, relEntity_22, relEntity_23, relEntity_24, relEntity_25, relEntity_26, relEntity_27, relEntity_28, relEntity_29, relEntity_30, relEntity_31, relEntity_32, relEntity_33, relEntity_34, relEntity_35, relEntity_36, relEntity_37, relEntity_38, relEntity_39, relEntity_40, relEntity_41, relEntity_42, relEntity_43, relEntity_44, relEntity_45, relEntity_46, relEntity_47, relEntity_48, relEntity_49, relEntity_50, relEntity_51, relEntity_52, relEntity_53, relEntity_54, relEntity_55, relEntity_56, relEntity_57, relEntity_58, relEntity_59, relEntity_60, relEntity_61, relEntity_62, relEntity_63, relEntity_64, relEntity_65, relEntity_66, relEntity_67, relEntity_68, relEntity_69, relEntity_70, relEntity_71, relEntity_72, relEntity_73, relEntity_74, relEntity_75, relEntity_76, relEntity_77, relEntity_78, relEntity_79, relEntity_80, relEntity_81, relEntity_82, relEntity_83, relEntity_84, relEntity_85, relEntity_86, relEntity_87, relEntity_88, relEntity_89, relEntity_90, relEntity_91, relEntity_92, relEntity_93, relEntity_94, relEntity_95, relEntity_96, relEntity_97, relEntity_98, relEntity_99, relEntity_100, relEntity_101, relEntity_102, relEntity_103, relEntity_104, relEntity_105, relEntity_106, relEntity_107, relEntity_108, relEntity_109, relEntity_110, relEntity_111, relEntity_112, relEntity_113, relEntity_114, relEntity_115, relEntity_116, relEntity_117, relEntity_118, relEntity_119, relEntity_120, relEntity_121, relEntity_122, relEntity_123, relEntity_124, relEntity_125, relEntity_126, relEntity_127, relEntity_128, relEntity_129, relEntity_130, relEntity_131, relEntity_132, relEntity_133, relEntity_134, relEntity_135, relEntity_136, relEntity_137, relEntity_138, relEntity_139, relEntity_140, relEntity_141, relEntity_142, relEntity_143, relEntity_144, relEntity_145, relEntity_146, relEntity_147, relEntity_148, relEntity_149, relEntity_150, relEntity_151, relEntity_152, relEntity_153, relEntity_154, relEntity_155, relEntity_156, relEntity_157, relEntity_158, relEntity_159, relEntity_160, relEntity_161, relEntity_162, relEntity_163, relEntity_164, relEntity_165, relEntity_166, relEntity_167, relEntity_168, relEntity_169, relEntity_170, relEntity_171, relEntity_172, relEntity_173, relEntity_174, relEntity_175, relEntity_176, relEntity_177, relEntity_178, relEntity_179, relEntity_180, relEntity_181, relEntity_182, relEntity_183, relEntity_184, relEntity_185, relEntity_186, relEntity_187, relEntity_188, relEntity_189, relEntity_190, relEntity_191, relEntity_192, relEntity_193, relEntity_194, relEntity_195, relEntity_196, relEntity_197, relEntity_198, relEntity_199, relEntity_200, relEntity_201, relEntity_202, relEntity_203, relEntity_204, relEntity_205, relEntity_206, relEntity_207, relEntity_208, relEntity_209, relEntity_210, relEntity_211, relEntity_212, relEntity_213, relEntity_214, relEntity_215, relEntity_216, relEntity_217, relEntity_218, relEntity_219, relEntity_220, relEntity_221, relEntity_222, relEntity_223, relEntity_224, relEntity_225, relEntity_226, relEntity_227, relEntity_228, relEntity_229, relEntity_230, relEntity_231, relEntity_232, relEntity_233, relEntity_234, relEntity_235, relEntity_236, relEntity_237, relEntity_238, relEntity_239, relEntity_240, relEntity_241, relEntity_242, relEntity_243, relEntity_244, relEntity_245, relEntity_246, relEntity_247, relEntity_248, relEntity_249, relEntity_250, relEntity_251, relEntity_252, relEntity_253, relEntity_254, relEntity_255, relEntity_256, relEntity_257, relEntity_258, relEntity_259, relEntity_260, relEntity_261, relEntity_262, relEntity_263, relEntity_264, relEntity_265, relEntity_266, relEntity_267, relEntity_268, relEntity_269, relEntity_270, relEntity_271, relEntity_272, relEntity_273, relEntity_274, relEntity_275, relEntity_276, relEntity_277, relEntity_278, relEntity_279, relEntity_280, relEntity_281, relEntity_282, relEntity_283, relEntity_284, relEntity_285, relEntity_286, relEntity_287, relEntity_288));
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
