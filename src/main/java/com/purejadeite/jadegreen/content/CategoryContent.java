package com.purejadeite.jadegreen.content;

import java.util.List;

import com.purejadeite.jadegreen.definition.table.CategoryDefinition;

/**
 * 範囲の情報を保持するクラスのインターフェイスです
 * @author mitsuhiroseino
 */
public interface CategoryContent extends Content<SheetContent, CategoryDefinition<?>> {
	public List<CellContent<?, ?>> getCells();
}
