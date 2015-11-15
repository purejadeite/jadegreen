package com.purejadeite.jadegreen.definition;

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
	 * 値の出力を行うかを示します
	 *
	 * @return
	 */
	public boolean isNoOutput();

	/**
	 * 親定義を取得します
	 *
	 * @return
	 */
	public P getParent();

}