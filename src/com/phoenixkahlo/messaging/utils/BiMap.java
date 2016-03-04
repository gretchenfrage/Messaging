package com.phoenixkahlo.messaging.utils;

import java.util.ArrayList;
import java.util.List;

public class BiMap<A, B> {

	private class Pair {
		A a;
		B b;
		Pair(A a, B b) {
			this.a = a;
			this.b = b;
		}
	}
	
	private List<Pair> pairs = new ArrayList<Pair>();
	
	public void link(A a, B b) throws RuntimeException {
		for (Pair pair : pairs) {
			if (pair.a.equals(a)) {
				// a exists
				if (pair.b.equals(b)) {
					// a and b both exist and are linked together
					return;
				} else {
					// a exists and is not linked to b, if b exists
					for (Pair pair2 : pairs) {
						if (pair2.b.equals(b)) {
							// a and b both exist and are not linked together
							throw new RuntimeException("Duplicate items");
						}
					}
					// a exists and b does not exist
					pair.b = b;
					return;
				}
			}
		}
		// a does not exist
		for (Pair pair : pairs) {
			if (pair.b.equals(b)) {
				// a does not exist but b does
				pair.a = a;
				return;
			}
		}
		// neither a nor b exist
		pairs.add(new Pair(a, b));
	}
	
	public A getA(B b) {
		for (Pair pair : pairs) {
			if (pair.b.equals(b)) return pair.a;
		}
		return null;
	}
	
	public B getB(A a) {
		for (Pair pair : pairs) {
			if (pair.a.equals(a)) return pair.b;
		}
		return null;
	}
	
}
