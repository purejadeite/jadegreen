package com.purejadeite.jadegreen.definition.option;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.Definition;

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
	public Options (Definition<?> definition, List<Option> options) {
		super(definition);
		this.options = options;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object apply(Object values, Content<?, ?> content) {
		Object vals = values;
		for (Option option:options) {
			vals = option.apply(vals, content);
			if (vals == SpecificValue.UNDEFINED) {
				break;
			}
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
