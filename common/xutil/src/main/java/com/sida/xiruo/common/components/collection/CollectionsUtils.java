package com.sida.xiruo.common.components.collection;

import com.sida.xiruo.common.components.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionsUtils<E>{

	public static <E> List<E> getDuplicateElements(List<E> list) {
		if(list!=null&&list.size()==0){
			return null;
		}
		return list.stream()                           // list 对应的 Stream
				.collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
				.entrySet().stream()                   // 所有 entry 对应的 Stream
				.filter(entry -> entry.getValue() > 1) // 过滤出元素出现次数大于 1 的 entry
				.map(entry -> entry.getKey())          // 获得 entry 的键（重复元素）对应的 Stream
				.collect(Collectors.toList());         // 转化为 List
	}


	public static <E extends Object> Collection<E> contact(Collection <E> c1, Collection<E> c2){
		if(c1 == null){
			return c2;
		}else if(c2 == null){
			return c1;
		}
		c1.addAll(c2);
		return c1;
	}

	public static  List<String> convertListWithOperator(List<String> list,String operator){
		if(list == null){
			return list;
		}
		List<String> c1=new ArrayList<>();
		for(String key:list){
			c1.add(operator+key+operator);
		}
		return c1;
	}
	public static  Set<String> convertSetWithOperator(Set<String> set,String operator){
		if(set == null){
			return set;
		}
		Set<String> c1=new HashSet<>();
		for(String key:set){
			c1.add(operator+key+operator);
		}
		return c1;
	}
	
	public static List subList(List sourceList, int fromIndex, int toIndex){
		int endIndex = sourceList.size();
		if(fromIndex > endIndex){
			fromIndex = endIndex;
		}else if(fromIndex < 0){
			fromIndex = 0;
		}
		if(toIndex > endIndex){
			toIndex = endIndex;
		}
		return sourceList.subList(fromIndex, toIndex);
	}
	
	public static List<Object> subList(List<Object> sourceList, int fromIndex){
		return subList(sourceList,fromIndex, sourceList.size());
	}
	
	public static <T> List<T> toList(T... os){		
		return Arrays.asList(os);
	}
	
	public static List<String> toStringList(String... strings){
		return toList(strings);
	}
	
	public static List<Integer> toIntList(int... ints){		 
		int len = ints.length;
		List<Integer> list = new ArrayList<Integer>(len);
		for(int i : ints){
			list.add(i);
		}
		return list;
	}


	public static Map<String, Object> subMap(Map<String, Object> sourceMap, Map<String, Object> targetMap, String keyPrefix){
		Set<Map.Entry<String, Object>> entrys = sourceMap.entrySet();
		for(Map.Entry<String, Object> entry : entrys){
			String key = entry.getKey();
			if(key != null && key.startsWith(keyPrefix)){
				targetMap.put(key.replace(keyPrefix, ""), entry.getValue());
			}
		}
		return targetMap;
	}
	
	public static void print(Collection<?> c){
		ArrayUtils.print(c.toArray());
	}
	
	public static boolean inList(String id, List<String> idList){
		for(String i : idList){
			if(i.equals(id)){
				return true;
			}
		}
		return false;
	}


}
