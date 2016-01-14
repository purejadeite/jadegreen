package com.purejadeite.jadegreen.definition.option;

import java.util.Map;

/**
 *
 * 値に変更を適用するインターフェイス
 * @author mitsuhiroseino
 *
 */
public interface Option {

	/**
	 * 値に変更を適用します
	 * @return
	 */
	public Object apply(Object values);

	/**
	 * オプションの設定内容をMap形式で取得します
	 * @return オプション設定
	 */
	public Map<String,Object> toMap();

}
