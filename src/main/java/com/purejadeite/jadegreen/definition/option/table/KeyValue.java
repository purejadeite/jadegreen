package com.purejadeite.jadegreen.definition.option.table;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.util.SimpleValidator;
import com.purejadeite.util.collection.RoughlyMapUtils;

/**
 * Key-Value型テーブルコンバーター
 *
 * @author mitsuhiroseino
 *
 */
public class KeyValue extends AbstractTableConverter {

	private static final String CFG_KEY_ID = "keyId";

	private static final String CFG_VALUE_ID = "valueId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID, CFG_VALUE_ID };

	/**
	 * キーとなる項目の定義ID
	 */
	private String keyId;

	/**
	 * 値となる項目の定義ID
	 */
	private String valueId;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public KeyValue(Map<String, Object> config) {
		super();
		SimpleValidator.containsKey(config, CONFIG);
		this.keyId = RoughlyMapUtils.getString(config, CFG_KEY_ID);
		this.valueId = RoughlyMapUtils.getString(config, CFG_VALUE_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		// グループ化された配列を保持するMapに変換
		Map<String, Object> keyValues = new LinkedHashMap<>();
		for (Map<String, Object> line : values) {
			Object key = line.get(keyId);
			if (key != null) {
				keyValues.put(key.toString(), line.get(valueId));
			}
		}
		return keyValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("keyId", keyId);
		map.put("valueId", valueId);
		return map;
	}
}