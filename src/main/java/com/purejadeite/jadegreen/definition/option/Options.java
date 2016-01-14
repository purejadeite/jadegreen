package com.purejadeite.jadegreen.definition.option;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Options extends AbstractOption {

	private static final long serialVersionUID = 5483808061976653682L;

	/**
	 * オプションリスト
	 */
	private List<Option> options;

	/**
	 * コンストラクタ
	 * @param options オプションリスト
	 */
	public Options (List<Option> options) {
		this.options = options;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object apply(Object values) {
		Object vals = values;
		for (Option option:options) {
			vals = option.apply(vals);
		}
		return vals;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		List<Map<String, Object>> opts = new ArrayList<>();
		for (Option option: options) {
			opts.add(option.toMap());
		}
		map.put("options", opts);
		return map;
	}

}
