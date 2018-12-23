package baseDemo.ListCombination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
/**
 * 组合数据
 * 		a x b x c的组合,例如:属性A有3个属性值,属性B有2个属性值
 * 		那么属性值的组合情况就有3x2=6种
 */
public class ListCombination {
	
	/**
	 * 数组组合
	 * @param list 返回数据的容器
	 * @param count 源数据数组的长度
	 * @param array 源数据
	 * @param start 开始位置
	 * @param indexs 下标数组
	 * @return list
	 */
	private static LinkedList<String[]> recursionArr(
			LinkedList<String[]> list, int count,
			String[][] array, int start, int... indexs) {
		start ++;
		if (start > count - 1) {
			return null;
		}
		if (start == 0) {
			indexs = new int[array.length];
		}
		for (indexs[start] = 0; indexs[start] < array[start].length; indexs[start]++) {
			recursionArr(list, count, array, start, indexs);
			if (start == count - 1) {
				String[] temp = new String[count];
				for (int i = count - 1; i >= 0; i--) {
					temp[start - i] = array[start - i][indexs[start - i]];
				}
				list.add(temp);
			}
		}
		return list;
	}
	
	/**
	 * 集合String测试
	 * 
	 */
	private static LinkedList<ArrayList<String>> recursionStr (
			 LinkedList<ArrayList<String>> list, int count, 
			 ArrayList<ArrayList<String>> array, 
			 int ind,int start, int... indexs ){
	        start ++;
	        if (start > count - 1){
	            return null;
	        }
	        if (start == 0){
	        	 indexs = new int[array.size()];
	        }
	        for ( indexs[start] = 0; indexs[start] < array.get(start).size();  indexs[start]++ ){
				recursionStr (list, count, array, 0, start, indexs);
	            if (start == count - 1){
	                ArrayList<String> temp = new ArrayList<>();
	                for ( int i = count - 1; i >= 0; i-- ){
	                    String s =  array.get(start - i).get(indexs[start - i]);
	                    temp.add(start - i,s);
	                }
	                list.add (temp);
	            }
	        }
	        return list;
	    }
	
	/**
	 * 集合对象测试
	 * 
	 */
	private static LinkedList<ArrayList<AttributeValue>> recursionList ( 
			 LinkedList<ArrayList<AttributeValue>> list, int count, 
			 ArrayList<ArrayList<AttributeValue>> array, 
			 int ind,int start, int... indexs ){
	        start ++;
	        if (start > count - 1){
	            return null;
	        }
	        if (start == 0){
	        	 indexs = new int[array.size()];
	        }
	        for ( indexs[start] = 0; indexs[start] < array.get(start).size();  indexs[start]++ ){
	            recursionList (list, count, array, 0, start, indexs);
	            if (start == count - 1){
	                ArrayList<AttributeValue> temp = new ArrayList<>();
	                for ( int i = count - 1; i >= 0; i-- ){
	                	AttributeValue s =  array.get(start - i).get(indexs[start - i]);
	                    temp.add(start - i,s);
	                }
	                list.add (temp);
	            }
	        }
	        return list;
	    }
	
	/**
	 * 测试
	 * 
	 */
	public static void main(String[] args) {
		System.out.println("===================数组测试========================");
		String[] s1 = { "a", "b"};
		String[] s2 = { "d", "e"};
		String[] s3 = { "x", "y"};
		String[][] temp = { s1, s2, s3 };
		LinkedList<String[]> list = new LinkedList<>();
		recursionArr(list, temp.length, temp, -1);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(Arrays.toString(list.get(i)).replaceAll("[\\[\\]\\,\\s]", ""));
		}
		
//		System.out.println("===================集合String测试=====================");
//        ArrayList<String> list1 = new ArrayList<>();
//        list1.add("集合1");
//        list1.add("集合2");
//        ArrayList<String> list2 = new ArrayList<>();
//        list2.add("数据1");
//        list2.add("数据2");
//        ArrayList<ArrayList<String>> lst = new ArrayList<>();
//        lst.add(list1);
//        lst.add(list2);
//        LinkedList<ArrayList<String>> l = new LinkedList<>();
//        recursionStr(l, lst.size(), lst, 0, -1);
//        for(ArrayList<String> arr : l){
//        	System.out.println(arr);
//        }
//
//        System.out.println("===================集合对象测试========================");
//        AttributeValue value1 = new AttributeValue(1,"属性值1");
//        AttributeValue value2 = new AttributeValue(2,"属性值2");
//        ArrayList<AttributeValue> lst1 = new ArrayList<>();
//        lst1.add(value1);
//        lst1.add(value2);
//        AttributeValue value3 = new AttributeValue(3,"属性值3");
//        AttributeValue value4 = new AttributeValue(4,"属性值4");
//        ArrayList<AttributeValue> lst2 = new ArrayList<>();
//        lst2.add(value3);
//        lst2.add(value4);
//        ArrayList<ArrayList<AttributeValue>> lt = new ArrayList<>();
//        lt.add(lst1);
//        lt.add(lst2);
//        LinkedList<ArrayList<AttributeValue>> v = new LinkedList<>();
//        recursionList(v, lt.size(), lt, 0, -1);
//        for(ArrayList<AttributeValue> av : v){
//        	 System.out.println(av);
//        }
	}
}
