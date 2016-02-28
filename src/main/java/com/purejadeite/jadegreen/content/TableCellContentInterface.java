package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * 一覧形式子要素のセル読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface TableCellContentInterface<D extends TableCellDefinitionInterface<?, ?>> extends CellContentInterface<TableContentInterface, D> {

	/**
	 * 取得した件数を得します
	 * @return 取得した値の件数
	 */
	public int size();

	/**
	 * 指定されたサイズ分、値を取得します
	 * @param table
	 * @param size
	 * @return
	 */
	public int capture(Table<String> table, int size);

}
