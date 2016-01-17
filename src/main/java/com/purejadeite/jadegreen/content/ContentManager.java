package com.purejadeite.jadegreen.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * コンテンツを管理するクラスです
 *
 * @author mitsuhiroseino
 *
 */
public class ContentManager {

	// シート毎に全ての定義を保持するマップ
	private static Map<WorksheetContent, Content<?>> contents0 = new HashMap<>();
	private static Map<String, Map<String, Map<String, Content<?>>>> contents = new HashMap<>();

	// definitionからcontentを取れるマップ
	private static Map<String, List<Content<?>>> defCons = new HashMap<>();

	// contentからsheetを取れるマップ(ContentにtoHashを実装しないとだめ)
	private static Map<Content<?>, WorksheetContent> conSheet = new HashMap<>();

	public static boolean register(WorksheetContent sheet, Content<?> content) {
//		conSheet.put(content, sheet);
		return register(sheet.getId(), sheet.getSheetName(), content);
	}

	public static boolean register(String sheetId, String sheetName, Content<?> content) {
		// definitionからcontentを取れるマップ
		List<Content<?>> cons = defCons.get(content.getDefinition().getFullId());
		if (cons == null) {
			cons = new ArrayList<>();
			defCons.put(content.getDefinition().getFullId(), cons);
		}
		cons.add(content);
		// シート毎に全ての定義を保持するマップ
		Map<String, Content<?>> sheetContents = getSheetContents(sheetId, sheetName, true);
		return sheetContents.put(content.getId(), content) == null;
	}


	public static boolean register(String sheetId, String sheetName, List<Content<?>> contents) {
		boolean result = true;
		for (Content<?> content : contents) {
			if (!register(sheetId, sheetName, content)) {
				result = false;
			}
		}
		return result;
	}

	private static Map<String, Content<?>> getSheetContents(String sheetId, String sheetName, boolean create) {
		Map<String, Map<String, Content<?>>> sheets = contents.get(sheetId);
		if (sheets == null) {
			if (create) {
				sheets = new HashMap<>();
				contents.put(sheetId, sheets);
			} else {
				return null;
			}
		}
		Map<String, Content<?>> sheetContents = sheets.get(sheetName);
		if (sheetContents == null) {
			if (create) {
				sheetContents = new HashMap<>();
				sheets.put(sheetName, sheetContents);
			} else {
				return null;
			}
		}
		return sheetContents;
	}

	public static Content<?> get(WorksheetContent sheet, Definition<?> definition) {
		return get(sheet, definition.getId());
	}

	public static Content<?> get(WorksheetContent sheet, String id) {
		return get(sheet.getDefinition().getId(), sheet.getSheetName(), id);
	}

	public static Content<?> get(String sheetId, String sheetName, String id) {
		Map<String, Content<?>> sheetContents = getSheetContents(sheetId, sheetName, false);
		if (sheetContents == null) {
			return null;
		}
		return sheetContents.get(id);
	}

	public static List<Content<?>> get(String sheetId, String id) {
		Map<String, Map<String, Content<?>>> sheets = contents.get(sheetId);
		if (sheets == null) {
			return null;
		}
		List<Content<?>> c = new ArrayList<>();
		for (Map<String, Content<?>> sheetContents : sheets.values()) {
			Content<?> content = sheetContents.get(id);
			if (content != null) {
				c.add(content);
			}
		}
		return c;
	}

	public static List<Content<?>> get(Definition<?> definition) {
		return defCons.get(definition.getFullId());
	}

	public static Content<?> getSheet(Content<?> leftDef, Content<?> rightDef) {
		return null;
	}

}
