package com.entonomachia.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class QueryResultTest {
	QueryResult testRes;
	@Before
	public void setUp() throws Exception {
		testRes = new QueryResult();
	}

	@Test
	public void testQueryResultDefaultValue() {
		assertEquals(testRes.toString() , "placeholder");
	}

	@Test
	public void testReadJson() {
		assertTrue(true);
	}

}
