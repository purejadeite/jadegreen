package com.purejadeite.jadegreen.definition.option.cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.option.AbstactCondition;
import com.purejadeite.jadegreen.definition.option.Options;
import com.purejadeite.util.ConditionEvaluation;
import com.purejadeite.util.SimpleValidator;

/**
 * オプション実行条件オプション
 *
 * @author mitsuhiroseino
 *
 */
public class Condition extends AbstactCondition implements CellOption, Serializable {

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
	public Condition(Definition<?> definition, Map<String, Object> config) {
		super(definition, config);
		SimpleValidator.containsKey(config, CONFIG);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object value) {
		if (value == SpecificValue.UNDEFINED) {
			return value;
		} else if (value instanceof Iterable) {
			Iterable<Object> values = (Iterable<Object>) value;
			List<Object> vals = new ArrayList<>();
			for (Object v : values) {
				vals.add(this.apply(v));
			}
			return vals;
		} else {
			return applyImpl(value);
		}
	}

	protected Object applyImpl(Object values) {
		for (ConditionEvaluation condition : conditions) {
			if (!condition.evaluate(values)) {
				return values;
			}
		}
		return options.apply(values);
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