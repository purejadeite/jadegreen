package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

/**
 * 文字列を String へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToString extends AbstractStringCellConverter {

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToString(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return value == null ? null : value;
	}

}
