package com.purejadeite.jadegreen.definition.cell;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.util.collection.Table;

/**
 * Cell読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface CellDefinition<P extends ParentDefinition<?, ?>> extends Definition<P> {

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
