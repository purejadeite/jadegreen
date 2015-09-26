package com.purejadeite.jadegreen.definition.option.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;
import com.purejadeite.jadegreen.definition.DefinitionKeys;

public class ValueGeneratorManager {

	private static Map<String, Class<? extends ValueGenerator>> GENERATORS;

	static {
		GENERATORS = new HashMap<>();
		regiter(Fixed.class);
		regiter(Uuid.class);
		regiter(Now.class);
		regiter(Index.class);
	}

	public static void regiter(Class<? extends ValueGenerator> clazz) {
		GENERATORS.put(clazz.getSimpleName().toLowerCase(), clazz);
	}

	public static ValueGenerator build(Map<String, Object> config) {
		if (config == null) {
			return null;
		}
		String type = RoughlyMapUtils.getString(config, DefinitionKeys.TYPE);
		if (type == null) {
			return null;
		}
		// クラスを取得
		Class<? extends ValueGenerator> clazz = GENERATORS.get(type.toLowerCase());
		if (clazz == null) {
			return null;
		}
		// コンストラクタを取得
		Constructor<? extends ValueGenerator> constructor;
		try {
			constructor = clazz.getConstructor(Map.class);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}

		// インスタンスを取得
		ValueGenerator generator = null;
		try {
			generator = constructor.newInstance(config);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return null;
		}
		return generator;
	}

}
