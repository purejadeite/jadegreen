package com.purejadeite.jadegreen.option;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.DefinitionInterface;

public class Options extends AbstractOption {

	private static final long serialVersionUID = 5483808061976653682L;

	/**
	 * オプションリスト
	 */
	private List<OptionInterface> options;

	/**
	 * コンストラクタ
	 * @param options オプションリスト
	 */
	public Options (DefinitionInterface<?> definition, List<OptionInterface> options) {
		super(definition);
		this.options = options;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object apply(Object values, ContentInterface<?, ?> content) {
		Object vals = values;
		for (OptionInterface option:options) {
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
		for (OptionInterface option: options) {
			opts.add(option.toMap());
		}
		map.put("options", opts);
		return map;
	}

}
