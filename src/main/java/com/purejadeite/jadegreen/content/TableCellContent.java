package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinition;
import com.purejadeite.util.collection.Table;

/**
 * 一覧形式子要素のセル読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface TableCellContent<D extends TableCellDefinition<?>> extends CellContent<TableContent, D> {

	/**
	 * 指定件数でクローズします
	 * @param size 有効な取得件数
	 */
	public void close(int size);

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
