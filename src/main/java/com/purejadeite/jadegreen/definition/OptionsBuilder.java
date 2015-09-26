package com.purejadeite.jadegreen.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.option.cell.CellOptionManager;
import com.purejadeite.jadegreen.definition.option.generator.ValueGeneratorManager;
import com.purejadeite.jadegreen.definition.option.range.RangeOptionManager;

public class OptionsBuilder {

	public static Options build(List<Map<String, Object>> opts) {
		if (opts == null || opts.size() == 0) {
			return null;
		}
		List<Applier> options = new ArrayList<>();
		Applier option = null;
		for (Map<String, Object> opt : opts) {
			option = build(opt);
			if (option == null) {
				throw new IllegalStateException("type=" + opt.get(DefinitionKeys.TYPE) + "は存在しません");
			} else {
				options.add(option);
			}
		}
		return new Options(options);
	}

	public static Applier build(Map<String, Object> opt) {
		Applier option = ValueGeneratorManager.build(opt);
		if (option == null) {
			option = CellOptionManager.build(opt);
		}
		if (option == null) {
			option = RangeOptionManager.build(opt);
		}
		return option;
	}

}
