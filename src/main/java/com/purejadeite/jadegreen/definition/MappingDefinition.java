package com.purejadeite.jadegreen.definition;

import java.util.Map;

/**
 *
 * Excelファイル読み込みの定義情報インターフェイス
 *
 * @author mitsuhiroseino
 *
 */
public interface MappingDefinition<P extends ParentMappingDefinition<?, ?>> extends Definition {

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
	 * 同シート配下の全定義を設定します
	 * @param definitions シート配下の全定義
	 */
	public void setDefinitions(Map<String, MappingDefinition<?>> definitions);

}
