package com.purejadeite.jadegreen.definition.converter.cell;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionKeys;

public class CellConverterFactory {

	public static Map<String, Class<?>> CONVERTERS;

	static {
		CONVERTERS = new HashMap<>();
		CONVERTERS.put(Split.class.getSimpleName().toLowerCase(), Split.class);
		CONVERTERS.put(ToBigDecimal.class.getSimpleName().toLowerCase(), ToBigDecimal.class);
		CONVERTERS.put(ToBoolean.class.getSimpleName().toLowerCase(), ToBoolean.class);
		CONVERTERS.put(ToDate.class.getSimpleName().toLowerCase(), ToDate.class);
		CONVERTERS.put(ToDouble.class.getSimpleName().toLowerCase(), ToDouble.class);
		CONVERTERS.put(ToFloat.class.getSimpleName().toLowerCase(), ToFloat.class);
		CONVERTERS.put(ToInteger.class.getSimpleName().toLowerCase(), ToInteger.class);
		CONVERTERS.put(ToLong.class.getSimpleName().toLowerCase(), ToLong.class);
		CONVERTERS.put(ToShort.class.getSimpleName().toLowerCase(), ToShort.class);
		CONVERTERS.put(ToString.class.getSimpleName().toLowerCase(), ToString.class);
		CONVERTERS.put(ToStringDate.class.getSimpleName().toLowerCase(), ToStringDate.class);
		CONVERTERS.put(LowerCamel.class.getSimpleName().toLowerCase(), LowerCamel.class);
		CONVERTERS.put(LowerHyphen.class.getSimpleName().toLowerCase(), LowerHyphen.class);
		CONVERTERS.put(LowerUnderscore.class.getSimpleName().toLowerCase(), LowerUnderscore.class);
		CONVERTERS.put(UpperCamel.class.getSimpleName().toLowerCase(), UpperCamel.class);
		CONVERTERS.put(UpperUnderscore.class.getSimpleName().toLowerCase(), UpperUnderscore.class);
		CONVERTERS.put(Mapping.class.getSimpleName().toLowerCase(), Mapping.class);
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
		Class<?> clazz = CONVERTERS.get(name.toLowerCase());
		if (clazz == null) {
			return null;
		}
		// コンストラクタを取得
		Constructor<?> constructor;
		try {
			constructor = clazz.getConstructor(Map.class);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}

		// インスタンスを取得
		CellConverter converter = null;
		try {
			converter = (CellConverter) constructor.newInstance(config);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return null;
		}
		return converter;
	}

}
