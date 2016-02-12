package com.purejadeite.jadegreen.definition;

import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.option.Options;

/**
 *
 * Excelファイル読み込みの定義情報インターフェイス
 *
 * @author mitsuhiroseino
 *
 */
public interface Definition<P extends ParentDefinition<?, ?>> {

	/**
	 * 定義ID
	 */
	public static final String CFG_ID = "id";

	/**
	 * オプション
	 */
	public static final String[] CFG_OPTIONS = {"options", "option"};

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
	 * Mapで利用可能なキーを取得します
	 *
	 * @return
	 */
	public String getKey();

	/**
	 * 親定義を取得します
	 *
	 * @return
	 */
	public P getParent();

	/**
	 * コンバーターなどを取得します
	 * @return コンバーターなどのオプション
	 */
	public Options getOptions();

	/**
	 * 値にオプションを適用します
	 * @return
	 */
	public Object applyOptions(Object values, Content<?, ?> content);

	/**
	 * 定義をMap形式で取得します
	 * @return 定義
	 */
	public Map<String,Object> toMap();

}
