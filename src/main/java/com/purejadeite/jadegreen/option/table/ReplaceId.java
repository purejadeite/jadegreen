package com.purejadeite.jadegreen.option.table;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.util.SimpleValidator;

/**
 * IDの置換用テーブルコンバーター
 *
 * @author mitsuhiroseino
 *
 */
public class ReplaceId extends AbstractTableOption {

	protected static final String CFG_KEY_ID = "keyId";

	protected static final String CFG_NEW_IDS = "newIds";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID, CFG_NEW_IDS };

	/**
	 * キーとなる項目の定義ID
	 */
	protected String keyId;

	/**
	 * キーとなる項目の値と変換後の定義IDを紐付けた定義
	 */
	protected Map<String, Object> newIds;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public ReplaceId(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.keyId = getString(config, CFG_KEY_ID);
		this.newIds = getMap(config, CFG_NEW_IDS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values, ContentInterface<?, ?> content) {
		// グループ化された配列を保持するMapに変換
		List<Map<String, Object>> converted = new ArrayList<>();
		for (Map<String, Object> line : values) {
			// 対象の行か？
			String keyValue = getString(line, keyId);
			if (newIds.containsKey(keyValue)) {
				// 対象の行の場合は指定の項目のidを差し替える
				Map<String, Object> modefied = new HashMap<>();
				// 元のMapはいじりたくないので別のMapを作る
				modefied.putAll(line);
				// キーの値に対応するidを取得
				Map<String, Object> idMap = getMap(newIds, keyValue);
				if (idMap == null) {
					continue;
				} else {
					for (String orgId : idMap.keySet()) {
						String replacedId = getString(idMap, orgId);
						if (replacedId == null) {
							// nullの場合は削除
							modefied.remove(replacedId);
						} else if (modefied.containsKey(orgId)) {
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
		map.put("map", this.newIds);
		return map;
	}
}