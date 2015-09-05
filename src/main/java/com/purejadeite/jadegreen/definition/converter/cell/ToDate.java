package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.purejadeite.jadegreen.CellUtils;

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
		use1904windowing = MapUtils.getBooleanValue(config, "use1904windowing");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return CellUtils.getDateValue(value, use1904windowing);
	}

}
