package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import com.google.common.base.CaseFormat;

/**
 *
 * <pre>
 * アッパーアンダースコア形式の文字列へ変換を行うクラス
 * 例: "abcDef" -> "ABC_DEF"
 * </pre>
 * @author mitsuhiroseino
 *
 */
public class UpperUnderscore extends AbstractCaseFormatConverter {

	private static final long serialVersionUID = 5465181704672626887L;

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
	public Object convertImpl(String value) {
		return format(value, CaseFormat.UPPER_UNDERSCORE);
	}

//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public String toJson() {
//		return "{" + super.toJson() + "}";
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}

}
