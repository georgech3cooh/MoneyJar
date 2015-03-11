package com.khominhvi.moneyjar.hibernate;

import java.util.ArrayList;
import java.util.List;

public class QueryStringUtil {

	public static String stringArrayToString(String[] strings) {
		
		StringBuilder sb = new StringBuilder();
		for(String n : strings){
			if(sb.length() > 0) sb.append(',');
			sb.append("'").append(n).append("'");
		}
		return sb.toString();
	}
	
	public static List<Long> stringArrayToLongList(String[] strings) {
		List<Long> longList = new ArrayList<>();
		
		for(String n : strings) {
			longList.add(new Long(n));
		}
		
		return longList;
	}
	
}
