package com.purejadeite.jadegreen.definition.converter.cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.purejadeite.core.JsonUtils;
import com.purejadeite.core.ToJson;


/**
 * Cellの値を変換する抽象クラス
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractCellConverter implements CellConverter, ToJson, Serializable {

	private static final long serialVersionUID = -554429680948708719L;

	protected CellConverter converter;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 */
	public AbstractCellConverter() {
	}

	public Object convert(Object value) {
		if (value instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> values = (List<Object>)value;
			List<Object> vals = new ArrayList<>();
			for (Object v: values) {
				vals.add(this.convert(v));
			}
			return vals;
		} else {
			Object val = convertImpl(value);
			if (converter == null) {
				return val;
			} else {
				return converter.convert(val);
			}
		}
	}

	abstract protected Object convertImpl(Object value);

	public void chain(CellConverter converter) {
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
