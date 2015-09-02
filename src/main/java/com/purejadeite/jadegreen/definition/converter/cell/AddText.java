package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 文字列の前後に文字列を追加するクラス
 * @author mitsuhiroseino
 *
 */
public class AddText extends AbstractStringCellConverter {

	private static final long serialVersionUID = -7583261972286694964L;

	/**
	 * 接頭語
	 */
	private String prefix;

	/**
	 * 接尾語
	 */
	private String suffix;

	/**
	 * NULLの場合は処理対象外
	 */
	private boolean ignoreEmpty;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public AddText(Map<String, Object> config) {
		super();
		this.prefix = MapUtils.getString(config, "prefix");
		this.suffix = MapUtils.getString(config, "suffix");
		this.ignoreEmpty = MapUtils.getBooleanValue(config, "ignoreEmpty");
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
		return map;
	}

}
