package com.purejadeite.jadegreen.option.sheet;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.option.OptionInterface;
import com.purejadeite.jadegreen.option.OptionManager;
import com.purejadeite.jadegreen.option.Options;

/**
 * Worksheetの変換を行うコンバーターインスタンスを生成するクラスです
 * @author mitsuhiroseino
 *
 */
public class SheetOptionManager {

	private static OptionManager<SheetOptionInterface> manager;

	static {
		// マネージャーの初期化処理
		manager = new OptionManager<>();
		manager.register(If.class);
		manager.register(From.class);
	}

	public static void register(Class<? extends SheetOptionInterface> clazz) {
		manager.register(clazz);
	}

	public static Options build(DefinitionInterface<?> definition, List<Map<String, Object>> opts) {
		return manager.build(definition, opts);
	}

	public static OptionInterface build(DefinitionInterface<?> definition, String type, Map<String, Object> config) {
		return manager.build(definition, type, config);
	}

}
