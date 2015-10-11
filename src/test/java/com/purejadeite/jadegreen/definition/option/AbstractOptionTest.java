package com.purejadeite.jadegreen.definition.option;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Applier;
import com.purejadeite.jadegreen.definition.DefinitionKeys;
import com.purejadeite.jadegreen.definition.option.cell.CellOptionManager;

abstract public class AbstractOptionTest {

	protected static final Map<String, Object> EMPTY_CONFIG = new HashMap<>();

	protected void assertOption(Applier option, Object source, Object expected) {
		assertThat(option.apply(source), equalTo(expected));
	}

	protected void assertOption(Class<?> clazz, Object source, Object expected) {
		assertThat(getOption(clazz).apply(source), equalTo(expected));
	}

	protected Applier getOption(Class<?> clazz) {
		return getOption(clazz.getSimpleName());
	}

	protected Applier getOption(String type) {
		Map<String, Object> config = new HashMap<>();
		config.put(DefinitionKeys.TYPE, type);
		return CellOptionManager.build(type, config);
	}

	protected Map<String, Object> getConfig(String type) {
		Map<String, Object> config = new HashMap<>();
		config.put(DefinitionKeys.TYPE, type);
		return config;
	}

}
