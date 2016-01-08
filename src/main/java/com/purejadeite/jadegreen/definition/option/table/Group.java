package com.purejadeite.jadegreen.definition.option.table;

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
public class Group extends AbstractTableConverter {

	private static final long serialVersionUID = -7712482157745974514L;

	private static final String CFG_GROUP_ID = "groupId";

	private static final String CFG_VALUE_ID = "valueId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_GROUP_ID, CFG_VALUE_ID };

	/**
	 * グループ化キーとなる項目の定義ID
	 */
	private String groupId;

	/**
	 * グループ化した値を保存する項目の定義ID
	 */
	private String valueId;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Group(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.groupId = RoughlyMapUtils.getString(config, CFG_GROUP_ID);
		this.valueId = RoughlyMapUtils.getString(config, CFG_VALUE_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		// グループ化された配列を保持するMapに変換
		Map<Object, List<Map<String, Object>>> groupedMap = new LinkedHashMap<>();
		for (Map<String, Object> line : values) {
			Object groupKey = line.get(groupId);
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
			newLine.put(groupId, entry.getKey());
			newLine.put(valueId, entry.getValue());
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
		map.put("groupId", groupId);
		map.put("valueId", valueId);
		return map;
	}
}