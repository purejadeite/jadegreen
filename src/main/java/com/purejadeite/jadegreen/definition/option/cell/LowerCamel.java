package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.util.CaseFormat;

/**
*
* <pre>
* ロウアーキャメル形式の文字列へ変換を行うクラス
* 例: "ABC_DEF" -> "abcDef"
* </pre>
* @author mitsuhiroseino
*
*/
public class LowerCamel extends AbstractCaseFormatCellOption {

	private static final long serialVersionUID = -1001501612819077056L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public LowerCamel(String id, Map<String, Object> config) {
		super(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return format(value, CaseFormat.LOWER_CAMEL);
	}
}
