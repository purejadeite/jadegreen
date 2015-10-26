package com.purejadeite.jadegreen.definition.option.cell;

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

	private static final long serialVersionUID = 2045257264059932768L;

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
