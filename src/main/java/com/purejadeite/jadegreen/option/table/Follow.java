package com.purejadeite.jadegreen.option.table;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.util.SimpleValidator;

/**
 * 前の要素の引き継ぎテーブルコンバーター
 *
 * @author mitsuhiroseino
 *
 */
public class Follow extends AbstractTableOption {

	protected static final String CFG_FOLLOW_ID = "followId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_FOLLOW_ID };

	/**
	 * フォローする項目の定義ID
	 */
	protected String followId;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Follow(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.followId = getString(config, CFG_FOLLOW_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values, ContentInterface<?, ?> content) {
		// keyIdの項目の値が空だったらひとつ前の値を引き継ぐ
		List<Map<String, Object>> newValues = new ArrayList<>();
		Object prev = null;
		for (Map<String, Object> line : values) {
			Map<String, Object> newLine = new LinkedHashMap<>(line);
			Object key = newLine.get(followId);
			if (key == null || key.toString().equals("")) {
				newLine.put(followId, prev);
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
		map.put("followId", followId);
		return map;
	}
}