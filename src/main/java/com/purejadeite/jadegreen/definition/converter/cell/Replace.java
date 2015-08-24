package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.LinkedHashMap;
import java.util.List;
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
	public Object convertImpl(String value) {
		String val = value;
		for(Map.Entry<String, String> entry : map.entrySet()) {
			val = StringUtils.replace(val, entry.getKey(), entry.getValue());
		}
		return val;
	}

	public List<Map<String, Object>> toList() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", this.getClass().getSimpleName());
		map.put("map", this.map);
		List<Map<String, Object>> list = super.toList();
		list.add(map);
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", map=" + map + "]";
	}
}
