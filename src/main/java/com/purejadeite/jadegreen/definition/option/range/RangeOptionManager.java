package com.purejadeite.jadegreen.definition.option.range;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.purejadeite.jadegreen.MappingException;

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
		register(Eliminate.class);
	}

	public static void register(Class<? extends RangeOption> clazz) {
		OPTIONS.put(clazz.getSimpleName().toLowerCase(), clazz);
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
