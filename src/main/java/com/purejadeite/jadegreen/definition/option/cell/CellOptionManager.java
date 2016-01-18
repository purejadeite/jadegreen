package com.purejadeite.jadegreen.definition.option.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.option.Option;
import com.purejadeite.jadegreen.definition.option.OptionManager;
import com.purejadeite.jadegreen.definition.option.Options;

public class CellOptionManager {

	private static OptionManager<CellOption> manager;

	static {
		manager = new OptionManager<>();
		// generator
		manager.register(Fixed.class);
		manager.register(Index.class);
		manager.register(Now.class);
		manager.register(Uuid.class);
		// converter
		manager.register(Split.class);
		manager.register(ToBigDecimal.class);
		manager.register(ToBoolean.class);
		manager.register(ToDate.class);
		manager.register(ToDouble.class);
		manager.register(ToFloat.class);
		manager.register(ToInteger.class);
		manager.register(ToLong.class);
		manager.register(ToShort.class);
		manager.register(ToString.class);
		manager.register(ToStringDate.class);
		manager.register(ToNull.class);
		manager.register(Lower.class);
		manager.register(Upper.class);
		manager.register(LowerCamel.class);
		manager.register(LowerHyphen.class);
		manager.register(LowerUnderscore.class);
		manager.register(UpperCamel.class);
		manager.register(UpperUnderscore.class);
		manager.register(Mapping.class);
		manager.register(Replace.class);
		manager.register(Length.class);
		manager.register(ByteLength.class);
		manager.register(Remove.class);
	}

	public static void register(Class<CellOption> clazz) {
		manager.register(clazz);
	}

	public static Options build(String id, List<Map<String, Object>> opts) {
		return manager.build(id, opts);
	}

	public static Option build(String id, String type, Map<String, Object> config) {
		return manager.build(id, type, config);
	}

}
