package com.purejadeite.jadegreen.definition.cell.option;

import java.util.Map;

import com.google.common.base.CaseFormat;

/**
*
* <pre>
* アッパーキャメル形式の文字列へ変換を行うクラス
* 例: "abcDef" -> "AbcDef"
* </pre>
* @author mitsuhiroseino
*
*/
public class UpperCamel extends AbstractCaseFormatCellConverter {

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public UpperCamel(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return format(value, CaseFormat.UPPER_CAMEL);
	}

}
