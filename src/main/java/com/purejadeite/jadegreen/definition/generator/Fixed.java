package com.purejadeite.jadegreen.definition.generator;

import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;

/**
 * 固定値を提供するクラス
 * @author mitsuhiroseino
 *
 */
public class Fixed extends AbstractUnrelatedValueGenerator {

	private static final String CFG_VALUE = "value";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {CFG_VALUE};

	/**
	 * 固定値
	 */
	private String value;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Fixed(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		value = RoughlyMapUtils.getString(config, CFG_VALUE);
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
