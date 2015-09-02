package com.purejadeite.jadegreen.definition.generator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 値を生成する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractUnrelatedValueGenerator extends AbstractRelatedValueGenerator {

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractUnrelatedValueGenerator() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object generate(Object value) {
		if (value instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<Object> values = (Collection<Object>) value;
			Collection<Object> vals = new ArrayList<>();
			for (Object v : values) {
				vals.add(this.generate(v));
			}
			return vals;
		} else {
			return applyImpl(value);
		}
	}

	abstract protected Object applyImpl(Object value);

}
