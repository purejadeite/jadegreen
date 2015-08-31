package com.purejadeite.jadegreen.definition;

import java.util.List;
import java.util.Map;

public class Options implements Option {

	private List<Option> options;

	Options (List<Option> options) {
		this.options = options;
	}

	@Override
	public Object apply(Object values) {
		Object vals = values;
		for (Option option:options) {
			vals = option.apply(vals);
		}
		return vals;
	}

	@Override
	public Map<String, Object> toMap() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String toJson() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
