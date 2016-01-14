
package com.purejadeite.jadegreen.definition;

/**
 * 結合の定義です
 * @author mitsuhiroseino
 */
public interface JoinDefinition<P extends ParentDefinition<?, ?>> extends Definition<P> {

	/**
	 * 相手のシートのキーとなる定義IDを取得します
	 * @return 定義ID
	 */
	public String getSheetId();

	/**
	 * 自身のシートのキーとなる定義を取得します
	 * @return 定義
	 */
	public Definition<?> getMyKeyDefinition();

	/**
	 * 相手のシートのキーとなる定義を取得します
	 * @return 定義
	 */
	public Definition<?> getKeyDefinition();

	/**
	 * 相手のシートの値となる定義を取得します
	 * @return 定義
	 */
	public Definition<?> getValueDefinition();

}
