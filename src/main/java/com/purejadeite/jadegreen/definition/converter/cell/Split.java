package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 指定された区切り文字で文字列を分割するクラス
 * @author mitsuhiroseino
 *
 */
public class Split extends AbstractStringCellConverter {

	/**
	 * 区切り文字
	 */
	private String splitter;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Split(Map<String, Object> config) {
		super();
		this.splitter = (String) config.get("splitter");
	}

	/**
	 * {@inheritDoc}
	 */
	protected Object applyToString(String value) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		String[] values = StringUtils.split(value, splitter);
		return (Object) Arrays.asList(values);
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("splitter", this.splitter);
		return map;
	}
}
