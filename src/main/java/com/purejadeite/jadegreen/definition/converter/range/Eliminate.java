package com.purejadeite.jadegreen.definition.converter.range;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

/**
 * 削除コンバーター
 * @author mitsuhiroseino
 *
 */
public class Eliminate extends AbstractRangeConverter {

	private static final long serialVersionUID = -9052868413585611637L;

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
		this.keyId = MapUtils.getString(config, "keyId");
		this.conditionValue = MapUtils.getString(config, "conditionValue");
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