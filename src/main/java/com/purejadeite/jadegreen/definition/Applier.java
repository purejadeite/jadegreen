package com.purejadeite.jadegreen.definition;

import com.purejadeite.jadegreen.ToMap;

/**
 *
 * 値に変更を適用するインターフェイス
 * @author mitsuhiroseino
 *
 */
public interface Applier extends ToMap {

	/**
	 * 値に変更を適用します
	 * @return
	 */
	public Object apply(Object values);

}
