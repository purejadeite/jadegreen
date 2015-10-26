package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.CaseFormat;

/**
 *
 * <pre>
 * アッパーアンダースコア形式の文字列へ変換を行うクラス
 * 例: "abcDef" -> "ABC_DEF"
 * </pre>
 * @author mitsuhiroseino
 *
 */
public class UpperUnderscore extends AbstractCaseFormatCellConverter {

	private static final long serialVersionUID = -6147481277115805078L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public UpperUnderscore(Map<String, Object> config) {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return format(value, CaseFormat.UPPER_UNDERSCORE);
	}

}
