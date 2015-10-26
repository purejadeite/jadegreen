package com.purejadeite.jadegreen.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Options extends AbstractDefinition {

	private static final long serialVersionUID = 5483808061976653682L;

	/**
	 * オプションリスト
	 */
	private List<Definition> options;

	/**
	 * コンストラクタ
	 * @param options オプションリスト
	 */
	public Options (List<Definition> options) {
		this.options = options;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object apply(Object values) {
		Object vals = values;
		for (Definition option:options) {
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
		for (Definition option: options) {
			opts.add(option.toMap());
		}
		map.put("options", opts);
		return map;
	}

}
