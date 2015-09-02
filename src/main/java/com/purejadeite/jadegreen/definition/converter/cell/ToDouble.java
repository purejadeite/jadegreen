package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import com.purejadeite.jadegreen.CellUtils;

/**
 * 文字列を Double へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToDouble extends AbstractStringCellConverter {

	private static final long serialVersionUID = 6095833065569388637L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToDouble(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return CellUtils.getDoubleValue(value);
	}

}
