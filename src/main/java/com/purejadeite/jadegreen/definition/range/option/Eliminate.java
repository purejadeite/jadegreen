package com.purejadeite.jadegreen.definition.range.option;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;

/**
 * 削除コンバーター
 * @author mitsuhiroseino
 *
 */
public class Eliminate extends AbstractRangeConverter {

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {"keyId", "conditionValue"};

	/**
	 * 削除キー
	 */
	protected String keyId;

	/**
	 * 削除条件値
	 */
	protected String conditionValue;

	/**
	 * コンストラクタ
	 * @param range 変換元の値を持つ定義
	 * @param config コンバーターのコンフィグ
	 */
	public Eliminate(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.keyId = RoughlyMapUtils.getString(config, "keyId");
		this.conditionValue = RoughlyMapUtils.getString(config, "conditionValue");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		List<Map<String, Object>> vals = new ArrayList<>();
		for (Map<String, Object> value : values) {
			Object v = value.get(keyId);
			if (v == conditionValue || (conditionValue != null && conditionValue.equals(v))) {
				continue;
			}
			vals.add(value);
		}
		return vals;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("keyId", keyId);
		map.put("conditionValue", conditionValue);
		return map;
	}

}