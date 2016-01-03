package com.purejadeite.jadegreen.definition.cell;

import com.purejadeite.jadegreen.definition.MappingDefinition;
import com.purejadeite.jadegreen.definition.Options;
import com.purejadeite.jadegreen.definition.ParentMappingDefinition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;

/**
 * Cell読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface CellDefinition<P extends ParentMappingDefinition<?, ?>> extends MappingDefinition<P> {

	public static final int NO_ADDRESS = 0;

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
	 * コンバーターなどを取得します
	 * @return コンバーターなどのオプション
	 */
	public Options getOptions();

	/**
	 * シートの定義を設定します
	 */
	public void setSheet(WorksheetDefinition sheet);
}
