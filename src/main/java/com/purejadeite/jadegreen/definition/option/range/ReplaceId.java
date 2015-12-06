package com.purejadeite.jadegreen.definition.option.range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.util.RoughlyMapUtils;

/**
 * IDの置換用テーブルコンバーター
 *
 * @author mitsuhiroseino
 *
 */
public class ReplaceId extends AbstractRangeConverter {

	private static final String CFG_KEY_ID = "keyId";

	private static final String CFG_MAP = "map";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID, CFG_MAP };

	/**
	 * キーとなる項目の定義ID
	 */
	private String keyId;

	/**
	 * キーとなる項目の値と変換後の定義IDを紐付けた定義
	 */
	private Map<String, Object> map;

	/**
	 * コンストラクタ
	 *
	 * @param range
	 *            変換元の値を持つ定義
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public ReplaceId(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.keyId = RoughlyMapUtils.getString(config, CFG_KEY_ID);
		this.map = RoughlyMapUtils.getMap(config, CFG_MAP);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		// グループ化された配列を保持するMapに変換
		List<Map<String, Object>> converted = new ArrayList<>();
		for (Map<String, Object> line : values) {
			// 対象の行か？
			String keyValue = RoughlyMapUtils.getString(line, keyId);
			if (map.containsKey(keyValue)) {
				// 対象の行の場合は指定の項目のidを差し替える
				Map<String, Object> modefied = new HashMap<>();
				// 元のMapはいじりたくないので別のMapを作る
				modefied.putAll(line);
				// キーの値に対応するidを取得
				Map<String, Object> idMap = RoughlyMapUtils.getMap(map, keyValue);
				if (idMap == null) {
					continue;
				} else {
					for (String orgId : idMap.keySet()) {
						String replacedId = RoughlyMapUtils.getString(idMap, orgId);
						if (replacedId == null) {
							// nullの場合は削除
							modefied.remove(replacedId);
						} else {
							// idがある場合はidを差し替え
							modefied.put(replacedId, modefied.remove(orgId));
						}
					}
				}
				converted.add(modefied);
			} else {
				converted.add(line);
			}
		}
		return converted;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("keyId", keyId);
		map.put("map", this.map);
		return map;
	}
}