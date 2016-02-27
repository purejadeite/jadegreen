package com.purejadeite.jadegreen.definition.cell;

import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.util.collection.Table;

/**
 * Cell読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface CellDefinitionInterface<P extends ParentDefinitionInterface<?, ?>> extends DefinitionInterface<P> {

	public static final int NO_ADDRESS = 0;

	/**
	 * 列番号
	 */
	public static final String CFG_COLUMN = "column";

	/**
	 * 行番号
	 */
	public static final String CFG_ROW = "row";

	public Object capture(Table<String> table);

}
