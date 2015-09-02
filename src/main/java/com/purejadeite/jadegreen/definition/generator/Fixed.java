package com.purejadeite.jadegreen.definition.generator;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ランダムなUUIDを生成するクラス
 * @author mitsuhiroseino
 *
 */
public class Fixed extends AbstractUnrelatedValueGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(Fixed.class);

	private String value;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	@SuppressWarnings("unchecked")
	public Fixed(Map<String, Object> config) {
		super();
		value = MapUtils.getString(config, "value");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value) {
		return value;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("value", value);
		return map;
	}

}
