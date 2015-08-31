package com.purejadeite.jadegreen.definition.converter.range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * グループ化用テーブルコンバーター
 * @author mitsuhiroseino
 *
 */
public class Group extends AbstractRangeConverter {

	private static final long serialVersionUID = 1131941852790003606L;

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
		this((String) config.get("keyId"), (String) config.get("toId"));
	}

	/**
	 *
	 * @param range 変換元の値を持つ定義
	 * @param keyId グループ化キーとなる項目の定義ID
	 * @param toId グループ化した値を保存する項目の定義ID
	 */
	public Group(String keyId, String toId) {
		super();
		this.keyId = keyId;
		this.toId = toId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object convertImpl(List<Map<String, Object>> values) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", keyId=" + keyId + ", toId=" + toId + "]";
	}
}