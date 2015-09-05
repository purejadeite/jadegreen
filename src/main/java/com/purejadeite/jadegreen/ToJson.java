package com.purejadeite.jadegreen;

import java.util.Map;

/**
 *
 * JSON出力インターフェイス
 * @author mitsuhiroseino
 *
 */
public interface ToJson {

	/**
	 * オプションの設定内容をMap形式で取得します
	 * @return オプション設定
	 */
	public Map<String,Object> toMap();

	/**
	 * インスタンスの内容をJSON形式で取得します
	 * @return JSON
	 */
	public String toJson();

}
