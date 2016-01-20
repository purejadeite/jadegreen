package com.purejadeite.jadegreen.definition.option.worksheet;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.option.Option;
import com.purejadeite.jadegreen.definition.option.OptionManager;
import com.purejadeite.jadegreen.definition.option.Options;

/**
 * Worksheetの変換を行うコンバーターインスタンスを生成するクラスです
 * @author mitsuhiroseino
 *
 */
public class WorksheetOptionManager {

	private static OptionManager<WorksheetOption> manager;

	static {
		// マネージャーの初期化処理
		manager = new OptionManager<>();
	}

	public static void register(Class<WorksheetOption> clazz) {
		manager.register(clazz);
	}

	public static Options build(String id, List<Map<String, Object>> opts) {
		return manager.build(id, opts);
	}

	public static Option build(String id, String type, Map<String, Object> config) {
		return manager.build(id, type, config);
	}

}
