package com.purejadeite.jadegreen.definition.option.cell;

import java.io.Serializable;
import java.util.Map;

import com.purejadeite.jadegreen.definition.option.AbstractOption;

/**
 * 値を生成する抽象クラス
 *
 * @author mitsuhiroseino
 *
 */
abstract public class AbstractRelatedValueGenerator extends AbstractOption implements CellOption, Serializable {

	private static final long serialVersionUID = -4549283171915975394L;

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
