package com.purejadeite.jadegreen.definition;

import java.util.HashMap;
import java.util.Map;

/**
 * 定義情報を管理するクラスです
 *
 * @author mitsuhiroseino
 *
 */
public class DefinitionManager {

	// シート毎に全ての定義を保持するマップ
	private static Map<String, Map<String, Definition<?>>> definitions = new HashMap<>();

	private static Map<String, WorksheetDefinition> sheetDefinitions = new HashMap<>();

	private static Map<String, WorksheetDefinition> defSheet = new HashMap<>();

	public static boolean register(WorksheetDefinition sheet, Definition<?> definition) {
		defSheet.put(definition.getFullId(), sheet);
		String sheetId = sheet.getId();
		sheetDefinitions.put(sheetId, sheet);
		Map<String, Definition<?>> defs = getDefinitionMap(sheetId);
		return defs.put(definition.getId(), definition) == null;
	}

	private static Map<String, Definition<?>> getDefinitionMap(String sheetId) {
		Map<String, Definition<?>> defs = definitions.get(sheetId);
		if (defs == null) {
			defs = new HashMap<>();
			definitions.put(sheetId, defs);
		}
		return defs;
	}

	public static Definition<?> get(WorksheetDefinition sheet, String id) {
		return get(sheet.getId(), id);
	}

	public static Definition<?> get(String sheetId, String id) {
		Map<String, Definition<?>> defs = definitions.get(sheetId);
		if (defs == null) {
			return null;
		}
		return defs.get(id);
	}

	public static WorksheetDefinition get(String sheetId) {
		return sheetDefinitions.get(sheetId);
	}

	public static WorksheetDefinition getSheet(Definition<?> definition) {
		return defSheet.get(definition.getFullId());
	}

	public static Definition<?> getSheetsDefinition(Definition<?> definition, String id) {
		WorksheetDefinition sheet = getSheet(definition);
		if (sheet == null) {
			return null;
		}
		return get(sheet, id);
	}

}
