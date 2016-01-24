package com.purejadeite.jadegreen.content;

import java.util.List;

import com.purejadeite.jadegreen.definition.table.TableDefinition;

/**
 * テーブル形式の範囲の情報を保持するクラスのインターフェイスです
 * @author mitsuhiroseino
 */
public interface TableContent extends Content<WorksheetContent, TableDefinition<?>> {
	public List<TableCellContent<?>> getCells();
	public int size();
}
