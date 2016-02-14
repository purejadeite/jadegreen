package com.purejadeite.jadegreen.definition.option.cell;

import org.junit.Test;

import com.purejadeite.jadegreen.definition.option.AbstractOptionTest;
import com.purejadeite.jadegreen.option.cell.Upper;

public class UpperTest extends AbstractOptionTest {

	@Test
	public void half() {
		assertOption(Upper.class, "abc", "ABC");
	}

	@Test
	public void em() {
		assertOption(Upper.class, "ａｂｃ", "ＡＢＣ");
	}

}
