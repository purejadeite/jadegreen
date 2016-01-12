package com.purejadeite.jadegreen.content;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * コンテンツを管理するクラスです
 *
 * @author mitsuhiroseino
 *
 */
public class ContentManager {

	/**
	 * ロガー
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ContentManager.class);

	// シート毎に全ての定義を保持するマップ
	private static Map<String, Map<String, Content<?>>> contents;

	public static boolean register(String sheetId, Content<?> content) {
		Map<String, Content<?>> defs = getDefinitionMap(sheetId);
		return defs.put(content.getId(), content) == null;
	}

	private static Map<String, Content<?>> getDefinitionMap(String sheetId) {
		Map<String, Content<?>> cons = contents.get(sheetId);
		if (cons == null) {
			cons = new HashMap<>();
			contents.put(sheetId, cons);
		}
		return cons;
	}

	public static Content<?> get(String sheetId, String id) {
		Map<String, Content<?>> cons = contents.get(sheetId);
		if (cons == null) {
			return null;
		}
		return cons.get(id);
	}

}
