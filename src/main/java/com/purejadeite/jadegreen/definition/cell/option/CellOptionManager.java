package com.purejadeite.jadegreen.definition.cell.option;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionKeys;

public class CellOptionManager {

	private static Map<String, Class<? extends CellOption>> OPTIONS;

	static {
		OPTIONS = new HashMap<>();
		regiter(Split.class);
		regiter(ToBigDecimal.class);
		regiter(ToBoolean.class);
		regiter(ToDate.class);
		regiter(ToDouble.class);
		regiter(ToFloat.class);
		regiter(ToInteger.class);
		regiter(ToLong.class);
		regiter(ToShort.class);
		regiter(ToString.class);
		regiter(ToStringDate.class);
		regiter(Lower.class);
		regiter(Upper.class);
		regiter(LowerCamel.class);
		regiter(LowerHyphen.class);
		regiter(LowerUnderscore.class);
		regiter(UpperCamel.class);
		regiter(UpperUnderscore.class);
		regiter(Mapping.class);
		regiter(Replace.class);
	}

	public static void regiter(Class<? extends CellOption> clazz) {
		OPTIONS.put(clazz.getSimpleName().toLowerCase(), clazz);
	}

	public static CellOption build(Map<String, String> config) {
		String name = config.get(DefinitionKeys.CLASS);
		// クラスを取得
		Class<? extends CellOption> clazz = OPTIONS.get(name.toLowerCase());
		if (clazz == null) {
			return null;
		}
		// コンストラクタを取得
		Constructor<? extends CellOption> constructor;
		try {
			constructor = clazz.getConstructor(Map.class);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}

		// インスタンスを取得
		CellOption converter = null;
		try {
			converter = constructor.newInstance(config);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return null;
		}
		return converter;
	}

}
