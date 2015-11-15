package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.util.RoughlyMapUtils;

/**
 * 文字列の一部を定義されたマップの値に置き換えるクラス
 *
 * @author mitsuhiroseino
 *
 */
public class Replace extends AbstractStringCellConverter {

	private static final long serialVersionUID = 4151445349235116032L;

	private static final String CFG_MAP = "map";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_MAP };

	/**
	 * 文字列置換マップ
	 */
	private Map<String, String> map;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Replace(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.map = RoughlyMapUtils.getMap(config, CFG_MAP);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		// 10000件くらいまでは総当たりの方が早い?
		String val = value;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			val = StringUtils.replace(val, entry.getKey(), entry.getValue());
		}
		return val;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("map", this.map);
		return map;
	}
}
