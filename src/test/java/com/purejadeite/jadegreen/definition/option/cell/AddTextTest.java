package com.purejadeite.jadegreen.definition.option.cell;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.purejadeite.jadegreen.definition.option.AbstractOptionTest;
import com.purejadeite.jadegreen.option.cell.Append;

public class AddTextTest extends AbstractOptionTest {

	@Test
	public void prefix() {
		Map<String, Object> config = new HashMap<>();
		config.put("prefix", "0");
		Append option = new Append(null, config);
		assertOption(option, "ABC" ,"0ABC");
	}

	@Test
	public void suffix() {
		Map<String, Object> config = new HashMap<>();
		config.put("suffix", "1");
		Append option = new Append(null, config);
		assertOption(option, "ABC" ,"ABC1");
	}

	@Test
	public void prefixSuffix() {
		Map<String, Object> config = new HashMap<>();
		config.put("prefix", "0");
		config.put("suffix", "1");
		Append option = new Append(null, config);
		assertOption(option, "ABC" ,"0ABC1");
	}

	@Test
	public void emptyValue() {
		Map<String, Object> config = new HashMap<>();
		config.put("prefix", "0");
		config.put("suffix", "1");
		config.put("ignoreEmpty", true);
		Append option = new Append(null, config);
		assertOption(option, "" ,"");
	}

	@Test
	public void nullValue() {
		Map<String, Object> config = new HashMap<>();
		config.put("prefix", "0");
		config.put("suffix", "1");
		config.put("ignoreEmpty", true);
		Append option = new Append(null, config);
		assertOption(option, null ,null);
	}

}
