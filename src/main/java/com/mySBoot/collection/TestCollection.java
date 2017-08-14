package com.mySBoot.collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Null;

public class TestCollection {

	public static void main(String[] args) {
		Set<String> set = new HashSet<>();
		set.add("a");
		set.add("a");
		set.add("b");
		set.add("c");
		for (String s : set) {
			System.out.println(s);
		}
		Map<Object, Object> map = new HashMap<>();
		map.put(null, null);
		System.out.println(map.get(null));
		Map<Object, Object> table = new Hashtable<>();
		table.put(null, null);
		System.out.println(table.get(null));
	}
}
