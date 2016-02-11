package com.purejadeite.jadegreen.definition.option;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * Cellの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractOption implements Option, Serializable {

	private Definition<?> definition;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractOption(Definition<?> definition) {
		this.definition = definition;
	}

	@Override
	public Definition<?> getDefinition() {
		return definition;
	}

//	@SuppressWarnings("unchecked")
//	public Object apply(Object value) {
//		if (value == SpecificValue.UNDEFINED) {
//			return value;
//		} else if (value instanceof List) {
//			return applyImpl((List<Object>) value);
//		} else if (value instanceof Map) {
//			return applyImpl((Map<String, Object>) value);
//		} else {
//			return applyImpl(value);
//		}
//	}
//
//	abstract protected Object applyImpl(List<Object> value);
//
//	abstract protected Object applyImpl(Map<String, Object> value);
//
//	abstract protected Object applyImpl(Object value);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return toMap().toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<>();
		return map;
	}

}
