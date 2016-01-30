package com.purejadeite.jadegreen.definition.option.table;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.option.Option;
import com.purejadeite.jadegreen.definition.option.OptionManager;
import com.purejadeite.jadegreen.definition.option.Options;

/**
 * テーブルの変換を行うコンバーターインスタンスを生成するクラスです
 * @author mitsuhiroseino
 *
 */
public class TableOptionManager {

	private static OptionManager<TableOption> manager;

	static {
		// マネージャーの初期化処理
		manager = new OptionManager<>();
		manager.register(Group.class);
		manager.register(Sort.class);
		manager.register(Unique.class);
		manager.register(Exclude.class);
		manager.register(ReplaceId.class);
		manager.register(KeyValue.class);
		manager.register(Chain.class);
	}

	public static void register(Class<? extends TableOption> clazz) {
		manager.register(clazz);
	}

	public static Options build(Definition<?> definition, List<Map<String, Object>> opts) {
		return manager.build(definition, opts);
	}

	public static Option build(Definition<?> definition, String type, Map<String, Object> config) {
		return manager.build(definition, type, config);
	}

}
