package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import com.purejadeite.jadegreen.CellUtils;

/**
 * 文字列を Float へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToFloat extends AbstractStringCellConverter {

	private static final long serialVersionUID = 7106270007850010696L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToFloat(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return CellUtils.getLongValue(value);
	}

}
