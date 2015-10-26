package com.purejadeite.jadegreen.definition.option.cell;

import org.junit.Test;

import com.purejadeite.jadegreen.definition.option.AbstractOptionTest;

public class UpperCamelTest extends AbstractOptionTest {

	@Test
	public void fromLowerCamel() {
		assertOption(UpperCamel.class, "abcDef" ,"AbcDef");
	}

	@Test
	public void fromLowerHyphen() {
		assertOption(UpperCamel.class, "abc-def" ,"AbcDef");
	}

	@Test
	public void fromLowerUnderscore() {
		assertOption(UpperCamel.class, "abc_def" ,"AbcDef");
	}

	@Test
	public void fromUpperCamel() {
		assertOption(UpperCamel.class, "AbcDef" ,"AbcDef");
	}

	@Test
	public void fromUpperHyphen() {
		assertOption(UpperCamel.class, "ABC-DEF" ,"AbcDef");
	}

	@Test
	public void fromUpperUnderscore() {
		assertOption(UpperCamel.class, "ABC_DEF" ,"AbcDef");
	}

	@Test
	public void fromLowerCamel1() {
		assertOption(UpperCamel.class, "aBcDef" ,"ABcDef");
	}

}
