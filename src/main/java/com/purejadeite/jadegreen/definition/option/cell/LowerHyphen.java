package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.util.CaseFormat;

/**
*
* <pre>
* ロウアーハイフン形式の文字列へ変換を行うクラス
* 例: "abcDef" -> "abc-def"
* </pre>
* @author mitsuhiroseino
*
*/
public class LowerHyphen extends AbstractCaseFormatCellOption {

	private static final long serialVersionUID = 1942276560388833496L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public LowerHyphen(String id, Map<String, Object> config) {
		super(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return format(value, CaseFormat.LOWER_HYPHEN);
	}

}
