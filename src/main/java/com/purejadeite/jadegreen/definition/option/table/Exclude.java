package com.purejadeite.jadegreen.definition.option.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.util.EvaluationUtils;
import com.purejadeite.util.SimpleValidator;
import com.purejadeite.util.collection.RoughlyMapUtils;

/**
 * 削除コンバーター
 *
 * @author mitsuhiroseino
 *
 */
public class Exclude extends AbstractTableOption {

	private static final long serialVersionUID = -7665794968023216952L;

	private static final String CFG_KEY_ID = "keyId";

	private static final String CFG_OPERATOR = "operator";

	private static final String CFG_VALUE = "value";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID, CFG_VALUE };

	/**
	 * 削除キー
	 */
	protected String keyId;

	/**
	 * 比較演算子
	 */
	protected String operator;

	/**
	 * 削除条件値
	 */
	protected String value;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Exclude(Map<String, Object> config) {
		super();
		SimpleValidator.containsKey(config, CONFIG);
		this.keyId = RoughlyMapUtils.getString(config, CFG_KEY_ID);
		this.operator = RoughlyMapUtils.getString(config, CFG_OPERATOR, "==");
		this.value = RoughlyMapUtils.getString(config, CFG_VALUE, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		List<Map<String, Object>> rows = new ArrayList<>();
		for (Map<String, Object> row : values) {
			Object value = row.get(keyId);
			if (EvaluationUtils.evaluate(value, operator, this.value)) {
				rows.add(row);
			}
		}
		return rows;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("keyId", keyId);
		map.put("operator", operator);
		map.put("value", value);
		return map;
	}

}