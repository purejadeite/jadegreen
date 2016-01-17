package com.purejadeite.jadegreen.definition.cell;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.ParentDefinition;

/**
 * Cell読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface CellDefinition<P extends ParentDefinition<?, ?>> extends Definition<P> {

	public static final int NO_ADDRESS = 0;

	/**
	 * 行番号
	 */
	public static final String CFG_ROW = "row";

	/**
	 * 列番号
	 */
	public static final String CFG_COLUMN = "column";


	/**
	 * 最少行番号を取得します
	 */
	public int getMinRow();

	/**
	 * 最大行番号を取得します
	 */
	public int getMaxRow();

	/**
	 * 最少列番号を取得します
	 */
	public int getMaxCol();

	/**
	 * 最大列番号を取得します
	 */
	public int getMinCol();

	/**
	 * 指定の行番号、列番号が当セルの範囲内か判定します
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isIncluded(int row, int col);

}
