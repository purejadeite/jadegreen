package com.purejadeite.jadegreen.definition.option.range;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;

/**
 * ソート用テーブルコンバーター
 * @author mitsuhiroseino
 *
 */
public class Sort extends AbstractRangeConverter {

	private static final String CFG_KEY_ID = "keyId";

	private static final String CFG_DESC = "desc";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {CFG_KEY_ID};

	/**
	 * ソートキー
	 */
	protected String keyId;

	/**
	 * 昇順/降順
	 */
	protected boolean desc;

	/**
	 * コンストラクタ
	 * @param range 変換元の値を持つ定義
	 * @param config コンバーターのコンフィグ
	 */
	public Sort(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.keyId = RoughlyMapUtils.getString(config, CFG_KEY_ID);
		this.desc = RoughlyMapUtils.getBooleanValue(config, CFG_DESC);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values) {
		Collections.sort(values, new Comparator<Map<String, Object>>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Map<String, Object> m1 = o1;
				Map<String, Object> m2 = o2;
				if (desc) {
					// 降順の場合は入れ替え
					m1 = o2;
					m2 = o1;
				}
				if (m1 == m2) {
					return 0;
				}
				if (m1 == null) {
					return -1;
				}
				if (m2 == null) {
					return 1;
				}

				Object t1 = m1.get(keyId);
				Comparable<Object> s1 = null;
				if (t1 instanceof Comparable) {
					s1 = (Comparable<Object>) t1;
				}

				Object t2 = m2.get(keyId);
				Comparable<Object> s2 = null;
				if (t2 instanceof Comparable) {
					s2 = (Comparable<Object>) t2;
				}

				if (s1 == s2) {
					return 0;
				}
				if (s1 == null) {
					return -1;
				}
				if (s2 == null) {
					return 1;
				}
				return s1.compareTo(s2);
			}
		});
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("keyId", keyId);
		map.put("desc", desc);
		return map;
	}

}