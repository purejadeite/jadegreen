package com.purejadeite.jadegreen;

import org.junit.Test;

import com.purejadeite.util.RoughlyConverter;

public class AnyTest {

	@Test
	public void test1() {
		Number v = RoughlyConverter.intoByte(12.2);
		System.out.println(v);
		if (v != null) {
			System.out.println(v.getClass().getName());
			System.out.println(v.longValue() == (long) v.byteValue());

		}
	}

}
