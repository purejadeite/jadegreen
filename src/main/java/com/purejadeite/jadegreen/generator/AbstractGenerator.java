package com.purejadeite.jadegreen.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * 値を生成する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractGenerator implements Generator, Serializable {

	protected Definition<?> definition;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractGenerator(Definition<?> definition) {
		this.definition = definition;
	}

	@Override
	public Definition<?> getDefinition() {
		return definition;
	}

	public Object generate(Object value, Content<?, ?> content) {
		if (value == SpecificValue.UNDEFINED) {
			return value;
		} else if (value instanceof Iterable) {
			@SuppressWarnings("unchecked")
			Iterable<Object> values = (Iterable<Object>) value;
			List<Object> vals = new ArrayList<>();
			for (Object v : values) {
				vals.add(this.generate(v, content));
			}
			return vals;
		} else {
			return applyImpl(value, content);
		}
	}

	abstract protected Object applyImpl(Object value, Content<?, ?> content);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return toMap().toString();
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<>();
		return map;
	}

}
