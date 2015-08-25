package com.purejadeite.jadegreen.definition.converter.cell;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionKeys;

public class CellConverterFactory {

	public static Map<String, Class<? extends CellConverter>> CONVERTERS;

	static {
		CONVERTERS = new HashMap<>();
		putConverter(Split.class);
		putConverter(ToBigDecimal.class);
		putConverter(ToBoolean.class);
		putConverter(ToDate.class);
		putConverter(ToDouble.class);
		putConverter(ToFloat.class);
		putConverter(ToInteger.class);
		putConverter(ToLong.class);
		putConverter(ToShort.class);
		putConverter(ToString.class);
		putConverter(ToStringDate.class);
		putConverter(Lower.class);
		putConverter(Upper.class);
		putConverter(LowerCamel.class);
		putConverter(LowerHyphen.class);
		putConverter(LowerUnderscore.class);
		putConverter(UpperCamel.class);
		putConverter(UpperUnderscore.class);
		putConverter(Mapping.class);
		putConverter(Replace.class);
	}

	private static void putConverter(Class<? extends CellConverter> clazz) {
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
