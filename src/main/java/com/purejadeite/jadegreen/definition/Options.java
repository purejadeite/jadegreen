package com.purejadeite.jadegreen.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Options extends AbstractApplier {

	private List<Applier> options;

	public Options (List<Applier> options) {
		this.options = options;
	}

	@Override
	public Object apply(Object values) {
		Object vals = values;
		for (Applier option:options) {
			vals = option.apply(vals);
		}
		return vals;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> opts = new ArrayList<>();
		for (Applier option: options) {
			opts.add(option.toMap());
		}
		map.put("options", opts);
		return map;
	}

}
