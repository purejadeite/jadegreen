package com.purejadeite.jadegreen.definition.table;

import java.util.List;

import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;

/**
 * テーブル形式の範囲の情報を保持するクラスのインターフェイスです
 * @author mitsuhiroseino
 */
public interface TableDefinition<C extends TableCellDefinition<?>> extends ParentDefinition<WorksheetDefinition, C> {

	/**
	 * 繰り返し項目の取得開始判定項目
	 */
	public static final String CFG_BEGIN_ID = "beginId";

	/**
	 * 繰り返し項目の取得開始判定値
	 */
	public static final String CFG_BEGIN_VALUE = "beginValue";

	/**
	 * 繰り返し項目の取得終了判定項目
	 */
	public static final String CFG_BREAK_ID = "breakId";

	/**
	 * 繰り返し項目の取得終了判定値
	 */
	public static final String CFG_BREAK_VALUE = "breakValue";

	/**
	 * 終了条件になる項目の定義IDを取得します
	 * @return 定義ID
	 */
	public String getBreakId();

	/**
	 * 終了条件になる項目の定義IDを設定します
	 * @param endKey 定義ID
	 */
	public void setBreakId(String breakKey);

	/**
	 * 終了条件になる項目の値を取得します
	 * @return 値
	 */
	public String getBreakValue();

	/**
	 * 終了条件になる項目の値を設定します
	 * @param endValue 値
	 */
	public void setBreakValue(String breakValue);

	/**
	 * 開始行/列を取得します
	 * @return 開始行/列
	 */
	public int getBegin();

	/**
	 * 終了行/列を取得します
	 * @return 終了行/列
	 */
	public int getEnd();

	/**
	 * 指定の行番号、列番号が当セルの範囲内か判定します
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isIncluded(int row, int col);

	/**
	 * 子要素を追加します
	 * @param children 子要素
	 */
	public void addChildren(List<C> children);
}
