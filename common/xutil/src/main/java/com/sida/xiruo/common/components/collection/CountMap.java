package com.sida.xiruo.common.components.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountMap<K> {


	private Map<Object,Integer> values = new HashMap<Object, Integer>();


	public int put(K key){
		if(values.containsKey(key)){
			return values.put(key, values.get(key) + 1);
		}else{
			return values.put(key, 1) == null? 0 : values.put(key, 1);
		}
	}


	public int get(K key){
		if(values.containsKey(key)){
			return values.get(key);
		}else{
			return 0;
		}
	}
	
	public Map<Object, Integer> toMap(){
		return this.values;
	}
	
	public String toString(){
		return this.values.toString();
	}
}
