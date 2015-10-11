package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;
import com.purejadeite.jadegreen.SxssfUtils;

/**
 * 日付を表す文字列を、別の日付形式の文字列へ変換するクラス
 *
 * @author mitsuhiroseino
 *
 */
public class ToStringDate extends AbstractStringCellConverter {

	private static final long serialVersionUID = -6876337386509102177L;

	private static final String CFG_DATE_FORMAT = "dateFormat";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_DATE_FORMAT };

	/**
	 * 日付フォーマット
	 */
	private String dateFormat;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public ToStringDate(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.dateFormat = RoughlyMapUtils.getString(config, CFG_DATE_FORMAT);
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
