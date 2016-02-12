package com.purejadeite.jadegreen.definition.option.cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.option.AbstactIf;
import com.purejadeite.jadegreen.definition.option.Options;
import com.purejadeite.util.SimpleValidator;

/**
 * オプション実行条件オプション
 *
 * @author mitsuhiroseino
 *
 */
public class If extends AbstactIf implements CellOption, Serializable {

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {};

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public If(Definition<?> definition, Map<String, Object> config) {
		super(definition, config);
		SimpleValidator.containsKey(config, CONFIG);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object cellValue, Content<?, ?> content) {
		if (cellValue == SpecificValue.UNDEFINED) {
			return cellValue;
		} else if (cellValue instanceof Iterable) {
			Iterable<Object> values = (Iterable<Object>) cellValue;
			List<Object> vals = new ArrayList<>();
			for (Object v : values) {
				vals.add(this.apply(v, content));
			}
			return vals;
		} else {
			return applyImpl(cellValue, content);
		}
	}

	protected Object applyImpl(Object cellValue, Content<?, ?> content) {
		if (evaluate(cellValue)) {
			return thenOptions.apply(cellValue, content);
		} else {
			if (thenOptions == null) {
				return cellValue;
			} else {
				return elseOptions.apply(cellValue, content);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}

	@Override
	protected Options buildOptions(Definition<?> definition, List<Map<String, Object>> options) {
		return CellOptionManager.build(definition, options);
	}

}