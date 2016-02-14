package com.purejadeite.jadegreen.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * 文字列の前後に文字列を追加するクラス
 * @author mitsuhiroseino
 *
 */
public class Append extends AbstractStringCellOption {

	private static final long serialVersionUID = -9117326686130712272L;

	protected static final String CFG_PREFIX = "prefix";

	protected static final String CFG_SUFFIX = "suffix";

	protected static final String CFG_IGNORE_EMPTY = "ignoreEmpty";

	/**
	 * 接頭語
	 */
	protected String prefix;

	/**
	 * 接尾語
	 */
	protected String suffix;

	/**
	 * NULL,空文字の場合は処理対象外
	 */
	protected boolean ignoreEmpty;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Append(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		this.prefix = getString(config, CFG_PREFIX);
		this.suffix = getString(config, CFG_SUFFIX);
		this.ignoreEmpty = getBooleanValue(config, CFG_IGNORE_EMPTY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value, Content<?, ?> content) {
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
