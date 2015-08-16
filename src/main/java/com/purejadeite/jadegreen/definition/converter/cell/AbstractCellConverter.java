package com.purejadeite.jadegreen.definition.converter.cell;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * Cellの値を変換する抽象クラス
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractCellConverter implements CellConverter, Serializable {

	private static final long serialVersionUID = -554429680948708719L;

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
