package com.purejadeite.jadegreen.definition.cell;

import com.purejadeite.jadegreen.definition.range.RangeDefinition;

/**
 * 一覧形式子要素のセル読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface RangeCellDefinition<P extends RangeDefinition<?>> extends CellDefinition<P> {

	/**
	 * 取得可能な状態か判定します
	 * @param value 取得対象の値
	 * @return true:取得可能, false:取得不可
	 */
	public boolean isEndValue(Object value);

}
