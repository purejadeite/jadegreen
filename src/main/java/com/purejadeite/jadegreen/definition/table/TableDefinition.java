package com.purejadeite.jadegreen.definition.table;

import java.util.List;

import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinition;

/**
 * テーブル形式の範囲の情報を保持するクラスのインターフェイスです
 * @author mitsuhiroseino
 */
public interface TableDefinition<C extends TableCellDefinition<?>> extends ParentDefinition<SheetDefinition, C> {

	/**
	 * 列数/行数の上限なし
	 */
	public static final int UNLIMITED = Integer.MAX_VALUE;

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
	public List<String> getBreakValues();

	/**
	 * 終了条件になる項目の値を設定します
	 * @param endValue 値
	 */
	public void setBreakValues(List<String> breakValues);

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

}
