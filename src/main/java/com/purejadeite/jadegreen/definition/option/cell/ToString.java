package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

/**
 * 文字列を String へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToString extends AbstractStringCellOption {

	private static final long serialVersionUID = -4473704020871289734L;

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
