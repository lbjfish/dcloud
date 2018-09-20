package com.sida.xiruo.common.util;


import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 
 * @author jianglfa       
 * @version 1.0     
 * @created Aug 18, 2011 4:30:37 PM    
 */

public class Test {

	/**     
	 * 
	 * @param args     
	 */
	public static void main(String[] args) throws Exception {

		double a=37, b=0;
		System.out.println(a/b);
		System.out.println(new BigDecimal(a/b).setScale(1, BigDecimal.ROUND_HALF_UP));
		System.out.println(new BigDecimal(a/b).setScale(2, BigDecimal.ROUND_HALF_UP));
		System.out.println(new BigDecimal(a/b).setScale(3, BigDecimal.ROUND_HALF_UP));
		System.out.println(new BigDecimal(a/b).setScale(4, BigDecimal.ROUND_HALF_UP));

		System.out.println(new BigDecimal(a).divide(new BigDecimal(b), 1, BigDecimal.ROUND_HALF_UP));
		System.out.println(new BigDecimal(a).divide(new BigDecimal(b), 2, BigDecimal.ROUND_HALF_UP));
		System.out.println(new BigDecimal(a).divide(new BigDecimal(b), 3, BigDecimal.ROUND_HALF_UP));
		System.out.println(new BigDecimal(a).divide(new BigDecimal(b), 4, BigDecimal.ROUND_HALF_UP));

		/*
		String content = ",3,2,";
		System.out.println("contains:"+content.contains(",4,"));

		List<Map> mapList = new ArrayList<>();
		Map m1 = new HashMap();
		Map m2 = new HashMap();
		Map m3 = new HashMap();
		Map m4 = new HashMap();
		m1.put("A", 3.54);
		m2.put("A", 7.54);
		m3.put("A", 1.54);
		m4.put("A", 0.54);
		mapList.add(m1);
		mapList.add(m2);
		mapList.add(m3);
		mapList.add(m4);

		mapList = mapList.stream().sorted((o1,o2)->{
			Double r1 = Double.parseDouble(o1.get("A").toString());
			Double r2 = Double.parseDouble(o2.get("A").toString());
			return r1.compareTo(r2);
		}).collect(Collectors.toList());
		int rank = 1;
		for (int i=mapList.size()-1; i>=0; i--) {
			mapList.get(i).put("rank", rank++);
		}
		double sum = mapList.stream().mapToDouble((e -> Double.parseDouble(e.get("A").toString()))).sum();


		Map<Object, Double> areaMap = new HashMap<>();
		areaMap.put("A",1.34);
		areaMap.put("B",7.34);
		areaMap.put("C",2.34);
		areaMap.put("D",0.34);

		List<Map.Entry<Object, Double>> list2 = new ArrayList<>();
		list2.addAll(areaMap.entrySet());
		Collections.sort(list2, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));
		list2.forEach(entry -> {
			System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
		});


		System.out.println(sum);
		System.out.println(mapList);
*/

	}

}
