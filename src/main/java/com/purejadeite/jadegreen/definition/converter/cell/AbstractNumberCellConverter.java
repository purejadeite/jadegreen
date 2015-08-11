package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

/**
 * 文字列を変換する抽象クラスです
 * @author mitsuhiroseino
 *
 */
public abstract class AbstractNumberCellConverter extends AbstractStringCellConverter {

	private static final long serialVersionUID = 8428819225468846834L;

	private boolean nullToZero = true;

	private boolean emptyToZero = true;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 */
	public AbstractNumberCellConverter(Map<String, Object> config) {
		super();
		Boolean ntz = MapUtils.getBoolean(config, "nullToZero");
		if (ntz != null) {
			this.nullToZero = ntz.booleanValue();
		}
		Boolean etz = MapUtils.getBoolean(config, "emptyToZero");
		if (etz != null) {
			this.emptyToZero = etz.booleanValue();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertImpl(String value) {
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
	abstract protected Object toNumber(String value);

	abstract protected Object getZero();

}
