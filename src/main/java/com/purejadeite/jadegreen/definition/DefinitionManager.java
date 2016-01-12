package com.purejadeite.jadegreen.definition;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定義情報を管理するクラスです
 *
 * @author mitsuhiroseino
 *
 */
public class DefinitionManager {

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefinitionManager.class);

	// シート毎に全ての定義を保持するマップ
	private static Map<String, Map<String, MappingDefinition<?>>> definitions;

	public static boolean register(String sheetId, MappingDefinition<?> definition) {
		Map<String, MappingDefinition<?>> defs = getDefinitionMap(sheetId);
		return defs.put(definition.getId(), definition) == null;
	}

	private static Map<String, MappingDefinition<?>> getDefinitionMap(String sheetId) {
		Map<String, MappingDefinition<?>> defs = definitions.get(sheetId);
		if (defs == null) {
			defs = new HashMap<>();
			definitions.put(sheetId, defs);
		}
		return defs;
	}

	public static MappingDefinition<?> get(String sheetId, String id) {
		Map<String, MappingDefinition<?>> defs = definitions.get(sheetId);
		if (defs == null) {
			return null;
		}
		return defs.get(id);
	}

}
