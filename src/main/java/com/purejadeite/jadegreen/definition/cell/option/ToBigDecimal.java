package com.purejadeite.jadegreen.definition.cell.option;

import java.util.Map;

import com.purejadeite.jadegreen.SxssfUtils;

/**
 * 文字列を BigDecimal へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToBigDecimal extends AbstractStringCellConverter {

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
		return SxssfUtils.getBigDecimal(value);
	}

}
