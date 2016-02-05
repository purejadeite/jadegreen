package com.purejadeite.jadegreen.definition.option;

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
abstract public class AbstactCondition extends AbstractOption {

	protected static final String CFG_OPERATOR = "operator";

	protected static final String CFG_VALUE = "value";

	protected static final String CFG_CONDITIONS = "conditions";

	/**
	 * オプション
	 */
	public static final String[] CFG_OPTIONS = { "options", "option" };

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_VALUE };

	/**
	 * 条件
	 */
	protected List<ConditionEvaluation> conditions;

	protected Options options;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public AbstactCondition(Definition<?> definition, Map<String, Object> config) {
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
		List<Map<String, Object>> opts = getAsList(config, CFG_OPTIONS);
		options = buildOptions(definition, opts);
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
		map.put("options", options);
		return map;
	}

}