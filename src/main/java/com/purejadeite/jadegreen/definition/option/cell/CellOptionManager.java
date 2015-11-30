package com.purejadeite.jadegreen.definition.option.cell;

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

public class CellOptionManager {

	private static Map<String, Class<? extends CellOption>> OPTIONS;

	static {
		OPTIONS = new HashMap<>();
		// generator
		regiter(Fixed.class);
		regiter(Index.class);
		regiter(Now.class);
		regiter(Uuid.class);
		// converter
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
		regiter(ToNull.class);
		regiter(Lower.class);
		regiter(Upper.class);
		regiter(LowerCamel.class);
		regiter(LowerHyphen.class);
		regiter(LowerUnderscore.class);
		regiter(UpperCamel.class);
		regiter(UpperUnderscore.class);
		regiter(Mapping.class);
		regiter(Replace.class);
		regiter(Length.class);
		regiter(ByteLength.class);
		regiter(Remove.class);
	}

	public static void regiter(Class<? extends CellOption> clazz) {
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

	public static CellOption build(String type, Map<String, Object> config) {
		// クラスを取得
		Class<? extends CellOption> clazz = OPTIONS.get(type.toLowerCase());
		if (clazz == null) {
			return null;
		}
		// コンストラクタを取得
		Constructor<? extends CellOption> constructor;
		try {
			constructor = clazz.getConstructor(Map.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new MappingException("type=" + type + ":optionsのclassからデフォルトコンストラクターが取得できません");
		}

		// インスタンスを取得
		CellOption option = null;
		try {
			option = constructor.newInstance(config);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new MappingException("type=" + type + ":optionsのclassからインスタンスが作成できません");
		}
		return option;
	}

}
