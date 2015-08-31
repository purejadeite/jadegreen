package com.purejadeite.jadegreen.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.converter.cell.CellConverterManager;
import com.purejadeite.jadegreen.definition.converter.range.RangeConverterManager;
import com.purejadeite.jadegreen.definition.generator.ValueGeneratorManager;

public class OptionsBuilder {

	public static Options build(List<Map<String, String>> configs) {
		if (configs == null || configs.size() == 0) {
			return null;
		}
		List<Option> options = new ArrayList<>();
		Option option = null;
		for (Map<String, String> config : configs) {
			option = CellConverterManager.build(config);
			if (option != null) {
				options.add(option);
				continue;
			}
			option = RangeConverterManager.build(config);
			if (option != null) {
				options.add(option);
				continue;
			}
			option = ValueGeneratorManager.build(config);
			if (option != null) {
				options.add(option);
				continue;
			}
		}
		return new Options(options);
	}

}
