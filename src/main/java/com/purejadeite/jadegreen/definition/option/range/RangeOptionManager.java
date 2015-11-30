package com.purejadeite.jadegreen.definition.option.range;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.jadegreen.MappingException;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.DefinitionKeys;
import com.purejadeite.jadegreen.definition.Options;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * テーブルの変換を行うコンバーターインスタンスを生成するクラスです
 * @author mitsuhiroseino
 *
 */
public class RangeOptionManager {

	/**
	 * コンバーター
	 */
	private static Map<String, Class<? extends RangeOption>> OPTIONS;

	static {
		// コンバーターの初期化処理
		OPTIONS = new HashMap<>();
		register(Group.class);
		register(Sort.class);
		register(Unique.class);
		register(Exclude.class);
		register(ReplaceId.class);
	}

	public static void register(Class<? extends RangeOption> clazz) {
		OPTIONS.put(clazz.getSimpleName().toLowerCase(), clazz);
	}

	public static Options build(List<Map<String, Object>> opts) {
		if (opts == null || opts.size() == 0) {
			return null;
		}
		List<Definition> options = new ArrayList<>();
		Definition option = null;
		for (Map<String, Object> opt : opts) {
			String type = RoughlyMapUtils.getString(opt, DefinitionKeys.TYPE);
			option = build(type, opt);
			if (option == null) {
				throw new DefinitionException("type=" + type + ":optionsのclassが取得できません");
			} else {
				options.add(option);
			}
		}
		return new Options(options);
	}

	/**
	 * コンバーターのインスタンスを生成します
	 * @param range Range読み込み定義
	 * @param config コンバーターのコンフィグ
	 * @return コンバーター
	 */
	public static RangeOption build(String type, Map<String, Object> config) {
		// クラスを取得
		Class<? extends RangeOption> clazz = OPTIONS.get(type.toLowerCase());
		if (clazz == null) {
			return null;
		}
		// コンストラクタを取得
		Constructor<? extends RangeOption> constructor;
		try {
			constructor = clazz.getConstructor(Map.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new MappingException("type=" + type + ":optionsのclassからデフォルトコンストラクターが取得できません");
		}

		// インスタンスを取得
		RangeOption option = null;
		try {
			option = constructor.newInstance(config);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new MappingException("type=" + type + ":optionsのclassからインスタンスが作成できません");
		}
		return option;
	}

}
