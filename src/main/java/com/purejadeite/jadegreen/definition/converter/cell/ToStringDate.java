package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.reader.CellUtils;

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
	public List<Map<String, Object>> toList() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", this.getClass().getSimpleName());
		map.put("dateFormat", dateFormat);
		List<Map<String, Object>> list = super.toList();
		list.add(map);
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", dateFormat=" + dateFormat + "]";
	}
}
