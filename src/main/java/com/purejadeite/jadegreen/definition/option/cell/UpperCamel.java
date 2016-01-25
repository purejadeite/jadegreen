package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.util.CaseFormat;

/**
*
* <pre>
* アッパーキャメル形式の文字列へ変換を行うクラス
* 例: "abcDef" -> "AbcDef"
* </pre>
* @author mitsuhiroseino
*
*/
public class UpperCamel extends AbstractCaseFormatCellOption {

	private static final long serialVersionUID = 7814724537813902517L;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public UpperCamel(Definition<?> definition, Map<String, Object> config) {
		super(definition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return format(value, CaseFormat.UPPER_CAMEL);
	}

}
