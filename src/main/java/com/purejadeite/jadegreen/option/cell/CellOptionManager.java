package com.purejadeite.jadegreen.option.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.option.OptionInterface;
import com.purejadeite.jadegreen.option.OptionManager;
import com.purejadeite.jadegreen.option.Options;

public class CellOptionManager {

	private static OptionManager<CellOptionInterface> manager;

	static {
		manager = new OptionManager<>();
		manager.register(AddValue.class);
		manager.register(Append.class);
		manager.register(ByteLength.class);
		manager.register(Convert.class);
		manager.register(First.class);
		manager.register(If.class);
		manager.register(Last.class);
		manager.register(Length.class);
		manager.register(Lower.class);
		manager.register(LowerCamel.class);
		manager.register(LowerHyphen.class);
		manager.register(LowerUnderscore.class);
		manager.register(Mapping.class);
		manager.register(Read.class);
		manager.register(Remove.class);
		manager.register(Replace.class);
		manager.register(Switch.class);
		manager.register(ToBigDecimal.class);
		manager.register(ToBoolean.class);
		manager.register(ToDate.class);
		manager.register(ToDouble.class);
		manager.register(ToFloat.class);
		manager.register(ToInteger.class);
		manager.register(ToLong.class);
		manager.register(ToNull.class);
		manager.register(ToShort.class);
		manager.register(ToString.class);
		manager.register(ToStringDate.class);
		manager.register(Unique.class);
		manager.register(Upper.class);
		manager.register(UpperCamel.class);
		manager.register(UpperUnderscore.class);
	}

	public static void register(Class<? extends CellOptionInterface> clazz) {
		manager.register(clazz);
	}

	public static Options build(DefinitionInterface<?> definition, List<Map<String, Object>> opts) {
		return manager.build(definition, opts);
	}

	public static OptionInterface build(DefinitionInterface<?> definition, String type, Map<String, Object> config) {
		return manager.build(definition, type, config);
	}

}
