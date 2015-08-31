package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import com.purejadeite.jadegreen.CellUtils;

/**
 * 日付を表す文字列を、別の日付形式の文字列へ変換するクラス
 * @author mitsuhiroseino
 *
 */
public class ToStringDate extends AbstractStringCellConverter {

	private static final long serialVersionUID = -1776705030591016850L;

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
		this.dateFormat = (String) config.get("dateFormat");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertImpl(String value) {
		return CellUtils.getStringDateValue(value, dateFormat);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", dateFormat=" + dateFormat + "]";
	}
}
