package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.purejadeite.jadegreen.SxssfUtils;

/**
 * 日付を表す文字列を、別の日付形式の文字列へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToStringDate extends AbstractStringCellConverter {

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {"dateFormat"};

	/**
	 * 日付フォーマット
	 */
	private String dateFormat;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ToStringDate(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.dateFormat = MapUtils.getString(config, "dateFormat");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return SxssfUtils.getStringDate(value, dateFormat);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("dateFormat", dateFormat);
		return map;
	}
}
