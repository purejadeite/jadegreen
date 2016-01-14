package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
*
* <pre>
* 小文字へ変換を行うクラス
* 例: "ABCDEF" -> "abcdef"
* </pre>
* @author mitsuhiroseino
*
*/
public class Lower extends AbstractStringCellOption {

	private static final long serialVersionUID = -4426567676789266496L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Lower(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return StringUtils.lowerCase(value);
	}
}
