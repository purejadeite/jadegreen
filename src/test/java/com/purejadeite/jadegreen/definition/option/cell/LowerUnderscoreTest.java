package com.purejadeite.jadegreen.definition.option.cell;

import org.junit.Test;

import com.purejadeite.jadegreen.definition.option.AbstractOptionTest;

public class LowerUnderscoreTest extends AbstractOptionTest {

	@Test
	public void fromLowerCamel() {
		assertOption(LowerUnderscore.class, "abcDef" ,"abc_def");
	}

	@Test
	public void fromLowerHyphen() {
		assertOption(LowerUnderscore.class, "abc-def" ,"abc_def");
	}

	@Test
	public void fromLowerUnderscore() {
		assertOption(LowerUnderscore.class, "abc_def" ,"abc_def");
	}

	@Test
	public void fromUpperCamel() {
		assertOption(LowerUnderscore.class, "AbcDef" ,"abc_def");
	}

	@Test
	public void fromUpperHyphen() {
		assertOption(LowerUnderscore.class, "ABC-DEF" ,"abc_def");
	}

	@Test
	public void fromUpperUnderscore() {
		assertOption(LowerUnderscore.class, "ABC_DEF" ,"abc_def");
	}

}
