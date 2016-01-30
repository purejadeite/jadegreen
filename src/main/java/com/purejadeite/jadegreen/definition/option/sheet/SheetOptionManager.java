package com.purejadeite.jadegreen.definition.option.sheet;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.option.Option;
import com.purejadeite.jadegreen.definition.option.OptionManager;
import com.purejadeite.jadegreen.definition.option.Options;

/**
 * Worksheetの変換を行うコンバーターインスタンスを生成するクラスです
 * @author mitsuhiroseino
 *
 */
public class SheetOptionManager {

	private static OptionManager<SheetOption> manager;

	static {
		// マネージャーの初期化処理
		manager = new OptionManager<>();
	}

	public static void register(Class<? extends SheetOption> clazz) {
		manager.register(clazz);
	}

	public static Options build(Definition<?> definition, List<Map<String, Object>> opts) {
		return manager.build(definition, opts);
	}

	public static Option build(Definition<?> definition, String type, Map<String, Object> config) {
		return manager.build(definition, type, config);
	}

}