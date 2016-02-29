package com.purejadeite.jadegreen.option.table;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.option.OptionInterface;
import com.purejadeite.jadegreen.option.OptionManager;
import com.purejadeite.jadegreen.option.Options;

/**
 * テーブルの変換を行うコンバーターインスタンスを生成するクラスです
 * @author mitsuhiroseino
 *
 */
public class TableOptionManager {

	private static OptionManager<TableOptionInterface> manager;

	static {
		// マネージャーの初期化処理
		manager = new OptionManager<>();
		manager.register(Chain.class);
		manager.register(Follow.class);
		manager.register(Group.class);
		manager.register(If.class);
		manager.register(KeyValue.class);
		manager.register(Remove.class);
		manager.register(ReplaceId.class);
		manager.register(Sort.class);
		manager.register(Unique.class);
	}

	public static void register(Class<? extends TableOptionInterface> clazz) {
		manager.register(clazz);
	}

	public static Options build(DefinitionInterface<?> definition, List<Map<String, Object>> opts) {
		return manager.build(definition, opts);
	}

	public static OptionInterface build(DefinitionInterface<?> definition, String type, Map<String, Object> config) {
		return manager.build(definition, type, config);
	}

}
