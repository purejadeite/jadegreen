package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 文字列の長さを取得するクラス
 * @author mitsuhiroseino
 *
 */
public class Length extends AbstractStringCellConverter {

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Length(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return Integer.valueOf(StringUtils.length(value));
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}

}
