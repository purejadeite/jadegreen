package com.purejadeite.jadegreen.option;

import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;

/**
 *
 * 値に変更を適用するインターフェイス
 * @author mitsuhiroseino
 *
 */
public interface OptionInterface {

	/**
	 * オプション種別
	 */
	public static final String CFG_TYPE = "type";

	/**
	 * 値に変更を適用します
	 * @return
	 */
	public Object apply(Object values, ContentInterface<?, ?> content);

	/**
	 * オプションが付与されている定義を取得します
	 * @return
	 */
	public DefinitionInterface<?> getDefinition();

	/**
	 * オプションの設定内容をMap形式で取得します
	 * @return オプション設定
	 */
	public Map<String,Object> toMap();

}
