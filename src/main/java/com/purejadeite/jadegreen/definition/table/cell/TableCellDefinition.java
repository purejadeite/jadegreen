package com.purejadeite.jadegreen.definition.table.cell;

import java.util.List;

import com.purejadeite.jadegreen.definition.cell.CellDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.util.collection.Table;

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

	public void setBreakKey(boolean breakKey);

	public boolean isBreakKey();

	public void setBreakValues(List<String> breakValues);

	public List<String> getBreakValues();

	public int getBeginX();

	public int getEndX();

	public int getBeginY();

	public int getEndY();

	public Object capture(Table<String> table, int size);


}
