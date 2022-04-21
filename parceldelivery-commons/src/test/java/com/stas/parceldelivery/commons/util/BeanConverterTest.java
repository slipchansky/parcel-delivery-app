package com.stas.parceldelivery.commons.util;
import lombok.*;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class BeanConverterTest {
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class A {
		public String a;
		public String b;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class B {
		public String a;
		public String b;
	}
	
	@Test
	public void testConvert() {
		A a = new A("a", "b");
		B b = from(a).to(B.class);
		assertEquals("a", b.getA());
		assertEquals("b", b.getB());
	}
	
	@Test
	public void testMerge() {
		A a = new A("a", "b");
		B b = new B("x", null);
		Object c = from(a).with(b);
		assertEquals(A.class, c.getClass());
		assertTrue(c == a);
		assertEquals("x", a.getA());
		assertEquals("b", a.getB());
	}
	
	@Test
	public void testStream() {
		A a = new A("a", "b");
		B b = from(a)
				.convert(B.class)
				.update(x -> x.setB("x"))
				.get();
		
		assertEquals(B.class, b.getClass());
		assertEquals("a", b.getA());
		assertEquals("x", b.getB());
	}
	
	

}
