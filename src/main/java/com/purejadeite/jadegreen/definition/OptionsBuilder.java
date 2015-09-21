package com.purejadeite.jadegreen.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.option.cell.CellOptionManager;
import com.purejadeite.jadegreen.definition.option.range.RangeOptionManager;

public class OptionsBuilder {

	public static Options build(List<Map<String, Object>> opts) {
		if (opts == null || opts.size() == 0) {
			return null;
		}
		List<Option> options = new ArrayList<>();
		Option option = null;
		for (Map<String, Object> opt : opts) {
			option = build(opt);
			if (option != null) {
				options.add(option);
			}
		}
		return new Options(options);
	}

	public static Option build(Map<String, Object> opt) {
		Option option;
		option = CellOptionManager.build(opt);
		if (option == null) {
			option = RangeOptionManager.build(opt);
		}
		return option;
	}

}
