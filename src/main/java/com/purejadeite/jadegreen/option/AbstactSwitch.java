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
abstract public class AbstactSwitch extends AbstractOption {

	/**
	 * { "type": "switch", "cellId": "aaaaa", "cases": [ {"value": null,
	 * "options": []}, {"value": "", "options": []}, {"value": "ArrayList<>();",
	 * "options": []}
	 *
	 *
	 * ]}
	 *
	 *
	 *
	 *
	 *
	 *
	 */

	protected static final String CFG_CELL_ID = "cellId";

	protected static final String[] CFG_CASES = { "case", "cases" };

	protected static final String CFG_OPERATOR = "oeprator";

	protected static final String[] CFG_VALUES = { "value", "values" };

	protected static final String[] CFG_OPTIONS = { "option", "options" };

	/**
	 * 必須項目名称
	 */
	private static final Object[] CONFIG = { CFG_CELL_ID, CFG_CASES };

	/**
	 * 条件
	 */
	protected List<Case> cases;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public AbstactSwitch(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);

		this.cases = new ArrayList<>();
		List<Map<String, Object>> cfgCases = getAsList(config, CFG_CASES);
		if (cfgCases != null) {
			for (Map<String, Object> cfgCase : cfgCases) {
				List<Map<String, Object>> cfgOptions = getAsList(cfgCase, CFG_OPTIONS);
				Options options = buildOptions(definition, cfgOptions);

				String cfgOperator = getString(cfgCase, CFG_OPERATOR, "==");
				List<String> cfgValues = getAsList(cfgCase, CFG_VALUES);
				Case optCase = new Case(options);
				for (String cfgValue : cfgValues) {
					optCase.addConditions(new ConditionEvaluation(cfgOperator, cfgValue));
				}
				this.cases.add(optCase);
			}
		}
	}

	abstract protected Options buildOptions(Definition<?> definition, List<Map<String, Object>> options);

	protected Options getOptions(Object value) {
		for (Case c : cases) {
			if (c.evaluate(value)) {
				return c.getOptions();
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("cases", cases);
		return map;
	}

	private static class Case {
		private List<ConditionEvaluation> conditions;
		private Options options;

		public Case(Options options) {
			super();
			this.conditions = new ArrayList<>();
			this.options = options;
		}

		public boolean evaluate(Object value) {
			for (ConditionEvaluation condition : conditions) {
				if (condition.evaluate(value)) {
					return true;
				}
			}
			return false;
		}

		public Options getOptions() {
			return options;
		}

		public void addConditions(ConditionEvaluation condition) {
			this.conditions.add(condition);
		}
	}

}