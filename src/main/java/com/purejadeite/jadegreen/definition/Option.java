package com.purejadeite.jadegreen.definition;

import java.util.Map;

/**
 *
 * オプション処理インターフェイス
 * @author mitsuhiroseino
 *
 */
public interface Option {

	/**
	 * オプションを適用します
	 * @return
	 */
	public Object apply(Object values);

	public Map<String,Object> toMap();

	/**
	 * インスタンスの内容をJSON形式で取得します
	 * @return JSON
	 */
	public String toJson();

}
