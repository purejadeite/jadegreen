package com.purejadeite.jadegreen.definition.converter.range;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionKeys;

/**
 * テーブルの変換を行うコンバーターインスタンスを生成するクラスです
 * @author mitsuhiroseino
 *
 */
public class RangeConverterFactory {

	/**
	 * コンバーター
	 */
	public static Map<String, Class<? extends RangeConverter>> CONVERTERS;

	static {
		// コンバーターの初期化処理
		CONVERTERS = new HashMap<>();
		putConverter(Group.class);
		putConverter(Sort.class);
		putConverter(Unique.class);
		putConverter(Eliminate.class);
	}

	private static void putConverter(Class<? extends RangeConverter> clazz) {
		CONVERTERS.put(clazz.getSimpleName().toLowerCase(), clazz);
	}

	/**
	 * Range読み込み定義をコンバーターでラップします
	 * @param range Range読み込み定義
	 * @param configs コンバーターのコンフィグ
	 * @return
	 */
	public static RangeConverter build(List<Map<String, String>> configs) {
		if (configs == null || configs.size() == 0) {
			return null;
		}
		RangeConverter converter = null;
		for (Map<String, String> config : configs) {
			RangeConverter nextConverter = getConverter(config);
			if (converter == null) {
				converter = nextConverter;
			} else {
				converter.chain(nextConverter);
			}
		}
		return converter;
	}

	/**
	 * コンバーターのインスタンスを生成します
	 * @param range Range読み込み定義
	 * @param config コンバーターのコンフィグ
	 * @return コンバーター
	 */
	private static RangeConverter getConverter(Map<String, String> config) {
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
