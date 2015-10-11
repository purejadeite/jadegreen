package com.purejadeite.jadegreen.definition.option.cell;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.RoughlyMapUtils;

/**
 * 文字列の前後に文字列を追加するクラス
 * @author mitsuhiroseino
 *
 */
public class AddText extends AbstractStringCellConverter {

	private static final long serialVersionUID = -9117326686130712272L;

	private static final String CFG_PREFIX = "prefix";

	private static final String CFG_SUFFIX = "suffix";

	private static final String CFG_IGNORE_EMPTY = "ignoreEmpty";

	/**
	 * 接頭語
	 */
	private String prefix;

	/**
	 * 接尾語
	 */
	private String suffix;

	/**
	 * NULL,空文字の場合は処理対象外
	 */
	private boolean ignoreEmpty;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public AddText(Map<String, Object> config) {
		super();
		this.prefix = RoughlyMapUtils.getString(config, CFG_PREFIX);
		this.suffix = RoughlyMapUtils.getString(config, CFG_SUFFIX);
		this.ignoreEmpty = RoughlyMapUtils.getBooleanValue(config, CFG_IGNORE_EMPTY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		String val = "";
		if (!StringUtils.isEmpty(value)) {
			val = value;
		} else {
			if (ignoreEmpty) {
				return value;
			}
		}
		if (!StringUtils.isEmpty(prefix)) {
			val = prefix + val;
		}
		if (!StringUtils.isEmpty(suffix)) {
			val = val + suffix;
		}
		return val;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("prefix", prefix);
		map.put("suffix", suffix);
		map.put("ignoreEmpty", ignoreEmpty);
		return map;
	}

}
