package com.purejadeite.jadegreen.definition.option.cell;

import java.util.ArrayList;
import java.util.Collection;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * 値を生成する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractUnrelatedValueGenerator extends AbstractRelatedValueGenerator {

	private static final long serialVersionUID = 6845820256414231577L;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractUnrelatedValueGenerator(Definition<?> definition) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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

}
