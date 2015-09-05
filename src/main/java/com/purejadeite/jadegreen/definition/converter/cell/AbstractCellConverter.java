package com.purejadeite.jadegreen.definition.converter.cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.purejadeite.jadegreen.AbstractToJson;

/**
 * Cellの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractCellConverter extends AbstractToJson implements CellConverter, Serializable {

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

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("name", this.getClass().getSimpleName());
		return map;
	}

}
