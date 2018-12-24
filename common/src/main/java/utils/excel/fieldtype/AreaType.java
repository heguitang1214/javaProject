package utils.excel.fieldtype;//package excel.fieldtype;
//
//import com.thinkgem.jeesite.common.utils.StringUtils;
//import com.thinkgem.jeesite.modules.sys.entity.Area;
//import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
//
///**
// * 字段类型转换
// * @author ThinkGem
// * @version 2013-03-10
// */
//public class AreaType {
//
//	/**
//	 * 获取对象值（导入）
//	 */
//	public static Object getValue(String val) {
//		for (Area e : UserUtils.getAreaList()){
//			if (StringUtils.trimToEmpty(val).equals(e.getName())){
//				return e;
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 获取对象值（导出）
//	 */
//	public static String setValue(Object val) {
//		if (val != null && ((Area)val).getName() != null){
//			return ((Area)val).getName();
//		}
//		return "";
//	}
//}
