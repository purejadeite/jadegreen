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
abstract public class AbstractCellOption extends AbstractOption implements CellOptionInterface, Serializable {

	private static final long serialVersionUID = 3541565837327103077L;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractCellOption(DefinitionInterface<?> definition) {
		super(definition);
	}

	public Object apply(Object value, ContentInterface<?, ?> content) {
		if (value == SpecificValue.UNDEFINED) {
			return value;
		} else if (value instanceof Iterable) {
			if (this instanceof ListCellOption) {
				// List形式の値を処理するオプションの場合は、そのまま渡す
				return applyImpl(value, content);
			} else {
				// List形式の値を処理するオプションじゃない場合は、ばらして渡す
				@SuppressWarnings("unchecked")
				Iterable<Object> values = (Iterable<Object>) value;
				List<Object> vals = new ArrayList<>();
				for (Object v : values) {
					vals.add(this.apply(v, content));
				}
				return vals;
			}
		} else {
			return applyImpl(value, content);
		}
	}

	abstract protected Object applyImpl(Object value, ContentInterface<?, ?> content);

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("name", this.getClass().getSimpleName());
		return map;
	}

}
