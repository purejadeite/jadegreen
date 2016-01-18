package com.purejadeite.jadegreen.definition.option;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Cellの値を変換する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractOption implements Option, Serializable {

	private String id;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractOption(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
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
		return map;
	}

}
