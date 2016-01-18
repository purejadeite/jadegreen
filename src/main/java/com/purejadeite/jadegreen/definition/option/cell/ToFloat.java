package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.SxssfUtils;

/**
 * 文字列を Float へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToFloat extends AbstractStringCellOption {

	private static final long serialVersionUID = 6055332520387597482L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToFloat(String id, Map<String, Object> config) {
		super(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return SxssfUtils.getLong(value);
	}

}
