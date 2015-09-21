package com.purejadeite.jadegreen.definition.option.range;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractApplier;

/**
 * Rangeの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractRangeConverter extends AbstractApplier implements RangeOption, Serializable {

	/**
	 * コンストラクタ
	 *
	 * @param range
	 *            値の取得元Range読み込み定義
	 */
	public AbstractRangeConverter() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object values) {
		return applyImpl((List<Map<String, Object>>) values);
	}

	/**
	 * テーブルの変換を行います
	 *
	 * @param values
	 *            変換前のテーブル
	 * @return 変換後のテーブル
	 */
	abstract protected Object applyImpl(List<Map<String, Object>> values);

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("name", this.getClass().getSimpleName());
		return map;
	}
}
