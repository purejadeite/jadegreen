package com.purejadeite.jadegreen.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * 文字列を変換する抽象クラスです
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractNumberCellOption<N> extends AbstractStringCellOption {

	private static final long serialVersionUID = 698720974407694707L;

	protected static final String CFG_NULL_TO_ZERO = "nullToZero";

	protected static final String CFG_EMPTY_TO_ZERO = "emptyToZero";

	protected boolean nullToZero;

	protected boolean emptyToZero;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 */
	public AbstractNumberCellOption(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		this.nullToZero = getBooleanValue(config, CFG_NULL_TO_ZERO, true);
		this.emptyToZero = getBooleanValue(config, CFG_EMPTY_TO_ZERO, true);
	}

	/**
	 * {@inheritDoc}
	 */
	protected Object applyToString(String value, Content<?, ?> content) {
		if (value == null) {
			if (nullToZero) {
				return getZero();
			}
		} else if (value.length() == 0) {
			if (emptyToZero) {
				return getZero();
			}
		}
		return toNumber(value, content);
	}

	/**
	 * 数値を変換します
	 * @param value 数値
	 * @return 変換された数値
	 */
	abstract protected N toNumber(String value, Content<?, ?> content);

	abstract protected N getZero();

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("nullToZero", nullToZero);
		map.put("emptyToZero", emptyToZero);
		return map;
	}

}
