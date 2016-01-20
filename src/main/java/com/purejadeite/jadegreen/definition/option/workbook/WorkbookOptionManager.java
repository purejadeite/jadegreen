package com.purejadeite.jadegreen.definition.option.workbook;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.option.Option;
import com.purejadeite.jadegreen.definition.option.OptionManager;
import com.purejadeite.jadegreen.definition.option.Options;

/**
 * Workbookの変換を行うコンバーターインスタンスを生成するクラスです
 * @author mitsuhiroseino
 *
 */
public class WorkbookOptionManager {

	private static OptionManager<WorkbookOption> manager;

	static {
		// マネージャーの初期化処理
		manager = new OptionManager<>();
	}

	public static void register(Class<WorkbookOption> clazz) {
		manager.register(clazz);
	}

	public static Options build(String id, List<Map<String, Object>> opts) {
		return manager.build(id, opts);
	}

	public static Option build(String id, String type, Map<String, Object> config) {
		return manager.build(id, type, config);
	}

}
