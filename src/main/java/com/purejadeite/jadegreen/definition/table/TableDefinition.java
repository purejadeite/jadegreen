package com.purejadeite.jadegreen.definition.table;

import java.util.List;

import com.purejadeite.jadegreen.definition.Options;
import com.purejadeite.jadegreen.definition.ParentMappingDefinition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;

/**
 * テーブル形式の範囲の情報を保持するクラスのインターフェイスです
 * @author mitsuhiroseino
 */
public interface TableDefinition<C extends TableCellDefinition<?>> extends ParentMappingDefinition<WorksheetDefinition, C> {

	/**
	 * 終了条件になる項目の定義IDを取得します
	 * @return 定義ID
	 */
	public String getEndKeyId();

	/**
	 * 終了条件になる項目の定義IDを設定します
	 * @param endKey 定義ID
	 */
	public void setEndKeyId(String endKey);

	/**
	 * 終了条件になる項目の値を取得します
	 * @return 値
	 */
	public String getEndValue();

	/**
	 * 終了条件になる項目の値を設定します
	 * @param endValue 値
	 */
	public void setEndValue(String endValue);

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
	 * コンバーターでラップされたTable読み込み定義を取得します
	 * @return
	 */
	public Options getOptions();

	/**
	 * 子要素を追加します
	 * @param children 子要素
	 */
	public void addChildren(List<C> children);
}