package com.purejadeite.jadegreen.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.definition.DefinitionInterface;

/**
 * 指定された区切り文字で文字列を分割した最初の要素を取るオプション
 *
 * @author mitsuhiroseino
 *
 */
public class First extends AbstractPositionCellOption {

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public First(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition, config);
	}

	@Override
	protected String get(String[] values) {
		return values[0];
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}
}
