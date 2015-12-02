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

	private static final String CFG_TARGET_ID = "targetId";

	private static final String CFG_MAP = "map";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID, CFG_TARGET_ID, CFG_MAP };

	/**
	 * キーとなる項目の定義ID
	 */
	private String keyId;

	/**
	 * 変換対象の定義ID
	 */
	private String targetId;

	/**
	 * キーとなる項目の値と変換後の定義IDを紐付けた定義
	 */
	private Map<String, String> map;

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
		this.targetId = RoughlyMapUtils.getString(config, CFG_TARGET_ID);
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
			Object keyValue = line.get(keyId);
			if (map.containsKey(keyValue)) {
				// 対象の行の場合は指定の項目のidを差し替える
				Map<String, Object> modefied = new HashMap<>();
				// 元のMapはいじりたくないので別のMapを作る
				modefied.putAll(line);
				// キーの値に対応するidを取得
				String id = map.get(keyValue);
				if (id == null) {
					// idが無い場合は項目を削除
					modefied.remove(targetId);
				} else {
					// idがある場合はidを差し替え
					modefied.put(id, modefied.remove(targetId));
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
		map.put("targetId", targetId);
		map.put("map", this.map);
		return map;
	}
}