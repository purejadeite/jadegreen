package com.purejadeite.jadegreen.definition.option.range;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * 削除コンバーター
 * @author mitsuhiroseino
 *
 */
public class Unique extends AbstractRangeConverter {

	private static final long serialVersionUID = 5701716891112764511L;

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
	protected Object applyImpl(List<Map<String, Object>> values) {
		return new ArrayList<>(new LinkedHashSet<>(values));
	}

}