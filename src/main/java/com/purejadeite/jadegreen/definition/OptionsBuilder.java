package com.purejadeite.jadegreen.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.jadegreen.RoughlyMapUtils;
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

	public static Applier build(String type, Map<String, Object> config) {
		if (type == null) {
			throw new DefinitionException("option=" + config + ":optionにtypeが設定されていません");
		}
		Applier option = ValueGeneratorManager.build(type, config);
		if (option == null) {
			option = CellOptionManager.build(type, config);
		}
		if (option == null) {
			option = RangeOptionManager.build(type, config);
		}
		return option;
	}

}
