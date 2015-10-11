package com.purejadeite.jadegreen.definition.option.cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractApplier;

/**
 * Cellの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractCellConverter extends AbstractApplier implements CellOption, Serializable {

	private static final long serialVersionUID = 3541565837327103077L;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractCellConverter() {
	}

	public Object apply(Object value) {
		if (value instanceof Iterable) {
			@SuppressWarnings("unchecked")
			Iterable<Object> values = (Iterable<Object>) value;
			List<Object> vals = new ArrayList<>();
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
