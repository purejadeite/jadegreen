package com.purejadeite.jadegreen.definition.generator;

import java.util.Map;

import com.purejadeite.jadegreen.definition.Option;

/**
 * 値を生成するインターフェイス
 * @author mitsuhiroseino
 *
 */
public interface ValueGenerator extends Option {

	/**
	 * インスタンスの内容をJSON形式で取得します
	 * @return JSON
	 */
	public String toJson();

	public Map<String,Object> toMap();

}
