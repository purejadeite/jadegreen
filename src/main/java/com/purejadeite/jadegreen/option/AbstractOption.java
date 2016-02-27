package com.purejadeite.jadegreen.option;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionInterface;

/**
 * Cellの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractOption implements OptionInterface, Serializable {

	protected DefinitionInterface<?> definition;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractOption(DefinitionInterface<?> definition) {
		this.definition = definition;
	}

	@Override
	public DefinitionInterface<?> getDefinition() {
		return definition;
	}

//	@SuppressWarnings("unchecked")
//	public Object apply(Object value) {
//		if (value == SpecificValue.UNDEFINED) {
//			return value;
//		} else if (value instanceof List) {
//			List<Object> listValue = (List<Object>) value;
//			if (listValue.isEmpty()) {
//				return applyImpl(listValue);
//			} else {
//				Object item = listValue.get(0);
//				if (item instanceof Map) {
//					return applyImpl((List<Map<String, Object>>) value);
//
//				} else {
//					return applyImpl((List<Object>) value);
//				}
//			}
//		} else if (value instanceof Map) {
//			return applyImpl((Map<String, Object>) value);
//		} else {
//			return applyImpl(value);
//		}
//	}
//
//	// Cell, List Cell
//	abstract protected Object applyImpl(Object value);
//
//	// Sheet, Category
//	abstract protected Object applyImpl(Map<String, Object> value);
//
//	// Book, Table
//	abstract protected Object applyImpl(List<Map<String, Object>> value);

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
