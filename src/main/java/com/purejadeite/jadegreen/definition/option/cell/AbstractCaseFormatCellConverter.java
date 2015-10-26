package com.purejadeite.jadegreen.definition.option.cell;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.CaseFormat;
import com.purejadeite.jadegreen.DefinitionException;

/**
 * <pre>
 * 文字列の形式を変換する抽象クラスです
 * 以下の形式の文字列を相互変換します
 *・LOWER_CAMEL:		'abcDef'
 *・UPPER_CAMEL:		'AbcDef'
 *・LOWER_UNDERSCORE:	'abc_def'
// *・UPPER_UNDERSCORE:	'ABC_DEF'
 *・LOWER_HYPHEN:		'abc-def'
 * </pre>
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractCaseFormatCellConverter extends AbstractStringCellConverter {

	/**
	 * コンストラクタ
	 * @param cell Cell読み込み定義
	 */
	public AbstractCaseFormatCellConverter() {
		super();
	}

	/**
	 * 形式の変換処理
	 * @param value 値
	 * @param to 変換後の形式
	 * @return 変換された値
	 */
	protected Object format(String value, CaseFormat to) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}

		CaseFormat from = null;
		for (CaseFormat cf : CaseFormat.values()) {
			if (cf.match(value)) {
				from = cf;
				break;
			}
		}
		if (from == null) {
			throw new DefinitionException();
		}
		return from.to(to, value);
	}

}
