package com.purejadeite.jadegreen.definition.option.cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.option.AbstractOption;

/**
 * Cellの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractCellOption extends AbstractOption implements CellOption, Serializable {

	private static final long serialVersionUID = 3541565837327103077L;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractCellOption(Definition<?> definition) {
		super(definition);
	}

	public Object apply(Object value, Content<?, ?> content) {
		if (value == SpecificValue.UNDEFINED) {
			return value;
		} else if (value instanceof Iterable) {
			@SuppressWarnings("unchecked")
			Iterable<Object> values = (Iterable<Object>) value;
			List<Object> vals = new ArrayList<>();
			for (Object v : values) {
				vals.add(this.apply(v, content));
			}
			return vals;
		} else {
			return applyImpl(value, content);
		}
	}

	abstract protected Object applyImpl(Object value, Content<?, ?> content);

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("name", this.getClass().getSimpleName());
		return map;
	}

}
