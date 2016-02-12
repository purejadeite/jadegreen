package com.purejadeite.jadegreen.definition.option;

import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;

/**
 *
 * 値に変更を適用するインターフェイス
 * @author mitsuhiroseino
 *
 */
public interface Option {

	/**
	 * コンバータークラス
	 */
	public static final String CFG_TYPE = "type";

	/**
	 * 値に変更を適用します
	 * @return
	 */
	public Object apply(Object values, Content<?, ?> content);

	/**
	 * オプションが付与されている定義を取得します
	 * @return
	 */
	public Definition<?> getDefinition();

	/**
	 * オプションの設定内容をMap形式で取得します
	 * @return オプション設定
	 */
	public Map<String,Object> toMap();

}
