package com.purejadeite.jadegreen.option;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.util.ConditionEvaluation;
import com.purejadeite.util.SimpleValidator;

/**
 * オプション実行条件オプション
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstactIf extends AbstractOption {

	protected static final String CFG_OPERATOR = "operator";

	protected static final String CFG_VALUE = "value";

	protected static final String CFG_CONDITIONS = "conditions";

	protected static final String CFG_THEN = "then";

	protected static final String CFG_ELSE = "else";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_VALUE };

	/**
	 * 条件
	 */
	protected List<ConditionEvaluation> conditions;

	protected Options thenOptions;

	protected Options elseOptions;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public AbstactIf(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);

		this.conditions = new ArrayList<>();
		List<Map<String, String>> conditions = getList(config, CFG_CONDITIONS);
		if (conditions != null) {
			for (Map<String, String> condition : conditions) {
				this.conditions.add(new ConditionEvaluation(getString(condition, CFG_OPERATOR, "=="),
						getString(condition, CFG_VALUE, null)));
			}
		} else {
			this.conditions.add(
					new ConditionEvaluation(getString(config, CFG_OPERATOR, "=="), getString(config, CFG_VALUE, null)));
		}
		List<Map<String, Object>> cfgThen = getAsList(config, CFG_THEN);
		thenOptions = buildOptions(definition, cfgThen);
		List<Map<String, Object>> cfgElse = getAsList(config, CFG_ELSE);
		if (cfgElse != null) {
			elseOptions = buildOptions(definition, cfgElse);
		}
	}

	abstract protected Options buildOptions(Definition<?> definition, List<Map<String, Object>> options);

	protected boolean evaluate(Object value) {
		for (ConditionEvaluation condition : conditions) {
			if (!condition.evaluate(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("conditons", conditions);
		map.put("thenOptions", thenOptions);
		map.put("elseOptions", elseOptions);
		return map;
	}

}