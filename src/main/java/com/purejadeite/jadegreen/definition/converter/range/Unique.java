package com.purejadeite.jadegreen.definition.converter.range;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * 削除コンバーター
 * @author mitsuhiroseino
 *
 */
public class Unique extends AbstractRangeConverter {


	/**
	 * コンストラクタ
	 * @param range 変換元の値を持つ定義
	 * @param config コンバーターのコンフィグ
	 */
	public Unique(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object convertImpl(List<Map<String, Object>> values) {
		return new ArrayList<>(new LinkedHashSet<>(values));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, Object>> toList() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", this.getClass().getSimpleName());
		List<Map<String, Object>> list = super.toList();
		list.add(map);
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}

}