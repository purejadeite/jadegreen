package com.purejadeite.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.AbstractTest;

/**
 * Unit test for simple App.
 */
public class ComparisionUtilsTest extends AbstractTest {

	private static Logger LOGGER = LoggerFactory.getLogger(ComparisionUtilsTest.class);

	@Test
	public void equalTo() throws IOException {
		assertTrue(EvaluationUtils.equalTo(1, 1));
		assertFalse(EvaluationUtils.equalTo(1, 2));
		assertFalse(EvaluationUtils.equalTo(2, 1));
		assertTrue(EvaluationUtils.equalTo(null, null));
		assertFalse(EvaluationUtils.equalTo(1, null));
		assertFalse(EvaluationUtils.equalTo(null, 1));
	}

	@Test
	public void notEqualTo() throws IOException {
		assertFalse(EvaluationUtils.notEqualTo(1, 1));
		assertTrue(EvaluationUtils.notEqualTo(1, 2));
		assertTrue(EvaluationUtils.notEqualTo(2, 1));
		assertFalse(EvaluationUtils.notEqualTo(null, null));
		assertTrue(EvaluationUtils.notEqualTo(1, null));
		assertTrue(EvaluationUtils.notEqualTo(null, 1));
	}

	@Test
	public void greaterThan() throws IOException {
		assertFalse(EvaluationUtils.greaterThan(1, 1));
		assertFalse(EvaluationUtils.greaterThan(1, 2));
		assertTrue(EvaluationUtils.greaterThan(2, 1));
		assertFalse(EvaluationUtils.greaterThan(null, null));
		assertTrue(EvaluationUtils.greaterThan(1, null));
		assertFalse(EvaluationUtils.greaterThan(null, 1));
	}

	@Test
	public void greaterThanOrEqualTo() throws IOException {
		assertTrue(EvaluationUtils.greaterThanOrEqualTo(1, 1));
		assertFalse(EvaluationUtils.greaterThanOrEqualTo(1, 2));
		assertTrue(EvaluationUtils.greaterThanOrEqualTo(2, 1));
		assertTrue(EvaluationUtils.greaterThanOrEqualTo(null, null));
		assertTrue(EvaluationUtils.greaterThanOrEqualTo(1, null));
		assertFalse(EvaluationUtils.greaterThanOrEqualTo(null, 1));
	}

	@Test
	public void lessThan() throws IOException {
		assertFalse(EvaluationUtils.lessThan(1, 1));
		assertTrue(EvaluationUtils.lessThan(1, 2));
		assertFalse(EvaluationUtils.lessThan(2, 1));
		assertFalse(EvaluationUtils.lessThan(null, null));
		assertFalse(EvaluationUtils.lessThan(1, null));
		assertTrue(EvaluationUtils.lessThan(null, 1));
	}

	@Test
	public void lessThanOrEqualTo() throws IOException {
		assertTrue(EvaluationUtils.lessThanOrEqualTo(1, 1));
		assertTrue(EvaluationUtils.lessThanOrEqualTo(1, 2));
		assertFalse(EvaluationUtils.lessThanOrEqualTo(2, 1));
		assertTrue(EvaluationUtils.lessThanOrEqualTo(null, null));
		assertFalse(EvaluationUtils.lessThanOrEqualTo(1, null));
		assertTrue(EvaluationUtils.lessThanOrEqualTo(null, 1));
	}
}
