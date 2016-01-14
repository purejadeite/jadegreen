package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.SxssfUtils;
import com.purejadeite.util.collection.RoughlyMapUtils;

/**
 * 文字列を Date へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToDate extends AbstractStringCellOption {

	private static final long serialVersionUID = 247396559988860716L;

	private static final String CFG_USE1904 = "use1904";

	private boolean use1904windowing;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToDate(Map<String, Object> config) {
		super();
		use1904windowing = RoughlyMapUtils.getBooleanValue(config, CFG_USE1904);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return SxssfUtils.getDate(value, use1904windowing);
	}

}
