package com.purejadeite.jadegreen;

import java.util.Map;

/**
 *
 * 関表現用Map出力インターフェイス
 * @author mitsuhiroseino
 *
 */
public interface ToMap {

	/**
	 * オプションの設定内容をMap形式で取得します
	 * @return オプション設定
	 */
	public Map<String,Object> toMap();

}
