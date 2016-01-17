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
	 * シート
	 */
	public static final String SHEETS = "sheets";

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
	 * 終了行番号
	 */
	public static final String END_ROW = "endRow";

	/**
	 * 列番号
	 */
	public static final String COLUMN = "column";

	/**
	 * 終了列番号
	 */
	public static final String END_COLUMN = "endColumn";

	/**
	 * 繰り返し項目の取得開始判定項目
	 */
	public static final String BEGIN_ID = "beginId";

	/**
	 * 繰り返し項目の取得開始判定値
	 */
	public static final String BEGIN_VALUE = "beginValue";

	/**
	 * 繰り返し項目の取得終了判定項目
	 */
	public static final String BREAK_ID = "breakId";

	/**
	 * 繰り返し項目の取得終了判定値
	 */
	public static final String BREAK_VALUE = "breakValue";

	/**
	 * 列方向の繰り返しの子要素
	 */
	public static final String ROWS = "rows";

	/**
	 * 行方向の繰り返しの子要素
	 */
	public static final String COLUMNS = "columns";

	/**
	 * セルの結合
	 */
	public static final String JOIN = "join";

	/**
	 * オプション
	 */
	public static final String[] OPTIONS = {"options", "option"};

	/**
	 * コンバータークラス
	 */
	public static final String TYPE = "type";

	/**
	 * 分割文字
	 */
	public static final String SPLITTER = "splitter";

	/**
	 * 外部ファイルのパス
	 */
	public static final String FILE = "file";

	/**
	 * 値の生成
	 */
	public static final String GENERATOR = "generator";

	/**
	 * 値
	 */
	public static final String VALUE = "value";

}
