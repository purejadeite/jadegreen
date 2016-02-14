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
	 * 行番号(0始まり)
	 */
	public static final String CFG_X = "x";

	/**
	 * 列番号(0始まり)
	 */
	public static final String CFG_Y = "y";


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

	/**
	 * 値を取得します
	 * @param value 取得する値
	 * @param appliedValues 取得済みの値
	 * @return 取得した値、または取得した値を追加した取得済みの値
	 */
	public Object applyValue(Object value, Object appliedValues);

}
