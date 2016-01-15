package com.purejadeite.jadegreen.definition;

import java.util.Map;

/**
 *
 * Excelファイル読み込みの定義情報インターフェイス
 *
 * @author mitsuhiroseino
 *
 */
public interface Definition<P extends ParentDefinition<?, ?>> {

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
	 * 値にオプションを適用します
	 * @return
	 */
	public Object applyOptions(Object values);

	/**
	 * 定義をMap形式で取得します
	 * @return 定義
	 */
	public Map<String,Object> toMap();

}
