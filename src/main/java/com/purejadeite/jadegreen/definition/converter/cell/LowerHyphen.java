package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import com.google.common.base.CaseFormat;

/**
*
* <pre>
* ロウアーハイフン形式の文字列へ変換を行うクラス
* 例: "abcDef" -> "abc-def"
* </pre>
* @author mitsuhiroseino
*
*/
public class LowerHyphen extends AbstractCaseFormatCellConverter {

	private static final long serialVersionUID = 7141848730680448427L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public LowerHyphen(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return format(value, CaseFormat.LOWER_HYPHEN);
	}

}
