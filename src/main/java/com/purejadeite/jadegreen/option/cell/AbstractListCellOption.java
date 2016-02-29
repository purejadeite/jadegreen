package com.purejadeite.jadegreen.option.cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.option.AbstractOption;

/**
 * Cellの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractListCellOption extends AbstractOption implements ListCellOptionInterface, Serializable {

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractListCellOption(DefinitionInterface<?> definition) {
		super(definition);
	}

	@SuppressWarnings("unchecked")
	public Object apply(Object value, ContentInterface<?, ?> content) {
		if (value == SpecificValue.UNDEFINED) {
			return value;
		}
		List<Object> values = null;
		if (value instanceof List) {
			values = (List<Object>) value;
		} else {
			values = new ArrayList<>();
			values.add(value);
		}
		return applyImpl(values, content);
	}

	abstract protected Object applyImpl(List<Object> value, ContentInterface<?, ?> content);

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}

}
