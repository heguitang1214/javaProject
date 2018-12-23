package ai.yunxi.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author Five
 * @createTime 2018年5月9日 下午7:38:34
 * 
 */
public class JsonUtil {
	
	public static String fromBean(Object obj) {
		Class<? extends Object> clz = obj.getClass();
		Field[] declaredFields = clz.getDeclaredFields();
		JSONObject json = new JSONObject();
		try {
			for (Field field : declaredFields) {
				String fieldName = field.getName();
				Method method = clz.getDeclaredMethod("get"+(fieldName.substring(0,1)).toUpperCase()+fieldName.substring(1));
				Object value = method.invoke(obj);
				json.put(fieldName, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	public static JSONArray fromList(List<Object> list) {
		try {
			if(list != null){
				JSONArray json = new JSONArray();
				for (int i = 0; i < list.size(); i++) {
					Object obj = list.get(i);
					Class<? extends Object> clz = obj.getClass();
					Field[] declaredFields = clz.getDeclaredFields();
					JSONObject jsonObj = new JSONObject();
					for (Field field : declaredFields) {
						String fieldName = field.getName();
						Method method = clz.getDeclaredMethod("get"+(fieldName.substring(0,1)).toUpperCase()+fieldName.substring(1));
						Object value = method.invoke(obj);
						jsonObj.put(fieldName, value);
					}
					json.put(jsonObj);
				}
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
