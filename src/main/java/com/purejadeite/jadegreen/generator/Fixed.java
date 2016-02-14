package com.purejadeite.jadegreen.generator;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.util.SimpleValidator;

/**
 * 固定値を提供するクラス
 *
 * @author mitsuhiroseino
 *
 */
public class Fixed extends AbstractUnrelatedValueGenerator {

	private static final long serialVersionUID = 2129167612190837570L;

	protected static final String CFG_VALUE = "value";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_VALUE };

	/**
	 * 固定値
	 */
	protected String value;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Fixed(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		value = getString(config, CFG_VALUE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(Object value, Content<?, ?> content) {
		return this.value;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("value", value);
		return map;
	}

}
