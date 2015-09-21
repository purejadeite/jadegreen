package com.purejadeite.jadegreen.definition;

import com.purejadeite.jadegreen.ToMap;

/**
 *
 * オプション処理インターフェイス
 * @author mitsuhiroseino
 *
 */
public interface Applier extends ToMap {

	/**
	 * オプションを適用します
	 * @return
	 */
	public Object apply(Object values);

}
