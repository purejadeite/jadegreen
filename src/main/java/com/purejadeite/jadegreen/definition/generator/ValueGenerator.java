package com.purejadeite.jadegreen.definition.generator;

import java.util.Map;

/**
 * 値を生成するインターフェイス
 * @author mitsuhiroseino
 *
 */
public interface ValueGenerator {

	/**
	 * オプションを適用します
	 * @return
	 */
	public Object generate(Object values);

	public Map<String,Object> toMap();

	/**
	 * インスタンスの内容をJSON形式で取得します
	 * @return JSON
	 */
	public String toJson();

}
