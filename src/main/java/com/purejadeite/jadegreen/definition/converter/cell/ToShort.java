package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import com.purejadeite.jadegreen.CellUtils;

/**
 * 文字列を Short へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToShort extends AbstractStringCellConverter {

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToShort(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return CellUtils.getShortValue(value);
	}

}