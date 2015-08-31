package com.purejadeite.jadegreen.definition.generator;

import java.io.IOException;
import java.io.Serializable;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 値を生成する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractValueGenerator implements ValueGenerator, Serializable {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractValueGenerator() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		try {
			return OBJECT_MAPPER.writeValueAsString(toMap());
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}

}
