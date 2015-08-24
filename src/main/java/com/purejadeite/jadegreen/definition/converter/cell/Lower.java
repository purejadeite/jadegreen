package com.purejadeite.jadegreen.definition.converter.cell;

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
public class Lower extends AbstractStringCellConverter {

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
	public Object convertImpl(String value) {
		return StringUtils.lowerCase(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}
}
