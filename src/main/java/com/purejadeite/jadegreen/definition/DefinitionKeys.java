package com.purejadeite.jadegreen.definition;

/**
 * Excel読み込み定義のプロパティ名
 * @author mitsuhiroseino
 *
 */
public class DefinitionKeys {

	/**
	 * 定義ID
	 */
	public static final String ID = "id";

	/**
	 * コメント
	 */
	public static final String COMMENT = "comment";

	/**
	 * 取得条件・シート名
	 */
	public static final String NAME = "name";

	/**
	 * 取得条件
	 */
	public static final String CONDITIONS = "conditions";

	/**
	 * データの出力を行わないか
	 */
	public static final String NO_OUTPUT = "noOutput";

	/**
	 * Sheet配下の全Cellの定義
	 */
	public static final String CELLS = "cells";

	/**
	 * 行番号
	 */
	public static final String ROW = "row";

	/**
	 * 開始行番号
	 */
	public static final String BEGIN_ROW = "beginRow";

	/**
	 * 終了行番号
	 */
	public static final String END_ROW = "endRow";

	/**
	 * 列番号
	 */
	public static final String COLUMN = "column";

	/**
	 * 開始列番号
	 */
	public static final String BEGIN_COLUMN = "beginColumn";

	/**
	 * 終了列番号
	 */
	public static final String END_COLUMN = "endColumn";

	/**
	 * 繰り返し項目の取得開始判定項目
	 */
	public static final String BEGIN_KEY = "beginKeyId";

	/**
	 * 繰り返し項目の取得開始判定値
	 */
	public static final String BEGIN_VALUE = "beginValue";

	/**
	 * 繰り返し項目の取得終了判定項目
	 */
	public static final String END_KEY = "endKeyId";

	/**
	 * 繰り返し項目の取得終了判定値
	 */
	public static final String END_VALUE = "endValue";

	/**
	 * 列方向の繰り返しの子要素
	 */
	public static final String ROWS = "rows";

	/**
	 * 行方向の繰り返しの子要素
	 */
	public static final String COLUMNS = "columns";

	/**
	 * セルのリンク
	 */
	public static final String LINK = "link";

	/**
	 * オプション
	 */
	public static final String OPTIONS = "options";

	/**
	 * コンバータークラス
	 */
	public static final String CLASS = "class";

	/**
	 * 分割文字
	 */
	public static final String SPLITTER = "splitter";

}
