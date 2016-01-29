package com.purejadeite.jadegreen.definition.option.table;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.util.SimpleValidator;

/**
 * Key-Value型テーブルコンバーター
 *
 * @author mitsuhiroseino
 *
 */
public class KeyValue extends AbstractTableOption {

	protected static final String CFG_KEY_ID = "keyId";

	protected static final String CFG_VALUE_ID = "valueId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID, CFG_VALUE_ID };

	/**
	 * キーとなる項目の定義ID
	 */
	protected String keyId;

	/**
	 * 値となる項目の定義ID
	 */
	protected String valueId;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public KeyValue(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.keyId = getString(config, CFG_KEY_ID);
		this.valueId = getString(config, CFG_VALUE_ID);
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