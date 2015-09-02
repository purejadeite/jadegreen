package com.purejadeite.jadegreen.definition.generator;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 値を生成する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractRelatedValueGenerator implements ValueGenerator, Serializable {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractRelatedValueGenerator() {
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", this.getClass().getSimpleName());
		return map;
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
		return toMap().toString();
	}

}
