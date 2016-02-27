package com.purejadeite.jadegreen.generator;

import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;

/**
 * 値を生成するインターフェイス
 * @author mitsuhiroseino
 *
 */
public interface GeneratorInterface {

	/**
	 * ジェネレーター種別
	 */
	public static final String CFG_TYPE = "type";

	/**
	 * 値を生成します
	 * @return
	 */
	public Object generate(Object values, ContentInterface<?, ?> content);

	/**
	 * ジェネレーターが付与されている定義を取得します
	 * @return
	 */
	public DefinitionInterface<?> getDefinition();

	/**
	 * ジェネレーターの設定内容をMap形式で取得します
	 * @return ジェネレーター設定
	 */
	public Map<String,Object> toMap();

}
