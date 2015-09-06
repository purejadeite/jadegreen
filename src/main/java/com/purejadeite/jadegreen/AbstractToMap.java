package com.purejadeite.jadegreen;

import java.util.LinkedHashMap;
import java.util.Map;

/**
*
* Map出抽象クラス
* @author mitsuhiroseino
*
*/
public abstract class AbstractToMap implements ToMap {

	/**
	 * デフォルトコンストラクタ
	 */
	protected AbstractToMap() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return toMap().toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("className", this.getClass().getName());
		return map;
	}

}
