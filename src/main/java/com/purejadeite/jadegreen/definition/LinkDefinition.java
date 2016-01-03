
package com.purejadeite.jadegreen.definition;

/**
 * リンク定義です
 * @author mitsuhiroseino
 */
public interface LinkDefinition<P extends ParentMappingDefinition<?, ?>> extends MappingDefinition<P> {

	/**
	 * 相手のシートのキーとなる定義IDを取得します
	 * @return 定義ID
	 */
	public String getSheetId();

	/**
	 * 自身のシートのキーとなる定義を取得します
	 * @return 定義
	 */
	public MappingDefinition<?> getMyKeyDefinition();

	/**
	 * 相手のシートのキーとなる定義を取得します
	 * @return 定義
	 */
	public MappingDefinition<?> getKeyDefinition();

	/**
	 * 相手のシートの値となる定義を取得します
	 * @return 定義
	 */
	public MappingDefinition<?> getValueDefinition();

}
