package com.purejadeite.jadegreen.definition.cell.option;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
*
* <pre>
* 大文字へ変換を行うクラス
* 例: "abcdef" -> "ABCDEF"
* </pre>
* @author mitsuhiroseino
*
*/
public class Upper extends AbstractStringCellConverter {

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Upper(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return StringUtils.upperCase(value);
	}
}
