package com.purejadeite.jadegreen.definition.range;

import java.util.List;

import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.cell.CellDefinition;

/**
 * 範囲の情報を保持するクラスのインターフェイスです
 * @author mitsuhiroseino
 */
public interface RangeDefinition<C extends CellDefinition<?>> extends ParentDefinition<SheetDefinition, C> {

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
