package com.purejadeite.jadegreen.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.option.cell.CellOptionManager;
import com.purejadeite.jadegreen.definition.option.range.RangeOptionManager;

public class OptionsBuilder {

	public static Options build(List<Map<String, String>> configs) {
		if (configs == null || configs.size() == 0) {
			return null;
		}
		List<Option> options = new ArrayList<>();
		Option option = null;
		for (Map<String, String> config : configs) {
			option = CellOptionManager.build(config);
			if (option != null) {
				options.add(option);
				continue;
			}
			option = RangeOptionManager.build(config);
			if (option != null) {
				options.add(option);
				continue;
			}
		}
		return new Options(options);
	}

}
