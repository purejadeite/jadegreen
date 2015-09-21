package com.purejadeite.jadegreen.definition.generator;

import java.io.Serializable;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractApplier;

/**
 * 値を生成する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractRelatedValueGenerator extends AbstractApplier implements ValueGenerator, Serializable {

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 */
	public AbstractRelatedValueGenerator() {
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("name", this.getClass().getSimpleName());
		return map;
	}

}
