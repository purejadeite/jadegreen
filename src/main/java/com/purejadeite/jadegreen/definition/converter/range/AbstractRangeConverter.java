package com.purejadeite.jadegreen.definition.converter.range;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.purejadeite.core.JsonUtils;
import com.purejadeite.core.ToJson;

/**
 * Rangeの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 */
public abstract class AbstractRangeConverter implements RangeConverter, ToJson, Serializable {

	private static final long serialVersionUID = 4438043042165420559L;

	protected RangeConverter converter;

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
	public Object convert(Object values) {
		return convertImpl((List<Map<String, Object>>) values);
	}

	/**
	 * テーブルの変換を行います
	 *
	 * @param values
	 *            変換前のテーブル
	 * @return 変換後のテーブル
	 */
	abstract protected Object convertImpl(List<Map<String, Object>> values);

	@Override
	public void chain(RangeConverter converter) {
		if (this.converter != null) {
			this.converter.chain(converter);
		} else {
			this.converter = converter;
		}
	}

	/**
	 * JSON形式のnameとvalueの表現を取得します
	 * @param name プロパティ名
	 * @param value 値
	 * @return "name": "value"形式の文字列
	 */
	protected String getJson(String name, Object value) {
		return JsonUtils.getJsonStyle(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return getJson("converter", converter);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "converter=" + converter;
	}
}
