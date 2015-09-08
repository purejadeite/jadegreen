package com.purejadeite.jadegreen.definition.generator;

import com.purejadeite.jadegreen.ToMap;

/**
 * 値を生成するインターフェイス
 * @author mitsuhiroseino
 *
 */
public interface ValueGenerator extends ToMap {

	/**
	 * オプションを適用します
	 * @return
	 */
	public Object apply(Object values);

}
