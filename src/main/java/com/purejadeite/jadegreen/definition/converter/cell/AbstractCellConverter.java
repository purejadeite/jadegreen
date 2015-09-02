package com.purejadeite.jadegreen.definition.converter.cell;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Cellの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractCellConverter implements CellConverter, Serializable {

	private static final long serialVersionUID = -554429680948708719L;

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractCellConverter() {
	}

	public Object apply(Object value) {
		if (value instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<Object> values = (Collection<Object>) value;
			Collection<Object> vals = new ArrayList<>();
			for (Object v : values) {
				vals.add(this.apply(v));
			}
			return vals;
		} else {
			return applyImpl(value);
		}
	}

	abstract protected Object applyImpl(Object value);

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
		return toMap().toString();
	}

}
