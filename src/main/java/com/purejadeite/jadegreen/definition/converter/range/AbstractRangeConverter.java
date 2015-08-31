package com.purejadeite.jadegreen.definition.converter.range;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Rangeの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 */
public abstract class AbstractRangeConverter implements RangeConverter, Serializable {

	private static final long serialVersionUID = 4438043042165420559L;

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		try {
			return OBJECT_MAPPER.writeValueAsString(toMap());
		} catch (IOException e) {
			return null;
		}
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", this.getClass().getSimpleName());
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return null;
	}
}
