package com.purejadeite.jadegreen.definition.cell.option;

import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;

/**
 * 文字列を定義されたマップに応じた値に置き換えるクラス
 * @author mitsuhiroseino
 *
 */
public class Mapping extends AbstractCellConverter {

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {"map"};

	/**
	 * 値変換マップ
	 */
	private Map<String, String> map;

	/**
	 * <pre>
	 * 変換先が無い場合の動作
	 * true: 元の値を返す
	 * false: nullを返す
	 * </pre>
	 */
	private boolean lazy;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Mapping(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.map = RoughlyMapUtils.getMap(config, "map");
		this.lazy = RoughlyMapUtils.getBooleanValue(config, "lazy");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value) {
		Object mappedValue = null;
		if (map.containsKey(value)) {
			mappedValue = map.get(value);
		} else {
			if (this.lazy) {
				mappedValue = value;
			}
		}
		return mappedValue;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("map", this.map);
		map.put("lazy", this.lazy);
		return map;
	}
}
