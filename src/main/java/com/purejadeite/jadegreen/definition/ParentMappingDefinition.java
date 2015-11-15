package com.purejadeite.jadegreen.definition;

import java.util.List;

/**
 *
 * Excelファイル読み込みの親定義情報インターフェイス
 *
 * @author mitsuhiroseino
 *
 */
public interface ParentMappingDefinition<P extends ParentMappingDefinition<?, ?>, C extends MappingDefinition<?>>
		extends MappingDefinition<P> {

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

	/**
	 * 配下の定義を取得します
	 *
	 * @param fullId
	 *            当定義から辿った定義ID
	 * @return 対象の定義
	 */
	public MappingDefinition<?> get(String fullId);

	/**
	 * 配下の定義を取得します
	 *
	 * @param ids
	 *            当定義から辿った定義IDの配列
	 * @return 対象の定義
	 */
	public MappingDefinition<?> get(String... ids);

}