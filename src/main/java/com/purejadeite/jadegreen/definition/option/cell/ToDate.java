package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;
import com.purejadeite.jadegreen.SxssfUtils;

/**
 * 文字列を Date へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToDate extends AbstractStringCellConverter {

	private boolean use1904windowing;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToDate(Map<String, Object> config) {
		super();
		use1904windowing = RoughlyMapUtils.getBooleanValue(config, "use1904windowing");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return SxssfUtils.getDate(value, use1904windowing);
	}

}
