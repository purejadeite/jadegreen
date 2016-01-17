package com.purejadeite.jadegreen.definition.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

/**
 * 文字列を変換する抽象クラスです
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractNumberCellOption<N> extends AbstractStringCellOption {

	private static final long serialVersionUID = 698720974407694707L;

	private static final String CFG_NULL_TO_ZERO = "nullToZero";

	private static final String CFG_EMPTY_TO_ZERO = "emptyToZero";

	private boolean nullToZero;

	private boolean emptyToZero;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 */
	public AbstractNumberCellOption(Map<String, Object> config) {
		super();
		this.nullToZero = getBooleanValue(config, CFG_NULL_TO_ZERO, true);
		this.emptyToZero = getBooleanValue(config, CFG_EMPTY_TO_ZERO, true);
	}

	/**
	 * {@inheritDoc}
	 */
	protected Object applyToString(String value) {
		if (value == null) {
			if (nullToZero) {
				return getZero();
			}
		} else if (value.length() == 0) {
			if (emptyToZero) {
				return getZero();
			}
		}
		return toNumber(value);
	}

	/**
	 * 数値を変換します
	 * @param value 数値
	 * @return 変換された数値
	 */
	abstract protected N toNumber(String value);

	abstract protected N getZero();

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("nullToZero", nullToZero);
		map.put("emptyToZero", emptyToZero);
		return map;
	}

}
