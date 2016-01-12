package com.purejadeite.jadegreen.definition.option.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.util.collection.RoughlyMapUtils;

/**
 * グループ化用テーブルコンバーター
 *
 * @author mitsuhiroseino
 *
 */
public class Group extends AbstractTableConverter {

	private static final long serialVersionUID = -7712482157745974514L;

	private static final String CFG_GROUPS = "group";

	private static final String CFG_GROUPS_GROUP_ID = "groupId";

	private static final String CFG_GROUPS_VALUE_ID = "valueId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_GROUPS };

	/**
	 * グループ化キーとなる項目の定義ID
	 */
	private List<Map<String, Object>> groups;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Group(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		Map<String, Object> group = RoughlyMapUtils.getMap(config, CFG_GROUPS);
		if (group == null) {
			this.groups = RoughlyMapUtils.getList(config, CFG_GROUPS);
		} else {
			this.groups = new ArrayList<>();
			this.groups.add(group);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		List<Map<String, Object>> groupedValues = values;
		for (Map<String, Object> group : groups) {
			// 繰り返しグループ化
			List<String> groupIds = null;
			String groupId = RoughlyMapUtils.getString(group, CFG_GROUPS_GROUP_ID);
			if (groupId == null) {
				groupIds = RoughlyMapUtils.getList(group, CFG_GROUPS_GROUP_ID);
			} else {
				groupIds = new ArrayList<>();
				groupIds.add(groupId);
			}
			String valueId = RoughlyMapUtils.getString(group, CFG_GROUPS_VALUE_ID);
			groupedValues = group(groupIds, valueId, groupedValues);
		}
		return groupedValues;
	}

	// groupIdをキーとしたテーブルを作成します
	private List<Map<String, Object>> group(List<String> groupIds, String valueId, List<Map<String, Object>> values) {

		// 行を各グループに割り振る
		Map<Map<String, Object>, List<Map<String, Object>>> groupedMap = new LinkedHashMap<>();
		for (Map<String, Object> line : values) {
			Map<String, Object> groupKeys = new LinkedHashMap<>();
			for (String groupId : groupIds) {
				groupKeys.put(groupId, line.get(groupId));
			}
			List<Map<String, Object>> groupedLines = groupedMap.get(groupKeys);
			if (groupedLines == null) {
				groupedLines = new ArrayList<>();
				groupedMap.put(groupKeys, groupedLines);
			}
			groupedLines.add(line);
		}

		// グループ化したテーブルに変換
		List<Map<String, Object>> groupedValues = new ArrayList<>();
		for (Map<String, Object> grpIds : groupedMap.keySet()) {
			List<Map<String, Object>> lines = groupedMap.get(grpIds);
			groupedValues.add(createLine(valueId, grpIds, lines));
		}

		return groupedValues;
	}

	/**
	 * 同じグループの行を、１つの新しい行に纏めます
	 * @param valueId グループ化された値のid
	 * @param groupIdValues グループ化キーの値
	 * @param lines 同じグループに属する行
	 * @return グループ化された行
	 */
	private Map<String, Object> createLine(String valueId, Map<String, Object> groupIdValues, List<Map<String, Object>> lines) {
		// グループ化された行
		Map<String, Object> newLine = new HashMap<>();
		// グループに属する行の値
		List<Map<String, Object>> values = new ArrayList<>();
		newLine.put(valueId, values);

		// グループキーの項目を設定
		for (String groupId : groupIdValues.keySet()) {
			newLine.put(groupId, groupIdValues.get(groupId));
		}

		// グループの値を設定
		for (Map<String, Object> line : lines) {
			Map<String, Object> l = new HashMap<>();
			for (String id : line.keySet()) {
				if (!groupIdValues.containsKey(id)) {
					// グループ化キー以外を設定
					l.put(id, line.get(id));
				}
			}
			values.add(l);
		}
		return newLine;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("groups", groups);
		return map;
	}
}