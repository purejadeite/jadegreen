package com.purejadeite.jadegreen.definition.converter.range;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Rangeの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 */
public abstract class AbstractRangeConverter implements RangeConverter, Serializable {

	private static final long serialVersionUID = 4438043042165420559L;

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		try {
			List<Map<String, Object>> list = toList();
			Object[] array = list.toArray();
			ArrayUtils.reverse(array);
			return OBJECT_MAPPER.writeValueAsString(array);
		} catch (IOException e) {
			return null;
		}
	}

	public List<Map<String, Object>> toList() {
		List<Map<String, Object>> list = new ArrayList<>();
		if (converter != null) {
			list.addAll(converter.toList());
		}
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "converter=" + converter;
	}
}
