package com.purejadeite.jadegreen.definition.converter.range;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
		this((String) config.get("keyId"), (String) config.get("conditionValue"));
	}

	/**
	 * コンストラクタ
	 * @param range 変換元の値を持つ定義
	 * @param keyId ソートキー
	 * @param desc 昇順/降順
	 */
	public Eliminate(String keyId, String conditionValue) {
		super();
		this.keyId = keyId;
		this.conditionValue = conditionValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object convertImpl(List<Map<String, Object>> values) {
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
	public List<Map<String, Object>> toList() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", this.getClass().getSimpleName());
		map.put("keyId", keyId);
		map.put("conditionValue", conditionValue);
		List<Map<String, Object>> list = super.toList();
		list.add(map);
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", keyId=" + keyId + ", conditionValue="
				+ conditionValue + "]";
	}

}