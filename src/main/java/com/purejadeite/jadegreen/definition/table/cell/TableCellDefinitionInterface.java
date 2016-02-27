package com.purejadeite.jadegreen.definition.table.cell;

import java.util.List;

import com.purejadeite.jadegreen.definition.cell.CellDefinitionInterface;
import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * 一覧形式子要素のセル読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface TableCellDefinitionInterface<P extends TableDefinitionInterface<?>> extends CellDefinitionInterface<P> {

	public void setBreakKey(boolean breakKey);

	public boolean isBreakKey();

	public void setBreakValues(List<String> breakValues);

	public List<String> getBreakValues();

	public Object capture(Table<String> table, int size);


}
