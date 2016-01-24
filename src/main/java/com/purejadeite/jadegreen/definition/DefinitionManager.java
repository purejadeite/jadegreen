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

	private static ThreadLocal<DefinitionManager> tl = new ThreadLocal<>();

	/**
	 * シートから配下のdefinitionを全て取得するMap
	 */
	private Map<String, Map<String, Definition<?>>> keySheetValDefs;

	/**
	 * idからシートのdefinitionを取得するMap
	 */
	private Map<String, WorksheetDefinition> keyIdValSheet;

	/**
	 * definitionからシートを取得するMap
	 */
	private Map<String, WorksheetDefinition> keyDefValSheet;

	private DefinitionManager() {
	}

	public void init() {
		keySheetValDefs = new HashMap<>();
		keyIdValSheet = new HashMap<>();
		keyDefValSheet = new HashMap<>();
	}

	public static DefinitionManager getInstance() {
		DefinitionManager dm = tl.get();
		if (dm == null) {
			dm = createInstance();
		}
		return dm;
	}

	public synchronized static DefinitionManager createInstance() {
		DefinitionManager dm = tl.get();
		if (dm == null) {
			dm = new DefinitionManager();
			tl.set(dm);
		}
		return dm;
	}

	public boolean register(WorksheetDefinition sheet, Definition<?> definition) {
		keyDefValSheet.put(definition.getKey(), sheet);
		String sheetId = sheet.getId();
		keyIdValSheet.put(sheetId, sheet);
		Map<String, Definition<?>> defs = getDefinitionMap(sheetId);
		return defs.put(definition.getId(), definition) == null;
	}

	private Map<String, Definition<?>> getDefinitionMap(String sheetId) {
		Map<String, Definition<?>> defs = keySheetValDefs.get(sheetId);
		if (defs == null) {
			defs = new HashMap<>();
			keySheetValDefs.put(sheetId, defs);
		}
		return defs;
	}

	public Definition<?> get(WorksheetDefinition sheet, String id) {
		return get(sheet.getId(), id);
	}

	public Definition<?> get(String sheetId, String id) {
		Map<String, Definition<?>> defs = keySheetValDefs.get(sheetId);
		if (defs == null) {
			return null;
		}
		return defs.get(id);
	}

	public WorksheetDefinition get(String sheetId) {
		return keyIdValSheet.get(sheetId);
	}

	public WorksheetDefinition getSheet(Definition<?> definition) {
		return keyDefValSheet.get(definition.getKey());
	}

	public Definition<?> getSheetsDefinition(Definition<?> definition, String id) {
		WorksheetDefinition sheet = getSheet(definition);
		if (sheet == null) {
			return null;
		}
		return get(sheet, id);
	}

}
