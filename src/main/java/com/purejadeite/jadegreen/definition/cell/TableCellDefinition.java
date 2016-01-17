package com.purejadeite.jadegreen.definition.cell;

import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * 一覧形式子要素のセル読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface TableCellDefinition<P extends TableDefinition<?>> extends CellDefinition<P> {

	/**
	 * 取得可能な状態か判定します
	 * @param value 取得対象の値
	 * @return true:取得可能, false:取得不可
	 */
	public boolean isEndValue(Object value);

	public void setBreakId(boolean breakId);

	public void setBreakValue(String breakValue);

}
