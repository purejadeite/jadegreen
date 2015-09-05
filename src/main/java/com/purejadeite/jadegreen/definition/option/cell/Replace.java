package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 文字列の一部を定義されたマップの値に置き換えるクラス
 * @author mitsuhiroseino
 *
 */
public class Replace extends AbstractStringCellConverter {

	/**
	 * 文字列置換マップ
	 */
	private Map<String, String> map;

	/**
	 * コンストラクタ
	 * @param config コンバーターのコンフィグ
	 */
	@SuppressWarnings("unchecked")
	public Replace(Map<String, Object> config) {
		super();
		this.map = (Map<String, String>) config.get("map");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		String val = value;
		for(Map.Entry<String, String> entry : map.entrySet()) {
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
