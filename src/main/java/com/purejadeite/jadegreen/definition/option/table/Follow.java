package com.purejadeite.jadegreen.definition.option.table;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
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
public class Follow extends AbstractTableOption {

	private static final String CFG_KEY_ID = "keyId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID };

	/**
	 * キーとなる項目の定義ID
	 */
	private String keyId;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Follow(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.keyId = getString(config, CFG_KEY_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		// keyIdの項目の値が空だったらひとつ前の値を引き継ぐ
		List<Map<String, Object>> newValues = new ArrayList<>();
		Object prev = null;
		for (Map<String, Object> line : values) {
			Map<String, Object> newLine = new LinkedHashMap<>(line);
			Object key = newLine.get(keyId);
			if (key == null || key.toString().equals("")) {
				newLine.put(keyId, prev);
			}
			newValues.add(newLine);
			prev = key;
		}
		return newValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("keyId", keyId);
		return map;
	}
}