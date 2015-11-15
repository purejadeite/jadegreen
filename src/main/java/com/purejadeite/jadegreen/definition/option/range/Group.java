package com.purejadeite.jadegreen.definition.option.range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.purejadeite.util.RoughlyMapUtils;

/**
 * グループ化用テーブルコンバーター
 *
 * @author mitsuhiroseino
 *
 */
public class Group extends AbstractRangeConverter {

	private static final long serialVersionUID = -7712482157745974514L;

	private static final String CFG_KEY_ID = "keyId";

	private static final String CFG_TO_ID = "toId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID, CFG_TO_ID };

	/**
	 * グループ化キーとなる項目の定義ID
	 */
	private String keyId;

	/**
	 * グループ化した値を保存する項目の定義ID
	 */
	private String toId;

	/**
	 * コンストラクタ
	 *
	 * @param range
	 *            変換元の値を持つ定義
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Group(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.keyId = RoughlyMapUtils.getString(config, CFG_KEY_ID);
		this.toId = RoughlyMapUtils.getString(config, CFG_TO_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		// グループ化された配列を保持するMapに変換
		Map<Object, List<Map<String, Object>>> groupedMap = new LinkedHashMap<>();
		for (Map<String, Object> line : values) {
			Object groupKey = line.get(keyId);
			List<Map<String, Object>> groupedLines = groupedMap.get(groupKey);
			if (groupedLines == null) {
				groupedLines = new ArrayList<>();
				groupedMap.put(groupKey, groupedLines);
			}
			groupedLines.add(line);
		}

		// 欲しい形式に変換
		List<Map<String, Object>> groupedValues = new ArrayList<>();
		for (Entry<Object, List<Map<String, Object>>> entry : groupedMap.entrySet()) {
			Map<String, Object> newLine = new HashMap<>();
			newLine.put(keyId, entry.getKey());
			newLine.put(toId, entry.getValue());
			groupedValues.add(newLine);
		}

		return groupedValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("keyId", keyId);
		map.put("toId", toId);
		return map;
	}
}