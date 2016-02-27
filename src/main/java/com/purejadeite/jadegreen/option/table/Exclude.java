package com.purejadeite.jadegreen.option.table;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.util.EvaluationUtils;
import com.purejadeite.util.SimpleValidator;

/**
 * 削除コンバーター
 *
 * @author mitsuhiroseino
 *
 */
public class Exclude extends AbstractTableOption {

	private static final long serialVersionUID = -7665794968023216952L;

	protected static final String CFG_KEY_ID = "keyId";

	protected static final String CFG_OPERATOR = "operator";

	protected static final String CFG_VALUE = "value";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID };

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
	public Exclude(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);

		this.keyId = getString(config, CFG_KEY_ID);
		this.operator = getString(config, CFG_OPERATOR, "==");
		this.value = getString(config, CFG_VALUE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values, ContentInterface<?, ?> content) {
		List<Map<String, Object>> rows = new ArrayList<>();
		for (Map<String, Object> row : values) {
			Object value = row.get(keyId);
			if (!EvaluationUtils.evaluate(value, operator, this.value)) {
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