package com.purejadeite.jadegreen.definition;

import java.util.List;

/**
 *
 * Excelファイル読み込みの親定義情報インターフェイス
 *
 * @author mitsuhiroseino
 *
 */
public interface ParentDefinition<P extends ParentDefinition<?, ?>, C extends Definition<?>>
		extends Definition<P> {

	/**
	 * 子定義を取得します
	 *
	 * @return
	 */
	public List<C> getChildren();

	/**
	 * 子定義を追加します
	 *
	 * @param child
	 */
	public void addChild(C child);

}