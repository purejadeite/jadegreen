package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.SxssfUtils;

/**
 * 文字列を BigDecimal へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToBigDecimal extends AbstractStringCellOption {

	private static final long serialVersionUID = 8053055718334698484L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToBigDecimal(String id, Map<String, Object> config) {
		super(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return SxssfUtils.getBigDecimal(value);
	}

}
