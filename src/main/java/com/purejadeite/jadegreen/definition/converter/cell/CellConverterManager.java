package com.purejadeite.jadegreen.definition.converter.cell;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionKeys;

public class CellConverterManager {

	private static Map<String, Class<? extends CellConverter>> CONVERTERS;

	static {
		CONVERTERS = new HashMap<>();
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

	public static void regiter(Class<? extends CellConverter> clazz) {
		CONVERTERS.put(clazz.getSimpleName().toLowerCase(), clazz);
	}


	public static CellConverter build(List<Map<String, String>> configs) {
		if (configs == null || configs.size() == 0) {
			return null;
		}
		CellConverter converter = null;
		for (Map<String, String> config : configs) {
			CellConverter nextConverter = getConverter(config);
			if (converter == null) {
				converter = nextConverter;
			} else {
				converter.chain(nextConverter);
			}
		}
		return converter;
	}

	public static CellConverter getConverter(Map<String, String> config) {
		String name = config.get(DefinitionKeys.CLASS);
		// クラスを取得
		Class<? extends CellConverter> clazz = CONVERTERS.get(name.toLowerCase());
		if (clazz == null) {
			return null;
		}
		// コンストラクタを取得
		Constructor<? extends CellConverter> constructor;
		try {
			constructor = clazz.getConstructor(Map.class);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}

		// インスタンスを取得
		CellConverter converter = null;
		try {
			converter = constructor.newInstance(config);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return null;
		}
		return converter;
	}

}
