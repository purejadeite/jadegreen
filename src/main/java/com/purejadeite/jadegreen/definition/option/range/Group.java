package com.purejadeite.jadegreen.definition.option.range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;

/**
 * グループ化用テーブルコンバーター
 * @author mitsuhiroseino
 *
 */
public class Group extends AbstractRangeConverter {

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
	 * @param range 変換元の値を持つ定義
	 * @param config コンバーターのコンフィグ
	 */
	public Group(Map<String, Object> config) {
		super();
		this.keyId = MapUtils.getString(config, "keyId");
		this.toId = MapUtils.getString(config, "toId");
		if (keyId == null) {
			throw new IllegalArgumentException("keyId must not be null.");
		}
		if (toId == null) {
			throw new IllegalArgumentException("toId must not be null.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		// グループ化された配列を保持するMapに変換
		Map<Object, List<Map<String, Object>>> groupedMap = new HashMap<>();
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