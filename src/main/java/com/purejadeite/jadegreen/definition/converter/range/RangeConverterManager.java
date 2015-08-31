package com.purejadeite.jadegreen.definition.converter.range;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionKeys;

/**
 * テーブルの変換を行うコンバーターインスタンスを生成するクラスです
 * @author mitsuhiroseino
 *
 */
public class RangeConverterManager {

	/**
	 * コンバーター
	 */
	private static Map<String, Class<? extends RangeConverter>> CONVERTERS;

	static {
		// コンバーターの初期化処理
		CONVERTERS = new HashMap<>();
		register(Group.class);
		register(Sort.class);
		register(Unique.class);
		register(Eliminate.class);
	}

	public static void register(Class<? extends RangeConverter> clazz) {
		CONVERTERS.put(clazz.getSimpleName().toLowerCase(), clazz);
	}

	/**
	 * コンバーターのインスタンスを生成します
	 * @param range Range読み込み定義
	 * @param config コンバーターのコンフィグ
	 * @return コンバーター
	 */
	public static RangeConverter build(Map<String, String> config) {
		String name = config.get(DefinitionKeys.CLASS);
		// クラスを取得
		Class<? extends RangeConverter> clazz = CONVERTERS.get(name.toLowerCase());
		if (clazz == null) {
			return null;
		}
		// コンストラクタを取得
		Constructor<? extends RangeConverter> constructor;
		try {
			constructor = clazz.getConstructor(Map.class);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}

		// インスタンスを取得
		RangeConverter converter = null;
		try {
			converter = constructor.newInstance(config);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return null;
		}
		return converter;
	}

}
