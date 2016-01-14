package com.purejadeite.jadegreen.definition;

import com.purejadeite.jadegreen.ToMap;

/**
 *
 * Excelファイル読み込みの定義情報インターフェイス
 *
 * @author mitsuhiroseino
 *
 */
public interface Definition<P extends ParentDefinition<?, ?>> extends ToMap {

	/**
	 * 定義IDを取得します
	 *
	 * @return
	 */
	public String getId();

	/**
	 * ルート辿った定義IDを取得します
	 *
	 * @return
	 */
	public String getFullId();

	/**
	 * 親定義を取得します
	 *
	 * @return
	 */
	public P getParent();

	/**
	 * 値に変更を適用します
	 * @return
	 */
	public Object apply(Object values);

}
