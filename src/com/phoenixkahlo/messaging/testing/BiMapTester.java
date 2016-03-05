package com.phoenixkahlo.messaging.testing;

import com.phoenixkahlo.messaging.utils.BiMap;

public class BiMapTester {

	public static void main(String[] args) {
		BiMap<String, String> map = new BiMap<String, String>();
		map.link("a", "a");
		map.link("b", "b");
		map.link("c", "c");
		System.out.println(map);
		map.link("b", "d");
		System.out.println(map);
		map.link("f", "c");
		System.out.println(map);
		try {
			map.link("b", "c");
		} catch (RuntimeException e) {
			System.out.println("Correctly threw RuntimeException");
		}
		System.out.println(map);
		map.link("a", "a");
		System.out.println(map);
	}
	
}
