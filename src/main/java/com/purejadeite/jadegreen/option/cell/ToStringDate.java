package com.purejadeite.jadegreen.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.SxssfUtils;
import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.util.SimpleValidator;

/**
 * 日付を表す文字列を、別の日付形式の文字列へ変換するクラス
 *
 * @author mitsuhiroseino
 *
 */
public class ToStringDate extends AbstractStringCellOption {

	private static final long serialVersionUID = -6876337386509102177L;

	protected static final String CFG_DATE_FORMAT = "dateFormat";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_DATE_FORMAT };

	/**
	 * 日付フォーマット
	 */
	protected String dateFormat;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public ToStringDate(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.dateFormat = getString(config, CFG_DATE_FORMAT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value, Content<?, ?> content) {
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
