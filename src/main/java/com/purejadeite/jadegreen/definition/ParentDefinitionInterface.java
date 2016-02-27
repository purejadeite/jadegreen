package com.purejadeite.jadegreen.definition;

import java.util.List;

/**
 *
 * Excelファイル読み込みの親定義情報インターフェイス
 *
 * @author mitsuhiroseino
 *
 */
public interface ParentDefinitionInterface<P extends ParentDefinitionInterface<?, ?>, C extends DefinitionInterface<?>>
		extends DefinitionInterface<P> {

	/**
	 * 子定義を全て取得します
	 *
	 * @return
	 */
	public List<C> getChildren();

	/**
	 * 子定義を取得します
	 *
	 * @return
	 */
	public C getChild(int index);

	/**
	 * 子定義を追加します
	 *
	 * @param child
	 */
	public void addChild(C child);

	/**
	 * 子要素を追加します
	 * @param children 子要素
	 */
	public void addChildren(List<C> children);

}
