package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import com.purejadeite.jadegreen.CellUtils;

/**
 * 文字列を BigDecimal へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToBigDecimal extends AbstractStringCellConverter {

	private static final long serialVersionUID = 4341224550647996037L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToBigDecimal(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return CellUtils.getBigDecimalValue(value);
	}

}
